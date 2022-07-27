package com.exsample.tictactoe.fragments

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.exsample.tictactoe.R

class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed(Runnable {
            initViews()
        }, 2000)

    }

    private fun initViews() {
        lifecycleScope.launchWhenResumed {
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        }

    }
}