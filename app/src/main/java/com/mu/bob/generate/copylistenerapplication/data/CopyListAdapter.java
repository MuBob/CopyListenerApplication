package com.mu.bob.generate.copylistenerapplication.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mu.bob.generate.copylistenerapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/10/18.
 */

public class CopyListAdapter extends BaseAdapter{
    private List<CopyBean> list;
    private Context context;
    private SimpleDateFormat dateFormat;

    public CopyListAdapter(Context context,List<CopyBean> list) {
        this.list = list;
        this.context = context;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public CopyBean getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private ViewHolder holder;
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.item_list, viewGroup, false);
            holder=new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder= (ViewHolder) view.getTag();
        }
        CopyBean item = getItem(i);
        holder.textView.setText(item.getText());
        holder.timeView.setText(dateFormat.format(new Date(Long.parseLong(item.getTimestample()))));
        return view;
    }
    private class ViewHolder{
        public TextView textView;
        public TextView timeView;
        public ViewHolder(View view) {
            textView = (TextView) view.findViewById(R.id.text);
            timeView = (TextView) view.findViewById(R.id.time);
        }
    }

}
