package weidong.com.tablistview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Scroller;

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
    private VelocityTracker mVelocityTracker;
    private float mVelocityX;
    private float mVelocityY;
    private int mMaxVelocity;
    private int mPointerId;
    private Scroller mScroller;
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
        mMaxVelocity = ViewConfiguration.get(context).getMaximumFlingVelocity();
        mScroller = new Scroller(context);
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
        acquireVelocityTracker(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getY();
                mPointerId = ev.getPointerId(0);
                Log.i("qiyue","downY="+ev.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                float currentY = ev.getY();


                mVelocityTracker.computeCurrentVelocity(1000, mMaxVelocity);
                mVelocityX = mVelocityTracker.getXVelocity(mPointerId);
                mVelocityY = mVelocityTracker.getYVelocity(mPointerId);

                difference = (currentY - downY) /3;
                int distance = (int)difference;
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
                        //   Log.i("qiyue","false");
                        //  return false;
                    }else{
                        //   Log.i("qiyue","true");
                        return true;
                    }
                }


                break;
            case MotionEvent.ACTION_UP:
                float s = 0f;
                float v = mVelocityY;
                int t = 0;
                int a = 1000;
                int finals = 0;
                if (v>0) {
                    s = -((v * v) / (2 * a));
                }else if (v<0){
                    s = (v * v) / (2 * a);
                }
                finals = (int)s;

                //    Log.i("y","finals>>>>>>>>>>>>>>>>>>>>>>>>>>>="+finals);
                //    Log.i("y","mCurrentPosition="+mCurrentPosition);
                if (firstVisibleItemPosition == 0) {
                    if (v < 0) {
                        //   Log.i("y", "22222222222");
                        mMHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
                        mCurrentPosition = -mHeaderViewHeight;
                    } else {
                        //    Log.i("y", "11111111111");
                        mMHeaderView.setPadding(0, 0, 0, 0);
                        mCurrentPosition = 0;
                    }
                }
/*
                        if (firstVisibleItemPosition == 0) {
                            //  smoothScrollBy(0, 50);
                            final ValueAnimator valueAnimator = ValueAnimator
                                    .ofInt(finals, 0);
                            valueAnimator.setDuration(500);
                            valueAnimator.setTarget(mMHeaderView);
                            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {
                                    int paddingTop = mCurrentPosition + (int) animation.getAnimatedValue();
                                   // if (Math.abs(paddingTop)<Math.abs(mHeaderViewHeight)) {
                                        mMHeaderView.setPadding(0, mCurrentPosition + (int) animation.getAnimatedValue(), 0, 0);
                                 //   }else{
                                     *//*   if (paddingTop>0) {
                                            mMHeaderView.setPadding(0, mHeaderViewHeight, 0, 0);
                                        }else{
                                            mMHeaderView.setPadding(0,-mHeaderViewHeight, 0, 0);
                                        }
                                    }*//*
                                }
                            });
                            valueAnimator.start();*/
                //   }
                // }
                break;
            default:
                break;
        }

        return super.onTouchEvent(ev);
    }

    //调用此方法设置滚动的相对偏移
    public void smoothScrollBy(int dx, int dy) {

        //设置mScroller的滚动偏移量
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    private void acquireVelocityTracker(final MotionEvent event) {
        if(null == mVelocityTracker) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
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


    @Override
    public void computeScroll() {

        //先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {

            //这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());

            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
        super.computeScroll();
    }
}
