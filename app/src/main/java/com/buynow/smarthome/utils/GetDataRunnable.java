package com.buynow.smarthome.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.buynow.smarthome.activity.HomeActivity;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by Zhao on 2016/7/17.
 * 实现温湿度显示
 */
public class GetDataRunnable implements Runnable {
    private Handler handler;

    public GetDataRunnable(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        byte[] buf = new byte[1024];

            DatagramSocket socket = GetMyDatagramSocket.getSocket();
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            Log.d("GETDATARUNNABLE_TAG", "run");
            while(true){
                try {
                    socket.receive(packet);
                    String data = new String(packet.getData(), 0, packet.getLength());
                    Log.d("GETDATARUNNABLE_TAG", data);
                    if (data.contains("wenshidu=")){
                        String str_wendu = data.substring(9,11);
                        String str_shidu = data.substring(11, 13);
                        int wendu = Integer.parseInt(str_wendu);
                        int shidu = Integer.parseInt(str_shidu);

                        Message msg = Message.obtain();
                        msg.arg1=wendu;
                        msg.arg2=shidu;

                        msg.what= HomeActivity.UPDATE_WENSHIDU;
                        handler.sendMessage(msg);
                    }else if (data.contains("ruqin&")){
                        //检测到入侵
                        Message msg = Message.obtain();
                        msg.what= HomeActivity.RUQIN_Y;
                        handler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


    }
}
