package runvr.oslanka.cnn.selecttable;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cloud.lashou.retrofit.SmartCall;
import com.cloud.lashou.utils.AppUtils;
import com.cloud.lashou.widget.pulltorefresh.PullToRefreshBase;
import com.cloud.lashou.widget.recycle.SlideRecycleView;
import com.cloud.lashou.widget.recycle.SlideRecycleViewItemDecoration;
import com.cloud.lashou.widget.recycle.SlideRecycleViewItemVerticalDecoration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import runvr.oslanka.cnn.selecttable.bean.SelectPicBean;
import runvr.oslanka.cnn.selecttable.http.HttpFactory;
import runvr.oslanka.cnn.selecttable.util.AllCapTransformationMethod;

public class HistorySelectActivity extends BaseActivity {

    private View dataPickerLayout;
    private TextView tv_start_time;
    private TextView tv_end_time;
    private EditText tv_ydh;
    private EditText tv_user;
    private View begin_select;
    private DatePicker datePicker;
    private int position = -1;
    private List<SelectPicBean.DataBean> selectBeans;
    private ScenceListAdapter listAdapter;
    private SlideRecycleView mRecycleView;
    private SmartCall<SelectPicBean> call;
    private View cancel_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVIew();

        setListener();


    }

    @Override
    public String getMyTitle() {
        return "统计查询";
    }

    @Override
    public int getResId() {
        return R.layout.activity_history_select;
    }

    private void initVIew() {
        dataPickerLayout = findViewById(R.id.data_picker_layout);
        tv_start_time = findViewById(R.id.tv_start_time);
        tv_end_time = findViewById(R.id.tv_end_time);
        tv_ydh = findViewById(R.id.tv_ydh);
        tv_ydh.setTransformationMethod(new AllCapTransformationMethod(true));
        tv_user = findViewById(R.id.tv_user);
        datePicker = findViewById(R.id.data_picker);
        cancel_date = findViewById(R.id.cancel_date);
        begin_select = findViewById(R.id.begin_select);
        mProgressView = findViewById(R.id.login_progress);
        mRecycleView = findViewById(R.id.recycle_view);
        SlideRecycleViewItemVerticalDecoration item = new SlideRecycleViewItemVerticalDecoration(this,
                SlideRecycleViewItemDecoration.VERTICAL_LIST, getResources().getDrawable(R.drawable.driver_line12));
        mRecycleView.getRefreshableView().addItemDecoration(item);  //添加分割线
        mRecycleView.setScrollingWhileRefreshingEnabled(false); //设置刷新的时候不可滑动
        mRecycleView.getRefreshableView().setLayoutManager(new LinearLayoutManager(this)); //添加布局管理器
        mRecycleView.setMode(PullToRefreshBase.Mode.BOTH);
        selectBeans = new ArrayList<>();

    }

    private void setListener() {
        tv_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (call != null) {
                    call.cancel();
                }
                tv_start_time.requestFocus();
                tv_ydh.clearFocus();
                tv_user.clearFocus();
                AppUtils.hideSoftKeybord(HistorySelectActivity.this);
                dataPickerLayout.setVisibility(View.VISIBLE);
                position = 0;

            }
        });
        tv_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (call != null) {
                    call.cancel();
                }
                tv_end_time.requestFocus();
                tv_ydh.clearFocus();
                tv_user.clearFocus();
                AppUtils.hideSoftKeybord(HistorySelectActivity.this);
                dataPickerLayout.setVisibility(View.VISIBLE);
                position = 1;
            }
        });
        begin_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (call != null) {
                    call.cancel();
                }
                begin_select.requestFocus();
                tv_ydh.clearFocus();
                tv_user.clearFocus();
                AppUtils.hideSoftKeybord(HistorySelectActivity.this);
                beginSelect();

            }
        });
        datePicker.setMode(DPMode.SINGLE);
        datePicker.setDate(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH) + 1);
        datePicker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {

                TextView textView = position == 1 ? tv_end_time : tv_start_time;
                textView.setText(date);
                dataPickerLayout.setVisibility(View.GONE);
            }
        });
        cancel_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position==1)tv_end_time.setText("");
                if (position==0)tv_start_time.setText("");
                dataPickerLayout.setVisibility(View.GONE);
            }
        });
        /*dataPickerLayout.findViewById(R.id.select_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = position == 1 ? tv_end_time : tv_start_time;
                textView.setText(""+datePicker.get+ datePicker.getMonth() + datePicker.getDayOfMonth());
                dataPickerLayout.setVisibility(View.GONE);
            }
        });*/
    }

    private void beginSelect() {
        showProgress(true);
        call = HttpFactory.getInstance().getSelectPic(tv_start_time.getText().toString(), tv_end_time.getText().toString(), tv_ydh.getText().toString(), tv_user.getText().toString());
        call.enqueue(new Callback<SelectPicBean>() {
            @Override
            public void onResponse(Call<SelectPicBean> call, Response<SelectPicBean> response) {
                selectBeans.clear();
                showProgress(false);
                if (response.isSuccessful()) {
                    SelectPicBean body = response.body();
                    if (body.getCode() == 0) {
                        selectBeans.addAll(body.getData());
                        listAdapter = new ScenceListAdapter(HistorySelectActivity.this, selectBeans, 0);
                        mRecycleView.getRefreshableView().setAdapter(listAdapter);
                    }
                }
                call = null;
            }

            @Override
            public void onFailure(Call<SelectPicBean> call, Throwable t) {
                showProgress(false);
                call = null;
            }
        });
    }

    private View mProgressView;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);


            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }
}
