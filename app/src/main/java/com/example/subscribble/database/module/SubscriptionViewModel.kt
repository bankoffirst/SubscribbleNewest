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

    fun insertSub(subsList: SubsList){
        viewModelScope.launch {
            repository.insertSubscription(subsList)
            _sub.emit(repository.getAllSubs())
        }
    }

    fun deleteSub(id: Int){
        viewModelScope.launch {
            repository.deleteSubscription(id)
        }
    }

    fun sumPriceByCategory(category: String): Int {
        return repository.sumPriceByCategory(category)
    }

}


