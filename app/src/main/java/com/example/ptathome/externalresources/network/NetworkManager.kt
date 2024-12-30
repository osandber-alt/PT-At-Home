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

class NetworkManager @Inject constructor(
    private var application: Application,
    private var gsonManager: GsonManager,
) {

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

    // TODO: Insert specific RestService Here!!!
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
        gsonManager.printCurrentJsonData()
        val job = RetrofitHandler.runService(serviceName,baseURL,apiKey,topic,title,receiver,_isComplete)
    }

}