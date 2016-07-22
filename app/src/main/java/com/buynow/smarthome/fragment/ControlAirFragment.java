package com.buynow.smarthome.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.buynow.smarthome.R;
import com.buynow.smarthome.utils.CmdSender;
import com.buynow.smarthome.utils.UDPRunnable;
import com.dd.CircularProgressButton;

import org.w3c.dom.Text;

/**
 * Created by Zhao on 2016/7/16.
 *
 */
public class ControlAirFragment extends Fragment {
    private int power_count=1;
    private int wendu;
    //00：自动 01：制冷 02：除湿 03：送风 04：制暖
    private int modle;
    //00 = 自动 01=1 档 02=2 档 03=3 档
    private int fengli=0;
    private ImageButton ib_model_cool;
    private ImageButton ib_model_hot;
    private ImageButton ib_model_water;
    private ImageButton ib_model_wind;
    private ImageButton ib_power;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control_airconditioner,null);
        final TextView tv_wendu = (TextView) view.findViewById(R.id.tv_wendu);
        ib_model_cool = (ImageButton) view.findViewById(R.id.ib_model_cool);
        ib_model_hot = (ImageButton) view.findViewById(R.id.ib_model_hot);
        ib_model_water = (ImageButton) view.findViewById(R.id.ib_model_water);
        ib_model_wind = (ImageButton) view.findViewById(R.id.ib_model_wind);
        ib_power = (ImageButton) view.findViewById(R.id.ib_power);
        final ImageButton ib_fengli = (ImageButton) view.findViewById(R.id.ib_fengli);

        CircularProgressButton cpb_lessen = (CircularProgressButton) view.findViewById(R.id.ib_wendu_lessen);
        CircularProgressButton cpb_add = (CircularProgressButton) view.findViewById(R.id.ib_wendu_add);
        //制冷模式
        ib_model_cool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setModle(1);
            }
        });
        //除湿模式
        ib_model_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setModle(2);
            }
        });
        ib_model_wind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setModle(3);
            }
        });
        ib_model_hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setModle(4);
            }
        });

        //添加温度
        cpb_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int wendu = Integer.parseInt(tv_wendu.getText().toString());
                if (wendu>=31){
                    return;
                }
                wendu++;
                tv_wendu.setText(wendu+"");
                setWendu(wendu);
            }
        });
        //开关
        ib_power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str="";
                if (power_count==1){
                    //开空调
                    str = "FF";
                    power_count=0;
                }else{
                    power_count=1;
                    str = "00";
                }
                String cmd = "04"+str+"0808";
                CmdSender.sendCmd(cmd);

            }
        });
        //风力设置
        //00 = 自动 01=1 档 02=2 档 03=3 档
        ib_fengli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str="";
                switch (fengli){
                    case 0:
                        str="00";
                        fengli++;
                        ib_fengli.setImageResource(R.drawable.fengli_zd);
                        Toast.makeText(getContext(), "风力自动", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        str="01";
                        ib_fengli.setImageResource(R.drawable.fengli_r);
                        Toast.makeText(getContext(), "风力一档", Toast.LENGTH_SHORT).show();
                        fengli++;
                        break;
                    case 2:
                        str="02";
                        ib_fengli.setImageResource(R.drawable.fengli_z);
                        Toast.makeText(getContext(), "风力二档", Toast.LENGTH_SHORT).show();
                        fengli++;
                        break;
                    case 3:
                        str="03";
                        ib_fengli.setImageResource(R.drawable.fengli_q);
                        Toast.makeText(getContext(), "风力三档", Toast.LENGTH_SHORT).show();
                        fengli=0;
                        break;
                }
                String cmd = "07"+str+"08"+"08";
                CmdSender.sendCmd(cmd);
            }
        });
        cpb_lessen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int wendu = Integer.parseInt(tv_wendu.getText().toString());
                if (wendu<=16){
                    return;
                }
                wendu--;
                tv_wendu.setText(wendu+"");
                setWendu(wendu);
            }
        });
        return view;
    }

    private void setWendu(int wendu){

        String cmd = "yaokong_key="+"06"+Integer.toHexString(wendu)+"08"+"08"+"&";
        new Thread(new UDPRunnable("255.255.255.255",8002,cmd.toString())).start();
    }
    //00：自动 01：制冷 02：除湿 03：送风 04：制暖
    private void setModle(int modle){
        String mod ="";
        switch (modle){
            case 0:
                Toast.makeText(getContext(), "自动模式", Toast.LENGTH_SHORT).show();
                mod="00";
                break;
            case 1:
                Toast.makeText(getContext(),"制冷模式",Toast.LENGTH_SHORT).show();
                mod = "01";
                break;
            case 2:
                Toast.makeText(getContext(), "除湿模式", Toast.LENGTH_SHORT).show();
                mod="02";
                break;
            case 3:
                Toast.makeText(getContext(), "送风模式", Toast.LENGTH_SHORT).show();
                mod = "03";
                break;
            case 4:
                Toast.makeText(getContext(), "制暖模式", Toast.LENGTH_SHORT).show();
                mod = "04";
                break;
        }
        //点击后按钮变为蓝色背景
        setBg(modle);
        String cmd ="yaokong_key="+ "05"+mod+"08"+"08&";
        new Thread(new UDPRunnable("255.255.255.255",8002,cmd)).start();

    }
    //不同温度模式
    //点击后实现背景变换
    //01：制冷 02：除湿 03：送风 04：制暖
    private void setBg(int flag){
        ib_model_cool.setBackgroundResource(R.drawable.bg_white);
        ib_model_water.setBackgroundResource(R.drawable.bg_white);
        ib_model_wind.setBackgroundResource(R.drawable.bg_white);
        ib_model_hot.setBackgroundResource(R.drawable.bg_white);

        switch (flag){
            case 1:
                ib_model_cool.setBackgroundResource(R.drawable.button_bg);
                break;
            case 2:
                ib_model_water.setBackgroundResource(R.drawable.button_bg);
                break;
            case 3:
                ib_model_wind.setBackgroundResource(R.drawable.button_bg);
                break;
            case 4:
                ib_model_hot.setBackgroundResource(R.drawable.button_bg);
                break;
        }

    }

}
