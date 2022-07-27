package com.exsample.tictactoe.fragments.twoplayer

import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.exsample.tictactoe.R
import com.exsample.tictactoe.databinding.FragmentTwoPlayersBinding
import com.exsample.tictactoe.fragments.BaseFragment
import com.exsample.tictactoe.helper.OnClickEvent
import com.exsample.tictactoe.utils.KeyValues
import com.exsample.tictactoe.utils.WinDialog
import com.google.firebase.database.*

class TwoPlayersFragment : BaseFragment(R.layout.fragment_two_players) {
    private lateinit var binding: FragmentTwoPlayersBinding
    private var conbinationList: ArrayList<Array<Int>> = ArrayList()
    private var doneBox = ArrayList<String>()
    private var playerUniqueId = "0"
    private var opponentFound = false
    private var opponentUniqeuId = "0"
    private var status = "matching"
    private var playerTurn = ""
    private var getPlayerName = "Kompyuter"
    private var connictionId = ""
    private var turnsEventListner: ValueEventListener? = null
    private var wonEventListner: ValueEventListener? = null
    private var boxesSelectedBy = arrayOf("", "", "", "", "", "", "", "", "")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            getPlayerName = it.get(KeyValues.PLAYER_NAME) as String
        }
    }

    var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://tic-tac-toe-84173-default-rtdb.firebaseio.com/")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTwoPlayersBinding.bind(view)
        initViews()
    }

    private fun initViews() {
        conbinationList.add(arrayOf(0, 1, 2))
        conbinationList.add(arrayOf(3, 4, 5))
        conbinationList.add(arrayOf(6, 7, 8))
        conbinationList.add(arrayOf(0, 3, 6))
        conbinationList.add(arrayOf(1, 4, 7))
        conbinationList.add(arrayOf(2, 5, 8))
        conbinationList.add(arrayOf(2, 4, 6))
        conbinationList.add(arrayOf(0, 4, 8))

        val progreessDialog = ProgressDialog(requireContext())
        progreessDialog.setCancelable(false)
        progreessDialog.setMessage("Wating for Opponent")
        progreessDialog.show()

        playerUniqueId = System.currentTimeMillis().toString()

        binding.tvPlayerOne.text = getPlayerName

        databaseReference.child("connections").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!opponentFound){
                    if (snapshot.hasChildren()){
                        for (connections in snapshot.children){
                            val conId = connections.key
                            val getPlayerCount = connections.childrenCount.toInt()

                            if (status.equals("waiting")){
                                if (getPlayerCount == 2){
                                    playerTurn = playerUniqueId
                                    applyPlayerTurn(playerTurn)

                                    var playedFound = false

                                    for (players in connections.children){
                                        val getPlayerUniqueId = players.key
                                        if (getPlayerUniqueId.equals(playerUniqueId)){
                                            playedFound = true
                                        }else if (playedFound){
                                            val getOpponentPlayerName = players.child("player_name").getValue(String::class.java)

                                            binding.tvPlayerTwo.text = getOpponentPlayerName

                                            connictionId = conId.toString()
                                            opponentFound = true

                                            databaseReference.child("turns").child(connictionId).addValueEventListener(turnsEventListner!!)
                                            databaseReference.child("won").child(connictionId).addValueEventListener(wonEventListner!!)

                                            if (progreessDialog.isShowing){
                                                progreessDialog.dismiss()
                                            }

                                            databaseReference.child("connections").removeEventListener(this)
                                        }
                                    }
                                }
                            }
                            else{
                                if (getPlayerCount == 1){
                                    connections.child(playerUniqueId).child("player_name").ref.setValue(getPlayerName)

                                    for (players in connections.children){
                                        val getOpponentName = players.child("player_name").getValue(String::class.java)
                                        opponentUniqeuId = players.key!!
                                        playerTurn = opponentUniqeuId
                                        applyPlayerTurn(playerTurn)
                                        binding.tvPlayerTwo.text = getOpponentName
                                        connictionId = conId!!
                                        opponentFound = true
                                        databaseReference.child("turns").child(connictionId).addValueEventListener(turnsEventListner!!)
                                        databaseReference.child("won").child(connictionId).addValueEventListener(wonEventListner!!)

                                        if (progreessDialog.isShowing){
                                            progreessDialog.dismiss()
                                        }

                                        databaseReference.child("connections").removeEventListener(this)

                                        break
                                    }
                                }
                            }
                        }
                        if (!opponentFound && !status.equals("waiting")){
                            val connectionUniqueId = System.currentTimeMillis().toString()
                            snapshot.child(connectionUniqueId).child(playerUniqueId).child("player_name").ref.setValue(getPlayerName)

                            status = "waiting"
                        }
                    }else{
                        val connectionUniqueId = System.currentTimeMillis().toString()
                        snapshot.child(connectionUniqueId).child(playerUniqueId).child("player_name").ref.setValue(getPlayerName)

                        status = "waiting"
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

        turnsEventListner = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children){
                    if (dataSnapshot.childrenCount.toInt() == 2){
                        val getBoxPosition = dataSnapshot.child("box_position").getValue(String::class.java)!!.toInt()
                        val getPlayerId = dataSnapshot.child("player_id").getValue(String::class.java)
                        if (!doneBox.contains(getBoxPosition.toString())){
                            doneBox.add(getBoxPosition.toString())
                            if (getBoxPosition == 1){
                                selectBox(binding.ivImageOne, getBoxPosition, getPlayerId!!)
                            }else if (getBoxPosition == 2){
                                selectBox(binding.ivImageTwo, getBoxPosition, getPlayerId!!)
                            }else if (getBoxPosition == 3){
                                selectBox(binding.ivImageThree, getBoxPosition, getPlayerId!!)
                            }else if (getBoxPosition == 4){
                                selectBox(binding.ivImageFour, getBoxPosition, getPlayerId!!)
                            }else if (getBoxPosition == 5){
                                selectBox(binding.ivImageFive, getBoxPosition, getPlayerId!!)
                            }else if (getBoxPosition == 6){
                                selectBox(binding.ivImageSix, getBoxPosition, getPlayerId!!)
                            }else if (getBoxPosition == 7){
                                selectBox(binding.ivImageSeven, getBoxPosition, getPlayerId!!)
                            }else if (getBoxPosition == 8){
                                selectBox(binding.ivImageEight, getBoxPosition, getPlayerId!!)
                            }else if (getBoxPosition == 9){
                                selectBox(binding.ivImageNine, getBoxPosition, getPlayerId!!)
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }

        wonEventListner = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild("player_id")){
                    val getWinPlayerId = snapshot.child("player_id").getValue(String::class.java)
                    val dialog = WinDialog(object : OnClickEvent {
                        override fun setOnClickBackListner() {
                            requireActivity().onBackPressed()
                            requireActivity().onBackPressed()
                        }

                        override fun setOnClickPlayAgainListner() {
                            requireActivity().onBackPressed()
                        }

                    })
                    if (getWinPlayerId.equals(playerUniqueId)){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            dialog.showCalendarDialog(requireActivity(), "You won the game")

                        }
                    }else{
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            dialog.showCalendarDialog(requireActivity(), "Opponnent won the game")
                        }
                    }

                    databaseReference.child("turns").child(connictionId).removeEventListener(turnsEventListner!!)
                    databaseReference.child("won").child(connictionId).removeEventListener(wonEventListner!!)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }
        binding.ivImageOne.setOnClickListener {
            if (!doneBox.contains("1") && playerTurn.equals(playerUniqueId)){
                binding.ivImageOne.setImageResource(R.drawable.iv_wrong)
                databaseReference.child("turns").child(connictionId).child((doneBox.size + 1).toString()).child("box_position").setValue("1")
                databaseReference.child("turns").child(connictionId).child((doneBox.size + 1).toString()).child("player_id").setValue(playerUniqueId)

                playerTurn = opponentUniqeuId
            }
        }

        binding.ivImageTwo.setOnClickListener {
            if (!doneBox.contains("2") && playerTurn.equals(playerUniqueId)){
                binding.ivImageTwo.setImageResource(R.drawable.iv_wrong)
                databaseReference.child("turns").child(connictionId).child((doneBox.size + 1).toString()).child("box_position").setValue("2")
                databaseReference.child("turns").child(connictionId).child((doneBox.size + 1).toString()).child("player_id").setValue(playerUniqueId)

                playerTurn = opponentUniqeuId
            }
        }
        binding.ivImageThree.setOnClickListener {
            if (!doneBox.contains("3") && playerTurn.equals(playerUniqueId)){
                binding.ivImageThree.setImageResource(R.drawable.iv_wrong)
                databaseReference.child("turns").child(connictionId).child((doneBox.size + 1).toString()).child("box_position").setValue("3")
                databaseReference.child("turns").child(connictionId).child((doneBox.size + 1).toString()).child("player_id").setValue(playerUniqueId)

                playerTurn = opponentUniqeuId
            }
        }
        binding.ivImageFour.setOnClickListener {
            if (!doneBox.contains("4") && playerTurn.equals(playerUniqueId)){
                binding.ivImageFour.setImageResource(R.drawable.iv_wrong)
                databaseReference.child("turns").child(connictionId).child((doneBox.size + 1).toString()).child("box_position").setValue("4")
                databaseReference.child("turns").child(connictionId).child((doneBox.size + 1).toString()).child("player_id").setValue(playerUniqueId)

                playerTurn = opponentUniqeuId
            }
        }
        binding.ivImageFive.setOnClickListener {
            if (!doneBox.contains("5") && playerTurn.equals(playerUniqueId)){
                binding.ivImageFive.setImageResource(R.drawable.iv_wrong)
                databaseReference.child("turns").child(connictionId).child((doneBox.size + 1).toString()).child("box_position").setValue("5")
                databaseReference.child("turns").child(connictionId).child((doneBox.size + 1).toString()).child("player_id").setValue(playerUniqueId)

                playerTurn = opponentUniqeuId
            }
        }
        binding.ivImageSix.setOnClickListener {
            if (!doneBox.contains("6") && playerTurn.equals(playerUniqueId)){
                binding.ivImageSix.setImageResource(R.drawable.iv_wrong)
                databaseReference.child("turns").child(connictionId).child((doneBox.size + 1).toString()).child("box_position").setValue("6")
                databaseReference.child("turns").child(connictionId).child((doneBox.size + 1).toString()).child("player_id").setValue(playerUniqueId)

                playerTurn = opponentUniqeuId
            }
        }
        binding.ivImageSeven.setOnClickListener {
            if (!doneBox.contains("7") && playerTurn.equals(playerUniqueId)){
                binding.ivImageSeven.setImageResource(R.drawable.iv_wrong)
                databaseReference.child("turns").child(connictionId).child((doneBox.size + 1).toString()).child("box_position").setValue("7")
                databaseReference.child("turns").child(connictionId).child((doneBox.size + 1).toString()).child("player_id").setValue(playerUniqueId)

                playerTurn = opponentUniqeuId
            }
        }
        binding.ivImageEight.setOnClickListener {
            if (!doneBox.contains("8") && playerTurn.equals(playerUniqueId)){
                binding.ivImageEight.setImageResource(R.drawable.iv_wrong)
                databaseReference.child("turns").child(connictionId).child((doneBox.size + 1).toString()).child("box_position").setValue("8")
                databaseReference.child("turns").child(connictionId).child((doneBox.size + 1).toString()).child("player_id").setValue(playerUniqueId)

                playerTurn = opponentUniqeuId
            }
        }
        binding.ivImageNine.setOnClickListener {
            if (!doneBox.contains("9") && playerTurn.equals(playerUniqueId)){
                binding.ivImageNine.setImageResource(R.drawable.iv_wrong)
                databaseReference.child("turns").child(connictionId).child((doneBox.size + 1).toString()).child("box_position").setValue("9")
                databaseReference.child("turns").child(connictionId).child((doneBox.size + 1).toString()).child("player_id").setValue(playerUniqueId)

                playerTurn = opponentUniqeuId
            }
        }
    }

    private fun applyPlayerTurn(playerTurn: String) {
        if (playerTurn.equals(playerUniqueId)){
            binding.llPlayOne.setBackgroundResource(R.drawable.view_button_background_dark_blue_stroke_20dp)
            binding.llPlayTwo.setBackgroundResource(R.drawable.view_button_background_dark_blue_20dp)
        }else{
            binding.llPlayTwo.setBackgroundResource(R.drawable.view_button_background_dark_blue_stroke_20dp)
            binding.llPlayOne.setBackgroundResource(R.drawable.view_button_background_dark_blue_20dp)
        }
    }

    private fun selectBox(imageView: ImageView, selectBoxPosition: Int, selectedByPlayer: String){
        boxesSelectedBy[selectBoxPosition - 1] = selectedByPlayer

        if (selectedByPlayer.equals(playerUniqueId)){
            imageView.setImageResource(R.drawable.iv_wrong)
            playerTurn = opponentUniqeuId
        }else{
            imageView.setImageResource(R.drawable.iv_queue)
            playerTurn = playerUniqueId
        }
        applyPlayerTurn(playerTurn)
        if (chceckPlayerWin(selectedByPlayer)){
            databaseReference.child("won").child(connictionId).child("player_id").setValue(selectedByPlayer)
        }
        if (doneBox.size == 9){
            val dialog = WinDialog(object : OnClickEvent {
                override fun setOnClickBackListner() {
                    requireActivity().onBackPressed()
                    requireActivity().onBackPressed()
                }

                override fun setOnClickPlayAgainListner() {
                    requireActivity().onBackPressed()
                }

            })
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dialog.showCalendarDialog(requireActivity(), "It is a Draw!")
            }
        }
    }

    private fun chceckPlayerWin(playerId: String): Boolean{
        var isPlayerWon = false

        for (i in 0..conbinationList.size - 1){
            val combination = conbinationList.get(i)

            if (boxesSelectedBy[combination[0]].equals(playerId) &&
                boxesSelectedBy[combination[1]].equals(playerId) &&
                boxesSelectedBy[combination[2]].equals(playerId)){
                isPlayerWon = true
            }
        }
        return isPlayerWon
    }
}