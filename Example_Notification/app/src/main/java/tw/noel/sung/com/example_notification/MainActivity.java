package tw.noel.sung.com.example_notification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //開啟Launch Activity所用
//        CustomNotification notification = new CustomNotification(this, MainActivity.class, null);
//        notification.displayNotificationToLaunchActivity(R.mipmap.ic_launcher, R.mipmap.ic_launcher_round, getString(R.string.notification_title), getString(R.string.notification_content), CustomNotification.NOTIFICATION_TYPE_BIG_TEXT);

        //開啟指定 Activity所用
        CustomNotification notification = new CustomNotification(this, NextActivity.class, null);
        notification.displayNotificationToLaunchActivity(R.mipmap.ic_launcher, R.mipmap.ic_launcher_round, getString(R.string.notification_title), getString(R.string.notification_content), CustomNotification.NOTIFICATION_TYPE_CUSTOM);
    }
}
