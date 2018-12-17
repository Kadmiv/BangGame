package com.kadmiv.game.model.repos

import com.google.gson.annotations.SerializedName

data class Repo(
        @SerializedName("audio_repo")
        var audioRepo: HashMap<String, RepoFile>,
        @SerializedName("texture_repo")
        var textureRepo: HashMap<String, RepoFile>
)