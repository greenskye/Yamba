package com.brandon.yamba;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StatusFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StatusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatusFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = "yamba.StatusActivity";
    private EditText editStatus;
    private Button buttonTweet;
    private TextView textCount;
    private int defaultTextColor;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String userID = "student";
    private String password = "password";
    private String serverURL = "http://yamba.marakana.com/api"; // Correct URL: http://yamba.newcircle.com/api

    private OnFragmentInteractionListener mListener;

    public StatusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment testFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatusFragment newInstance(String param1, String param2) {
        StatusFragment fragment = new StatusFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_status, container, false);

        editStatus = (EditText) view.findViewById(R.id.edit_status);
        buttonTweet = (Button) view.findViewById(R.id.button_tweet);
        textCount = (TextView) view.findViewById(R.id.textCount);
        defaultTextColor = textCount.getTextColors().getDefaultColor();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        userID = prefs.getString("username", userID);
        password = prefs.getString("password", password);
        serverURL = prefs.getString("server", serverURL);

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
            YambaClient yambaCloud = new YambaClient(userID, password, serverURL);
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
