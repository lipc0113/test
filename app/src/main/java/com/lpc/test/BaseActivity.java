package com.lpc.test;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @ Author     ：v_lipengcheng
 * @ Date       ：Created in 10:56 2019-08-07
 * @ Description：
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
    }

    protected abstract void initView();

    protected abstract void initData();
}
