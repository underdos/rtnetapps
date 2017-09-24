package net.kusnadi.rtnetapps.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import net.kusnadi.rtnetapps.entity.User;
import net.kusnadi.rtnetapps.fragment.DashboardFragment;
import net.kusnadi.rtnetapps.fragment.HomeFragment;
import net.kusnadi.rtnetapps.fragment.NotificationFragment;
import net.kusnadi.rtnetapps.R;
import net.kusnadi.rtnetapps.helper.BottomNavigationViewHelper;
import net.kusnadi.rtnetapps.helper.SessionManagerHelper;

public class HomeActivity extends BaseActivity {

    private SessionManagerHelper sessionManagerHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sessionManagerHelper = new SessionManagerHelper(HomeActivity.this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        displayFragment(R.id.navigation_home);

    }

    public void displayFragment(int id){
        Fragment fragment = null;

        switch(id){
            case R.id.navigation_home :
                User user = sessionManagerHelper.getSessionData();
                Log.d("USER", user.toString());
                Log.d("MENU", "its home drawer");
                fragment = new HomeFragment();
                break;
            case R.id.navigation_dashboard :
                Log.d("MENU", "its dashboard drawer");
                fragment = new DashboardFragment();
                break;
            case R.id.navigation_notifications :
                Log.d("MENU", "its notification drawer");
                fragment = new NotificationFragment();
                break;
            case R.id.navigation_exit :
                Log.d("EXIT", "calling logout");
                sessionManagerHelper.clearSession();
                Intent loginIntent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
                break;
        }

        if (fragment != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content, fragment);
            ft.commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            displayFragment(item.getItemId());
            return true;
        }

    };




}
