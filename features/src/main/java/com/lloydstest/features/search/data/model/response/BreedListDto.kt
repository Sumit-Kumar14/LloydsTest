package com.lloydstest.features.search.data.model.response

import androidx.annotation.Keep
import com.lloydstest.features.search.domain.model.BreedList
import com.lloydstest.network.core.BaseResponse

@Keep
data class BreedListDto(
    override val status: String,
    override val message: Map<String, List<String>>
) : BaseResponse()

fun BreedListDto.toBreedDomain(): BreedList {
    if (status != "success") {
        return BreedList(
            breedList = emptyList()
        )
    }

    val breedList = mutableListOf<String>()
    message.forEach { breed ->
        if (breed.value.isEmpty()) {
            breedList.add(breed.key)
        } else {
            breed.value.forEach {
                breedList.add("${breed.key}/$it")
            }
        }
    }
    return BreedList(
        breedList = breedList
    )
}