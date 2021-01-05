package org.techtown.mydiarytop.ui.Chatbot;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.bumptech.glide.Glide;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;
import net.yslibrary.android.keyboardvisibilityevent.Unregistrar;

import org.techtown.mydiarytop.MainActivity;
import org.techtown.mydiarytop.R;
import org.techtown.mydiarytop.data.AnimationLooper;
import org.techtown.mydiarytop.data.DiaryData;
import org.techtown.mydiarytop.data.EmotionUserDataAdapter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatbotFragment extends Fragment {

    ImageView emotionGraph;
    ImageView chatbotInHouse;
    RecyclerView emotionCountRecyclerView;
    ImageView repository;
    ImageView chatbotloding2;

    TextView chatbotAnswer;
    EditText chatbotQuestion;

    Button chatbotQuestionButton;
    Button backHomeFromChatbot;

    EmotionUserDataAdapter emotionUserDataAdapter;
    String Answer;

    Handler chatbotHandler = new Handler();

    public ChatbotFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_chatbot, container, false);


        ((MainActivity)getActivity()).floatingChatbot.hide();

        ImageView emotionGraph;
        ImageView chatbotInHouse;
        RecyclerView emotionCountRecyclerView;
        ImageView repository;

        chatbotAnswer = (TextView) root.findViewById(R.id.chatbotAnswer);
        chatbotQuestion = (EditText) root.findViewById(R.id.chatbotQuestion);
        chatbotQuestionButton = (Button) root.findViewById(R.id.chatbotQuestionButton);
        chatbotloding2 = (ImageView) root.findViewById(R.id.chatbotlodding2);

        emotionGraph = (ImageView) root.findViewById(R.id.EmotionGraph);
        chatbotInHouse = (ImageView) root.findViewById(R.id.ChatbotInHouse);
        emotionCountRecyclerView = (RecyclerView) root.findViewById(R.id.EmotionCountRecyclerView);
        emotionCountRecyclerView.setHasFixedSize(true);

        repository = (ImageView) root.findViewById(R.id.repository);

        repository.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, ((MainActivity)getActivity()).repositoryFragment).commitAllowingStateLoss();;
            }
        });

        emotionGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, ((MainActivity)getActivity()).graphFragment).commitAllowingStateLoss();
            }
        });

        Glide.with(this).load(R.drawable.chatbotmove).into(chatbotInHouse);

        chatbotInHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatbotQuestion.setVisibility(View.VISIBLE);
                chatbotQuestionButton.setVisibility(View.VISIBLE);

                if(((MainActivity)getActivity()).totalDiaryCount >= 3){
                    ((MainActivity)getActivity()).sqlDB = ((MainActivity)getActivity()).myDBHelper.getReadableDatabase();
                    Cursor cursor;

                    String query = "SELECT * FROM DiaryDB";

                    cursor = ((MainActivity)getActivity()).sqlDB.rawQuery(query,null);

                    String strDate = "";
                    String strTag = "";
                    String strEmotion = "";
                    String strDiaryTitle = "";
                    String strDiaryText = "";

                    ArrayList<DiaryData> list = new ArrayList<DiaryData>();
                    DiaryData item = null;

                    for(int i = 0; i < 3; i++){
                        cursor.moveToNext();

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

                    List<String> emotion0 = Arrays.asList("사랑", "기쁨");
                    List<String> emotion1 = Arrays.asList("불안", "분노", "슬픔", "상처");
                    int goodEmotion = 0;
                    int badEmotion = 0;


                    for(int i = 0; i < 3; i++) {
                        if(emotion0.contains(list.get(i).getEmotion())){
                            goodEmotion++;
                        } else {
                            badEmotion++;
                        }
                    }

                    if(goodEmotion == 3){
                        chatbotAnswer.setText("요즘 행복해보여요! 앞으로도 좋은 일이 가득하길 바랄께요♡");
                        goodEmotion = 0;
                    }
                    if(badEmotion == 3){
                        chatbotAnswer.setText("많이 힘들었죠? 일기 쓰면서 훌훌 털어버려요 :)");
                        badEmotion = 0;
                    }


                }

            }
        });


        ((MainActivity)getActivity()).backButton.show();
        ((MainActivity)getActivity()).writeMenu.show();

        chatbotQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).floatingChatbot.hide();
                chatbotloding2.setVisibility(View.VISIBLE);
                // Glide.with().load(R.drawable.chatbotmove).into(chatbotloding2);

                Animation anim = AnimationUtils.loadAnimation
                        (getActivity().getApplicationContext(), // 현재화면의 제어권자
                                R.anim.chatbot_loding);   // 에니메이션 설정 파일
                //anim.setRepeatMode(Animation.INFINITE);
                //anim.setRepeatCount(Animation.INFINITE);

                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        chatbotloding2.startAnimation(
                                AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.chatbot_loding)
                        );
                    }
                }, -1);

                AnimationLooper.start(chatbotloding2, R.anim.chatbot_loding);
                ChatbotEvent(chatbotQuestion.getText().toString());
            }
        });

        ((MainActivity)getActivity()).backButton.show();
        ((MainActivity)getActivity()).backButton.setOnClickListener(new View.OnClickListener() {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.nav_host_fragment, ((MainActivity)getActivity()).homeFragment).commitAllowingStateLoss();
            }
        });

        ArrayList<Integer> list = new ArrayList<Integer>();
        Integer totalCount = ((MainActivity) getActivity()).totalDiaryCount + 1;

        for(int i = 0; i < 6; i++){
            System.out.println(totalCount);
            if(totalCount - 10 >= 0){
                list.add(10);
                totalCount-=10;
            } else if (totalCount - 10 <= -10) {
                list.add(0);
                totalCount-=10;
            } else {
                list.add(totalCount);
                totalCount-=10;
            }
            System.out.println(list.get(i));
        }

        emotionUserDataAdapter = new EmotionUserDataAdapter(getActivity().getApplicationContext(), list);
        emotionCountRecyclerView.setAdapter(emotionUserDataAdapter);
        emotionCountRecyclerView.setClickable(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        emotionCountRecyclerView.setLayoutManager(gridLayoutManager);


        ((MainActivity)getActivity()).writeMenu.setOnClickListener(new View.OnClickListener() {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.nav_host_fragment, ((MainActivity)getActivity()).diaryFragment).commitAllowingStateLoss();
                //((MainActivity) getActivity()).floatingChatbot.show();
                ((MainActivity)getActivity()).backButton.show();
            }
        });

        return root;
    }


    public void ChatbotEvent(String question) {


        String taskStr = "http://168.131.151.207:5000/chatbotqa/" + question;
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
                            String str = '\n'+ result;
                            chatbotAnswer.setText(str);
                            ((MainActivity)getActivity()).floatingChatbot.hide();
                            chatbotloding2.setVisibility(View.INVISIBLE);
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