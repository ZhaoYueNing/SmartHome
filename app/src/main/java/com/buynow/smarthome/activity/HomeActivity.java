package com.buynow.smarthome.activity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.buynow.smarthome.R;
import com.buynow.smarthome.dao.MySQLiteOpenHelper;
import com.buynow.smarthome.fragment.AddDeviceFragment;
import com.buynow.smarthome.fragment.ControlAirFragment;
import com.buynow.smarthome.fragment.GetDataFragment;
import com.buynow.smarthome.fragment.InitModuleFragment;
import com.buynow.smarthome.fragment.DeviceListFragment;
import com.buynow.smarthome.fragment.SafeManagerFragment;
import com.buynow.smarthome.fragment.StudyModFragment;
import com.buynow.smarthome.utils.GetDataRunnable;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final int UPDATE_WENSHIDU = 0;
    public static final int RUQIN_Y = 1;
    //温湿度数据 0温度 1湿度
    private int[] wenshidu = new int[]{20,20};
    //是否有入侵
    private static boolean isRuqin = false;
    public static int cuurentDevice=27;

    private FragmentManager mFm;
    
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_WENSHIDU:
                    wenshidu[0]=msg.arg1;
                    wenshidu[1]=msg.arg2;
                    break;
                //发现入侵
                case RUQIN_Y:
                    isRuqin=true;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        //初始化数据
        initData();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        
        //创建线程监听 模块发送的温湿度数据
        new Thread(new GetDataRunnable(handler)).start();

        //当前设备
        mFm.beginTransaction().replace(R.id.fl_home,new ControlAirFragment()).commit();
    }

    private void initData() {
        mFm = getSupportFragmentManager();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //TODO: 设置菜单监听
        if (id == R.id.nav_InitModule) {
            //进入初始化模块界面
            mFm.beginTransaction().replace(R.id.fl_home, new InitModuleFragment()).commit();
        } else if (id == R.id.nav_deviceList) {
            //进入设备列表
            mFm.beginTransaction().replace(R.id.fl_home, new DeviceListFragment()).commit();
        } else if (id == R.id.nav_currentDevice) {
            //当前设备
            mFm.beginTransaction().replace(R.id.fl_home,new ControlAirFragment()).commit();
        }else if (id== R.id.nav_addNewDevice){
            //添加设备
            mFm.beginTransaction().replace(R.id.fl_home,new AddDeviceFragment()).commit();
        }else if(id==R.id.nav_studyMod){
            //学习模块
            mFm.beginTransaction().replace(R.id.fl_home, new StudyModFragment()).commit();
        } else if (id == R.id.nav_data) {
            //室内环境
            mFm.beginTransaction().replace(R.id.fl_home,new GetDataFragment(wenshidu)).commit();
        } else if (id == R.id.nav_safe_manager) {
            //安全管家
            mFm.beginTransaction().replace(R.id.fl_home,new SafeManagerFragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static void setIsRuqin(boolean ruqin) {
        isRuqin=ruqin;
    }

    public static boolean getRuqin() {
        return isRuqin;
    }
}
