package com.example.project3mon;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class BookingActivity extends AppCompatActivity {

    private Button btnTime,btnTime2;
    private TextView txtName,txtPrice,txtShow,txtNotiCheck;
    private RoundedImageView imageAvatar;
    int hour,minute,hour2,minute2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        btnTime=findViewById(R.id.btnTime);
        btnTime2=findViewById(R.id.btnTime2);
        txtName=findViewById(R.id.txtName);
        txtPrice=findViewById(R.id.txtPrice);
        imageAvatar=findViewById(R.id.imageAvatar);
        txtShow=findViewById(R.id.txtShow);
        txtNotiCheck=findViewById(R.id.txtNotiCheck);

        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }
        User user=(User) bundle.get("User");
        String Price=(String) bundle.get("Price");
        int imgResID = this.getResources().getIdentifier(user.getImage(), "drawable", this.getPackageName());
        imageAvatar.setImageResource(imgResID);
        txtName.setText(user.getName());
        txtPrice.setText(Price+" / 1 Buổi");
        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        List<String> listday=new ArrayList<>();
        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                int date=eventDay.getCalendar().getTime().getDate();
                int month=eventDay.getCalendar().getTime().getMonth()+1;
                String alo="";
                if(listday.contains(date+"")){
                    listday.remove(date+"");
                }else{
                    listday.add(date+"");
                }
                Collections.sort(listday);
                for (int i = 0; i < listday.size(); i++) {
                    if(i==(listday.size()-1)){
                        alo += listday.get(i);
                    }else{
                        alo += listday.get(i) + ",";
                    }
                }
                txtShow.setText("Buổi tập sễ diễn ra vào ngày "+alo+" tháng " + month +" năm 2022");
                txtNotiCheck.setText("Ngày "+date+" HLV này còn trống lịch từ 10:00 đến 12:00 và 17:30 đén 19:30");
                txtNotiCheck.setBackground(getDrawable(R.drawable.custom_input_2));
            }
        });
    }

    public void clickToChooseTime(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectHour, int selectMinute) {
                hour=selectHour;
                minute=selectMinute;
                btnTime.setText("Từ: "+String.format(Locale.getDefault(),"%02d:%02d",hour,minute));
                btnTime2.setText("Đến: "+String.format(Locale.getDefault(),"%02d:%02d",hour+1,minute));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(BookingActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth,onTimeSetListener,hour,minute,true);
        timePickerDialog.show();
    }

    public void clicktoBack2(View view) {
        finish();
    }
}