package com.thirdmodule.pgy;

import android.net.Uri;
import android.util.Log;

import com.pgyersdk.update.DownloadFileListener;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.pgyersdk.update.javabean.AppBean;

/**
 * 蒲公英版本更新管理类
 *
 * @author lzy
 * create at 2018/11/12 10:53
 **/
public class PGYUpdateManager {

    /**
     * @param isForce 是否强制更新
     **/
    public static void checkAppUpdate(boolean isForce) {
        new PgyUpdateManager.Builder()
                .setForced(isForce)                //设置是否强制更新,非自定义回调更新接口此方法有用
                .setUserCanRetry(false)         //失败后是否提示重新下载，非自定义下载 apk 回调此方法有用
                .setDeleteHistroyApk(false)     // 检查更新前是否删除本地历史 Apk， 默认为true
                .register();
    }

//      new PgyUpdateManager.Builder()
//              .setForced(false)                //设置是否强制更新,非自定义回调更新接口此方法有用
//                .setUserCanRetry(false)         //失败后是否提示重新下载，非自定义下载 apk 回调此方法有用
//                .setDeleteHistroyApk(false)     // 检查更新前是否删除本地历史 Apk， 默认为true
//                .setUpdateManagerListener(new UpdateManagerListener() {
//        @Override
//        public void onNoUpdateAvailable() {
//            //没有更新是回调此方法
//            Log.d("pgyer", "there is no new version");
//        }
//
//        @Override
//        public void onUpdateAvailable(AppBean appBean) {
//            //有更新回调此方法
//            Log.d("pgyer", "there is new version can update"
//                    + "new versionCode is " + appBean.getVersionCode());
//            //调用以下方法，DownloadFileListener 才有效；
//            //如果完全使用自己的下载方法，不需要设置DownloadFileListener
//            PgyUpdateManager.downLoadApk(appBean.getDownloadURL());
//        }
//
//        @Override
//        public void checkUpdateFailed(Exception e) {
//            //更新检测失败回调
//            //更新拒绝（应用被下架，过期，不在安装有效期，下载次数用尽）以及无网络情况会调用此接口
//            Log.e("pgyer", "check update failed ", e);
//        }
//    })
//            //注意 ：
//            //下载方法调用 PgyUpdateManager.downLoadApk(appBean.getDownloadURL()); 此回调才有效
//            //此方法是方便用户自己实现下载进度和状态的 UI 提供的回调
//            //想要使用蒲公英的默认下载进度的UI则不设置此方法
//            .setDownloadFileListener(new DownloadFileListener() {
//        @Override
//        public void downloadFailed() {
//            //下载失败
//            Log.e("pgyer", "download apk failed");
//        }
//
//        @Override
//        public void downloadSuccessful(Uri uri) {
//            Log.e("pgyer", "download apk success");
//            // 使用蒲公英提供的安装方法提示用户 安装apk
//            PgyUpdateManager.installApk(uri);
//        }
//
//        @Override
//        public void onProgressUpdate(Integer... integers) {
//            Log.e("pgyer", "update download apk progress==>" + integers);
//        }
//    })
//            .register();
}
