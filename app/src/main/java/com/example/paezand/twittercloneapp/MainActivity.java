package com.example.paezand.twittercloneapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.user_name)
    protected TextView userName;

    @BindView(R.id.password)
    protected TextView password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setTitle("Twitter: Login");
        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        if (ParseUser.getCurrentUser() != null) {
            openUserListActivity();
        }
    }

    @OnClick(R.id.login_button)
    protected void onLoginTapped() {
        ParseUser.logInInBackground(userName.getText().toString(), password.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    Log.i("Info", "Logged in");
                    openUserListActivity();
                } else {
                    ParseUser parseUser = new ParseUser();
                    parseUser.setUsername(userName.getText().toString());
                    parseUser.setPassword(password.getText().toString());

                    parseUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                openUserListActivity();
                            } else {
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void openUserListActivity() {
        Intent intent = new Intent(getBaseContext(), DisplayUsersActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
