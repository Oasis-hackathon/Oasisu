package org.techtown.mydiarytop.ui.Repository;

public class monthItem {
    String month;
    int image;

    public monthItem(String month, int image) {
        this.month = month;
        this.image = image;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
