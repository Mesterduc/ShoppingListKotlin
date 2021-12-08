package com.example.shoppingliststartcodekotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.change_product_dialog.*
import kotlinx.android.synthetic.main.change_product_dialog.view.*

class Dialog(val oldName: String, val changeProduct: (String, String, Int) -> (Unit)) : DialogFragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.change_product_dialog, container)
        dialog?.setTitle("Change Product")

        view.confirmButton.setOnClickListener {
            val name = productName.text.toString()
            val unit = productUnits.text.toString()
            if(name.isNotEmpty() && unit.isNotEmpty()){
                dismiss()
                changeProduct(oldName, name, unit.toInt())
            }
        }

        view.cancelButton.setOnClickListener{
            dismiss()
        }

        return view
    }
}