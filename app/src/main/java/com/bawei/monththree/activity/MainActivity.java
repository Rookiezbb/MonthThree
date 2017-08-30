package com.bawei.monththree.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.bawei.monththree.fragment.MyFragment;
import com.bawei.monththree.R;
import com.bawei.monththree.bean.TabData;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager vp;
    private Fragment[] frag;
    private TabLayout tl;
    private List<TabData.DataBean> tlistd ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(isNetworkConnected(this)){
            Toast.makeText(this, "网络已连接", Toast.LENGTH_SHORT).show();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("网络连接失败");
            builder.setMessage("请连接您的网络");
            builder.setNegativeButton("取消" ,null);
            builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS));
                }
            }).create().show();
        }

        vp = (ViewPager) findViewById(R.id.vp);
        tl = (TabLayout) findViewById(R.id.tl);
        getData();

    }

    private void getData() {
        String tburl = "http://lf.snssdk.com/neihan/service/tabs/";
        x.http().get(new RequestParams(tburl), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson g = new Gson();
                TabData tabData = g.fromJson(result, TabData.class);
                getTBL(tabData.getData());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void getTBL(final List<TabData.DataBean> tblist) {
            if(tblist!=null){
                frag = new Fragment[tblist.size()];
                tlistd = tblist;
            }

        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return getfrag(position);
            }

            @Override
            public int getCount() {
                return tblist.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tblist.get(position).getName();
            }
        });
        tl.setupWithViewPager(vp);
    }

    private Fragment getfrag(int position) {
        Fragment fg = frag[position];
        if(fg==null){
            fg = MyFragment.getInstance(tlistd.get(position).getUrl());
            frag[position] = fg;
        }
        return fg;
    }

    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
