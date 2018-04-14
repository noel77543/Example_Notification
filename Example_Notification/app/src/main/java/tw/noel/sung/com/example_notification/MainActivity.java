package tw.noel.sung.com.example_notification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CustomNotification notification = new CustomNotification(this,MainActivity.class,null);
        notification.showMainNotification(R.mipmap.ic_launcher,R.mipmap.ic_launcher_round,"Hello Notification","安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安");

    }
}
