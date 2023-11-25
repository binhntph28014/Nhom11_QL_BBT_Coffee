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
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DAO.KhachHangDAO;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DAO.NhanVienDAO;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.DatDoUong;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.GioHang;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.HoaDon;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.KhachHang;
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

    ListView lvKhachHang;
    ArrayList<KhachHang> list;
    KhachHangDAO khachHangDAO;
    KhachHang khachHang;


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
        khachHangDAO = new KhachHangDAO(getActivity());

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


        if (HomeFragment.listGioHang.size() > 0) {

            if (!chkThanhToan.isChecked()) {
                Toast.makeText(getContext(), "Chưa thanh toán", Toast.LENGTH_SHORT).show();
            } else {
                //hiện dialog để thêm khách hàng
                openDialog(getContext());
            }
        } else {
            Toast.makeText(getContext(), "Giỏ hàng của bạn chưa có sản phẩm", Toast.LENGTH_SHORT).show();
        }
    }

    private void xoaSP() {
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

    public static void tinhTong() {
        int tongTien = 0;
        for (int i = 0; i < HomeFragment.listGioHang.size(); i++) {
            tongTien += HomeFragment.listGioHang.get(i).getTongTien();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
        tvTongTien.setText("Tổng tiền: " + decimalFormat.format(tongTien) + " VND");
    }

    public int tongTien() {
        int tongTien = 0;
        for (int i = 0; i < HomeFragment.listGioHang.size(); i++) {
            tongTien += HomeFragment.listGioHang.get(i).getTongTien();
        }
        return tongTien;
    }

    public void openDialog(final Context context) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_khachhang);
        //dialog khach hàng

        edMaKhachHang = dialog.findViewById(R.id.edMaKhachHang);
        edHoTenKhachHang = dialog.findViewById(R.id.edHoTenKhachHang);
        edSDTKhachHang = dialog.findViewById(R.id.edSDTKhachHang);
        edNamSinhKhachHang = dialog.findViewById(R.id.edNamSinhKhachHang);

        btnLuuKhachHang = dialog.findViewById(R.id.btnLuuKhachHang);
        btnCheckKhachHang = dialog.findViewById(R.id.btnHuyKhachHang);

        rdoNamKH = dialog.findViewById(R.id.rdoNamKH);
        rdoNuKH = dialog.findViewById(R.id.rdoNuKH);

        rdgGioiTinhKhạchHang = dialog.findViewById(R.id.rdgGioiTinhKhạchHang);

        edMaKhachHang.setEnabled(false);

        btnCheckKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edSDTKhachHang.getText().toString().isEmpty()) {
                    edSDTKhachHang.setError("Không được để trống");
                } else {
                    if (khachHangDAO.checkSdt(edSDTKhachHang.getText().toString().trim()) > 0) {
                        khachHang = khachHangDAO.getSDT(edSDTKhachHang.getText().toString().trim());
                        edHoTenKhachHang.setText(khachHang.getHoTen());
                        edNamSinhKhachHang.setText(khachHang.getNamSinh() + "");
                        edMaKhachHang.setText(khachHang.getMaKH() + "");
                        if (khachHang.getGioiTinh() == 1) {
                            rdoNamKH.setChecked(true);
                        } else {
                            rdoNuKH.setChecked(true);
                        }
                    } else {
                        Toast.makeText(context, "Khách hàng này chưa tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnLuuKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate() > 0) {
                    if (khachHangDAO.checkSdt(edSDTKhachHang.getText().toString().trim()) > 0) {
//                        Toast.makeText(context, "Số điện thoại này đã được đăng ký, vui lòng click 'Kiểm tra'! ", Toast.LENGTH_SHORT).show();
                        khachHang = khachHangDAO.getSDT(edSDTKhachHang.getText().toString().trim());
                        edHoTenKhachHang.setText(khachHang.getHoTen());
                        edNamSinhKhachHang.setText(khachHang.getNamSinh() + "");
                        edMaKhachHang.setText(khachHang.getMaKH() + "");
                        if (khachHang.getGioiTinh() == 1) {
                            rdoNamKH.setChecked(true);
                        } else {
                            rdoNuKH.setChecked(true);
                        }
                        //insertHD
                        insertHD_DaCoKH();
                        chkThanhToan.setChecked(false);
                    } else {
                        insertHD_ChuaCoKH();
                        chkThanhToan.setChecked(false);
                    }

                    dialog.dismiss();
                }
            }
        });
        dialog.show();

    }

    private void insertHD_DaCoKH() {
        //tạo hóa đơn
        hoaDon = new HoaDon();
        //maKH
        hoaDon.setMaKH(Integer.parseInt(edMaKhachHang.getText().toString()));
        //maNV
        layMaNhanVien();
        //trangThai
        if (chkThanhToan.isChecked()) {
            hoaDon.setTrangThai(1);
        } else {
            hoaDon.setTrangThai(0);
        }
        //ngayXuat
        layNgayHT();

        hoaDon.setTongTien(tongTien()); //tongTien
        if (hoaDonDAO.insertHoaDon(hoaDon) > 0) {
            Log.d("zzzz", "insert hoa don thanh cong");
            for (int i = 0; i < HomeFragment.listGioHang.size(); i++) {
                datDoUong = new DatDoUong();
                datDoUong.setMaDoUong(HomeFragment.listGioHang.get(i).getMaDoUong());
                hoaDon = hoaDonDAO.getHDLast();
                datDoUong.setMaHD(hoaDon.getMaHD());
                datDoUong.setSoLuong(HomeFragment.listGioHang.get(i).getSoLuong());
                datDoUong.setTongTien(HomeFragment.listGioHang.get(i).getTongTien());
                datDoUong.setHinhAnh(HomeFragment.listGioHang.get(i).getHinhAnh());
                if (datDoUongDAO.insertDatDoUong(datDoUong) > 0) {
                    Log.d("zzzz", "insert dat do uong thanh cong");
                }
            }
        }
        HomeFragment.listGioHang.removeAll(HomeFragment.listGioHang);
        adapter = new AdapterGioHang(getActivity(), HomeFragment.listGioHang);
        lv.setAdapter(adapter);
        tinhTong();

    }

    private void insertHD_ChuaCoKH() {
        khachHang = new KhachHang();
        khachHang.setHoTen(edHoTenKhachHang.getText().toString());
        khachHang.setSdt(edSDTKhachHang.getText().toString());
        khachHang.setNamSinh(edNamSinhKhachHang.getText().toString());

        if (rdoNamKH.isChecked()) {
            khachHang.setGioiTinh(1);
        } else {
            khachHang.setGioiTinh(0);
        }
        if (khachHangDAO.insertKhachHang(khachHang) > 0) {
            //tạo hóa đơn
            hoaDon = new HoaDon();
            khachHang = khachHangDAO.getKHLast(); //maKH
            hoaDon.setMaKH(khachHang.getMaKH());
            //maNV
            layMaNhanVien();
            if (chkThanhToan.isChecked()) { //trangThai
                hoaDon.setTrangThai(1);
            } else {
                hoaDon.setTrangThai(0);
            }
            //ngayXuat
            layNgayHT();

            hoaDon.setTongTien(tongTien()); //tongTien
            if (hoaDonDAO.insertHoaDon(hoaDon) > 0) {
                Log.d("zzzz", "insert hoa don thanh cong");
                for (int i = 0; i < HomeFragment.listGioHang.size(); i++) {
                    datDoUong = new DatDoUong();
                    datDoUong.setMaDoUong(HomeFragment.listGioHang.get(i).getMaDoUong());
                    hoaDon = hoaDonDAO.getHDLast();
                    datDoUong.setMaHD(hoaDon.getMaHD());
                    datDoUong.setSoLuong(HomeFragment.listGioHang.get(i).getSoLuong());
                    datDoUong.setTongTien(HomeFragment.listGioHang.get(i).getTongTien());
                    datDoUong.setHinhAnh(HomeFragment.listGioHang.get(i).getHinhAnh());
                    if (datDoUongDAO.insertDatDoUong(datDoUong) > 0) {
                        Log.d("zzzz", "insert dat do uong thanh cong");
                    }
                }
            }
            HomeFragment.listGioHang.removeAll(HomeFragment.listGioHang);
            adapter = new AdapterGioHang(getActivity(), HomeFragment.listGioHang);
            lv.setAdapter(adapter);
            tinhTong();
        } else {
            Toast.makeText(getContext(), "Tạo đơn hàng không thành công", Toast.LENGTH_SHORT).show();
        }

    }


    private void layNgayHT() {
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

    private void layMaNhanVien() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
        String sdt = sharedPreferences.getString("SDT", "");
        NhanVienDAO nhanVienDAO = new NhanVienDAO(getContext());
        nhanVien = nhanVienDAO.getSDT(sdt);
        hoaDon.setMaNV(nhanVien.getMaNV());//maNV
    }

    public int validate() {
        int check = 1;
        if (edHoTenKhachHang.getText().toString().isEmpty() || edNamSinhKhachHang.getText().toString().isEmpty() || edSDTKhachHang.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Phải nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            try {
                Integer.parseInt(edNamSinhKhachHang.getText().toString().trim());
                edNamSinhKhachHang.setError(null);
            } catch (Exception e) {
                edNamSinhKhachHang.setError("Phải là số");
                check = -1;
            }
            try {
                Integer.parseInt(edSDTKhachHang.getText().toString().trim());
                edSDTKhachHang.setError(null);
            } catch (Exception e) {
                edSDTKhachHang.setError("Phải là số");
                check = -1;
            }
            if (!rdoNamKH.isChecked() && !rdoNuKH.isChecked()) {
                Toast.makeText(getContext(), "Vui lòng chọn giới tính", Toast.LENGTH_SHORT).show();
                return check = -1;
            }
        }
        return check;
    }

}
