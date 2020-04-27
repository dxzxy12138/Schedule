package com.example.schedule.sleep;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author twn29004
 */
public class CardManager extends RecyclerView.LayoutManager {

    /**
     * MAX_COUNT 最大显示个数
     */

    public static int MAX_COUNT = 4;
    /**
     * SCALE_RATIO 缩放系数
     */
    public static float SCALE_RATIO = 0.03f;
    /**
     * TRANS_RATIO 平移系数
     */
    public static float TRANS_RATIO = 5f;


    public CardManager(Context context) {
        //初始化平移系数
        TRANS_RATIO = (int) (8f * context.getResources().getDisplayMetrics().density);
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return  new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        int itemCount = getItemCount();
        //如果RecyclerView中只有一个item或者没有，什么都不做
        if(itemCount<1){
            return;
        }
        //最底部item的角标
        int bottomPosition;
        if(itemCount<MAX_COUNT){
            bottomPosition = 0;
        }else {
            bottomPosition = itemCount - MAX_COUNT;
        }
        //从最底层的item开始摆放
        for(int i =bottomPosition;i<itemCount;i++){
            //从缓冲池中获取到itemView
            View view = recycler.getViewForPosition(i);
            //将itemView添加到RecyclerView中
            addView(view);
            //测量itemView
            measureChildWithMargins(view,0,0);
            //recyclerView宽度-itemView宽度
            int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
            int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);
            //将itemView水平居中
            layoutDecoratedWithMargins(view,widthSpace/2,heightSpace/2
                    ,widthSpace/2+getDecoratedMeasuredWidth(view)
                    ,heightSpace/2+getDecoratedMeasuredHeight(view));
        }
    }
}
