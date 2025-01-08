package com.example.ptathome.model

/**
 *
 * A class for representing the youTube document.
 *
 * It contains data related to a collection of youtube videos.
 *
 * @author Oscar Sandberg
 *
 */
class YoutubeDocument {
    private var trainingVideoId:MutableList<CombinedWikipediaYoutubeData> = mutableListOf()
    private var rehabVideoId:MutableList<CombinedWikipediaYoutubeData>  = mutableListOf()

    fun clearTrainingVideoId(){
        this.trainingVideoId.clear()
    }

    fun clearRehabVideoId(){
        this.rehabVideoId.clear()
    }

    fun addTrainingVideoId(
        videoId: String,
        videoTitle: String,
        dimensions: IntArray,
        url: String
    ){
        this.trainingVideoId.add(CombinedWikipediaYoutubeData(Triple(videoId,videoTitle,dimensions),url))
    }

    fun addRehabVideoId(videoId: String, videoTitle: String, dimensions: IntArray, url: String){
        this.rehabVideoId.add(CombinedWikipediaYoutubeData(Triple(videoId,videoTitle,dimensions),url))
    }


    fun getTrainingVideoId():MutableList<CombinedWikipediaYoutubeData>{
        return trainingVideoId
    }

    fun getTrainingVideoIdByIndex(index:Int) = trainingVideoId[index]

    fun getRehabVideoId() = rehabVideoId

    fun getRehabVideoIdByIndex(index:Int) = rehabVideoId[index]

}