package binhntph28014.fpoly.nhom11_ql_bbt_coffee.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.KhachHang;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DbHelper.DbHelper;


public class KhachHangDAO {
    SQLiteDatabase db;

    public KhachHangDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }


    public long insertKhachHang(KhachHang obj){
        ContentValues values = new ContentValues();
        values.put("hoTen", obj.getHoTen());
        values.put("sdt", obj.getSdt());
        values.put("namSinh", obj.getNamSinh());
        values.put("gioiTinh", obj.getGioiTinh());
        return db.insert("khachHang", null, values);
    }
//    public long insertKH(KhachHang obj){
//        String sql = "select maKH from khachHang";
//        ContentValues values = new ContentValues();
//        values.put("hoTen", obj.getHoTen());
//        values.put("sdt", obj.getSdt());
//        values.put("namSinh", obj.getNamSinh());
//        values.put("gioiTinh", obj.getGioiTinh());
//        if (db.insert("khachHang", null, values) >0){
//            return db.insert("khachHang", null, values);
//        }
//    }
    public int updateKhachHang(KhachHang obj){
        ContentValues values = new ContentValues();
        values.put("hoTen", obj.getHoTen());
        values.put("sdt", obj.getSdt());
        values.put("namSinh", obj.getNamSinh());
        values.put("gioiTinh", obj.getGioiTinh());
        return  db.update("khachHang", values, "maKH=?", new String[]{obj.getMaKH()+""});
    }
    public int deleteKhachHang(String id){
        return db.delete("khachHang", "maKH=?", new String[]{id});
    }

    @SuppressLint("Range")
    private List<KhachHang> getData(String sql, String...selectionArgs){
        List<KhachHang> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            KhachHang obj = new KhachHang();
            obj.setMaKH(Integer.parseInt(c.getString(c.getColumnIndex("maKH"))));
            obj.setHoTen(c.getString(c.getColumnIndex("hoTen")));
            obj.setSdt(c.getString(c.getColumnIndex("sdt")));
            obj.setNamSinh(c.getString(c.getColumnIndex("namSinh")));
            obj.setGioiTinh(Integer.parseInt(c.getString(c.getColumnIndex("gioiTinh"))));
            list.add(obj);
        }
        return list;
    }

    public List<KhachHang> getAll(){
        String sql = "SELECT khachHang.maKH as maKH, khachHang.hoTen as hoTen, khachHang.sdt as sdt, khachHang.namSinh as namSinh, khachHang.gioiTinh as gioiTinh\n" +
                " FROM khachHang JOIN hoaDon on khachHang.maKH = hoaDon.maKH\n" +
                " GROUP by khachHang.maKH\n" +
                " ORDER by sum(hoaDon.tongTien) DESC";
        return getData(sql);
    }


    public KhachHang getID(String id){
        String sql = "select * from khachHang where maKH=?";
        List<KhachHang> list = getData(sql,id);
        return list.get(0);
        //////
///
        ///
    }
    public KhachHang getKHLast(){
        String sql = "SELECT * FROM khachHang ORDER BY maKH DESC LIMIT 1";
        List<KhachHang> list = getData(sql);
        return list.get(0);
    }
    public int checkSdt(String Sdt){
        String sql = "select * from khachHang where sdt=?";
        List<KhachHang> list = getData(sql, Sdt);
        if(list.size() == 0){
            return -1;
        }
        return 1;
    }
    public KhachHang getSDT(String sdt){
        String sql = "select * from khachHang where sdt=?";
        List<KhachHang> list = getData(sql,sdt);
        return list.get(0);
    }
}
