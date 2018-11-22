package com.it.yangkun.commonsdk.service;

import android.app.Application;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.blankj.utilcode.util.ResourceUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;

import org.litepal.LitePal;

import java.io.File;

/**
 * Created by yangk on 2017/7/18.
 * 项目一些初始化
 *
 */

public class InitializeService extends IntentService {
    private static final String ACTION_INIT = "initApplication";
    private static Context context;
    private String assertFolderName = "h";
    private String IFLYTEK = "iflytek";
    public static File externalFilesDir;
    private String BAIDULOCATION = "/BaiduMapSDKNew/vmp";
    private String iflayLOCATION = "/XUNFEI";
    private String WPS_ASSETS = "wps";
    private String WPS = "/勤务调度文件查看软件";
    public static File filePa;
    public static String wpsPath;

    public InitializeService() {
        super("InitializeService");
    }

    public static void start(Context context) {
        InitializeService.context = context;
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_INIT);
        context.startService(intent);
    }

    public static void stop() {
        if (context == null) return;
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_INIT);
        context.stopService(intent);
        context = null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT.equals(action)) {
                initApplication();
            }
        }
    }

    /**
     * 初始化
     */
    private void initApplication() {

        initLitePal();

//        initLog();

        initOKGOHttp();

        initBugly();

        initGQT();

        initKeDaXunFei();

        copyFile();

        initchangeLogin();
    }

    private void initchangeLogin() {
    }

    private void initLitePal() {
        LitePal.initialize(this);
    }


    /**
     * 初始化 Log
     */
    private void initLog() {
    }

    /**
     * 初始化 Http
     */
    public void initOKGOHttp() {
        // 其他统一的配置
        // 详细说明看GitHub文档：https://github.com/jeasonlzy/
        // 其他统一的配置
        // 详细说明看GitHub文档：https://github.com/jeasonlzy/
        OkGo.getInstance().init((Application) context)                           //必须调用初始化
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3)                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
        ;                       //全局公共参数
    }

    /**
     * 初始化崩溃日志搜集
     */
    private void initBugly() {
    }

    /**
     * 初始化 GQT
     */
    private void initGQT() {

    }

    /**
     * 初始化科大讯飞语音播报
     */
    private void initKeDaXunFei() {
//        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID + "=59239d52");
    }

    /**
     * copy 百度离线资源 and 科大讯飞资源
     */
    private void copyFile() {
        //离线地图
//        copyResourceFile(assertFolderName, BAIDULOCATION);
        //离线语音
//        copyResourceFile(IFLYTEK, iflayLOCATION);
        //copy wps   cn.wps.moffice_eng
//        copyResourceFile(WPS_ASSETS, WPS);
    }

    /**
     * copy 百度离线数据到 SD 卡中
     *
     * @param assertFolderName
     * @param pathFile
     */
    private void copyResourceFile(String assertFolderName, String pathFile) {
        if (pathFile.equals(iflayLOCATION)) {
            String path = Environment.getExternalStorageDirectory() + pathFile;
            filePa = new File(path);
            final File file = new File(filePa.getPath() + "/com.iflytek.vflynote_100206.apk");
            if (!file.exists()) {
                if (filePa.isDirectory()) {
                    filePa.delete();
                }
            }
            if (!filePa.exists())
                filePa.mkdir();

            ResourceUtils.copyFileFromAssets(assertFolderName, filePa.getPath());
        } else if (pathFile.equals(BAIDULOCATION)) {
            String path = Environment.getExternalStorageDirectory() + pathFile;
            if (Build.VERSION.SDK_INT >= 23) {
                externalFilesDir = getApplicationContext().getExternalFilesDir(pathFile);
            } else if (Build.VERSION.SDK_INT >= 15 && Build.VERSION.SDK_INT <= 21) {
                String sdPath = getSDPath();
                if (sdPath != null) {
                    externalFilesDir = new File(path);
                } else {
                    Toast.makeText(context, "没有找到手机SD卡,将不能使用北京离线地图", Toast.LENGTH_SHORT).show();
                }
            } else {
                String sdPath = getSDPath();
                if (sdPath != null) {
                    externalFilesDir = new File(path);
                } else {
                    Toast.makeText(context, "没有找到手机SD卡,将不能使用北京离线地图", Toast.LENGTH_SHORT).show();
                }
            }
            if (externalFilesDir != null && !externalFilesDir.exists())
                externalFilesDir.mkdir();
            ResourceUtils.copyFileFromAssets(assertFolderName, filePa.getPath());
        } else if (pathFile.equals(WPS)) {
            String path = Environment.getExternalStorageDirectory() + pathFile;
            filePa = new File(path);
            wpsPath = filePa.getPath();
            final File file = new File(filePa.getPath() + "/moffice_10.7.3_1033_cn00563_multidex_d41f69bc0d.apk");
            if (!file.exists()) {
                if (filePa.isDirectory()) {
                    filePa.delete();
                }
            }
            if (!filePa.exists())
                filePa.mkdir();
            ResourceUtils.copyFileFromAssets(assertFolderName, filePa.getPath());
        }
    }

    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }
}
