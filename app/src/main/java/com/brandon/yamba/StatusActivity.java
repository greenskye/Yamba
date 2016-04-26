package com.brandon.yamba;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
//                try {
//                    YambaClient yamba = new YambaClient("sutdent", "password");
//                    yamba.postStatus(status);
//                } catch (YambaClientException e) {
//                    e.printStackTrace();
//                }
                Toast.makeText(StatusActivity.this, status, Toast.LENGTH_SHORT).show();
            }
        });


    }
}
