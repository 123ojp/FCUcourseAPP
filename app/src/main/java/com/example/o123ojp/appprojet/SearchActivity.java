package com.example.o123ojp.appprojet;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.activity_search);
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
        getMenuInflater().inflate(R.menu.search, menu);
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

    //自訂一開始
    public void searchcourse(View view) {
        TextView sub_name_text=(TextView) findViewById(R.id.sub_name_text);
        TextView open_quota_text=(TextView) findViewById(R.id.open_quota_text);
        TextView real_quota_text=(TextView) findViewById(R.id.real_quota_text);
        TextView course_time_text=(TextView) findViewById(R.id.course_time_text);
        TextView course_credits_text=(TextView) findViewById(R.id.course_credits_text);
       // TextView search_error_text=(TextView) findViewById(R.id.search_error_text);
        EditText editTextMessage;
        editTextMessage = (EditText) findViewById(R.id.course_input);
        String course = editTextMessage.getText().toString();

        //清空
        sub_name_text.setText("-");
        open_quota_text.setText("-");
        real_quota_text.setText("-");
        course_time_text.setText("-");
        course_credits_text.setText("-");
     //   search_error_text.setText("");



        if (course.length() == 4) {
            Cheakcourse new_course = new Cheakcourse(course);
            if(new_course.getIs_error()){
                Toast.makeText(this, new_course.getError_text(), Toast.LENGTH_SHORT).show();

            } else {
                sub_name_text.setText(new_course.getSub_name());
                open_quota_text.setText(new_course.getOpen_quota());
                real_quota_text.setText(new_course.getReal_quota());
                course_time_text.setText(new_course.getCourse_time());
                course_credits_text.setText(new_course.getCourse_credits());

            }

        } else {
            Toast.makeText(this,"請輸入四碼數字", Toast.LENGTH_SHORT).show();

        }
    }
}
