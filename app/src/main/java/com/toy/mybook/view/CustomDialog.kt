package com.toy.mybook.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.toy.mybook.databinding.CustomDialogBinding

class CustomDialog(context: Context, listener: CustomDialogListener):Dialog(context){
    private lateinit var binding: CustomDialogBinding
    private val TAG="CustomDialog"
    val mListener=listener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
    }

    class Builder(context: Context, listener:CustomDialogListener){
        private val dialog=CustomDialog(context, listener)
        private val TAG="CustomDialogBuilder"

        init{
            Log.i(TAG, "init")
            dialog.binding= CustomDialogBinding.inflate(dialog.layoutInflater)
            dialog.setContentView(dialog.binding.root)

            dialog.binding.ivClose.setOnClickListener {
                dialog.dismiss()
            }
            dialog.binding.btnOk.setOnClickListener{
                listener.onPositiveClicked(dialog.binding.et.text.toString())
                dialog.dismiss()
            }
        }

        fun setTitle(str: String):Builder{
            dialog.binding.title.text=str
            return this
        }

        fun setOkButton(str: String):Builder{
            dialog.binding.btnOk.text=str
            return this
        }

        fun show():CustomDialog{
            dialog.show()
            return dialog
        }
    }

    interface CustomDialogListener{
        fun onPositiveClicked(data: String)
    }
}