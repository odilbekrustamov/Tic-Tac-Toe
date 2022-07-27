package com.exsample.tictactoe.fragments.singleplayer

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.exsample.tictactoe.R
import com.exsample.tictactoe.databinding.FragmentSinglePlayerBinding
import com.exsample.tictactoe.fragments.BaseFragment
import com.exsample.tictactoe.helper.OnClickEvent
import com.exsample.tictactoe.model.GameResults
import com.exsample.tictactoe.utils.KeyValues.COMPYUTER
import com.exsample.tictactoe.utils.WinDialog

class SinglePlayerFragment : BaseFragment(R.layout.fragment_single_player) {

    private lateinit var binding: FragmentSinglePlayerBinding
    private var boxesSelectedBy: ArrayList<Int> = arrayListOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
    private var conbinationList: ArrayList<Array<Int>> = ArrayList()
    private val viewModel: SinglePlayerViewModel by viewModels()
    private var gameResults = GameResults(0)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSinglePlayerBinding.bind(view)

        conbinationList.add(arrayOf(0, 1, 2))
        conbinationList.add(arrayOf(3, 4, 5))
        conbinationList.add(arrayOf(6, 7, 8))
        conbinationList.add(arrayOf(0, 3, 6))
        conbinationList.add(arrayOf(1, 4, 7))
        conbinationList.add(arrayOf(2, 5, 8))
        conbinationList.add(arrayOf(2, 4, 6))
        conbinationList.add(arrayOf(0, 4, 8))

        viewModel.isRowIsExist(COMPYUTER)
        initObservesIsRowExist()

        initViews()
    }

    private fun initViews() {

        binding.ivImageOne.setOnClickListener {
            if (boxesSelectedBy[0] == 0){
                binding.ivImageOne.setImageResource(R.drawable.iv_wrong)
                boxesSelectedBy[0] = 1
                onPlayerClick()
            }
        }

        binding.ivImageTwo.setOnClickListener {
            if (boxesSelectedBy[1] == 0){
                binding.ivImageTwo.setImageResource(R.drawable.iv_wrong)
                boxesSelectedBy[1] = 1
                onPlayerClick()
            }
        }
        binding.ivImageThree.setOnClickListener {
            if (boxesSelectedBy[2] == 0){
                binding.ivImageThree.setImageResource(R.drawable.iv_wrong)
                boxesSelectedBy[2] = 1
                onPlayerClick()
            }
        }
        binding.ivImageFour.setOnClickListener {
            if (boxesSelectedBy[3] == 0){
                binding.ivImageFour.setImageResource(R.drawable.iv_wrong)
                boxesSelectedBy[3] = 1
                onPlayerClick()
            }
        }
        binding.ivImageFive.setOnClickListener {
            if (boxesSelectedBy[4] == 0){
                binding.ivImageFive.setImageResource(R.drawable.iv_wrong)
                boxesSelectedBy[4] = 1
                onPlayerClick()
            }
        }
        binding.ivImageSix.setOnClickListener {
            if (boxesSelectedBy[5] == 0){
                binding.ivImageSix.setImageResource(R.drawable.iv_wrong)
                boxesSelectedBy[5] = 1
                onPlayerClick()
            }
        }
        binding.ivImageSeven.setOnClickListener {
            if (boxesSelectedBy[6] == 0){
                binding.ivImageSeven.setImageResource(R.drawable.iv_wrong)
                boxesSelectedBy[6] = 1
                onPlayerClick()
            }
        }
        binding.ivImageEight.setOnClickListener {
            if (boxesSelectedBy[7] == 0){
                binding.ivImageEight.setImageResource(R.drawable.iv_wrong)
                boxesSelectedBy[7] = 1
                onPlayerClick()
            }
        }
        binding.ivImageNine.setOnClickListener {
            if (boxesSelectedBy[8] == 0){
                binding.ivImageNine.setImageResource(R.drawable.iv_wrong)
                boxesSelectedBy[8] = 1
                onPlayerClick()
            }
        }
    }

    fun onPlayerClick(){
        if (chceckPlayerWin(1)){
            val dialog = WinDialog(object: OnClickEvent{
                override fun setOnClickBackListner() {
                    requireActivity().onBackPressed()
                }

                override fun setOnClickPlayAgainListner() {
                   requireActivity().onBackPressed()
                    findNavController().navigate(R.id.action_homeFragment_to_singlePlayerFragment)
                }

            })
            dialog.showCalendarDialog(requireActivity(),"Player You Won!")
            gameResults.meResult++
            viewModel.insertGameResultsToDB(gameResults)
        }else {
            applyPlayerTurn(2)
            Handler().postDelayed(Runnable {
                compyuterClick()
            }, 300)
        }
    }

    private fun chceckPlayerWin(playerId: Int): Boolean{
        var isPlayerWon = false
        for (i in 0..conbinationList.size - 1){
            val combination = conbinationList.get(i)
            if (boxesSelectedBy[combination[0]].equals(playerId) &&
                boxesSelectedBy[combination[1]].equals(playerId) &&
                boxesSelectedBy[combination[2]].equals(playerId)){
                isPlayerWon = true
                break
            }
        }
       return isPlayerWon
    }

    fun compyuterClick(){
        while (true){
            val ran = (0..8).random()
            if (boxesSelectedBy[ran] == 0){
                compyuterClickImage(ran)
                boxesSelectedBy[ran] = 2
                if (chceckPlayerWin(2)){
                    val dialog =WinDialog(object : OnClickEvent{
                        override fun setOnClickBackListner() {
                            requireActivity().onBackPressed()
                        }

                        override fun setOnClickPlayAgainListner() {
                            requireActivity().onBackPressed()
                            findNavController().navigate(R.id.action_homeFragment_to_singlePlayerFragment)
                        }

                    })
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                        dialog.showCalendarDialog(requireActivity(), "Player Compyuter Won!")

                        gameResults.youResult++
                        viewModel.insertGameResultsToDB(gameResults)
                    }
                    break
                }
                break
            }
        }
        applyPlayerTurn(1)
    }

    fun compyuterClickImage(num: Int){
        when(num){
            0 ->{
                binding.ivImageOne.setImageResource(R.drawable.iv_queue)
            }
            1 ->{
                binding.ivImageTwo.setImageResource(R.drawable.iv_queue)
            }
            2 ->{
                binding.ivImageThree.setImageResource(R.drawable.iv_queue)
            }
            3 ->{
                binding.ivImageFour.setImageResource(R.drawable.iv_queue)
            }
            4 ->{
                binding.ivImageFive.setImageResource(R.drawable.iv_queue)
            }
            5 ->{
                binding.ivImageSix.setImageResource(R.drawable.iv_queue)
            }
            6 ->{
                binding.ivImageSeven.setImageResource(R.drawable.iv_queue)
            }
            7 ->{
                binding.ivImageEight.setImageResource(R.drawable.iv_queue)
            }
            8 ->{
                binding.ivImageNine.setImageResource(R.drawable.iv_queue)
            }
        }
    }

    private fun applyPlayerTurn(playerTurn: Int) {
        if (playerTurn.equals(1)){
            binding.llPlayOne.setBackgroundResource(R.drawable.view_button_background_dark_blue_stroke_20dp)
            binding.llPlayTwo.setBackgroundResource(R.drawable.view_button_background_dark_blue_20dp)
        }else{
            binding.llPlayTwo.setBackgroundResource(R.drawable.view_button_background_dark_blue_stroke_20dp)
            binding.llPlayOne.setBackgroundResource(R.drawable.view_button_background_dark_blue_20dp)
        }
    }

    private fun initObservesIsRowExist() {
        viewModel.isUser.observe(requireActivity(), {
            Log.d("TAG", "initObservesIsRowExist: ${it}")
            if (it){
                viewModel.getItem(COMPYUTER)
                initObservesgetObject()
            }else{
                viewModel.insertGameResultsToDB(GameResults(1, 0, 0, "Me", COMPYUTER))
            }
        })
    }

    private fun initObservesgetObject() {
        viewModel.getItem.observe(requireActivity(), {
            gameResults = it
            binding.tvScoreOne.text = it.meResult.toString()
            binding.tvScoreTwo.text = it.youResult.toString()
            binding.tvPlayerTwo.text = it.yourName
            binding.tvPlayerOne.text = it.meName
        })
    }
}