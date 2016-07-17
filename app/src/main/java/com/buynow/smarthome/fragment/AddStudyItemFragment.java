package com.buynow.smarthome.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.buynow.smarthome.R;
import com.buynow.smarthome.adapter.StudyModAdapter;
import com.buynow.smarthome.domain.StudyMod;
import com.buynow.smarthome.utils.CmdSender;
import com.dd.CircularProgressButton;

import java.util.List;

/**
 * Created by Zhao on 2016/7/17.
 */
public class AddStudyItemFragment extends Fragment{
    private Handler handler;
    private List<StudyMod> studyMods;


    public AddStudyItemFragment(){

    }
    @SuppressLint("ValidFragment")
    public AddStudyItemFragment(List<StudyMod> studyMods, Handler handler) {
        this.studyMods = studyMods;
        this.handler = handler;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addstudymod, null);
        final EditText et_name = (EditText) view.findViewById(R.id.et_name);
        CircularProgressButton cpb_commit = (CircularProgressButton) view.findViewById(R.id.cpb_startStudy);
        //添加
        cpb_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //备注名
                String name = et_name.getText().toString();
                int studyId = studyMods.size();

                if (studyId>64){
                    Toast.makeText(getContext(), "学习位已占完", Toast.LENGTH_SHORT).show();
                    return;
                }
                StudyMod item = new StudyMod(studyId);
                item.setName(name);

                //发送学习命令
                String cmd = "88"+ CmdSender.fromStringCmd(studyId+"")+"0000";
                CmdSender.sendCmd(cmd);

                //保存到本机 学习记录 刷新 listview
                studyMods.add(item);
                Message msg = Message.obtain();
                msg.what = StudyModFragment.UPDATE_LISTVIEW;
                handler.sendMessage(msg);

                getFragmentManager().popBackStack();
            }
        });
        return view;
    }
}
