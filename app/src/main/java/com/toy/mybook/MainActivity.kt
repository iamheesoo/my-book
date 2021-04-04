package com.toy.mybook

import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.toy.mybook.databinding.ActivityMainBinding
import com.toy.mybook.view.AccountFragment
import com.toy.mybook.view.RecordFragment
import com.toy.mybook.view.HomeFragment
import com.toy.mybook.view.StarFragment


class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    private var TAG: String ="MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navBottom.setOnNavigationItemSelectedListener{
            navigation(it)
        }

        binding.navBottom.selectedItemId=R.id.action_home

        val SDK_INT = Build.VERSION.SDK_INT
        if (SDK_INT > 8) {
            val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

    }

    fun navigation(item: MenuItem): Boolean{
        when(item.itemId){
            R.id.action_home ->{
                supportFragmentManager.beginTransaction().replace(R.id.main_content,
                    HomeFragment()
                ).commit()
                return true
            }
            R.id.action_favorite->{
                supportFragmentManager.beginTransaction().replace(R.id.main_content,
                    RecordFragment()
                ).commit()
                return true
            }
            R.id.action_star->{
                supportFragmentManager.beginTransaction().replace(R.id.main_content,
                    StarFragment()
                ).commit()
                return true
            }
            R.id.action_account->{
                supportFragmentManager.beginTransaction().replace(R.id.main_content,
                    AccountFragment()
                ).commit()
                return true
            }
            else -> return false
        }
    }
}