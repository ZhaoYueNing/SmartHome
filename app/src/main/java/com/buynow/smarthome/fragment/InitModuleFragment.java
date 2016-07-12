package com.buynow.smarthome.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.buynow.smarthome.R;
import com.buynow.smarthome.utils.TCPRunnable;
import com.dd.CircularProgressButton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by buynow on 16-6-9.
 * 初始化模块
 */
public class InitModuleFragment extends Fragment{
    private static String TAG = "TAG_InitModuleFragment";
    //TODO 模块ip地址
    private final String MODULE_IP ="169.254.131.175";// "192.168.4.1";
    //模块端口号
    private final int MODULE_PORT = 8098;

    private CircularProgressButton bt_initDevice;
    private EditText et_wifiName;
    private EditText et_wifiPassword;
    private EditText et_SN;
    private View mView;
    private Socket mSocket;
    private Executor mExecutor;
    /*    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };*/

    public InitModuleFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragm_init_module, null);

        //初始化数据
        //初始化各组件
        initData();
        //初始化事件
        initEvent();
        return mView;
    }

    private void initEvent() {
        //点击按钮
        bt_initDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //初始化模块
                initDevice();

            }
        });
    }

    /**
     * 初始化模块
     * 1.通过TCP链接到模块
     * (TCP 192.168.4.1:8080 模块为服务端)
     * 2.为模块分配ID(通过UUID随机分配)
     * 3.传递模块需要的wifi名及密码
     */
    private void initDevice() {
        //TODO init dervice
        //获取 wifi名 wifi密码 模块sn码
        String wifiName = et_wifiName.getText().toString();
        String wifiPassword = et_wifiPassword.getText().toString();
        String moduleSN = et_SN.getText().toString();
        mExecutor.execute(new TCPRunnable(wifiName,wifiPassword,moduleSN,MODULE_IP,MODULE_PORT));

    }

    private void initData() {
        mExecutor = Executors.newCachedThreadPool();
        et_wifiName = (EditText) mView.findViewById(R.id.et_wifiName);
        et_wifiPassword = (EditText) mView.findViewById(R.id.et_wifiPassword);
        et_SN = (EditText)mView.findViewById(R.id.et_sn);
        bt_initDevice = (CircularProgressButton) mView.findViewById(R.id.bt_initDevice);
    }
}
