package com.example.myapplication.notiy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.example.myapplication.R
import com.example.myapplication.notiy.adapter.ViewPagerAdapter
import com.example.myapplication.notiy.fragment.OneFragment
import com.example.myapplication.notiy.fragment.ProfileFragment
import kotlinx.android.synthetic.main.activity_main3.*

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        initEvent()
        initListener()
        initViewModel()
    }

    private fun initEvent() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.isVisible=true

        val adapter=ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(OneFragment(this@MainActivity3),getString(R.string.tab2))
        adapter.addFragment(ProfileFragment(),getString(R.string.tab3))
        viewpager.adapter=adapter
        tabs.setupWithViewPager(viewpager)
        viewpager.setCurrentItem(0,false)
    }

    private fun initListener() {



    }

    private fun initViewModel() {

    }
}