package com.example.videodetail.Base;

import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;


import com.example.videodetail.R;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hushendian on 2017/9/3.
 */

public class BaseSuppportFragment extends Fragment {
    Unbinder unbinder;
    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView Toolbar_title;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        if (!TextUtils.isEmpty(getTitle())) {
            Toolbar_title.setText(getTitle());
        }
    }

    protected String getTitle() {
        return "";
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
