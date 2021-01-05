package org.techtown.mydiarytop.data;

import android.view.View;

public interface OnDayClickListener {
    public void onItemClick(DiaryDataAdapter.DiaryDataBeholder holder, View view, int position);
}
