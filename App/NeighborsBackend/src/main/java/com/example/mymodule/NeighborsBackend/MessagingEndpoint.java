package com.example.mymodule.NeighborsBackend;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiNamespace;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import javax.inject.Named;

import static com.example.mymodule.NeighborsBackend.OfyService.ofy;

/**
 * An endpoint to send messages to devices registered with the backend
 *
 * For more information, see
 * https://developers.google.com/appengine/docs/java/endpoints/
 *
 * NOTE: This endpoint does not use any form of authorization or
 * authentication! If this app is deployed, anyone can access this endpoint! If
 * you'd like to add authentication, take a look at the documentation.
 */
@Api(name = "messaging", version = "v1", namespace = @ApiNamespace(ownerDomain = "NeighborsBackend.mymodule.example.com", ownerName = "NeighborsBackend.mymodule.example.com", packagePath=""))
public class MessagingEndpoint {

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    private double distance(RegistrationRecord A, RegistrationRecord B) {
        double lat1 = A.getLat(),lat2 = B.getLat();
        double lon1 = A.getLng(),lon2 = B.getLng();

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;

        return (dist);
    }

    private static final Logger log = Logger.getLogger(MessagingEndpoint.class.getName());

    /** Api Keys can be obtained from the google cloud console */
    private static final String API_KEY = System.getProperty("gcm.api.key");

    /**
     * Send to the first 10 devices (You can modify this to send to any number of devices or a specific device)
     *
     * @param message The message to send
     */
    public void sendMessage(@Named("message") String message,@Named("user") String user) throws IOException {
        if(message == null || message.trim().length() == 0) {
            log.warning("Not sending message because it is empty");
            return;
        }
        // crop longer messages
        if (message.length() > 1000) {
            message = message.substring(0, 1000) + "[...]";
        }
        Sender sender = new Sender(API_KEY);
        Message msg = new Message.Builder().addData("user", user).addData( "message",message).build();
        List<RegistrationRecord> records = ofy().load().type(RegistrationRecord.class).list();

        RegistrationRecord from = null;
        for(RegistrationRecord record : records) {
            if(record.getUserId().toString().equals(user)==true) {
                from = record;
                break;
            }
        }


        for(RegistrationRecord record : records) {

            if(distance(from,record)>2.0) {
                log.warning(String.valueOf(distance(from,record)));
                log.fine(String.valueOf(distance(from,record)));
                //log.warning("Mesage Not Sent!!!: " + message + " sent from" + from.getLat() + " " + from.getLng() + "to " + record.getLat() + " " + record.getLng() + " DISTANCE IS: " + distance(from,record));
                continue;
            }
            Result result = sender.send(msg, record.getRegId(), 5);
            if (result.getMessageId() != null) {
                log.info("Message sent to " + record.getRegId());
                log.warning("Sent by: "+ user +"Mesage: " + message + " sent from" + from.getLat() + " " + from.getLng() + "to " + record.getLat() + " " + record.getLng() + "IS: " + distance(from,record));
                String canonicalRegId = result.getCanonicalRegistrationId();
                if (canonicalRegId != null) {
                    // if the regId changed, we have to update the datastore
                    log.info("Registration Id changed for " + record.getRegId() + " updating to " + canonicalRegId);
                    record.setRegId(canonicalRegId);
                    ofy().save().entity(record).now();
                }
            } else {
                String error = result.getErrorCodeName();
                if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
                    log.warning("Registration Id " + record.getRegId() + " no longer registered with GCM, removing from datastore");
                    // if the device is no longer registered with Gcm, remove it from the datastore
                    ofy().delete().entity(record).now();
                }
                else {
                    log.warning("Error when sending message : " + error);
                }
            }
        }
    }

    /**
     * Send to the first 10 devices (You can modify this to send to any number of devices or a specific device)
     *
     * @param message The message to send
     */
    public void sendPrivateMessage(@Named("message") String message,@Named("user") String user,@Named("target_user") String targetUser) throws IOException {
        if(message == null || message.trim().length() == 0) {
            log.warning("Not sending message because it is empty");
            return;
        }
        // crop longer messages
        if (message.length() > 1000) {
            message = message.substring(0, 1000) + "[...]";
        }
        Sender sender = new Sender(API_KEY);
        Message msg = new Message.Builder().addData("user", user).addData( "message", message + "!" ).addData("target_user",targetUser).build();
        List<RegistrationRecord> records = ofy().load().type(RegistrationRecord.class).list();
        for(RegistrationRecord record : records) {
            if(record.getUserId().toString().equals(targetUser)==false && record.getUserId().toString().equals(user)==false) continue;
            Result result = sender.send(msg, record.getRegId(), 5);
            if (result.getMessageId() != null) {
                log.info("Private Message sent to " + record.getRegId());
                log.warning("Sent by: "+ user + "to " + targetUser);
                String canonicalRegId = result.getCanonicalRegistrationId();
                if (canonicalRegId != null) {
                    // if the regId changed, we have to update the datastore
                    log.info("Registration Id changed for " + record.getRegId() + " updating to " + canonicalRegId);
                    record.setRegId(canonicalRegId);
                    ofy().save().entity(record).now();
                }
            } else {
                String error = result.getErrorCodeName();
                if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
                    log.warning("Registration Id " + record.getRegId() + " no longer registered with GCM, removing from datastore");
                    // if the device is no longer registered with Gcm, remove it from the datastore
                    ofy().delete().entity(record).now();
                }
                else {
                    log.warning("Error when sending message : " + error);
                }
            }
        }
    }
}
