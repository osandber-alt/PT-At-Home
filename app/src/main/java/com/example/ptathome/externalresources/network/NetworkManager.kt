package com.example.ptathome.externalresources.network

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.ptathome.externalresources.RetrofitHandler
import com.example.ptathome.externalresources.gson.GsonManager
import com.example.ptathome.model.MyDocument
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/**
 *
 * Class used for dealing with network related topics.
 *
 * @author Oscar Sandberg
 */

class NetworkManager @Inject constructor(
    private var application: Application,
    private var gsonManager: GsonManager,
) {

    /**
     *
     * Check if app is connected to the internet
     *
     * @return true if there is a network connection
     *
     */
    fun isOnline():Boolean{
        val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val network = connectivityManager?.activeNetwork
        val capabilities = connectivityManager?.getNetworkCapabilities(network)
        if(capabilities != null &&
            (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))){
            println("network")
            return true
        }
        else{
            println("no network")
            return false
        }
    }

    /**
     * Run network service, or network rest service in this case.
     * @param serviceName, The name of the network service
     * @param baseURL, The base url of the network service
     * @param apiKey, The api key of the network service
     * @param topic, The topics used to search a body part with the network service
     * @param title, The title of the current body part to search with the network service
     * @param receiver, A receiver which will store the result of the network service, which is in this case represented as the class MyDocument
     * @param _isComplete, A mutable stateflow to indicate that the current network service has been completed
     */
    fun <T:Any>runNetworkService(
        serviceName: String,
        baseURL: String,
        apiKey: String,
        topic: List<String>,
        title: String,
        receiver: T,
        _isComplete: MutableStateFlow<Boolean>
    ) {
        println("Is running network service")
        val job = RetrofitHandler.runService(serviceName,baseURL,apiKey,topic,title,receiver,_isComplete)
    }

}