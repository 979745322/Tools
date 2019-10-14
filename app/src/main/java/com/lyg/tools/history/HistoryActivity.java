package com.lyg.tools.history;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.erning.common.utils.LogUtils;
import com.lyg.tools.R;
import com.necer.calendar.BaseCalendar;
import com.necer.calendar.Miui9Calendar;
import com.necer.entity.CalendarDate;
import com.necer.enumeration.CalendarState;
import com.necer.listener.OnCalendarChangedListener;
import com.necer.listener.OnCalendarStateChangedListener;
import com.necer.utils.CalendarUtil;

import org.joda.time.LocalDate;

import java.util.Calendar;

public class HistoryActivity extends AppCompatActivity {

    private TextView textViewMonth;
    private Miui9Calendar miui9Calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        textViewMonth = findViewById(R.id.text_month);
        miui9Calendar = findViewById(R.id.miui9Calendar);
        miui9Calendar.post(new Runnable() {
            @Override
            public void run() {
                changeDate();
            }
        });
        miui9Calendar.setOnCalendarChangedListener(new OnCalendarChangedListener() {
            @Override
            public void onCalendarChange(BaseCalendar baseCalendar, int year, int month, LocalDate localDate) {
                changeDate();
            }
        });
    }

    public void changeDate(){
        CalendarDate calendarDate = CalendarUtil.getCalendarDate(miui9Calendar.getCurrectSelectDateList().get(0));
        //获取当前页面的数据 如果是月周折叠日历 周状态下获取的是一周的数据，月状态下获取的一月的数据
        LogUtils.d("localDate", calendarDate.localDate.toString());
        textViewMonth.setText(calendarDate.localDate.toString());

    }

}
