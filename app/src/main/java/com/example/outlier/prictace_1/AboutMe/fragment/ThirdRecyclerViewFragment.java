package com.example.outlier.prictace_1.AboutMe.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.outlier.prictace_1.AboutMe.AllClass.foodAdapter;
import com.example.outlier.prictace_1.AboutMe.AllClass.foodItem;
import com.example.outlier.prictace_1.R;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class ThirdRecyclerViewFragment extends Fragment {

    private static final boolean GRID_LAYOUT = false;
    private static final int ITEM_COUNT = 100;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    public static ThirdRecyclerViewFragment newInstance() {
        return new ThirdRecyclerViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        //setup materialviewpager

        if (GRID_LAYOUT) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        mRecyclerView.setHasFixedSize(true);

        //将“没有XX”的标签显示隐藏
        TextView noItem=(TextView)view.findViewById(R.id.label_noitem);
        noItem.setText("您还没有添加任何美食");
        noItem.setVisibility(View.GONE);


        //初始化美食列表
        final List<foodItem> items = new ArrayList<>();
        foodItem first=new foodItem(R.drawable.bql,"哈根达斯冰淇淋");
        items.add(first);
        foodItem second=new foodItem(R.drawable.ps,"披萨");
        items.add(second);

        //将美食的列表列出来
        final foodAdapter adapter=new foodAdapter(items);
        adapter.setOnItemLongClickListener(new foodAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view,final int position) {
                //定义AlertDialog.Builder对象，当长按列表项的时候弹出确认删除对话框
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setMessage("确定删除?");
                builder.setTitle("提示");
                //添加AlertDialog.Builder对象的setPositiveButton()方法
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(items.remove(position)!=null){
                            System.out.println("success");
                        }else {
                            System.out.println("failed");
                        }
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "删除列表项", Toast.LENGTH_SHORT).show();
                    }
                });

                //添加AlertDialog.Builder对象的setNegativeButton()方法
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create().show();
            }
        });
        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        mRecyclerView.setAdapter(adapter);

        if(!(items.size()>0)){
            noItem.setVisibility(View.VISIBLE);
        }
    }
}