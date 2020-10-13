package com.example.foody.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.foody.View.Fragment.AnGiFragment;
import com.example.foody.View.Fragment.ODauFragment;

public class AdapterViewPagerTrangChu extends FragmentStatePagerAdapter {
    AnGiFragment anGiFragment;
    ODauFragment oDauFragment;

    public AdapterViewPagerTrangChu(@NonNull FragmentManager fm) {
        super(fm);
        anGiFragment = new AnGiFragment();
        oDauFragment = new ODauFragment();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return oDauFragment;
            case 1:
                return anGiFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }


}
