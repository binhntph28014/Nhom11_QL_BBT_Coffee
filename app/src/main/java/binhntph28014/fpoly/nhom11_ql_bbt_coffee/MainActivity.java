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
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import binhntph28014.fpoly.nhom11_ql_bbt_coffee.Fragment.QuanLyDoUongFragment;


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

        tvTitle = findViewById(R.id.tvTitle);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);

        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        bar.setHomeAsUpIndicator(R.drawable.ic_menu);
        bar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String sdt = intent.getStringExtra("user");



        FragmentManager manager = getSupportFragmentManager();
        HomeFragment homeFragment = new HomeFragment();
        manager.beginTransaction().replace(R.id.flContent, homeFragment).commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager manager = getSupportFragmentManager();
                switch (item.getItemId()){
                    case R.id.navHome:
                        tvTitle.setText("Trang chủ");
                        setTitle("BBT Cofffee");
                        HomeFragment mainActivity2 = new HomeFragment();
                        manager.beginTransaction().replace(R.id.flContent, mainActivity2).commit();
                        break;
                    case R.id.navNhanVien:
                        tvTitle.setText("Quản lý nhân viên");
                        break;
                    case R.id.navKhachHang:
                        tvTitle.setText("Quản lý khách hàng");
                        break;
                    case R.id.navDoUong:
                        tvTitle.setText("Quản lý đồ uống");
                        setTitle("Quản lý đồ uống");
                        QuanLyDoUongFragment quanLyDoUongFragment = new QuanLyDoUongFragment();
                        manager.beginTransaction().replace(R.id.flContent, quanLyDoUongFragment).commit();
                        break;
                    case R.id.nav_DoanhThu:
                        tvTitle.setText("Doanh thu");
                        break;
                    case R.id.nav_Top10:
                        tvTitle.setText("Top 10 đồ uống ");
                        break;
                    case R.id.navHoaDon:
                        tvTitle.setText("Quản lý hóa đơn");
                        break;
                    case R.id.nav_DoiMatKhau:
                        tvTitle.setText("Đổi mật khẩu");
                        break;
                    case R.id.nav_DangXuat:
                        startActivity( new Intent(MainActivity.this, ManHinhDangNhap.class));
                        break;
                    case R.id.nav_BieuDoDoanhThu:
                        tvTitle.setText("Biểu đồ doanh thu");
                        break;
                    case R.id.nav_DoanhSoNhanVien:
                        tvTitle.setText("Doanh số");
                        break;
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