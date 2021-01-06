package org.techtown.mydiarytop.ui.Diary;

import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;

import org.techtown.mydiarytop.MainActivity;
import org.techtown.mydiarytop.R;
import org.techtown.mydiarytop.data.AnimationLooper;
import org.techtown.mydiarytop.data.DiaryData;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DiaryFragment extends Fragment {

    EditText diaryWriteInput;
    EditText diaryTitleInput;
    TextView todayDate;
    TextView totalDiaryCountText;
    ImageView chatbotLodding;
    Button saveButton;

    ArrayList<DiaryData> diaryList = null;
    DiaryData diaryData = null;




    Handler chatbotHandler = new Handler();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_diary, container, false);

        ((MainActivity)getActivity()).writeMenu.hide();
        saveButton = (Button) root.findViewById(R.id.SaveButton);

        diaryWriteInput = (EditText) root.findViewById(R.id.diaryWriteInput);
        diaryTitleInput = (EditText) root.findViewById(R.id.diaryTitleInput);

        todayDate = (TextView) root.findViewById(R.id.todayDate);
        totalDiaryCountText = (TextView) root.findViewById(R.id.totalDiaryCountText);

        chatbotLodding = (ImageView) root.findViewById(R.id.chatbotlodding);

        ((MainActivity) getActivity()).backButton.show();
        ((MainActivity) getActivity()).backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, ((MainActivity) getActivity()).homeFragment).commitAllowingStateLoss();
                ((MainActivity)getActivity()).writeMenu.show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveDiary(diaryTitleInput.getText().toString(), diaryWriteInput.getText().toString());
                chatbotLodding.setVisibility(View.INVISIBLE);
            }
        });


        // 너무 피곤하고.. 자고 싶어요..
        // 날씨 : 눈
        // 오늘은 눈이 펑펑 내려서 기분이 너무 좋았어! 친구들이랑 사진 찍어서 공유도 하고 따뜻한 핫초코도 타먹었어ㅎㅎ 코로나라서 친구들이랑 못 만나는게 너무 아쉽더라ㅠㅜ

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
        String day = sdf.format(date);

        todayDate.setText(day);

        Integer totalDiaryCount = ((MainActivity) getActivity()).totalDiaryCount;
        totalDiaryCount++;
        String totalDiaryCountStr = totalDiaryCount.toString() + "번째 일기";
        totalDiaryCountText.setText(totalDiaryCountStr);

        ((MainActivity)getActivity()).floatingChatbot.hide();
        return root;
    }

    public void SaveDiary(String diaryTitle, String diaryText){

        // 현재 날짜
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String day = sdf.format(date);



        // 태그

        ChatbotTag(diaryText, diaryTitle, day);
        // diaryText를 API로 보내서 태그 데이터 받아옴

    }

    public void ChatbotTag(String diaryText, String diaryTitle, String day) {
        chatbotLodding.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.chatbotmove).into(chatbotLodding);

        Animation anim = AnimationUtils.loadAnimation
                (getActivity().getApplicationContext(), // 현재화면의 제어권자
                        R.anim.chatbot_loding);   // 에니메이션 설정 파일
        //anim.setRepeatMode(Animation.INFINITE);
        //anim.setRepeatCount(Animation.INFINITE);

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                chatbotLodding.startAnimation(
                        AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.chatbot_loding)
                );
            }
        }, -1);

        AnimationLooper.start(chatbotLodding, R.anim.chatbot_loding);

        String taskStr = "http://168.131.151.207:5000/chatbotqa/tag/" + diaryText;
        System.out.println(taskStr);
        // new RestAPITask(taskStr).execute();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(taskStr);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    InputStream is = conn.getInputStream();

                    // Get the stream
                    StringBuilder builder = new StringBuilder();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                    // Set the result
                    String result = builder.toString();
                    System.out.println(result);


                    chatbotHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            // 감정 상태
                            String emotion;
                            List<String> emotion0 = Arrays.asList("감정/모호함");
                            List<String> emotion1 = Arrays.asList("감정/즐거움", "감정/모호함");
                            List<String> emotion2 = Arrays.asList("감정/불안감", "감정/답답", "감정/불안감/미래", "감정/심란", "감정/불신", "감정/걱정/암", "감정/생각", "감정/걱정", "감정/걱정/불면", "감정/걱정/경제적문제", "감정/불편감", "감정/걱정/건강염려", "감정/죄책감", "감정/신경쓰임", "감정/창피함", "감정/무서움", "감정/과민반응", "감정/걱정/주변평가", "감정/공포", "감정/초조함", "감정/기시감", "감정/당황", "감정/통제력상실", "감정/공포/새", "감정/걱정/건강문제", "감정/모호함", "감정/곤혹감", "감정/답답/사람많은곳", "감정/긴장", "감정/감정조절이상", "감정/걱정/자녀", "감정/예민함", "감정/걱정/미래", "감정/힘듦", "감정/괴로움", "감정/충격", "감정/두려움", "감정/불안감/초조함", "감정/힘듦/스트레스", "감정/불안감/긴장", "감정/불안감/증상재발", "감정/두려움/운전", "감정/걱정/증상재발", "감정/두려움/자동차", "감정/우울감", "감정/짜증");
                            List<String> emotion3 = Arrays.asList("감정/살인욕구", "감정/짜증", "감정/분노", "감정/답답", "감정/화", "감정/미움", "감정/불쾌감", "감정/생각", "감정/감정조절이상", "감정/감정조절이상/화", "감정/불만", "감정/모호함", "감정/불안감", "감정/긴장");
                            List<String> emotion4 = Arrays.asList("감정/우울감", "감정/우울감/증상지속", "감정/무력감", "감정/자살충동", "감정/눈물", "감정/힘듦/지침", "감정/자신감저하", "감정/슬픔", "감정/부정적사고", "감정/후회/결혼", "감정/허무함", "감정/의기소침", "감정/후회", "감정/무미건조", "감정/절망감", "감정/공허감", "감정/미안함/자녀", "감정/의욕상실", "감정/멍함", "감정/모호함", "감정/기분저하", "감정/의욕상실/무기력", "감정/우울감/증상재발", "감정/생각", "감정/의기소침/자격지심", "감정/좌절", "감정/우울감/눈물", "감정/불안감/초조함");
                            List<String> emotion5 = Arrays.asList("감정/괴로움", "감정/외로움", "감정/힘듦", "감정/서운함", "감정/자존감저하", "감정/충격", "감정/속상함", "감정/자괴감", "감정/억울함", "감정/배신감", "감정/고독감", "감정/비관적", "감정/예민함", "감정/생각", "감정/답답", "감정/무서움", "감정/분노");

                            if(emotion0.contains(result)){
                                emotion = ((MainActivity)getActivity()).emotion.get(0);
                            } else if(emotion1.contains(result)){
                                emotion = ((MainActivity)getActivity()).emotion.get(1);
                            } else if(emotion2.contains(result)){
                                emotion = ((MainActivity)getActivity()).emotion.get(2);
                            } else if(emotion3.contains(result)){
                                emotion = ((MainActivity)getActivity()).emotion.get(3);
                            } else if(emotion4.contains(result)){
                                emotion = ((MainActivity)getActivity()).emotion.get(4);
                            } else if(emotion5.contains(result)){
                                emotion = ((MainActivity)getActivity()).emotion.get(5);
                            } else {
                                emotion = ((MainActivity)getActivity()).emotion.get(0);
                            }


                            String query = "SELECT * FROM DiaryDB WHERE date == '" + day +"'";
                            Cursor cursor;
                            ((MainActivity)getActivity()).sqlDB = ((MainActivity)getActivity()).myDBHelper.getReadableDatabase();
                            cursor = ((MainActivity)getActivity()).sqlDB.rawQuery(query,null);
                            boolean Dup = false;
                            while (cursor.moveToNext())
                                Dup = true;
                            if(Dup == true)
                                ((MainActivity)getActivity()).UpdateDB(((MainActivity) getActivity()).sqlDB, day, result, emotion, diaryTitle, diaryText);
                            else {
                                System.out.println("증가" + ((MainActivity) getActivity()).totalDiaryCount);

                                ((MainActivity) getActivity()).WriteDB(((MainActivity) getActivity()).sqlDB, day, result, emotion, diaryTitle, diaryText);
                                ((MainActivity) getActivity()).totalDiaryCount++;

                            }

                            ((MainActivity) getActivity()).ShowDB(((MainActivity) getActivity()).sqlDB);

                        }
                    });

                } catch (Exception e) {
                    // Error calling the rest api
                    Log.e("REST_API", "GET method failed: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }).start();
    }

}