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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DAO.NhanVienDAO;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.HoaDon;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.NhanVien;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.Fragment.QuanLyHoaDonFragment;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.R;


public class AdapterQuanLyHoaDon extends ArrayAdapter {
    private Context context;
    QuanLyHoaDonFragment fragment;
    //
    private ArrayList<HoaDon> list;
    TextView tvMaHD , tvNhanVien , tvKhachHang , tvTongTien , tvNgayXuat , tvTrangThai;
    ImageView img;
    public AdapterQuanLyHoaDon(@NonNull Context context, QuanLyHoaDonFragment fragment, ArrayList<HoaDon> list) {
        super(context, 0 , list);
        this.context = context;
        this.list =list;
        this.fragment = fragment;
    }
    public AdapterQuanLyHoaDon(@NonNull Context context, ArrayList<HoaDon> list) {
        super(context, 0 , list);
        this.context = context;
        this.list =list;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_item_hoa_don, null);
        }
        final HoaDon item = list.get(position);
        if(view!= null){
            tvMaHD = view.findViewById(R.id.tvMaHD);
            tvNhanVien = view.findViewById(R.id.tvNhanVien);
            tvKhachHang = view.findViewById(R.id.tvKhachHang);
            tvTongTien = view.findViewById(R.id.tvTongTien);
            tvNgayXuat = view.findViewById(R.id.tvNgayXuat);
            tvTrangThai = view.findViewById(R.id.tvTrangThai);

            tvMaHD.setText("Mã hóa đơn: " + item.getMaHD());

            NhanVienDAO nhanVienDAO = new NhanVienDAO(context);
            NhanVien nv = nhanVienDAO.getID(String.valueOf(item.getMaNV()));
            tvNhanVien.setText("Tên nhân viên: " + nv.getHoTen());
            //

            DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
            tvTongTien.setText("Tổng tiền: " + decimalFormat.format(item.getTongTien())+" VND");

            tvNgayXuat.setText("Ngày xuất: " + sdf.format(item.getNgayXuat()) );

            if(item.getTrangThai() == 1){
                tvTrangThai.setText("Đã thanh toán");
                tvTrangThai.setTextColor(Color.BLUE);
            }else{
                tvTrangThai.setText("Chưa thanh toán");
                tvTrangThai.setTextColor(Color.RED);
            }


        }

        return view;
    }
}
