package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication.asynctaskscheduler.AsyncExecutor
import com.example.myapplication.asynctaskscheduler.AsyncTaskScheduler
import com.example.myapplication.asynctaskscheduler.SingleAsyncTask
import com.tencent.mmkv.MMKV
import kotlinx.android.synthetic.main.activity_main2.*
import java.lang.Exception
import java.lang.Thread.sleep
import java.util.concurrent.FutureTask

class MainActivity2 : AppCompatActivity() {
    private var mTag = MainActivity2::class.simpleName
    val stb: StringBuffer? = null

    var mAsyncTaskScheduler: AsyncTaskScheduler? = null
    private var mAsyncExecutor: AsyncExecutor? = null

    private var mSingleAsyncTask: SingleAsyncTask<String, String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val defaultMMKV = MMKV.defaultMMKV()
        mAsyncExecutor =
            AsyncExecutor()

        btn_show.setOnClickListener {
            startHandler()
        }
        btn_startOne.setOnClickListener {
            startAsy()
        }
        btn_endOne.setOnClickListener {
            mAsyncTaskScheduler?.cancelTask(mSingleAsyncTask, false)
        }


    }

    private fun startAsy() {
        mAsyncTaskScheduler = AsyncTaskScheduler()
        mSingleAsyncTask = object : SingleAsyncTask<String, String>() {
            override fun doInBackground(): String {
                return "测试返回"
            }

            override fun onProgressUpdate(values: String?) {
                Log.i(mTag, "onProgressUpdate$values")
                super.onProgressUpdate(values)
            }

            override fun onExecuteSucceed(result: String?) {
                Log.i(mTag, "onExecuteSucceed$result")
            }

            override fun onExecuteFailed(exception: Exception?) {
                Log.i(mTag, "onExecuteSucceed$exception")
            }

            override fun onExecuteCancelled(result: String?) {
                Log.i(mTag, "onExecuteCancelled$result")
            }

        }
        mAsyncTaskScheduler?.execute(mSingleAsyncTask!!)
    }

    private fun startHandler() {
        mAsyncExecutor?.execute(object : AsyncExecutor.Worker<String>() {
            override fun doInBackground(): String {
                Log.i(mTag, "doInBackground 22222222")
                return "2222222"
            }

            override fun onPostExecute(data: String?) {
                stb?.append(data)
                Log.i(mTag, "onPostExecute $data")
                tv_show.setText("${data}")
            }

            override fun onCanceled() {
                Log.i(mTag, "onCanceled")
                stb?.append("onCanceled")
                tv_show.setText("${stb.toString()}")
            }

            override fun abort() {
                stb?.append("abort")
                Log.i(mTag, "abort")
                tv_show.setText("${stb.toString()}")
            }
        })
    }
}