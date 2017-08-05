package in.codingninjas.twitterdemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.OAuthSigning;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import com.twitter.sdk.android.tweetui.BasicTimelineFilter;
import com.twitter.sdk.android.tweetui.CollectionTimeline;
import com.twitter.sdk.android.tweetui.FilterValues;
import com.twitter.sdk.android.tweetui.FixedTweetTimeline;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TimelineFilter;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.TwitterListTimeline;
import com.twitter.sdk.android.tweetui.UserTimeline;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.id.empty;
import static in.codingninjas.twitterdemo.R.id.container;

public class Tabbed extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private  TabLayout tabLayout;
    private ViewPager mViewPager;
    static CoordinatorLayout coordinatorLayout;
    static long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawerlayout);
        coordinatorLayout=(CoordinatorLayout) findViewById(R.id.main_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i=getIntent();
         id=i.getLongExtra("ID",-1);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final TwitterSession session = TwitterCore.getInstance().getSessionManager()
                        .getActiveSession();
                final Intent intent = new ComposerActivity.Builder(Tabbed.this)
                        .session(session)
                        .image(Uri.parse("https://www.google.co.in/search?q=hd+wallpaper&tbm=isch&imgil=mQHsIJxCQG6oDM%253A%253BG8XXjjbUAYL6HM%253Bhttps%25253A%25252F%25252Fwww.planwallpaper.com%25252Fwallpaper-hd&source=iu&pf=m&fir=mQHsIJxCQG6oDM%253A%252CG8XXjjbUAYL6HM%252C_&usg=__SnfWR2Mf8TMgg6VVCQa-MZSb6WE%3D&biw=1366&bih=662&ved=0ahUKEwiOwOjjmarVAhWMmZQKHd-fBPUQyjcIQA&ei=kT96Wc6zH4yz0gTfv5KoDw#imgrc=9CPmb1CtMCBzwM:"))
                        .text("Love where you work")
                        .hashtags("#twitter")
                        .createIntent();
                startActivity(intent);
            }
        });
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);//additional line to setup with tab and view of fragment



    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tabbed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.followers) {
            Intent i=new Intent(Tabbed.this,FollowersListActivity.class);
            startActivity(i);

        } else if (id == R.id.profile) {
            Intent i=new Intent(Tabbed.this,UserProfile.class);
            startActivity(i);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        String screenName;
        ListView listview;
        static String use;
        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Bundle b=getArguments();
            int selectionNumber=b.getInt(ARG_SECTION_NUMBER);
            if(selectionNumber==1) {
                View rootView = inflater.inflate(R.layout.fragment_tabbed, container, false);
                listview = (ListView) rootView.findViewById(R.id.list);

//                OAuthSigning autthSigning = new OAuthSigning();
//                HashMap<String , String> params = new HashMap<>();
//                params.put("count", 50+"");
//                String header = autthSigning.getAuthorizationHeader("GET", "https://api.twitter.com/1.1/statuses/home_timeline.json",params );


                final TwitterSession session = TwitterCore.getInstance().getSessionManager()
                        .getActiveSession();
                final UserTimeline userTimeline = new UserTimeline.Builder()
                        .screenName(session.getUserName())
                        .build();
                CustomAdapter.OnTweetClickListener listener=new CustomAdapter.OnTweetClickListener()
                {
                    @Override
                    public void onTweetClicked(int position, Tweet tweet) {
                        Intent i=new Intent(getContext(),TweetView.class);
                        i.putExtra("id",tweet.id);
                        startActivity(i);
                    }
                };
                CustomAdapter adapteruse=new CustomAdapter(getContext(),userTimeline,listener);

                listview.setAdapter(adapteruse);

                return rootView;

            }
            else if (selectionNumber == 2) {
                View rootView = inflater.inflate(R.layout.fragment_tabbed, container, false);

                listview = (ListView) rootView.findViewById(R.id.list);
                final List<String> handles = Arrays.asList("ericfrohnhoefer", "benward", "vam_si");
                final FilterValues filterValues = new FilterValues(null, null, handles, null); // or load from JSON, XML, etc
                final TimelineFilter timelineFilter = new BasicTimelineFilter(filterValues, Locale.ENGLISH);
                SearchTimeline searchTimeline = new SearchTimeline.Builder()
                        .query("TwitterDev")
                        .build();
                final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(getContext())
                        .setTimeline(searchTimeline)
                        .setTimelineFilter(timelineFilter)
                        .build();
                listview.setAdapter(adapter);
                return rootView;
            } else if (selectionNumber == 3) {
                View rootView = inflater.inflate(R.layout.fragment_tabbed, container, false);
                listview = (ListView) rootView.findViewById(R.id.list);
                ApiInterface apiInterface =  ApiClient.getInstance();
                Call<ArrayList<Tweet>> call  =  apiInterface.getUserTimeline();
                final ArrayList<Tweet> use = new ArrayList<>();
                call.enqueue(new Callback<ArrayList<Tweet>>() {
                    @Override
                    public void success(Result<ArrayList<Tweet>> result) {
                        FixedTweetTimeline homeTimeline = new FixedTweetTimeline.Builder()
                        .setTweets(result.data)
                        .build();
                        for(int i=0;i<result.data.size();i++)
                        {
                            use.add(result.data.get(i));
                        }
                        CustomAdapter.OnTweetClickListener listener=new CustomAdapter.OnTweetClickListener()
                        {
                            @Override
                            public void onTweetClicked(int position, Tweet tweet) {
                                Intent i=new Intent(getContext(),TweetView.class);
                                i.putExtra("id",tweet.id);
                                startActivity(i);
                            }
                        };
                        CustomAdapter adapteruse=new CustomAdapter(getContext(),homeTimeline,listener);
                        listview.setAdapter(adapteruse);
                    }

                    @Override
                    public void failure(TwitterException exception) {

                    }
                });
                return rootView;

            } else if (selectionNumber == 4) {
                View rootView = inflater.inflate(R.layout.fragment_tabbed, container, false);
                final SearchView searchView=(SearchView) coordinatorLayout.findViewById(R.id.searchView);
                searchView.setVisibility(View.VISIBLE);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        Intent i=new Intent(getContext(),TrendsOpenActivity.class);
                        i.putExtra("Trendsclicked",query);
                        startActivity(i);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return true;
                    }
                });
                listview = (ListView) rootView.findViewById(R.id.list);
                ApiInterface apiInterface =  ApiClient.getInstance();
                Call<ArrayList<Home>> call  =  apiInterface.getUserTrends("1");
                call.enqueue(new Callback<ArrayList<Home>>() {
                    @Override
                    public void success(Result<ArrayList<Home>> result) {
                        Home home =result.data.get(0);
                        ArrayList<Trends> trendslist=home.trends;
                        Log.i("mysize","trends"+trendslist.size());
                        ArrayList<String> list=new ArrayList<>();
                        for(int i=0;i<trendslist.size();i++)
                        {
                            list.add(trendslist.get(i).name);
                            Log.i("mysize",list.get(i));
                        }

                        ArrayAdapter<String> adapter=new ArrayAdapter<>(getContext().getApplicationContext(),R.layout.usedfortrends,list);
                        listview.setAdapter(adapter);
                    }

                    @Override
                    public void failure(TwitterException exception) {

                    }
                });
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i=new Intent(getContext(),TrendsOpenActivity.class);
                        TextView textView=(TextView) view.findViewById(R.id.textView2);
                        i.putExtra("Trendsclicked",textView.getText().toString());
                        startActivity(i);
                    }
                });
                return rootView;
            }
            return null;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "My Tweets";
                case 1:
                    return "SEARCH";
                case 2:
                    return "RECENT";
                case 3:
                    return "TRENDS";
            }
            return null;
        }
    }
}
