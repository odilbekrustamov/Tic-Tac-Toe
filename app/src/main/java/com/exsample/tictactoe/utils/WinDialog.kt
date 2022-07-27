package com.exsample.tictactoe.utils

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import com.exsample.tictactoe.databinding.WinDialogLayoutBinding
import com.exsample.tictactoe.helper.OnClickEvent

class WinDialog(private val onClickEvent: OnClickEvent) {


    fun showCalendarDialog(activity: Activity?, message: String) {
        val binding = WinDialogLayoutBinding.inflate(LayoutInflater.from(activity))
        val dialog = Dialog(activity!!)
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(binding.root)
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(true)
        }

        binding.tvMessage.text = message

        binding.btnBack.setOnClickListener {
            onClickEvent.setOnClickBackListner()
            dialog.dismiss()
        }

        binding.btnPlayAgain.setOnClickListener {
            onClickEvent.setOnClickPlayAgainListner()
            dialog.dismiss()
        }

        dialog.show()
    }
}