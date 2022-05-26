package com.app.reddittask.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.reddittask.data.model.CharactersResponse
import com.app.reddittask.data.usecase.GetCharactersUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.reddittask.data.remote.DataState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCharactersUsecase: GetCharactersUsecase
) : ViewModel() {

    private val _uiState = MutableLiveData<UIState>()
    val uiStateLiveData: LiveData<UIState> = _uiState

    private val _characters = MutableLiveData<CharactersResponse>()
    val charactersResponseLiveData: LiveData<CharactersResponse> = _characters

    private var page:Int = 1

    init {
        getCharactersFromServer(page = 1)
    }


    fun getCharactersFromServer(page: Int) {
        _uiState.postValue(LoadingState)
        viewModelScope.launch(Dispatchers.IO) {
            getCharactersUsecase.invoke(pageNo = page).collect { dataState ->
                withContext(Dispatchers.Main) {
                    when (dataState) {
                        is DataState.Success -> {
                            _uiState.postValue(ContentState)
                            dataState.data?.let {
                                _characters.postValue(it)
                            } ?: run {
                                _uiState.postValue(EmptyState)
                            }
                        }
                        is DataState.Error -> {
                            _uiState.postValue(
                                ErrorState(
                                    dataState.message ?: "Something went wrong!!"
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    fun retry() {
        getCharactersFromServer(page = 1)
    }

    fun loadMore() {
        page += 1
        getCharactersFromServer(page)
    }
}
