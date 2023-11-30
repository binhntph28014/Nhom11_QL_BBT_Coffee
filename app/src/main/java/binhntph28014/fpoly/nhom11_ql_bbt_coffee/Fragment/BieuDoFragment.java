package binhntph28014.fpoly.nhom11_ql_bbt_coffee.Fragment;

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

import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DAO.ThongKeDAO;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.DoanhThu;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.R;


//biểu đồ

public class BieuDoFragment extends Fragment  {
    LineChart lineChart;
    ArrayList<DoanhThu> list;
    @Nullable


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_bieu_do_fragment, container, false);
        lineChart = view.findViewById(R.id.lineChart);

        LineDataSet lineDataSet = new LineDataSet(dataValues(),"Doanh thu");
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
        ThongKeDAO thongKeDAO = new ThongKeDAO(getContext());
        list = (ArrayList<DoanhThu>) thongKeDAO.getBieuDo();

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
