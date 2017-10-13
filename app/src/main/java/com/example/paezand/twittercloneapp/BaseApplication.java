package com.example.paezand.twittercloneapp;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

/**
 * Created by paezand on 10/4/17.
 */

public class BaseApplication extends Application {

//    var api = new ParseServer({
//    databaseURI: "mongodb://root:UxqjAw1wfZ9X@127.0.0.1:27017/bitnami_parse",
//    cloud: "./node_modules/parse-server/lib/cloud-code/Parse.Cloud.js",
//    appId: "a2923bf73a9d6d852ca2af641459d9787d43aca4",
//    masterKey: "8971843adc286944c5dc72869c44c247a49d957f",
//    fileKey: "f49733e659f6f0a5972002cd64840a9a3e748b65",
//    serverURL: "http://18.221.217.146:80/parse"

    @Override
    public void onCreate() {
        super.onCreate();

        //Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        //Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("a2923bf73a9d6d852ca2af641459d9787d43aca4")
                .clientKey("8971843adc286944c5dc72869c44c247a49d957f")
                .server("http://18.221.217.146:80/parse")
                .build()
        );

//        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
