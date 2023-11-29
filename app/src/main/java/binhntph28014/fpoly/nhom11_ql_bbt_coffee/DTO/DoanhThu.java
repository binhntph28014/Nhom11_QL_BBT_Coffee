package binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO;

public class DoanhThu {
    private int thang;
    private int tongTien;

    public DoanhThu(int thang, int tongTien) {
        this.thang = thang;
        this.tongTien = tongTien;
    }

    public int getThang() {
        return thang;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public DoanhThu() {
    }
}
