package com.samplerecyclerview.ui.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this) { it?.let { t -> action(t) } }
}

fun <T> removeObserve(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.removeObserver(action)
}

fun <T> LifecycleOwner.observeEvent(
    liveData: LiveData<SingleEvent<T>>,
    action: (t: SingleEvent<T>) -> Unit
) {
    liveData.observe(this) { it?.let { t -> action(t) } }

}

fun <T> LifecycleOwner.observeOnce(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observeOnce(this) { it?.let { t -> action(t) } }
}

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {


        override fun onChanged(value: T) {
            observer.onChanged(value)
            removeObserver(this)
        }
    })
}