package com.dicoding.calocare.data.remote.response

import com.google.gson.annotations.SerializedName

data class DeleteRequest(
    @SerializedName("name") val name: String
) {
    init {
        require(name.isNotBlank()) { "Nama makanan tidak boleh kosong" }
    }
}