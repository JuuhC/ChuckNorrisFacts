package com.carrati.chucknorrisfacts.presentation.viewmodels

import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.SingleTransformer

sealed class ViewState<out T> {
    data class Success<T>(val data: T) : ViewState<T>()
    data class Failed(val throwable: Throwable) : ViewState<Nothing>()
}

class StateMachineSingle<T>: SingleTransformer<T, ViewState<T>> {

    override fun apply(upstream: Single<T>): SingleSource<ViewState<T>> {
        return upstream
            .map {
                ViewState.Success(it) as ViewState<T>
            }
            .onErrorReturn {
                ViewState.Failed(it)
            }
    }
}