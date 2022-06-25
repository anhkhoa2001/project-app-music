package com.example.appmusic.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.appmusic.api.MusicApi;
import com.example.appmusic.activities.Base;
import com.example.appmusic.activities.MainActivity;
import com.example.appmusic.models.User;
import com.example.appmusic.R;
import com.example.appmusic.result.EResponse;

public class LoginFragment extends Fragment {

    TextView register;
    Button submit;
    TextView username;
    TextView password;
    ImageView backToHome;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        register = view.findViewById(R.id.registor);
        submit = view.findViewById(R.id.submit);
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        backToHome = view.findViewById(R.id.back_to_home);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(username.getText().toString(), password.getText().toString());
                new InsertTask(getActivity()).execute("/login", user);
                Base.username = user.getUsername();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                RegisterFragment nextFrag= new RegisterFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.layout_user, nextFrag)
                        .commit();
            }
        });

        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;

    }


    private class InsertTask extends AsyncTask<Object, Void, String> {
        protected ProgressDialog dialog;
        protected Context context;
        public InsertTask(Context context) {
            this.context = context;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Logining....");
            this.dialog.show();
        }
        @Override
        protected String doInBackground(Object... params) {
            String res = null;
            try {
                res = MusicApi.login((String) params[0], (User) params[1]);
            }
            catch(Exception e) {
                Log.v("Music","ERROR : " + e);
                e.printStackTrace();
            }
            return res;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            if(!result.equals(EResponse.FAILED.toString())) {
                Base.token = result;
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            } else {
                Toast.makeText(context, EResponse.FAILED.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }


}
