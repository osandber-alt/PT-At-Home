package com.example.ptathome.externalresources.restresources

import com.example.ptathome.externalresources.network.NetworkManager

abstract class GenericRestService(
    private val baseURL: String,
    private val apiKey: String,
    private val topic:String,
    private val title:String
) : RestInterface() {
    override fun runRestService(networkManager: NetworkManager) {
        println("Run service")
        println("Base url = $baseURL")
        println("API key = $apiKey")
        println("Topic = $topic")
        println("Title = $title")

        if(networkManager.isOnline()){
            networkManager.runNetworkService(baseURL, apiKey, topic,title)
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

    override fun getTopic(): String {
        return topic
    }
}