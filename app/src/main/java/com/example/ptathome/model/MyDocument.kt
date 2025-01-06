package com.example.ptathome.model

import com.example.ptathome.ui.viewmodel.combinedData

class MyDocument {

    //TODO: Temporary video detail, move to a new class

    private var youtubeDocument:YoutubeDocument = YoutubeDocument()
    private var wikipediaDocument: WikipediaDocument = WikipediaDocument()

    fun getRawHtml() = wikipediaDocument.getRawHtml()

    fun getDocument() = wikipediaDocument.getDocument()

    fun setRawHtml(html:String){
        wikipediaDocument.setRawHtml(html)
    }

    fun setName(name:String){
        wikipediaDocument.setName(name)
    }

    fun getName() = wikipediaDocument.getName()

    fun isEmpty()= wikipediaDocument.isEmpty()

    fun initSummary(summary:String){
        this.wikipediaDocument.initSummary(summary)
    }

    fun getSummary():String{
        return wikipediaDocument.getSummary()
    }

    fun initNewDocument(theDocument: MutableList<Pair<String, String>>){
        wikipediaDocument.initNewDocument(theDocument)
    }

    //-------------------------------------------------------------------

    fun clearTrainingVideoId(){
        this.youtubeDocument.clearTrainingVideoId()
    }

    fun clearRehabVideoId(){
        this.youtubeDocument.clearRehabVideoId()
    }

    fun modifyTrainingVideoId(
        videoId: String,
        videoTitle: String,
        dimensions: IntArray,
        url: String
    ){
        this.youtubeDocument.modifyTrainingVideoId(videoId,videoTitle,dimensions,url)
    }

    fun modifyRehabVideoId(videoId: String, videoTitle: String, dimensions: IntArray, url: String){
        this.youtubeDocument.modifyRehabVideoId(videoId,videoTitle,dimensions,url)
    }


    fun getTrainingVideoId():MutableList<combinedData>{
        return youtubeDocument.getTrainingVideoId()
    }
    fun getTrainingVideoIdByIndex(index:Int) = this.youtubeDocument.getTrainingVideoIdByIndex(index)

    fun getRehabVideoId() = this.youtubeDocument.getRehabVideoId()
    fun getrehabVideoIdByIndex(index:Int) = this.youtubeDocument.getrehabVideoIdByIndex(index)


}