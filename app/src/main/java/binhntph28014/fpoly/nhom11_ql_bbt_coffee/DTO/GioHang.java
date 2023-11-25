package binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO;

public class GioHang {
    private int maDoUong;
    private String tenDoUong;
    private int giaTien;
    private int tongTien;
    private int soLuong;
    private byte[] hinhAnh;

    public GioHang() {
    }

    public GioHang(int maDoUong, String tenDoUong, int giaTien, int tongTien, int soLuong, byte[] hinhAnh) {
        this.maDoUong = maDoUong;
        this.tenDoUong = tenDoUong;
        this.giaTien = giaTien;
        this.tongTien = tongTien;
        this.soLuong = soLuong;
        this.hinhAnh = hinhAnh;
    }

    public String getTenDoUong() {
        return tenDoUong;
    }

    public void setTenDoUong(String tenDoUong) {
        this.tenDoUong = tenDoUong;
    }

    public int getMaDoUong() {
        return maDoUong;
    }

    public void setMaDoUong(int maDoUong) {
        this.maDoUong = maDoUong;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public byte[] getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
