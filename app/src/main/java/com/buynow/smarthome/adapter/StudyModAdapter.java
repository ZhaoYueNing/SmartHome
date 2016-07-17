package com.buynow.smarthome.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.buynow.smarthome.R;
import com.buynow.smarthome.domain.StudyMod;

import java.util.List;

/**
 * Created by Zhao on 2016/7/17.
 */
public class StudyModAdapter extends ArrayAdapter<StudyMod> {
    private int resource;
    private List<StudyMod> studyMods;
    private Context context;

    public StudyModAdapter(Context context, int resource, List<StudyMod> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.studyMods = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StudyMod item = studyMods.get(position);
        View view = View.inflate(context, R.layout.item_oneword, null);
        TextView name = (TextView) view.findViewById(R.id.tv_oneword);
        name.setText(item.getName());
        return view;
    }
}
