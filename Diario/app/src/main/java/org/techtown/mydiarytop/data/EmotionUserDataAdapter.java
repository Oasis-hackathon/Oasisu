package org.techtown.mydiarytop.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.mydiarytop.R;

import java.util.ArrayList;

public class EmotionUserDataAdapter extends RecyclerView.Adapter<EmotionUserDataAdapter.EmotionUserDataBeholder> {

    private ArrayList<Integer> mList;
    private LayoutInflater mInflate;
    private Context mContext;

    OnDayClickListener listener;

    public EmotionUserDataAdapter(Context context, ArrayList<Integer> mList) {
        this.mList = mList;
        this.mInflate = LayoutInflater.from(context);
        this.mContext = context;
    }

    @NonNull
    @Override
    public EmotionUserDataBeholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.emotion_grid_item, parent, false);
        EmotionUserDataAdapter.EmotionUserDataBeholder viewHolder = new EmotionUserDataAdapter.EmotionUserDataBeholder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EmotionUserDataBeholder holder, int position) {
        switch (position){
            case 0:
                holder.emotionImage.setImageResource(R.drawable.heart_emotion0);
                break;
            case 1:
                holder.emotionImage.setImageResource(R.drawable.heart_emotion1);
                break;
            case 2:
                holder.emotionImage.setImageResource(R.drawable.heart_emotion2);
                break;
            case 3:
                holder.emotionImage.setImageResource(R.drawable.heart_emotion3);
                break;
            case 4:
                holder.emotionImage.setImageResource(R.drawable.heart_emotion4);
                break;
            case 5:
                holder.emotionImage.setImageResource(R.drawable.heart_emotion5);
                break;
            case 6:
                holder.emotionImage.setImageResource(R.drawable.ic_dashboard_black_24dp);
                break;
        }

        String setStr;
        if(mList.get(position) < 10)
            setStr = " "+ mList.get(position).toString() + "/10";
        else
            setStr = mList.get(position).toString() + "/10";
        System.out.println(setStr);
        holder.emotionCountText.setText(setStr);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    //ViewHolder
    public static class EmotionUserDataBeholder extends RecyclerView.ViewHolder {
        public ImageView emotionImage;
        public TextView emotionCountText;

        public EmotionUserDataBeholder(View itemView, final OnDayClickListener listener) {
            super(itemView);
            emotionImage = itemView.findViewById(R.id.emotionImage);
            emotionCountText = itemView.findViewById(R.id.emotionCountText);
        }

    }
    public Integer getItem(int position){
        return mList.get(position);
    }

    public void addItem(Integer item){ mList.add(item); }

    public void setItems(ArrayList<Integer> items){ this.mList = items; }

    public void setItem(int position, Integer item) {
        mList.set(position, item);
    }

    public int getCount() { return mList.size(); }

}
