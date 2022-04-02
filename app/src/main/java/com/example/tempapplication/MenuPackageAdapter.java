package com.example.tempapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MenuPackageAdapter extends BaseAdapter {

    Context context;
    ArrayList<MenuDataHolder> menuDataHolderArrayList;

    public MenuPackageAdapter(Context context, ArrayList<MenuDataHolder> menuDataHolderArrayList) {
        this.context=context;
        this.menuDataHolderArrayList=menuDataHolderArrayList;

    }

    @Override
    public int getCount() {
        return menuDataHolderArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return menuDataHolderArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=layoutInflater.inflate(R.layout.raw_caterer_list,null);

        ImageView imgData=view.findViewById(R.id.rcl_icon);
        TextView tvData=view.findViewById(R.id.rcl_title);

        MenuDataHolder menuDataHolder=menuDataHolderArrayList.get(i);
//        imgData.setImageResource(caterModelArrayList.get(i).getPimage());
        Glide.with(view).load(menuDataHolderArrayList.get(i).getP_image()).into(imgData);
        tvData.setText(menuDataHolder.getMenu_name());
        return  view;

    }
}
