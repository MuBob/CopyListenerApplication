package com.mu.bob.generate.copylistenerapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class MainService extends Service {

    private static final String TAG = "MainServiceTAG";
    private ClipboardManager clipboardManager;
    private NotificationManager notificationManager;
    private ClipboardManager.OnPrimaryClipChangedListener clipboarListener;
    public MainService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sendServiceNotify();
        return super.onStartCommand(intent, flags, startId);
    }

    public void sendServiceNotify(){
       //实例化NotificationCompat.Builde并设置相关属性
        Intent deleteIntent=new Intent(this, MainService.class);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                //设置小图标
                .setSmallIcon(R.mipmap.ic_launcher)
                //设置通知标题
                .setContentTitle("复制监听服务")
                //设置通知内容
                .setContentText("正在监听中")
                .setAutoCancel(false)
                .setOngoing(true)
                .setDeleteIntent(PendingIntent.getService(this, 1, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                //设置通知时间，默认为系统发出通知的时间，通常不用设置
                .setWhen(System.currentTimeMillis());
        //通过builder.build()方法生成Notification对象,并发送通知,id=1
        notificationManager.notify(1, builder.build());
    }

    public void sendCopyNotify(CharSequence copyText){
        Intent copyIntent=new Intent(CopyBroadcastReceiver.ACTION);
        copyIntent.putExtra("copyText",copyText);
        //实例化NotificationCompat.Builde并设置相关属性
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("复制内容")
                .setContentText(copyText)
                .setContentIntent(PendingIntent.getBroadcast(this, 102, copyIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .setWhen(System.currentTimeMillis());
        //通过builder.build()方法生成Notification对象,并发送通知,id=1
        notificationManager.notify(2, builder.build());
    }
    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipboarListener=new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                if (clipboardManager.hasPrimaryClip()) {
                    for (int i = 0; i < clipboardManager.getPrimaryClip().getItemCount(); i++) {
                        CharSequence addedText = clipboardManager.getPrimaryClip().getItemAt(i).getText();
                        if (addedText != null) {
                            Log.i(TAG, "copied index:"+i+", text:" + addedText);
                            sendCopyNotify(addedText);
                        }
                    }
                }
            }
        };
        registerClipEvents();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterClipEvents();
    }

    private void registerClipEvents() {
        clipboardManager.addPrimaryClipChangedListener(clipboarListener);
    }

    private void unregisterClipEvents(){
        clipboardManager.removePrimaryClipChangedListener(clipboarListener);
    }

}
