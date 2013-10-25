package com.suicune.pokeutils;

import android.app.Application;
import android.content.Context;

/**
 * Created by lapuente on 25.10.13.
 */
public class App extends Application {
    public static Context mContext;

    @Override
    public void onCreate(){
        super.onCreate();
        mContext = this;
    }

    public static String getResourceString(int resId){
        if(mContext != null){
            return mContext.getString(resId);
        } else {
            return "";
        }
    }
}
