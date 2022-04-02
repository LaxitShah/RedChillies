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

public class CatererPackageAdapter extends BaseAdapter {
    Context context;
    ArrayList<PackageDataHolder> packageDataHolderArrayList;
    public CatererPackageAdapter(Context context, ArrayList<PackageDataHolder> packageDataHolderArrayList) {
        this.context=context;
        this.packageDataHolderArrayList=packageDataHolderArrayList;

    }

    @Override
    public int getCount() {
        return packageDataHolderArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return packageDataHolderArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=layoutInflater.inflate(R.layout.raw_list2,null);

        ImageView imgData=view.findViewById(R.id.package_image_data);
        TextView textView=view.findViewById(R.id.package_name);

        PackageDataHolder packageDataHolder=packageDataHolderArrayList.get(i);

        Glide.with(view).load(packageDataHolderArrayList.get(i).getPackage_image()).into(imgData);
        textView.setText(packageDataHolder.getPackage_name());

        return view;
    }
}
