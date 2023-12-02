package binhntph28014.fpoly.nhom11_ql_bbt_coffee.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DAO.NhanVienDAO;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DAO.ThongKeDAO;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.DoanhThu;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.NhanVien;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.R;


public class DoanhSoFragment extends Fragment  {
    LineChart lineChart;
    ArrayList<DoanhThu> list;
    @Nullable
// doanh số
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_doanh_so_fragment, container, false);
        lineChart = view.findViewById(R.id.lineChartDoanhSo);

        LineDataSet lineDataSet = new LineDataSet(dataValues(),"Doanh số");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        LineData data = new LineData(lineDataSet);
        lineChart.setData(data);

        lineChart.getAxisRight().setEnabled(false);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setGranularity(1.0f);

        lineDataSet.setColors( Color.RED);
        lineDataSet.setValueTextColor(Color.BLACK);
        lineDataSet.setValueTextSize(18f);

        lineChart.invalidate(); //refesh
        return view;
    }

    private List<Entry> dataValues() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
        String sdt = sharedPreferences.getString("SDT", "");
        NhanVienDAO nhanVienDAO = new NhanVienDAO(getContext());
        NhanVien nhanVien = nhanVienDAO.getSDT(sdt);

        ThongKeDAO thongKeDAO = new ThongKeDAO(getContext());
        list = (ArrayList<DoanhThu>) thongKeDAO.getDoanhSoNV(String.valueOf(nhanVien.getMaNV()));

        List<Entry> dataValue = new ArrayList<>();
        dataValue.add(new Entry(0,0));

        for (int i=0; i<list.size(); i++){
            int thang = list.get(i).getThang();
            int tongTien = list.get(i).getTongTien();
            dataValue.add(new Entry(list.get(i).getThang(), list.get(i).getTongTien()));
            Log.d("zzzzz", "dataValues: "+ thang+ " tổng: "+ tongTien);
        }


        return dataValue;
    }
}
