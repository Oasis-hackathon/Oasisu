package org.techtown.mydiarytop.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;

import org.techtown.mydiarytop.MainActivity;
import org.techtown.mydiarytop.R;
import org.techtown.mydiarytop.data.TowerData;

import java.util.ArrayList;

public class Tower1Fragment extends Fragment {

    TextView roomTitle;
    ImageView emotionImageRoom;
    TextView emotionCountRoom;
    LinearLayout roomBackground;
    ImageView furnitureImage1;
    ImageView furnitureImage2;

    ImageView total1;
    ImageView total2;
    ImageView total3;


    public Tower1Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tower1, container, false);

        TextView roomTitle;
        ImageView emotionImageRoom;
        TextView emotionCountRoom;
        ImageView furnitureImage1;
        ImageView furnitureImage2;
        ImageView characterImage;

        roomTitle = (TextView) root.findViewById(R.id.roomTitle);
        emotionImageRoom = (ImageView) root.findViewById(R.id.emotionImageRoom);
        emotionCountRoom = (TextView) root.findViewById(R.id.emotionCountRoom);
        characterImage = (ImageView) root.findViewById(R.id.characterImage);
        total1 = (ImageView) root.findViewById(R.id.total1);
        total2 = (ImageView) root.findViewById(R.id.total2);
        total3 = (ImageView) root.findViewById(R.id.total3);

        Glide.with(this).load(R.drawable.character_move).into(characterImage);


        String roomTitleStr = ((MainActivity)getActivity()).currentRoom + "의 방";
        roomTitle.setText(roomTitleStr);

        switch (((MainActivity)getActivity()).currentRoom){
            case "사랑":
                emotionImageRoom.setImageResource(R.drawable.heart_emotion0);
                break;
            case "기쁨":
                emotionImageRoom.setImageResource(R.drawable.heart_emotion1);
                break;
            case "불안":
                emotionImageRoom.setImageResource(R.drawable.heart_emotion2);
                break;
            case "분노":
                emotionImageRoom.setImageResource(R.drawable.heart_emotion3);
                break;
            case "슬픔":
                emotionImageRoom.setImageResource(R.drawable.heart_emotion4);
                break;
            case "상처":
                emotionImageRoom.setImageResource(R.drawable.heart_emotion5);
                break;
        }

        String emotionCountRoomStr;

        Integer tmpCount = ((MainActivity)getActivity()).totalDiaryCount;
        while (tmpCount > 10){
            tmpCount-=10;
        }

        if(tmpCount < 10)
            emotionCountRoomStr = " "+ tmpCount.toString() + "/10";
        else
            emotionCountRoomStr = tmpCount.toString() + "/10";
        emotionCountRoom.setText(emotionCountRoomStr);


        if(((MainActivity)getActivity()).totalDiaryCount >= 3)
            total1.setVisibility(View.VISIBLE);
        else if(((MainActivity)getActivity()).totalDiaryCount >= 6)
            total2.setVisibility(View.VISIBLE);
        else if (((MainActivity)getActivity()).totalDiaryCount >= 9)
            total3.setVisibility(View.VISIBLE);


        ((MainActivity)getActivity()).backButton.setOnClickListener(new View.OnClickListener() {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            @Override
            public void onClick(View view) {
                total1.setVisibility(View.INVISIBLE);
                total2.setVisibility(View.INVISIBLE);
                total3.setVisibility(View.INVISIBLE);

                fragmentTransaction.replace(R.id.nav_host_fragment, ((MainActivity)getActivity()).homeFragment).commitAllowingStateLoss();

            }
        });

        ((MainActivity)getActivity()).floatingChatbot.hide();
        ((MainActivity)getActivity()).writeMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                total1.setVisibility(View.INVISIBLE);
                total2.setVisibility(View.INVISIBLE);
                total3.setVisibility(View.INVISIBLE);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, ((MainActivity)getActivity()).diaryFragment).commitAllowingStateLoss();
                ((MainActivity)getActivity()).backButton.show();
            }
        });


        return root;
    }
}