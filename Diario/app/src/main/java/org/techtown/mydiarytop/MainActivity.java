package org.techtown.mydiarytop;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.techtown.mydiarytop.data.AnimationLooper;
import org.techtown.mydiarytop.data.UserData;
import org.techtown.mydiarytop.ui.Chatbot.ChatbotFragment;
import org.techtown.mydiarytop.ui.Diary.DiaryFragment;
import org.techtown.mydiarytop.ui.Graph.GraphFragment;
import org.techtown.mydiarytop.ui.Repository.DayDiaryFragment;
import org.techtown.mydiarytop.ui.Repository.MonthRepoFragment;
import org.techtown.mydiarytop.ui.Repository.RepositoryFragment;
import org.techtown.mydiarytop.ui.home.HomeFragment;
import org.techtown.mydiarytop.ui.home.Tower1Fragment;
import org.techtown.mydiarytop.ui.home.Tower2Fragment;
import org.techtown.mydiarytop.ui.home.Tower3Fragment;
import org.techtown.mydiarytop.ui.home.Tower4Fragment;
import org.techtown.mydiarytop.ui.home.Tower5Fragment;
import org.techtown.mydiarytop.ui.home.Tower6Fragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public static myDBHelper myDBHelper;
    public static SQLiteDatabase sqlDB;
    public static Integer totalDiaryCount;
    public static ArrayList<String> emotion = new ArrayList<String>();

    public static UserData userData = new UserData();
    public static String currentRoom = null;
    public static FloatingActionButton writeMenu;

    public static FloatingActionButton floatingChatbot;
    public static FloatingActionButton backButton;
    public static FloatingActionButton SaveButton;
    public static Fragment homeFragment = new HomeFragment();
    public static Fragment diaryFragment = new DiaryFragment();
    public static Fragment chatbotFragment = new ChatbotFragment();
    public static Fragment repositoryFragment = new RepositoryFragment();
    public static Fragment graphFragment = new GraphFragment();
    public static Fragment monthRepoFragment = new MonthRepoFragment();
    public static Fragment daydiaryFragment = new DayDiaryFragment();

    public static Fragment tower1Fragment = new Tower1Fragment();
    public static Fragment tower2Fragment = new Tower2Fragment();
    public static Fragment tower3Fragment = new Tower3Fragment();
    public static Fragment tower4Fragment = new Tower4Fragment();
    public static Fragment tower5Fragment = new Tower5Fragment();
    public static Fragment tower6Fragment = new Tower6Fragment();

    MediaPlayer mediaPlayer;

    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //기본 SharedPreferences 환경과 관련된 객체를 얻어옵니다.
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        // SharedPreferences 수정을 위한 Editor 객체를 얻어옵니다.

        SoundPool soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC,0);	//작성
        int soundID = soundPool.load(this,R.raw.read,1);

        initializeValue();

        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        mediaPlayer.setLooping(true); //무한재생
        mediaPlayer.start();
        /*
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_repository, R.id.navigation_diary)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        */
        emotion.add("사랑");
        emotion.add("기쁨");
        emotion.add("불안");
        emotion.add("분노");
        emotion.add("슬픔");
        emotion.add("상처");

        writeMenu = (FloatingActionButton) findViewById(R.id.WriteMenu);
        floatingChatbot = (FloatingActionButton) findViewById(R.id.chatbot);
        backButton = (FloatingActionButton) findViewById(R.id.backButton);
        backButton.hide();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.nav_host_fragment, homeFragment).commitAllowingStateLoss();;
        //fragmentTransaction.commit();

        writeMenu.setOnClickListener(new View.OnClickListener() {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            @Override
            public void onClick(View v) {
                soundPool.play(soundID,1f,1f,1,0,1f);	//작성
                fragmentTransaction.replace(R.id.nav_host_fragment, diaryFragment).commitAllowingStateLoss();
                backButton.show();
            }
        });


        floatingChatbot.hide();
        floatingChatbot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, chatbotFragment).commitAllowingStateLoss();
                backButton.show();
                floatingChatbot.hide();
            }
        });

        myDBHelper = new myDBHelper(this);

        sqlDB = myDBHelper.getWritableDatabase();
        myDBHelper.onUpgrade(sqlDB,1,1);


        //sqlDB.close();

        Animation anim = AnimationUtils.loadAnimation
                (getApplicationContext(), // 현재화면의 제어권자
                        R.anim.chatbot_moving);   // 에니메이션 설정 파일
        //anim.setRepeatMode(Animation.INFINITE);
        //anim.setRepeatCount(Animation.INFINITE);

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                floatingChatbot.startAnimation(
                        AnimationUtils.loadAnimation(getApplicationContext(), R.anim.chatbot_moving)
                );
            }
        }, -1);

        AnimationLooper.start(floatingChatbot, R.anim.chatbot_moving);
    }
    @Override
    protected void onStop() {
        super.onStop();
        // Activity 가 종료되기 전에 저장한다
        // SharedPreferences 에 설정값(특별히 기억해야할 사용자 값)을 저장하기


        editor = preferences.edit();
        editor.putInt("count", totalDiaryCount);
        editor.apply();
        editor.commit(); // 파일에 최종 반영함 //꼭!!!!!!
    }

    public void initializeValue()
    {
        totalDiaryCount = preferences.getInt("count",0);
    }

    public class myDBHelper extends SQLiteOpenHelper {
        
        // 유저 컬럼 명칭 키
        private static final String EMOTION_1 = "emotion1";
        private static final String EMOTION_2 = "emotion2";
        private static final String EMOTION_3 = "emotion3";
        private static final String EMOTION_4 = "emotion4";
        private static final String EMOTION_5 = "emotion5";
        private static final String EMOTION_6 = "emotion6";
        private static final String TOTAL_DIARY_COUNT = "total_diary_count";
        
        public myDBHelper(Context context) {
            super(context, "DiaryDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE DiaryDB ( date CHAR(20) PRIMARY KEY, tag VARCHAR(50), emotion VARCHAR(20), diaryTitle TEXT, diaryData TEXT);");
            db.execSQL("CREATE TABLE UserDB ( id VARCHAR(20) PRIMARY KEY, data VARCHAR(50));");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if(newVersion != oldVersion){
                db.execSQL("DROP TABLE IF EXISTS DiaryDB");
                db.execSQL("DROP TABLE IF EXISTS UserDB");
                onCreate(db);
            }
        }
    }

    
    public void WriteDB(SQLiteDatabase sqlDB, String date, String tag, String emotion, String diaryTitle, String diaryText){
        sqlDB = myDBHelper.getWritableDatabase();

        String values = "'"+date+"', "+"'"+tag+"', "+"'"+emotion+"', "+"'"+diaryTitle+"', "+"'"+diaryText+"'";

        sqlDB.execSQL("INSERT INTO DiaryDB VALUES (" + values + ");");
        sqlDB.close();
        System.out.println("입력 완료");
    }

    public void UpdateDB(SQLiteDatabase sqlDB, String date, String tag, String emotion, String diaryTitle, String diaryText){
        sqlDB = myDBHelper.getWritableDatabase();

        String values = "date = '"+date+"', "+"tag = '"+tag+"', "+"emotion = '"+emotion+"', "+"diaryTitle = '"+diaryTitle+"', "+"diaryData = '"+diaryText+"'";

        sqlDB.execSQL("UPDATE DiaryDB SET " + values + " WHERE date == '" + date +"';");
        sqlDB.close();
        System.out.println("Update 완료");
    }

    public void ShowDB(SQLiteDatabase sqlDB){
        sqlDB = myDBHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM DiaryDB;",null);

        String strDate = "";
        String strTag = "";
        String strEmotion = "";
        String strDiaryTitle = "";
        String strDiaryText = "";

        while (cursor.moveToNext()){
            strDate += cursor.getString(0) + "\n";
            strTag += cursor.getString(1) + "\n";
            strEmotion += cursor.getString(2) + "\n";
            strDiaryTitle += cursor.getString(3) + "\n";
            strDiaryText += cursor.getString(4) + "\n";
        }

        System.out.println(strDate);
        System.out.println(strTag);
        System.out.println(strEmotion);
        System.out.println(strDiaryTitle);
        System.out.println(strDiaryText);

        cursor.close();
        sqlDB.close();
    }

    // Fragment 전환 함수
    public void replaceFragment(Fragment fragment, int currFragId){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(currFragId, fragment);
        fragmentTransaction.commit();
    }

    // 뒤로가기 키를 눌렀을 때에 대한 상황을 오버라이딩한다.
    @Override
    public void onBackPressed() {
        // AlertDialog 빌더를 이용해 종료시 발생시킬 창을 띄운다
        AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
        alBuilder.setMessage("종료하시겠습니까?");

        // "예" 버튼을 누르면 실행되는 리스너
        alBuilder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish(); // 현재 액티비티를 종료한다. (MainActivity에서 작동하기 때문에 애플리케이션을 종료한다.)
            }
        });
        // "아니오" 버튼을 누르면 실행되는 리스너
        alBuilder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return; // 아무런 작업도 하지 않고 돌아간다
            }
        });
        alBuilder.setTitle("프로그램 종료");
        alBuilder.show(); // AlertDialog.Bulider로 만든 AlertDialog를 보여준다.
    }



}