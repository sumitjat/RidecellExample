package com.example.ridecellexample.data

import com.example.ridecellexample.data.db.CharacterDao
import com.example.ridecellexample.data.model.CharacterData
import com.example.ridecellexample.data.model.Characters
import com.example.ridecellexample.data.util.NetworkHelper
import com.example.ridecellexample.data.util.Resource
import retrofit2.Response
import javax.inject.Inject

class CharacterListRepository @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val apiService: ApiService,
    private val characterDao: CharacterDao
) {

    private suspend fun fetchData(): Resource<List<CharacterData>> =
        Resource.Success(characterDao.getAllCharacter())

    private suspend fun searchData(searchQuery: String): Resource<List<CharacterData>> =
        Resource.Success(characterDao.searchCharacter("%$searchQuery%"))

    suspend fun getSearchedImage(
        searchQuery: String,
        page: Int,
    ): Resource<List<CharacterData>> {
        return if (networkHelper.isOnline()) {
            if (searchQuery.isEmpty())
                responseToResource(
                    apiService.getCharacters(page)
                )
            else {
                responseToResource(apiService.filterCharacters(searchQuery))
            }
        } else {
            if (searchQuery.isEmpty()) fetchData()
            else searchData(searchQuery)
        }
    }

    private fun responseToResource(response: Response<Characters>): Resource<List<CharacterData>> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                characterDao.insertCharacters(*result.results.toTypedArray())
                return Resource.Success(result.results)
            }
        }

        return Resource.Error(response.message().toString())
    }
}