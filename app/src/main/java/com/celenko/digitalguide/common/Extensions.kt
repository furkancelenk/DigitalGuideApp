package com.celenko.digitalguide.common

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(text: String) {
    Snackbar.make(this, text, 1000).show()
}