package com.exsample.tictactoe.fragments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.exsample.tictactoe.R
import com.exsample.tictactoe.databinding.FragmentHomeBinding
import com.exsample.tictactoe.utils.KeyValues.IS_OPPONENT
import com.exsample.tictactoe.utils.KeyValues.PLAYER_NAME

class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        initViews()
    }

    private fun initViews() {

        binding.ivSinglerPlayer.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_singlePlayerFragment)
        }

        binding.ivTwoPlayer.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_signUpFragment)
        }

    }

}