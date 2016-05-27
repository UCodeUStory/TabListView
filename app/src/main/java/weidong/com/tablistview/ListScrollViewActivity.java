package weidong.com.tablistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ListScrollViewActivity extends AppCompatActivity {

    int i = 40;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final TabListView listScrollView = (TabListView)findViewById(R.id.listview);

        final MyAdapter adapter = new MyAdapter();
        final MyAdapter2 adapter2 = new MyAdapter2();
        listScrollView.setAdapter(adapter);
        //listScrollView.setScrollPosition(40);
        listScrollView.setOnClickListener(new TabListView.OnClickHeaderListener() {
            @Override
            public void onClick(View v) {
               // i=i+20;
               // listScrollView.setScrollPosition(i);
            }
        });

        listScrollView.setOnTabChangeListener(new TabListView.OnTabChangeListener() {
            @Override
            public void onChange(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_left:
                        listScrollView.setAdapter(adapter);
                       // Log.i("qiyue","currentPosition="+listScrollView.getCurrentPosition());
                     //   listScrollView.setScrollPosition(50);
                        break;
                    case R.id.rb_right:
                        listScrollView.setAdapter(adapter2);
                     //   Log.i("qiyue","currentPosition2="+listScrollView.getCurrentPosition());
                    //    listScrollView.setScrollPosition(50);
                        break;

                }
            }
        });

        listScrollView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListScrollViewActivity.this,"position="+position,Toast.LENGTH_SHORT).show();
            }
        });

    }




    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return 20;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
             View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item,null);
            TextView textView = (TextView)view.findViewById(R.id.tv);
            textView.setText("first"+position);
            return view;
        }

    }

    class MyAdapter2 extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return 20;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,null);
            TextView textView = (TextView)view.findViewById(R.id.tv);
            textView.setText("second"+position);
            return view;
        }

    }
}
