package com.example.paezand.twittercloneapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedActivity extends AppCompatActivity {

    @BindView(R.id.tweets_container)
    protected ListView tweetsListView;

    List<Map<String, String>> tweetData = new ArrayList<>();

    SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        ButterKnife.bind(this);

        getTweetsFromFollowers();

        simpleAdapter= new SimpleAdapter(this,
                tweetData,
                android.R.layout.simple_list_item_2,
                new String[] {"content", "username"},
                new int[] {android.R.id.text1, android.R.id.text2});

        tweetsListView.setAdapter(simpleAdapter);
    }

    public void getTweetsFromFollowers() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tweet");
        query.whereContainedIn("username", ParseUser.getCurrentUser().getList("isFollowing"));
        query.orderByDescending("createdAt");
        query.setLimit(20);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (ParseObject tweet : objects) {
                            Map<String, String> tweetInfo = new HashMap<>();
                            tweetInfo.put("content", tweet.getString("tweet"));
                            tweetInfo.put("username", tweet.getString("username"));
                            tweetData.add(tweetInfo);
                        }
                    }
                    simpleAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
