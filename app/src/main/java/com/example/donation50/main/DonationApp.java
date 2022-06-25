package com.example.donation50.main;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.example.donation50.db.DBManage;
import com.example.donation50.model.Donation;

import java.util.ArrayList;
import java.util.List;


public class DonationApp extends Application {
    public final int target = 10000;
    public int totalDonated = 0;
    public List<Donation> donations = new ArrayList<>();
    public DBManage dbManage;

    public boolean newDonation(Donation donation) {
        boolean targetAchieved = totalDonated > target;
        if (!targetAchieved){
//            donations.add(donation);
            dbManage.add(donation);
        }
        else {
            Toast.makeText(this, "Target Exceeded!", Toast.LENGTH_SHORT).show();
        }
        return targetAchieved;
    }

    public List<Donation> getDonations() {
//        return donations;
        return dbManage.getAll();
    }

    public void setDonations(List<Donation> donations) {
        this.donations = donations;
    }

    public int getTotalDonated() {
        int sum = 0;
        for(int i=0; i<this.getDonations().size(); i++) {
            sum+= this.getDonations().get(i).getAmount();
        }
        return sum;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("Donate Message", "Donation App Started");
        dbManage = new DBManage(this);
        Log.v("Donate", "Database Created");
    }
}
