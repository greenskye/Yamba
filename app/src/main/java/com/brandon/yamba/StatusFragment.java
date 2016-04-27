package com.brandon.yamba;

import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class StatusFragment extends Fragment {

    private static final String TAG = "yamba.StatusActivity";
    private EditText editStatus;
    private Button buttonTweet;
    private TextView textCount;
    private int defaultTextColor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status, container, false);

        editStatus = (EditText) view.findViewById(R.id.edit_status);
        buttonTweet = (Button) view.findViewById(R.id.button_tweet);
        textCount = (TextView) view.findViewById(R.id.textCount);
        defaultTextColor = textCount.getTextColors().getDefaultColor();

        buttonTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = editStatus.getText().toString();
                Log.d(TAG, "Status to send" + status);

                new PostTask().execute(status);
            }
        });


        editStatus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int count = 140 - editStatus.length();
                textCount.setText(Integer.toString(count));
                if (count < 10)
                {
                    textCount.setTextColor(Color.RED);
                }
                else {
                    textCount.setTextColor(defaultTextColor);
                }

                if(count < 0){
                    buttonTweet.setEnabled(false);
                } else {
                    buttonTweet.setEnabled(true);
                }
            }
        });

        return view;
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
            Toast.makeText(StatusFragment.this.getActivity(), result, Toast.LENGTH_SHORT).show();
        }
    }
}
