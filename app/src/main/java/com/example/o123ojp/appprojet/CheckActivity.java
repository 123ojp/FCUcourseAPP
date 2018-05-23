package com.example.o123ojp.appprojet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class CheckActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button btnStart = (Button)findViewById(R.id.btnStartService);
        Button btnStop = (Button)findViewById(R.id.btnStopService);
        final TextView tvCourseNum = (TextView)findViewById(R.id.tvCheck);

        View.OnClickListener btnOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(CheckActivity.this, CheckService.class);
                it.putExtra("courseNum", tvCourseNum.getText().toString());
                switch (view.getId()) {
                    case R.id.btnStartService:
                        startService(it);
                        break;
                    case R.id.btnStopService:
                        stopService(it);
                        break;
                }
            }
        };

        btnStart.setOnClickListener(btnOnClickListener);
        btnStop.setOnClickListener(btnOnClickListener);
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
        getMenuInflater().inflate(R.menu.check, menu);
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

        if (id == R.id.sidebar_course_alert) {
            Intent intent = new Intent();
            intent.setClass(this, CheckActivity.class);
            startActivity(intent);
        } else if (id == R.id.sidebar_course_book) {
            Intent intent = new Intent();
            intent.setClass(this, BookActivity.class);
            startActivity(intent);
        } else if (id == R.id.sidebar_course_search) {
            Intent intent = new Intent();
            intent.setClass(this, SearchActivity.class);
            startActivity(intent);
        } else if (id == R.id.sidebar_donate) {
            Intent intent = new Intent();
            intent.setClass(this, DonateActivity.class);
            startActivity(intent);
        } else if (id == R.id.sidebar_qa) {
            Intent intent = new Intent();
            intent.setClass(this, QaActivity.class);
            startActivity(intent);

        } else if (id == R.id.sidebar_main  ) {
            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
