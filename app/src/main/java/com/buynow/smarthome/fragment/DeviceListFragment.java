package com.buynow.smarthome.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.buynow.smarthome.R;
import com.buynow.smarthome.dao.DeviceContainer;
import com.buynow.smarthome.domain.Device;

import java.util.List;


public class DeviceListFragment extends Fragment{
    private ListView lv_derviceList;
    private DeviceListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dervice_list, null);
        lv_derviceList = (ListView) view.findViewById(R.id.lv_derviceList);
        List<Device> devices = DeviceContainer.get();
        mAdapter = new DeviceListAdapter(getActivity(), R.layout.item_dervice,devices );
        lv_derviceList.setAdapter(mAdapter);
        return view;
    }


    class DeviceListAdapter extends ArrayAdapter<Device>{
        private int mResource;
        private Context mContext;
        private List<Device> mDevices;

        @Override
        public int getCount() {
            if (mDevices == null) {
                return 0;
            }
            return mDevices.size();
        }

        public DeviceListAdapter(Context context, int resource, List<Device> devices) {
            super(context, resource, devices);
            this.mContext = context;
            this.mDevices = devices;
            this.mResource = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final Device device = mDevices.get(position);
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.item_dervice, null);
            ImageView iv_loge = (ImageView) view.findViewById(R.id.iv_loge);
            ImageView iv_remove = (ImageView) view.findViewById(R.id.iv_remove);
            TextView tv_deviceNmae = (TextView) view.findViewById(R.id.tv_derviceName);
            tv_deviceNmae.setText(device.getName());
            setLoge(iv_loge,device.getType());
            iv_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //如果删除成功刷新list
                    if (DeviceContainer.removeDervice(device)) {
                        refresh();
                    }
                }
            });
            return view;
        }
    }

    //为不同模块设置不同loge
    private void setLoge(ImageView iv_loge, int type) {
        //TODO 为不同模块设置不同loge
    }

    /**
     * 刷新列表显示
     */
    private void refresh() {
        mAdapter.notifyDataSetChanged();
    }
}
