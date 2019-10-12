package com.lyg.tools.joke;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.erning.common.absbase.AbsBaseFragment;
import com.erning.common.utils.LogUtils;
import com.lyg.tools.R;
import com.lyg.tools.entity.Joke;
import com.lyg.tools.entity.Joke_Table;
import com.lyg.tools.entity.OttoStringData;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectFragment extends AbsBaseFragment {
    private RecyclerView recyclerView;
    private JokeAdapter jokeAdapter;
    private int page = 0;
    private Boolean hasMore = true;

    public CollectFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_collect;
    }

    @Override
    protected void initWidget(View root) {
        AppBus.instance.register(this);
        super.initWidget(root);

    }


    public void reset(){
        recyclerView = mRoot.findViewById(R.id.collectjokelist);
        page = 0;
        hasMore = true;
        initRecyclerView();
        loadData();
    }

    @Subscribe
    public void subscribeEvent(OttoStringData data) {
        loadData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppBus.instance.unregister(this);
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

    public void loadData() {
        if(hasMore){
            List list = SQLite.select().from(Joke.class).orderBy(Joke_Table.id, false).limit(30).offset(page++ * 30).queryList();
            jokeAdapter.addData(list);
            /*if(list.size()<30){
                hasMore = false;
            }*/
        }




    }
}
