package com.example.mymodule.NeighborsBackend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/** The Objectify object model for device registrations we are persisting */
@Entity
public class RegistrationRecord {

    @Id
    Long id;

    @Index
    private String regId;

    @Index
    private String userId;

    private double lat;
    private double lng;

    // you can add more fields...

    public RegistrationRecord() {}

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getUserId(){ return userId;}

    public double getLat() {return lat;}
    public double getLng() {return lng;}

    public void setLat(double lat) {this.lat=lat;}
    public void setLng(double lng) {this.lng=lng;}

    public void setUserId(String userId) { this.userId=userId; }
}