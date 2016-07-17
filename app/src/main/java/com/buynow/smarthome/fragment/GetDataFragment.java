package com.buynow.smarthome.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buynow.smarthome.R;

/**
 * Created by Zhao on 2016/7/17.
 */
public class GetDataFragment extends Fragment {
    private int[] wenshidu;
    private TextView tv_wendu;
    private TextView tv_shidu;
    private View cpb_refresh;

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
        updateData();
        cpb_refresh = view.findViewById(R.id.cpb_refresh);
        cpb_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
        return view;
    }

    private void updateData(){
        tv_wendu.setText(wenshidu[0]+"");
        tv_shidu.setText(wenshidu[1]+"");
    }
}
