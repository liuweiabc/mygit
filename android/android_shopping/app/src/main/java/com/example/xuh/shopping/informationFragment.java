package com.example.xuh.shopping;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link informationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class informationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView lv1;
    private View rootview;

    public informationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment informationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static informationFragment newInstance(String param1, String param2) {
        informationFragment fragment = new informationFragment();
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
       rootview = inflater.inflate(R.layout.fragment_information, container, false);
        lv1 = (ListView)rootview.findViewById(R.id.lv1);
        ArrayList<String> list = new ArrayList<String>();
        list.add("给您的最新动态点了一个赞");
        list.add("请问这件短袖洗了容易皱嘛");
        list.add("扫描下方二维码即可领取购物津贴");
        Myadapter1 adapter = new Myadapter1(informationFragment.this.getActivity(),list);
        lv1.setAdapter(adapter);
       return rootview;
    }
}