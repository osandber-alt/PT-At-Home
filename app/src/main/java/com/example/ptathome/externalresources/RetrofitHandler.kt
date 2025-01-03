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
                if(i == "summary"){
                    val jsonPlaceholderService =
                        RetrofitClient.createRetrofit(
                            baseURL//"https://en.wikipedia.org/api/rest_v1/"
                        )
                            .create(JsonPlaceholderService::class.java)

                    // Represents the call class
                    val call: Call<theRestCallDataClass> = jsonPlaceholderService.getRestApiJsonService(
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
                                //println("The summary = ${post.extract}")
                                receiver.initSummary(post.extract)
                            }

                        } else {
                            println("Error: ${response.code()}")
                        }
                    }
                }
                else if(i == "html"){
                    val jsonPlaceholderService =
                        RetrofitClient.createRetrofit2(
                            baseURL//"https://en.wikipedia.org/api/rest_v1/"
                        )
                            .create(HtmlPlaceholderService::class.java)

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
            for(i in topic){
                if(i == "exercise"){
                    val jsonPlaceholderService =
                        RetrofitClient.createRetrofit(
                            baseURL
                        )
                            .create(JsonYoutubePlaceholderService::class.java)

                    // Represents the call class
                    val call: Call<theYoutubeRestCallDataClass> =
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

                    // If successful, set the localWeatherState with the json data stored in the database
                    if (response.isSuccessful) {
                        val post = response.body()
                        if (post != null) {
                            if(receiver is MyDocument){
                                receiver.clearTrainingVideoId()
                                for(j in post.items){
                                    receiver.modifyTrainingVideoId(
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

                else if(i == "rehab"){
                    val jsonPlaceholderService =
                        RetrofitClient.createRetrofit(
                            baseURL
                        )
                            .create(JsonYoutubePlaceholderService::class.java)

                    // Represents the call class
                    val call: Call<theYoutubeRestCallDataClass> =
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
                                    receiver.modifyRehabVideoId(
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


    interface JsonPlaceholderService {
        @GET("page/{pageType}/{topic}")
        fun getRestApiJsonService(
            @Path("pageType") pageType: String,
            @Path("topic") topic: String
        ): Call<theRestCallDataClass>
    }
    // example: https://youtube.googleapis.com/youtube/v3/search?part=snippet&key=AIzaSyDwnst2BtTY4jWPGVztQRHGdTshYT-2hCM&q=shoulder exercise
    interface JsonYoutubePlaceholderService {
        @GET("search")
        fun getRestApiJsonService(
            @Query ("part") part:String,
            @Query ("key") key:String,
            @Query ("type") type:String,
            @Query ("location") location:String,
            @Query ("locationRadius") locationRadius:String,
            @Query ("q") q:String
        ): Call<theYoutubeRestCallDataClass>
    }

    interface HtmlPlaceholderService {
        @GET("page/{pageType}/{topic}")
        fun getRestApiJsonService(
            @Path("pageType") pageType: String,
            @Path("topic") topic: String
        ): Call<String>
    }

    data class theRestCallDataClass(val extract:String, val html:String)

    data class theYoutubeRestCallDataClass(val items:List<theData2>)
    data class theData2(val id:theData3, val snippet:theData4)
    data class theData3(val kind:String, val videoId:String)
    data class theData4(val title:String, val thumbnails:theData5)
    data class theData5(val default:theData6,val medium:theData6,val high:theData6)
    data class theData6(val url:String, val width:Int, val height:Int)

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