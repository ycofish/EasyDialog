package com.ryanyu.easydialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.content.DialogInterface
import android.widget.TextView


/**
 * Created by Ryan Yu on 3/1/2019.
 */

class RYEasyDialog<T> {
    var canCancel: Boolean = true
    var recallFunction: (() -> T)? = null
    var recallFunctionWithValue: ((value: T) -> Unit)? = null
    var context: Context? = null
    var value: T? = null

    constructor(context: Context?, canCancel: Boolean) {
        this.context = context
        this.canCancel = canCancel
    }

    constructor(context: Context?, canCancel: Boolean, recallFunction: () -> T) {
        this.context = context
        this.canCancel = canCancel
        this.recallFunction = recallFunction
    }

    constructor(context: Context?, canCancel: Boolean, value: T, recallFunctionWithValue: (value: T) -> Unit) {
        this.context = context
        this.canCancel = canCancel
        this.value = value
        this.recallFunctionWithValue = recallFunctionWithValue
    }

    fun createCustomNetworkDialog(layout: View, titleView: TextView, cancelBtn: TextView, confirmBtn: TextView) {
        val dialog = Dialog(context)
        dialog.setContentView(layout)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        titleView.text = context?.resources?.getString(R.string.global_network_error)
        confirmBtn.text = context?.resources?.getString(R.string.global_retry)
        cancelBtn.text = context?.resources?.getString(R.string.gloda_cancel)

        if (!canCancel) {
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)
            cancelBtn.setVisibility(View.GONE)
        }

        confirmBtn.setOnClickListener {
            recallFunction?.invoke()
            value?.let { it1 -> recallFunctionWithValue?.invoke(it1) }
            dialog.dismiss()
        }

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    fun createNativeNetworkDialog() {
        val builder = AlertDialog.Builder(context)
        if (!canCancel) {
            builder.setCancelable(false)
        } else {
            builder.setNegativeButton(context?.resources?.getString(R.string.gloda_cancel)) { dialogInterface, id -> }
        }
        builder.setMessage(context?.resources?.getString(R.string.global_network_error))
        builder.setPositiveButton(context?.resources?.getString(R.string.global_retry)) { dialog, id ->
            recallFunction?.invoke()
            value?.let { it1 -> recallFunctionWithValue?.invoke(it1) }
        }

        val dialog = builder.create()
        dialog.show()
    }


    fun createCustomNetworkDialog(layout: View, titleView: TextView, cancelBtn: TextView, confirmBtn: TextView, title: String) {
        val dialog = Dialog(context)
        dialog.setContentView(layout)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        titleView.text = title
        confirmBtn.text = context?.resources?.getString(R.string.global_retry)
        cancelBtn.text = context?.resources?.getString(R.string.gloda_cancel)

        if (!canCancel) {
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)
            cancelBtn.setVisibility(View.GONE)
        }

        confirmBtn.setOnClickListener {
            recallFunction?.invoke()
            value?.let { it1 -> recallFunctionWithValue?.invoke(it1) }
            dialog.dismiss()
        }

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    fun createNativeNetworkDialog(title: String) {
        val builder = AlertDialog.Builder(context)
        if (!canCancel) {
            builder.setCancelable(false)
        } else {
            builder.setNegativeButton(context?.resources?.getString(R.string.gloda_cancel)) { dialogInterface, id -> }
        }
        builder.setMessage(title)
        builder.setPositiveButton(context?.resources?.getString(R.string.global_retry)) { dialog, id ->
            recallFunction?.invoke()
            value?.let { it1 -> recallFunctionWithValue?.invoke(it1) }
        }

        val dialog = builder.create()
        dialog.show()
    }


    fun createCustomDialog(layout: Int,titleView: TextView, cancelBtn: TextView, confirmBtn: TextView, title: String, confirm: String, cancel: String) {
        val dialog = Dialog(context)
        dialog.setContentView(layout)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        titleView.text = title
        confirmBtn.text = confirm
        cancelBtn.text = cancel

        if (!canCancel) {
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)
            cancelBtn.setVisibility(View.GONE)
        }

        confirmBtn.setOnClickListener {
            recallFunction?.invoke()
            value?.let { it1 -> recallFunctionWithValue?.invoke(it1) }
            dialog.dismiss()
        }

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    fun createNativeDialog(title: String, confirm: String, cancel: String) {
        val builder = AlertDialog.Builder(context)
        if (!canCancel) {
            builder.setCancelable(false)
        } else {
            builder.setNegativeButton(cancel) { dialogInterface, id -> }
        }

        builder.setMessage(title)
        builder.setPositiveButton(confirm) { dialog, id ->
            recallFunction?.invoke()
            value?.let { it1 -> recallFunctionWithValue?.invoke(it1) }
        }

        val dialog = builder.create()
        dialog.show()
    }


}
