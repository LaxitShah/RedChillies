package com.example.tempapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MyPageAdapter extends FragmentStatePagerAdapter {
    int tabCount;
    public MyPageAdapter(FragmentManager supportFragmentManager, int tabCount) {
        super(supportFragmentManager);

        this.tabCount=tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch(position)
        {
            case 0:
                Caterer_Home_Fragment chf=new Caterer_Home_Fragment();
                return chf;
            case 1:
                Caterer_Manage_Fragment cmf=new Caterer_Manage_Fragment();
                return  cmf;
            case 2:
                Caterer_Profile_Fragment cpf=new Caterer_Profile_Fragment();
                return cpf;

        }

        return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }

}
