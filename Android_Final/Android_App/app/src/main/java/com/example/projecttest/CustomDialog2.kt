package com.example.projecttest

import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.NonCancellable.cancel

interface CustomDialogCallback2 {
    fun onTextEntered2(text: ArrayList<Float>)
}

class CustomDialog2 (private val callback: CustomDialogCallback2){
    companion object {
        fun showDialog(context: Context, callback: CustomDialogCallback2) {
            val builder = AlertDialog.Builder(context)
            val inflater = LayoutInflater.from(context)
            val dialogView = inflater.inflate(R.layout.custom_dialog, null)
            builder.setView(dialogView)

            val infoInputButton = dialogView.findViewById<Button>(R.id.ok)
            val cancelButton = dialogView.findViewById<Button>(R.id.cancel)
            var editCalorie : EditText = dialogView.findViewById(R.id.totalCalorie)
            var editCarbo: EditText = dialogView.findViewById(R.id.editCarbo)
            var editProtein : EditText = dialogView.findViewById(R.id.editProtein)
            var editFat : EditText = dialogView.findViewById(R.id.editFat)
            val dialog = builder.create()


            val infoList = ArrayList<Float>()

            infoInputButton.setOnClickListener{
                val calorie : String = editCalorie.text.toString()
                val carbo : String = editCarbo.text.toString()
                val protein : String = editProtein.text.toString()
                val fat : String = editFat.text.toString()

                if(calorie.toString().isEmpty())  //숫자 입력하지 않았을 때
                    dialog.dismiss()
                else if(carbo.toString().isEmpty())
                    dialog.dismiss()
                else if(protein.toString().isEmpty())
                    dialog.dismiss()
                else if(fat.toString().isEmpty())
                    dialog.dismiss()
                else{
                    infoList.add(calorie.toFloat())
                    infoList.add(carbo.toFloat())
                    infoList.add(protein.toFloat())
                    infoList.add(fat.toFloat())

                    callback.onTextEntered2(infoList)
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