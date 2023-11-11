package com.example.subscribble.database.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subscribble.repository.Repository
import com.example.subscribble.database.CardList
import com.example.subscribble.database.SubsList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscriptionViewModel @Inject constructor(private val repository: Repository):ViewModel() {

    private val _card = MutableStateFlow(emptyList<CardList>())
    val cards : StateFlow<List<CardList>> = _card

    private val _sub = MutableStateFlow(emptyList<SubsList>())
    val subs : StateFlow<List<SubsList>> = _sub

    init {
        viewModelScope.launch {
            _card.emit(repository.getAllCards())
            _sub.emit(repository.getAllSubs())
        }
    }

    fun insertCard(cardList: CardList){
        viewModelScope.launch {
            repository.insertCard(cardList)
            _card.emit(repository.getAllCards())
        }
    }

    fun getCardById(id: Int): CardList?{
        return repository.getCardById(id)
    }

    fun insertSub(subsList: SubsList){
        viewModelScope.launch {
            repository.insertSubscription(subsList)
            _sub.emit(repository.getAllSubs())
        }
    }

    fun deleteSubscription(subsList: SubsList){
        viewModelScope.launch {
            repository.deleteSubscription(subsList)
            _sub.emit(repository.getAllSubs())
        }
    }

    fun loadCards(){
        viewModelScope.launch {
            _card.value = repository.getAllCards()
        }
    }

    fun loadSubs(){
        viewModelScope.launch {
            _sub.value = repository.getAllSubs()
        }
    }

    fun updateSubscription(subsList: SubsList){
        viewModelScope.launch {
            repository.updateSubscription(subsList)
        }
    }

    fun sumPriceByCategory(category: String): Float {
        return repository.sumPriceByCategory(category)
    }

    fun getSubscriptionById(id: Int): SubsList? {
        return repository.getSubscriptionById(id)
    }

    fun getSubCardByName(name: String): SubsList? {
        return repository.getSubCardByName(name)
    }

    fun getSubscriptionByCategory(category: String): SubsList?{
        return repository.getSubscriptionByCategory(category)
    }

}


