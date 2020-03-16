package com.carrati.chucknorrisfacts.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import com.carrati.chucknorrisfacts.domain.entities.Fact
import com.carrati.chucknorrisfacts.domain.usecases.GetRandomFactUseCase
import com.carrati.chucknorrisfacts.domain.usecases.ListFactsUseCase
import com.carrati.chucknorrisfacts.presentation.extension.StateMachineSingle
import com.carrati.chucknorrisfacts.presentation.extension.ViewState
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.plusAssign

class MainViewModel (
    private val listFactsUC: ListFactsUseCase,
    private val getRandomFactUC: GetRandomFactUseCase,
    private val uiScheduler: Scheduler
): BaseViewModel() {

    val stateListFacts = MutableLiveData<ViewState<List<Fact>>>()

    val stateGetRandonFact = MutableLiveData<ViewState<Fact>>()

    fun listFacts() {
        disposables += listFactsUC.execute()
            .observeOn(uiScheduler)
            .compose(StateMachineSingle())
            .subscribe(
                {
                    stateListFacts.postValue(it)
                },
                {
                }
            )
    }

    fun onTryAgainRequired() {
        listFacts()
    }

    fun getRandomFact(){
        disposables += getRandomFactUC.execute()
            .observeOn(uiScheduler)
            .compose(StateMachineSingle())
            .subscribe(
                {
                    stateGetRandonFact.postValue(it)
                },
                {
                }
            )
    }

}