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
import android.util.Log;
import android.util.SparseArray;

import androidx.core.app.NotificationCompat;

import wsj.crash.lib.R;
import wsj.crash.lib.ui.CrashInfoActivity;
import wsj.crash.lib.ui.CrashViewerActivity;


public class NotificationUtil {

    private static final String CHANNEL_ID = "crash_canary_notify_id";
    private static final String CHANNEL_NAME = "crash_canary_notify_name";

    private static SparseArray<NotificationCompat.Builder> notificationMap = new SparseArray<>();

    private static NotificationManager notificationManager;


    private static NotificationManager initNotificationManager(Context context) {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                channel.canBypassDnd();//可否绕过请勿打扰模式
                channel.setLightColor(Color.RED);   // 闪光时的灯光颜色
                channel.canShowBadge();         // 桌面launcher显示角标
                channel.shouldShowLights();//是否会闪光
                channel.enableLights(true); // 闪光
                channel.enableVibration(true);  // 是否震动
                notificationManager.createNotificationChannel(channel);
            }
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

        NotificationCompat.Builder builder = initBaseBuilder(context, title, content, false);
        Intent intent = new Intent(context, CrashViewerActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        notificationManager.notify(0, builder.build());
    }

    public static void createNotification(Context context, String title, String content, long itemId) {
        initNotificationManager(context);

        NotificationCompat.Builder builder = initBaseBuilder(context, title, content, false);
        Intent intent = new Intent(context, CrashInfoActivity.class);
        if (itemId != -1) {
            intent.putExtra("id", itemId);
            //解决PendingIntent的extra数据不准确问题
            intent.setAction(Long.toString(System.currentTimeMillis()));
        }
        // 需要传递参数需设置为：PendingIntent.FLAG_UPDATE_CURRENT
        PendingIntent contentIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        notificationManager.notify(0, builder.build());
    }

    public static Notification createNotification(Context context) {
        initNotificationManager(context);

        NotificationCompat.Builder builder = initBaseBuilder(context, "CrashCanary", "正在检测程序运行异常", true);
        Intent intent = new Intent(context, CrashViewerActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        return builder.build();
    }

    /**
     * 初始化Builder
     *
     * @param context
     * @param title
     * @param content
     * @return
     */
    private static NotificationCompat.Builder initBaseBuilder(Context context, String title, String content, boolean slient) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.mipmap.small_icon)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_crash_icon))
                .setOnlyAlertOnce(true)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis());
        if (!slient) {
            builder.setDefaults(Notification.DEFAULT_ALL);
        }
        return builder;
    }
}
