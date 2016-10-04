package com.czy.tips.wxapi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.czy.tips.R;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;

import static com.czy.tips.wxapi.WXEntryActivity.SHARE_TYPE.Type_WXSceneSession;
import static com.czy.tips.wxapi.WXEntryActivity.SHARE_TYPE.Type_WXSceneTimeline;
import static com.tencent.mm.sdk.modelmsg.SendMessageToWX.Req.WXSceneSession;
import static com.tencent.mm.sdk.modelmsg.SendMessageToWX.Req.WXSceneTimeline;


public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {


    private String APP_ID = "wx6eec3491d158eba8";

    private IWXAPI iwxapi;

    enum SHARE_TYPE {Type_WXSceneSession, Type_WXSceneTimeline}

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);
        iwxapi = WXAPIFactory.createWXAPI(this, APP_ID, false);
        iwxapi.handleIntent(getIntent(), this);
        iwxapi.registerApp(APP_ID);
    }

    public void shareWXSceneSession(View view) {
        share(Type_WXSceneSession);
    }

    public void shareWXSceneTimeline(View view) {
        share(Type_WXSceneTimeline);
    }

    private void share(SHARE_TYPE type) {
        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = "http://www.initobject.com/";
        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        msg.title = "Hi,Tips";
        msg.description = "这是一个校园应用";
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.ninja);
        msg.thumbData = bmpToByteArray(thumb, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("Req");
        req.message = msg;
        switch (type) {
            case Type_WXSceneSession:
                req.scene = WXSceneSession;
                break;
            case Type_WXSceneTimeline:
                req.scene = WXSceneTimeline;
                break;
        }
        iwxapi.sendReq(req);
        finish();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        iwxapi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp resp) {
        String result;
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = "分享成功";
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "取消分享";
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "分享被拒绝";
                break;
            default:
                result = "发送返回";
                break;
        }
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        finish();
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
