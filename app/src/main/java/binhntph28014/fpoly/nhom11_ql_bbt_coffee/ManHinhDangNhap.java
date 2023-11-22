package binhntph28014.fpoly.nhom11_ql_bbt_coffee;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import binhntph28014.fpoly.bbt_coffee.DAO.NhanVienDAO;
import binhntph28014.fpoly.bbt_coffee.DTO.NhanVien;

public class ManHinhDangNhap extends AppCompatActivity {
    EditText edSDT , edPasswordT;
    Button btnDangNhap;
    CheckBox ckLuuMatKhau;
    NhanVienDAO dao;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_manhinhdangnhap);
        edSDT = findViewById(R.id.edSDT);
        edPasswordT = findViewById(R.id.edPasswordT);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        ckLuuMatKhau = findViewById(R.id.ckLuuMatKhau);

        dao = new NhanVienDAO(this);

        SharedPreferences pref = getSharedPreferences("USER_FILE" , MODE_PRIVATE);
        edSDT.setText(pref.getString("SDT" , ""));
        edPasswordT.setText(pref.getString("PASSWORD" , ""));
        ckLuuMatKhau.setChecked(pref.getBoolean("REMEMBER" , false));

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String SDT = edSDT.getText().toString();
                String matKhau = edPasswordT.getText().toString();
                if(SDT.isEmpty()){
                    edSDT.setError("Số điện thoại không được để trống");
                }else if(matKhau.isEmpty()){
                    edPasswordT.setError("Mật khẩu không được để trống");
                }else{
                    if(dao.checkLogin(SDT , matKhau) > 0){
                        NhanVien nv = dao.getLogin(SDT, matKhau);
                        if (nv.getTrangThai() == 1){
                            Toast.makeText(ManHinhDangNhap.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            rememberSDT(SDT , matKhau , ckLuuMatKhau.isChecked());
                            Intent intent = new Intent(getApplicationContext() , MainActivity.class);
                            intent.putExtra("user" , SDT);

                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(ManHinhDangNhap.this, "Tài khoản này đã bị vô hiệu hóa", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(ManHinhDangNhap.this, "Số điện thoại hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void rememberSDT(String SDT , String matKhau , boolean checked){
        SharedPreferences pref = getSharedPreferences("USER_FILE" , MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if(!checked){
            editor.clear();
        }else{
            editor.putString("SDT" , SDT);
            editor.putString("PASSWORD"  , matKhau);
            editor.putBoolean("REMEMBER"  , checked);
            Log.d("TAG", SDT);
        }
        editor.apply();
//        editor.commit();
    }

}