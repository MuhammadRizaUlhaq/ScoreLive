package com.example.scorelive.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.scorelive.R;
import com.google.android.material.tabs.TabLayout;

public class FavoritesFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);
        FavoritesPagerAdapter favoritesPagerAdapter = new FavoritesPagerAdapter(getContext(), getChildFragmentManager());
        ViewPager viewPager = root.findViewById(R.id.viewPagerFavorites);
        viewPager.setAdapter(favoritesPagerAdapter);
        TabLayout tabs = root.findViewById(R.id.tabsFavorites);
        tabs.setupWithViewPager(viewPager);
        return root;
    }
}