package com.androidcamp.neighbors.ui;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androidcamp.neighbors.GcmRegistrationAsyncTask;
import com.androidcamp.neighbors.LocationHelper;
import com.androidcamp.neighbors.R;
import com.androidcamp.neighbors.db.Conversation;
import com.androidcamp.neighbors.db.Group;
import com.androidcamp.neighbors.db.NeighboursContract;
import com.androidcamp.neighbors.db.NeighboursDatabase;
import com.androidcamp.neighbors.db.NeighboursDbHelper;
import com.androidcamp.neighbors.db.User;
import com.example.mymodule.neighborsbackend.messaging.Messaging;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;


public class HomeActivity extends Activity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LoginDialog.SignInHandler
{
    LocationHelper lh;

    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;
    private boolean isGroupSelected = true;

    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;
    //private PlusClient mPlusClient;

    /* A flag indicating that a PendingIntent is in progress and prevents
     * us from starting further intents.
     */
    private boolean mIntentInProgress;

    private TextView groupView;
    private TextView privateChatView;
    private TextView sendToAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        lh = new LocationHelper();
        lh.onCreate(this);
        groupView = (TextView) findViewById(R.id.groups_title);
        privateChatView = (TextView) findViewById(R.id.private_chat_title);
        sendToAll = (TextView) findViewById(R.id.send_all_neighbours);
        sendToAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, BroadcastChatActivity.class);
                intent.putExtra("user_id", Plus.AccountApi.getAccountName(mGoogleApiClient));
                startActivity(intent);
            }
        });

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();


        findViewById(R.id.groups_title).setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {
                if(v.getId() == R.id.groups_title && isGroupSelected) {
                    // DO nothing
                }
                else {
                    //TODO:inflate into user list
                }
            }
        });

        findViewById(R.id.private_chat_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.private_chat_title && !isGroupSelected) {
                    // DO nothing
                }
                else {
                    //TODO:inflate into group list
                }
            }
        });


      /*  findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.sign_in_button
                        && !mGoogleApiClient.isConnecting()) {
                    mSignInClicked = true;
                    resolveSignInError();
                }
            }
        });
*/
    }


    @Override
    protected void onStart() {
        super.onStart();
        lh.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        lh.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendMsg() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {


                Messaging.Builder builder = new Messaging.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);
                Messaging endpoint = builder.build();
                try {
                    //endpoint.messagingEndpoint().sendMessage("sad", Plus.AccountApi.getAccountName(mGoogleApiClient)).execute();
                    endpoint.messagingEndpoint().sendPrivateMessage("private", Plus.AccountApi.getAccountName(mGoogleApiClient), Plus.AccountApi.getAccountName(mGoogleApiClient)).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    /* Track whether the sign-in button has been clicked so that we know to resolve
 * all issues preventing sign-in without waiting.
 */
    private boolean mSignInClicked;

    /* Store the connection result from onConnectionFailed callbacks so that we can
     * resolve them when the user clicks sign-in.
     */
    private ConnectionResult mConnectionResult;

    /* A helper method to resolve the current ConnectionResult error. */
    //
    public void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                startIntentSenderForResult(mConnectionResult.getResolution().getIntentSender(),
                        RC_SIGN_IN, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                // The intent was canceled before it was sent.  Return to the default
                // state and attempt to connect to get an updated ConnectionResult.
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        mConnectionResult = result;
        new LoginDialog().show(getFragmentManager(), "HI");
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Fragment loginDialog = getFragmentManager().findFragmentByTag("HI");
        if (loginDialog != null) {
            ((DialogFragment) loginDialog).dismiss();
        }

        Toast.makeText(this, "User is connected!" + Plus.AccountApi.getAccountName(mGoogleApiClient), Toast.LENGTH_LONG).show();

        //LocationClient mLocationClient = new LocationClient(this, this, this);
        //mLocationClient.connect();
        //Location l = mLocationClient.getLastLocation();

        Location l = lh.getLocation();
        double lat = l.getLatitude(), lng = l.getLongitude();

        new GcmRegistrationAsyncTask(Plus.AccountApi.getAccountName(mGoogleApiClient), lat, lng).execute(this);
        //sendMsg();
        //TODO use the token to retrieve user's basic profile

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            if (responseCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }

    // add some raw data for testing
    private void insertRawData() {
    //public void onDisconnected() {
        NeighboursDbHelper myHelper = new NeighboursDbHelper(getApplicationContext());
        SQLiteDatabase myDatabase = myHelper.getWritableDatabase();


        for(int i = 0; i < 10; i ++) {
            User user = new User("id" + i, "user" + i, "mail" + i, i + "," +i, null, "F");
            Group group = new Group("id" + i, "group" + i, "location" + i, null);
            Timestamp tm = new Timestamp(new Date().getTime());
            Conversation conversation = new Conversation("id" + i, tm, false, "user" +i, "user" + (((i -1) == 0)?2: i-1),"message" + i );

            myDatabase.update(
                    NeighboursContract.UserEntry.TABLE_NAME,
                    user.toContentValues(),
                    null,
                    null);

            myDatabase.update(
                    NeighboursContract.GroupEntry.TABLE_NAME,
                    group.toContentValues(),
                    null,
                    null);

            myDatabase.update(
                    NeighboursContract.ConversationEntry.TABLE_NAME,
                    conversation.toContentValues(),
                    null,
                    null);
        }
        myDatabase.close();
    }

}
