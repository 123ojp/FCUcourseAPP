package com.example.o123ojp.appprojet;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private EditText etName;
    private TeacherArrayAdapter adapter = null;
    private List<bookMessage> teachers;
    private  int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        etName = (EditText)findViewById(R.id.etName);

        getTeachersFromFirebase();
        adapter = new TeacherArrayAdapter(this,new ArrayList<bookMessage>());

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
        getMenuInflater().inflate(R.menu.book, menu);
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

    public void turnSearchName(View v){
        int i = 0;
        String teaName = etName.getText().toString();
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface di, int i) {
            }
        };
        if(teaName.equals("")){
            ad.setTitle("查詢失敗");
            ad.setMessage("輸入格式錯誤");
        }else{
            for(i = 0;i < total;i++){
                if(teachers.get(i).getTitle().equals(teaName)) {
                    break;
                }
            }
            if(i > total){
                ad.setTitle("查詢失敗");
                ad.setMessage("輸入錯誤 or 無資料");
            }else{
                ad.setTitle(teachers.get(i).getTitle());
                ad.setMessage(teachers.get(i).getMessage());
            }
        }
        ad.setPositiveButton("確定",listener);
        ad.show();
    }

    public void turnBookWeb(View v){
        Uri uri = Uri.parse("http://shakemylife.pixnet.net/blog/post/27321371");
        Intent intent = new Intent();
        intent.setAction(intent.ACTION_VIEW);
        intent.setData(uri);
        startActivity(intent);
    }

    private void getTeachersFromFirebase(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                new FirebaseThread(dataSnapshot).start();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("AdoptTeacher",databaseError.getMessage());
            }
        });
    }

    class FirebaseThread extends Thread{
        private DataSnapshot dataSnapshot;

        public FirebaseThread(DataSnapshot dataSnapshot){
            this.dataSnapshot = dataSnapshot;
        }

        public void run(){
            List<bookMessage>lsTeachers = new ArrayList<>();
            for(DataSnapshot ds:dataSnapshot.getChildren()){
                DataSnapshot dsSTitle = ds.child("Title");
                DataSnapshot dsSMessage = ds.child("Message");

                String title = (String)dsSTitle.getValue();
                String message = (String)dsSMessage.getValue();

                bookMessage ateacher = new bookMessage();
                ateacher.setTitle(title);
                ateacher.setMessage(message);
                lsTeachers.add(ateacher);
                Log.v("AdoptTeacher",title + ";" + message);
                total++;
            }
            teachers = lsTeachers;
        }
    }

    class TeacherArrayAdapter extends ArrayAdapter<bookMessage>{
        Context context;

        public TeacherArrayAdapter(Context context,List<bookMessage> items){
            super(context,0,items);
            this.context = context;
        }
    }
}
