package org.techtown.mydiarytop.ui.Repository;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.techtown.mydiarytop.MainActivity;
import org.techtown.mydiarytop.R;
import org.techtown.mydiarytop.ui.Diary.DiaryFragment;

import java.util.ArrayList;

public class DayDiaryFragment extends Fragment {

    ArrayList<String> diaryArrayList = null;
    TextView dayTitle;
    TextView dayEmotion;
    TextView diaryTitle;
    TextView diaryText;
    TextView dayTag;

    public DayDiaryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();  //번들 받기. getArguments() 메소드로 받음.

        if (bundle != null) {
            diaryArrayList = bundle.getStringArrayList("DiaryArrayList"); //Name 받기.

            for(int i = 0; i < 5; i++){
                System.out.println(diaryArrayList.get(i)); //확인
            }

        }
    }

            @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_day_diary, container, false);

        dayTitle = (TextView) root.findViewById(R.id.day_sel_title);
        dayEmotion = (TextView) root.findViewById(R.id.day_sel_emotion);
        diaryTitle = (TextView) root.findViewById(R.id.diary_sel_title);
        diaryText = (TextView) root.findViewById(R.id.diary_sel_text);
        dayTag = (TextView) root.findViewById(R.id.day_sel_tag);

        StringBuffer day = new StringBuffer(diaryArrayList.get(0));
        day.insert(4, "년 ");
        if(day.charAt(6) == '0') {
            day.deleteCharAt(6);
            day.insert(6, " ");
            day.insert(8, "월 ");
        } else
            day.insert(8, "월 ");

        if(day.charAt(10) == '0'){
            day.deleteCharAt(10);
            day.insert(10, " ");
            day.insert(12, "월 ");
        } else
            day.insert(12,"일");

        dayTitle.setText(day);
        dayTag.setText(diaryArrayList.get(1));
        dayEmotion.setText(diaryArrayList.get(2));
        diaryTitle.setText(diaryArrayList.get(3));
        diaryText.setText(diaryArrayList.get(4));

        ((MainActivity)getActivity()).backButton.show();
       ((MainActivity)getActivity()).floatingChatbot.hide();
       ((MainActivity)getActivity()).backButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
               FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
               fragmentTransaction.replace(R.id.nav_host_fragment, ((MainActivity)getActivity()).chatbotFragment).commitAllowingStateLoss();
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

        return root;
    }
}