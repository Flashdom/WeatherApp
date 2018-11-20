package com.example.weatherapp.weatherapp.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.weatherapp.weatherapp.Fragments.RequestFragment;
import com.example.weatherapp.weatherapp.MyService;
import com.example.weatherapp.weatherapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Navigation_drawer_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RequestFragment.MyFragment.OnFragmentInteractionListener, RequestFragment.OnFragmentInteractionListener {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    RequestFragment.MyAnotherFragment myAnotherFragment;
    RequestFragment requestFragment;
    RequestFragment.MyFragment myFragment;
    private FirebaseAuth mAuth;
    private int users;
    private String TAG="ABC";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer_activity);
        initFragments();
        initNavigationDrawer();
       // onStartService();
        initFireBase();
    }
    public void updateUI(FirebaseUser user)
    {
        if (user!=null)
        users++;

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    public void onStartService()
    {
        Intent intent = new Intent(this, MyService.class);
        startService(intent);

    }
    public void onStopService() {
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }
    public void createAccount(String email, String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            //Toast.makeText(.this, "Authentication failed.",
                             //       Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });

    }
    public void initFireBase()
    {
        users=0;
        mAuth=FirebaseAuth.getInstance();

    }
        @Override
        protected void onDestroy()
        {
         super.onDestroy();
            onStopService();

        }
    public void initNavigationDrawer()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction ft = null;
                switch (menuItem.getItemId()){
                    case R.id.Callback:
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.fragmentContainer, myFragment);
                        ft.commit();
                        drawer.closeDrawers();

                        break;
                    case R.id.about:
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.fragmentContainer, myAnotherFragment);
                        ft.commit();
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_slideshow:
                        createAccount("arr98@mail.ru", "MyPassword");
                        drawer.closeDrawers();
                    case R.id.nav_manage:
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.fragmentContainer, requestFragment);
                        ft.commit();
                        drawer.closeDrawers();
                    default:
                        break;
                }
                return false;
            }
        });



    }

        public void initFragments()
        {
            myFragment=new RequestFragment.MyFragment();
            myAnotherFragment= new RequestFragment.MyAnotherFragment();
            requestFragment=new RequestFragment();
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragmentContainer, myFragment);
            fragmentTransaction.commit();

        }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Callback) {
            fragmentManager.beginTransaction().replace(R.id.fragmentlayout, myFragment).commit() ;

        } else if (id == R.id.about) {
            fragmentManager.beginTransaction().replace(R.id.fragmentlayout, myAnotherFragment).commit() ;

        } else if (id == R.id.nav_slideshow) {
            //fragmentManager.beginTransaction().replace(R.id.fragmentlayout, myFragment).commit() ;


        } else if (id == R.id.nav_manage) {
            fragmentManager.beginTransaction().replace(R.id.fragmentlayout, requestFragment).commit() ;


        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
