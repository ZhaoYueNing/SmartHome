package com.buynow.smarthome.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buynow.smarthome.R;
import com.buynow.smarthome.utils.CmdSender;

/**
 * Created by Zhao on 2016/7/17.
 */
public class GetDataFragment extends Fragment {
    private static final int UPDATE_DATA = 1;
    private int[] wenshidu;
    private TextView tv_wendu;
    private TextView tv_shidu;
    private View cpb_refresh;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_DATA:
                    updateData();
                    break;
            }
        }
    };

    public GetDataFragment() {

    }

    public GetDataFragment(int[] wenshidu) {
        super();
        this.wenshidu = wenshidu;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_getdata, null);
        tv_wendu = (TextView) view.findViewById(R.id.tv_wendu);
        tv_shidu = (TextView) view.findViewById(R.id.tv_shidu);
        //进入界面 起始开始刷新数据
        sendGetDataCmdAndUpdate();
        cpb_refresh = view.findViewById(R.id.cpb_refresh);
        //刷新 发送命令给模块 获取数据
        cpb_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendGetDataCmdAndUpdate();
            }
        });
        return view;
    }

    //发送获取室内环境数据命令
    //延迟1s后更新数据
    private void sendGetDataCmdAndUpdate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String cmd = "getwenshidu";
                CmdSender.sendCmd(cmd);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message msg = Message.obtain();
                msg.what=UPDATE_DATA;
                handler.sendMessage(msg);
            }
        }).start();
    }

    private void updateData(){
        tv_wendu.setText(wenshidu[0]+"");
        tv_shidu.setText(wenshidu[1]+"");
    }
}
