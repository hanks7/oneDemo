package com.home.onedemo.App;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.support.multidex.MultiDex;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.interfaces.BetaPatchListener;

import java.util.Locale;

/**
 * Created by wangtao on 2018/4/22.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setStrictMode();
        AppUtils.init(this);
        initBuglyHeat(this, "68208e6ec2", true);
    }

    private void initBuglyHeat(Context context, String appId, boolean isDebug) {
        // 设置是否自动下载补丁
        Beta.canAutoDownloadPatch = true;
        // 设置是否提示用户重启
        Beta.canNotifyUserRestart = true;
        // 设置是否自动合成补丁
        Beta.canAutoPatch = true;
        /**
         * 补丁回调接口，可以监听补丁接收、下载、合成的回调
         */
        Beta.betaPatchListener = new BetaPatchListener() {
            @Override
            public void onPatchReceived(String patchFileUrl) {
                UToast.showText(patchFileUrl);
            }

            @Override
            public void onDownloadReceived(long savedLength, long totalLength) {
                UToast.showText(String.format(Locale.getDefault(),
                        "%s %d%%",
                        Beta.strNotificationDownloading,
                        (int) (totalLength == 0 ? 0 : savedLength * 100 / totalLength)));
            }

            @Override
            public void onDownloadSuccess(String patchFilePath) {
                UToast.showText( patchFilePath);
            }

            @Override
            public void onDownloadFailure(String msg) {
                UToast.showText( msg);
            }

            @Override
            public void onApplySuccess(String msg) {
                UToast.showText( msg);
            }

            @Override
            public void onApplyFailure(String msg) {
                UToast.showText( msg);
            }

            @Override
            public void onPatchRollback() {
                UToast.showText( "onPatchRollback");
            }
        };
        Bugly.init(context, appId, isDebug);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);
        // 安装tinker
        Beta.installTinker();
    }

    @TargetApi(9)
    protected void setStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
    }
}

