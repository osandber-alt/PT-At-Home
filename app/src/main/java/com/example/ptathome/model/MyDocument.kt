package com.example.ptathome.model

class MyDocument {

    //TODO: Temporary video detail, move to a new class

    private var trainingVideoId:MutableList<String> = mutableListOf()
    private var rehabVideoId:MutableList<String> = mutableListOf()

    private var documentName:String = ""

    fun setName(name:String){
        documentName = name
    }

    fun getName() = documentName

    fun clearTrainingVideoId(){
        this.trainingVideoId.clear()
    }

    fun clearRehabVideoId(){
        this.rehabVideoId.clear()
    }

    fun modifyTrainingVideoId(videoId:String){
        this.trainingVideoId.add(videoId)
    }

    fun modifyRehabVideoId(videoId:String){
        this.rehabVideoId.add(videoId)
    }


    fun getTrainingVideoId() = trainingVideoId
    fun getTrainingVideoIdByIndex(index:Int) = trainingVideoId[index]

    fun getRehabVideoId() = rehabVideoId
    fun getrehabVideoIdByIndex(index:Int) = rehabVideoId[index]

    // Pair(string,string) = Pair(The text, The tags)
    private var theDocument: MutableList<Pair<String,String>> = mutableListOf()
    private var theSummary: String = ""

    fun isEmpty()= theDocument.isEmpty()

    fun initSummary(summary:String){
        this.theSummary = summary
    }

    fun getSummary():String{
        return if(theSummary.isEmpty()) "No Summary here"
        else this.theSummary
    }

    fun initNewDocument(theDocument: MutableList<Pair<String, String>>){
        this.theDocument.clear()
        this.theDocument = mutableListOf()
        for(i in theDocument) this.theDocument.add(Pair(i.first, i.second))
    }

    fun getAllTags():MutableList<String>{
        val localList = mutableListOf<String>()

        for(i in theDocument) localList.add(i.second)

        return localList
    }

    fun getAllSections(section:String):MutableList<String>{
        val localList = mutableListOf<String>()

        for(i in theDocument){
            if(i.second == section) localList.add(i.first)
        }

        return localList
    }

    fun getAllBySection(section:String): MutableList<String> {
        var theState = false
        val theList = mutableListOf<String>()
        for(i in this.theDocument){
            if(i.second == "section" && i.first == section){
                theState = true
            }

            else if(i.second == "section" && i.first != section){
                theState = false
            }

            if(theState){
                theList.add(i.first)
            }

        }

        return theList
    }

    fun getAllHeadingBySection(section: String,heading:String): MutableList<String> {
        var theState = false
        val theList = mutableListOf<String>()
        for(i in this.theDocument){
            if(i.second == "section" && i.first == section){
                theState = true
            }

            else if(i.second == "section" && i.first != section){
                theState = false
            }

            if(theState && i.second == heading ){
                theList.add(i.first)
            }

        }

        return theList
    }

    fun getAllTextBySection(section: String,headingName:String):MutableList<String>{
        var theState = false
        var theHeadingState = false
        val theList = mutableListOf<String>()
        for(i in this.theDocument){
            if(i.second == "section" && i.first == section){
                theState = true
            }

            else if(i.second == "section" && i.first != section){
                theState = false
            }

            if(i.first == headingName && theState){
                theHeadingState = true
            }

            else if (i.first == headingName && theState){
                theHeadingState = false
            }

            if(theState &&  theHeadingState && i.first != headingName ){
                theList.add(i.first)
            }

        }

        return theList
    }

    fun getUnformatedDocumentContents():MutableList<String>{
        val localList = mutableListOf<String>()
        for(i in theDocument){
            localList.add(i.first)
        }
        return localList
    }

}