package com.example.videodetail.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.VideoView;
import com.example.videodetail.Base.BaseActivity;

import com.example.videodetail.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;


import java.util.ArrayList;
import java.util.List;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecycleViewActivity extends BaseActivity {
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.player)
    VideoView ivImage;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    @BindView(R.id.collaps)
    CollapsingToolbarLayout collaps;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    private boolean isPreapered;
    private List<String > list;
    private CollapsingToolbarLayoutState state;
    private int imageHeight = 0;
    private boolean hasMesure = false;
    private int collHeight = 0;

    protected void loadDatas() {
        recycleView.setLayoutManager(new LinearLayoutManager(RecycleViewActivity.this));
        ivImage.setUrl("https://mvvideo10.meitudata.com/5eb41c9f3eb6blzeqq9idu8378_H264_1_87336388071ea.mp4"); //设置视频地址
        StandardVideoController controller = new StandardVideoController(this);
        controller.addDefaultControlComponent("", false);
        ivImage.setVideoController(controller); //设置控制器
        ivImage.start();
        recycleView.setAdapter(new RecyleViewAdapter(this,list));
    }


    protected void getData() {
        list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Log.d("initData", "initData: " + i % 3);
            list.add(i, "");
        }

    }


    private enum CollapsingToolbarLayoutState {EXPANDED, COLLAPSED, INTERNEDIATE}

    private ViewGroup.LayoutParams layoutParams;

    @Override
    protected void initView(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 隐藏状态栏
        setContentView(R.layout.coordinatorlayout);
        ButterKnife.bind(this);
        getData();
        loadDatas();
//        ViewGroup.LayoutParams layoutParams = collaps.getLayoutParams();
//        layoutParams.height = ScreenUtil.getScreenHeight(this)+ScreenUtil.getStatusBarHeight(this);
//        collaps.setLayoutParams(layoutParams);
        ivImage.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!hasMesure) {
                    imageHeight = ivImage.getHeight();
                    RecycleViewActivity.this.layoutParams = ivImage.getLayoutParams();
                    collHeight = collaps.getMinimumHeight();
                    Log.i(TAG, "initData:imageHeight==== " + imageHeight);
                    Log.i(TAG, "initData: collHeight====" + collHeight);
                }
                hasMesure = true;
            }
        });

        setListener();
    }

    @Override
    protected void initVariables() {

//        loadData();//解决viewpager不滑动不显示数据问题

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private static final String TAG = "RecycleViewActivity";

    private void setListener() {
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset == 0) {
                    if (state != CollapsingToolbarLayoutState.EXPANDED) {
                        state = CollapsingToolbarLayoutState.EXPANDED;
                    }
                    if (layoutParams != null) {
                        layoutParams.height = imageHeight;
                        ivImage.setLayoutParams(layoutParams);
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {

                    if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                        state = CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠
                    }
                } else {
                    if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                        state = CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
                    }
                    float percentage = ((float) Math.abs(verticalOffset) / appBarLayout.getTotalScrollRange());
                    if (layoutParams != null) {
                        int height = (int) ((imageHeight - collHeight) * (1 - percentage));
                        layoutParams.height = collHeight + height;
                        ivImage.setLayoutParams(layoutParams);
                    }
                }


            }
        });
    }
}