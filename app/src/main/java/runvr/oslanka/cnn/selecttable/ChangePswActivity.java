package runvr.oslanka.cnn.selecttable;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cloud.lashou.utils.ShowMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import runvr.oslanka.cnn.selecttable.bean.UserBean;
import runvr.oslanka.cnn.selecttable.http.HttpFactory;
import runvr.oslanka.cnn.selecttable.util.SharedPreferencesUtil;


public class ChangePswActivity extends BaseActivity implements View.OnClickListener {
    //    @BindView(R.id.tv_titile)
//    TextView tv_titile;
//    @BindView(R.id.et_psw_num)
    EditText et_psw_num;
    EditText et_nick_name;
    //    @BindView(R.id.et_check_psw_num)
    EditText et_check_psw_num;
    //    @BindView(R.id.et_psw_new_num)
    EditText et_psw_new_num;
    TextView et_id;
    //    @BindView(R.id.btn_next)
    Button btn_next;
    //    @BindView(R.id.iv_back)
//    FrameLayout iv_back;
    public static String regexPsw = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{0,20}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViews();
        setListeners();
    }

    @Override
    public String getMyTitle() {
        return "用户设置";
    }

    @Override
    public int getResId() {
        return R.layout.activity_change_psw;
    }

    public void setViews() {
//        tv_titile = findViewById(R.id.tv_title);
//        tv_titile.setText("修改登录密码");
        et_psw_num = findViewById(R.id.et_psw_num);
        et_nick_name = findViewById(R.id.et_nick_name);
        et_check_psw_num = findViewById(R.id.et_check_psw_num);
        et_psw_new_num = findViewById(R.id.et_psw_new_num);
        et_id = findViewById(R.id.et_id);
        btn_next = findViewById(R.id.btn_next);
        et_id.setText(SharedPreferencesUtil.getString("user", ""));
        String nickName = SharedPreferencesUtil.getString("nickName", "");
        et_nick_name.setText(nickName);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                if (TextUtils.isEmpty(et_nick_name.getText().toString())) {
                    ShowMessage.showToast(this, "请设置用户昵称");
                } else if (!et_psw_new_num.getText().toString().equals(et_check_psw_num.getText().toString())) {
                    ShowMessage.showToast(this, "两次密码不一致");
                } else if (et_psw_num.getText().toString().equals(et_psw_new_num.getText().toString())) {
                    ShowMessage.showToast(this, "新密码不能与旧密码相同");
                }/* else if (!et_psw_num.getText().toString().matches(regexPsw)) {
                    ShowMessage.showToast(this, "密码格式错误，登录密码为1-20位之间数字和字母组合,且字母区分大小写！");
                } else if (!et_psw_new_num.getText().toString().matches(regexPsw)) {
                    ShowMessage.showToast(this, "密码格式错误，登录密码为1-20位之间数字和字母组合,且字母区分大小写！");
                }*/ else {

                    HttpFactory.getInstance().changePassword(et_nick_name.getText().toString(),
                            et_id.getText().toString(),
                            et_psw_num.getText().toString(),
                            et_psw_new_num.getText().toString()).enqueue(new Callback<UserBean>() {
                        @Override
                        public void onResponse(Call<UserBean> call, Response<UserBean> response) {
                            if (response.isSuccessful()) {
                                UserBean body = response.body();
                                if (body.getCode() == 0) {
                                    if (body.isIsLogin()) {
                                        if (body.isUpdate()) {
                                            ShowMessage.showToast(ChangePswActivity.this, "更新成功");
                                            SharedPreferencesUtil.putString("user", body.getData().getUsername());
                                            SharedPreferencesUtil.putString("nickName", body.getData().getNickName());
                                            finish();
                                        } else {
                                            ShowMessage.showToast(ChangePswActivity.this, "更新失败");
                                        }
                                    } else {
                                        ShowMessage.showToast(ChangePswActivity.this, "旧密码验证错误");
                                    }
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<UserBean> call, Throwable t) {

                        }
                    });
                    /*PersonInfo userInfo = mSession.getUserInfo();
                    ChangePsw changePsw = new ChangePsw(userInfo.getId(), userInfo.getPhonenum(), "", DigestUtils.md5Hex(et_psw_num.getText().toString().getBytes()), DigestUtils.md5Hex(et_psw_new_num.getText().toString().getBytes()));
                    HttpFactory.getInstance().changePassWord(userInfo.getId(), changePsw).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            ShowMessage.showToast(mContext, "密码设置成功");
                            mSession.setUserInfo(null);
                            mSession.setLogin(false);
                            ActivitiesManager.getInstance().popSpecialActivity(PersonInfoActivity.class);
                            ActivitiesManager.getInstance().popSpecialActivity(SettingActivity.class);
                            startActivity(new Intent(mContext, FastLoginActivity.class));
                            finish();

                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            ShowMessage.showToast(mContext, t.getMessage());
                        }
                    }, false);*/
                }
                break;

        }
    }


    public void setListeners() {
        btn_next.setOnClickListener(this);
//        iv_back.setOnClickListener(this);
        et_psw_new_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(et_psw_num.getText().toString())
                        && !TextUtils.isEmpty(et_check_psw_num.getText().toString())
                        && !TextUtils.isEmpty(et_psw_new_num.getText().toString())) {
                    btn_next.setEnabled(true);
                } else {
                    btn_next.setEnabled(false);
                }
            }
        });
        et_psw_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(et_psw_num.getText().toString())
                        && !TextUtils.isEmpty(et_check_psw_num.getText().toString())
                        && !TextUtils.isEmpty(et_psw_new_num.getText().toString())) {
                    btn_next.setEnabled(true);
                } else {
                    btn_next.setEnabled(false);
                }
            }
        });
        et_check_psw_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(et_psw_num.getText().toString())
                        && !TextUtils.isEmpty(et_check_psw_num.getText().toString())
                        && !TextUtils.isEmpty(et_psw_new_num.getText().toString())) {
                    btn_next.setEnabled(true);
                } else {
                    btn_next.setEnabled(false);
                }
            }
        });
    }
}
