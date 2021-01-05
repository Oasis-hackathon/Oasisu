package org.techtown.mydiarytop.ui.Repository;

import android.app.LauncherActivity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.mydiarytop.R;

import java.util.ArrayList;

public class MonthRecyclerAdapter extends RecyclerView.Adapter<MonthRecyclerAdapter.MyViewHolder>
        implements OnMonthClickListener {

    private ArrayList<monthItem> mList;
    private LayoutInflater mInflate;
    private Context mContext;


    OnMonthClickListener listener;

    public MonthRecyclerAdapter(Context context, ArrayList<monthItem> mList) {
        this.mList = mList;
        this.mInflate = LayoutInflater.from(context);
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.month_griditem, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //binding
        holder.monthText.setText(mList.get(position).getMonth());
        // holder.image.setText(mList.get(position).getImage());

        //Click event
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }




    @Override
    public void onItemClick(MyViewHolder holder, View view, int position) {
        if(listener != null){ listener.onItemClick(holder,view,position); }
    }

    public void setOnItemClicklistener(OnMonthClickListener listener){
        this.listener = listener;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    //ViewHolder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView monthText;

        public MyViewHolder(View itemView, final OnMonthClickListener listener) {
            super(itemView);

            monthText = itemView.findViewById(R.id.month_text);

            // recylerview 클릭 시
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onItemClick(MyViewHolder.this, v, position);
                    }

                }
            });
        }

    }
    public monthItem getItem(int position){
        return mList.get(position);
    }

    public void addItem(monthItem item){ mList.add(item); }

    public void setItems(ArrayList<monthItem> items){ this.mList = items; }

    public void setItem(int position, monthItem item) {
        mList.set(position, item);
    }

    public int getCount() { return mList.size(); }
}
