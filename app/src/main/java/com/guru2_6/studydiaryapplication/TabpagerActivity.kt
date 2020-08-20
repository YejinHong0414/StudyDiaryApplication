package com.guru2_6.studydiaryapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_tabpager.*
import javax.crypto.spec.RC2ParameterSpec

class TabpagerActivity : AppCompatActivity() {
    val RC_SIGN_IN = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabpager)

        var auth: FirebaseAuth = Firebase.auth
        if(auth.currentUser == null)
        {
            login()
        }

        tab_layout.addTab(tab_layout.newTab().setText("메모장")) // 탭의 첫번째 화면 : 메모장 (달력)
        tab_layout.addTab(tab_layout.newTab().setText("할 일"))
        tab_layout.addTab(tab_layout.newTab().setText("통계"))
        tab_layout.addTab(tab_layout.newTab().setText("스탑워치"))
        tab_layout.addTab(tab_layout.newTab().setText("타이머"))

        val pagerAdapter = FragmentPagerAdapter(supportFragmentManager, 5)
        view_pager.adapter = pagerAdapter

        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                view_pager.currentItem = tab!!.position
                // null 을 할당할 수 없으므로 !!
            }
        })
        view_pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
        // -> 페이저가 이동했을때 탭을 이동시키는 코드
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                // ...
            } else {
                finish()
            }
        }
    }

    fun login()
    {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build())

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN)
    }

    fun logout()
    {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                login()
            }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.mainmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_log_out -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

class FragmentPagerAdapter(
    fragmentManager: FragmentManager,
    val tabCount: Int
): FragmentStatePagerAdapter(fragmentManager){
    override fun getItem(position: Int): Fragment {
        // 여기에 각자 기능 Fragment들을 넣으시면 됩니다!:)
        when(position){
            0 -> {
                return MemoFragment()
            }
            1 -> {
                return TodoListFragment()
            }
            2 -> {
                return GraphFragment()
            }
            3 -> {
                return Fragment3()
            }
            4 -> {
                return TimerFragment()
            }
            else -> return MemoFragment()
        }
    }

    override fun getCount(): Int {
        return tabCount
    }
}