package com.erning.common.absbase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
public abstract class AbsBaseFragment extends Fragment {
    private static final String TAG = "AbsBaseFragment";

    public View mRoot;
    public Context mContext;
    protected SharedPreferences sp;
    protected RequestQueue requestQueue;
    public MyprogressDialog progressDialog;
    private Toast toast;

    private boolean mIsFirstInitData = true;
    protected boolean haveView = true;

    protected abstract int getLayoutResId();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRoot == null) {
            mContext = this.getActivity().getApplicationContext();
            sp = ((AbsBaseActivity) getActivity()).sp;
            requestQueue = Volley.newRequestQueue(mContext);

            // 初始化当前的跟布局，但是不在创建时就添加到container里边
            View root = inflater.inflate(getLayoutResId(), container, false);
            initWidget(root);
            mRoot = root;
        } else {
            if (mRoot.getParent() != null) {
                // 把当前Root从其父控件中移除
                ((ViewGroup) mRoot.getParent()).removeView(mRoot);
            }
        }
        haveView = true;
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mIsFirstInitData) {
            // 触发一次以后就不会触发
            mIsFirstInitData = false;
            // 触发
            onFirstInit();
        }
        // 当View创建完成后初始化数据
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        haveView = false;
    }

    /**
     * 初始化控件
     */
    protected void initWidget(View root) {
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 当首次初始化数据的时候会调用的方法
     */
    protected void onFirstInit() {

    }

    public void startActivity(Class<?> cls) {
        startActivity(new Intent(mContext, cls));
    }
    public void startActivity(Class<?> cls, String[] key, String[] values) {
        Intent intent = new Intent(mContext, cls);
        if (key != null) {
            for (int i = 0; i < key.length; i++) {
                intent.putExtra(key[i], values[i]);
            }
        }
        startActivity(intent);
    }
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(mContext, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void post(String url, final String apiname,final HashMap<String, Object> params) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("")) {
                    LogUtils.d(TAG,"没有收到任何数据");
                    return;
                }
                LogUtils.d(TAG,response);
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
                Map<String, String> map = new HashMap<>();
                if (params != null){
                    for (String key : params.keySet()) {
                        map.put(key, params.get(key).toString());
                    }
                }
                return map;
            }
        };
        stringRequest.setRetryPolicy( new DefaultRetryPolicy(5000,1,1));
        stringRequest.setTag(this.getClass().getSimpleName());
        requestQueue.add(stringRequest);
    }
    protected void onPostFail(String api,VolleyError error){}
    protected void onPostResult(JSONObject result){}
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

    /*protected void setImage(String url, final ImageView view, int width, int height){
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
            String k = AesUtils.encrypt(CPPUtils.getSPKey(getContext()),key);
            String r = sp.getString(k, "");
            rr = r.isEmpty() ? "" : AesUtils.decrypt(CPPUtils.getSPKey(getContext()),r);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rr;
    }
    public void putSpString(String key, String value) {
        try {
            String k = AesUtils.encrypt(CPPUtils.getSPKey(getContext()),key);
            String v= AesUtils.encrypt(CPPUtils.getSPKey(getContext()),value);
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
            progressDialog = new MyprogressDialog(mContext);
            progressDialog.show();
        }
    }
    public void hideprogressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
    public void showNotisDialog(String content, View.OnClickListener OKClickListener, View.OnClickListener cancelClickListener){
        View view = getLayoutInflater().inflate(R.layout.notisdialog,null,false);
        ((TextView)view.findViewById(R.id.text_notis)).setText(content);
        view.findViewById(R.id.text_ds_ok).setOnClickListener(OKClickListener);
        view.findViewById(R.id.text_notis).setOnClickListener(cancelClickListener);
        new MyViewDialog(mContext,view).show();
    }

    protected void cancelAll(Object obj){
        requestQueue.cancelAll(obj);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelAll(this.getClass().getSimpleName());
    }
}