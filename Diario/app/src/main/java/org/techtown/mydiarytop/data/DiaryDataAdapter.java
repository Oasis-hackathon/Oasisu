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

public class DiaryDataAdapter extends RecyclerView.Adapter<DiaryDataAdapter.DiaryDataBeholder>
        implements OnDayClickListener {

    private ArrayList<DiaryData> mList;
    private LayoutInflater mInflate;
    private Context mContext;

    OnDayClickListener listener;

    public DiaryDataAdapter(Context context, ArrayList<DiaryData> mList) {
        this.mList = mList;
        this.mInflate = LayoutInflater.from(context);
        this.mContext = context;
    }

    @NonNull
    @Override
    public DiaryDataBeholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.day_linear_item, parent, false);
        DiaryDataBeholder viewHolder = new DiaryDataBeholder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryDataBeholder holder, int position) {
        //binding
        holder.dayTitle.setText(mList.get(position).getDay());

        switch (mList.get(position).getEmotion()){
            case "사랑":
                holder.diaryImage.setImageResource(R.drawable.ic_diarysadcolor);
                break;
            case "기쁨":
                holder.diaryImage.setImageResource(R.drawable.ic_diarysadcolor);
                break;
            case "불안":
                holder.diaryImage.setImageResource(R.drawable.ic_diarysadcolor);
                break;
            case "상처":
                holder.diaryImage.setImageResource(R.drawable.ic_diarysadcolor);
                break;
            case "슬픔":
                holder.diaryImage.setImageResource(R.drawable.ic_diarysadcolor);
                break;
            case "분노":
                holder.diaryImage.setImageResource(R.drawable.ic_diaryangrycolor);
                break;
        }

        holder.diaryTag.setText(mList.get(position).getTag());
        holder.diaryTitle.setText(mList.get(position).getDiaryTitle());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    @Override
    public void onItemClick(DiaryDataBeholder holder, View view, int position) {
        if(listener != null){ listener.onItemClick(holder,view,position); }
    }

    public void setOnItemClicklistener(OnDayClickListener listener){
        this.listener = listener;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    //ViewHolder
    public static class DiaryDataBeholder extends RecyclerView.ViewHolder {
        public TextView dayTitle;
        public ImageView diaryImage;
        public TextView diaryTitle;
        public TextView diaryTag;

        public DiaryDataBeholder(View itemView, final OnDayClickListener listener) {
            super(itemView);

            dayTitle = itemView.findViewById(R.id.day_title);
            diaryImage = itemView.findViewById(R.id.diaryImage);
            diaryTitle = itemView.findViewById(R.id.diaryTitle);
            diaryTag = itemView.findViewById(R.id.diaryTag);

            // recylerview 클릭 시
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onItemClick(DiaryDataBeholder.this, v, position);
                    }
                }
            });
        }

    }
    public DiaryData getItem(int position){
        return mList.get(position);
    }

    public void addItem(DiaryData item){ mList.add(item); }

    public void setItems(ArrayList<DiaryData> items){ this.mList = items; }

    public void setItem(int position, DiaryData item) {
        mList.set(position, item);
    }

    public int getCount() { return mList.size(); }

}
