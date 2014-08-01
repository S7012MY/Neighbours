package com.androidcamp.neighbors;

import android.app.Application;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by demouser on 8/1/14.
 */
public class NeighbourApplication extends Application {

    public static String LOG_TAG = "Neighbour app";

    /* Client used to interact with Google APIs. */
    public static GoogleApiClient sGoogleApiClient;
}
