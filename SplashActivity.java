package com.bestdb;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bestdb.permissionManagerViews.Permission;
import com.bestdb.permissionManagerViews.PermissionManagerInstance;
import com.bestdb.permissionManagerViews.PermissionManagerListener;

public class SplashActivity extends AppCompatActivity {

    private PermissionManagerInstance mPermissionManagerInstance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mPermissionManagerInstance = new PermissionManagerInstance(this);
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        mPermissionManagerInstance.requestForPermissions(
                permissions,
                new PermissionManagerListener() {
                    @Override
                    public void permissionCallback(String[] permissions, Permission[] grantResults, boolean allGranted) {
                        startApp();
                    }
                });
    }

    private void startApp() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}