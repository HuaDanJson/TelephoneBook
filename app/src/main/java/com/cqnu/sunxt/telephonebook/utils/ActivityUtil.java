package com.cqnu.sunxt.telephonebook.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

public class ActivityUtil {

    public static boolean isFinishing(Activity activity) {
        return (activity == null || activity.isFinishing());
    }

    public static void startActivity(Activity activity, Class targetClass) {
        Intent intent = new Intent(activity, targetClass);
        activity.startActivity(intent);
    }

    public static void runOnUiThread(Runnable runnable) {
        Looper mainLooper = Looper.getMainLooper();
        new Handler(mainLooper).post(runnable);
    }

    public static void sendSMSMessage(String message, Context mContext, String number) {
        Uri smsToUri = Uri.parse("smsto:");
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.setData(Uri.parse("smsto:" + number));
        //to do  add Monkey Download url
        intent.putExtra("sms_body", message);
        if (mContext != null) {
            mContext.startActivity(intent);
        }
    }

}
