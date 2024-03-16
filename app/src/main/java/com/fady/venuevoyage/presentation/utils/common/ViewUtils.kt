package com.fady.venuevoyage.presentation.utils.common

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.fady.venuevoyage.R
import com.fady.venuevoyage.databinding.ProgressDialogLayoutBinding
import com.google.android.material.snackbar.Snackbar
import com.tapadoo.alerter.Alerter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object ViewUtils {
    fun showLoadingDialog(activity: Activity?, hint: String?): Dialog? {
        if (activity == null || activity.isFinishing) {
            return null
        }

        val dialogBinding = ProgressDialogLayoutBinding.inflate(LayoutInflater.from(activity))

        val progressDialog = Dialog(activity)
        progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.setContentView(dialogBinding.root)

        if (!hint.isNullOrEmpty()) {
            dialogBinding.tvHint.show()
            dialogBinding.tvHint.text = hint
        } else {
            dialogBinding.tvHint.hide()
        }

        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()

        return progressDialog
    }

    fun hideLoadingDialog(mProgressDialog: Dialog?, activity: Activity?) {
        if (activity != null && !activity.isFinishing && mProgressDialog != null && mProgressDialog.isShowing) {
            mProgressDialog.dismiss()
        }
    }


    fun View.show() {
        if (visibility == View.VISIBLE) return

        visibility = View.VISIBLE
        if (this is Group) {
            this.requestLayout()
        }
    }

    fun View.hide() {
        if (visibility == View.GONE) return

        visibility = View.GONE
        if (this is Group) {
            this.requestLayout()
        }
    }

    fun Fragment.handleApiError(
        failure: Resource.Failure,
        retryAction: (() -> Unit)? = null,
        noDataAction: (() -> Unit)? = null,
        noInternetAction: (() -> Unit)? = null
    ) {
        when (failure.failureStatus) {
            FailureStatus.EMPTY -> {
                noDataAction?.invoke()
            }

            FailureStatus.NO_INTERNET -> {
                noInternetAction?.invoke()

                showNoInternetAlert(requireActivity())
            }

            FailureStatus.API_FAIL, FailureStatus.OTHER -> {
                noDataAction?.invoke()

                requireView().showSnackBar(
                    failure.message ?: resources.getString(R.string.some_error),
                    resources.getString(R.string.retry),
                    retryAction
                )
            }
        }
    }

    fun showNoInternetAlert(activity: Activity) {
        Alerter.create(activity)
            .setTitle(activity.resources.getString(R.string.connection_error))
            .setText(activity.resources.getString(R.string.no_internet))
            .setIcon(R.drawable.ic_no_internet)
            .setBackgroundColorRes(R.color.red)
            .enableClickAnimation(true)
            .enableSwipeToDismiss()
            .show()
    }

    fun View.showSnackBar(
        message: String,
        retryActionName: String? = null,
        action: (() -> Unit)? = null
    ) {
        val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)

        action?.let {
            snackBar.setAction(retryActionName) {
                it()
            }
        }

        snackBar.show()
    }

    fun Activity.setStatusBarColor(color: Int) {
        window.statusBarColor = getColorCompat(color)
    }

    fun Context.getColorCompat(color: Int) = ContextCompat.getColor(this, color)

    fun todayDate() =
        SimpleDateFormat(
            "EEEE, dd MMM yyyy",
            Locale.getDefault()
        ).format(Calendar.getInstance().time) ?: ""

    fun Context.getMyString(id: Int, vararg formatArgs: Any) =
        resources.getString(id, *formatArgs)

    fun String.getDateDescription(): String? {
        return SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.getDefault()
        ).parse(this)?.let {
            SimpleDateFormat(
                "EEEE",
                Locale.getDefault()
            ).format(
                it
            )
        }

    }

    fun View.showAnimation(context: Context) {
        this.visibility = View.VISIBLE
        this.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.slide_down
            )
        )
    }

    fun View.hideAnimation(context: Context) {
        this.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.slide_up
            )
        )
        this.visibility = View.GONE
    }

    fun Activity.hideKeyboard() {
        // Only runs if there is a view that is currently focused
        this.currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    enum class DrawerPage {
        HOME,
        PROFILE,
        TERMS_AND_CONDITIONS,
        LOGOUT
    }

    fun String.isValidPassword(): Boolean {
        val passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[\$@\$!%*?&])[A-Za-z\\d\$@\$!%*?&]{8,}\$".toRegex()
        return passwordRegex.matches(this)
    }

    fun String.isValidEmail(): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})".toRegex()
        return emailRegex.matches(this)
    }
    fun Context?.toast(@StringRes textId: Int, duration: Int = Toast.LENGTH_LONG) =
        this?.let { Toast.makeText(it, textId, duration).show() }

    fun Context?.toastStringText(text: String, duration: Int = Toast.LENGTH_LONG) =
        this?.let { Toast.makeText(it, text, duration).show() }

}