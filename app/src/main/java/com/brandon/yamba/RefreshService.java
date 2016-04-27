package com.brandon.yamba;

import android.app.IntentService;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

import java.util.List;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class RefreshService extends IntentService {
//    // TODO: Rename actions, choose action names that describe tasks that this
//    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
//    public static final String ACTION_FOO = "com.brandon.yamba.action.FOO";
//    public static final String ACTION_BAZ = "com.brandon.yamba.action.BAZ";
//
//    // TODO: Rename parameters
//    public static final String EXTRA_PARAM1 = "com.brandon.yamba.extra.PARAM1";
//    public static final String EXTRA_PARAM2 = "com.brandon.yamba.extra.PARAM2";

    public static final String TAG = "RefreshService";

    public RefreshService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //TODO refactor
    private String userID = "student";
    private String password = "password";
    private String serverURL = "http://yamba.marakana.com/api"; // Correct URL: http://yamba.newcircle.com/api

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Intent Delivered: " + intent);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        userID = prefs.getString("username", userID);
        password = prefs.getString("password", password);
        serverURL = prefs.getString("server", serverURL);

        if (TextUtils.isEmpty(userID) || TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Update your username and password in Settings", Toast.LENGTH_LONG).show();
            return;
        }

        YambaClient yambaClient = new YambaClient(userID, password, serverURL);
        try {
            List<YambaClient.Status> timeline = yambaClient.getTimeline(20);
            for (YambaClient.Status s : timeline )
            {
                Log.d(TAG, s.getUser() + ": " + s.getMessage() );
            }
        } catch (YambaClientException e) {
            e.printStackTrace();
        }
        return;

//        if (intent != null) {
//            final String action = intent.getAction();
//            if (ACTION_FOO.equals(action)) {
//                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
//                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
//                handleActionFoo(param1, param2);
//            } else if (ACTION_BAZ.equals(action)) {
//                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
//                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
//                handleActionBaz(param1, param2);
//            }
//        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
