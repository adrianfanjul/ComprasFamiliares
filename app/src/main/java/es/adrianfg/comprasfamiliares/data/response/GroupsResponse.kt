package es.adrianfg.comprasfamiliares.data.response

import com.google.gson.annotations.SerializedName


data class GroupsResponse(
    @SerializedName("results")
    val results: List<GroupResponseItem?> ? = null
)