package com.example.suantony.moviecatalogue.utils

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {
    private const val RESOURCE = "GLOBAL"

    val expressoTestIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        expressoTestIdlingResource.increment()
    }

    fun decrement() {
        expressoTestIdlingResource.decrement()
    }
}