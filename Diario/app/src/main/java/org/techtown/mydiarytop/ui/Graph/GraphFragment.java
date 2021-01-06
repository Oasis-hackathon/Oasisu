package org.techtown.mydiarytop.ui.Graph;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.techtown.mydiarytop.MainActivity;
import org.techtown.mydiarytop.R;
import org.techtown.mydiarytop.data.DiaryData;
import org.techtown.mydiarytop.ui.Chatbot.ChatbotFragment;
import org.techtown.mydiarytop.ui.Diary.DiaryFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import kotlin.random.URandomKt;

public class GraphFragment extends Fragment {

    LineChart emotionChart;

    public GraphFragment() {
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
       View root = inflater.inflate(R.layout.fragment_graph, container, false);

       emotionChart = (LineChart) root.findViewById(R.id.emotionChart);

        ArrayList<Entry> values = new ArrayList<Entry>();

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        String month = sdf.format(date);

        ((MainActivity)getActivity()).sqlDB = ((MainActivity)getActivity()).myDBHelper.getReadableDatabase();
        Cursor cursor;

        String query = "SELECT * FROM DiaryDB WHERE date LIKE ";
        query += "'____" + month +"__';";

        cursor = ((MainActivity)getActivity()).sqlDB.rawQuery(query,null);

        String strDate = "";
        String strTag = "";
        String strEmotion = "";
        String strDiaryTitle = "";
        String strDiaryText = "";

        ArrayList<DiaryData> list = new ArrayList<DiaryData>();
        DiaryData item = null;

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


        for (int i = 0; i < 30; i++) {
            if(list.size() > i){
                if(list.get(i).getEmotion() != null){
                    values.add(new Entry(i+1, EmotionLabeling(list.get(i).getEmotion())));
                } else {
                    values.add(new Entry(i+1, 0));
                }
            } else {
                values.add(new Entry(i+1, 0));
            }
        }

        LineDataSet set1;
        set1 = new LineDataSet(values, "감정 분포");

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the data sets

        // create a data object with the data sets
        LineData data = new LineData(dataSets);

        // black lines and points
        //set1.setColor(Color.BLUE);
        set1.setCircleColor(Color.GREEN);
        set1.setFillColor(Color.GREEN);
        set1.disableDashedLine();
        set1.setValueTextSize(0.0f);
        set1.setDrawValues(false);

        emotionChart.getAxisLeft().setDrawGridLines(false);
        emotionChart.getXAxis().setDrawGridLines(false);
        emotionChart.setDescription(null);

        // set data;
        emotionChart.setDrawGridBackground(false);
        //emotionChart.setOutlineSpotShadowColor(Color.GREEN);
        emotionChart.getLegend().setEnabled(false);
        emotionChart.getAxisRight().setEnabled(false);
        emotionChart.getAxisLeft().setEnabled(false);
        // dont forget to refresh the drawing


        emotionChart.animateX(1000);
        emotionChart.setData(data);
        emotionChart.invalidate();


        ((MainActivity)getActivity()).backButton.show();
        ((MainActivity)getActivity()).backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, ((MainActivity)getActivity()).chatbotFragment).commitAllowingStateLoss();
            }
        });

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

    public Integer EmotionLabeling(String emotion){

        if ("사랑".equals(emotion)) {
            return 10;
        } else if ("기쁨".equals(emotion)) {
            return 5;
        } else if ("불안".equals(emotion)) {
            return -4;
        } else if ("상처".equals(emotion)) {
            return -6;
        } else if ("슬픔".equals(emotion)) {
            return -8;
        } else if ("분노".equals(emotion)) {
            return -10;
        }
        return 0;
    }
}