package com.qinlei.num.refreshview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.qinlei.num.refreshview.adapter.AddHeadFootAdapter;
import com.qinlei.num.refreshview.listener.OnItemClickListener;
import com.qinlei.num.refreshview.model.HeadFootBean;

import java.util.ArrayList;
import java.util.List;

public class AddHeadFootActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<HeadFootBean> mDatas = new ArrayList<>();
    private AddHeadFootAdapter myAdapter;
    private View head;
    private View foot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_head_foot);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        initAdapter();
        addHeadAndFoot();
        initListener();
        loadData();
    }

    private void initListener() {
        myAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(AddHeadFootActivity.this, mDatas.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddHeadFootActivity.this, "head 被点击", Toast.LENGTH_SHORT).show();
            }
        });
        foot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddHeadFootActivity.this, "foot 被点击", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new AddHeadFootAdapter(mDatas);
        recyclerView.setAdapter(myAdapter);
    }

    private void addHeadAndFoot() {
        head = LayoutInflater.from(AddHeadFootActivity.this).inflate(R.layout.item_custom_head, null);
        myAdapter.addHead(head);

        foot = LayoutInflater.from(AddHeadFootActivity.this).inflate(R.layout.item_custom_foot, null);
        myAdapter.addFoot(foot);
    }

    private void loadData() {
        for (int i = 0; i < 3; i++) {
            mDatas.add(new HeadFootBean("item" + i, i));
        }
        myAdapter.notifyDataSetChanged();
    }
}
