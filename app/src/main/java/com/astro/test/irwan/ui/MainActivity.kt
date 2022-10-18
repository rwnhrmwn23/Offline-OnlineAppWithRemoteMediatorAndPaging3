package com.astro.test.irwan.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.RadioButton
import com.astro.test.irwan.databinding.ActivityMainBinding
import com.astro.test.irwan.utils.BaseActivityBinding
import com.astro.test.irwan.utils.Constant.KEY_PAGE
import com.astro.test.irwan.utils.Constant.KEY_PER_PAGE
import com.astro.test.irwan.utils.PreferenceManager.Companion.getPref
import com.astro.test.irwan.utils.gone
import com.astro.test.irwan.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivityBinding<ActivityMainBinding>() {

    private lateinit var mainAdapter: MainAdapter
    private val mainViewModel: MainViewModel by viewModel()

    override fun inflateBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreateBinding(savedInstanceState: Bundle?) {

        mainAdapter = MainAdapter()
        binding.rvUser.adapter = mainAdapter
        binding.rvUser.adapter = mainAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                mainAdapter.retry()
            }
        )

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                Looper.myLooper()?.let {
                    Handler(it).postDelayed({
                        loadUser()
                    }, 300)
                }
            }
        })
        if (getPref(this).prefSortAsc) {
            binding.rbAsc.isChecked = true
            binding.rbDesc.isChecked = false
        } else {
            binding.rbAsc.isChecked = false
            binding.rbDesc.isChecked = true
        }


        binding.rbGroup.setOnCheckedChangeListener { _, checkedId ->
            when (findViewById<RadioButton>(checkedId)) {
                binding.rbAsc -> {
                    getPref(this).prefSortAsc = true
                }
                binding.rbDesc -> {
                    getPref(this).prefSortAsc = false
                }
            }
            loadUser()
        }
    }

    private fun loadUser() {
        val username = binding.edtSearch.text.toString()
        if (username.isNotEmpty()) {
            mainViewModel.users(
                username,
                getPref(this).prefSortAsc,
                KEY_PAGE,
                KEY_PER_PAGE
            ).observe(this) { response ->
                if (response != null) {
                    mainAdapter.submitData(lifecycle, response)
                    binding.rvUser.visible()
                    binding.rbGroup.visible()
                } else {
                    binding.rvUser.gone()
                    binding.rbGroup.gone()
                }
            }
        }
    }
}