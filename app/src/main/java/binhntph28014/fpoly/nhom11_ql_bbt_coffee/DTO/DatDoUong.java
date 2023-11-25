package binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO;

public class DatDoUong {
    private  int maDatDoUong;
    private int maDoUong;
    private int tongTien;
    private int maHD;
    private int soLuong;
    private byte[] hinhAnh;

    public byte[] getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public DatDoUong(int tongTien) {
        this.tongTien = tongTien;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public int getMaDatDoUong() {
        return maDatDoUong;
    }

    public void setMaDatDoUong(int maDatDoUong) {
        this.maDatDoUong = maDatDoUong;
    }

    public int getMaDoUong() {
        return maDoUong;
    }

    public void setMaDoUong(int maDoUong) {
        this.maDoUong = maDoUong;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public DatDoUong(int maDatDoUong, int maDoUong, int tongTien, int maHD, int soLuong, byte[] hinhAnh) {
        this.maDatDoUong = maDatDoUong;
        this.maDoUong = maDoUong;
        this.tongTien = tongTien;
        this.maHD = maHD;
        this.soLuong = soLuong;
        this.hinhAnh = hinhAnh;
    }

    public DatDoUong() {
    }
}
