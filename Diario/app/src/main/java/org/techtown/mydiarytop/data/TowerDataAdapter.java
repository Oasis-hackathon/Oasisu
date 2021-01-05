package org.techtown.mydiarytop.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.mydiarytop.MainActivity;
import org.techtown.mydiarytop.R;

import java.util.ArrayList;

public class TowerDataAdapter extends RecyclerView.Adapter<TowerDataAdapter.TowerDataBeholder>
        implements OnTowerDataClickListener {

    private ArrayList<TowerData> mList;
    private LayoutInflater mInflate;
    private Context mContext;

    OnTowerDataClickListener listener;

    public TowerDataAdapter(Context context, ArrayList<TowerData> mList) {
        this.mList = mList;
        this.mInflate = LayoutInflater.from(context);
        this.mContext = context;
    }

    @NonNull
    @Override
    public TowerDataBeholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.tower_linear_item, parent, false);
        TowerDataBeholder viewHolder = new TowerDataAdapter.TowerDataBeholder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TowerDataAdapter.TowerDataBeholder holder, int position) {

        if (position == 0) {
            holder.towerBackground.setBackgroundResource(R.drawable.towertop);
        } else if (position == 7) {
            holder.towerBackground.setBackgroundResource(R.drawable.towerbottom);
            holder.towerRoomImage.setImageResource(mList.get(position).getTowerImage());

        } else {
            holder.towerBackground.setBackgroundResource(R.drawable.towerbackground);
            if ((position != 6) & (mList.get(position).getFurnitureCount() == 0)) {
                holder.towerRoomImage.setVisibility(View.INVISIBLE);
            } else {
                holder.towerRoomImage.setImageResource(mList.get(position).getTowerImage());
            }
        }
    }




    @Override
    public int getItemCount() {
        return mList.size();
    }


    @Override
    public void onItemClick(TowerDataAdapter.TowerDataBeholder holder, View view, int position) {
        if(listener != null){ listener.onItemClick(holder,view,position); }
    }

    public void setOnItemClicklistener(OnTowerDataClickListener listener){
        this.listener = listener;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    //ViewHolder
    public static class TowerDataBeholder extends RecyclerView.ViewHolder {
        public ImageView towerRoomImage;
        public LinearLayout towerBackground;

        public TowerDataBeholder(View itemView, final OnTowerDataClickListener listener) {
            super(itemView);

            towerRoomImage = (ImageView) itemView.findViewById(R.id.towerRoomImage);
            towerBackground = (LinearLayout) itemView.findViewById(R.id.towerBackground);

            // recylerview 클릭 시
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onItemClick(TowerDataAdapter.TowerDataBeholder.this, v, position);
                    }
                }
            });
        }

    }
    public TowerData getItem(int position){
        return mList.get(position);
    }

    public void addItem(TowerData item){ mList.add(item); }

    public void setItems(ArrayList<TowerData> items){ this.mList = items; }

    public void setItem(int position, TowerData item) {
        mList.set(position, item);
    }

    public int getCount() { return mList.size(); }


}
