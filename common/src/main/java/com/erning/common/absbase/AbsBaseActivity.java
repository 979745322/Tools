package com.erning.common.absbase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
/*import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;*/
import com.erning.common.R;
import com.erning.common.dialog.MyViewDialog;
import com.erning.common.dialog.MyprogressDialog;
import com.erning.common.sharedperference.AesUtils;
import com.erning.common.sharedperference.CPPUtils;
import com.erning.common.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 集成了 post get toast SharedPreferences
 * Created by 二宁 on 2017/11/23.
 */
public abstract class AbsBaseActivity extends AppCompatActivity {
    private String TAG = "AbsBaseActivity";
    public Context mContext;
    protected SharedPreferences sp;
    protected RequestQueue requestQueue;
    public MyprogressDialog progressDialog;
    protected abstract int getLayoutResId();
    protected abstract void initControls();
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
        setContentView(getLayoutResId());
        mContext = getApplicationContext();
        sp = PreferenceManager.getDefaultSharedPreferences(mContext);

//        SSLSocketFactory sslSocketFactory = null;
//        try {
//            sslSocketFactory = HTTPSUtils.getSSLSocketFactory_Certificate(mContext,"BKS", R.raw.coinbulb2);
//        } catch (CertificateException e) {
//            //证书验证失败
//            e.printStackTrace();
//            return;
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (KeyManagementException e) {
//            e.printStackTrace();
//        }
//        HurlStack stack = new HurlStack(null, sslSocketFactory);
//        requestQueue = Volley.newRequestQueue(mContext,stack);
        requestQueue = Volley.newRequestQueue(mContext);

        initControls();
    }

    protected void initWindow() {
    }

    public void startActivity(Class<?> cls) {
        startActivity(new Intent(getApplicationContext(), cls));
    }
    public void startActivity(Class<?> cls, String[] key, String[] values) {
        Intent intent = new Intent(getApplicationContext(), cls);
        if (key != null) {
            for (int i = 0; i < key.length; i++) {
                intent.putExtra(key[i], values[i]);
            }
        }
        startActivity(intent);
    }
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(getApplicationContext(), cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void post(String url, final String apiname,final HashMap<String, String> params) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("")) {
                    Log.w(TAG,"没有收到任何数据");
                    return;
                }
//                LogUtils.d(TAG,response);
                JSONObject res;
                try {
                    if (response.charAt(0) == '[') {
                        res = new JSONObject();
                        res.put("data", JSONObject.parseArray(response));
                    } else {
                        res = JSONObject.parseObject(response);
                    }
                    res.put("api", apiname);
                    onPostResult(res);
                } catch (Exception e) {
                    LogUtils.e(TAG,e.toString() + ":" + response);
                    onRequestError(apiname,e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
                onPostFail(apiname,error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // 在这里设置需要post的参数
                Map<String, String> map = new HashMap<>(params.size());
                for (String key : params.keySet()) {
                    map.put(key, params.get(key));
                }
                return map;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                Map<String, String> headers = response.headers;//获取所有头字段
                String cookies = headers.get("Set-Cookie");//获取Cookie头字段
                if(cookies != null){
                    cookies = parseVolleyCookie(cookies);
                    putSpString("cookie",cookies);
                }
                return super.parseNetworkResponse(response);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // 给服务器上传cookie值
                Map<String, String> map = new HashMap<>();
                String cookie = getSpString("cookie");
                if(cookie != null){
                    map.put("cookie", cookie);
                }
                return map;
            }
        };
        stringRequest.setRetryPolicy( new DefaultRetryPolicy(5000,3,1));
        stringRequest.setTag(this.getClass().getSimpleName());
        requestQueue.add(stringRequest);
    }

    /*
     * 方法的作用: 解析volley返回cookie
     */
    public String parseVolleyCookie(String cookie) {
        String[] cookieValues = cookie.split(";");

        return cookieValues[0];
    }
    protected void logout(){}
    protected void onPostResult(JSONObject result){}
    protected void onPostFail(String api,VolleyError error){}
    protected void get(String url,final String apiname, Map<String,Object> map){
        if (map != null){
            url += "?";
            for (String key : map.keySet()) {
                map.put(key, map.get(key).toString());
                url = url + key + "=" + map.get(key).toString() + "&";
            }
            url = url.substring(0,url.length()-1);
        }
        StringRequest sr = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("")) {
                    Log.w(TAG,"没有收到任何数据");
                    return;
                }
                JSONObject res;
                try {
                    if (response.charAt(0) == '[') {
                        res = new JSONObject();
                        res.put("data", JSONObject.parseArray(response));
                    } else {
                        res = JSONObject.parseObject(response);
                    }
                    res.put("api", apiname);
                    onGetResult(res);

                } catch (Exception e) {
                    Log.e(TAG,e.toString() + ":" + response);
                    onRequestError(apiname,e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,"有参get失败"+error.toString());
            }
        });
        sr.setRetryPolicy( new DefaultRetryPolicy(5000,3,1));
        sr.setTag(this.getClass().getSimpleName());
        requestQueue.add(sr);
    }
    protected void onGetResult(JSONObject result){}
    protected void onRequestError(String apiname,Exception e){}
    protected void cancelAll(Object obj){
        requestQueue.cancelAll(obj);
    }
    /*protected void setImage(String url, final ImageView view,int width,int height){
        DrawableRequestBuilder<String> glide = Glide.with(this)
                .load(url)//地址
                .dontAnimate()//不使用默认动画
                .placeholder(R.drawable.loading)//加载中的图片
                .error(R.drawable.loaddie);//加载失败的图片
                //.centerCrop()//缩放方式
                //.thumbnail(0.3f)//先加载缩略图

        if (width!=0 && height!=0)
            glide.override(width, height);//加载尺寸

        glide.into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                view.setImageDrawable(resource);
            }
        });//控件
    }*/

    public void toast(int res) {
        if(toast != null)
            toast.cancel();

        toast = Toast.makeText(mContext, getResources().getString(res), Toast.LENGTH_SHORT);

        if(toast != null)
            toast.show();
    }
    public void toast(CharSequence content) {
        if(toast != null)
            toast.cancel();

        toast = Toast.makeText(mContext, content, Toast.LENGTH_SHORT);

        if(toast != null)
            toast.show();
    }

    public String getSpString(String key) {
        String rr = null;
        try {
            String k = AesUtils.encrypt(CPPUtils.getSPKey(this),key);
            String r = sp.getString(k, "");
            rr = r.isEmpty() ? "" : AesUtils.decrypt(CPPUtils.getSPKey(this),r);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rr;
    }
    public void putSpString(String key, String value) {
        try {
            String k = AesUtils.encrypt(CPPUtils.getSPKey(this),key);
            String v= AesUtils.encrypt(CPPUtils.getSPKey(this),value);
            sp.edit().putString(k, v).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public long getSpLong(String key) {
        return sp.getLong(key, 0);
    }
    protected void putlong(String key, long value) {
        sp.edit().putLong(key, value).commit();
    }

    public void showprogressDialog() {
        if (progressDialog == null || !progressDialog.isShowing()) {
            progressDialog = new MyprogressDialog(this);
            progressDialog.show();
        }
    }
    public void hideprogressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void showNotisDialog(String content, @Nullable View.OnClickListener OKClickListener, @Nullable View.OnClickListener cancelClickListener){
        View view = getLayoutInflater().inflate(R.layout.notisdialog,null,false);
        ((TextView)view.findViewById(R.id.text_notis)).setText(content);
        view.findViewById(R.id.text_ds_ok).setOnClickListener(OKClickListener);
        view.findViewById(R.id.text_notis).setOnClickListener(cancelClickListener);
        new MyViewDialog(mContext,view).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelAll(this.getClass().getSimpleName());
    }
}
