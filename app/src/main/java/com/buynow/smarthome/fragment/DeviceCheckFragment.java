package com.buynow.smarthome.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.buynow.smarthome.R;
import com.buynow.smarthome.activity.HomeActivity;
import com.buynow.smarthome.utils.ByteArrayGroup;
import com.buynow.smarthome.utils.UDPRunnable;

/**
 * Created by Zhao on 2016/7/15.
 */
public class DeviceCheckFragment extends Fragment {
    private int[] models=new int[]{0,20,21,22,23,24,25,26,27,28,
        29,30,31,32,33,34,35,36,37,38,39};
    byte currentCount = 1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkdevice,null);
        final TextView tv_count = (TextView) view.findViewById(R.id.tv_count);
        //有响应
        Button bt_havResp = (Button) view.findViewById(R.id.bt_havResp);
        tv_count.setText("1"+"/"+models.length);
        Button bt_noResp = (Button) view.findViewById(R.id.bt_noResp);
        ImageButton ib_power = (ImageButton) view.findViewById(R.id.ib_power);
        //无响应
        bt_noResp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //到下一个适配型号
                currentCount++;
                if (currentCount>models.length){
                    Toast.makeText(getContext(), "无该设备", Toast.LENGTH_SHORT).show();
                    currentCount= (byte) models.length;
                }
                tv_count.setText((currentCount)+"/"+models.length);
            }
        });
        ib_power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送命令
                String head = "yaokong_key=";
                StringBuffer cmd1 = new StringBuffer();
                StringBuffer cmd2 = new StringBuffer();

                //当前型号
                int n = models[currentCount-1];
                String str = Integer.toHexString(n);
                //补0数量
                n = 4-str.length();
                StringBuffer sb = new StringBuffer();
                if (n>=0){
                    for (int i=0;i<n;i++){
                        sb.append("0");
                    }
                }
                sb.append(str);

                cmd1.append("02"+sb.toString()+"08"+"&");
                cmd2.append("04"+"FF"+"0808"+"&");


                /*
                bytes = new byte[]{0x4,(byte)255,0x08,0x08,0x4^((byte)255)^0x08^0x08,46};
                byte[] bytes = new byte[]{0x02,0x0, (byte) models[currentCount-1],0x8, (byte) (02^0xf^currentCount^0x8),46};
                byte[] bytes1 = ByteArrayGroup.group(head.getBytes(),bytes);
                byte[] bytes2 = ByteArrayGroup.group(head.getBytes(),bytes);
                */

                /*int i=0;
                for(;i<head.getBytes().length;i++){
                    bytes1[i]=head.getBytes()[i];
                }
                int d=i;
                for (;i<bytes1.length;i++){
                    bytes1[i]=bytes[i-d];
                }*/


                //TODO UDP PORT IP
                new Thread(new MyRunnable(head+cmd1.toString().toUpperCase(),head+cmd2.toString().toUpperCase())).start();

            }
        });
        bt_havResp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity.cuurentDevice=models[currentCount];

            }
        });






        return view;
    }
    private String getXOR(int i){
        int i_xor = (i);
        String xor = Integer.toHexString(i_xor);
        if (xor.length()==1){
            xor="0"+xor;
        }else if (xor.length()>2){
            xor=xor.substring(xor.length()-1, xor.length());
        }
        return xor;
    }
    private static  class MyRunnable implements Runnable{
        private String cmd1;
        private String cmd2;

        public MyRunnable(String cmd1, String cmd2) {
            this.cmd1 = cmd1;
            this.cmd2 = cmd2;
        }

        @Override
        public void run() {
            new Thread(new UDPRunnable("255.255.255.255",8002,cmd1.toString())).start();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Thread(new UDPRunnable("255.255.255.255",8002,cmd2.toString())).start();
        }
    }
}
