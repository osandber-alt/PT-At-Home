package com.example.ptathome.externalresources.restresources

import com.example.ptathome.externalresources.network.NetworkManager
import com.example.ptathome.model.MyDocument
import kotlinx.coroutines.flow.MutableStateFlow

/**
 *
 * A class for representing a generic rest service.
 *
 * The rest services which have been implemented are the following:
 *      - WikipediaRestService
 *      - YoutubeRestService
 *
 * @author Oscar Sandberg
 *
 */
abstract class GenericRestService(
    private val baseURL: String,
    private val apiKey: String,
    private val topic:List<String>,
    private var title:String,
) : RestInterface() {

    /**
     *
     * Run specific rest service
     *
     */
    override fun <T:Any>runRestService(
        networkManager: NetworkManager,
        receiver: T,
        _isComplete: MutableStateFlow<Boolean>,
        bodyPartSearch: String
    ) {
        println("Run service")
        //println("Base url = $baseURL")
        //println("API key = $apiKey")
        //println("Topic = $topic")
        //println("Title = $title")

        if(networkManager.isOnline()){
            var theLocal = this.javaClass.name
            theLocal = theLocal.replace("com.example.ptathome.externalresources.restresources.","")
            //println("The service name: ${theLocal}")
            setTitle(bodyPartSearch)
            networkManager.runNetworkService(theLocal,baseURL, apiKey, topic,title,receiver,_isComplete)
        }

    }

    override fun getApiKey(): String {
        return apiKey
    }

    override fun getBaseUrl(): String {
        return baseURL
    }

    override fun getTitle(): String {
        return title
    }

    override fun getTopic(): List<String> {
        return topic
    }

    override fun setTitle(value: String) {
        title = value
    }
}