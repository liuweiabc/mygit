package com.example.xuh.shopping;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.xuh.shopping.adapter.AllCommodityAdapter;
import com.example.xuh.shopping.adapter.StudentAdapter;
import com.example.xuh.shopping.bean.Commodity;
import com.example.xuh.shopping.bean.Student;
import com.example.xuh.shopping.util.CommodityDbHelper;
import com.example.xuh.shopping.util.StudentDbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link designerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class designerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView lv;
    StudentDbHelper dbHelper;
    StudentAdapter adapter;
    List<Student> Students = new ArrayList<>();

    public designerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment designerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static designerFragment newInstance(String param1, String param2) {
        designerFragment fragment = new designerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_designer, container, false);
        lv = view.findViewById(R.id.lv_all_commodity);
        dbHelper = new StudentDbHelper(getActivity().getApplicationContext(), StudentDbHelper.DB_NAME, null, 1);
        adapter = new StudentAdapter(getActivity().getApplicationContext());
        Students = dbHelper.readAllStudents();
        adapter.setData(Students);
        lv.setAdapter(adapter);
        return view;
    }
}