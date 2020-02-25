package com.allever.app.virtual.call.util;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.allever.lib.common.util.log.ILogger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by Mac on 18/3/1.
 */

public class Utils {
    private static final String TAG = "Util";

    public static void wakeUpAndUnlock(Context context) { //屏锁管理器
        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
        //解锁
        kl.disableKeyguard();
        //获取电源管理器对象
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, ILogger.class.getSimpleName());
        //点亮屏幕
        wl.acquire(10*60*1000L);
        //释放
//        wl.release();
    }


//    public static AnimationDrawable getAnimDrawable(int actionId){
//        //action id -> name ->assets dir
//        AnimationDrawable animationDrawable = null;
//        try {
//            String parentDir = "td_action_img/lunge";
//            String[] filePath = MyApplication.getContext().getAssets().list(parentDir);
//            animationDrawable = new AnimationDrawable();
//            for (String path: filePath){
//                if (path.equals("icon.png")){
//                    continue;
//                }
//                Drawable drawable = Drawable.createFromStream(MyApplication.getContext().getAssets().open(parentDir + "/" + path),"");
//                animationDrawable.addFrame(drawable, 1000);
//            }
//            return animationDrawable;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return animationDrawable;
//    }
//
//    public static Intent getShareIntent(Context context){
//        if (context == null){
//            return null;
//        }
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(context.getResources().getString(R.string.share_text_1) + "\n\n");
//        stringBuilder.append(context.getResources().getString(R.string.share_text_2) + "\n\n");
//        stringBuilder.append(context.getResources().getString(R.string.share_text_3) + "\n");
//        stringBuilder.append(context.getResources().getString(R.string.share_text_4));
//        Intent shareIntent = new Intent();
//        shareIntent.setAction(Intent.ACTION_SEND);
//        shareIntent.putExtra(Intent.EXTRA_TEXT, stringBuilder.toString());
//        shareIntent.setType("text/plain");
//        Intent intent = Intent.createChooser(shareIntent, context.getResources().getString(R.string.share_to));
//        return intent;
//    }

    public static void searchFromMarket(Context context,String keyword){
        if (context == null){
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://search?q="+keyword)); //跳转到应用市场，非Google Play市场一般情况也实现了这个接口
        //存在手机里没安装应用市场的情况，跳转会包异常，做一个接收判断
        if (intent.resolveActivity(context.getPackageManager()) != null) { //可以接收
            context.startActivity(intent);
        } else { //没有应用市场，我们通过浏览器跳转到Google Play
            intent.setData(Uri.parse("market://search?q="+keyword));
            //这里存在一个极端情况就是有些用户浏览器也没有，再判断一次
            if (intent.resolveActivity(context.getPackageManager()) != null) { //有浏览器
                context.startActivity(intent);
            } else { //天哪，这还是智能手机吗？
                Toast.makeText(context, "您没安装应用市场", Toast.LENGTH_SHORT).show();
            }
        }
    }

//    public static boolean isChinese() {
//        return GlobalData.config.getLanguage() == Config.LANG_CHINESE;
//    }
//
//
//    public static void setLanguage(Context context){
//        Log.d(TAG, "setLanguage: ");
//        if (context == null){
//            return;
//        }
//        Configuration configuration = context.getResources().getConfiguration();
//        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
//        int id = GlobalData.config.getLanguage();
//        switch (id){
//            case Config.LANG_CHINESE:
//                configuration.locale = Locale.CHINESE;
//                break;
//            case Config.LANG_ENGLISH:
//                configuration.locale = Locale.ENGLISH;
//                break;
//            default:
//                break;
//        }
//        context.getResources().updateConfiguration(configuration,displayMetrics);
//    }

    public static void restartApp(Activity activity, Class<?> homeClass) {
        Log.d(TAG, "restartApp: ");
        if (homeClass == null) {
            return;
        }
        if (activity == null){
            return;
        }
        Intent intent = new Intent(activity, homeClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        // 杀掉进程
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }



    public static void openAppInPlay(Context context, String uristring) {
        openAppInPlay(context, uristring, null);
    }

    public static void openAppInPlay(Context context, String uristring, String appstore) {
        if (context == null) {
            return;
        }
        if (!uristring.startsWith("market") && !uristring.startsWith("http")) {
            uristring = "market://details?id=" + uristring;
        }
        Log.d(TAG, "openAppInPlay: " + uristring + "  " + appstore);
        Uri uri = Uri.parse(uristring);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (!TextUtils.isEmpty(appstore)) {
            if (isThereApp(context, appstore)) {
                intent.setPackage(appstore);
            }
        }

        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "openAppInPlay: " + "open faild " + uristring + "  " + e.toString());
        }
    }

    public static boolean isThereApp(Context context, String pkg) {
        if (context == null){
            return false;
        }
        try {
            if (pkg.startsWith("market")) {
                pkg = pkg.replaceAll(".*id=", "");
            }
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(pkg, PackageManager.GET_META_DATA);
            if (null != packageInfo) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


//    public static DataSet createWeightDataSet(float weight, int year, int month, int day) {
//        // Create a data source
//        DataSource dataSource =
//                new DataSource.Builder()
//                        .setAppPackageName(MyApplication.getContext())
//                        .setDataType(DataType.TYPE_WEIGHT)
//                        .setStreamName(TAG + " - weight")
//                        .setType(DataSource.TYPE_RAW)
//                        .build();
//
//        DataSet dataSet = DataSet.create(dataSource);
//
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar calendar = Calendar.getInstance();
//        Log.d(TAG, "createDataSet: weitht = " + weight);
//        try {
//            Log.d(TAG, "createDataSet: date = " + year + "-" + month + "-" + day + ": " + weight);
//            calendar.setTime(simpleDateFormat.parse(year + "-" + month + "-" + day));
//            long endTime = calendar.getTimeInMillis();
//            calendar.add(Calendar.HOUR_OF_DAY, -1);
//            long startTime = calendar.getTimeInMillis();
//            Log.d(TAG, "createDataSet: startTime = " + simpleDateFormat.format(startTime));
//            Log.d(TAG, "createDataSet: endTime = " + simpleDateFormat.format(endTime));
//            DataPoint dataPoint =
//                    dataSet.createDataPoint().setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS);
//            dataPoint.getValue(Field.FIELD_WEIGHT).setFloat(weight);
//            dataSet.add(dataPoint);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        // [END build_insert_data_request]
//
//        return dataSet;
//    }


    public static boolean isApkAvailable(Context context, String packagename) {
        if (context == null){
            return false;
        }
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(
                    packagename, 0);

        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
        }
        if(packageInfo ==null){
            return false;
        }else{
            return true;
        }
    }
}
