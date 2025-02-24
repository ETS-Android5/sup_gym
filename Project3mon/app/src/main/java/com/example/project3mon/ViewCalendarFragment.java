package com.example.project3mon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ViewCalendarFragment extends Fragment {

    private LinearLayout layoutView;
    private BottomNavigationItemView viewCalendar;

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_calendar, container, false);
        viewCalendar = getActivity().findViewById(R.id.action_view_calendar);
        viewCalendar.setEnabled(false);
        List<EventDay> events = new ArrayList<>();
        GetData dao = new GetData();
        Bundle bundle = getActivity().getIntent().getExtras();
        List<Calendar> list = new ArrayList<>();
        String id = (String) bundle.get("ID");
        int roleID = (int) bundle.get("roleID");
        if (!(bundle == null)) {
            try {
                if (roleID == 1) {
                    list = dao.getListday(id);
                }
                if (roleID == 2) {
                    list = dao.getListdayTrainer(id);
                }
                if (list != null) {
                    for (Calendar calendar : list) {
                        events.add(new EventDay(calendar, R.drawable.ic_baseline_event_available_24));
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        CalendarView calendarView = (CalendarView) view.findViewById(R.id.calendarView);
        calendarView.setEvents(events);
        layoutView = view.findViewById(R.id.layoutView);

        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                int date = eventDay.getCalendar().getTime().getDate();
                for (int i = 0; i < events.size(); i++) {
                    int event = events.get(i).getCalendar().getTime().getDate();
                    if (event == date) {
                        List<String> string = new ArrayList<>();
                        List<User> list = new ArrayList<>();
                        try {
                            TextView txtTitle = view.findViewById(R.id.txtTitle);
                            TextView txtStatus = view.findViewById(R.id.txtStatus);
                            TextView txtName = view.findViewById(R.id.txtName);
                            Button btnViewVideo = view.findViewById(R.id.btnViewVideo);
                            Button btnAccpet = view.findViewById(R.id.btnAccept);
                            TextView txtFromTo = view.findViewById(R.id.txtFromTo);
                            Button baitapDone = view.findViewById(R.id.btnBaiTapDone);
                            if (roleID == 1) {
                                txtTitle.setText("Lịch Tập HLV");
                                txtName.setText("Nguyễn Phùng Công Danh");
                                txtStatus.setText(" Đã Xác Nhận");
                                txtStatus.setTextColor(getResources().getColor(R.color.green));
                                btnViewVideo.setVisibility(View.VISIBLE);
                                btnAccpet.setVisibility(View.GONE);
                                txtStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_circle, 0, 0, 0);
                                string = dao.getSchedulesCustomer((String) bundle.get("ID"), events.get(i).getCalendar().getTime());
                            }
                            if (roleID == 2) {
                                txtTitle.setText("Học Viên");
                                txtName.setText("Dương Kim Long");
                                txtStatus.setText(" Đợi Xác Nhận");
                                txtStatus.setTextColor(getResources().getColor(R.color.yellowStar));
                                btnViewVideo.setVisibility(View.GONE);
                                btnAccpet.setVisibility(View.VISIBLE);
                                txtStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_cir_yellow, 0, 0, 0);
                                string = dao.getSchedulesTrainer((String) bundle.get("ID"), events.get(i).getCalendar().getTime());
                            }

                            btnAccpet.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    txtStatus.setText(" Đã Xác Nhận");
                                    txtStatus.setTextColor(getResources().getColor(R.color.green));
                                    btnAccpet.setVisibility(View.GONE);
                                    txtStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_circle, 0, 0, 0);
                                }
                            });
                            txtFromTo.setText(string + "");
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        layoutView.setVisibility(View.VISIBLE);
                        break;
                    } else {
                        layoutView.setVisibility(View.GONE);
                    }
                }


            }
        });
        TextView txtlink = (TextView) view.findViewById(R.id.txtlink);
        txtlink.setMovementMethod(LinkMovementMethod.getInstance());
        txtlink.setLinkTextColor(Color.GREEN);
        return view;
    }
}