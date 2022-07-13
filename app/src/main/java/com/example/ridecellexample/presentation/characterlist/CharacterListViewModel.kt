package com.example.ridecellexample.presentation.characterlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ridecellexample.data.CharacterListRepository
import com.example.ridecellexample.data.model.CharacterData
import com.example.ridecellexample.data.model.Characters
import com.example.ridecellexample.data.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.zip.Checksum
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(private val characterListRepository: CharacterListRepository) :
    ViewModel() {

    private val characterMutableLiveData: MutableLiveData<Resource<List<CharacterData>>> =
        MutableLiveData()
    val characters: LiveData<Resource<List<CharacterData>>> = characterMutableLiveData

    fun getSearchedImage(searchQuery: String, page: Int) = viewModelScope.launch(Dispatchers.IO) {
        characterMutableLiveData.postValue(Resource.Loading())

        try {
            val apiResult = characterListRepository.getSearchedImage(searchQuery, page)
            characterMutableLiveData.postValue(apiResult)
        } catch (e: Exception) {
            characterMutableLiveData.postValue(Resource.Error(e.message.toString()))
        }
    }
}
