package com.lzp.netmonitor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.yanzhenjie.permission.AndPermission;

import java.util.ArrayList;
import java.util.List;

/**
 * 网络状态切换
 *
 * @author li.zhipeng
 */
public class NetworkBroadCast extends BroadcastReceiver {

    @SuppressLint("StaticFieldLeak")
    private static Context applicationContext;

    /**
     * 保留所有需要监听监听网络的回调函数
     */
    private static ArrayList<NetEventHandler> mListeners = new ArrayList<>();

    /**
     * 添加网络监听Listener
     *
     * @param sticky 是否需要立即返回网络结果
     */
    public static void addNetWorkHandler(NetEventHandler handler, Boolean sticky) {
        if (mListeners.contains(handler)) {
            return;
        }
        if (sticky) {
            handler.onNetChange(NetUtils.INSTANCE.getNetworkType(applicationContext));
        }
        mListeners.add(handler);
    }

    /**
     * 添加网络监听Listener
     */
    public static void addNetWorkHandler(NetEventHandler handler) {
        if (mListeners.contains(handler)) {
            return;
        }
        mListeners.add(handler);
    }

    /**
     * 移除网络监听Listener
     */
    public static void removeNetWorkHandler(NetEventHandler handler) {
        mListeners.remove(handler);
    }

    /**
     * 在Application中注册，不需要解绑，会直接创建NetworkBroadCast对象
     */
    public static void register(final Context context) {
        applicationContext = context.getApplicationContext();
        // 判断是否拥有权限
        if (AndPermission.hasPermission(context,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET)) {
            //安卓5.0以上使用新的api进行监听
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // 据说在个别机型上有问题，我们做一个异常处理
                try {
                    registerAboveLOLLIPOP();
                } catch (Exception e) {
                    registerNormal();
                }
            } else {
                registerNormal();
            }
        }
    }

    /**
     * 安卓5.0注册网络监听
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static void registerAboveLOLLIPOP() {
        ConnectivityManager
                connectivityManager = (ConnectivityManager) applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            connectivityManager.requestNetwork(new NetworkRequest.Builder().build(), new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(Network network) {
                    super.onAvailable(network);
                    setNetWordState();
                }

                @Override
                public void onLost(Network network) {
                    super.onLost(network);
                    setNetWordState();
                }
            });
        }
    }

    /**
     * 普通网络监听注册
     */
    private static void registerNormal() {
        IntentFilter netFilter = new IntentFilter();
        netFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        applicationContext.registerReceiver(new NetworkBroadCast(), netFilter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            setNetWordState();
        }
    }

    /**
     * 为所有的监听函数分发网络变化的状态
     */
    private static void setNetWordState() {
        // 获得当前的网络类型
        int netWorkType = NetUtils.INSTANCE.getNetworkType(applicationContext);
        // 通知接口完成加载
        if (mListeners.size() > 0) {
            // 对目前注册的监听做一个备份，防止并发操作list出现的异常
            List<NetEventHandler> arrLocal = (List<NetEventHandler>) mListeners.clone();
            for (int i = 0; i < arrLocal.size(); i++) {
                (arrLocal.get(i)).onNetChange(netWorkType);
            }
        }
    }

    public interface NetEventHandler {

        void onNetChange(int netWorkType);
    }
}