package binhntph28014.fpoly.nhom11_ql_bbt_coffee.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DAO.NhanVienDAO;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.NhanVien;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.Fragment.QuanLyNhanVienFragment;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.R;

public class AdapterQuanLyNhanVien extends ArrayAdapter {
    private Context context;
    QuanLyNhanVienFragment fragment;
    private ArrayList<NhanVien> list;
    TextView tvHoTen , tvSDT , tvNamSinh , tvGioiTinh , tvMaNV , tvTrangThai;
    ImageView imgChart;
    public AdapterQuanLyNhanVien(@NonNull Context context, QuanLyNhanVienFragment fragment, ArrayList<NhanVien> list) {
        super(context, 0 , list);
        this.context = context;
        this.list =list;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_item_them_nhan_vien, null);
        }
        final NhanVien item = list.get(position);
        if(view != null){
            NhanVienDAO nhanVienDAO = new NhanVienDAO(context);
            NhanVien nhanVien = nhanVienDAO.getID(String.valueOf(item.getMaNV()));


            tvHoTen = view.findViewById(R.id.tvHoTen);
            tvSDT = view.findViewById(R.id.tvSDT);
            tvNamSinh = view.findViewById(R.id.tvNamSinh);
            tvGioiTinh = view.findViewById(R.id.tvGioiTinh);
            tvMaNV = view.findViewById(R.id.tvMaNV);
            tvTrangThai = view.findViewById(R.id.tvTrangThai);
            imgChart = view.findViewById(R.id.imgChart);

            tvHoTen.setText("Họ tên : " + item.getHoTen());
            tvSDT.setText("Số điện thoại : " + item.getSdt());
            tvNamSinh.setText("Năm sinh : " + item.getNamSinh());
            tvMaNV.setText("Mã nhân viên : " + item.getMaNV());

            if(item.getTrangThai() == 1){
                tvTrangThai.setText("Đi làm");
                tvTrangThai.setTextColor(Color.BLUE);
            }else{
                tvTrangThai.setText("Nghỉ làm");
                tvTrangThai.setTextColor(Color.RED);
            }

            if(item.getGioiTinh() == 1){
                tvGioiTinh.setText("Giới tính : Nam " );
            }else{
                tvGioiTinh.setText("Giới tính : Nữ " );
            }

//            imgChart.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    fragment.bieuDoDoanhSo(getContext(), item);
//
//                }
//            });

        }

        return view;
    }
}
