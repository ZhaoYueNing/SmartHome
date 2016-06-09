package com.buynow.smarthome.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.buynow.smarthome.R;
import com.buynow.smarthome.domain.Device;
import com.dd.CircularProgressButton;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by buynow on 16-6-9.
 */
public class AddNewDeviceFragment  extends Fragment{
    //模块ip地址
    private final String DERVICE_IP = "192.168.4.1";
    //模块端口号
    private final int DERVICE_PORT = 8080;

    private CircularProgressButton bt_initDevice;
    private EditText et_wifiName;
    private EditText et_wifiPassword;
    private View mView;
/*    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };*/

    public AddNewDeviceFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragm_add_new_device, null);

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                //通过tcp连接模块
                try {
                    String wifiName = et_wifiName.getText().toString();
                    String wifiPassword = et_wifiPassword.getText().toString();

                    Socket socket= new Socket(DERVICE_IP,DERVICE_PORT);
                    PrintWriter out = new PrintWriter(
                            new BufferedWriter(new OutputStreamWriter(
                                    socket.getOutputStream())), true);

                    //为设备设置id

                    //传输给设备需要连入的wifi名及其密码

                    //添加设备到设备列表

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void initData() {
        et_wifiName = (EditText) mView.findViewById(R.id.et_wifiName);
        et_wifiPassword = (EditText) mView.findViewById(R.id.et_wifiPassword);
        bt_initDevice = (CircularProgressButton) mView.findViewById(R.id.bt_initDevice);
    }
}
