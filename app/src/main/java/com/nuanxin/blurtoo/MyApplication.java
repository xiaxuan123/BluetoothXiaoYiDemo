package com.nuanxin.blurtoo;

import android.app.Application;
import android.content.Context;

import com.inuker.bluetooth.library.BluetoothClient;

/**
 * @author Staff
 * @description
 * @date 2019/4/25
 */
public class MyApplication extends Application {
   private static BluetoothClient mClient;
   private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
//        if (mClient == null) {
//            mClient = new BluetoothClient(mContext);
//        }
    }
    public static BluetoothClient getBluetoothClient() {
        if (mClient == null) {
            mClient = new BluetoothClient(mContext);
        }
        return mClient;
    }
    
    public void initData() {

    }
}
