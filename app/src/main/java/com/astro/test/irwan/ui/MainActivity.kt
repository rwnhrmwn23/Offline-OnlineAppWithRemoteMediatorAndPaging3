package com.astro.test.irwan.ui

import android.os.Bundle
import com.astro.test.irwan.databinding.ActivityMainBinding
import com.astro.test.irwan.utils.BaseActivityBinding

class MainActivity : BaseActivityBinding<ActivityMainBinding>() {


    override fun inflateBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreateBinding(savedInstanceState: Bundle?) {


    }

}