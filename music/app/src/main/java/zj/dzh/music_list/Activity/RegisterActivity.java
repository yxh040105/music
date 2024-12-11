package zj.dzh.music_list.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import zj.dzh.music_list.R;
import zj.dzh.music_list.Bean.User;
import zj.dzh.music_list.DataBase.UserDao;

public class RegisterActivity extends AppCompatActivity {
    private EditText reg_name;
    private EditText reg_password;
    private Button btn_reg;
    private Button btn_quit;
    private UserDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    public void initView() {
        //去除标题栏
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!= null){
            actionBar.hide();
        }
        //绑定控件
        reg_name = findViewById(R.id.reg_name);
        reg_password = findViewById(R.id.reg_password);
        btn_reg = findViewById(R.id.btn_reg);
        btn_quit = findViewById(R.id.btn_quit);
        //点击事件
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String acc = reg_name.getText().toString().trim();
                String pass = reg_password.getText().toString().trim();
                User user = new User(acc, pass);
                dao = new UserDao(getApplicationContext());
                dao.open();
                if (dao.find(user) == true) {
                    Toast.makeText(RegisterActivity.this, "账号已存在", Toast.LENGTH_SHORT).show();
                } else {
                    dao.addUser(user);
                    Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    //将账号和密码传递过去
                    intent.putExtra("account", acc);
                    intent.putExtra("password", pass);
                    setResult(1, intent);
                    finish();
                }
                dao.close();
            }
        });
        btn_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
