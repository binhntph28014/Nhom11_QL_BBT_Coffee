package binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO;

public class NhanVien {
    private int maNV;
    private String hoTen;
    private String sdt;
    private String namSinh;
    private int trangThai;
    private int gioiTinh;
    private String matKhau;

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(String namSinh) {
        this.namSinh = namSinh;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public int getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(int gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public NhanVien(int maNV, String hoTen, String sdt, String namSinh, int trangThai, int gioiTinh, String matKhau) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.sdt = sdt;
        this.namSinh = namSinh;
        this.trangThai = trangThai;
        this.gioiTinh = gioiTinh;
        this.matKhau = matKhau;
    }

    public NhanVien() {
    }
}
