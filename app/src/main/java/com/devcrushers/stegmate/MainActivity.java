package com.devcrushers.stegmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout navigationBar;
    Fragment fragmentEncode=new FragmentEncode();
    Fragment fragmentDecode=new DecodeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationBar=findViewById(R.id.navigationDrawer);
        NavigationView navView=findViewById(R.id.navigationView);
        Toolbar toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,navigationBar,toolbar,R.string.openDrawer,R.string.closeDrawer);

        navigationBar.addDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();
                if(id==R.id.optHome){
                    loadFragment(new FragmentHome());
                } else if (id==R.id.optRegister) {
                    loadFragment(new FragmentHome());

                }
                else if (id==R.id.optLogin) {
                    loadFragment(new FragmentHome());

                }
                else{
                    loadFragment(new FragmentHome());

                }

                navigationBar.closeDrawer(GravityCompat.START);


                return true;
            }
        });



    }
    @Override
    public void onBackPressed(){

        FragmentManager f=getSupportFragmentManager();
        Fragment fm = f.findFragmentById(R.id.container);

        if(navigationBar.isDrawerOpen(GravityCompat.START)){
            navigationBar.closeDrawer(GravityCompat.START);
        /*}else if(fm.equals(fragmentbb)){
            loadFragment(new BaFragment());
        }else if(fm.equals(fragmentbc)){
            loadFragment(fragmentbb);*/
        }else
            super.onBackPressed();
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();

        ft.replace(R.id.container, fragment);
        ft.commit();
    }


    public void goToEncode() {
        loadFragment(fragmentEncode);
    }

    public void goToDecode() {
        loadFragment(fragmentDecode);
    }
}