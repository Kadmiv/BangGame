package com.kadmiv.game.model.repos

import com.google.gson.annotations.SerializedName

data class RepoFile(
        @SerializedName("name")
        var name: String,
        @SerializedName("path")
        var path: String
)