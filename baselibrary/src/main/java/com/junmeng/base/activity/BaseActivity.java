package com.junmeng.base.activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.junmeng.base.BuildConfig;

/**
 *
 */
public class BaseActivity extends AppCompatActivity {

    public Toast longToast;
    public Toast shortToast;
    public Context context;
    @Deprecated
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("activity_lifecycle", this.getClass() + "  onCreate");
        super.onCreate(savedInstanceState);
        context = this;
        initLoadingDialog();
        processExtraData();
    }

    @Override
    public void recreate() {
        Log.i("activity_lifecycle", this.getClass() + "  recreate");
        super.recreate();
    }

    @Override
    protected void onStart() {
        Log.i("activity_lifecycle", this.getClass() + "  onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.i("activity_lifecycle", this.getClass() + "  onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.i("activity_lifecycle", this.getClass() + "  onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i("activity_lifecycle", this.getClass() + "  onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i("activity_lifecycle", this.getClass() + "  onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("activity_lifecycle", this.getClass() + "  onDestroy");
        cancelLoadingDialog();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.i("activity_lifecycle", this.getClass() + "  onNewIntent");
        super.onNewIntent(intent);
        setIntent(intent);// 在处理extra之前一定要调用,如果没有调用此句，在getIntent时可能拿不到数据
        processExtraData();
    }

    /**
     * 当launchMode为singleTask时或其他情况时onCreate并不执行，
     * 因此最好覆盖此方法处理intent传值
     */
    protected void processExtraData() {
    }

    /**
     * 初始化加载对话框
     */
    private void initLoadingDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("请稍候...");
    }

    /**
     * 设置全屏，需在setContentView之前调用
     */
    public void setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 获得屏幕宽度
     *
     * @return
     */
    public int getScreenWidth() {
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获得屏幕高度
     *
     * @return
     */
    public int getScreenHeight() {
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }


    /**
     * 显示加载对话框
     */
    public void showLoadingDialog() {
        showLoadingDialog("请稍候...");
    }


    /**
     * 显示加载对话框
     *
     * @param hint 提示信息
     * @deprecated Use a progress indicator such as ProgressBar inline inside of
     * an activity rather than using this modal dialog.
     */
    @Deprecated
    public void showLoadingDialog(final String hint) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog != null && !progressDialog.isShowing()) {
                    progressDialog.setMessage(hint);
                    progressDialog.show();
                }
            }
        });
    }

    /**
     * 取消加载对话框
     */
    @Deprecated
    public void cancelLoadingDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.cancel();
                }
            }
        });

    }


    /**
     * 显示Toast，以长时间显示
     *
     * @param msg 显示的内容（字符串）
     */
    public void showLongToast(final String msg) {
        if (!TextUtils.isEmpty(msg)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (longToast == null) {
                        longToast = Toast.makeText(getApplicationContext(), msg,
                                Toast.LENGTH_LONG);
                    } else {
                        longToast.setText(msg);
                    }

                    longToast.show();

                }

            });
        }
    }

    /**
     * 显示测试Toast,只在debug下生效
     *
     * @param msg
     */
    public void showDebugToast(final String msg) {
        if (BuildConfig.DEBUG) {
            showShortToast(msg);
        }
    }

    /**
     * 显示测试Toast,只在debug下生效
     *
     * @param resId
     */
    public void showDebugToast(@StringRes final int resId) {
        if (BuildConfig.DEBUG) {
            showShortToast(resId);
        }
    }

    /**
     * 显示Toast，以长时间显示
     *
     * @param resId 显示的内容id
     */
    public void showLongToast(@StringRes final int resId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (longToast == null) {
                    longToast = Toast.makeText(
                            BaseActivity.this.getApplicationContext(), resId,
                            Toast.LENGTH_LONG);
                } else {
                    longToast.setText(resId);
                }
                longToast.show();
            }
        });
    }

    /**
     * 显示Toast，以短时间显示
     *
     * @param msg 显示的内容（字符串）
     */
    public void showShortToast(final String msg) {
        if (!TextUtils.isEmpty(msg)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (shortToast == null) {
                        shortToast = Toast.makeText(getApplicationContext(), msg,
                                Toast.LENGTH_SHORT);
                    } else {
                        shortToast.setText(msg);
                    }

                    shortToast.show();

                }

            });
        }
    }

    /**
     * 显示Toast，以短时间显示
     *
     * @param resId 显示的内容id
     */
    public void showShortToast(@StringRes final int resId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (shortToast == null) {
                    shortToast = Toast.makeText(
                            BaseActivity.this.getApplicationContext(), resId,
                            Toast.LENGTH_SHORT);
                } else {
                    shortToast.setText(resId);
                }
                shortToast.show();
            }
        });
    }

    /**
     * 结束当前Activity
     * @param v
     */
    public void back(View v) {
        finish();
    }

    /**
     * 按返回键
     */
    @Override
    public void onBackPressed() {
        cancelLoadingDialog();
        super.onBackPressed();
    }

    /**
     * 跳转到Activity
     *
     * @param clas            Activity
     * @param isFinishCurrent 是否Finish当前Activity
     */
    public void gotoActivity(Class clas, boolean isFinishCurrent) {
        Intent intent = new Intent(this, clas);
        startActivity(intent);
        if (isFinishCurrent) {
            finish();
        }
    }

    /**
     * 跳转到Activity,保留当前Activity
     *
     * @param clas Activity
     */
    public void gotoActivity(Class clas) {
        gotoActivity(clas, false);
    }

    /**
     * 带返回码结束当前Activity，可以用来监听返回事件
     *
     * @param view
     * @param resultCode 如RESULT_OK
     */
    public void finishActivityWithResultCode(View view, int resultCode) {
        Intent intent = new Intent();
        setResult(resultCode, intent);
        this.finish();
    }
}
