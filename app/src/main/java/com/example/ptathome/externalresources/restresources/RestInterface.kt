package com.example.ptathome.externalresources.restresources

import com.example.ptathome.externalresources.network.NetworkManager

abstract class RestInterface {

    abstract fun runRestService(networkManager: NetworkManager)
    abstract fun getBaseUrl():String
    abstract fun getApiKey():String
    abstract fun getTopic():String
    abstract fun getTitle():String

}