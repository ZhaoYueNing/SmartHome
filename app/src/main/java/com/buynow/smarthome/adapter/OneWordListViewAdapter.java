package com.buynow.smarthome.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.buynow.smarthome.R;

import java.util.List;

/**
 * Created by Zhao on 2016/7/14.
 */
public class OneWordListViewAdapter extends ArrayAdapter<String> {
    private int mResource = R.layout.item_oneword;
    private List<String> mStrings ;
    private Context mContext;
    public OneWordListViewAdapter(Context context, List<String> objects) {
        super(context, R.layout.item_oneword, objects);
        mStrings = objects;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, mResource, null);
        TextView tv = (TextView) view.findViewById(R.id.tv_oneword);
        tv.setText(mStrings.get(position));

        return view;
    }
}
