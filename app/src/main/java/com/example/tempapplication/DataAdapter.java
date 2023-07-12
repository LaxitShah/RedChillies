package com.example.tempapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DataAdapter extends BaseAdapter {

    Context context;
    ArrayList<DataModel> dataModelArrayList;
    public DataAdapter(Context context, ArrayList<DataModel> dataModelArrayList) {
            this.context=context;
            this.dataModelArrayList=dataModelArrayList;

    }

    @Override
    public int getCount() {
        return dataModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=layoutInflater.inflate(R.layout.raw_caterer_list,null);

        ImageView imgData=convertView.findViewById(R.id.rcl_icon);
        TextView tvData=convertView.findViewById(R.id.rcl_title);

        DataModel dataModel=dataModelArrayList.get(position);
        imgData.setImageResource(dataModelArrayList.get(position).getImgLang());
        tvData.setText(dataModel.getStrLang());


        return convertView;
    }
}
