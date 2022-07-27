package com.exsample.tictactoe.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.exsample.tictactoe.R
import com.exsample.tictactoe.databinding.FragmentSignUpBinding
import com.exsample.tictactoe.utils.KeyValues.PLAYER_NAME

class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {
    private lateinit var binding: FragmentSignUpBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding =  FragmentSignUpBinding.bind(view)

        initViews()
    }

    private fun initViews() {

        binding.btnStartGame.setOnClickListener {
            val name = binding.etPlayerName.text.toString()

            if (name.isEmpty()){
                Log.d("TAG", "initViews: wqdq")
                Toast.makeText(requireContext(), "Iltimos o'yinchi ismini kiriting.", Toast.LENGTH_SHORT).show()
            }else{
                Log.d("TAG", "initViews: ")
                findNavController().navigate(R.id.action_signUpFragment_to_gameFragment, bundleOf(PLAYER_NAME to name))
            }
        }
    }

}