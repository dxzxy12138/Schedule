package com.example.schedule.sleep;

import android.graphics.Canvas;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;



/**
 * @author twn29004
 */

public class CardHelperCallback extends ItemTouchHelper.Callback {


    private OnItemTouchCallbackListener mListener;

    public void setListener(OnItemTouchCallbackListener listener){
        this.mListener = listener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = 0;
        int swipeFlags = 0;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof CardManager) {
            //允许上下滑动
            swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT |
                    ItemTouchHelper.UP | ItemTouchHelper.DOWN ;
        }
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    //itemView滑出了屏幕
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Log.i("zs","direction  "+direction);
        if(mListener!=null){
            mListener.onSwiped(viewHolder.getAdapterPosition(),direction);
        }
    }

    /**
     * 是否是长按的时候进行拖拽操作,
     * 返回true长按可以进行拖拽
     * 返回false可以进行手动触发拖拽
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    /**
     * 是否可以被滑动
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        float trans;
        //以偏移量大的方向为标准
        if(Math.abs(dX)>Math.abs(dY)){
            trans = Math.abs(dX);
        }else {
            trans = Math.abs(dY);
        }
        //滑动比例
        float ratio = trans/getThreshold(recyclerView,viewHolder);
        if(ratio>1){
            ratio = 1;
        }
        //获取itemView总量
        int itemCount = recyclerView.getChildCount();

        //移除时为底部显示的View增加动画
        /*
        for (int i = 1;i<CardManager.MAX_COUNT-1;i++){
            View view = recyclerView.getChildAt(i);
            float t = 1/(1-CardManager.SCALE_RATIO*ratio)-CardManager.SCALE_RATIO*(itemCount-i-1);
            view.setScaleX(t);
            view.setTranslationY(-CardManager.TRANS_RATIO*ratio+CardManager.TRANS_RATIO*(itemCount-i-1));
        }
         */

        //为被拖动的View增加透明度动画
        View view = recyclerView.getChildAt(itemCount-1);

        view.setAlpha(1 - Math.abs(ratio) * 0.2f);
    }

    /**
     * 获取划出屏幕的距离阈值
     */

    private float getThreshold(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return recyclerView.getWidth() * getSwipeThreshold(viewHolder);
    }

    public interface OnItemTouchCallbackListener {
        /*
         * item被滑动的回调方法
         */
        void onSwiped(int position, int direction);
    }
}