package in.codingninjas.twitterdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.BasicTimelineFilter;
import com.twitter.sdk.android.tweetui.FilterValues;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TimelineFilter;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class TrendsOpenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trends_open);
        Intent i=getIntent();
        String str=i.getStringExtra("Trendsclicked");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        ListView listview = (ListView) findViewById(R.id.trendsListView);
        listview.setNestedScrollingEnabled(true);
        final List<String> handles = Arrays.asList("ericfrohnhoefer", "benward", "vam_si");
        final FilterValues filterValues = new FilterValues(null, null, handles, null); // or load from JSON, XML, etc
        final TimelineFilter timelineFilter = new BasicTimelineFilter(filterValues, Locale.ENGLISH);
        SearchTimeline searchTimeline = new SearchTimeline.Builder()
                .query(str)
                .build();
        CustomAdapter.OnTweetClickListener listener=new CustomAdapter.OnTweetClickListener()
        {
            @Override
            public void onTweetClicked(int position, Tweet tweet) {
                Intent i=new Intent(TrendsOpenActivity.this,TweetView.class);
                i.putExtra("id",tweet.id);
                startActivity(i);
            }
        };
        CustomAdapter adapteruse=new CustomAdapter(TrendsOpenActivity.this,searchTimeline,listener);
        listview.setAdapter(adapteruse);
    }
}
