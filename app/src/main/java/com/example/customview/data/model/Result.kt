package com.example.customview.data.model

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("results") val barList: List<Bar>
)
