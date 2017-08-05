package in.codingninjas.twitterdemo;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by anurag on 30-07-2017.
 */

public class FollowersAdapter extends ArrayAdapter<FollowersList> {
     ArrayList<FollowersList> followersLists;
    Context context;
    public FollowersAdapter(@NonNull Context context,ArrayList<FollowersList> followersLists) {
        super(context,R.layout.followersviewlayout);
        this.context=context;
        this.followersLists=followersLists;
    }

    @Override
    public int getCount() {
        return followersLists.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v=LayoutInflater.from(context).inflate(R.layout.followersviewlayout,null);
        ImageView followersImageView=(ImageView) v.findViewById(R.id.followersImageView);
        TextView followersTextView=(TextView) v.findViewById(R.id.followersEditText);
        FollowersList follows=followersLists.get(position);
        Picasso.with(context)
                .load(follows.profile_image_url)
                .into(followersImageView);
        followersTextView.setText(follows.name);
        return v;
    }
}
