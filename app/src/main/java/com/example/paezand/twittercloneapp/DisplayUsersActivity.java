package com.example.paezand.twittercloneapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DisplayUsersActivity extends AppCompatActivity {

    @BindView(R.id.user_list)
    protected ListView userList;

    List<String> users = new ArrayList<>();

    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_users);
        ButterKnife.bind(this);
        setTitle("User List");

        if (ParseUser.getCurrentUser().get("isFollowing") == null) {
            List<String> emptyList = new ArrayList<>();
            ParseUser.getCurrentUser().put("isFollowing", emptyList);
        }

        userList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_checked, users);

        userList.setAdapter(adapter);

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView checkedTextView = (CheckedTextView) view;
                if (checkedTextView.isChecked()) {
                    ParseUser.getCurrentUser().getList("isFollowing").add(users.get(position));
                    ParseUser.getCurrentUser().saveInBackground();
                    Log.i("onItemClick", users.get(position).toString());
                } else {
                    ParseUser.getCurrentUser().getList("isFollowing").remove(users.get(position));
                    ParseUser.getCurrentUser().saveInBackground();
                    Log.i("onItemClick", "Row not checked: " + users.get(position).toString());
                }
            }
        });

        ParseQuery<ParseUser> query = ParseUser.getQuery();

        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (ParseUser user : objects) {
                            users.add(user.getUsername());
                        }

                        adapter.notifyDataSetChanged();

                        for (String username : users) {
                            if (ParseUser.getCurrentUser().getList("isFollowing").contains(username)) {
                                userList.setItemChecked(username.indexOf(username), true);
                            }
                        }
                    }
                }
            }
        });
    }
}
