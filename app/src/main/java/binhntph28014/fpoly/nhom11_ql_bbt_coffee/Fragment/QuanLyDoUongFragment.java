package binhntph28014.fpoly.nhom11_ql_bbt_coffee.Fragment;


import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import binhntph28014.fpoly.nhom11_ql_bbt_coffee.Adapter.QuanLyDoUongAdapter;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DAO.DoUongDAO;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.DoUong;


public class QuanLyDoUongFragment extends Fragment {
    FloatingActionButton fabDoUong;
    ListView lvDoUong;
    Dialog dialog;
    ArrayList<DoUong> list;
    DoUong item;
    DoUongDAO dao;
    QuanLyDoUongAdapter adapter;

    EditText edMaDoUong, edTenDoUong, edGiaTien;
    CheckBox chkTrangThai;
    Spinner spnLoai;
    ImageView imgCamera, imgFileUpload, imgAnh;
    Button btnLuu, btnHuy;

    LoaiDAO loaiDAO;
    ArrayList<Loai> listLoai;
    AdapterLoaiSpinner adapterLoaiSpinner;
    int maLoai;

    final int requestCodeCamera = 123;
    final int requestCodeFolder =456;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_do_uong, container, false);
        fabDoUong = view.findViewById(R.id.fabDoUong);
        lvDoUong = view.findViewById(R.id.lvDoUong);

        dao = new DoUongDAO(getActivity());
        capNhatLv();
        fabDoUong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(getActivity(), 0);
            }
        });
        lvDoUong.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = list.get(position);
                openDialog(getActivity(), 1);
                return false;
            }
        });
        return view;
    }

    protected void openDialog(final Context context, final int type){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_them_do_uong);
        edMaDoUong = dialog.findViewById(R.id.edMaDoUong);
        edTenDoUong = dialog.findViewById(R.id.edTenDoUong);
        edGiaTien = dialog.findViewById(R.id.edGia);
        chkTrangThai = dialog.findViewById(R.id.chkTrangThai);
        spnLoai = dialog.findViewById(R.id.spnMaLoai);
        imgCamera = dialog.findViewById(R.id.imgCamera);
        imgFileUpload = dialog.findViewById(R.id.imgFile);
        imgAnh = dialog.findViewById(R.id.imgAnh);
        btnHuy = dialog.findViewById(R.id.btnHuyDoUong);
        btnLuu = dialog.findViewById(R.id.btnLuuDoUong);

        loaiDAO = new LoaiDAO(context);
        listLoai = new ArrayList<Loai>();
        listLoai = (ArrayList<Loai>) loaiDAO.getAll();
        adapterLoaiSpinner = new AdapterLoaiSpinner(context, listLoai);
        spnLoai.setAdapter(adapterLoaiSpinner);
        spnLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maLoai = listLoai.get(position).getMaLoai();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        edMaDoUong.setEnabled(false);
        if (type!=0){
            edMaDoUong.setText(item.getMaDoUong()+"");
            edTenDoUong.setText(item.getTenDoUong());
            edGiaTien.setText(item.getGiaTien()+"");
            if (item.getTrangThai() == 1){
                chkTrangThai.setChecked(true);
            }else {
                chkTrangThai.setChecked(false);
            }

            byte[] hinhAnh = item.getHinhAnh();
            Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh, 0, hinhAnh.length);
            imgAnh.setImageBitmap(bitmap);


            int position = 0;
            for (int i=0; i<listLoai.size(); i++){
                if (item.getMaLoai() == listLoai.get(i).getMaLoai()){
                    position = i;
                }
            }
            spnLoai.setSelection(position);
        }


        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, 100);
                }else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, requestCodeCamera);
                }
//                    ActivityCompat.requestPermissions(getActivity(),
//                            new String[]{Manifest.permission.CAMERA},
//                            requestCodeCamera);


            }
        });
        imgFileUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, 100);
                }else {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, requestCodeFolder);
                }

//                    ActivityCompat.requestPermissions(getActivity(),
//                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                            requestCodeFolder);


            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chuyển data imageview sang byte[]
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgAnh.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                byte[] hinhAnh = byteArray.toByteArray();


                item = new DoUong();
                item.setTenDoUong(edTenDoUong.getText().toString().trim());
                item.setGiaTien(Integer.parseInt(edGiaTien.getText().toString().trim()));
                if (chkTrangThai.isChecked()){
                    item.setTrangThai(1);
                }else {
                    item.setTrangThai(0);
                }
                item.setMaLoai(maLoai);
                item.setHinhAnh(hinhAnh);

                if (validate() >0){
                    if (type ==0){
                        if (dao.insertDU(item) >0){
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        item.setMaDoUong(Integer.parseInt(edMaDoUong.getText().toString()));
                        if (dao.updateDoUong(item) >0){
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                capNhatLv();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public int validate(){
        int check = 1;
        if (edTenDoUong.getText().toString().isEmpty() || edGiaTien.getText().toString().isEmpty()||imgAnh.getDrawable()==null){
            Toast.makeText(getContext(), "Phải nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        else if ( edTenDoUong.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Phải nhập Tên đồ uống", Toast.LENGTH_SHORT).show();
            check = -1;

        }else if ( edGiaTien.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Phải nhập giá tiền", Toast.LENGTH_SHORT).show();
            check = -1;

        }else if(imgAnh.getDrawable()==null){
            Toast.makeText(getContext(), "Phải chọn ảnh", Toast.LENGTH_SHORT).show();
            check = -1;

        }else {
            try {
                Integer.parseInt(edGiaTien.getText().toString().trim());
                edGiaTien.setError(null);
            } catch (Exception e) {
                edGiaTien.setError("Phải là số");
                check = -1;
            }
        }
        return check;
    }
    void capNhatLv(){
        list = (ArrayList<DoUong>) dao.getAll();
        adapter = new QuanLyDoUongAdapter(getActivity(), this, list);
        lvDoUong.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == requestCodeCamera && resultCode == RESULT_OK && data!=null ){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgAnh.setImageBitmap(bitmap);
        }
        if (requestCode == requestCodeFolder && resultCode == RESULT_OK && data!=null ){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgAnh.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode){
//            case requestCodeCamera:
//                if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(intent, requestCodeCamera);
//                }else {
//                    Toast.makeText(getContext(), "Bạn không cho phép mở camera", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case requestCodeFolder:
//                if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                    Intent intent = new Intent(Intent.ACTION_PICK);
//                    intent.setType("image/*");
//                    startActivityForResult(intent, requestCodeFolder);
//                }else {
//                    Toast.makeText(getContext(), "Bạn không cho phép truy cập thư viện", Toast.LENGTH_SHORT).show();
//                }
//                break;
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
}
