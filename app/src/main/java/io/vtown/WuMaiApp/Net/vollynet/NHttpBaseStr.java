/**
 *
 */
package io.vtown.WuMaiApp.Net.vollynet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import io.vtown.WuMaiApp.R;
import io.vtown.WuMaiApp.Utilss.StrUtils;
import io.vtown.WuMaiApp.interf.IHttpResult;


/**
 * @author 王永奎 E-mail:wangyk@nsecurities.cn
 * @version 创建时间：2015-11-6 下午2:03:07
 * @department 互联网金融部
 */

public class NHttpBaseStr extends NHttpBase {

    public NHttpBaseStr(Context context) {
        super(context);
    }

    private IHttpResult<String> postResult;

    public void setPostResult(IHttpResult<String> postresult) {
        this.postResult = postresult;
    }

    @Override
    public void myOnResponse(String str) {
        if (StrUtils.isEmpty(str)) {
            if (postResult != null)
                postResult.onError(context.getResources().getString(R.string.error_fuwuqi), 0);
            return;
        } else {
            int Status = 0;
            int Code = 0;
            String Msg = null;
            String Data = "";
            try {
                JSONObject obj = new JSONObject(str);
                try {
                    Code = obj.getInt("code");
                    // 判断如果是900就直接被踢下来
//                    if (Code == Constants.NetCodeExit) {
//                        ShowPromptCustomDialog(context, "账号已在其他设备登录请重新登录");
//
//                    }

                } catch (Exception e) {
                    if (postResult != null)
                        postResult.onError(context.getResources().getString(R.string.error_fuwuqi), 0);
                    return;
                }
                try {
                    Msg = obj.getString("msg");
                } catch (Exception e) {
                    if (postResult != null)
                        postResult.onError(context.getResources().getString(R.string.error_fuwuqi), 0);
                    return;
                }

                try {
                    Data = obj.getString("data");
                } catch (Exception e) {
                    if (postResult != null)
                        postResult.onError(context.getResources().getString(R.string.error_fuwuqi), 0);
                    return;
                }

            } catch (Exception e) {
                if (postResult != null)
                    postResult.onError(context.getResources().getString(R.string.error_fuwuqi), 0);
                return;
            }

            if (postResult != null)
                postResult.getResult(Code, Msg, Data);
        }
    }

    @Override
    public void myonErrorResponse(VolleyError arg0) {
        if (postResult != null)
            postResult.onError(context.getResources().getString(R.string.error_fuwuqi), 0);
    }

}
