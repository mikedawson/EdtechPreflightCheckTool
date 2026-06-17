package net.mike_dawson.edtechpreflightchecktool.app


data class Snack(
    val message: String,
    val action: String? = null,
    val onAction: (() -> Unit)? = null,
)
