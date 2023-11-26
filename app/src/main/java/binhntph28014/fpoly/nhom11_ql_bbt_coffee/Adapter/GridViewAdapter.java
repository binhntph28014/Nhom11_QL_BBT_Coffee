package binhntph28014.fpoly.nhom11_ql_bbt_coffee.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.DoUong;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.HomeFragment;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.R;

public class GridViewAdapter extends BaseAdapter implements Filterable {
    private Context context;
    ArrayList<DoUong> list;
    ArrayList<DoUong> listOld;
    HomeFragment fragment;
    TextView tvTenDoUong, tvGia, tvAdd, tvAddToCart;
    ImageView imgAnh;

    public GridViewAdapter(Context context, HomeFragment fragment, ArrayList<DoUong> list) {
        this.context = context;
        this.listOld = list;
        this.fragment = fragment;
    }
    @Override
    public int getCount() {
        return listOld.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.layout_item_grid_view, null);
        imgAnh = view.findViewById(R.id.imgAnhDoUong);
        tvTenDoUong = view.findViewById(R.id.tvName);
        tvGia = view.findViewById(R.id.tvGiaDoUong);


        final DoUong item = listOld.get(i);

        byte[] hinhAnh = item.getHinhAnh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh, 0, hinhAnh.length);
        imgAnh.setImageBitmap(bitmap);

        tvTenDoUong.setText(item.getTenDoUong());
        DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
        tvGia.setText(decimalFormat.format(item.getGiaTien())+" VND");

//        tvAddToCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (HomeFragment.listGioHang.size() >0){
////                    int soLuong = ;
//                    for (int i1 = 0; i1<HomeFragment.listGioHang.size(); i1++){
//
//                    }
//                }else {
//                    GioHang gioHang = new GioHang();
//                    gioHang.setMaDoUong(item.getMaDoUong());
//                    gioHang.setTenDoUong(item.getTenDoUong());
//                    gioHang.setGiaTien(item.getGiaTien());
//                    gioHang.setTongTien(item.getGiaTien());
//                    gioHang.setSoLuong(1);
//                    gioHang.setHinhAnh(item.getHinhAnh());
//
//                    HomeFragment.listGioHang.add(gioHang);
//                }
//            }
//        });
        return view;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                    if(strSearch.isEmpty()){
                       list = listOld;
                    }else{
                        List<DoUong> listDoUong = new ArrayList<>();
                        for(DoUong doUong: listOld){
                            if(doUong.getTenDoUong().toLowerCase().contains(strSearch.toLowerCase())){
                                listDoUong.add(doUong);
                            }
                        }
                        list = (ArrayList<DoUong>) listDoUong;
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = list;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (ArrayList<DoUong>) results.values;
                notifyDataSetChanged();

            }
        };
    }
}
