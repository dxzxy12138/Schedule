package com.example.schedule.sleep;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.schedule.R;

import java.io.IOException;
import java.util.List;



/**
 * @author twn29004
 */


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardHolder> {

    private List<CardBean> mCardBeenList;
    private CardItemCallback mCardItemCallback;
    public CardAdapter(List<CardBean> cardBeenList){
        mCardBeenList = cardBeenList;
    }

    public void setCardItemCallback(CardItemCallback cardItemCallback){
        this.mCardItemCallback = cardItemCallback;
    }

    @Override
    public CardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("zs","onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_whitenoise_item,null);
        CardHolder holder = new CardHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CardHolder holder, int position) {
        Log.i("zs","onBindViewHolder");
        holder.imageView.setImageResource(mCardBeenList.get(position).getPic());
        holder.tv_media_name.setText(mCardBeenList.get(position).getMediaName());
        holder.tv_media_describe.setText(mCardBeenList.get(position).getMediaDercribe());
    }

    @Override
    public int getItemCount() {
        return mCardBeenList.size();
    }

    class CardHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView tv_media_name,tv_media_describe;
        public CardHolder(View itemView) {
            super(itemView);
            imageView =  itemView.findViewById(R.id.img);
            tv_media_name = itemView.findViewById(R.id.tv_media_name);
            tv_media_describe = itemView.findViewById(R.id.tv_media_describe);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mCardItemCallback!=null){
                        try {
                            mCardItemCallback.onItemClick(getAdapterPosition());
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    public interface CardItemCallback{
        void onItemClick(int position) throws IOException, InterruptedException;
    }
}