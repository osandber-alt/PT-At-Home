package com.example.ptathome.externalresources.gson

import com.google.gson.Gson
import javax.inject.Inject


class GsonManager @Inject
constructor()
{

    //private val x = "{\"type\":\"standard\",\"title\":\"Lord\",\"displaytitle\":\"<span class=\\\"mw-page-title-main\\\">Lord</span>\",\"namespace\":{\"id\":0,\"text\":\"\"},\"wikibase_item\":\"Q12552047\",\"titles\":{\"canonical\":\"Lord\",\"normalized\":\"Lord\",\"display\":\"<span class=\\\"mw-page-title-main\\\">Lord</span>\"},\"pageid\":69304,\"lang\":\"en\",\"dir\":\"ltr\",\"revision\":\"1257744892\",\"tid\":\"ea10aa20-a415-11ef-9859-e30a82ba4f62\",\"timestamp\":\"2024-11-16T12:25:50Z\",\"description\":\"Title for a person or deity\",\"description_source\":\"local\",\"content_urls\":{\"desktop\":{\"page\":\"https://en.wikipedia.org/wiki/Lord\",\"revisions\":\"https://en.wikipedia.org/wiki/Lord?action=history\",\"edit\":\"https://en.wikipedia.org/wiki/Lord?action=edit\",\"talk\":\"https://en.wikipedia.org/wiki/Talk:Lord\"},\"mobile\":{\"page\":\"https://en.m.wikipedia.org/wiki/Lord\",\"revisions\":\"https://en.m.wikipedia.org/wiki/Special:History/Lord\",\"edit\":\"https://en.m.wikipedia.org/wiki/Lord?action=edit\",\"talk\":\"https://en.m.wikipedia.org/wiki/Talk:Lord\"}},\"extract\":\"Lord is an appellation for a person or deity who has authority, control, or power over others, acting as a master, chief, or ruler. The appellation can also denote certain persons who hold a title of the peerage in the United Kingdom, or are entitled to courtesy titles. The collective \\\"Lords\\\" can refer to a group or body of peers.\",\"extract_html\":\"<p><b>Lord</b> is an appellation for a person or deity who has authority, control, or power over others, acting as a master, chief, or ruler. The appellation can also denote certain persons who hold a title of the peerage in the United Kingdom, or are entitled to courtesy titles. The collective \\\"Lords\\\" can refer to a group or body of peers.</p>\"}"

    val jsonData = """
            {
                "name": "John Doe",
                "age": 30,
                "email": "john.doe@example.com",
                "address": {
                    "street": "123 Main St",
                    "city": "Anytown",
                    "state": "CA"
                },
                "interests": ["reading", "hiking", "coding"]
            }"""

    val summaryJsonData = """
       {
    "type": "standard",
    "title": "Lord",
    "displaytitle": "<span class=\"mw-page-title-main\">Lord</span>",
    "namespace": {
        "id": 0,
        "text": ""
    },
    "wikibase_item": "Q12552047",
    "titles": {
        "canonical": "Lord",
        "normalized": "Lord",
        "display": "<span class=\"mw-page-title-main\">Lord</span>"
    },
    "pageid": 69304,
    "lang": "en",
    "dir": "ltr",
    "revision": "1257744892",
    "tid": "ea10aa20-a415-11ef-9859-e30a82ba4f62",
    "timestamp": "2024-11-16T12:25:50Z",
    "description": "Title for a person or deity",
    "description_source": "local",
    "content_urls": {
        "desktop": {
            "page": "https://en.wikipedia.org/wiki/Lord",
            "revisions": "https://en.wikipedia.org/wiki/Lord?action=history",
            "edit": "https://en.wikipedia.org/wiki/Lord?action=edit",
            "talk": "https://en.wikipedia.org/wiki/Talk:Lord"
        },
        "mobile": {
            "page": "https://en.m.wikipedia.org/wiki/Lord",
            "revisions": "https://en.m.wikipedia.org/wiki/Special:History/Lord",
            "edit": "https://en.m.wikipedia.org/wiki/Lord?action=edit",
            "talk": "https://en.m.wikipedia.org/wiki/Talk:Lord"
        }
    },
    "extract": "Lord is an appellation for a person or deity who has authority, control, or power over others, acting as a master, chief, or ruler. The appellation can also denote certain persons who hold a title of the peerage in the United Kingdom, or are entitled to courtesy titles. The collective \"Lords\" can refer to a group or body of peers.",
    "extract_html": "<p><b>Lord</b> is an appellation for a person or deity who has authority, control, or power over others, acting as a master, chief, or ruler. The appellation can also denote certain persons who hold a title of the peerage in the United Kingdom, or are entitled to courtesy titles. The collective \"Lords\" can refer to a group or body of peers.</p>"
} 
    """

    private val gson:Gson = Gson()
    fun getGson() = gson

    data class xGon(var extract:String)

    fun printCurrentJsonData() {
        val y = gson.fromJson(summaryJsonData,xGon::class.java)
        println(y.extract)
    }

    //TODO: Modify
    /*fun toJson(weathersState: WeathersState):String{
        return gson!!.toJson(weathersState, WeathersState::class.java)
    }

    fun fromJson(weathersState: String): WeathersState {
        return gson!!.fromJson(weathersState, WeathersState::class.java)
    }*/


}