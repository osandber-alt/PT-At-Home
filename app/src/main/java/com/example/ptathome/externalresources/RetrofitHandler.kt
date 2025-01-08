package com.example.ptathome.externalresources

import com.example.ptathome.externalresources.htmlparser.MyHtmlParser
import com.example.ptathome.model.MyDocument
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//TODO: Implement with the following link:
// https://youtube.googleapis.com/youtube/v3/search?part=snippet&key=AIzaSyDwnst2BtTY4jWPGVztQRHGdTshYT-2hCM&type=video&location=59.32,18.06&locationRadius=1000km&q=shoulder rehab exercise

/**
 *
 * Implementation for handling network related operations with Retrofit.
 *
 * @author Oscar Sandberg
 *
 */
class RetrofitHandler {

    companion object{
        // A service which can access the wikipedia rest api
        // It currently only implements the Wikipedia rest api.
        // The wikipedia Api currently gets the Summary of a wikipedia article.
        // It does not seem that the API can directly access specific parts of the wikipedia article.
        // The only way to access the wikipedia article directly is by getting the entire HTML file of the Wikipedia article.
        fun <T:Any>runService(
            serviceName: String,
            baseURL: String,
            apiKey: String = "nothing",
            topic: List<String>,
            title: String,
            receiver: T,
            _isComplete: MutableStateFlow<Boolean>
        ) {

            if(serviceName == "WikipediaRestService")
                runWikipediaService(baseURL, apiKey, topic, title,receiver,_isComplete)

            if(serviceName == "YoutubeRestService"){
                runYoutubeService(baseURL, apiKey, topic, title,receiver,_isComplete)
            }
        }

        // Run the wikipedia service.
        // The service yields the summary and the HTML representation of a Wikipedia article.
        private fun<T:Any> runWikipediaService(
            baseURL: String,
            apiKey: String,
            topic: List<String>,
            title: String,
            receiver: T,
            _isComplete: MutableStateFlow<Boolean>
            ){
            // Define the URL of the RESTful API you want to call

            for(i in topic){

                // Run summary search
                if(i == "summary"){
                    val jsonWikipediaSummaryService =
                        RetrofitClient.createRetrofit(
                            baseURL//"https://en.wikipedia.org/api/rest_v1/"
                        )
                            .create(JsonWikipediaSummaryService::class.java)

                    // Represents the call class
                    val call: Call<WikipediaSummaryCallDataClass> = jsonWikipediaSummaryService.getRestApiJsonService(
                        i,title//"summary", "Shoulder"
                    )

                    val response = call.execute()   // Executes the call synchronously
                    println(response.code())        // Prints the response code

                    if (response.code() == 404) throw Exception("404 response")   // Throw an exception when coordinates are out of bounds

                    if (response.isSuccessful) {
                        val post = response.body()

                        if (post != null) {
                            if(receiver is MyDocument){
                                //println("The summary = ${post.extract}")
                                receiver.initSummary(post.extract)
                            }

                        } else {
                            println("Error: ${response.code()}")
                        }
                    }
                }

                // Run html search
                else if(i == "html"){
                    val jsonPlaceholderService =
                        RetrofitClient.createRetrofit2(
                            baseURL//"https://en.wikipedia.org/api/rest_v1/"
                        )
                            .create(HtmlWikipediaService::class.java)

                    // Represents the call class
                    val call: Call<String> = jsonPlaceholderService.getRestApiJsonService(
                        i,title//"summary", "Shoulder"
                    )

                    val response = call.execute()   // Executes the call synchronously
                    println(response.code())        // Prints the response code

                    if (response.code() == 404) throw Exception("404 response")   // Throw an exception when coordinates are out of bounds

                    // If successful, set the localWeatherState with the json data stored in the database
                    if (response.isSuccessful) {
                        val post = response.body()

                        if (post != null) {
                            if(receiver is MyDocument){
                                //println("The html:\n ${post}")
                                receiver.initNewDocument(
                                    MyHtmlParser.parseHtml2(post)
                                )
                                receiver.setRawHtml(post)
                            }

                        } else {
                            println("Error: ${response.code()}")
                        }
                    }
                }
            }
            _isComplete.value = true

        }

        // Run the youtube service.
        // The service yields the following data: Video Id, Video title and Thumbnail url.
        private fun<T:Any> runYoutubeService(
            baseURL: String,
            apiKey: String,
            topic: List<String>,
            title: String,
            receiver: T,
            _isComplete: MutableStateFlow<Boolean>
        ){
            // Define the URL of the RESTful API you want to call

            if(receiver is MyDocument){
                receiver.setName(title)
            }

            // Get video information when searching for a specific topic.
            // The topic is defined in the RestServices.kt class.
            for(i in topic){
                // Get videos from exercise topic
                if(i == "exercise"){
                    val jsonPlaceholderService =
                        RetrofitClient.createRetrofit(
                            baseURL
                        )
                            .create(JsonYoutubeInfoService::class.java)

                    // Represents the call class
                    val call: Call<TheYoutubeRestCallDataClass> =
                        jsonPlaceholderService.getRestApiJsonService(
                            "snippet",
                            apiKey,
                            "video",
                            "59.32,18.06",
                            "1000km",
                            "$title exercise",
                        )

                    val response = call.execute()   // Executes the call synchronously
                    println(response.code())        // Prints the response code

                    if (response.code() == 404) throw Exception("404 response")   // Throw an exception when coordinates are out of bounds

                    if (response.isSuccessful) {
                        val post = response.body()
                        if (post != null) {
                            if(receiver is MyDocument){
                                receiver.clearTrainingVideoId()
                                for(j in post.items){
                                    receiver.addTrainingVideoId(
                                        j.id.videoId,j.snippet.title,
                                        intArrayOf(j.snippet.thumbnails.high.height,j.snippet.thumbnails.high.width),
                                        j.snippet.thumbnails.high.url
                                    )
                                }
                            }

                        } else {
                            println("Error: ${response.code()}")
                        }
                    }
                }

                // Get videos from rehab topic
                else if(i == "rehab"){
                    val jsonPlaceholderService =
                        RetrofitClient.createRetrofit(
                            baseURL
                        )
                            .create(JsonYoutubeInfoService::class.java)

                    // Represents the call class
                    val call: Call<TheYoutubeRestCallDataClass> =
                        jsonPlaceholderService.getRestApiJsonService(
                            "snippet",
                            apiKey,
                            "video",
                            "59.32,18.06",
                            "1000km",
                            "$title rehab exercise"
                        )

                    val response = call.execute()   // Executes the call synchronously
                    println(response.code())        // Prints the response code

                    if (response.code() == 404) throw Exception("404 response")   // Throw an exception when coordinates are out of bounds

                    // If successful, set the localWeatherState with the json data stored in the database
                    if (response.isSuccessful) {
                        val post = response.body()
                        if (post != null) {

                            if(receiver is MyDocument){
                                receiver.clearRehabVideoId()
                                for(j in post.items){
                                    receiver.addRehabVideoId(
                                        j.id.videoId,j.snippet.title,
                                        intArrayOf(j.snippet.thumbnails.high.height,j.snippet.thumbnails.high.width),
                                        j.snippet.thumbnails.high.url
                                    )
                                }

                            }

                        } else {
                            println("Error: ${response.code()}")
                        }
                    }
                }
            }
            _isComplete.value = true

        }


    }


    // Json data for wikipedia rest service
    interface JsonWikipediaSummaryService {
        @GET("page/{pageType}/{topic}")
        fun getRestApiJsonService(
            @Path("pageType") pageType: String,
            @Path("topic") topic: String
        ): Call<WikipediaSummaryCallDataClass>
    }
    // example: https://youtube.googleapis.com/youtube/v3/search?part=snippet&key=AIzaSyDwnst2BtTY4jWPGVztQRHGdTshYT-2hCM&q=shoulder exercise
    // Json data for youtube rest service
    interface JsonYoutubeInfoService {
        @GET("search")
        fun getRestApiJsonService(
            @Query ("part") part:String,
            @Query ("key") key:String,
            @Query ("type") type:String,
            @Query ("location") location:String,
            @Query ("locationRadius") locationRadius:String,
            @Query ("q") q:String
        ): Call<TheYoutubeRestCallDataClass>
    }

    // String data for wikipedia rest service
    interface HtmlWikipediaService {
        @GET("page/{pageType}/{topic}")
        fun getRestApiJsonService(
            @Path("pageType") pageType: String,
            @Path("topic") topic: String
        ): Call<String>
    }

    // Summary data for Wikipedia
    data class WikipediaSummaryCallDataClass(val extract:String, val html:String)


    data class TheYoutubeRestCallDataClass(val items:List<YoutubeItem>)
    data class YoutubeItem(val id:VideoIdAndKind, val snippet:SnippetData)
    data class VideoIdAndKind(val kind:String, val videoId:String)
    data class SnippetData(val title:String, val thumbnails:ThumbnailsData)
    data class ThumbnailsData(val default:ThumbNailData, val medium:ThumbNailData, val high:ThumbNailData)
    data class ThumbNailData(val url:String, val width:Int, val height:Int)

    object RetrofitClient {
        fun createRetrofit(baseUrl: String) = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun createRetrofit2(baseUrl: String) = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }




}