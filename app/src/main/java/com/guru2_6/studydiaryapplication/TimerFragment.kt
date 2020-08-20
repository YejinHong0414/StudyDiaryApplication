package com.guru2_6.studydiaryapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_study_timer.*
import kotlin.concurrent.timer
open class Handler

class TimerFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("life_cycle","F onCreateView")
        // fragment가 인터페이스를 처음 그릴때 호출된다
        // inflater -> 뷰를 그려주는 역할
        // container -> 부모 뷰
        super.onCreate(savedInstanceState)

        return inflater.inflate(R.layout.activity_study_timer, container, false)


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("life_cycle","F onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        var timeTick = 0
        var minute = 0
        var second = 0
        val initialTextViewTranslationY = time.translationY

        // Activity의 Oncreate에서 했던 작업을 여기에서 한다
//        pass.setOnClickListener {
//            dataPassListener.onDataPass("Good Bye")
//        }
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                timeTick = progress
                time.text = String.format("%02d : %02d", timeTick/60, timeTick%60)

                val translationDistance = (initialTextViewTranslationY +
                        progress * resources.getDimension(R.dimen.text_anim_step) * -1)

                //time.animate().translationY(translationDistance)

                if(!fromUser){
                    time.animate().setDuration(500).rotationBy(360f)
                        .translationY(initialTextViewTranslationY)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                timeTick = seekBar!!.progress
                time.text = String.format("%02d : %02d", timeTick/60, timeTick%60)
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                timeTick = seekBar!!.progress
                time.text = String.format("%02d : %02d", timeTick/60, timeTick%60)
            }
        })
        start.setOnClickListener {
            minute = timeTick / 60
            second = timeTick % 60
            timer(period = 1000, initialDelay = 1000){
                requireActivity().runOnUiThread {
                    time.text = String.format("%02d : %02d", minute, second)
                }
                if(second == 0 && minute == 0){
                    println("타이머 종료")
                    cancel()
                }
                if(second == 0){
                    minute--
                    second = 60
                }
                second--
                stop.setOnClickListener { cancel() }
            }
        }
        reset.setOnClickListener { view ->
            seekBar.progress = 0

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d("life_cycle","F onActivityCreated")

        val data = arguments?.getString("hello")
        //Log.d("data", data)

        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        Log.d("life_cycle","F onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d("life_cycle","F onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d("life_cycle","F onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d("life_cycle","F onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d("life_cycle","F onDestroyView")
        super.onDestroyView()
    }

    override fun onDetach() {
        Log.d("life_cycle","F onDetach")
        super.onDetach()
    }



}