package com.buynow.smarthome.fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.buynow.smarthome.R;
import com.buynow.smarthome.activity.HomeActivity;
import com.buynow.smarthome.utils.CmdSender;
import com.buynow.smarthome.utils.GetMyDatagramSocket;
import com.dd.CircularProgressButton;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class SafeManagerFragment extends Fragment {
    //有入侵
    private static final int RUQIN_Y = 1;
    //无入侵
    private static final int RUQIN_N = 0;
    private boolean isOpenSafe=false;
    private boolean currentIsRuqin = false;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RUQIN_Y:
                    ruqin_y();
            }
        }
    };
    private Switch sw_isOpenSafe;
    private ImageView iv_safephoto;
    private TextView tv_safeResult;
    private CircularProgressButton cpb_selectTime;
    private CircularProgressButton cpb_start;

    //发现入侵
    private void ruqin_y() {
        //设置图片与结果
        iv_safephoto.setImageResource(R.drawable.safe_red);
        tv_safeResult.setText("发现入侵!");
        //设为红色字体
        tv_safeResult.setTextColor(Color.rgb(255,0,0));
    }


    public SafeManagerFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_safe_manager, container, false);
        sw_isOpenSafe = (Switch) view.findViewById(R.id.sw_isOpenSafe);
        iv_safephoto = (ImageView) view.findViewById(R.id.iv_safephoto);
        tv_safeResult = (TextView) view.findViewById(R.id.tv_safe_result);
        cpb_selectTime = (CircularProgressButton) view.findViewById(R.id.cpb_selectTime);
        cpb_selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().add(R.id.fl_home, new TimeSelectFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        cpb_start = (CircularProgressButton) view.findViewById(R.id.cpb_start);
        cpb_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "预约成功", Toast.LENGTH_SHORT).show();
            }
        });

        sw_isOpenSafe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == false) {
                    //关闭防盗功能
                    //发送关闭防盗功能命令
                    String cmd = "FangDao_SET=0&";
                    CmdSender.sendCustomCmd(cmd);
                    isOpenSafe=false;
                    HomeActivity.setIsRuqin(false);
                    currentIsRuqin = false;
                    //还原图片与结果
                    iv_safephoto.setImageResource(R.drawable.safe);
                    tv_safeResult.setTextColor(Color.rgb(255,255,255));
                    tv_safeResult.setText("未启动该功能");
                }else{
                    //开启防盗功能
                    //发送开启防盗功能命令
                    //设置图片与结果为待命状态
                    String cmd = "FangDao_SET=1&";
                    CmdSender.sendCustomCmd(cmd);
                    iv_safephoto.setImageResource(R.drawable.safe_green);
                    tv_safeResult.setText("安全");
                    tv_safeResult.setTextColor(Color.rgb(0,255,0));

                    //开启线程 监听返回结果
                    isOpenSafe=true;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while(isOpenSafe){
                                if (HomeActivity.getRuqin()!=currentIsRuqin){
                                    currentIsRuqin=HomeActivity.getRuqin();
                                    if (currentIsRuqin) {
                                        //有入侵
                                        Message msg = Message.obtain();
                                        msg.what = RUQIN_Y;
                                        handler.sendMessage(msg);
                                    }else{
                                        //安全状态
                                        Message msg = Message.obtain();
                                        msg.what = RUQIN_N;
                                        handler.sendMessage(msg);
                                    }

                                }
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    }).start();
                }
            }
        });
        return view;
    }
}
