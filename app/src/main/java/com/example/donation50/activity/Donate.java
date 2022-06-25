package com.example.donation50.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.donation50.R;
import com.example.donation50.api.DonationApi;
import com.example.donation50.model.Donation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Donate extends Base {
    private Button donateButton;
    private RadioGroup paymentMethod;
    private ProgressBar progressBar;
    private NumberPicker amountPicker;
    private EditText amountText;
    private TextView amountTotal;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        donateButton = findViewById(R.id.donateButton);
        donateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donateButtonPressed(v);
            }
        });

        paymentMethod = (RadioGroup)   findViewById(R.id.paymentMethod);
        progressBar   = (ProgressBar)  findViewById(R.id.progressBar);
        amountPicker  = (NumberPicker) findViewById(R.id.amountPicker);
        amountText    = (EditText)     findViewById(R.id.paymentAmount);
        amountTotal   = (TextView)     findViewById(R.id.total);

        amountPicker.setMinValue(0);
        amountPicker.setMaxValue(1000);
        progressBar.setMax(10000);
        setProgressBarAndAmountTotal(app.getTotalDonated());
//        new GetAllTask(this).execute("/donation");
    }

    public void donateButtonPressed (View view) {
        String method = paymentMethod.getCheckedRadioButtonId() == R.id.paypal ? "PayPal" : "Direct";
        int donatedAmount =  amountPicker.getValue();
        if (donatedAmount == 0) {
            String text = amountText.getText().toString();
            if (!text.equals(""))
                donatedAmount = Integer.parseInt(text);
        }
        if (donatedAmount > 0) {
            int upvotes = random.nextInt(100);
            Donation donation = new Donation(donatedAmount, method, upvotes%7);
            app.newDonation(donation);
            progressBar.setProgress(app.getTotalDonated());
            String totalDonatedStr = "$" + app.getTotalDonated();
            amountTotal.setText(totalDonatedStr);
            Log.v("Donate", donation.toString());
//            new InsertTask(this).execute("/donation/insert", donation);
        }
        amountPicker.setValue(0);
        amountText.setText("");
    }

    @Override
    public void reset(MenuItem item) {
        app.dbManage.reset();
//        deleteAllDonation();
        setProgressBarAndAmountTotal(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuReport :
                super.report();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setProgressBarAndAmountTotal(int n) {
        progressBar.setProgress(n);
        amountTotal.setText("$" + n);
    }

/*    public void deleteAllDonation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All Donation?");
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setMessage("Are you sure you want to Delete All the Donations?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int ids) {
                new ResetTask(Donate.this).execute("/donation/reset");
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int ids) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }*/
/*
    private class GetAllTask extends AsyncTask<String, Void, List<Donation>> {
        protected ProgressDialog dialog;
        protected Context context;
        public GetAllTask(Context context) {
            this.context = context;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Retrieving Donations List");
            this.dialog.show();
        }
        @Override
        protected List<Donation> doInBackground(String... params) {
            List<Donation> list = new ArrayList<>();
            try {
                Log.v("Donate", "Donation App Getting All Donations");
                list = DonationApi.getAll(params[0]);
            }
            catch (Exception e) {
                Log.v("Donate", "ERROR : " + e);
                e.printStackTrace();
            }
            return list;
        }
        @Override
        protected void onPostExecute(List<Donation> result) {
            super.onPostExecute(result);
            app.setDonations(result);
            setProgressBarAndAmountTotal(app.getTotalDonated());
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
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
            this.dialog.setMessage("Saving Donation....");
            this.dialog.show();
        }
        @Override
        protected String doInBackground(Object... params) {
            String res = null;
            try {
                res = DonationApi.insert((String) params[0], (Donation) params[1]);
            }
            catch(Exception e) {
                Log.v("Donate","ERROR : " + e);
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

            Toast.makeText(Donate.this, result, Toast.LENGTH_LONG).show();
        }
    }

    //xóa hết tất cả dữ liệu trên server
    private class ResetTask extends AsyncTask<Object, Void, String> {
        protected ProgressDialog dialog;
        protected Context context;
        public ResetTask(Context context) {
            this.context = context;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Deleting Donations....");
            this.dialog.show();
        }
        @Override
        protected String doInBackground(Object... params) {
            String res = null;
            try {
                res = DonationApi.deleteAll((String)params[0]);
            }
            catch(Exception e) {
                Log.v("Donate"," RESET ERROR : " + e);
                e.printStackTrace();
            }
            return res;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            app.totalDonated = 0;
            setProgressBarAndAmountTotal(0);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            Toast.makeText(Donate.this, result, Toast.LENGTH_LONG).show();
        }
    }*/
}