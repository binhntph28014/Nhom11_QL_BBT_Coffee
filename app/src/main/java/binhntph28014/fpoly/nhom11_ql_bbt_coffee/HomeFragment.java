package binhntph28014.fpoly.nhom11_ql_bbt_coffee;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import binhntph28014.fpoly.nhom11_ql_bbt_coffee.Adapter.GridViewAdapter;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.Adapter.QuanLyDoUongAdapter;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.Adapter.adapterSideshow;

import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DAO.DoUongDAO;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.DoUong;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.GioHang;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.sideshow;
import me.relex.circleindicator.CircleIndicator;


public class HomeFragment extends Fragment {
    LinearLayout btnTea, btnCoffee, btnSmoothie, btnOther;
    DoUongDAO dao;
    ArrayList<DoUong> list;
    ArrayList<DoUong> listAll;
    GridViewAdapter adapter;
    QuanLyDoUongAdapter doUongAdapter;
    GridView gv;
    EditText edTimKiem;
    TextView tv_entry;
    DoUong item;

    ViewPager viewPager;
    List<sideshow> list_sideshow;
    CircleIndicator circleIndicator;
    binhntph28014.fpoly.nhom11_ql_bbt_coffee.Adapter.adapterSideshow adapterSideshow;
    Timer timer;

    public static ArrayList<GioHang> listGioHang;


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
        listAll = new ArrayList<>();
        dao = new DoUongDAO(getActivity());

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

        if(dao.getLoai("1").size() != 0) {
            listAll.addAll((ArrayList<DoUong>) dao.getLoai("1"));
            //get nhu
        }
        if(dao.getLoai("2").size() != 0) {
            listAll.addAll((ArrayList<DoUong>) dao.getLoai("2"));
        }
        if(dao.getLoai("3").size() != 0) {
            listAll.addAll((ArrayList<DoUong>) dao.getLoai("3"));
        }
        if(dao.getLoai("4").size() != 0) {
            listAll.addAll((ArrayList<DoUong>) dao.getLoai("4"));
        }
        ArrayList <DoUong> listSearch = new ArrayList<>();
        edTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listSearch.clear();
                tv_entry.setVisibility(View.VISIBLE);
                for (int i = 0; i < listAll.size(); i++) {
                    if(listAll.get(i).getTenDoUong().contains(edTimKiem.getText()) && edTimKiem.getText().length() != 0) {
                        listSearch.add(listAll.get(i));
                        tv_entry.setVisibility(View.GONE);
                    }
                }
                adapter = new GridViewAdapter(getActivity(), HomeFragment.this, listSearch);
                gv.setAdapter(adapter);
            }
        });

        //nếu giỏ hàng đã có dữ liệu thì k cần tạo mảng mới
        if (listGioHang != null){

        }else { //tạo mảng mới cho giỏ hàng
            listGioHang = new ArrayList<>();
        }


        listAll = (ArrayList<DoUong>) dao.getTop10();
        adapter = new GridViewAdapter(getActivity(), HomeFragment.this, listAll);
        gv.setAdapter(adapter);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                item = listAll.get(i);
                if (item.getTrangThai()==1){
                    openDialog(getActivity());
                }else {
                    Toast.makeText(getContext(), "Đồ uống này đã hết, vui lòng chọn đồ uống khác!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnTea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listAll = (ArrayList<DoUong>) dao.getLoai("2");
                adapter = new GridViewAdapter(getActivity(), HomeFragment.this, listAll);
                gv.setAdapter(adapter);
            }
        });
        btnCoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listAll = (ArrayList<DoUong>) dao.getLoai("1");
                adapter = new GridViewAdapter(getActivity(), HomeFragment.this, listAll);
                gv.setAdapter(adapter);
            }
        });
        btnSmoothie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listAll = (ArrayList<DoUong>) dao.getLoai("3");
                adapter = new GridViewAdapter(getActivity(), HomeFragment.this, listAll);
                gv.setAdapter(adapter);
            }
        });
        btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listAll = (ArrayList<DoUong>) dao.getLoai("4");
                adapter = new GridViewAdapter(getActivity(), HomeFragment.this, listAll);
                gv.setAdapter(adapter);
            }
        });
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer!= null){
            timer.cancel();
            timer = null;
        }
    }
    //end hàm sideshow

    public void openDialog(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_chi_tiet_do_uong, null);
        builder.setView(view);

        TextView tvTen = view.findViewById(R.id.tvTenDoUongChiTiet);
        TextView tvGia = view.findViewById(R.id.tvGiaChiTiet);
        TextView tvSoLuong = view.findViewById(R.id.tvSoLuong);
        TextView btnCong = view.findViewById(R.id.btnCongSL);
        TextView btnTru = view.findViewById(R.id.btnTruSL);
        ImageView imgAnh = view.findViewById(R.id.imgAnhChiTiet);

        tvTen.setText(item.getTenDoUong());
        DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
        tvGia.setText(decimalFormat.format(item.getGiaTien())+" VND");
        byte[] hinhAnh = item.getHinhAnh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh, 0, hinhAnh.length);
        imgAnh.setImageBitmap(bitmap);

        btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuongMoi = Integer.parseInt(tvSoLuong.getText().toString())+1;
                if (soLuongMoi > 9){
                    btnCong.setVisibility(view.INVISIBLE);
                    btnTru.setVisibility(view.VISIBLE);
                    tvSoLuong.setText(soLuongMoi+"");
                }else {
                    btnCong.setVisibility(view.VISIBLE);
                    btnTru.setVisibility(view.VISIBLE);
                    tvSoLuong.setText(soLuongMoi+"");
                }
            }
        });
        if (Integer.parseInt(tvSoLuong.getText().toString()) <2){
            btnCong.setVisibility(view.VISIBLE);
            btnTru.setVisibility(view.INVISIBLE);
        }
        btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuongMoi = Integer.parseInt(tvSoLuong.getText().toString())-1;
                if (soLuongMoi <2){
                    btnCong.setVisibility(view.VISIBLE);
                    btnTru.setVisibility(view.INVISIBLE);
                    tvSoLuong.setText(soLuongMoi+"");
                }else {
                    btnCong.setVisibility(view.VISIBLE);
                    btnTru.setVisibility(view.VISIBLE);
                    tvSoLuong.setText(soLuongMoi+"");
                }
            }
        });

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (listGioHang.size()> 0){
                    int sl = Integer.parseInt(tvSoLuong.getText().toString());
                    boolean check = false;
                    for (int i1=0; i1<listGioHang.size(); i1++){
                        if (listGioHang.get(i1).getMaDoUong() == item.getMaDoUong()){
                            listGioHang.get(i1).setSoLuong( listGioHang.get(i1).getSoLuong() + sl);
                            listGioHang.get(i1).setTongTien( listGioHang.get(i1).getGiaTien() * listGioHang.get(i1).getSoLuong());
                            check=true;
                        }

                    }
                    if (check == false){
                        int soLuong = Integer.parseInt(tvSoLuong.getText().toString());
                        int tongTien = soLuong*item.getGiaTien();
                        listGioHang.add(new GioHang(item.getMaDoUong(), item.getTenDoUong(), item.getGiaTien(), tongTien, sl, item.getHinhAnh()));

                    }
                }else {
                    int sl = Integer.parseInt(tvSoLuong.getText().toString());
                    int tongTien = sl * item.getGiaTien();
                    listGioHang.add(new GioHang(item.getMaDoUong(), item.getTenDoUong(), item.getGiaTien(), tongTien, sl, item.getHinhAnh()));

                }
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();

    }


}