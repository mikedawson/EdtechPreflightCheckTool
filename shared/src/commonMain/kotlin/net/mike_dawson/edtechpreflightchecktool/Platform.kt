package net.mike_dawson.edtechpreflightchecktool

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform