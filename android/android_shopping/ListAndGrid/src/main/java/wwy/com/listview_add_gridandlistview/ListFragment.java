package wwy.com.listview_add_gridandlistview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import wwy.com.R;
import wwy.com.adapter.ListViewAdapter;
import wwy.com.bean.Group;
import wwy.com.bean.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View rootView;

    private ListView mListView;
    private ListViewAdapter mListViewAdapter;
    private ArrayList<ArrayList<HashMap<String,Object>>> mArrayList;
    private List<Group> list = new ArrayList<>();

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
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
       rootView = inflater.inflate(R.layout.fragment_list, container, false);
        init(rootView);
        return rootView;
    }
    private void init(View view){
        mListView = view.findViewById(R.id.listView1);
        initDataList();
        initData();
        mListViewAdapter=new ListViewAdapter(mArrayList, ListFragment.this.getActivity(),list);
        mListView.setAdapter(mListViewAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ListFragment.this.getActivity(),"点击了：" + i,Toast.LENGTH_SHORT).show();
                Log.i("mListView","------------> " + i);
            }
        });
    }
    private void initDataList() {
        for (int i = 0; i < 10; i++) {
            Group group = new Group();
            group.setGroupName("张三" + i);
            group.setGropAge("" + (i + 10));
            List<User> users = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                User user = new User();
                user.setUserAge(i + j);
                user.setUserName("李四" + i );
                users.add(user);
            }
            group.setMembers(users);
            list.add(group);
        }
    }
    private void initData(){
        mArrayList=new ArrayList<ArrayList<HashMap<String,Object>>>();
        HashMap<String, Object> hashMap=null;
        ArrayList<HashMap<String,Object>> arrayListForEveryGridView;

        for (int i = 0; i < 10; i++) {
            arrayListForEveryGridView=new ArrayList<HashMap<String,Object>>();
            //for (int j = 0; j < 5; j++) {
            hashMap=new HashMap<String, Object>();
            hashMap.put("content", "i="+i+" ,j="+0);
            arrayListForEveryGridView.add(hashMap);
            //}
            mArrayList.add(arrayListForEveryGridView);
        }

    }

}