package com.example.ptathome.model

import com.example.ptathome.ui.viewmodel.combinedData

class YoutubeDocument {
    private var trainingVideoId:MutableList<combinedData> = mutableListOf()
    private var rehabVideoId:MutableList<combinedData>  = mutableListOf()

    fun clearTrainingVideoId(){
        this.trainingVideoId.clear()
    }

    fun clearRehabVideoId(){
        this.rehabVideoId.clear()
    }

    fun modifyTrainingVideoId(
        videoId: String,
        videoTitle: String,
        dimensions: IntArray,
        url: String
    ){
        this.trainingVideoId.add(combinedData(Triple(videoId,videoTitle,dimensions),url))
    }

    fun modifyRehabVideoId(videoId: String, videoTitle: String, dimensions: IntArray, url: String){
        this.rehabVideoId.add(combinedData(Triple(videoId,videoTitle,dimensions),url))
    }


    fun getTrainingVideoId():MutableList<combinedData>{
        return trainingVideoId
    }
    fun getTrainingVideoIdByIndex(index:Int) = trainingVideoId[index]

    fun getRehabVideoId() = rehabVideoId
    fun getrehabVideoIdByIndex(index:Int) = rehabVideoId[index]

}