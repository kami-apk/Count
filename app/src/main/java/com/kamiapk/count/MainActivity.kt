package com.kamiapk.count

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {


    //時間を計測しているかのフラグ
    //trueなら計測していることにする
    private var startflag = false

    //カレンダーで取得できる時間がLongなので全部Longに合わせます。
    private var startTime = 0L
    private var endTime = 0L

    //ミリ秒表示します。ラジオボタンを押すと切り替わるようにします。
    private var Time = 3000L



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //カレンダークラスのインスタンスを先に取得しておく。
        val calendar = Calendar.getInstance()


        startstop.setOnClickListener {
            //フラグによって条件分岐
            when(startflag){
                true -> {
                    //時刻取得
                    calendar.timeInMillis = System.currentTimeMillis() //Long
                    endTime = calendar.timeInMillis

                    //ボタンのTextを変更
                    startstop.text = "Start!"
                    //関数で結果を表示する
                    Result(startTime,endTime,Time)

                    startflag = false
                }
                false ->{
                    //時刻取得
                    calendar.timeInMillis = System.currentTimeMillis() //Long
                    startTime = calendar.timeInMillis

                    //Text更新
                    timer_count.setText("計測中")

                    //ボタンのTextを変更
                    startstop.text = "Stop!"
                    //フラグの真偽を入れ替えます。
                    startflag = true
                }
            }

        }

        //3秒
        s.setOnClickListener{
            Time = 3000L
            textView.setText("3秒で止めろ！")
        }

        //5秒
        ss.setOnClickListener{
            Time = 5000L
            textView.setText("5秒で止めろ！")
        }

        //10秒
        sss.setOnClickListener{
            Time = 10000L
            textView.setText("10秒で止めろ！")
        }
    }


    //3つの時間を受け取り結果を表示する
    fun Result(start :Long,end :Long, time :Long){
        //計測時間を取得
        val S :Long = end -start
        //秒
        val Second :String  = ( S / 1000L).toString()
        //小数点を取得
        val Decimal :String = (S - S /1000L).toString()

        timer_count.setText(Second + "." + Decimal + "秒")

        //不格好になってますが相対誤差を求めます
        val D : Double = (time - S).toDouble() / time.toDouble()

        //Dの値によって表示する文字を変更する
        val evaluation : String
        if(D < 0){
            evaluation = "長すぎ！"
        }else if(D < 0.02){
            evaluation = "正確！"
        }else if(D < 0.5){
            evaluation = "惜しい"
        }else{
            evaluation = "早いかも"
        }

        //スナックバー表示する。
        Snackbar.make(startstop,evaluation,Snackbar.LENGTH_SHORT).show()
    }

}
