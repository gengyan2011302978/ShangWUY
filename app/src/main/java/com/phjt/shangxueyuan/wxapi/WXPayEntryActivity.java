package com.phjt.shangxueyuan.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.phjt.base.integration.EventBusManager;
import com.phjt.shangxueyuan.bean.event.EventBean;
import com.phjt.shangxueyuan.utils.Constant;
import com.phsxy.utils.LogUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * @author: Roy
 * date: 2019/11/11 10:08
 * company: 普华集团
 * description:
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constant.WECHAT_APPID, false);
        try {
            Intent intent = getIntent();
            api.handleIntent(intent, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.e(TAG, "onReq：onPayFinish, errCode = sssss");
    }

    @Override
    public void onResp(BaseResp baseResp) {
        //0 	成功 	展示成功页面
        //-1 	错误 	可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
        //-2 	用户取消 	无需处理。发生场景：用户不支付了，点击取消，返回APP。
        LogUtils.d("onResp: 微信回调onResp：到这里了 resp.errCode=" + baseResp.errCode + ",---baseResp.getType():" + baseResp.getType() + ",---baseResp.errStr:" + baseResp.errStr);
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (baseResp.errCode) {
                case 0:
                    EventBusManager.getInstance().post(new EventBean(EventBean.PAY_SUCCESS, null));
                    break;
                case -2:
                    EventBusManager.getInstance().post(new EventBean(EventBean.PAY_FAILED, null));
                    break;
                default:
                    break;
            }
            this.finish();
        } else {
            this.finish();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return true;
    }
}
