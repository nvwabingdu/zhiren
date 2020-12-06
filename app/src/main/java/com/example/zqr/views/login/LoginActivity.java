package com.example.zqr.views.login;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zqr.MainActivity;
import com.example.zqr.R;
import com.example.zqr.model.global.BaseActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Android Studio.
 * User: 86182
 * Date: 2020-11-27
 * Time: 13:06
 */
public class LoginActivity extends BaseActivity {
    /*登录*/
    private EditText et_username;
    private EditText et_password;
    private TextInputLayout tl_username;
    private TextInputLayout tl_password;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;
    private Button bt_login;

    @Override
    public int LayoutId() {
        return R.layout.activity_login_main;
    }

    @Override
    public void initView() {

        /*登录逻辑*/
        tl_username = (TextInputLayout) this.findViewById(R.id.login_main_tl_username);
        tl_password = (TextInputLayout) this.findViewById(R.id.login_main_tl_password);
        bt_login = (Button) this.findViewById(R.id.login_main_bt_login);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }



    /**
     * 登录逻辑
     */
    private void login() {
        String username = tl_username.getEditText().getText().toString();
        String password = tl_password.getEditText().getText().toString();
        if (!validateUserName(username)) {
            tl_username.setErrorEnabled(true);
            tl_username.setError("请输入正确的邮箱地址");
        } else if (!validatePassword(password)) {
            tl_password.setErrorEnabled(true);
            tl_password.setError("密码字数过少");
        } else {
            tl_username.setErrorEnabled(false);
            tl_password.setErrorEnabled(false);
            Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
            /*跳转main页面*/
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }

    private boolean validatePassword(String password) {
        return password.length() > 6;
    }

    private boolean validateUserName(String username) {
        matcher = pattern.matcher(username);
        return matcher.matches();
    }

}
