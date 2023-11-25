package binhntph28014.fpoly.nhom11_ql_bbt_coffee.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.DatDoUong;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DbHelper.DbHelper;

public class DatDoUongDAO {
    SQLiteDatabase db ;

    public DatDoUongDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
//    public long insertDatDoUong(DatDoUong obj){
//        ContentValues values = new ContentValues();
//        values.put("maDoUong", obj.getMaDoUong());
//        values.put("tongTien", obj.getTongTien());
//        values.put("maHD", obj.getMaHD());
//        values.put("soLuong", obj.getSoLuong());
//        return db.insert("datDoUong", null, values);
//    }
    public long insertDatDoUong(DatDoUong obj){
        String sql = "insert into datDoUong values(null, ?, ?, ?, ?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, String.valueOf(obj.getMaDoUong()));
        statement.bindString(2, String.valueOf(obj.getTongTien()));
        statement.bindString(3, String.valueOf(obj.getMaHD()));
        statement.bindString(4, String.valueOf(obj.getSoLuong()));
        statement.bindBlob(5, obj.getHinhAnh());
        return statement.executeInsert();
    }
    public int updateDatDoUong(DatDoUong obj){
        ContentValues values = new ContentValues();
        values.put("maDoUong", obj.getMaDoUong());
        values.put("tongTien", obj.getTongTien());
        values.put("maHD", obj.getMaHD());
        values.put("soLuong", obj.getSoLuong());
        values.put("hinhAnh", obj.getHinhAnh());
        return db.update("datDoUong",values, "maDatDoUong=?", new String[]{obj.getMaDatDoUong()+""});
    }
    public int deleteDatDoUong(String id){
        return db.delete("datDoUong", "maDatDoUong=?",new String[]{id});
    }

    @SuppressLint("Range")
    private List<DatDoUong> getData(String sql, String...selectionArgs){
        List<DatDoUong> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            DatDoUong obj = new DatDoUong();
            obj.setMaDatDoUong(Integer.parseInt(c.getString(c.getColumnIndex("maDatDoUong"))));
            obj.setMaDoUong(Integer.parseInt(c.getString(c.getColumnIndex("maDoUong"))));
            obj.setTongTien(Integer.parseInt(c.getString(c.getColumnIndex("tongTien"))));
            obj.setMaHD(Integer.parseInt(c.getString(c.getColumnIndex("maHD"))));
            obj.setSoLuong(Integer.parseInt(c.getString(c.getColumnIndex("soLuong"))));
            list.add(obj);
        }
        return list;
    }

    public List<DatDoUong> getAll(){
        String sql = "select * from datDoUong";
        return getData(sql);
    }

    public DatDoUong getID(String id){
        String sql = "select * from datDoUong where maDatDoUong=?";
        List<DatDoUong> list = getData(sql, id);
        return list.get(0);
    }
    public List<DatDoUong> getMaHoaDon(String maHD){
        String sql = "select * from datDoUong where maHD=?";
        return getData(sql, maHD);
    }

}
