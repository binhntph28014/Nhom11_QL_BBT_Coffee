package binhntph28014.fpoly.nhom11_ql_bbt_coffee.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import binhntph28014.fpoly.nhom11_ql_bbt_coffee.Adapter.AdapterQuanLyNhanVien;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DAO.NhanVienDAO;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.NhanVien;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.R;

public class QuanLyNhanVienFragment extends Fragment {
    ListView lv;
    ArrayList<NhanVien> list;
    FloatingActionButton fabNhanVien;
    Dialog dialog;
    EditText edMaNV  , edHoTen , edSDT , edNamSinh , edMatKhau;
    CheckBox chkTrangThai;
    RadioButton rdoNam, rdoNu;
    RadioGroup rdgGioiTinh;
    Button btnLuuNhanVien , btnHuyNhanVien;

    static NhanVienDAO dao;
    AdapterQuanLyNhanVien adapter;
    NhanVien item;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_them_nhan_vien , container , false);
        lv = view.findViewById(R.id.lvNhanVien);
        fabNhanVien = view.findViewById(R.id.fabNhanVien);
        dao = new NhanVienDAO(getActivity());
        capNhapLv();
        fabNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(), 0);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                item = list.get(i);
                openDialog(getActivity(), 1);
                return false;
            }
        });


        return view;
    }
    public void openDialog(final Context context , final int type) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_them_nhan_vien);

        edMaNV = dialog.findViewById(R.id.edMaNV);
        edHoTen = dialog.findViewById(R.id.edHoTen);
        edSDT = dialog.findViewById(R.id.edSDT);
        edNamSinh = dialog.findViewById(R.id.edNamSinh);
        edMatKhau = dialog.findViewById(R.id.edMatKhau);

        btnHuyNhanVien = dialog.findViewById(R.id.btnHuyNhanVien);
        btnLuuNhanVien = dialog.findViewById(R.id.btnLuuNhanVien);

        chkTrangThai = dialog.findViewById(R.id.chkTrangThai);

        rdoNam = dialog.findViewById(R.id.rdoNam);
        rdoNu = dialog.findViewById(R.id.rdoNu);

        rdgGioiTinh = dialog.findViewById(R.id.rdgGioiTinh);

        edMaNV.setEnabled(false);

        if (type != 0) {
            edSDT.setText(item.getSdt()+"");
            edMaNV.setText(String.valueOf(item.getMaNV()));
            edNamSinh.setText(item.getNamSinh()+"");
            edMatKhau.setText(item.getMatKhau());
            edHoTen.setText(item.getHoTen());
            if (item.getGioiTinh() == 1) {
                rdoNam.setChecked(true);
            } else {
                rdoNu.setChecked(true);
            }
            if (item.getTrangThai() == 1) {
                chkTrangThai.setChecked(true);
            } else {
                chkTrangThai.setChecked(false);
            }
            edMatKhau.setEnabled(false);
        }
        btnHuyNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnLuuNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item = new NhanVien();
                item.setHoTen(edHoTen.getText().toString());
                item.setMatKhau(edMatKhau.getText().toString());
                item.setNamSinh(edNamSinh.getText().toString());
                item.setSdt(edSDT.getText().toString());
                if (rdoNam.isChecked()) {
                    item.setGioiTinh(1);
                } else {
                    item.setGioiTinh(0);
                }
                if (chkTrangThai.isChecked()) {
                    item.setTrangThai(1);
                } else {
                    item.setTrangThai(0);
                }
                if (validate() > 0) {
                    if (type == 0) {
                        if(dao.checkSdt(edSDT.getText().toString().trim()) > 0){
                            edSDT.setError("Số điện thoại này đã tồn tại");
                            return;
                        }else {
                            if (dao.insertNhanVien(item) > 0) {
                                Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Thêm không thành công", Toast.LENGTH_SHORT).show();
                            }
                        }

                    } else {
                        item.setMaNV(Integer.parseInt(edMaNV.getText().toString()));
                        if (dao.updateNhanVien(item) > 0) {
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(context, "Sửa không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                    capNhapLv();
                    dialog.dismiss();
                }

            }

        });
        dialog.show();
    }
    void capNhapLv(){
        list = (ArrayList<NhanVien>) dao.getAll();
        adapter = new AdapterQuanLyNhanVien(getActivity() , this, list);
        lv.setAdapter(adapter);
    }
    public int validate(){
        int check = 1;
        if (edHoTen.getText().toString().isEmpty() || edNamSinh.getText().toString().isEmpty()
                || edMatKhau.getText().toString().isEmpty() || edSDT.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Phải nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }else {
            try {
                Integer.parseInt(edSDT.getText().toString().trim());
                edSDT.setError(null);
            }catch (Exception e){
                edSDT.setError("Phải là số");
                check = -1;
            }

            try {
                Integer.parseInt(edNamSinh.getText().toString().trim());
                edNamSinh.setError(null);
            }catch (Exception e){
                edNamSinh.setError("Phải là số");
                check = -1;
            }
            if(!rdoNam.isChecked() && !rdoNu.isChecked()){
                Toast.makeText(getContext(), "Vui lòng chọn giới tính", Toast.LENGTH_SHORT).show();
                return check = -1;
            }

        }
        return check;
    }

}
