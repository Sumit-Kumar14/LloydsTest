package com.lloydstest.network.core

abstract class BaseResponse {
    abstract val status: String
    abstract val message: Any
}