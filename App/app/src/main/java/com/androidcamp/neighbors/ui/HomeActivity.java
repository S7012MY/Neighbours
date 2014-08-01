package com.androidcamp.neighbors.ui;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.content.IntentSender;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidcamp.neighbors.AdaptersHelper;
import com.androidcamp.neighbors.GcmRegistrationAsyncTask;
import com.androidcamp.neighbors.LocationHelper;
import com.androidcamp.neighbors.NeighbourApplication;
import com.androidcamp.neighbors.R;
import com.androidcamp.neighbors.db.Conversation;
import com.androidcamp.neighbors.db.Group;
import com.androidcamp.neighbors.db.NeighboursContract;
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
import java.util.ArrayList;
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
    //private PlusClient mPlusClient;

    /* A flag indicating that a PendingIntent is in progress and prevents
     * us from starting further intents.
     */
    private boolean mIntentInProgress;

    private TextView privateChatView;
    private TextView sendToAll;
    private ListView conversationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        lh = new LocationHelper() {
            @Override
            public void onLocationChanged(Location location) {

                super.onLocationChanged(location);
                Toast.makeText(HomeActivity.this,"Location Inaccurate"+ location.getAccuracy(),Toast.LENGTH_LONG).show();
                if(location.getAccuracy() > 300) {
                    return;

                }

                 double lat = location.getLatitude(), lng = location.getLongitude();
                 removeLocationUpdates();
                new GcmRegistrationAsyncTask(Plus.AccountApi.getAccountName(NeighbourApplication.sGoogleApiClient), lat, lng).execute(HomeActivity.this);
            }
        };
        lh.onCreate(this);
        privateChatView = (TextView) findViewById(R.id.private_chat_list_title);
        sendToAll = (TextView) findViewById(R.id.send_all_neighbours);
        conversationList = (ListView) findViewById(R.id.conversation_list);
        sendToAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, BroadcastChatActivity.class);
                intent.putExtra("user_id",Plus.AccountApi.getAccountName(NeighbourApplication.sGoogleApiClient));
                startActivity(intent);
            }
        });

        NeighbourApplication.sGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();


        conversationList.setAdapter(new ChatListAdapter(this));


      /*  findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.sign_in_button
                        && !NeighbourApplication.sGoogleApiClient.isConnecting()) {
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
        NeighbourApplication.sGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        lh.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (NeighbourApplication.sGoogleApiClient.isConnected()) {
            NeighbourApplication.sGoogleApiClient.disconnect();
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
                    //endpoint.messagingEndpoint().sendMessage("sad", Plus.AccountApi.getAccountName(NeighbourApplication.sGoogleApiClient)).execute();
                    endpoint.messagingEndpoint().sendPrivateMessage("private",Plus.AccountApi.getAccountName(NeighbourApplication.sGoogleApiClient),Plus.AccountApi.getAccountName(NeighbourApplication.sGoogleApiClient)).execute();
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
                NeighbourApplication.sGoogleApiClient.connect();
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

        Toast.makeText(this, "User is connected!" + Plus.AccountApi.getAccountName(NeighbourApplication.sGoogleApiClient), Toast.LENGTH_LONG).show();


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

            if (!NeighbourApplication.sGoogleApiClient.isConnecting()) {
                NeighbourApplication.sGoogleApiClient.connect();
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

    public static class ChatListAdapter extends AdaptersHelper.MessageListAdapter {


        private ArrayList<AdaptersHelper.Message> messages;

        private Activity mActivity;

        private Intent mOnClickSendIntent;

        private ChatListAdapter(Activity activity) {
            super(activity, new Intent(activity, PrivateChatActivity.class));
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View resultView = super.getView(position, convertView, parent);
            TextView messageText = (TextView) resultView.findViewById(R.id.message_text);
            messageText.setMaxLines(1);
            return resultView;
        }

    }

}
