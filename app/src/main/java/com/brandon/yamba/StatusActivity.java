package com.brandon.yamba;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class StatusActivity extends AppCompatActivity {

    public static final String TAG = "yamba.StatusActivity";
    private EditText editStatus;
    private Button buttonTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        editStatus = (EditText) findViewById(R.id.edit_status);
        buttonTweet = (Button) findViewById(R.id.button_tweet);

        buttonTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = editStatus.getText().toString();
                Log.d(TAG, "Status to send" + status);

                new PostTask().execute(status);
            }
        });


    }

    private final class PostTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String status = params[0];
            String result = "Unknown Result";
            YambaClient yambaCloud = new YambaClient("student", "password", "http://yamba.newcircle.com/api");
            try {
                yambaCloud.postStatus( status );
                Log.e(TAG, "Status sent: " + status);
                result = "Status sent: " + status;
            } catch (YambaClientException e) {
                Log.e(TAG, "Yamba exception: " + e.getMessage());
                e.printStackTrace();
                result = "Yamba exception: " + e.getMessage();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(StatusActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }
}
