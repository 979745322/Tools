package com.lyg.tools.history;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.erning.common.absbase.AbsBaseActivity;
import com.erning.common.utils.LogUtils;
import com.lyg.tools.R;
import com.lyg.tools.history.ebtity.History;
import com.lyg.tools.joke.JokeAdapter;
import com.necer.calendar.BaseCalendar;
import com.necer.calendar.Miui9Calendar;
import com.necer.entity.CalendarDate;
import com.necer.enumeration.CalendarState;
import com.necer.listener.OnCalendarChangedListener;
import com.necer.listener.OnCalendarStateChangedListener;
import com.necer.utils.CalendarUtil;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class HistoryActivity extends AbsBaseActivity {

    private RecyclerView recyclerView;
    private TextView textViewMonth;
    private Miui9Calendar miui9Calendar;
    private HistoryAdapter adapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_history;
    }

    @Override
    protected void initControls() {
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
        recyclerView = findViewById(R.id.history_list);
        initRecyclerView();

    }

    public void changeDate() {
        CalendarDate calendarDate = CalendarUtil.getCalendarDate(miui9Calendar.getCurrectSelectDateList().get(0));
        //获取当前页面的数据 如果是月周折叠日历 周状态下获取的是一周的数据，月状态下获取的一月的数据
        LogUtils.d("localDate", calendarDate.localDate.toString());
        textViewMonth.setText(calendarDate.localDate.toString());
        loadData(calendarDate.localDate.toString());

    }

    @Override
    protected void onGetResult(JSONObject result) {
        super.onGetResult(result);
        List<History> list = JSON.parseArray(result.getJSONArray("result").toJSONString(), History.class);
//        LogUtils.d("list", list.toString());
        adapter.setData(list);
    }
    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        // 定义一个线性布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(this);
        // 设置布局管理器
        recyclerView.setLayoutManager(manager);
        adapter = new HistoryAdapter(new ArrayList<History>(), this);
        recyclerView.setAdapter(adapter);

    }
    void loadData(String date) {
        String dateStr = Integer.parseInt(date.split("-")[1]) + "/" + Integer.parseInt(date.split("-")[2]);
        String url = "http://v.juhe.cn/todayOnhistory/queryEvent.php";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("date", dateStr);
        hashMap.put("key", "30686ea390709f8cdb9f86066564e058");
        get(url, "history", hashMap);
    }

}
