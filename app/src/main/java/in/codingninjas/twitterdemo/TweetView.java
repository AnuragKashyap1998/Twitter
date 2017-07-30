package in.codingninjas.twitterdemo;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetUtils;

public class TweetView extends AppCompatActivity {

    private LinearLayout tweetView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_view2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        long id = getIntent().getLongExtra("id",-1);

        tweetView = (LinearLayout) findViewById(R.id.mylinearlayout);

        TweetUtils.loadTweet(id, new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {
                com.twitter.sdk.android.tweetui.TweetView tv = new com.twitter.sdk.android.tweetui.TweetView(TweetView.this, result.data);
                tweetView.addView(tv);
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
