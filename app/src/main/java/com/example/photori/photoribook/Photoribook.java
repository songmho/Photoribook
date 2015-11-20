package com.example.photori.photoribook;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;

/**
 * Created by songmho on 15. 11. 20..
 */
public class Photoribook extends Application {
    String APPLICATION_ID="o9KijpnT1lIpvdqnLzRNXuvaCUwvqCWlbJyNsKAB";
    String CLIENT_KEY="Kuu7JOwXgg4b1vJoDoBpEMXOex8rCxBmr1yUR9GK";
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);
        ParseACL defaultACL=new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }
}
