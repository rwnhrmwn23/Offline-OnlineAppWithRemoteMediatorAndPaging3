package com.astro.test.irwan.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.RadioButton
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.astro.test.irwan.databinding.ActivityMainBinding
import com.astro.test.irwan.utils.*
import com.astro.test.irwan.utils.Constant.KEY_PAGE
import com.astro.test.irwan.utils.Constant.KEY_PER_PAGE
import com.astro.test.irwan.utils.PreferenceManager.Companion.getPref
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
        with(binding) {
            val username = edtSearch.text.toString()
            if (username.isNotEmpty()) {
                mainViewModel.users(
                    username,
                    getPref(this@MainActivity).prefSortAsc,
                    KEY_PAGE,
                    KEY_PER_PAGE
                ).observe(this@MainActivity) { response ->
                    mainAdapter.submitData(lifecycle, response)

                    lifecycleScope.launch {
                        mainAdapter.loadStateFlow.collectLatest { loadStates ->
                            when (loadStates.refresh) {
                                is LoadState.Loading -> {
                                    progressCircular.visible()
                                    rvUser.gone()
                                    rbGroup.gone()
                                    tvUserNotFound.gone()
                                }
                                is LoadState.NotLoading -> {
                                    if (mainAdapter.itemCount > 0) {
                                        rvUser.visible()
                                        rbGroup.visible()
                                        tvUserNotFound.gone()
                                        progressCircular.gone()
                                        rvUser.layoutManager?.smoothScrollToPosition(
                                            rvUser,null, 0
                                        )
                                    } else {
                                        noData(true)
                                    }
                                }
                                is LoadState.Error -> {
                                    noData(true)
                                }
                            }
                        }
                    }

                }
            } else {
                noData(false)
            }
        }
    }

    private fun noData(showErrorMessage: Boolean) {
        with(binding) {
            rvUser.gone()
            rbGroup.gone()
            progressCircular.gone()
            if (showErrorMessage)
                tvUserNotFound.visible()
            else
                tvUserNotFound.gone()
        }
    }
}