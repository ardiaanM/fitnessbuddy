//The Information & More Tab.

package com.example.fitnessbuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fitnessbuddy.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
