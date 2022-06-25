package com.example.donation50.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.donation50.main.DonationApp;
import com.example.donation50.R;

public class Base extends AppCompatActivity {
    public DonationApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (DonationApp) getApplication();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        app.dbManage.close();
    }

    //tạo menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_donate, menu);
        return true;
    }

    //xử lí khi ấn vào menu
    @Override
    public boolean onPrepareOptionsMenu (Menu menu){
        super.onPrepareOptionsMenu(menu);
        MenuItem report = menu.findItem(R.id.menuReport);
        MenuItem donate = menu.findItem(R.id.menuDonate);
        MenuItem reset = menu.findItem(R.id.menuReset);
        if(this instanceof Donate){
            donate.setVisible(false);
            if(!app.getDonations().isEmpty()) {
                report.setVisible(true);
                reset.setEnabled(true);
            }
        }
        else {
            report.setVisible(false);
            donate.setVisible(true);
            reset.setVisible(false);
        }
        return true;
    }

    //tạo ra activity report
    public void report() {
        startActivity (new Intent(this, Report.class));
    }
    //tạo ra activity donate
    public void donate() {
        startActivity (new Intent(this, Donate.class));
    }
    public void reset(MenuItem item) {}
}
