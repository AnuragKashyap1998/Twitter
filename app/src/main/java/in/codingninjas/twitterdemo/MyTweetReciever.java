package in.codingninjas.twitterdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.twitter.sdk.android.tweetcomposer.TweetUploadService;

public class MyTweetReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (TweetUploadService.UPLOAD_SUCCESS.equals(intent.getAction())) {
            // success
            final Long tweetId = intent.getLongExtra(TweetUploadService.EXTRA_TWEET_ID,0);
        } else {
            // failure
            final Intent retryIntent = intent.getParcelableExtra(TweetUploadService.EXTRA_RETRY_INTENT);
        }
    }
}
