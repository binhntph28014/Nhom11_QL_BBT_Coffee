package binhntph28014.fpoly.nhom11_ql_bbt_coffee.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;


import binhntph28014.fpoly.bbt_coffee.DbHelper.DbHelper;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.DoUong;

public class DoUongDAO {
    SQLiteDatabase db;

    public DoUongDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertDoUong(DoUong obj){
        ContentValues values = new ContentValues();
        values.put("maLoai", obj.getMaLoai());
        values.put("tenDoUong", obj.getTenDoUong());
        values.put("giaTien", obj.getGiaTien());
        values.put("trangThai", obj.getTrangThai());
        values.put("hinhAnh", obj.getHinhAnh());
        return db.insert("doUong", null, values);
    }
    public long insertDU(DoUong obj){
        String sql = "insert into doUong values(null, ?, ?, ?, ?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, String.valueOf(obj.getMaLoai()));
        statement.bindString(2, obj.getTenDoUong());
        statement.bindString(3, String.valueOf(obj.getGiaTien()));
        statement.bindString(4, String.valueOf(obj.getTrangThai()));
        statement.bindBlob(5, obj.getHinhAnh());
        return statement.executeInsert();
    }

    public int updateDoUong(DoUong obj){
        ContentValues values = new ContentValues();
        values.put("maLoai", obj.getMaLoai());
        values.put("tenDoUong", obj.getTenDoUong());
        values.put("giaTien", obj.getGiaTien());
        values.put("trangThai", obj.getTrangThai());
        values.put("hinhAnh", obj.getHinhAnh());
        return db.update("doUong", values, "maDoUong=?", new String[]{obj.getMaDoUong()+""});
    }
    public int deleteDoUong(String id){
        return db.delete("doUong","maDoUong=?",new String[]{id});
    }

    @SuppressLint("Range")
    private List<DoUong> getData(String sql, String...selectionArgs){
        List<DoUong> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            DoUong obj = new DoUong();
            obj.setMaDoUong(Integer.parseInt(c.getString(c.getColumnIndex("maDoUong"))));
            obj.setMaLoai(Integer.parseInt(c.getString(c.getColumnIndex("maLoai"))));
            obj.setTenDoUong(c.getString(c.getColumnIndex("tenDoUong")));
            obj.setGiaTien(Integer.parseInt(c.getString(c.getColumnIndex("giaTien"))));
            obj.setTrangThai(Integer.parseInt(c.getString(c.getColumnIndex("trangThai"))));
            obj.setHinhAnh(c.getBlob(c.getColumnIndex("hinhAnh")));
            list.add(obj);
        }
        return list;
    }
    public List<DoUong> getAll(){
        String sql = "select * from doUong";
        return getData(sql);
    }

    public DoUong getID(String id){
        String sql = "select * from doUong where maDoUong=?";
        List<DoUong> list = getData(sql, id);
        return list.get(0);
    }
    public List<DoUong> getLoai(String maLoai){
        String sql = "select * from doUong where maLoai=?";
        List<DoUong> list = getData(sql, maLoai);
        return list;
    }

    public List<DoUong> getTop10(){
        String sql = "select doUong.maDoUong, doUong.tenDoUong, doUong.maLoai, doUong.giaTien, doUong.trangThai, doUong.hinhAnh, sum(datDoUong.soLuong) as SL " +
                "from doUong join datDoUong on doUong.maDoUong= datDoUong.maDoUong\n" +
                " group by doUong.maDoUong order by sum(datDoUong.soLuong) desc limit 10";
        List<DoUong> list = getData(sql, null);
        return list;
    }
}
