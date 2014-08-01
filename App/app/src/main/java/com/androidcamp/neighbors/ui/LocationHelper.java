package com.androidcamp.neighbors;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

/**
 * Created by demouser on 01/08/2014.
 */
public class LocationHelper implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener {
    LocationClient mLocationClient;
    /*
     * Called by Location Services when the request to connect the
     * client finishes successfully. At this point, you can
     * request the current location or start periodic updates
     */

    private boolean isConnected;
    @Override
    public void onConnected(Bundle dataBundle) {
        isConnected = true;
    }

    /*
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     */
    @Override
    public void onDisconnected() {
    }

    /*
     * Called by Location Services if the attempt to
     * Location Services fails.
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    public void onCreate(Context context) {
        /*
         * Create a new location client, using the enclosing class to
         * handle callbacks.
         */
        mLocationClient = new LocationClient(context, this, this);
        mLocationClient.connect();
    }

    public void onStart() {
        // Connect the client.
        mLocationClient.connect();
    }

    public void onStop() {
        // Disconnecting the client invalidates it.
        mLocationClient.disconnect();
    }

    public Location getLocation() {
        if (isConnected) {
            return mLocationClient.getLastLocation();
        }
        return null;
    }
}