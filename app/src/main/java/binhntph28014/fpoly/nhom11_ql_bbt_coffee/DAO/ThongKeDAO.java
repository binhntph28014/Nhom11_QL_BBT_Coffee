package binhntph28014.fpoly.nhom11_ql_bbt_coffee.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.DoUong;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.DoanhThu;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.Top10;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DbHelper.DbHelper;


public class ThongKeDAO {
    private SQLiteDatabase db;
    private Context context;

    public ThongKeDAO(Context context){
        this.context = context;
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }



    //thong ke top 10
    @SuppressLint("Range")
    public List<Top10> getTop(){
        String sqlTop = "select maDoUong, sum(soLuong) as soLuong from datDoUong group by maDoUong order by soLuong desc limit 10 ";
        List<Top10> list = new ArrayList<>();
        DoUongDAO doUongDAO = new DoUongDAO(context);
        Cursor c = db.rawQuery(sqlTop,null);
        while (c.moveToNext()){
            Top10 top10 = new Top10();
            DoUong doUong = doUongDAO.getID(c.getString(c.getColumnIndex("maDoUong")));
            top10.tenDoUong = doUong.getTenDoUong();
            top10.soLuong = Integer.parseInt(c.getString(c.getColumnIndex("soLuong")));
            list.add(top10);
        }
        return list;
    }

    @SuppressLint("Range")
    public int getDoanhThu(String tuNgay, String denNgay){
        String sqlDoanhThu = "select sum(tongTien) as doanhThu from hoaDon where ngayXuat between ? and ?";
        List<Integer> list = new ArrayList<>();
        Cursor c = db.rawQuery(sqlDoanhThu, new String[]{tuNgay,denNgay});
        while (c.moveToNext()){
            try{
                list.add(Integer.parseInt(c.getString(c.getColumnIndex("doanhThu"))));
            } catch (NumberFormatException e) {
                list.add(0);
            }
        }
        return list.get(0);
    }


    @SuppressLint("Range")
    public List<DoanhThu> getBieuDo(){
        String sqlDoanhThu = "select strftime('%m', ngayXuat) as thang, sum(tongTien) as doanhThu from hoaDon GROUP by strftime('%m', ngayXuat)";
        List<DoanhThu> list = new ArrayList<>();
        Cursor c = db.rawQuery(sqlDoanhThu,null);
        while (c.moveToNext()){
            try{
                DoanhThu doanhThu = new DoanhThu();
                doanhThu.setThang(Integer.parseInt(c.getString(c.getColumnIndex("thang"))));
                doanhThu.setTongTien(Integer.parseInt(c.getString(c.getColumnIndex("doanhThu"))));
                list.add(doanhThu);
            } catch (NumberFormatException e) {
                list.add(new DoanhThu(0,0));
            }
        }
        return list;
    }



    public int getCount(String sql, String ... Args){
        int count = 0;
        Cursor c = db.rawQuery(sql, Args);
        if (c.moveToFirst()){
            count = c.getInt(0);
        }
        c.close();
        return count;
    }

    @SuppressLint("Range")
    public List<Top10> getTop10TheoThang(String thang){
        String sqlTop = "SELECT datDoUong.maDoUong as doUongTheoThang,sum(datDoUong.soLuong) as Top10TheoThang from datDoUong join hoaDon on datDoUong.maHD = hoaDon.maHD " +
                "WHERE strftime('%m', hoaDon.ngayXuat)=? GROUP by datDoUong.maDoUong ORDER by datDoUong.soLuong DESC LIMIT 10";
        List<Top10> list = new ArrayList<>();
        DoUongDAO doUongDAO = new DoUongDAO(context);
        Cursor c = db.rawQuery(sqlTop,new String[]{thang});
        while (c.moveToNext()){
            Top10 top10 = new Top10();
            @SuppressLint("Range") DoUong doUong = doUongDAO.getID(c.getString(c.getColumnIndex("doUongTheoThang")));
            top10.tenDoUong = doUong.getTenDoUong();
            top10.soLuong = Integer.parseInt(c.getString(c.getColumnIndex("Top10TheoThang")));
            list.add(top10);
        }
        return list;
    }

    @SuppressLint("Range")
    public List<DoanhThu> getDoanhSoNV( String maNV){
        String sqlDoanhThu = "select strftime('%m', ngayXuat) as thang, sum(tongTien) as doanhThu from hoaDon WHERE maNV=? GROUP by strftime('%m', ngayXuat)";
        List<DoanhThu> list = new ArrayList<>();
        Cursor c = db.rawQuery(sqlDoanhThu,new String[]{maNV});
        while (c.moveToNext()){
            try{
                DoanhThu doanhThu = new DoanhThu();
                doanhThu.setThang(Integer.parseInt(c.getString(c.getColumnIndex("thang"))));
                doanhThu.setTongTien(Integer.parseInt(c.getString(c.getColumnIndex("doanhThu"))));
                list.add(doanhThu);
            } catch (NumberFormatException e) {
                list.add(new DoanhThu(0,0));
            }
        }
        return list;
    }
}
