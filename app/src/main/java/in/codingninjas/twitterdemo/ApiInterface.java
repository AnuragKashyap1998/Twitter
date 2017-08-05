package in.codingninjas.twitterdemo;

import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.Timeline;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by anurag on 28-07-2017.
 */

public interface ApiInterface {
    @GET("statuses/home_timeline.json")
    Call<ArrayList<Tweet>> getUserTimeline();
    //Call<ArrayList<Home>> getUserTimeline(@Header("Authorization") String header);
    @GET("trends/place.json")
    Call<ArrayList<Home>> getUserTrends(@Query("id") String id);
    @GET("users/show.json")
    Call<Profile> getUserProfile(@Query("screen_name") String screen_name);
    @GET("followers/list.json")
    Call<Followers> getFollowersList(@Query("screen_name") String screen_name);
}
//user_id=2956103107