package com.example.schedule.sleep;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.example.schedule.R;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

/**
 * @author twn29004
 */

public class SleepActivity extends AppCompatActivity implements
        CardHelperCallback.OnItemTouchCallbackListener
        ,CardAdapter.CardItemCallback{

    private RecyclerView recyclerView;
    private List<CardBean> mCardBeanList;
    private CardAdapter mCardAdapter;
    private MediaPlayer mediaPlayer;
    private ImageView mIvTimer;
    private ImageView mInQuiet;
    // 助眠时长的默认值
    private long HowLong = 30;
    private ProgressBar mPbBuffer;

    // 两个线程池，一个用于计时，一个用于操作MediaPlayer
    private ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(2);
    ExecutorService singleExecutorService = Executors.newSingleThreadExecutor();

    /**
     * flag 用于判断点击是播放还是暂停
     * 初始化为false，开始为停止播放，点击一下，开始播放，同时flag取反
     * 下次再点击的时候开始播放
     */

    private boolean flag = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);
        init();
    }

    //加载更多
    public void loadMore(){
        List<CardBean> cardBeenList = new ArrayList<>();

        CardBean bean0 = new CardBean();
        bean0.setMediaUrl("http://twn29004.top/wp-content/uploads/sea.mp3");
        bean0.setMediaName("海洋");
        bean0.setMediaDercribe("海边漫步的气息");
        bean0.setPic(R.drawable.sea);

        CardBean bean1 = new CardBean();
        bean1.setMediaUrl("https://twn29004.top/wp-content/uploads/rain.mp3");
        bean1.setMediaName("夏雨");
        bean1.setMediaDercribe("六月与夏雨的邂逅");
        bean1.setPic(R.drawable.rain);

        CardBean bean2 = new CardBean();
        bean2.setMediaUrl("http://twn29004.top/wp-content/uploads/brid.mp3");
        bean2.setMediaName("晨鸟");
        bean2.setMediaDercribe("唤醒美好一天");
        bean2.setPic(R.drawable.brid);

        CardBean bean3 = new CardBean();
        bean3.setMediaUrl("http://twn29004.top/wp-content/uploads/fire.mp3");
        bean3.setMediaName("炉火");
        bean3.setMediaDercribe("温暖冬日的闲暇时光");
        bean3.setPic(R.drawable.fire);
/*
        CardBean bean4 = new CardBean();
        bean4.setMediaUrl("http://twn29004.top/wp-content/uploads/Pianoboy高至豪-The-truth-that-you-leave.mp3");
        bean4.setMediaName("5");
        bean4.setMediaDercribe("球队：芝加哥公牛");
        bean4.setPic(R.drawable.jordan);

        CardBean bean5 = new CardBean();
        bean5.setMediaUrl("http://twn29004.top/wp-content/uploads/Delacey-Dream-It-Possible.mp3");
        bean5.setMediaName("6");
        bean5.setMediaDercribe("球队：迈阿密热火");
        bean5.setPic(R.drawable.wade);

        CardBean bean6 = new CardBean();
        bean6.setMediaUrl("http://twn29004.top/wp-content/uploads/Pianoboy高至豪-The-truth-that-you-leave.mp3");
        bean6.setMediaName("7");
        bean6.setMediaDercribe("球队：达拉斯小牛");
        bean6.setPic(R.drawable.derk);

        CardBean bean7 = new CardBean();
        bean7.setMediaUrl("http://twn29004.top/wp-content/uploads/Delacey-Dream-It-Possible.mp3");
        bean7.setMediaName("8");
        bean7.setMediaDercribe("球队：波特兰开拓者");
        bean7.setPic(R.drawable.lilade);

        CardBean bean8 = new CardBean();
        bean8.setMediaUrl("http://twn29004.top/wp-content/uploads/Pianoboy高至豪-The-truth-that-you-leave.mp3");
        bean8.setMediaName("9");
        bean8.setMediaDercribe("球队：金州勇士");
        bean8.setPic(R.drawable.curry01);

        CardBean bean9 = new CardBean();
        bean9.setMediaUrl("http://twn29004.top/wp-content/uploads/Delacey-Dream-It-Possible.mp3");
        bean9.setMediaName("10");
        bean9.setMediaDercribe("球队：波士顿凯尔特人");
        bean9.setPic(R.drawable.irving01);
*/
        cardBeenList.add(bean0);
        cardBeenList.add(bean1);
        cardBeenList.add(bean2);
        cardBeenList.add(bean3);
/*
        cardBeenList.add(bean4);
        cardBeenList.add(bean5);
        cardBeenList.add(bean6);
        cardBeenList.add(bean7);
        cardBeenList.add(bean8);
        cardBeenList.add(bean9);
*/
        Collections.reverse(cardBeenList);
        mCardBeanList.addAll(0,cardBeenList);
    }


    private void init(){
        recyclerView = findViewById(R.id.rv_content);
        mIvTimer = findViewById(R.id.iv_timer);

        // 使静音图标可见
        mInQuiet = findViewById(R.id.iv_quiet);
        mInQuiet.setVisibility(View.VISIBLE);
        mCardBeanList = new ArrayList<>();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setLooping(true);

        mPbBuffer = findViewById(R.id.pb_buffer);
        mPbBuffer.setVisibility(View.INVISIBLE);

        loadMore();
        CardManager manager = new CardManager(this);
        recyclerView.setLayoutManager(manager);
        mCardAdapter = new CardAdapter(mCardBeanList);
        mCardAdapter.setCardItemCallback(this);
        recyclerView.setAdapter(mCardAdapter);
        initTouch();

        mIvTimer.setOnClickListener(new OnClick());
    }

    public void initTouch(){
        CardHelperCallback itemTouchHelpCallback = new CardHelperCallback();
        itemTouchHelpCallback.setListener(this);
        ItemTouchHelper helper = new ItemTouchHelper(itemTouchHelpCallback);
        //将ItemTouchHelper和RecyclerView进行绑定
        helper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onSwiped(int position,int direction) {

        if(mCardBeanList!=null){
            for (int i = 0;i<recyclerView.getChildCount();i++){
                View view = recyclerView.getChildAt(i);
                view.setAlpha(1);
            }
            // 如果切换之后还在播放，则停止播放
            mInQuiet.setVisibility(View.VISIBLE);
            mediaPlayer.stop();
            flag = false;
            mCardBeanList.remove(position);
            // 加载更多
            if(mCardBeanList.size()<CardManager.MAX_COUNT){
                loadMore();
            }
            mCardAdapter.notifyDataSetChanged();
        }
    }


    /**
     * 每个card的点击事件
     */
    @Override
    public void onItemClick(final int position) throws IOException, InterruptedException {
        // 由于切换到新的页面，需要重新初始化media,播放新的歌曲
        if(!flag){
            // 创建一个线程用于处理media player的一系列事务，否则的话会导致ProgressBar的显示不正常
            singleExecutorService.execute(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.reset();
                    Log.i("Media","show progressBar");
                    try {
                        mediaPlayer.setDataSource(mCardBeanList.get(position).getMediaUrl());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mediaPlayer.setLooping(true);
                    try {
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.i("Media","创建了新的线程用于显示progressBar");
                }
            });
            mInQuiet.setVisibility(View.INVISIBLE);
            mPbBuffer.setVisibility(View.VISIBLE);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mPbBuffer.setVisibility(View.INVISIBLE);
                    mediaPlayer.start();
                }
            });
            Log.i("Media","initial mediaplayer and play music");
            flag = true;
        }
        else if(!mediaPlayer.isPlaying()){
            mInQuiet.setVisibility(View.INVISIBLE);
            mediaPlayer.start();
            Log.i("Media","start playing music");
        }
        else if(mediaPlayer.isPlaying()){
            mInQuiet.setVisibility(View.VISIBLE);
            mediaPlayer.pause();
            Log.i("Media","pause play music");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    /**
     * 描述Timer的点击事件
     */
    class OnClick implements View.OnClickListener{

        final String[] time = new String[]{"10","20","30","40","50","60"};
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SleepActivity.this);
            builder.setSingleChoiceItems(time, 100, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    HowLong = Integer.parseInt(time[which]);
                    Toast.makeText(SleepActivity.this,"音乐将在" + HowLong + "分钟后停止播放",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            builder.setCancelable(false).show();

            // 创建线程，开始计时，时间到的话停止播放，释放播放器的资源
            scheduledExecutorService.schedule(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    // 返回到主界面
                    android.os.Process.killProcess(android.os.Process.myPid());
                    Log.i("Media","停止播放"+HowLong);
                }
            },HowLong,TimeUnit.MINUTES);
        }
    }

    class MyRunable implements Runnable{
        @Override
        public void run() {
            mPbBuffer.setVisibility(View.VISIBLE);
        }
    }
}
