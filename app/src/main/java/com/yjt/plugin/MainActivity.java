package com.yjt.plugin;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.yjt.constant.Regex;
import com.yjt.dialog.ProgressDialog;
import com.yjt.dialog.constant.Constant;
import com.yjt.http.net.request.HttpRequest;
import com.yjt.http.net.response.DownloadResponse;
import com.yjt.plugin.base.BaseApplication;
import com.yjt.utils.LogUtil;
import com.yjt.utils.ToastUtil;
import com.yjt.utils.ViewUtil;

import java.io.File;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DialogFragment mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                String url = "http://m.800j.com/download/HyPhonePass.apk";
                String fileName = "plugin" + Regex.LEFT_SLASH.getRegext() + url.substring(url.lastIndexOf(Regex.LEFT_SLASH.getRegext()) + 1);
                HttpRequest.getInstance().doDownload(BaseApplication.getInstance(), url, new File(getFilesDir(), fileName), new DownloadResponse() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        mDialog = ProgressDialog.createBuilder(getSupportFragmentManager())
                                .setPrompt(getString(R.string.prompt1))
                                .setCancelableOnTouchOutside(false)
                                .setCancelable(true)
                                .setRequestCode(Constant.RequestCode.DIALOG_PROMPT_DOWNLOAD)
                                .show(BaseApplication.getInstance());
                    }

                    @Override
                    public void onProgress(int progress, long speed) {
                        super.onProgress(progress, speed);
                        LogUtil.getInstance().println("---->progress:" + progress + ",speed:" + speed);
                        ToastUtil.getInstance().showToast(BaseApplication.getInstance(), progress + "%", Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onEnd() {
                        super.onEnd();
                        ViewUtil.getInstance().hideDialog(mDialog);
                    }


                    @Override
                    public void onSuccess() {
                        super.onSuccess();
                        ToastUtil.getInstance().showToast(BaseApplication.getInstance(), "Success", Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onFailed(int code, String message) {
                        super.onFailed(code, message);
                        ToastUtil.getInstance().showToast(BaseApplication.getInstance(), "code：" + code + ",message:" + message, Toast.LENGTH_SHORT);
                    }
                });
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_aar1:
                break;
            case R.id.nav_aar2:
                break;
            case R.id.nav_aar3:
                break;
            case R.id.nav_aar4:
                break;
            case R.id.nav_aar5:
                break;
            case R.id.nav_aar6:
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
