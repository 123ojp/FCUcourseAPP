package com.example.o123ojp.appprojet;

import android.content.Intent;
import android.net.Uri;
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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DonateActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public void open_browser(View v){
        Uri uri = Uri.parse("http://machine116688.000webhostapp.com/666_2.html");
        Intent intent = new Intent();
        intent.setAction(intent.ACTION_VIEW);
        intent.setData(uri);
        startActivity(intent);
    }


    private WebView m_WV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
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


        m_WV = (WebView)findViewById(R.id.webview);
        WebSettings webSettings = m_WV.getSettings();
        webSettings.setJavaScriptEnabled(true);


    }

    @Override
    public void onStart()
    {
        super.onStart();
        String strRbtUrl = "http://machine116688.000webhostapp.com/666_2.html";
        m_WV.loadUrl(strRbtUrl);
        m_WV.setWebViewClient(new WebViewClient());
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
        getMenuInflater().inflate(R.menu.main, menu);
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
            Intent intent = new Intent();
            intent.setClass(this,SettingActivity.class);
            this.startActivity(intent);
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

        } else if (id == R.id.sidebar_share  ) {
            Intent share_intent = new Intent();
            share_intent.setAction(Intent.ACTION_SEND);//设置分享行为
            share_intent.setType("text/plain");//设置分享内容的类型
            share_intent.putExtra(Intent.EXTRA_SUBJECT, "分享 逢甲餘額提醒app");//添加分享内容标题
            share_intent.putExtra(Intent.EXTRA_TEXT, MainActivity.share_detale);//添加分享内容
            //创建分享的Dialog
            share_intent = Intent.createChooser(share_intent, "分享");
            startActivity(share_intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
