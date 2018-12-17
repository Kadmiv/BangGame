package com.kadmiv.game.model

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.google.gson.Gson
import com.kadmiv.game.model.repos.Repo
import com.kadmiv.game.model.repos.RepoFile

class RuntimeRepo() {

    companion object {
        val audioRepo = HashMap<String, Sound>()
        val textureRepo = HashMap<String, Texture>()

        fun getAnimation(sectorWidth: Int, sectorHeight: Int, textureName: String): Animation<TextureRegion> {
            var loader = AnimationLoader(sectorWidth, sectorHeight)
            return loader.parseAnimation(textureRepo[textureName])
        }

        fun getAnimation(actor: Actor, textureName: String): Animation<TextureRegion> {
            var loader = AnimationLoader(actor.width.toInt(), actor.height.toInt())
            var animation = loader.parseAnimation(textureRepo[textureName])
            return animation
        }
    }

    init {
        // Load json file, which contain all information for game assets files
        var collectedRepo: Repo = loadFromFile()
        // Load files to memory
        loadAudio(collectedRepo.audioRepo, audioRepo)
        loadTextures(collectedRepo.textureRepo, textureRepo)
    }

    private fun loadFromFile(): Repo {
        var jsonFile = Gdx.files.internal("repo.json")
        var jsonString = jsonFile.readString()
        var gson = Gson()
        var repo = gson.fromJson<Repo>(jsonString, Repo::class.java)
        return repo
    }

    private fun loadAudio(audioRepo: HashMap<String, RepoFile>, runtimeAudioRepo: HashMap<String, Sound>) {
        for (file in audioRepo.values) {
            var sound = Gdx.audio.newSound(Gdx.files.internal(file.path));
            runtimeAudioRepo.put(file.name, sound)
        }
    }

    private fun loadTextures(textureRepo: HashMap<String, RepoFile>, runtimeTextureRepo: HashMap<String, Texture>) {
        for (file in textureRepo.values) {
            var texture = Texture(file.path)
            runtimeTextureRepo.put(file.name, texture)
        }
    }


}