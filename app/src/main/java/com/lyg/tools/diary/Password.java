package com.lyg.tools.diary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lyg.tools.R;

public class Password extends AppCompatActivity {

    private SharedPreferences sp;
    private String pwd;
    private EditText editText;
    FingerprintManager manager;
    KeyguardManager mKeyManager;
    private final static int REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS = 0;
    CancellationSignal mCancellationSignal;
    private final static String TAG = "finger_log";


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        editText = findViewById(R.id.editText);
        setTitle("日记");
        // 检查是否有密码文件，没有则创建
        sp = getSharedPreferences("diary", Context.MODE_APPEND);
        pwd = sp.getString("password","");
        mCancellationSignal = new CancellationSignal();
        manager = (FingerprintManager) this.getSystemService(Context.FINGERPRINT_SERVICE);
        mKeyManager = (KeyguardManager) this.getSystemService(Context.KEYGUARD_SERVICE);
        if(pwd.equals("")&&getIntent().getStringExtra("struts")==null){ // 如果没有设置密码，直接进入日记列表页面
            // 进日记列表页面
            startActivity(new Intent(this,DiaryList.class));
            finish();
        }else if (isFinger()) {
//            Toast.makeText(Password.this, "请进行指纹识别", Toast.LENGTH_LONG).show();
            Log(TAG, "keyi");
            startListening(null);
        }
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                // 输入密码框焦点 关闭指纹识别
                mCancellationSignal.cancel();
            }
        });

    }

    public void login(View view) {
        if(getIntent().getStringExtra("struts")==null) {
            if (pwd.equals(editText.getText().toString())) { // 密码正确
                // 进日记列表页面
                startActivity(new Intent(this, DiaryList.class));
                finish();
            } else { // 密码错误
                editText.setText("");
                Toast.makeText(this, "密码输入错误，请重新输入！！！", Toast.LENGTH_SHORT).show();
            }
        }else{
            sp.edit().putString("password",editText.getText().toString()).apply();
            finish();
        }
    }

    /**
     * 关闭指纹识别
     * @param view
     */
    public void cancel(View view) {
        mCancellationSignal.cancel();
    }

    public boolean isFinger() {
        if (!manager.isHardwareDetected()) {

            Toast.makeText(this, "没有指纹识别模块", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!mKeyManager.isKeyguardSecure()) {

            Toast.makeText(this, "没有开启锁屏密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!manager.hasEnrolledFingerprints()) {

            Toast.makeText(this, "没有录入指纹", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    //回调方法
    FingerprintManager.AuthenticationCallback mSelfCancelled = new FingerprintManager.AuthenticationCallback() {
        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {

            //但多次指纹密码验证错误后，进入此方法；并且，不能短时间内调用指纹验证
//            Toast.makeText(Password.this, errString, Toast.LENGTH_SHORT).show();
//            showAuthenticationScreen();
        }

        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

//            Toast.makeText(Password.this, helpString, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {

//            Toast.makeText(Password.this, "指纹识别成功", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Password.this, DiaryList.class));
            finish();
        }

        @Override
        public void onAuthenticationFailed() {

//            Toast.makeText(Password.this, "指纹识别失败", Toast.LENGTH_SHORT).show();
        }
    };

    public void startListening(FingerprintManager.CryptoObject cryptoObject) {
        //android studio 上，没有这个会报错
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "没有指纹识别权限", Toast.LENGTH_SHORT).show();
            return;
        }
        manager.authenticate(cryptoObject, mCancellationSignal, 0, mSelfCancelled, null);
    }

    private void showAuthenticationScreen() {
        Intent intent = mKeyManager.createConfirmDeviceCredentialIntent("finger", "测试指纹识别");
        if (intent != null) {

            startActivityForResult(intent, REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS) {

            // Challenge completed, proceed with using cipher
            if (resultCode == RESULT_OK) {
//                Toast.makeText(this, "识别成功", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(this, "识别失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void Log(String tag, String msg) {
        Log.d(tag, msg);
    }

    @Override
    protected void onDestroy() {
        mCancellationSignal.cancel();
        super.onDestroy();
    }
}
