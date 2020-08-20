package com.guru2_6.studydiaryapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_study__stop_watch_.*
import java.util.*
import kotlin.concurrent.timer

class Fragment3 : Fragment() {

    private var time = 0
    private var TimerTask: Timer? = null // null을 허용
    private var isRunning = false
    private var index :Int = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("life_cycle", "F onCreateView")
// fragment가 인터페이스를 처음 그릴때 호출된다
// inflater -> 뷰를 그려주는 역할
// container -> 부모 뷰
        super.onCreate(savedInstanceState)

        return inflater.inflate(R.layout.activity_study__stop_watch_, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("life_cycle", "F onViewCreated")
        super.onViewCreated(view, savedInstanceState)

        btn_start.setOnClickListener {
            isRunning = !isRunning

            start()
        }

        btn_stop.setOnClickListener {
            stop()
        }

        btn_reset.setOnClickListener {
            reset()
        }

    }

    private fun start() { //시작

        TimerTask = timer(period = 1000) {
            time++

            val hou = time / 3600
            val min = time / 60
            val sec = time % 60

            activity!!.runOnUiThread { //UI 조작
                hourView.text = "$hou"
                minuteView.text = "$min"
                secondView.text = "$sec"
            }
        }
    }

    private fun stop() {
        TimerTask?.cancel() // 실행중인 타이머 일시정지
    }

    private fun reset() {
        TimerTask?.cancel() // 실행중인 타이머 리셋

// 모든 변수 초기화
        time = 0
        isRunning = false
        hourView.text = "00"
        minuteView.text = "00"
        secondView.text = "00"

        index = 1
    }
    private fun TextView(fragment3: Fragment3) {

    }


    private fun TextView() {

    }


}