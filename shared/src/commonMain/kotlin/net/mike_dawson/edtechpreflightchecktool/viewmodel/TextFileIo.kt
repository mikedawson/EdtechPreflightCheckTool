package net.mike_dawson.edtechpreflightchecktool.viewmodel

expect fun saveTextFile(name: String, text: String)

expect fun openTextFile(onContentRead: (String) -> Unit)

