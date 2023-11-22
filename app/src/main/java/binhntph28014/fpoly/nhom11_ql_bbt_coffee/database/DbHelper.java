package binhntph28014.fpoly.nhom11_ql_bbt_coffee.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {


    public DbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    //tao bang thu thu
        String createTableThuThu="create table ThuThu" +
                "(maTT TEXT PRIMARY KEY, " +
                "hoTen TEXT NOT NULL, " +
                "matKhau TEXT NOT NULL)";
        db.execSQL(createTableThuThu);

        String insertThuThuMau="insert into ThuThu values('hau', 'NguyenDucHau', '123')";
        db.execSQL(insertThuThuMau);

        String insertThuThuMau2="insert into ThuThu values('admin', 'Nguyen Duc Hau', '123')";
        db.execSQL(insertThuThuMau2);
        //tao bang thanh vien
        String createTableThanhVien=
                "create table ThanhVien(" +
                        "maTV INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "hoTen TEXT NOT NULL, " +
                        "namSinh TEXT NOT NULL)";
        db.execSQL(createTableThanhVien);

        //tao bang loai sach
        String createTableLoaiSach=
                "create table LoaiSach(" +
                        "maLoai INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "tenLoai TEXT NOT NULL)";
        db.execSQL(createTableLoaiSach);

        //tao bang sach
        String createTableSach =
                "create table Sach(" +
                        "maSach INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "tenSach TEXT NOT NULL," +
                        "giaThue INTEGER NOT NULL, " +
                        "maLoai INTEGER REFERENCES LoaiSach(maLoai), " +
                        "soTrang INTEGER NOT NULL)";

        db.execSQL(createTableSach);


        //tao bang phieu muon
        String createTablePhieuMuon=
                "create table PhieuMuon(" +
                        "maPM INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "maTT TEXT REFERENCES ThuThu(maTT)," +
                        "maTV INTEGER REFERENCES ThanhVien(maTV)," +
                        "maSach INTEGER REFERENCES Sach(maSach)," +
                        "tienThue INTEGER NOT NULL," +
                        "ngay DATE NOT NULL," +
                        "traSach INTEGER NOT NULL)";

        db.execSQL(createTablePhieuMuon);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String dropTableLoaiThuThu="drop table if exists ThuThu";
        db.execSQL(dropTableLoaiThuThu);

        String dropTableThanhVien="drop table if exists ThanhVien";
        db.execSQL(dropTableThanhVien);

        String dropTableLoaiSach="drop table if exists LoaiSach";
        db.execSQL(dropTableLoaiSach);

        String dropTableSach="drop table if exists Sach";
        db.execSQL(dropTableSach);

        String dropTablePhieuMuon="drop table if exists PhieuMuon";
        db.execSQL(dropTablePhieuMuon);

        onCreate(db);
    }
}
