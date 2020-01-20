package com.example.salma.doctolib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

public class CalendarActivity extends AppCompatActivity {

   CalendarView calendarView;
   String mydate;
    Integer idmm;
    String idms ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        Intent ooIntent = getIntent();
        idms = ooIntent.getStringExtra("idm");
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        Button saveBtn = (Button) findViewById(R.id.saveDateBtn);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                mydate = ( dayOfMonth  )  + "/" + ( month + 1 ) + "/" +year;
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String datt = mydate;
                    Intent detIntent = new Intent(getApplicationContext(), listTimeActivity.class);
                    detIntent.putExtra("date", datt);
                    detIntent.putExtra("idm",idms);
                    startActivity(detIntent);
                }
        });
    }
}
