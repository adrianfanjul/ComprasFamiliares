package es.adrianfg.comprasfamiliares.data.response

import com.google.gson.annotations.SerializedName


data class UsersResponse(
    @SerializedName("results")
    val results: List<UserResponseItem?> ? = null
)