package wsj.crash.lib.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.util.SparseArray;

import androidx.core.app.NotificationCompat;

import wsj.crash.lib.R;
import wsj.crash.lib.ui.CrashViewerActivity;


public class NotificationUtil {

    private static final String CHANNEL_ID = "crash_canary_notify_id";
    private static final String CHANNEL_NAME = "crash_canary_notify_name";

    private static SparseArray<NotificationCompat.Builder> notificationMap = new SparseArray<>();

    private static NotificationManager notificationManager;


    private static NotificationManager initNotificationManager(Context context) {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    /**
     * 创建进度通知栏
     *
     * @param context
     * @param title
     * @param content
     */
    public static void createNotification(Context context, String title, String content) {
        initNotificationManager(context);

        NotificationCompat.Builder builder = initBaseBuilder(context, title, content, R.mipmap.ic_crash_icon);
        Intent intent = new Intent(context, CrashViewerActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(contentIntent);

        notificationManager.notify(0, builder.build());
    }


    /**
     * 初始化Builder
     *
     * @param context
     * @param title
     * @param content
     * @param icon
     * @return
     */
    private static NotificationCompat.Builder initBaseBuilder(Context context, String title, String content, int icon) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.canBypassDnd();//可否绕过请勿打扰模式
            channel.enableLights(true); // 闪光
            channel.setLightColor(Color.RED);   // 闪光时的灯光颜色
            channel.canShowBadge();         // 桌面launcher显示角标
            channel.enableVibration(true);  // 是否震动
            channel.shouldShowLights();//是否会闪光
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(content)
//                .setSmallIcon(icon)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), icon))
                .setDefaults(Notification.DEFAULT_ALL)
                .setOnlyAlertOnce(true)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setSmallIcon(R.mipmap.ic_crash_icon);
        } else {
            builder.setSmallIcon(icon);
        }
        return builder;
    }
}
