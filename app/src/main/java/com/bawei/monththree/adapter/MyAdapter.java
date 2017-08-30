package com.bawei.monththree.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.monththree.R;
import com.bawei.monththree.bean.ListData;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Zhang on 2017/8/24.
 */

public class MyAdapter extends BaseAdapter {
    //上下文
    Context context;
    //list集合
    List<ListData.DataBeanX.DataBean> list;

    //构造器
    public MyAdapter(Context context, List<ListData.DataBeanX.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item, null);
            holder = new ViewHolder();
            holder.tv = (TextView) convertView.findViewById(R.id.itemtv);
            holder.img = (ImageView) convertView.findViewById(R.id.itemimg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //给控件赋值
        ListData.DataBeanX.DataBean daba = list.get(position);
        holder.tv.setText(daba.getGroup().getText());
        ImageLoader.getInstance().displayImage(daba.getGroup().getUser().getAvatar_url(), holder.img);
        return convertView;
    }

    class ViewHolder {
        TextView tv;
        ImageView img;
    }

}
