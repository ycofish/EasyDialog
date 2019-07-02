package com.ryanyu.easydialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.content.DialogInterface
import android.widget.FrameLayout
import android.widget.TextView


/**
 * Update 2019-01-10
 *
 * ██████╗ ██╗   ██╗ █████╗ ███╗   ██╗    ██╗   ██╗██╗   ██╗    ██╗     ██╗██████╗ ██████╗  █████╗ ██████╗ ██╗   ██╗
 * ██╔══██╗╚██╗ ██╔╝██╔══██╗████╗  ██║    ╚██╗ ██╔╝██║   ██║    ██║     ██║██╔══██╗██╔══██╗██╔══██╗██╔══██╗╚██╗ ██╔╝
 * ██████╔╝ ╚████╔╝ ███████║██╔██╗ ██║     ╚████╔╝ ██║   ██║    ██║     ██║██████╔╝██████╔╝███████║██████╔╝ ╚████╔╝
 * ██╔══██╗  ╚██╔╝  ██╔══██║██║╚██╗██║      ╚██╔╝  ██║   ██║    ██║     ██║██╔══██╗██╔══██╗██╔══██║██╔══██╗  ╚██╔╝
 * ██║  ██║   ██║   ██║  ██║██║ ╚████║       ██║   ╚██████╔╝    ███████╗██║██████╔╝██║  ██║██║  ██║██║  ██║   ██║
 * ╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═══╝       ╚═╝    ╚═════╝     ╚══════╝╚═╝╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝
 *
 *
 * ______                         _____    _           _
 * |  ____|                       |  __ \  (_)         | |
 * | |__      __ _   ___   _   _  | |  | |  _    __ _  | |   ___     __ _
 * |  __|    / _` | / __| | | | | | |  | | | |  / _` | | |  / _ \   / _` |
 * | |____  | (_| | \__ \ | |_| | | |__| | | | | (_| | | | | (_) | | (_| |
 * |______|  \__,_| |___/  \__, | |_____/  |_|  \__,_| |_|  \___/   \__, |
 *                         __/ |                                     __/ |
 *                         |___/                                    |___/
 *
 *
 * Created by Ryan Yu.
 */

class RYEasyDialog<T> {
    var canCancel: Boolean = true
    var recallFunction: (() -> T)? = null
    var recallFunctionWithValue: ((value: T) -> Unit)? = null
    var context: Context? = null
    var value: T? = null

    /**
     * Constructor RYEasyDialog
     *
     * @param context Context?
     * @param canCancel Boolean
     * @constructor
     */
    constructor(context: Context?, canCancel: Boolean) {
        this.context = context
        this.canCancel = canCancel
    }

    /**
     * Constructor RYEasyDialog Contains return function
     *
     * @param context Context?
     * @param canCancel Boolean
     * @param recallFunction Function<T>
     * @constructor
     */
    constructor(context: Context?, canCancel: Boolean, recallFunction: () -> T) {
        this.context = context
        this.canCancel = canCancel
        this.recallFunction = recallFunction
    }

    /**
     * Constructor RYEasyDialog Contains return function with parameter
     *
     * @param context Context?
     * @param canCancel Boolean
     * @param value T
     * @param recallFunctionWithValue Function1<[@kotlin.ParameterName] T, Unit>
     * @constructor
     */
    constructor(context: Context?, canCancel: Boolean, value: T, recallFunctionWithValue: (value: T) -> Unit) {
        this.context = context
        this.canCancel = canCancel
        this.value = value
        this.recallFunctionWithValue = recallFunctionWithValue
    }

    /**
     * Create custom layout dialog for network error
     *
     * @param layout View
     * @param titleView TextView
     * @param cancelBtn TextView
     * @param confirmBtn TextView
     */
    fun createCustomNetworkDialog(layout: View, titleView: TextView, cancelBtn: TextView, confirmBtn: TextView) {
        val dialog = Dialog(context)
        dialog.setContentView(layout)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
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

    /**
     * Create native dialog for network error
     */
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

    /**
     * Create a custom layout dialog for network error that can modify the title
     *
     * @param layout View
     * @param titleView TextView
     * @param cancelBtn TextView
     * @param confirmBtn TextView
     * @param title String
     */
    fun createCustomNetworkDialog(layout: View, titleView: TextView, cancelBtn: TextView, confirmBtn: TextView, title: String) {
        val dialog = Dialog(context)
        dialog.setContentView(layout)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
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

    /**
     * Create a native dialog for network error that can modify the title
     *
     * @param title String
     */
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

    /**
     * Create a custom layout dialog for any use that can modify anywhere
     *
     * @param layout Int
     * @param titleView TextView
     * @param cancelBtn TextView
     * @param confirmBtn TextView
     * @param title String
     * @param confirm String
     * @param cancel String
     */
    fun createCustomDialog(layout: Int,titleView: TextView, cancelBtn: TextView, confirmBtn: TextView, title: String, confirm: String, cancel: String) {
        val dialog = Dialog(context)
        dialog.setContentView(layout)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
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

    /**
     * Create a native dialog for any use that can modify title/confirm/cancel button
     *
     * @param title String
     * @param confirm String
     * @param cancel String
     */
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


    fun createCustomDialog(layout: Int) : Dialog {
        val dialog = Dialog(context)
        dialog.setContentView(layout)
        dialog.window?.setLayout(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if (!canCancel) {
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)
        }
        dialog.show()
        return dialog
    }

}
