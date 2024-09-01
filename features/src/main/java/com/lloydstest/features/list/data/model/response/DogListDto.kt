package com.lloydstest.features.list.data.model.response

import androidx.annotation.Keep
import com.lloydstest.features.list.domain.model.DogList
import com.lloydstest.network.core.BaseResponse

@Keep
data class DogListDto(
    override val status: String,
    override val message: List<String>
) : BaseResponse()

fun DogListDto.toDogsDomain(): DogList {
    if (status != "success") {
        return DogList(
            dogImages = emptyList()
        )
    }

    return DogList(
        dogImages = message
    )
}