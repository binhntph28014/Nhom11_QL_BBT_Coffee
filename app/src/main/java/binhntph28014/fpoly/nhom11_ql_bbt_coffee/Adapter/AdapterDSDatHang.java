package binhntph28014.fpoly.nhom11_ql_bbt_coffee.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DAO.DoUongDAO;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.DatDoUong;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.DoUong;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.R;

public class AdapterDSDatHang extends ArrayAdapter {
    private Context context;
    private ArrayList<DatDoUong> list;
    TextView tvTenDoUong, tvSoLuong, tvGiaTien, tvTongTien;



    public AdapterDSDatHang(@NonNull Context context, ArrayList<DatDoUong> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_item_ds_dat_hang,null);

        }
        final DatDoUong item = list.get(position);

        if(view != null){

            tvTenDoUong = view.findViewById(R.id.tvTenDoUong);
            tvSoLuong = view.findViewById(R.id.tvSoLuong);
            tvGiaTien = view.findViewById(R.id.tvGiaTien);
            tvTongTien = view.findViewById(R.id.tvTongTien);

            DoUongDAO doUongDAO = new DoUongDAO(context);
            DoUong doUong = doUongDAO.getID(String.valueOf(item.getMaDoUong()));
            tvTenDoUong.setText(""+doUong.getTenDoUong());

            DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
            tvSoLuong.setText(""+item.getSoLuong());
            tvGiaTien.setText(""+decimalFormat.format(doUong.getGiaTien()));
            tvTongTien.setText(""+decimalFormat.format(item.getTongTien()));


        }
        return view;
    }
}
