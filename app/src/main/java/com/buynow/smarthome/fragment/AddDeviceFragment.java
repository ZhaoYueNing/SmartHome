package com.buynow.smarthome.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buynow.smarthome.R;
import com.dd.CircularProgressButton;

/**
 * Created by Zhao on 2016/7/14.
 */
public class AddDeviceFragment extends Fragment {
    private FragmentManager mFm;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //init fragment magager
        mFm = getFragmentManager();
        View view = inflater.inflate(R.layout.fragment_add_device,null);
        //空调
        view.findViewById(R.id.bt_addDevice_airConditioning).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFm.beginTransaction().replace(R.id.fl_home,new BrandsSelectFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }
}
