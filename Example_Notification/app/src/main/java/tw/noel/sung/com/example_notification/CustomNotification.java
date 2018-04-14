package tw.noel.sung.com.example_notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * Created by noel on 2018/4/13.
 */

public class CustomNotification extends Notification {

    private Class targetClass;
    private Context context;
    private final int NOTIFICATION_ID = 9487;
    // 開啟另一個Activity的Intent
    private Intent intentNotification;
    private PendingIntent pendingIntent;
    private Bundle bundle;
    private int flags;

    public CustomNotification(Context context, Class targetClass, @Nullable Bundle bundle) {
        this.targetClass = targetClass;
        this.context = context;
        this.bundle = bundle;
        init();
    }


    //--------

    /***
     * init..
     */
    private void init() {
        intentNotification = new Intent(context.getApplicationContext(), targetClass);

        if (bundle != null) {
            intentNotification.putExtras(bundle);
        }
        intentNotification.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intentNotification.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        flags = PendingIntent.FLAG_CANCEL_CURRENT;


    }
    //-----------

    /***
     * 前往 主頁面
     */
    public void showMainNotification(int smallIconRes, int largeIconRes, String title, String content) {
        pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, intentNotification, flags);
        sendBigNotification(smallIconRes, largeIconRes, title, content);
    }


    //-----------

    /***
     * 前往 其他指定頁面
     */
    public void showGoAnotherNotification(int smallIconRes, int largeIconRes, String title, String content) {
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(targetClass);
        stackBuilder.addNextIntent(intentNotification);
        pendingIntent = stackBuilder.getPendingIntent(NOTIFICATION_ID, flags);
        sendNotification(smallIconRes, largeIconRes, title, content);
    }

    //-----

    /***
     * 大字串 可下拉延伸 notification
     */
    private void sendBigNotification(int smallIconRes, int largeIconRes, String title, String content) {

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap bigIcon = BitmapFactory.decodeResource(context.getResources(), largeIconRes);

        // 建立通知
        Notification notification = new NotificationCompat.Builder(context)
                //狀態欄的icon
                .setSmallIcon(smallIconRes)
                //通知欄的大icon
                .setLargeIcon(bigIcon)
                //使可以向下彈出
                .setPriority(Notification.PRIORITY_HIGH)
                //一般狀態title
                .setContentTitle(title)
                //通知聲音
                .setSound(defaultSoundUri)
                //一般狀態內容
                .setContentText(content)
                //設置的intent
                .setContentIntent(pendingIntent)
                //點了之後自動消失
                .setAutoCancel(true)
                //設置風格 - 大字串
                .setStyle(new NotificationCompat.BigTextStyle()
                        //下拉時顯示的字串
                        .bigText(content)
                        //下拉時顯示的title
                        .setBigContentTitle(title)
                )
                .build();

        //獲取通知服務
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        // 發送通知
        notificationManager.notify(NOTIFICATION_ID, notification);

    }


    //-----

    /***
     * 一般 notification
     */
    private void sendNotification(int smallIconRes, int largeIconRes, String title, String content) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap bigIcon = BitmapFactory.decodeResource(context.getResources(), largeIconRes);

        // 建立通知
        Notification notification = new Builder(context)
                //狀態欄的icon
                .setSmallIcon(smallIconRes)
                //通知欄的大icon
                .setLargeIcon(bigIcon)
                //使可以向下彈出
                .setPriority(Notification.PRIORITY_HIGH)
                //一般狀態title
                .setContentTitle(title)
                //通知聲音
                .setSound(defaultSoundUri)
                //一般狀態內容
                .setContentText(content)
                //設置的intent
                .setContentIntent(pendingIntent)
                //點了之後自動消失
                .setAutoCancel(true)
                .build();

        //獲取通知服務
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        // 發送通知
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}
