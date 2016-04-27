package com.brandon.yamba;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StatusActivity extends AppCompatActivity implements StatusFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
    }

    public void onFragmentInteraction(Uri uri) {
        // empty
    }
}
