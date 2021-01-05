package org.techtown.mydiarytop.data;

import java.util.ArrayList;

public class UserData {

    ArrayList<Integer> emotion;
    Integer totalDiaryCount;


    public UserData() {
        emotion = new ArrayList<Integer>();
        for(int i = 0; i < 6; i++)
            emotion.add(0);
        totalDiaryCount = 0;
    }

    public ArrayList<Integer> getEmotion() {
        return emotion;
    }

    public void setEmotion(ArrayList<Integer> emotion) {
        this.emotion = emotion;
    }

    public Integer getTotalDiaryCount() {
        return totalDiaryCount;
    }

    public void setTotalDiaryCount(Integer totalDiaryCount) {
        this.totalDiaryCount = totalDiaryCount;
    }
}
