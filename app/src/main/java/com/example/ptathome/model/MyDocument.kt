package com.example.ptathome.model

/**
 *
 * Model class which contains data yielded from Wikipedia and Youtube rest services.
 *
 * @author Oscar Sandberg
 *
 */
class MyDocument {


    private var youtubeDocument:YoutubeDocument = YoutubeDocument() // Contains youtube related data

    private var wikipediaDocument: WikipediaDocument = WikipediaDocument() // Contains wikipedia related data

    /**
     * Get the raw wikipedia html response data.
     * @return html data represented as a string.
     */
    fun getRawHtml() = wikipediaDocument.getRawHtml()

    /**
     * Get the contents of the wikipedia document as a list of pairs.
     *
     * The pair contains the html text and the html tag.
     *
     * @return the html document as a mutable list of pairs.
     */
    fun getDocument() = wikipediaDocument.getDocument()

    /**
     * Set the raw html string.
     *
     * @param html, the html response.
     */
    fun setRawHtml(html:String){
        wikipediaDocument.setRawHtml(html)
    }

    /**
     *
     * Set the name of the wikipedia document
     *
     * @param name, name of the document.
     *
     */
    fun setName(name:String){
        wikipediaDocument.setName(name)
    }

    /**
     *
     * Get the name of the document
     *
     */
    fun getName() = wikipediaDocument.getName()

    fun isEmpty()= wikipediaDocument.isEmpty()

    /**
     *
     * Set the wikipedia document summary with a new summary.
     *
     * @param summary, the new summary.
     *
     */
    fun initSummary(summary:String){
        this.wikipediaDocument.initSummary(summary)
    }

    /**
     *
     * Get the wikipedia summary.
     *
     */
    fun getSummary():String{
        return wikipediaDocument.getSummary()
    }

    /**
     * Initialize a new document from a mutable list.
     *
     * @param theDocument, the new wikipedia document.
     */
    fun initNewDocument(theDocument: MutableList<Pair<String, String>>){
        wikipediaDocument.initNewDocument(theDocument)
    }

    //-------------------------------------------------------------------

    /**
     *
     * Clear the youtube documents list of training video ids
     *
     */
    fun clearTrainingVideoId(){
        this.youtubeDocument.clearTrainingVideoId()
    }

    /**
     *
     * Clear the youtube documents list of rehab video ids
     *
     */
    fun clearRehabVideoId(){
        this.youtubeDocument.clearRehabVideoId()
    }


    /**
     *
     * Add a new training video id
     *
     * @param videoId, the video id
     * @param videoTitle, the video title
     * @param dimensions, the dimensions of the video thumbnail
     * @param url, the url of the video thumbnail
     *
     */
    fun addTrainingVideoId(
        videoId: String,
        videoTitle: String,
        dimensions: IntArray,
        url: String
    ){
        this.youtubeDocument.addTrainingVideoId(videoId,videoTitle,dimensions,url)
    }

    /**
     *
     * Add a new rehab video id
     *
     * @param videoId, the video id
     * @param videoTitle, the video title
     * @param dimensions, the dimensions of the video thumbnail
     * @param url, the url of the video thumbnail
     *
     */
    fun addRehabVideoId(videoId: String, videoTitle: String, dimensions: IntArray, url: String){
        this.youtubeDocument.addRehabVideoId(videoId,videoTitle,dimensions,url)
    }


    /**
     *
     * Get training video ids
     * @return a list of video ids
     *
     */
    fun getTrainingVideoId():MutableList<CombinedWikipediaYoutubeData>{
        return youtubeDocument.getTrainingVideoId()
    }
    fun getTrainingVideoIdByIndex(index:Int) = this.youtubeDocument.getTrainingVideoIdByIndex(index)

    /**
     *
     * Get rehab video ids
     * @return a list of video ids
     *
     */
    fun getRehabVideoId() = this.youtubeDocument.getRehabVideoId()
    fun getRehabVideoIdByIndex(index:Int) = this.youtubeDocument.getRehabVideoIdByIndex(index)


}

/**
 *
 * A data class for representing the combined data from both youtube data and wikipedia data
 *
 */
data class CombinedWikipediaYoutubeData(
    var theData:Triple<String, String,IntArray> = Triple("","", intArrayOf(0,0)),
    var theImageLink:String = ""
)