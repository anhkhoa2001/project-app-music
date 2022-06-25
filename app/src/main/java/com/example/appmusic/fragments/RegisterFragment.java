package com.example.appmusic.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.appmusic.api.MusicApi;
import com.example.appmusic.models.User;
import com.example.appmusic.R;

public class RegisterFragment extends Fragment {

    TextView backToLogin;
    TextView name;
    TextView username;
    TextView pass;
    TextView repass;
    TextView email;
    TextView phone;
    Button submitRegister;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registor, container, false);

        init(view);
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginFragment nextFrag= new LoginFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.layout_user, nextFrag)
                        .commit();
            }
        });

        submitRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pass.getText().toString().equals(repass.getText().toString())) {
                    User user = new User(name.getText().toString(), username.getText().toString(),
                            pass.getText().toString(), email.getText().toString(), phone.getText().toString());
                    new InsertTask(context).execute("/registor", user);
                    reset();
                } else {
                    Toast.makeText(context, "Vui long nhap lai thong tin", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    public void init(View view) {
        context = getActivity();

        backToLogin = view.findViewById(R.id.login);
        name = view.findViewById(R.id.registor_name);
        username = view.findViewById(R.id.register_username);
        pass = view.findViewById(R.id.pass);
        repass = view.findViewById(R.id.re_pass);
        phone = view.findViewById(R.id.register_phone);
        email = view.findViewById(R.id.email);
        submitRegister = view.findViewById(R.id.submit_register);
    }

    public void reset() {
        name.setText("");
        username.setText("");
        pass.setText("");
        repass.setText("");
        phone.setText("");
        email.setText("");
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
            this.dialog.setMessage("Registering....");
            this.dialog.show();
        }
        @Override
        protected String doInBackground(Object... params) {
            String res = null;
            try {
                res = MusicApi.register((String) params[0], (User) params[1]);
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

            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        }
    }
}
