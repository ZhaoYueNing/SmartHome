package com.buynow.smarthome.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.buynow.smarthome.R;
import com.buynow.smarthome.adapter.OneWordListViewAdapter;
import com.buynow.smarthome.adapter.StudyModAdapter;
import com.buynow.smarthome.dao.MySQLiteOpenHelper;
import com.buynow.smarthome.domain.StudyMod;
import com.buynow.smarthome.utils.CmdSender;
import com.dd.CircularProgressButton;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Zhao on 2016/7/17.
 */
public class StudyModFragment extends Fragment {

    public static final int UPDATE_LISTVIEW = 1;
    private static String TAG = "StudyModFragment_TAG";
    private ListView lv_studyItem;
    private StudyModAdapter adapter;
    private SQLiteDatabase db ;
    //已学习完成条目 list
    private static List<StudyMod> studyModList = new LinkedList<StudyMod>();

    private  Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_LISTVIEW:
                    adapter.notifyDataSetChanged();
                    lv_studyItem.setAdapter(adapter);
                    break;

            }
            return false;
        }
    });



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        //从数据库获取之前 保存的 学习记录
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(getContext(),null);
        Log.d(TAG,(helper==null)+"");
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query(MySQLiteOpenHelper.STUDYMOD_TABLE_NAME,
                new String[]{"name","count"},null,null,null,null,"count");


        View view = inflater.inflate(R.layout.fragment_studymod,null);
        //学习记录 条目 listview
        lv_studyItem = (ListView) view.findViewById(R.id.lv_studymod);
        adapter = new StudyModAdapter(getContext(), R.layout.item_oneword,studyModList);
        lv_studyItem.setAdapter(adapter);
        lv_studyItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemId = studyModList.get(position).getId();
                //发送命令 使用 学习信号
                CmdSender.sendCmd("86"+itemId+"0000");
            }
        });
        CircularProgressButton cpb_addStudyMod = (CircularProgressButton) view.findViewById(R.id.cpb_addStudyMod);
        //添加学习记录
        cpb_addStudyMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().add(R.id.fl_home,new AddStudyItemFragment(studyModList,handler))
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }
}
