package edu.ewubd.eventmanagementapp_206;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText userIdEditText, userPwdEditText, userConfirmPwdEditText;
    private CheckBox rememberUserId, rememberLogin;

    boolean isCreating = false;

    SharedPreferences shp;
    SharedPreferences.Editor myEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        shp = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        myEdit = shp.edit();

        findViewById(R.id.exitBtn).setOnClickListener(view -> finish());

        userIdEditText = findViewById(R.id.idIn);
        userPwdEditText = findViewById(R.id.passIn);
        userConfirmPwdEditText = findViewById(R.id.pass2In);
        rememberUserId = findViewById(R.id.checkID);
        rememberLogin = findViewById(R.id.checkPass);

        String userIdCheck = shp.getString("userId", "abc");
        if (userIdCheck.equals("abc")) {
            isCreating = true;
            userConfirmPwdEditText.setVisibility(View.VISIBLE);
        } else {
            if (shp.getBoolean("rememberLogin", false)) {
                isCreating = false;
                userConfirmPwdEditText.setVisibility(View.GONE);
                Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }

        findViewById(R.id.goBtn).setOnClickListener(view -> userLogin());

        findViewById(R.id.loginTextId).setOnClickListener(view -> {
            if (isCreating) {
                isCreating = false;
                userConfirmPwdEditText.setVisibility(View.GONE);
            } else {
                isCreating = true;
                userConfirmPwdEditText.setVisibility(View.VISIBLE);
            }
        });
    }

    private void userLogin() {
        String userId = userIdEditText.getText().toString().trim();
        String userPwd = userPwdEditText.getText().toString().trim();
        String userRePwd = userConfirmPwdEditText.getText().toString().trim();
        boolean loginRemember = rememberLogin.isChecked();
        System.out.println("user Id: " + userId);
        System.out.println("user pwd: " + userPwd);
        System.out.println("user confirm Pwd: " + userRePwd);
        if (isCreating) {
            if (userId != "" && userPwd != "" && userRePwd != "") {
                if (userPwd.equals(userRePwd)) {
                    myEdit.putString("userId", userId);
                    myEdit.putString("userPwd", userPwd);
                    myEdit.putBoolean("rememberLogin", loginRemember);
                    myEdit.commit();

                    Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    Toast.makeText(this, "Confirm Password doesn't match.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "All fields required.", Toast.LENGTH_LONG).show();
            }
        } else {
            String id = shp.getString("userId", "");
            String pwd = shp.getString("userPwd", "");

            if (id == userId && pwd == userPwd) {
                Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            } else {
                Toast.makeText(this, "user id & password invalid.", Toast.LENGTH_LONG).show();
            }
        }

    }
}
