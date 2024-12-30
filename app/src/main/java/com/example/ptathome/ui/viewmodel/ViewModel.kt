package com.example.ptathome.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ptathome.model.MyDocument
import com.example.ptathome.externalresources.network.NetworkManager
import com.example.ptathome.externalresources.restresources.RestInterface
import com.example.ptathome.externalresources.restresources.TypeOfService
import com.example.ptathome.externalresources.restresources.wikipedia
import com.example.ptathome.externalresources.restresources.youtube
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface PtHomeViewModelInterface{
    val currentDocument: StateFlow<MyDocument>
    val isComplete:StateFlow<Boolean>
    val isComplete2:StateFlow<Boolean>
    val lastService:StateFlow<TypeOfService>

    fun startService(aList: Set<TypeOfService>, bodyPartSearch: String):Boolean
    fun isServiceComplete():Boolean
    fun setDocumentsByIndex(name: String,index:Int)
}

@HiltViewModel
class ViewModel @Inject constructor(
    @youtube private val youtubeRestService: RestInterface,
    @wikipedia private val wikipediaGenericRestService: RestInterface,
    private val networkManager: NetworkManager
): ViewModel(),PtHomeViewModelInterface {

    private val _currentDocument = MutableStateFlow(MyDocument())
    override val currentDocument: StateFlow<MyDocument>
        get() = _currentDocument.asStateFlow()

    private var currentTime:Long = -1L

    private var _isComplete = MutableStateFlow(false)
    override val isComplete: StateFlow<Boolean>
        get() = _isComplete.asStateFlow()

    private var _isComplete2 = MutableStateFlow(false)
    override val isComplete2: StateFlow<Boolean>
        get() = _isComplete2.asStateFlow()

    private var _lastService = MutableStateFlow(TypeOfService.Nothing)
    override val lastService: StateFlow<TypeOfService>
        get() = _lastService.asStateFlow()

    private var listOfMyDocument:MutableList<MyDocument> = mutableListOf()

    override fun setDocumentsByIndex(name:String,index: Int) {
        if(listOfMyDocument.contains(_currentDocument.value)){
            _currentDocument.value = listOfMyDocument[index]
            _currentDocument.value.setName(youtubeRestService.getTitle())
        }
    }

    override fun startService(aList: Set<TypeOfService>, bodyPartSearch: String):Boolean{


        if((System.nanoTime()) <= (5000000000L+currentTime) && currentTime!=-1L){
            println("Dont start Services")
            //currentTime = System.nanoTime()
            return false
        }
        else {
            currentTime = System.nanoTime()
            _isComplete.value = false
            if (_currentDocument.value.getName() != bodyPartSearch){
                for(i in listOfMyDocument){
                    if(i.getName() != _currentDocument.value.getName()){
                        listOfMyDocument.add(_currentDocument.value)
                        _currentDocument.value = MyDocument()
                        break
                    }
                }
            }

            val job = GlobalScope.launch(Dispatchers.Default){

            //TODO: Fix later
            //launch{ youtubeRestService.runRestService(networkManager)}
                if(aList.contains(TypeOfService.Youtube) && !aList.contains(TypeOfService.Wikipedia)){
                    _lastService.value = TypeOfService.Youtube
                    launch{
                        youtubeRestService.runRestService(
                            networkManager, _currentDocument.value ,_isComplete, bodyPartSearch
                        )
                    }
                }

                else if(!aList.contains(TypeOfService.Youtube) && aList.contains(TypeOfService.Wikipedia)){
                    _lastService.value = TypeOfService.Wikipedia
                    launch{
                        wikipediaGenericRestService.runRestService(
                            networkManager, _currentDocument.value,_isComplete2, bodyPartSearch
                        )
                    }
                }

                else if(aList.contains(TypeOfService.Youtube) && aList.contains(TypeOfService.Wikipedia)){
                    _lastService.value = TypeOfService.Both
                    launch{
                        wikipediaGenericRestService.runRestService(
                            networkManager, _currentDocument.value,_isComplete, bodyPartSearch
                        )
                    }
                    launch{
                        youtubeRestService.runRestService(
                            networkManager, _currentDocument.value,_isComplete2, bodyPartSearch
                        )
                    }
                }

                //launch{
                //    wikipediaGenericRestService.runRestService(networkManager, _currentDocument.value,_isComplete)
                //}
                //withContext(Dispatchers.Main) {
                //    println("Switching to the main thread to update UI or perform other tasks.")
                //}
            /*val x = testHetml
            val y = MyHtmlParser.parseHtml2(x)

            _currentDocument.value = MyDocument()
            _currentDocument.value.initNewDocument(y)*/
            }
        }
        return true

        //if(networkManager.isOnline()){
        //    val backgroundJob = GlobalScope.launch(Dispatchers.Default){
        //        networkManager.runNetworkService()
        //    }
        //}

    }

    override fun isServiceComplete(): Boolean {
        return _isComplete.value
    }

}