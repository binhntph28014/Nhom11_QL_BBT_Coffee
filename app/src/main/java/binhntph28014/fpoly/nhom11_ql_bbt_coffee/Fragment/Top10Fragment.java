package binhntph28014.fpoly.nhom11_ql_bbt_coffee.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.Adapter.AdapterTop10;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DAO.ThongKeDAO;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.DTO.Top10;
import binhntph28014.fpoly.nhom11_ql_bbt_coffee.R;

public class Top10Fragment extends Fragment {
    ListView lv;
    ArrayList<Top10> list;
    AdapterTop10 adapterTop10;
    ThongKeDAO dao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_10,container,false);
        lv = view.findViewById(R.id.lvTop);
        dao  = new ThongKeDAO(getActivity());
        list = (ArrayList<Top10>) dao.getTop();
        adapterTop10 = new AdapterTop10(getActivity(), this, list);
        lv.setAdapter(adapterTop10);



        return  view;

    }
}
