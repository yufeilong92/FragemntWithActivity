package com.example.myapplication

import android.app.Application
import androidx.multidex.MultiDex

/**
 * @Author : YFL  is Creating a porject in My Application
 * @Package com.example.myapplication
 * @Email : yufeilong92@163.com
 * @Time :2020/11/10 10:01
 * @Purpose :
 */
class BaseAppliction :Application(){
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this);
    }
}