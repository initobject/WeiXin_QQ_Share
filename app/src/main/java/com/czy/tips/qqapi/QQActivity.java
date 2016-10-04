package com.czy.tips.qqapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.czy.tips.R;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class QQActivity extends AppCompatActivity implements IUiListener {

    private Tencent mTencent;

    private String APP_ID = "101351691";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq);
        mTencent = Tencent.createInstance(APP_ID, getApplicationContext());
    }

    public void shareToQQ(View view) {

    }

    public void shareToQZone(View view) {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "Hi,叶应是叶");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "欢迎访问我的博客");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://blog.csdn.net/new_one_object");
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://avatar.csdn.net/B/0/1/1_new_one_object.jpg");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "HiTips");
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        mTencent.shareToQQ(this, params, this);
    }

    @Override
    public void onComplete(Object o) {
        Toast.makeText(this, o.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(UiError uiError) {
        Toast.makeText(this, uiError.errorMessage + "--" + uiError.errorCode + "---" + uiError.errorDetail, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCancel() {
        Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mTencent != null) {
            Tencent.onActivityResultData(requestCode, resultCode, data, this);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
