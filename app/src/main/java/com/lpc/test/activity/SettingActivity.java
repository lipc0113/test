package com.lpc.test.activity;

import android.os.Looper;
import android.os.MessageQueue;
import android.util.Log;
import android.util.Printer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.lpc.test.R;
import com.lpc.test.base.BaseActivity;
import com.lpc.test.utils.LogUtil;
import com.lpc.test.utils.SkinUtil;
import com.lpc.test.utils.ToastUtils;

/**
 * @ Author     ：v_lipengcheng
 * @ Date       ：Created in 19:25 2019-08-26
 * @ Description：
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener
    , SkinUtil.SkinILoaderListener {

    private TextView tvSkinLight;
    private TextView tvSkinDark;
    private long startTime;
    private FrameLayout flOuter;

    @Override
    protected int getContentViewResid() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {

        tvSkinLight = findViewById(R.id.tv_skin_light);
        tvSkinDark = findViewById(R.id.tv_skin_dark);
        flOuter = findViewById(R.id.fl_outer);
        startTime = System.currentTimeMillis();
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                LogUtil.d("time = " + (System.currentTimeMillis() - startTime));
                LogUtil.d("queueIdle");
                return false;
            }
        });
        // 监听msg.target.dispatchMessage(msg)方法执行
        Looper.myLooper().setMessageLogging(new Printer() {
            //分发和处理消息开始前的log
            private static final String START = ">>>>> Dispatching";
            //分发和处理消息结束后的log
            private static final String END = "<<<<< Finished";
            @Override
            public void println(String x) {

                if (x.startsWith(START)) {
                    startTime2 = System.currentTimeMillis();
                    //开始计时
                    LogUtil.d("Message start = " + startTime2);
                }
                if (x.startsWith(END)) {
                    //结束计时，并计算出方法执行时间
                    LogUtil.d("Message end =" + (System.currentTimeMillis() - startTime2));
                }

            }
        });
    }
    private long startTime2 = 0;

    @Override
    protected void initData() {

        tvSkinLight.setOnClickListener(this);
        tvSkinDark.setOnClickListener(this);
        flOuter.setOnClickListener(this);
        tvSkinDark.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    LogUtil.d("MotionEvent.ACTION_DOWN");
                }else if(event.getAction() == MotionEvent.ACTION_MOVE){
                    LogUtil.d("MotionEvent.ACTION_MOVE");
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    LogUtil.d("MotionEvent.ACTION_UP");
                }else if(event.getAction() == MotionEvent.ACTION_CANCEL){
                    LogUtil.d("MotionEvent.ACTION_CANCEL");
                }

                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_skin_light:
                SkinUtil.restoreDefaultTheme();
                break;
            case R.id.tv_skin_dark:
                SkinUtil.setDarkSkin();
                break;
            case R.id.fl_outer:
                LogUtil.d("flOuter响应了事件");
                break;
        }
    }

    @Override
    public void onSkinStart() {

    }

    @Override
    public void onSkinSuccess() {
        ToastUtils.showShortToast("换肤成功");
    }

    @Override
    public void onSkinFailed() {
        ToastUtils.showShortToast("换肤失败");
    }

    public void outerClick(View view) {

    }
}
