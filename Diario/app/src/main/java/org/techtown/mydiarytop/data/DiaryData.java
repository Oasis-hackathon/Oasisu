package org.techtown.mydiarytop.data;

public class DiaryData {
    String day;
    String tag;
    String emotion;
    String diaryTitle;
    String diaryText;

    public DiaryData(String day, String tag, String emotion, String diaryTitle, String diaryText) {
        this.day = day;
        this.tag = tag;
        this.emotion = emotion;
        this.diaryTitle = diaryTitle;
        this.diaryText = diaryText;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDiaryTitle() {
        return diaryTitle;
    }

    public void setDiaryTitle(String diaryTitle) {
        this.diaryTitle = diaryTitle;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getDiaryText() {
        return diaryText;
    }

    public void setDiaryText(String diaryText) {
        this.diaryText = diaryText;
    }
}
