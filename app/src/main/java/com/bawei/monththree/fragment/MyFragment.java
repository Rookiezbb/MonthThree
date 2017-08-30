package com.bawei.monththree.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.bawei.monththree.R;
import com.bawei.monththree.activity.WebActivity;
import com.bawei.monththree.adapter.MyAdapter;
import com.bawei.monththree.bean.ListData;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.XListView;

/**
 * Created by Zhang on 2017/8/24.
 */

public class MyFragment extends Fragment implements XListView.IXListViewListener {
    private String urlPath;
    private XListView xlv;
    private List<ListData.DataBeanX.DataBean> list;
    private MyAdapter myAdapter;

    public static MyFragment getInstance(String url) {
        MyFragment myFragment = new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        myFragment.setArguments(bundle);
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag, container, false);
        xlv = (XListView) v.findViewById(R.id.xlv);
        urlPath = getArguments().getString("url");
        init();
        getData();
        return v;
    }

    private void init() {
        xlv.setXListViewListener(this);
        xlv.setPullRefreshEnable(true);
        xlv.setPullLoadEnable(true);
        list = new ArrayList<>();
        myAdapter = new MyAdapter(getActivity(), list);
        xlv.setAdapter(myAdapter);

        xlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListData.DataBeanX.DataBean daaa = (ListData.DataBeanX.DataBean) parent.getAdapter().getItem(position);
                String weburl = daaa.getGroup().getShare_url();
                Intent in = new Intent(getActivity(), WebActivity.class);
                in.putExtra("url",weburl);
                startActivity(in);
            }
        });
    }

    private void getData() {
        x.http().get(new RequestParams(urlPath), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson g = new Gson();
                ListData ld = g.fromJson(result, ListData.class);
                list .addAll(ld.getData().getData());
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("eeeeeeeeee", "onError: " + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                xlv.stopLoadMore();
                xlv.stopRefresh();
            }
        });
    }

    @Override
    public void onRefresh() {
       list.clear();
        getData();
    }

    @Override
    public void onLoadMore() {
        getData();
    }
}
