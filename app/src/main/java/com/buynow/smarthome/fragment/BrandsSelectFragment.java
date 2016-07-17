package com.buynow.smarthome.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.buynow.smarthome.R;
import com.buynow.smarthome.adapter.OneWordListViewAdapter;

import java.util.Arrays;

/**
 * Created by Zhao on 2016/7/15.
 */
public class BrandsSelectFragment extends Fragment {
    private FragmentManager mFm ;
    private String[] brands = new String[]{"格力","海尔","美的","长虹",
            "志高" ,"华宝","科龙","TCL", "格兰仕","华凌","春兰"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFm = getFragmentManager();
        View view = inflater.inflate(R.layout.fragment_brandsselect,null);
        ListView listView = (ListView) view.findViewById(R.id.lv);
        listView.setAdapter(new OneWordListViewAdapter(getContext(), Arrays.asList(brands)));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    //格力品牌空调
                    mFm.beginTransaction().replace(R.id.fl_home,new DeviceCheckFragment())
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
        return  view;
    }
}
