package com.example.tempapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CatererAdapter extends BaseAdapter {

    Context context;
    ArrayList<dataholder> catererModelArrayList;

    public CatererAdapter(Context context, ArrayList<dataholder> catererModelArrayList) {
        this.context=context;
        this.catererModelArrayList=catererModelArrayList;

    }

    @Override
    public int getCount() {
        return catererModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return catererModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=layoutInflater.inflate(R.layout.raw_caterer_list,null);

        ImageView imgData=view.findViewById(R.id.rcl_icon);
        TextView tvData=view.findViewById(R.id.rcl_title);

        dataholder dataholder=catererModelArrayList.get(i);
//        imgData.setImageResource(caterModelArrayList.get(i).getPimage());
        Glide.with(view).load(catererModelArrayList.get(i).getPimage()).into(imgData);
        tvData.setText(dataholder.getFirst_name());
        return  view;
    }
}
