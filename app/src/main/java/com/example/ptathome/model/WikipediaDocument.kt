package com.example.ptathome.model

class WikipediaDocument {

    private var theRawHtml:String =""
    private var theDocument: MutableList<Pair<String,String>> = mutableListOf()
    private var theSummary: String = ""
    private var documentName:String = ""

    fun getRawHtml() = theRawHtml
    fun setRawHtml(html:String){
        theRawHtml = html
    }

    fun getDocument() = theDocument

    fun setName(name:String){
        documentName = name
    }
    fun getName() = documentName

    fun initNewDocument(theDocument: MutableList<Pair<String, String>>){
        this.theDocument.clear()
        this.theDocument = mutableListOf()
        for(i in theDocument) this.theDocument.add(Pair(i.first, i.second))
    }
    fun isEmpty()= theDocument.isEmpty()

    fun initSummary(summary:String){
        this.theSummary = summary
    }
    fun getSummary():String{
        return if(theSummary.isEmpty()) "No Summary here"
        else this.theSummary
    }


    /*fun getAllTags():MutableList<String>{
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
}*/

}