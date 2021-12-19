package com.example.bankmanagement.widgets.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.bankmanagement.R

class CustomSpinnerAdapter(context: Context, layoutId: Int, items : List<String>): ArrayAdapter<String>(context, layoutId, items) {
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return super.getDropDownView(position, convertView, parent)
    }
}