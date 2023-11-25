package binhntph28014.fpoly.nhom11_ql_bbt_coffee.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.GioHang;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.Fragment.GioHangFragment;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.HomeFragment;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.R;

public class AdapterGioHang extends BaseAdapter {
    Context context;
    ArrayList<GioHang> listGioHang;

    public AdapterGioHang(Context context, ArrayList<GioHang> listGioHang) {
        this.context = context;
        this.listGioHang = listGioHang;
    }



    @Override
    public int getCount() {
        return listGioHang.size();
    }

    @Override
    public Object getItem(int position) {
        return listGioHang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public ImageView imgAnh;
        public TextView tvTen, tvTongTien, tvCong, tvTru, tvSoLuong;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_item_dat_do_uong, null);

            viewHolder.tvTen = (TextView) view.findViewById(R.id.tvTenDoUong);
            viewHolder.tvTongTien = (TextView) view.findViewById(R.id.tvTongSP);
            viewHolder.tvCong = (TextView) view.findViewById(R.id.tvCongSL);
            viewHolder.tvTru = (TextView) view.findViewById(R.id.tvTruSL);
            viewHolder.tvSoLuong = (TextView) view.findViewById(R.id.tvSoLuong);
            viewHolder.imgAnh = (ImageView) view.findViewById(R.id.imgDatDoUong);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        GioHang item = (GioHang) getItem(position);
        viewHolder.tvTen.setText(item.getTenDoUong());
        DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
        viewHolder.tvTongTien.setText(decimalFormat.format(item.getTongTien())+" VND");
        viewHolder.tvSoLuong.setText(item.getSoLuong()+"");

        //chuyen byte[] sang bitMap
        byte[] hinhAnh = item.getHinhAnh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh, 0, hinhAnh.length);
        viewHolder.imgAnh.setImageBitmap(bitmap);

        int sl = Integer.parseInt(viewHolder.tvSoLuong.getText().toString());
        if (sl >= 10){
            viewHolder.tvCong.setVisibility(view.INVISIBLE);
            viewHolder.tvTru.setVisibility(view.VISIBLE);
        }else if (sl <= 1){
            viewHolder.tvCong.setVisibility(view.VISIBLE);
            viewHolder.tvTru.setVisibility(view.INVISIBLE);
        }else if (sl >= 1){
            viewHolder.tvCong.setVisibility(view.VISIBLE);
            viewHolder.tvTru.setVisibility(view.VISIBLE);
        }

        ViewHolder finalViewHolder = viewHolder;
        viewHolder.tvCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuongMoi = Integer.parseInt(finalViewHolder.tvSoLuong.getText().toString())+1;
                int soLuongHT = HomeFragment.listGioHang.get(position).getSoLuong();
                int giaHT = HomeFragment.listGioHang.get(position).getTongTien();
                HomeFragment.listGioHang.get(position).setSoLuong(soLuongMoi);
                int giaMoi = (giaHT*soLuongMoi)/soLuongHT;
                HomeFragment.listGioHang.get(position).setTongTien(giaMoi);
                finalViewHolder.tvTongTien.setText(decimalFormat.format(giaMoi)+" VND");
                GioHangFragment.tinhTong();
                if (soLuongMoi > 9){
                    finalViewHolder.tvCong.setVisibility(v.INVISIBLE);
                    finalViewHolder.tvTru.setVisibility(v.VISIBLE);
                    finalViewHolder.tvSoLuong.setText(soLuongMoi+"");
                }else {
                    finalViewHolder.tvCong.setVisibility(v.VISIBLE);
                    finalViewHolder.tvTru.setVisibility(v.VISIBLE);
                    finalViewHolder.tvSoLuong.setText(soLuongMoi+"");
                }

            }
        });
        viewHolder.tvTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuongMoi = Integer.parseInt(finalViewHolder.tvSoLuong.getText().toString())-1;
                int soLuongHT = HomeFragment.listGioHang.get(position).getSoLuong();
                int giaHT = HomeFragment.listGioHang.get(position).getTongTien();
                HomeFragment.listGioHang.get(position).setSoLuong(soLuongMoi);
                int giaMoi = (giaHT*soLuongMoi)/soLuongHT;
                HomeFragment.listGioHang.get(position).setTongTien(giaMoi);
                finalViewHolder.tvTongTien.setText(decimalFormat.format(giaMoi)+" VND");
                GioHangFragment.tinhTong();
                if (soLuongMoi < 2){
                    finalViewHolder.tvCong.setVisibility(v.VISIBLE);
                    finalViewHolder.tvTru.setVisibility(v.INVISIBLE);
                    finalViewHolder.tvSoLuong.setText(soLuongMoi+"");
                }else {
                    finalViewHolder.tvCong.setVisibility(v.VISIBLE);
                    finalViewHolder.tvTru.setVisibility(v.VISIBLE);
                    finalViewHolder.tvSoLuong.setText(soLuongMoi+"");
                }
            }
        });
        return view;
    }

}
