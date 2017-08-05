package in.codingninjas.twitterdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowersListActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<FollowersList> followersLists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers_list);
        listView=(ListView) findViewById(R.id.followerListView);
        followersLists=new ArrayList<>();
        ApiInterface apiInterface=ApiClient.getInstance();
        Call<Followers> call=apiInterface.getFollowersList(MainActivity.userName);
        call.enqueue(new Callback<Followers>() {
            @Override
            public void onResponse(Call<Followers> call, Response<Followers> response) {
                Followers followers=response.body();
                followersLists=followers.users;
                FollowersAdapter adapter=new FollowersAdapter(FollowersListActivity.this,followers.users);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Followers> call, Throwable t) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(FollowersListActivity.this,FollowersTweetActivity.class);
                i.putExtra("screen_name",followersLists.get(position).screen_name);
                startActivity(i);
            }
        });
    }
}
