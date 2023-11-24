package binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO;

public class DoUong {
    private int maDoUong;
    private int maLoai;
    private String tenDoUong;
    private int giaTien;
    private int trangThai;
    private byte[] hinhAnh;

    public DoUong(int giaTien) {
        this.giaTien = giaTien;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }

    public int getMaDoUong() {
        return maDoUong;
    }

    public void setMaDoUong(int maDoUong) {
        this.maDoUong = maDoUong;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenDoUong() {
        this.tenDoUong = tenDoUong;
        return tenDoUong;
    }

    public void setTenDoUong(String tenDoUong) {
        this.tenDoUong = tenDoUong;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public DoUong(int maDoUong, int maLoai, String tenDoUong, int giaTien, int trangThai, byte[] hinhAnh) {
        this.maDoUong = maDoUong;
        this.maLoai = maLoai;
        this.tenDoUong = tenDoUong;
        this.giaTien = giaTien;
        this.trangThai = trangThai;
        this.hinhAnh = hinhAnh;
    }

    public byte[] getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public DoUong() {
    }
}
