package com.kadmiv.game.model.repos

import com.google.gson.Gson
import java.io.File


class RepoCollector {

    val MUSIC_TYPES = listOf<String>(".mp3", ".wav")
    val IMAGE_TYPES = listOf<String>(".jpeg", ".jpg", ".png")

    fun scanAllFiles(mainPath: String) {
        var repo = Repo(HashMap<String, RepoFile>(), HashMap<String, RepoFile>())
        val runnable = {
            val folderPath: String
            folderPath = File(mainPath).getParent()
            val files = getAllFilesInDir(folderPath, null)

            var cuttePath = mainPath.replace("sounds", "")
            for (repoFile in files) {
                var file = File(repoFile)
                for (type in IMAGE_TYPES) {
                    if (file.name.contains(type)) {
                        var repoFile = RepoFile(file.name.replace(type, ""), file.path.replace(cuttePath, ""))
                        if (repoFile.name != "")
                            repo.textureRepo.put(repoFile.name, repoFile)
                    }
                }
                for (type in MUSIC_TYPES) {
                    if (file.name.contains(type)) {
                        var repoFile = RepoFile(file.name.replace(type, ""), file.path.replace(cuttePath, ""))
                        if (repoFile.name != "")
                            repo.audioRepo.put(repoFile.name, repoFile)
                    }
                }
            }

            var jsonFile = File("repo.json")
            var gson = Gson()
            var jsonStr = gson.toJson(repo)
            var bw = jsonFile.bufferedWriter()
            bw.write(jsonStr)
            bw.close()
            println("Done")
        }
        val thread = Thread(null, runnable, "some name", 1000000)
        thread.start()
    }

    fun getAllFilesInDir(path: String, pathList: ArrayList<String>?): ArrayList<String> {
        var pathList = pathList

        if (pathList == null) {
            pathList = ArrayList()
        }

        val fList: Array<File>?
        val folder = File(path)
        fList = folder.listFiles()

        try {

            for (i in fList!!.indices) {
                val part = fList[i]
                if (part.isFile) {
                    val filePath = part.absolutePath
                    pathList.add(filePath)
                    println(filePath)
                }

                if (part.isDirectory) {
//                    println(String.format("%s\\", part.path))
                    try {
                        getAllFilesInDir(part.path, pathList)
                    } catch (ex: Exception) {
                        ex.stackTrace
                        println("Error " + part.path)
                    }
                }
            }
        } catch (ex: Exception) {
            ex.stackTrace
        }

        return pathList
    }
}