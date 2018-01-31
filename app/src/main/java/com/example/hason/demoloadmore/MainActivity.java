package com.example.hason.demoloadmore;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements ILoadMore{

    RecyclerView recyclerView;

    CustomAdapter adapter;

    String images[] = {"ca_basa_chien_sa_ot", "cha_ca_chien_gion", "ech_xao", "kho_ga_xe_cay",
    "nam_co_dong", "rau_tuoi_sach", "salad_rau_tuong_ot_1", "sen_kep_thit_chien_gion"};
    int id_images[] = new int[8];
    List<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        dataList = new ArrayList<>();
        dataList.add("Cá basa chiên sả ớt");
        dataList.add("Chả cá chiên giòn");
        dataList.add("Ếch xào");
        dataList.add("Khô gà xé cay");
        dataList.add("nấm cô đông");
        dataList.add("Rau tươi sạch");
        dataList.add("Salad rau tương ớt");
        dataList.add("Sen kẹp thịt chiên giòn");

        for (int i = 0; i < images.length; i++) {
            int id = getResources().getIdentifier(images[i], "drawable", "com.example.hason.demoloadmore");
            id_images[i] = id;
        }

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CustomAdapter(this, dataList, id_images, recyclerView);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void loadMore() {
        adapter.setLoading();
        dataList.add(null);
        adapter.notifyDataSetChanged();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dataList.remove(dataList.size() - 1);
                adapter.notifyItemRemoved(dataList.size() - 1);
                randomData();
                adapter.notifyDataSetChanged();
                adapter.setLoading();
            }
        }, 5000);
    }

    public void randomData() {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            String data = random.nextInt() + "";
            dataList.add(data);
        }
    }
}
