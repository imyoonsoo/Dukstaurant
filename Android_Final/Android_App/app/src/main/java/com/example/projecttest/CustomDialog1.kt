package com.example.projecttest

import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.projecttest.LoginUser.Companion.id
import kotlinx.coroutines.NonCancellable.cancel

interface CustomDialogCallback1 {
    fun onTextEntered1(text: Float)
}

class CustomDialog1 (private val callback: CustomDialogCallback1){
    companion object {
        fun showDialog(context: Context, callback: CustomDialogCallback1) {
            val builder = AlertDialog.Builder(context)
            val inflater = LayoutInflater.from(context)
            val dialogView = inflater.inflate(R.layout.first_dialog, null)
            builder.setView(dialogView)

            val infoInputButton = dialogView.findViewById<Button>(R.id.ok)
            val cancelButton = dialogView.findViewById<Button>(R.id.cancel)

            var totalCalorie : EditText = dialogView.findViewById(R.id.totalCalorie)
            val dialog = builder.create()

            infoInputButton.setOnClickListener{
                val totalCal_str =  totalCalorie.text.toString()
                if(totalCal_str.isEmpty()){
                    dialog.dismiss()
                } else {
                    val totalCal : Float = totalCal_str.toFloat()
                    callback.onTextEntered1(totalCal)
                    dialog.dismiss()
                }
            }
            cancelButton.setOnClickListener{
                dialog.dismiss()
            }
            dialog.show()
        }
    }
}