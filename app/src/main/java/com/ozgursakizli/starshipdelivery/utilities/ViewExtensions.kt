package com.ozgursakizli.starshipdelivery.utilities

import android.view.View

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.setVisibility(isVisible: Boolean) = if (isVisible) this.visible() else this.gone()