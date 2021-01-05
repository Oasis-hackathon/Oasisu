package org.techtown.mydiarytop.ui.Repository;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.mydiarytop.MainActivity;
import org.techtown.mydiarytop.R;
import org.techtown.mydiarytop.ui.Chatbot.ChatbotFragment;
import org.techtown.mydiarytop.ui.Diary.DiaryFragment;

import java.util.ArrayList;

public class RepositoryFragment extends Fragment {

    RecyclerView monthRecyclerView;
    MonthRecyclerAdapter monthRecyclerAdapter;
    ArrayList<monthItem> list = null;
    Context context;
    monthItem item = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getActivity().getBaseContext();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_repository, container, false);

        monthRecyclerView = (RecyclerView) root.findViewById(R.id.monthRecyclerView);
        monthRecyclerView.setHasFixedSize(true);

        list = new ArrayList<monthItem>();
        for (int i = 1; i < 13; i++) {
            String month = i + "월";
            item = new monthItem(month, 1);
            list.add(item);
        }


        monthRecyclerAdapter = new MonthRecyclerAdapter(getActivity().getApplicationContext(), list);
        monthRecyclerView.setAdapter(monthRecyclerAdapter);
        monthRecyclerView.setClickable(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(), 3);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        monthRecyclerView.setLayoutManager(gridLayoutManager);

        monthRecyclerAdapter.setOnItemClicklistener(new OnMonthClickListener() {
            @Override
            public void onItemClick(MonthRecyclerAdapter.MyViewHolder holder, View view, int position) {
                System.out.println((position+1) + "월 클릭\n");

                Bundle bundle = new Bundle();
                bundle.putInt("Month", position+1);
                ((MainActivity)getActivity()).monthRepoFragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, ((MainActivity)getActivity()).monthRepoFragment);
                fragmentTransaction.commit();
            }
        });
        ((MainActivity)getActivity()).backButton.show();
        ((MainActivity)getActivity()).backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, ((MainActivity)getActivity()).chatbotFragment).commitAllowingStateLoss();
            }
        });

        ((MainActivity)getActivity()).floatingChatbot.hide();

        ((MainActivity)getActivity()).writeMenu.setOnClickListener(new View.OnClickListener() {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.nav_host_fragment, ((MainActivity)getActivity()).diaryFragment).commitAllowingStateLoss();
            }
        });
        

        return root;
    }
}