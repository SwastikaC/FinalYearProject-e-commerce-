package com.example.lookclassy.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.lookclassy.R;
import com.example.lookclassy.home.fragments.CartFragment;
import com.example.lookclassy.home.fragments.CategoryFragment;
import com.example.lookclassy.home.fragments.home.HomeFragment;
import com.example.lookclassy.home.fragments.MoreFragment;
import com.example.lookclassy.home.fragments.WishlistFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
        BottomNavigationView bottomNavigationView;
        HomeFragment homeFragment;
        CategoryFragment categoryFragment;
        CartFragment cartFragment;
        WishlistFragment wishListFragment;
        MoreFragment moreFragment;
        Fragment currentFragment;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.WHITE);
            setContentView(R.layout.activity_main);
            bottomNavigationView = findViewById(R.id.mainBottomNav);
            homeFragment = new HomeFragment();
            homeFragment.setBottomNavigationView(bottomNavigationView);
            currentFragment = homeFragment;
            getSupportFragmentManager().beginTransaction().add(R.id.mainFrame, homeFragment).commit();
            bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
               public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    if (item.getTitle().equals("Home")) {
                        if (homeFragment == null)
                            homeFragment = new HomeFragment();
                        changeFragment(homeFragment);
                        return true;

                    }

                    else if (item.getTitle().equals("Category")) {
                        if (categoryFragment == null)
                            categoryFragment = new CategoryFragment();
                        changeFragment(categoryFragment);
                        return true;
                    }
                     else if (item.getTitle().equals("Cart")) {
                        if (cartFragment == null)
                            cartFragment = new CartFragment();
                        changeFragment(cartFragment);
                        return true;

                    }
                    else if (item.getTitle().equals("Wishlist")) {
                        if (wishListFragment == null)
                            wishListFragment = new WishlistFragment();
                        changeFragment(wishListFragment);
                        return true;

                    }
                    else if (item.getTitle().equals("More")) {
                        if (moreFragment == null)
                            moreFragment = new MoreFragment();
                        changeFragment(moreFragment);
                        return true;

                    }
                    return false;
                }
            });

        }

        void changeFragment(Fragment fragment) {
            getSupportFragmentManager().beginTransaction().hide(currentFragment).commit();

            if (fragment.isAdded()) {

                getSupportFragmentManager().beginTransaction().show(fragment).commit();

            } else {
                getSupportFragmentManager().beginTransaction().add(R.id.mainFrame, fragment).commit();
            }
            currentFragment = fragment;
        }
    }