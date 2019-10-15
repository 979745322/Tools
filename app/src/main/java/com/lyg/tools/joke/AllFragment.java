package com.lyg.tools.joke;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.erning.common.absbase.AbsBaseFragment;
import com.lyg.tools.R;
import com.lyg.tools.diary.entity.Joke;
import com.lyg.tools.diary.entity.OttoStringData;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllFragment extends AbsBaseFragment {
    private RecyclerView recyclerView;
    private JokeAdapter jokeAdapter;

    public AllFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_all;
    }

    @Override
    protected void initWidget(View root) {
        AppBus.instance.register(this);
        super.initWidget(root);
        recyclerView = root.findViewById(R.id.alljokelist);
        initRecyclerView();
        loadData();

    }

    @Subscribe
    public void subscribeEvent(OttoStringData data){
        loadData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppBus.instance.unregister(this);
    }

    @Override
    protected void onPostResult(JSONObject result) {
        super.onPostResult(result);
        List<Joke> list = JSON.parseArray(result.getJSONArray("result").toJSONString(), Joke.class);
        jokeAdapter.addData(list);
        /*for(Joke joke:list){
            LogUtils.d("joke",joke.toString());
        }*/
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        // 定义一个线性布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        // 设置布局管理器
        recyclerView.setLayoutManager(manager);
        jokeAdapter = new JokeAdapter(getActivity(), new ArrayList<Joke>());
        recyclerView.setAdapter(jokeAdapter);

    }

    void loadData(){
        String url = "https://api.apiopen.top/getJoke";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("type", "all");
        hashMap.put("page", "0");
        hashMap.put("count", "20");
        post(url, "joke", hashMap);
    }


}
