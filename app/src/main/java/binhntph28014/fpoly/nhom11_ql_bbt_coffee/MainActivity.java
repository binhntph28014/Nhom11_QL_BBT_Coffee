package binhntph28014.fpoly.nhom11_ql_bbt_coffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import binhntph28014.fpoly.nhom11_ql_bbt_coffee.Fragment.BieuDoFragment;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.Fragment.DoanhSoFragment;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.Fragment.DoanhThuFragment;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.Fragment.DoiMatKhauFragment;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.Fragment.GioHangFragment;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.Fragment.QuanLyDoUongFragment;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.Fragment.QuanLyHoaDonFragment;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.Fragment.QuanLyKhachHangFragment;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.Fragment.QuanLyNhanVienFragment;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.Fragment.Top10Fragment;


public class MainActivity extends AppCompatActivity {
    NavigationView navigationView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    TextView tvTitle;
    private String mSDT="";
    ImageView imgCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        toolbar = findViewById(R.id.toolbar);
        imgCart = findViewById(R.id.imgCart);

        tvTitle = findViewById(R.id.tvTitle);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);

        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        bar.setHomeAsUpIndicator(R.drawable.ic_menu);
        bar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String sdt = intent.getStringExtra("user");

        if (sdt.equalsIgnoreCase("admin")){
            navigationView.getMenu().findItem(R.id.nav_DoanhThu).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_BieuDoDoanhThu).setVisible(true);
            navigationView.getMenu().findItem(R.id.navNhanVien).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_DoanhSoNhanVien).setVisible(false);
        }


        FragmentManager manager = getSupportFragmentManager();
        HomeFragment homeFragment = new HomeFragment();
        manager.beginTransaction().replace(R.id.flContent, homeFragment).commit();
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GioHangFragment gioHangFragment = new GioHangFragment();
                manager.beginTransaction().replace(R.id.flContent, gioHangFragment).commit();
                tvTitle.setText("Giỏ hàng");
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager manager = getSupportFragmentManager();
                int id = item.getItemId();
                    if (id == R.id.navHome) {
                        tvTitle.setText("Trang chủ");
                        setTitle("BBT Cofffee");
                        HomeFragment mainActivity2 = new HomeFragment();
                        manager.beginTransaction().replace(R.id.flContent, mainActivity2).commit();
                    }else if(id == R.id.navNhanVien) {
                        tvTitle.setText("Quản lý nhân viên");
                        setTitle("Quản lý nhân viên");
                        QuanLyNhanVienFragment quanLyNhanVienFragment = new QuanLyNhanVienFragment();
                        manager.beginTransaction().replace(R.id.flContent, quanLyNhanVienFragment).commit();
                    }else if (id == R.id.navKhachHang) {
                        tvTitle.setText("Quản lý khách hàng");
                        setTitle("Quản lý nhân viên");
                        QuanLyKhachHangFragment quanLyKhachHangFragment = new QuanLyKhachHangFragment();
                        manager.beginTransaction().replace(R.id.flContent, quanLyKhachHangFragment).commit();
                    }else if (id == R.id.navDoUong) {
                        tvTitle.setText("Quản lý đồ uống");
                        setTitle("Quản lý đồ uống");
                        QuanLyDoUongFragment quanLyDoUongFragment = new QuanLyDoUongFragment();
                        manager.beginTransaction().replace(R.id.flContent, quanLyDoUongFragment).commit();
                    }else if (id == R.id.nav_DoanhThu) {
                        tvTitle.setText("Doanh thu");
                        setTitle("Doanh thu");
                        DoanhThuFragment doanhThuFragment = new DoanhThuFragment();
                        manager.beginTransaction().replace(R.id.flContent, doanhThuFragment).commit();
                    }else if (id == R.id.nav_Top10) {
                        tvTitle.setText("Top 10 đồ uống ");
                        setTitle("Quản lý đồ uống");
                        Top10Fragment top10Fragment = new Top10Fragment();
                        manager.beginTransaction().replace(R.id.flContent, top10Fragment).commit();
                    }else if (id == R.id.navHoaDon) {
                        tvTitle.setText("Quản lý hóa đơn");
                        setTitle("Quản lý hóa đơn");
                        QuanLyHoaDonFragment quanLyHoaDonFragment = new QuanLyHoaDonFragment();
                        manager.beginTransaction().replace(R.id.flContent, quanLyHoaDonFragment).commit();
                    }else if (id == R.id.nav_DoiMatKhau) {
                        tvTitle.setText("Đổi mật khẩu");
                        setTitle("Đổi mật khẩu");
                        DoiMatKhauFragment doiMatKhauFragment = new DoiMatKhauFragment();
                        manager.beginTransaction().replace(R.id.flContent, doiMatKhauFragment).commit();

                    }else if (id == R.id.nav_DangXuat) {
                        startActivity(new Intent(MainActivity.this, ManHinhDangNhap.class));
                    }else if (id == R.id.nav_BieuDoDoanhThu) {
                        tvTitle.setText("Biểu đồ doanh thu");
                        setTitle("Biểu đồ");
                        BieuDoFragment bieuDoFragment = new BieuDoFragment();
                        manager.beginTransaction().replace(R.id.flContent, bieuDoFragment).commit();
                    }else if (id == R.id.nav_DoanhSoNhanVien){
                        tvTitle.setText("Doanh số");
                        DoanhSoFragment doanhSoFragment = new DoanhSoFragment();
                        manager.beginTransaction().replace(R.id.flContent, doanhSoFragment).commit();
                }
                drawerLayout.closeDrawers();
                return false;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}