package com.example.schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schedule.diary.DaoDiary;
import com.example.schedule.diary.Diary;
import com.example.schedule.diary.NewActivity;

import java.util.List;

public class DiaryActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private Button mButton;
    private ListView mListView;
    private List<Diary> list;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        //初始化数据库
        DaoDiary.instance().init(getApplicationContext());
        mButton = (Button) findViewById(R.id.add);
        mListView = (ListView) findViewById(R.id.listview);
        list = DaoDiary.instance().selectAll();
        adapter = new MyAdapter();
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //新建日记
        Intent intent = new Intent();
        intent.setClass(this, NewActivity.class);
        if (list == null) {
            intent.putExtra("id", 1);
        } else {
            intent.putExtra("id", list.size()+2);
        }
        intent.putExtra("isshow", true);
        startActivity(intent);
    }

    /**
     * listview点击事件
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(this, NewActivity.class);
        intent.putExtra("id", list.get(position).getId());
        intent.putExtra("date", list.get(position).getDate());
        intent.putExtra("week", list.get(position).getWeek());
        intent.putExtra("weather", list.get(position).getWeather());
        intent.putExtra("content", list.get(position).getContent());
        intent.putExtra("isshow", false);
        startActivity(intent);
    }

    /**
     * listview的adapter
     */
    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if (list.size() != 0) {
                return list.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holoder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(DiaryActivity.this).inflate(R.layout.diary_item, null);
                holoder = new ViewHolder();
                holoder.date = (TextView) convertView.findViewById(R.id.item_date);
                holoder.delete = (TextView) convertView.findViewById(R.id.item_delete);
                holoder.edit = (TextView) convertView.findViewById(R.id.item_edit);
                convertView.setTag(holoder);
            } else {
                holoder = (ViewHolder) convertView.getTag();
            }
            holoder.date.setText(list.get(position).getDate());
            holoder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //编辑日记
                    Intent intent = new Intent();
                    intent.setClass(DiaryActivity.this, NewActivity.class);
                    intent.putExtra("id", list.get(position).getId());
                    intent.putExtra("date", list.get(position).getDate());
                    intent.putExtra("week", list.get(position).getWeek());
                    intent.putExtra("weather", list.get(position).getWeather());
                    intent.putExtra("content", list.get(position).getContent());
                    intent.putExtra("isshow", true);
                    startActivity(intent);
                }
            });
            holoder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //删除日记
                    if (DaoDiary.instance().delet(list.get(position).getId())) {
                        list.clear();
                        if (DaoDiary.instance().selectAll()!=null) {
                            list.addAll(DaoDiary.instance().selectAll());
                        }
                        //刷新listview
                        notifyDataSetChanged();
                        Toast.makeText(DiaryActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DiaryActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            return convertView;
        }

        /**
         * viewholder
         */
        class ViewHolder {
            TextView date;
            TextView delete;
            TextView edit;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        list.clear();
        list.addAll(DaoDiary.instance().selectAll());
        adapter.notifyDataSetChanged();
    }
}

