package com.example.o123ojp.appprojet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {
    private String years = "107";
    private String ss = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Spinner spinner = (Spinner) findViewById(R.id.year_spinner);
        final String[] lunch = {"105", "106", "107", "108", "109", "其他"};
        ArrayAdapter<String> lunchList = new ArrayAdapter<>(SettingActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                lunch);
        spinner.setAdapter(lunchList);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            EditText ss_editTextMessage = (EditText) findViewById(R.id.set_ss);
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (lunch[position].equals("其他")) {
                    ss_editTextMessage.setVisibility(View.VISIBLE);
                    years=null;
                } else {
                    years = lunch[position];
                    ss_editTextMessage.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Spinner ss_spinner = (Spinner) findViewById(R.id.ss_spinner);
        final String[] lunch1 = {"上學期","下學期"};
        ArrayAdapter<String> lunchList1 = new ArrayAdapter<>(SettingActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                lunch1);
        ss_spinner.setAdapter(lunchList1);
        ss_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                position += 1;
                ss =  Integer.toString(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void ClickFinish(View view) {
        EditText sec_editTextMessage = (EditText) findViewById(R.id.set_sec_cheak);
        String sec_set = sec_editTextMessage.getText().toString();
        if (sec_set.length()==0){
            sec_set="10";
        }
        if (years == null){
            EditText ss_editTextMessage = (EditText) findViewById(R.id.set_ss);
            String ss_set = ss_editTextMessage.getText().toString();
            if (ss_set.length()==0){
                sec_set="107";
            }
            years = ss_set;
        }
        Toast.makeText(this, "學年" + years + "\n學期:"+ss+"\n秒數:"+sec_set+"\n設定成功", Toast.LENGTH_SHORT).show();
        SettingfileRW.setconf("years",years);
        SettingfileRW.setconf("ss",ss);
        SettingfileRW.setconf("sec",sec_set);
        this.finish();

    }
}
