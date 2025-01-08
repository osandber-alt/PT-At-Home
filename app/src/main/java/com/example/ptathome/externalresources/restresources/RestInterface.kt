package com.example.ptathome.externalresources.restresources

import com.example.ptathome.externalresources.network.NetworkManager
import com.example.ptathome.model.MyDocument
import kotlinx.coroutines.flow.MutableStateFlow

/**
 *
 * Rest interface which describes what a rest service implementation can do.
 *
 * @author Oscar Sandberg
 *
 */
abstract class RestInterface {

    abstract fun <T:Any> runRestService(
        networkManager: NetworkManager,
        receiver: T,
        _isComplete: MutableStateFlow<Boolean>,
        bodyPartSearch: String
    )
    abstract fun getBaseUrl():String
    abstract fun getApiKey():String
    abstract fun getTopic():List<String>
    abstract fun getTitle():String
    abstract fun setTitle(value:String)

}