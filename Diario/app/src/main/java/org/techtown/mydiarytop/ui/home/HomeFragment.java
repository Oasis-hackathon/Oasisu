package org.techtown.mydiarytop.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.mydiarytop.MainActivity;
import org.techtown.mydiarytop.R;
import org.techtown.mydiarytop.data.DiaryDataAdapter;
import org.techtown.mydiarytop.data.OnDayClickListener;
import org.techtown.mydiarytop.data.OnTowerDataClickListener;
import org.techtown.mydiarytop.data.TowerData;
import org.techtown.mydiarytop.data.TowerDataAdapter;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView towerRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ((MainActivity)getActivity()).backButton.hide();

        towerRecyclerView = (RecyclerView) root.findViewById(R.id.tower);
        towerRecyclerView.setHasFixedSize(true);

        ArrayList<TowerData> list = new ArrayList<TowerData>();
        TowerData item;



        Integer totalCount = ((MainActivity)getActivity()).totalDiaryCount;
        ArrayList<Integer> tempFunCount = new ArrayList<Integer>();
        for(int i = 0; i < 6; i++){
            if(totalCount - 10 >= 0){
                tempFunCount.add(10);
                totalCount-=10;
            } else if (totalCount - 10 <= -10) {
                tempFunCount.add(0);
            } else {
                tempFunCount.add(totalCount);
                totalCount-=10;
            }
        }
        item = new TowerData("TowerTop", 0, R.drawable.room1, tempFunCount.get(5), R.drawable.diarywritemenuimage);
        list.add(item);
        item = new TowerData(((MainActivity)getActivity()).emotion.get(5), 0, R.drawable.room5, tempFunCount.get(5), R.drawable.diarywritemenuimage);
        list.add(item);
        item = new TowerData(((MainActivity)getActivity()).emotion.get(4), 0, R.drawable.room4, tempFunCount.get(4), R.drawable.diarywritemenuimage);
        list.add(item);
        item = new TowerData(((MainActivity)getActivity()).emotion.get(3), 0, R.drawable.room3, tempFunCount.get(3), R.drawable.diarywritemenuimage);
        list.add(item);
        item = new TowerData(((MainActivity)getActivity()).emotion.get(2), 0, R.drawable.room2, tempFunCount.get(2), R.drawable.diarywritemenuimage);
        list.add(item);
        item = new TowerData(((MainActivity)getActivity()).emotion.get(1), 0, R.drawable.room1, tempFunCount.get(1), R.drawable.diarywritemenuimage);
        list.add(item);
        item = new TowerData(((MainActivity)getActivity()).emotion.get(0), 0, R.drawable.room0, tempFunCount.get(0), R.drawable.diarywritemenuimage);
        list.add(item);
        item = new TowerData("챗봇방", 0, R.drawable.librarytower, 5, R.drawable.diarywritemenuimage);
        list.add(item);

        TowerDataAdapter towerDataAdapter = new TowerDataAdapter(getActivity().getApplicationContext(), list);
        towerRecyclerView.setAdapter(towerDataAdapter);
        towerRecyclerView.setClickable(true);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        towerRecyclerView.setLayoutManager(linearLayoutManager);
        towerRecyclerView.scrollToPosition(7);

        towerDataAdapter.setOnItemClicklistener(new OnTowerDataClickListener() {
            @Override
            public void onItemClick(TowerDataAdapter.TowerDataBeholder holder, View view, int position) {


                System.out.println(towerDataAdapter.getItem(position).getTowerCount() + "클릭\n");

                if (position == 7) {

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.nav_host_fragment, ((MainActivity)getActivity()).chatbotFragment);
                    fragmentTransaction.commit();

                } else if (position > 0) {

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    switch (position){
                        // ArrayList<String> strBun = null;

                        case 1:
                            if(((MainActivity)getActivity()).totalDiaryCount <= 50)
                                break;
                            ((MainActivity)getActivity()).currentRoom = ((MainActivity)getActivity()).emotion.get(5);
                            fragmentTransaction.replace(R.id.nav_host_fragment, ((MainActivity)getActivity()).tower6Fragment);
                            fragmentTransaction.commit();
                            break;
                        case 2:
                            if(((MainActivity)getActivity()).totalDiaryCount <= 40)
                                break;
                            ((MainActivity)getActivity()).currentRoom = ((MainActivity)getActivity()).emotion.get(4);
                            fragmentTransaction.replace(R.id.nav_host_fragment, ((MainActivity)getActivity()).tower5Fragment);
                            fragmentTransaction.commit();
                            break;
                        case 3:
                            if(((MainActivity)getActivity()).totalDiaryCount <= 30)
                                break;
                            ((MainActivity)getActivity()).currentRoom = ((MainActivity)getActivity()).emotion.get(3);
                            fragmentTransaction.replace(R.id.nav_host_fragment, ((MainActivity)getActivity()).tower4Fragment);
                            fragmentTransaction.commit();
                            break;
                        case 4:
                            if(((MainActivity)getActivity()).totalDiaryCount <= 20)
                                break;
                            ((MainActivity)getActivity()).currentRoom = ((MainActivity)getActivity()).emotion.get(2);
                            fragmentTransaction.replace(R.id.nav_host_fragment, ((MainActivity)getActivity()).tower3Fragment);
                            fragmentTransaction.commit();
                            break;
                        case 5:
                            if(((MainActivity)getActivity()).totalDiaryCount <= 10)
                                break;
                            ((MainActivity)getActivity()).currentRoom = ((MainActivity)getActivity()).emotion.get(1);
                            fragmentTransaction.replace(R.id.nav_host_fragment, ((MainActivity)getActivity()).tower2Fragment);
                            fragmentTransaction.commit();
                            break;
                        case 6:
                            ((MainActivity)getActivity()).currentRoom = ((MainActivity)getActivity()).emotion.get(0);
                            fragmentTransaction.replace(R.id.nav_host_fragment, ((MainActivity)getActivity()).tower1Fragment);
                            fragmentTransaction.commit();
                            break;

                    }


                    ((MainActivity)getActivity()).backButton.show();
                }



            }
        });


        ((MainActivity)getActivity()).floatingChatbot.hide();

        ((MainActivity)getActivity()).writeMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, ((MainActivity)getActivity()).diaryFragment).commitAllowingStateLoss();
                ((MainActivity)getActivity()).backButton.show();
            }
        });




        return root;
    }
}