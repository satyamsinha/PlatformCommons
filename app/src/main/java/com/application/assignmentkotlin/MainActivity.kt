package com.application.assignmentkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.room.Room
import com.application.assignmentkotlin.db.DBHelper
import com.application.assignmentkotlin.ui.main.MainFragment

class MainActivity : AppCompatActivity() {
    val mFragmentManagr:FragmentManager=supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val db = Room.databaseBuilder(
            applicationContext,
            DBHelper::class.java, "TaskAssignment.db"
        ).build()

        if (savedInstanceState == null) {
            addFragment(MainFragment.newInstance())

        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
         super.onKeyDown(keyCode, event)
        if(supportFragmentManager.backStackEntryCount>1) {
            supportFragmentManager.popBackStack()
            return false
        }
        else{
            finish()
            return true
        }

    }
}
fun AppCompatActivity.addFragment(fragment: Fragment){
    val fragmentManager = supportFragmentManager
    val transaction = fragmentManager.beginTransaction()
    transaction.add(R.id.container,fragment)
    transaction.addToBackStack(fragment::class.toString())
    transaction.commit()
}

