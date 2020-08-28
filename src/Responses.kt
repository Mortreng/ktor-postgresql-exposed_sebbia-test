package com.sebbia

data class ListResponse<T>(
    val code: Int,
    val list: List<T>?
)

data class ObjectResponse<T>(
    val code: Int,
    val `object`: T?
)

data class StatusResponse(
    val code: Int,
    val message:String
)

