package binhntph28014.fpoly.nhom11_ql_bbt_coffee.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import binhntph28014.fpoly.bbt_coffee.DbHelper.DbHelper;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.NhanVien;

public class NhanVienDAO {
    SQLiteDatabase db;

    public NhanVienDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertNhanVien(NhanVien obj){
        ContentValues values = new ContentValues();
        values.put("hoTen", obj.getHoTen());
        values.put("sdt", obj.getSdt());
        values.put("namSinh", obj.getNamSinh());
        values.put("trangThai", obj.getTrangThai());
        values.put("gioiTinh", obj.getGioiTinh());
        values.put("matKhau", obj.getMatKhau());
        return db.insert("nhanVien", null, values);
    }

    public int updateNhanVien(NhanVien obj){
        ContentValues values = new ContentValues();
        values.put("hoTen", obj.getHoTen());
        values.put("sdt", obj.getSdt());
        values.put("namSinh", obj.getNamSinh());
        values.put("trangThai", obj.getTrangThai());
        values.put("gioiTinh", obj.getGioiTinh());
        values.put("matKhau", obj.getMatKhau());
        return db.update("nhanVien", values,"maNV=?", new String[]{obj.getMaNV()+""});
    }
    public int deleteNhanVien(String id){
        return db.delete("nhanVien", "maNV=?", new String[]{id});
    }

    @SuppressLint("Range")
    private List<NhanVien> getData(String sql, String...selectionArgs){
        List<NhanVien> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            NhanVien obj = new NhanVien();
            obj.setMaNV(Integer.parseInt(c.getString(c.getColumnIndex("maNV"))));
            obj.setHoTen(c.getString(c.getColumnIndex("hoTen")));
            obj.setSdt(c.getString(c.getColumnIndex("sdt")));
            obj.setGioiTinh(Integer.parseInt(c.getString(c.getColumnIndex("gioiTinh"))));
            obj.setNamSinh(c.getString(c.getColumnIndex("namSinh")));
            obj.setMatKhau(c.getString(c.getColumnIndex("matKhau")));
            obj.setTrangThai(c.getInt(c.getColumnIndex("trangThai")));
            list.add(obj);
        }
        return list;
    }

    public List<NhanVien> getAll(){
        String sql = "select * from nhanVien";
        return getData(sql);
    }

    public NhanVien getID(String id){
        String sql = "select * from nhanVien where maNV=?";
        List<NhanVien> list = getData(sql, id);
        return list.get(0);
    }
    public NhanVien getSDT(String sdt){
        String sql = "select * from nhanVien where sdt=?";
        List<NhanVien> list = getData(sql, sdt);
        return list.get(0);
    }
    public NhanVien getLogin(String user, String pass){
        String sql = "select * from NhanVien where sdt=? and matKhau=?";
        List<NhanVien> list = getData(sql, user, pass);
        return list.get(0);
    }
    public int checkLogin(String user, String pass){
        String sql = "select * from NhanVien where sdt=? and matKhau=?";
        List<NhanVien> list = getData(sql, user, pass);
        if(list.size() == 0){
            return -1;
        }
        return 1;
    }
    public int checkSdt(String Sdt){
        String sql = "select * from NhanVien where sdt=?";
        List<NhanVien> list = getData(sql, Sdt);
        if(list.size() == 0){
            return -1;
        }
        return 1;
    }
}
