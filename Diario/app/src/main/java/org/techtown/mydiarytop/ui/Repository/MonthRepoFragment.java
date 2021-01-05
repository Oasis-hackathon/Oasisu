package org.techtown.mydiarytop.ui.Repository;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.techtown.mydiarytop.MainActivity;
import org.techtown.mydiarytop.R;
import org.techtown.mydiarytop.data.DiaryData;
import org.techtown.mydiarytop.data.DiaryDataAdapter;
import org.techtown.mydiarytop.data.OnDayClickListener;
import org.techtown.mydiarytop.ui.Diary.DiaryFragment;

import java.util.ArrayList;

public class MonthRepoFragment extends Fragment {

    Integer month;
    TextView monthSelTitle;
    RecyclerView dayRecyclerView;
    DiaryDataAdapter diaryDataAdapter;
    Fragment dayDiaryFragment = new DayDiaryFragment();

    ArrayList<DiaryData> list = null;
    Context context;
    DiaryData item = null;


    public MonthRepoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();  //번들 받기. getArguments() 메소드로 받음.

        if(bundle != null){
            month = bundle.getInt("Month"); //Name 받기.
            System.out.println(month); //확인
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_month_repo, container, false);

        monthSelTitle = (TextView) root.findViewById(R.id.month_sel_title);
        monthSelTitle.setText(month+"월");

        dayRecyclerView = (RecyclerView) root.findViewById(R.id.dayRecyclerView);
        dayRecyclerView.setHasFixedSize(true);

        ((MainActivity)getActivity()).sqlDB = ((MainActivity)getActivity()).myDBHelper.getReadableDatabase();
        Cursor cursor;

        String query = "SELECT * FROM DiaryDB WHERE date LIKE ";

        if(month < 10)
            query += "'____0" + month +"__';";
        else
            query += "'____" + month +"__';";

        cursor = ((MainActivity)getActivity()).sqlDB.rawQuery(query,null);

        String strDate = "";
        String strTag = "";
        String strEmotion = "";
        String strDiaryTitle = "";
        String strDiaryText = "";

        list = new ArrayList<DiaryData>();
        while (cursor.moveToNext()) {
            // strDate = cursor.getString(cursor.getColumnIndex(0));
            strDate = cursor.getString(0);
            strTag = cursor.getString(1) ;
            strEmotion = cursor.getString(2);
            strDiaryTitle = cursor.getString(3);
            strDiaryText = cursor.getString(4);

            System.out.println(strDate);
            System.out.println(strTag);
            System.out.println(strEmotion);
            System.out.println(strDiaryTitle);
            System.out.println(strDiaryText);

            item = new DiaryData(strDate, strTag, strEmotion, strDiaryTitle, strDiaryText);
            list.add(item);
        }

        cursor.close();
        ((MainActivity)getActivity()).sqlDB.close();


        diaryDataAdapter = new DiaryDataAdapter(getActivity().getApplicationContext(), list);
        dayRecyclerView.setAdapter(diaryDataAdapter);
        dayRecyclerView.setClickable(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(),3);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        dayRecyclerView.setLayoutManager(gridLayoutManager);



        diaryDataAdapter.setOnItemClicklistener(new OnDayClickListener() {
            @Override
            public void onItemClick(DiaryDataAdapter.DiaryDataBeholder holder, View view, int position) {


                System.out.println(diaryDataAdapter.getItem(position).getDay() + "클릭\n");

                ArrayList<String> strBun = null;
                strBun = new ArrayList<String>();
                strBun.add(diaryDataAdapter.getItem(position).getDay());
                strBun.add(diaryDataAdapter.getItem(position).getTag());
                strBun.add(diaryDataAdapter.getItem(position).getEmotion());
                strBun.add(diaryDataAdapter.getItem(position).getDiaryTitle());
                strBun.add(diaryDataAdapter.getItem(position).getDiaryText());

                Bundle bundle = new Bundle();
                bundle.putStringArrayList("DiaryArrayList", strBun);
                dayDiaryFragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, dayDiaryFragment);
                fragmentTransaction.commit();
            }
        });

        ((MainActivity)getActivity()).floatingChatbot.hide();
        ((MainActivity)getActivity()).backButton.show();
        ((MainActivity)getActivity()).backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, ((MainActivity)getActivity()).repositoryFragment).commitAllowingStateLoss();
            }
        });


        ((MainActivity)getActivity()).writeMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, ((MainActivity)getActivity()).diaryFragment).commitAllowingStateLoss();
            }
        });

        // Inflate the layout for this fragment
        return root;
    }

}