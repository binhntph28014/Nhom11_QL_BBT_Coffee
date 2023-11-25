package binhntph28014.fpoly.nhom11_ql_bbt_coffee.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import binhntph28014.fpoly.nhom11_ql_bbt_coffee.Adapter.AdapterGioHang;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DAO.DatDoUongDAO;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DAO.HoaDonDAO;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DAO.NhanVienDAO;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.DatDoUong;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.GioHang;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.HoaDon;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.NhanVien;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.HomeFragment;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.R;


public class GioHangFragment extends Fragment {
    ListView lv;
    static TextView tvTongTien;
    Button btnTaoDonHang;
    CheckBox chkThanhToan;
    AdapterGioHang adapter;
    GioHang gioHang;

    NhanVien nhanVien;
    NhanVienDAO nhanVienDAO;

    DatDoUongDAO datDoUongDAO;
    DatDoUong datDoUong;

    HoaDonDAO hoaDonDAO;
    HoaDon hoaDon;


    EditText edMaKhachHang, edHoTenKhachHang, edSDTKhachHang, edNamSinhKhachHang;
    RadioButton rdoNamKH, rdoNuKH;
    Button btnLuuKhachHang, btnCheckKhachHang;
    RadioGroup rdgGioiTinhKhạchHang;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gio_hang, container, false);
        lv = view.findViewById(R.id.lvGioHang);
        tvTongTien = view.findViewById(R.id.tvTongTien);
        chkThanhToan = view.findViewById(R.id.chkThanhToan);
        btnTaoDonHang = view.findViewById(R.id.btnTaoDonHang);
        adapter = new AdapterGioHang(getActivity(), HomeFragment.listGioHang);
        lv.setAdapter(adapter);

        datDoUongDAO = new DatDoUongDAO(getActivity());
        hoaDonDAO = new HoaDonDAO(getActivity());
        nhanVienDAO = new NhanVienDAO(getActivity());

        btnTaoDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taoDonHang();
            }
        });

        xoaSP();
        tinhTong();
        return view;
    }

    private void taoDonHang() {


        if (HomeFragment.listGioHang.size() >0){

            if (!chkThanhToan.isChecked()){
                Toast.makeText(getContext(), "Chưa thanh toán", Toast.LENGTH_SHORT).show();
            }else {
                //hiện dialog để thêm khách hàng
                //
            }
        }else {
            Toast.makeText(getContext(), "Giỏ hàng của bạn chưa có sản phẩm", Toast.LENGTH_SHORT).show();
        }
    }

    private void xoaSP(){
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này?");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                            HomeFragment.listGioHang.remove(position);
                            adapter.notifyDataSetChanged();
                            lv.setAdapter(adapter);
                            tinhTong();
                    }
                });
                builder.show();
                return true;
            }
        });
    }
    public static void tinhTong(){
        int tongTien =0;
        for (int i = 0; i<HomeFragment.listGioHang.size(); i++){
            tongTien+= HomeFragment.listGioHang.get(i).getTongTien();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
        tvTongTien.setText("Tổng tiền: "+decimalFormat.format(tongTien)+" VND");
    }
    public int tongTien(){
        int tongTien =0;
        for (int i = 0; i<HomeFragment.listGioHang.size(); i++){
            tongTien+= HomeFragment.listGioHang.get(i).getTongTien();
        }
        return tongTien;
    }


    private void layNgayHT(){
        //lấy ngày hiện tại
        Date date = new Date(System.currentTimeMillis());
        DateFormat dateFormat = new DateFormat();
        String ngayHT = (String) dateFormat.format("yyyy-MM-dd", date);
        //chuyển chuỗi ngày thành Date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date objNgay = format.parse(ngayHT);
            hoaDon.setNgayXuat(objNgay);
//                String ngayXuat = (String) dateFormat.format("yyyy-MM-dd", objNgay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private void layMaNhanVien(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
        String sdt = sharedPreferences.getString("SDT", "");
        NhanVienDAO nhanVienDAO = new NhanVienDAO(getContext());
        nhanVien = nhanVienDAO.getSDT(sdt);
        hoaDon.setMaNV(nhanVien.getMaNV());//maNV
    }

    public int validate(){
        int check = 1;
        if (edHoTenKhachHang.getText().toString().isEmpty() || edNamSinhKhachHang.getText().toString().isEmpty() || edSDTKhachHang.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Phải nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }else {
            try {
                Integer.parseInt(edNamSinhKhachHang.getText().toString().trim());
                edNamSinhKhachHang.setError(null);
            }catch (Exception e){
                edNamSinhKhachHang.setError("Phải là số");
                check = -1;
            }
            try {
                Integer.parseInt(edSDTKhachHang.getText().toString().trim());
                edSDTKhachHang.setError(null);
            }catch (Exception e){
                edSDTKhachHang.setError("Phải là số");
                check = -1;
            }
            if(!rdoNamKH.isChecked() && !rdoNuKH.isChecked()){
                Toast.makeText(getContext(), "Vui lòng chọn giới tính", Toast.LENGTH_SHORT).show();
                return check = -1;
            }
        }
        return check;
    }

}
