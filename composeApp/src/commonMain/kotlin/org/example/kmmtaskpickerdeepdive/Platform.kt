package org.example.kmmtaskpickerdeepdive

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform