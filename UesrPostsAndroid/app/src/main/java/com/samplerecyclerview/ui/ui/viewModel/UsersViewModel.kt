package com.samplerecyclerview.ui.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samplerecyclerview.ui.data.repository.UsersRepository
import com.samplerecyclerview.ui.ui.model.UsersModel
import com.samplerecyclerview.ui.utils.Resource
import com.samplerecyclerview.ui.utils.ResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    val repository: UsersRepository) : ViewModel() {

    private val _userResponse = MutableLiveData<Resource<ArrayList<UsersModel>?>?>()
    val userResponse: LiveData<Resource<ArrayList<UsersModel>?>?> get() = _userResponse

    fun fetchPosts() {
        viewModelScope.launch {
            try {
                _userResponse.postValue(
                    ResponseHandler.success(
                        repository.getUsers()
                    )
                )
            } catch (e: Exception) {
                _userResponse.postValue(ResponseHandler.failure(e))
            }
        }
    }

}