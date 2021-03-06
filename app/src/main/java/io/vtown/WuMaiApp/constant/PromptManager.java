package io.vtown.WuMaiApp.constant;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Iterator;

import io.vtown.WuMaiApp.R;
import io.vtown.WuMaiApp.Utilss.DimensionPixelUtil;
import io.vtown.WuMaiApp.Utilss.StrUtils;
import io.vtown.WuMaiApp.view.dialog.CustomDialog;
import io.vtown.WuMaiApp.view.load.ShapeLoadingDialog;

/**
 * 提示信息的管理
 */

public class PromptManager {
    private static Dialog dialog;
    private static Toast mToast;
    private static Toast CustomToast;
    private static CustomDialog wdialog;
    private static Dialog textdialog;
    private static Dialog textdialog3;
    private static ShapeLoadingDialog shapeLoadingDialog;
    // private static DialogInterfaace myDialogInterfaace;

    /**
     * 显示正常的toast
     *
     * @param context
     * @param text
     */
    public static void ShowMyToast(Context context, String text) {
        // if (mToast == null) {
        // mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        // } else {
        // mToast.setText(text);
        // mToast.setDuration(Toast.LENGTH_SHORT);
        // }
        // mToast.show();
        ShowCustomToast(context, text);
    }

    /**
     * 显示自定义的toast
     */
    public static void ShowCustomToast(Context context, String text) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View CustomView = inflater.inflate(R.layout.toast_customtoast, null);
        TextView ToastText = (TextView) CustomView.findViewById(R.id.toast_id);
        ToastText.setText(StrUtils.NullToStr(text));
        if (CustomToast == null) {
            CustomToast = new Toast(context);
        }
        CustomToast.setGravity(Gravity.CENTER, 0,
                DimensionPixelUtil.dip2px(context, 70));
        CustomToast.setDuration(Toast.LENGTH_SHORT);
        CustomToast.setView(CustomView);
        CustomToast.show();

    }

    public static void ShowCustomUpToast(Context context, String text) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View CustomView = inflater.inflate(R.layout.toast_customtoast, null);
        TextView ToastText = (TextView) CustomView.findViewById(R.id.toast_id);
        ToastText.setText(StrUtils.NullToStr(text));
        if (CustomToast == null) {
            CustomToast = new Toast(context);
        }
        CustomToast.setGravity(Gravity.TOP, 0,
                DimensionPixelUtil.dip2px(context, 120));
        CustomToast.setDuration(Toast.LENGTH_SHORT);
        CustomToast.setView(CustomView);
        CustomToast.show();

    }

    /**
     * 显示自定义的toast
     */
    public static void ShowCustomToastLong(Context context, String text) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View CustomView = inflater.inflate(R.layout.toast_customtoast, null);
        TextView ToastText = (TextView) CustomView.findViewById(R.id.toast_id);
        ToastText.setText(StrUtils.NullToStr(text));
        if (CustomToast == null) {
            CustomToast = new Toast(context);
        }
        CustomToast.setGravity(Gravity.CENTER, 0,
                DimensionPixelUtil.dip2px(context, 70));
        CustomToast.setDuration(Toast.LENGTH_LONG);
        CustomToast.setView(CustomView);
        CustomToast.show();

    }

    /**
     * 显示自定义的toast
     */
    public static void ShowCustomToast1(Context context, String text) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View CustomView = inflater.inflate(R.layout.toast_customtoast1, null);
        TextView ToastText = (TextView) CustomView.findViewById(R.id.toast_id1);
        ToastText.setText(StrUtils.NullToStr(text));
        if (CustomToast == null) {
            CustomToast = new Toast(context);
        }
        CustomToast.setGravity(Gravity.TOP, 0,
                DimensionPixelUtil.dip2px(context, 60));
        CustomToast.setDuration(Toast.LENGTH_SHORT);
        CustomToast.setView(CustomView);
        CustomToast.show();

    }

    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    public static void showLoading(Context context) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }

        if (null != context) {
            View layout = View.inflate(context, R.layout.dialog_loading, null);
            dialog = new Dialog(context, R.style.loading);
            dialog.setCancelable(true);
            dialog.setContentView(layout);
            dialog.show();

        }
    }

    public static void showLoading1(Context context) {

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }

        if (null != context) {
            View layout = View.inflate(context, R.layout.dialog_loading, null);
            dialog = new Dialog(context, R.style.loading);
            dialog.setCancelable(false);
            textdialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(layout);
            dialog.show();

        }
    }

    public static void closeLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public static void closeTextLoading() {
        if (textdialog != null && textdialog.isShowing()) {
            textdialog.dismiss();
        }
    }

    public static void makeCall(Context context, String phoneNum) {

        if (!TextUtils.isEmpty(phoneNum)) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
                    + phoneNum));
            context.startActivity(intent);
        }

    }

//	/**
//	 * 显示文本标示加载中
//	 *
//	 * @param context
//	 */
//	public static void showtextLoading(Context context, String content2) {
//		LinearLayout sd;
//		if (textdialog != null && textdialog.isShowing()) {
//			textdialog.dismiss();
//			textdialog = null;
//		}
//
//		if (null != context) {
//			View layout = View.inflate(context, R.layout.dialog_loading1, null);
//			// ((TextView) layout.findViewById(R.id.dialog_content))
//			// .setText(content);
//			textdialog = new Dialog(context, R.style.myloading);
//			ProgressBar loadingbar = (ProgressBar) layout
//					.findViewById(R.id.loadingbar);
//			TextView text = (TextView) layout.findViewById(R.id.jiazai);
//			sd = (LinearLayout) layout.findViewById(R.id.sd);
//			text.setText(content2);
//			LinearLayout.LayoutParams ps = new LinearLayout.LayoutParams(180,
//					100);
//			loadingbar.setLayoutParams(ps);
//			textdialog.setCanceledOnTouchOutside(true);
//
//			textdialog.setCancelable(true);
//			textdialog.setContentView(layout);
//
//			textdialog.show();
//			WindowManager.LayoutParams params = textdialog.getWindow()
//					.getAttributes();
//			// params.height = -1;
//			// params.width = -1;
//			// params.format = 1;
//			// params.flags = WindowManager.LayoutParams.FLAG_FULLSCREEN
//			// | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
//			// textdialog.getWindow().setAttributes(params);
//			textdialog.setCanceledOnTouchOutside(true);
//
//			textdialog.setCancelable(true);
//
//			sd.setOnTouchListener(new OnTouchListener() {
//				@Override
//				public boolean onTouch(View v, MotionEvent event) {
//					if (textdialog != null && textdialog.isShowing()) {
//						textdialog.dismiss();
//						textdialog = null;
//						// new java.util.Timer().schedule(new TimerTask() {
//						//
//						// @Override
//						// public void run() {
//						// StopTextDialog();
//						// this.cancel();
//						// }
//						// }, 3 * 1000);
//
//					}
//					return true;
//				}
//
//			});
//		}
//	}

    /**
     * 显示文本标示加载中
     *
     * @param context
     */
    public static void showtextLoading(Context context, String content2) {

        if (null != shapeLoadingDialog && shapeLoadingDialog.IsShow()) {
            shapeLoadingDialog.dismiss();
            shapeLoadingDialog = null;
        }
        shapeLoadingDialog = new ShapeLoadingDialog(context);
        shapeLoadingDialog.setLoadingText(content2);
        shapeLoadingDialog.show();

    }

    public static void closeTextLoading3() {
        if (textdialog3 != null && textdialog3.isShowing()) {
            textdialog3.dismiss();
            textdialog3 = null;
        }
    }

    /**
     * 关闭文本标示
     */
    public static void closetextLoading() {
//        if (textdialog != null && textdialog.isShowing()) {
//            textdialog.dismiss();
//        }
        if (shapeLoadingDialog != null && shapeLoadingDialog.IsShow()) {
            shapeLoadingDialog.dismiss();
        }


    }

    /**
     * 显示文本标示加载中
     *
     * @param context
     */
    public static void showtextLoading3(Context context, String content2) {
        LinearLayout sd;
        if (textdialog3 != null && textdialog3.isShowing()) {
            textdialog3.dismiss();
            textdialog3 = null;
        }

        if (null != context) {
            View layout = View.inflate(context, R.layout.dialog_loading1, null);
            // ((TextView) layout.findViewById(R.id.dialog_content))
            // .setText(content);
            textdialog3 = new Dialog(context, R.style.myloading3);
            ProgressBar loadingbar = (ProgressBar) layout
                    .findViewById(R.id.loadingbar);
            TextView text = (TextView) layout.findViewById(R.id.jiazai);
            sd = (LinearLayout) layout.findViewById(R.id.sd);
            text.setText(content2);
            LinearLayout.LayoutParams ps = new LinearLayout.LayoutParams(50,
                    50);
//            loadingbar.setLayoutParams(ps);
            textdialog3.setCanceledOnTouchOutside(false);

            textdialog3.setCancelable(false);
            textdialog3.setContentView(layout);

            textdialog3.show();

            // WindowManager.LayoutParams params = textdialog.getWindow()
            // .getAttributes();
            // params.height = -1;
            // params.width = -1;
            // params.format = 1;
            // params.flags = WindowManager.LayoutParams.FLAG_FULLSCREEN
            // | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
            // textdialog.getWindow().setAttributes(params);
            // textdialog.setCanceledOnTouchOutside(false);
            //
            // textdialog.setCancelable(false);

        }
    }

    static void StopTextDialog() {
        Message message = new Message();
        message.what = 101;
        textdialogHandler.sendMessage(message);
    }

    static Handler textdialogHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 101) {
                textdialog.dismiss();
                textdialog = null;
            }

        }
    };


    /**
     * 当判断当前手机没有网络时使用
     *
     * @param context
     */
    public static void showNoNetWork(final Context context) {
        AlertDialog.Builder builder = new Builder(context);
        builder.setIcon(R.mipmap.ic_launcher)
                //
                .setTitle(R.string.app_name)
                //
                .setMessage("当前无网络")
                .setPositiveButton("设置", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到系统的网络设置界面
                        Intent intent = new Intent();
                        intent.setClassName("com.android.settings",
                                "com.android.settings.WirelessSettings");
                        context.startActivity(intent);

                    }
                }).setNegativeButton("知道了", null).show();
    }

    /**
     * 页面跳转
     *
     * @param activity
     * @param intent
     */
    public static void SkipActivity(Activity activity, Intent intent) {
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);
    }

    public static void SkipActivityUpToTpo(Activity activity, Intent intent) {
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.push_bttomin,
                R.anim.push_topout);
    }

    /**
     * 页面跳转
     *
     * @param activity
     * @param intent
     */
    public static void SkipActivity1(Activity activity, Intent intent) {
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);
    }

    /**
     * 页面跳转 result的跳转
     */
    public static void SkipResultActivity(Activity activity, Intent intent,
                                          int Code) {
        activity.startActivityForResult(intent, Code);
        activity.overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);
    }

    /**
     * 跳转到网络设置
     *
     * @param mycContext
     */
    public static void GoToNetSeting(Context mycContext) {
        if (android.os.Build.VERSION.SDK_INT > 10) {
            // 3.0以上打开设置界面，也可以直接用ACTION_WIRELESS_SETTINGS打开到wifi界面
            mycContext.startActivity(new Intent(
                    android.provider.Settings.ACTION_SETTINGS));
        } else {
            mycContext.startActivity(new Intent(
                    android.provider.Settings.ACTION_WIRELESS_SETTINGS));
        }
    }



    /**
     * startactivity的入参操作
     */
    private void StartIntentExtra(Intent intent, Activity activity,
                                  HashMap<String, String> map) {
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
            entry.getKey();// //返回与此项对应的键
            entry.getValue();// 返回与此项对应的值
            intent.putExtra((String) entry.getKey(), (String) entry.getValue());
        }
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.push_rigth_in,
                R.anim.push_rigth_out);
    }



    private void Notifaction(Context context, String tickerText, String title,
                             String ContentTxt) {
        NotificationManager manager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        // 创建builder api16后兼容的api
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                context);
        // PendingIntent pendingIntent=PendingIntent.getActivities(context,
        // requestCode, intents, flags);
        // 赋值
        mBuilder.setContentTitle(title).setContentText(ContentTxt)
                .setTicker(tickerText).setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(null);

    }

//    public static void showNotify(Context context) {
//
//        NotificationManager notificationManager = (NotificationManager) context
//                .getSystemService(Context.NOTIFICATION_SERVICE);
//        Notification.Builder builder = new Notification.Builder(context);
//        Intent intent = new Intent(context, AChatInf.class); // 需要跳转指定的页面
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
//                intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(pendingIntent);
//        builder.setSmallIcon(R.mipmap.ic_launcher);// 设置图标
//        builder.setContentTitle("标题");// 设置通知的标题
//        builder.setContentText("内容");// 设置通知的内容
//        builder.setWhen(System.currentTimeMillis());// 设置通知来到的时间
//        builder.setAutoCancel(true); // 自己维护通知的消失
//        builder.setTicker("new message");// 第一次提示消失的时候显示在通知栏上的
//        builder.setOngoing(true);
//        builder.setNumber(20);
//
//        Notification notification = builder.build();
//        notification.flags = Notification.FLAG_NO_CLEAR; // 只有全部清除时，Notification才会清除
//        notificationManager.notify(0, notification);
//    }

}
