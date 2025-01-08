package com.example.ptathome.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ptathome.model.MyDocument
import com.example.ptathome.externalresources.network.NetworkManager
import com.example.ptathome.externalresources.restresources.RestInterface
import com.example.ptathome.externalresources.restresources.TypeOfService
import com.example.ptathome.externalresources.restresources.wikipedia
import com.example.ptathome.externalresources.restresources.youtube
import com.example.ptathome.model.CombinedWikipediaYoutubeData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 * The viewmodel used for the PT At Home application.
 *
 * @author Oscar Sandberg
 *
 */

interface PtHomeViewModelInterface{
    val currentDocument: StateFlow<MyDocument>
    val isWikipediaServiceComplete:StateFlow<Boolean>
    val isYoutubeServiceComplete:StateFlow<Boolean>
    val lastService:StateFlow<TypeOfService>

    val theDocumentsAsList: StateFlow<List<String>>
    val theTrainingIDsAsList: StateFlow<List<CombinedWikipediaYoutubeData>>
    val theRehabIDsAsList: StateFlow<List<CombinedWikipediaYoutubeData>>

    val currentTrainingId:StateFlow<String>
    val currentRehabId:StateFlow<String>

    fun startService(aList: Set<TypeOfService>, bodyPartSearch: String):Boolean
    fun isServiceComplete():Boolean
    fun setDocumentsByIndex(index:Int)
    fun getListOfDocuments():MutableList<MyDocument>
    fun setNewDocument(myDocument: MyDocument)

    fun updateContentListViews()

    fun modifyCurrentTrainingId(value:String)
    fun modifyCurrentRehabId(value: String)
}


@HiltViewModel
class ViewModel @Inject constructor(
    @youtube private val youtubeRestService: RestInterface,
    @wikipedia private val wikipediaGenericRestService: RestInterface,
    private val networkManager: NetworkManager
): ViewModel(),PtHomeViewModelInterface {

    // List of documents as a list of string
    private var _theDocumentsAsList = MutableStateFlow<List<String>>(emptyList())
    override val theDocumentsAsList: StateFlow<List<String>>
        get() = _theDocumentsAsList

    // List of training ids
    private var _theTrainingIDsAsList = MutableStateFlow<List<CombinedWikipediaYoutubeData>>(emptyList())
    override val theTrainingIDsAsList: StateFlow<List<CombinedWikipediaYoutubeData>>
        get() = _theTrainingIDsAsList

    // List of rehab ids
    private var _theRehabIDsAsList = MutableStateFlow<List<CombinedWikipediaYoutubeData>>(emptyList())
    override val theRehabIDsAsList: StateFlow<List<CombinedWikipediaYoutubeData>>
        get() = _theRehabIDsAsList

    // Current training id
    private var _currentTrainingId = MutableStateFlow("")
    override val currentTrainingId: StateFlow<String>
        get() = _currentTrainingId

    // Current rehab id
    private var _currentRehabId = MutableStateFlow("")
    override val currentRehabId: StateFlow<String>
        get() = _currentRehabId

    // Current document
    private val _currentDocument = MutableStateFlow(MyDocument())
    override val currentDocument: StateFlow<MyDocument>
        get() = _currentDocument.asStateFlow()

    // Current time
    private var currentTime:Long = -1L

    // Is wikipedia service complete
    private var _isWikipediaServiceComplete = MutableStateFlow(false)
    override val isWikipediaServiceComplete: StateFlow<Boolean>
        get() = _isWikipediaServiceComplete.asStateFlow()

    // Is youtube service complete
    private var _isYoutubeServiceComplete = MutableStateFlow(false)
    override val isYoutubeServiceComplete: StateFlow<Boolean>
        get() = _isYoutubeServiceComplete.asStateFlow()

    // The previous service that has been run
    private var _lastService = MutableStateFlow(TypeOfService.Nothing)
    override val lastService: StateFlow<TypeOfService>
        get() = _lastService.asStateFlow()

    // List of documents
    private var listOfMyDocument:MutableList<MyDocument> = mutableListOf()

    /**
     * Update all lists which are viewed in the screens which are used in this application
     */
    override fun updateContentListViews() {
        _theTrainingIDsAsList.value = _currentDocument.value.getTrainingVideoId()
        _theRehabIDsAsList.value = _currentDocument.value.getRehabVideoId()

        val localList = listOfMyDocument
        var local2 = mutableListOf<String>()
        println("the length = ${localList.size}")
        for (i in localList){
            println("is it valid")
            local2.add(i.getName())
        }
        println(local2)
        _theDocumentsAsList.value = local2
        println(_theDocumentsAsList.value)
    }

    /**
     *
     * Set current training video id
     *
     */
    override fun modifyCurrentTrainingId(value: String) {
        _currentTrainingId.value = value
    }

    /**
     *
     * Set current rehab video id
     *
     */
    override fun modifyCurrentRehabId(value: String) {
        _currentRehabId.value = value
    }

    /**
     * Set current document by index
     * @param index, the index used to set the new current document
     */
    override fun setDocumentsByIndex(index: Int) {
        if(listOfMyDocument.contains(_currentDocument.value)){
            _currentDocument.value = listOfMyDocument[index]
        }
    }

    /**
     *
     * Get all list of documents
     *
     */
    override fun getListOfDocuments(): MutableList<MyDocument> {
        return listOfMyDocument
    }

    /**
     *
     * Set current document with an new document
     *
     * @param myDocument, the new document
     *
     */
    override fun setNewDocument(myDocument: MyDocument) {
        _currentDocument.value = myDocument
        updateContentListViews()
    }

    /**
     * Start a Network rest service based on the TypeOfService and body part
     *
     * @param aList, the list of used services in this specific startService method
     * @param bodyPartSearch, the body part to search
     * @return false if service could not been started
     */
    override fun startService(aList: Set<TypeOfService>, bodyPartSearch: String):Boolean{


        if((System.nanoTime()) <= (5000000000L+currentTime) && currentTime!=-1L){
            println("Don't start Services")
            return false
        }
        else {
            currentTime = System.nanoTime()
            _isWikipediaServiceComplete.value = false
            _isYoutubeServiceComplete.value = false
            println("Current doc name = ${_currentDocument.value.getName()}")

            if(_currentDocument.value.getName()==""){
                listOfMyDocument.add(_currentDocument.value)
            }

            else if (_currentDocument.value.getName() == bodyPartSearch){
                        _currentDocument.value = MyDocument()
            }

            else if(_currentDocument.value.getName() != bodyPartSearch){
                _currentDocument.value = MyDocument()
                listOfMyDocument.add(_currentDocument.value)
            }


            val job = GlobalScope.launch(Dispatchers.Default){
                if(aList.contains(TypeOfService.Youtube) && !aList.contains(TypeOfService.Wikipedia)){
                    _lastService.value = TypeOfService.Youtube
                    launch{
                        youtubeRestService.runRestService(
                            networkManager, _currentDocument.value ,_isYoutubeServiceComplete, bodyPartSearch
                        )
                    }
                }

                else if(!aList.contains(TypeOfService.Youtube) && aList.contains(TypeOfService.Wikipedia)){
                    _lastService.value = TypeOfService.Wikipedia
                    launch{
                        wikipediaGenericRestService.runRestService(
                            networkManager, _currentDocument.value,_isWikipediaServiceComplete, bodyPartSearch
                        )
                    }
                }

                else if(aList.contains(TypeOfService.Youtube) && aList.contains(TypeOfService.Wikipedia)){
                    _lastService.value = TypeOfService.Both
                    launch{
                        wikipediaGenericRestService.runRestService(
                            networkManager, _currentDocument.value,_isWikipediaServiceComplete, bodyPartSearch
                        )
                    }
                    launch{
                        youtubeRestService.runRestService(
                            networkManager, _currentDocument.value,_isYoutubeServiceComplete, bodyPartSearch
                        )
                    }
                }
            /*val x = testHetml
            val y = MyHtmlParser.parseHtml2(x)

            _currentDocument.value = MyDocument()
            _currentDocument.value.initNewDocument(y)*/
            }
        }
        return true

    }

    /**
     *
     * Check if wikipedia service has been completed
     *
     * @return true if the service has been completed
     *
     */
    override fun isServiceComplete(): Boolean {
        return _isWikipediaServiceComplete.value
    }

}