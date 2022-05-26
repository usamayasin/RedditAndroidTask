package com.app.reddittask.data.usecase

import com.app.reddittask.data.repository.Repository
import javax.inject.Inject

class GetCharactersUsecase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(
        pageNo: Int,
    ) = repository.getCharacters(page = pageNo)

}
