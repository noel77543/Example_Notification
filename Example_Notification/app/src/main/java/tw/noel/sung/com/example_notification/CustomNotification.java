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
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * Created by noel on 2018/4/13.
 */

public class CustomNotification extends Notification {

    private final int NOTIFICATION_ID = 9487;
    //一般通知  單行字串 點選後開啟App 之 LaunchActivity
    public final static int NOTIFICATION_TYPE_NORMAL = 77;
    //大字串風格  點選後開啟App 之 LaunchActivity
    public final static int NOTIFICATION_TYPE_BIG_TEXT = 78;
    //客製化view  點選後開啟指定Activity
    public final static int NOTIFICATION_TYPE_CUSTOM = 79;

    @IntDef({NOTIFICATION_TYPE_NORMAL, NOTIFICATION_TYPE_BIG_TEXT, NOTIFICATION_TYPE_CUSTOM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface NotificationType {
    }


    private Class targetClass;
    private Context context;
    // 開啟另一個Activity的Intent
    private Intent intentNotification;
    private PendingIntent pendingIntent;
    private Bundle bundle;
    private int flags;


    private Notification notification;
    private NotificationManager notificationManager;
    private Uri defaultSoundUri;
    private Bitmap bigIcon;


    /***
     * @param targetClass 打算開啟的 activity
     * @param bundle  是否攜帶資訊 可null
     */
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
    public void displayNotificationToLaunchActivity(int smallIconRes, int largeIconRes, String title, String content, @NotificationType int notificationType) {

        pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, intentNotification, flags);
        defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        bigIcon = BitmapFactory.decodeResource(context.getResources(), largeIconRes);
        //獲取通知服務
        notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);


        switch (notificationType) {
            case NOTIFICATION_TYPE_NORMAL:
                buildNormalNotification(smallIconRes, title, content);
                break;
            case NOTIFICATION_TYPE_BIG_TEXT:
                buildBigTextNotification(smallIconRes, title, content);
                break;
            case NOTIFICATION_TYPE_CUSTOM:
                buildCustomNotification(smallIconRes, title, content);
                break;
        }


        // 發送通知
        notificationManager.notify(NOTIFICATION_ID, notification);
    }


    //-----------

    /***
     * 前往 其他指定頁面
     */
    public void displayNotificationToTargetActivity(int smallIconRes, int largeIconRes, String title, String content, @NotificationType int notificationType) {

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(targetClass);
        stackBuilder.addNextIntent(intentNotification);
        pendingIntent = stackBuilder.getPendingIntent(NOTIFICATION_ID, flags);
        defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        bigIcon = BitmapFactory.decodeResource(context.getResources(), largeIconRes);
        //獲取通知服務
        notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);


        switch (notificationType) {
            case NOTIFICATION_TYPE_NORMAL:
                buildNormalNotification(smallIconRes, title, content);
                break;
            case NOTIFICATION_TYPE_BIG_TEXT:
                buildBigTextNotification(smallIconRes, title, content);
                break;
            case NOTIFICATION_TYPE_CUSTOM:
                buildCustomNotification(smallIconRes, title, content);
                break;
        }

        // 發送通知
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    //-----

    /***
     * 大字串 可下拉延伸 notification
     */
    private void buildBigTextNotification(int smallIconRes, String title, String content) {
        // 建立通知
        notification = new NotificationCompat.Builder(context)
                //狀態欄的icon
                .setSmallIcon(smallIconRes)
                //通知欄的大icon
                .setLargeIcon(bigIcon)
                //使可以向下彈出
                .setPriority(Notification.PRIORITY_MAX)
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
    }


    //-----

    /***
     * 一般 notification
     */
    private void buildNormalNotification(int smallIconRes, String title, String content) {
        // 建立通知
        notification = new NotificationCompat.Builder(context)
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
    }

    //-----

    /***
     *  客製化view 通知
     * @param smallIconRes
     * @param title
     * @param content
     */
    private void buildCustomNotification(int smallIconRes, String title, String content) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.view_notification);
        remoteViews.setTextViewText(R.id.tv_title, title);
        remoteViews.setTextViewText(R.id.tv_content, content);

        // 建立通知
        notification = new NotificationCompat.Builder(context)
                //狀態欄的icon
                .setSmallIcon(smallIconRes)
                //通知欄的大icon
                .setLargeIcon(bigIcon)
                //使可以向下彈出
                .setPriority(Notification.PRIORITY_HIGH)
                //通知聲音
                .setSound(defaultSoundUri)
                //設置的intent
                .setContentIntent(pendingIntent)
                //點了之後自動消失
                .setAutoCancel(true)
                //指定客製化view
                .setCustomBigContentView(remoteViews)
                .build();

     /***
         *ps 如果RemoteView僅單行大小使用setContentView
         * 較大型的則使用setCustomBigContentView
         */

        //使無法被滑除
        notification.flags = Notification.FLAG_ONGOING_EVENT;
    }

}
