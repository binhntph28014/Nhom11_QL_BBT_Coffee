package binhntph28014.fpoly.nhom11_ql_bbt_coffee;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import binhntph28014.fpoly.nhom11_ql_bbt_coffee.Adapter.adapterSideshow;

import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.sideshow;
import me.relex.circleindicator.CircleIndicator;


public class HomeFragment extends Fragment {
    LinearLayout btnTea, btnCoffee, btnSmoothie, btnOther;
    GridView gv;
    SearchView edTimKiem;
    //commit thu
    TextView tv_entry;
    ViewPager viewPager;
    List<sideshow> list_sideshow;
    CircleIndicator circleIndicator;
    binhntph28014.fpoly.nhom11_ql_bbt_coffee.Adapter.adapterSideshow adapterSideshow;
    Timer timer;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main2, container, false);
        btnTea = view.findViewById(R.id.btnTea);
        btnCoffee = view.findViewById(R.id.btnCoffee);
        btnSmoothie = view.findViewById(R.id.btnSmoothies);
        btnOther = view.findViewById(R.id.btnOther);
        gv = view.findViewById(R.id.gvPopular);
        edTimKiem= view.findViewById(R.id.edTimKiem);
        tv_entry = view.findViewById(R.id.tv_entry);
// thử commit
        //sideshow
        viewPager = view.findViewById(R.id.pager_sideshow);
        circleIndicator = view.findViewById(R.id.indicator_img);
        list_sideshow  = getListSideShow();
        adapterSideshow = new adapterSideshow(getContext(),list_sideshow);
        viewPager.setAdapter(adapterSideshow);
        circleIndicator.setViewPager(viewPager);
        adapterSideshow.registerDataSetObserver(circleIndicator.getDataSetObserver());
        auto_sideshow();
        //end sideshow






        return view;
    }

    private List<sideshow> getListSideShow() {
        List<sideshow> list = new ArrayList<>();
        list.add(new sideshow(R.drawable.banner1));
        list.add(new sideshow(R.drawable.banner2));
        list.add(new sideshow(R.drawable.banner3));
        return list;
    }
    //hàm chạy auto
    private void auto_sideshow() {
        if (list_sideshow ==null || list_sideshow.isEmpty() || viewPager == null){
            return;
        }
        if (timer == null){
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int curentItem = viewPager.getCurrentItem();
                        int totalItem = list_sideshow.size()-1;
                        if (curentItem < totalItem){
                            curentItem++;
                            viewPager.setCurrentItem(curentItem);
                        }else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        },500,2500);
    }


    //end hàm sideshow
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer!= null){
            timer.cancel();
            timer = null;
        }
    }




}