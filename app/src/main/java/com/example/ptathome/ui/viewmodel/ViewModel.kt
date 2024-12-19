package com.example.ptathome.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ptathome.externalresources.network.NetworkManager
import com.example.ptathome.externalresources.restresources.RestInterface
import com.example.ptathome.externalresources.restresources.wikipedia
import com.example.ptathome.externalresources.restresources.youtube
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    @youtube private val youtubeRestService: RestInterface,
    @wikipedia private val wikipediaGenericRestService: RestInterface,
    private val networkManager: NetworkManager
): ViewModel() {

    fun startService(){
        val job = GlobalScope.launch{
            //launch{ youtubeRestService.runRestService(networkManager)}
            launch{ wikipediaGenericRestService.runRestService(networkManager)}
        }

        //if(networkManager.isOnline()){
        //    val backgroundJob = GlobalScope.launch(Dispatchers.Default){
        //        networkManager.runNetworkService()
        //    }
        //}

    }

}