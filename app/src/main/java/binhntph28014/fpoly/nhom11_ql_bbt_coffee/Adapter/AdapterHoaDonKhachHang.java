package binhntph28014.fpoly.nhom11_ql_bbt_coffee.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DAO.NhanVienDAO;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.HoaDon;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.NhanVien;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.R;


public class AdapterHoaDonKhachHang extends ArrayAdapter {
    private Context context;
    private ArrayList<HoaDon> list;
    TextView tvMaHD, tvTenNhanVien, tvNgayXuat, tvTongTien;



    public AdapterHoaDonKhachHang(@NonNull Context context, ArrayList<HoaDon> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_item_hoa_don_khach_hang,null);

        }
        final HoaDon item = list.get(position);

        if(view != null){

            tvMaHD = view.findViewById(R.id.tvMaHD);
            tvTenNhanVien = view.findViewById(R.id.tvTenNhanVien);
            tvNgayXuat = view.findViewById(R.id.tvNgayXuat);
            tvTongTien = view.findViewById(R.id.tvTongTien);

            tvMaHD.setText(item.getMaHD()+"");

            NhanVienDAO nhanVienDAO = new NhanVienDAO(context);
            NhanVien nv = nhanVienDAO.getID(String.valueOf(item.getMaNV()));
            tvTenNhanVien.setText(nv.getHoTen());

            DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
            tvTongTien.setText(""+decimalFormat.format(item.getTongTien()));

            tvNgayXuat.setText(sdf.format(item.getNgayXuat()));

        }
        return view;
    }
}
