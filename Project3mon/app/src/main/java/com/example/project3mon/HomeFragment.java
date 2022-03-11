package com.example.project3mon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView rcvData, rcvData_2, rcvData_3, rcvData_4;
    private UserAdapter userAdapter, userAdapter_2, userAdapter_3, userAdapter_4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle bundle = getArguments();
        if(bundle == null){
            return;
        }
        int roleID = (int) bundle.get("roleID");
      // Toast.makeText(getActivity(), "roleID:" + roleID, Toast.LENGTH_SHORT).show();
        try {
            userAdapter = new UserAdapter(getActivity(),getListTrainer(), roleID);
            userAdapter_2 = new UserAdapter(getActivity(),getListFemaleTrainer(), roleID);
            userAdapter_3 = new UserAdapter(getActivity(),getListMostExpTrainer(),roleID);
            userAdapter_4 = new UserAdapter(getActivity(),getListNewTrainer(), roleID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        rcvData = getActivity().findViewById(R.id.rcv_data_1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        rcvData.setLayoutManager(linearLayoutManager);
        rcvData.setAdapter(userAdapter);

        rcvData_2 = getActivity().findViewById(R.id.rcv_data_2);
        LinearLayoutManager linearLayoutManager_2 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        rcvData_2.setLayoutManager(linearLayoutManager_2);
        rcvData_2.setAdapter(userAdapter_2);

        rcvData_3 = getActivity().findViewById(R.id.rcv_data_3);
        LinearLayoutManager linearLayoutManager_3 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        rcvData_3.setLayoutManager(linearLayoutManager_3);
        rcvData_3.setAdapter(userAdapter_3);

        rcvData_4 = getActivity().findViewById(R.id.rcv_data_4);
        LinearLayoutManager linearLayoutManager_4 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        rcvData_4.setLayoutManager(linearLayoutManager_4);
        rcvData_4.setAdapter(userAdapter_4);



    }
    

    private List<User> getListTrainer() throws Exception {
        List<User> listTrainer = new ArrayList<>();
        GetData data = new GetData();
        listTrainer = data.getListTrainer();
        return listTrainer;
    }

    private List<User> getListFemaleTrainer() throws SQLException {
        List<User> listFemaleTrainer = new ArrayList<>();
        GetData data = new GetData();
        listFemaleTrainer = data.getListFemaleTrainer();
        return listFemaleTrainer;
    }

    private List<User> getListMostExpTrainer() {
        List<User> listMostExpTrainer = new ArrayList<>();

        return listMostExpTrainer;
    }

    private List<User> getListNewTrainer() {
        List<User> listNewTrainer = new ArrayList<>();


        return listNewTrainer;
    }

}