package weidong.com.tablistview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;

/**
 * @ Author: qiyue (ustory)
 * @ Email: qiyuekoon@foxmail.com
 * @ Data:2016/5/24 0024
 */
public class TabListView extends ListView implements AbsListView.OnScrollListener {
    LayoutInflater mInflater;
    private LinearLayout mMHeaderView;
    private int mHeaderViewHeight;
    private float downY;
    private float difference;
    private int mCurrentPosition;
    private int firstVisibleItemPosition = -1;
    private boolean isScrollToBottom ;
    private boolean isHeaderOnTouch = false;
    private OnTabChangeListener mOnTabChangeListener;

    public TabListView(Context context) {
        super(context);
        init(context);
    }

    public TabListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TabListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        // 获取LayoutInflater实例对象
        mInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        mMHeaderView =(LinearLayout) View.inflate(getContext(), R.layout.header_item, null);

        RadioGroup radioGroup = (RadioGroup)mMHeaderView.findViewById(R.id.rg_first_header);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mOnTabChangeListener.onChange(group,checkedId);
            }
        });
       // mMHeaderView.setPadding(0,-300,0,0);
        mMHeaderView.measure(0, 0);
        mHeaderViewHeight = mMHeaderView.getMeasuredHeight();

      //  mCurrentPosition = mHeaderViewHeight;
        Log.i("qiyue","mHeaderViewHeight="+mHeaderViewHeight);
        mMHeaderView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickListener.onClick(v);
            }
        });
        addHeaderView(mMHeaderView);
        this.setOnScrollListener(this);
        mMHeaderView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                Log.i("qiyue","header onTouch");
                isHeaderOnTouch = true;
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downY = ev.getY();
                        Log.i("qiyue","header downY"+downY);
                        break;
                }
                return true;
            }
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
            Log.i("qiyue","onTouchEvent");
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downY = ev.getY();

                    Log.i("qiyue","downY="+ev.getY());
                    break;
                case MotionEvent.ACTION_MOVE:
                    float currentY = ev.getY();
                    difference = (currentY - downY) /3;
                    int distance = (int)difference;
                    if (distance>0){
                        difference = 50;
                    }else if(distance<0){
                        difference = -50;
                    }
                 /*   if (isHeaderOnTouch){
                        isHeaderOnTouch = false;
                        return true;
                    }*/
                    Log.i("qiyue","difference="+difference);
                    if (firstVisibleItemPosition == 0) {
                        mCurrentPosition = mCurrentPosition + (int) difference;
                        if (mCurrentPosition<-mHeaderViewHeight){
                            mCurrentPosition =-mHeaderViewHeight;
                        }
                        if (mCurrentPosition>0){
                            mCurrentPosition =0;
                        }

                        Log.i("qiyue", "mCurrentPosition3=" + mCurrentPosition);
                        mMHeaderView.setPadding(0, mCurrentPosition, 0, 0);
                        if (mCurrentPosition==-mHeaderViewHeight) {
                            Log.i("qiyue","false");
                          //  return false;
                        }else{
                            Log.i("qiyue","true");
                            return true;
                        }
                    }


                    break;
                case MotionEvent.ACTION_UP:

                    break;
                default:
                    break;
            }

        return super.onTouchEvent(ev);
    }

    public void setScrollPosition(int position){
        mMHeaderView.setPadding(0,-position,0,0);
    }

    private OnClickHeaderListener mOnClickListener;

    public OnClickHeaderListener getOnClickListener() {
        return mOnClickListener;
    }

    public void setOnClickListener(OnClickHeaderListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public OnTabChangeListener getOnTabChangeListener() {
        return mOnTabChangeListener;
    }

    public void setOnTabChangeListener(OnTabChangeListener onTabChangeListener) {
        mOnTabChangeListener = onTabChangeListener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        firstVisibleItemPosition = firstVisibleItem;
        Log.i("qiyue","firstVisibleItem="+firstVisibleItem);
        if (getLastVisiblePosition() == (totalItemCount - 1)) {
            isScrollToBottom = true;
        } else {
            isScrollToBottom = false;
        }
    }


    public int getHeaderViewHeight() {
        return mHeaderViewHeight;
    }

    public void setHeaderViewHeight(int headerViewHeight) {
        mHeaderViewHeight = headerViewHeight;
    }

    public interface OnClickHeaderListener{
        public void onClick(View v);
    }

    public interface OnTabChangeListener{
        public void onChange(RadioGroup group, int checkedId);
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        mCurrentPosition = currentPosition;
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        Log.i("qiyue",">>>>>>>>>>>="+mCurrentPosition);
        mMHeaderView.setPadding(0, mCurrentPosition, 0, 0);
        super.setAdapter(adapter);
    }
}
