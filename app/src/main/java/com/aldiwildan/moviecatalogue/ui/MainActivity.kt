package com.aldiwildan.moviecatalogue.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aldiwildan.moviecatalogue.R
import com.aldiwildan.moviecatalogue.utils.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        vp_main.adapter = sectionsPagerAdapter
        tab_main.setupWithViewPager(vp_main)

        supportActionBar?.elevation = 0f
    }
}
