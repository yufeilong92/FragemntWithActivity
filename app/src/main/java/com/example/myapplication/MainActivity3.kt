package com.example.myapplication

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.StepView.StepBean
import com.example.myapplication.timerappwidget.NotificationReceiver
import com.example.myapplication.timerappwidget.NotificationReceiver.sId
import kotlinx.android.synthetic.main.activity_main7.*


class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main7)
        initEvent()
        initEvent1()
        initListener()
        initViewModel()
        initNotification(this) // 初始化通知栏

    }

    /**
     * 初始化通知栏
     *
     * @param context 上下文
     */
    private fun initNotification(context: Context) {
        val builder: NotificationCompat.Builder = NotificationReceiver.getNotification(context)
        val notification: Notification = builder.build()
        val manager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(sId, notification)
    }
    private fun initEvent() {
        val stepsBeanList: MutableList<StepBean> = ArrayList()
        val stepBean0 = StepBean("接单", 1)
        val stepBean1 = StepBean("打包", 1)
        val stepBean2 = StepBean("出发", 0)
        val stepBean3 = StepBean("送单", -1)
        val stepBean4 = StepBean("完成", -1)
        val stepBean5 = StepBean("支付", -1)
        stepsBeanList.add(stepBean0)
        stepsBeanList.add(stepBean1)
        stepsBeanList.add(stepBean2)
        stepsBeanList.add(stepBean3)
        stepsBeanList.add(stepBean4)
        stepsBeanList.add(stepBean5)
        step_view.setStepViewTexts(stepsBeanList)
            .setTextSize(16)
            .setStepsViewIndicatorCompletedLineColor(
                ContextCompat.getColor(
                    getBaseContext(),
                    android.R.color.white
                )
            )//设置StepsViewIndicator完成线的颜色
            .setStepsViewIndicatorUnCompletedLineColor(
                ContextCompat.getColor(
                    getBaseContext(),
                    R.color.uncompleted_text_color
                )
            )//设置StepsViewIndicator未完成线的颜色
            .setStepViewComplectedTextColor(
                ContextCompat.getColor(
                    getBaseContext(),
                    android.R.color.white
                )
            )//设置StepsView text完成线的颜色
            .setStepViewUnComplectedTextColor(
                ContextCompat.getColor(
                    getBaseContext(),
                    R.color.uncompleted_text_color
                )
            )//设置StepsView text未完成线的颜色
            .setStepsViewIndicatorCompleteIcon(
                ContextCompat.getDrawable(
                    getBaseContext(),
                    R.drawable.complted
                )
            )//设置StepsViewIndicator CompleteIcon
            .setStepsViewIndicatorDefaultIcon(
                ContextCompat.getDrawable(
                    getBaseContext(),
                    R.drawable.default_icon
                )
            )//设置StepsViewIndicator DefaultIcon
            .setStepsViewIndicatorAttentionIcon(
                ContextCompat.getDrawable(
                    getBaseContext(),
                    R.drawable.attention
                )
            );//设置StepsViewIndicator AttentionIcon

    }
    private fun initEvent1(){
        val list0: MutableList<String> = ArrayList()
        list0.add("您已提交定单，等待系统确认")
        list0.add("您的商品需要从外地调拨，我们会尽快处理，请耐心等待")
        list0.add("您的订单已经进入亚洲第一仓储中心1号库准备出库")
        list0.add("您的订单预计6月23日送达您的手中，618期间促销火爆，可能影响送货时间，请您谅解，我们会第一时间送到您的手中")
        list0.add("您的订单已打印完毕")
        list0.add("您的订单已拣货完成")
        list0.add("扫描员已经扫描")
        list0.add("打包成功")
        list0.add("您的订单在京东【华东外单分拣中心】发货完成，准备送往京东【北京通州分拣中心】")
        list0.add("您的订单在京东【北京通州分拣中心】分拣完成")
        list0.add("您的订单在京东【北京通州分拣中心】发货完成，准备送往京东【北京中关村大厦站】")
        list0.add("您的订单在京东【北京中关村大厦站】验货完成，正在分配配送员")
        list0.add("配送员【包牙齿】已出发，联系电话【130-0000-0000】，感谢您的耐心等待，参加评价还能赢取好多礼物哦")
        list0.add("感谢你在京东购物，欢迎你下次光临！")
        step_view1.setStepsViewIndicatorComplectingPosition(list0.size - 2) //设置完成的步数
            .reverseDraw(false) //default is true
            .setTextSize(14)
            .setStepViewTexts(list0) //总步骤
            .setStepsViewIndicatorCompletedLineColor(
                ContextCompat.getColor(
                    getBaseContext(),
                    R.color.white
                )
            ) //设置StepsViewIndicator完成线的颜色
            .setStepsViewIndicatorUnCompletedLineColor(
                ContextCompat.getColor(
                    getBaseContext(),
                    R.color.uncompleted_text_color
                )
            ) //设置StepsViewIndicator未完成线的颜色
            .setStepViewComplectedTextColor(
                ContextCompat.getColor(
                    getBaseContext(),
                    R.color.white
                )
            ) //设置StepsView text完成线的颜色
            .setStepViewUnComplectedTextColor(
                ContextCompat.getColor(
                    getBaseContext(),
                    R.color.uncompleted_text_color
                )
            ) //设置StepsView text未完成线的颜色
            .setStepsViewIndicatorCompleteIcon(
                ContextCompat.getDrawable(
                    getBaseContext(),
                    R.drawable.complted
                )
            ) //设置StepsViewIndicator CompleteIcon
            .setStepsViewIndicatorDefaultIcon(
                ContextCompat.getDrawable(
                    getBaseContext(),
                    R.drawable.default_icon
                )
            ) //设置StepsViewIndicator DefaultIcon
            .setStepsViewIndicatorAttentionIcon(
                ContextCompat.getDrawable(
                    getBaseContext(),
                    R.drawable.attention
                )
            ) //设置StepsViewIndicator AttentionIcon

    }

    private fun initListener() {

    }

    private fun initViewModel() {

    }
}