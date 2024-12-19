package com.example.ptathome.externalresources

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class RetrofitHandler {

    companion object{
        // A service which can access the wikipedia rest api
        // It currently only implements the Wikipedia rest api.
        // The wikipedia Api currently gets the Summary of a wikipedia article.
        // It does not seem that the API can directly access specific parts of the wikipedia article.
        // The only way to access the wikipedia article directly is by getting the entire HTML file of the Wikipedia article.
        fun runService(baseURL: String, apiKey: String = "nothing", topic: String, title: String) {

            // Define the URL of the RESTful API you want to call
            val jsonPlaceholderService =
                RetrofitClient.createRetrofit(
                    baseURL//"https://en.wikipedia.org/api/rest_v1/"
                )
                    .create(JsonPlaceholderService::class.java)

            // Represents the call class
            val call: Call<theRestCallDataClass> = jsonPlaceholderService.getRestApiJsonService(
                topic,title//"summary", "Shoulder"
            )

            val response = call.execute()   // Executes the call synchronously
            println(response.code())        // Prints the response code

            if (response.code() == 404) throw Exception("404 response")   // Throw an exception when coordinates are out of bounds

            // If successful, set the localWeatherState with the json data stored in the database
            if (response.isSuccessful) {
                val post = response.body()

                if (post != null) {
                    println("The post = ${post.extract}")

                } else {
                    println("Error: ${response.code()}")
                }
            }
        }
    }

    interface JsonPlaceholderService {
        @GET("page/{pageType}/{topic}")
        fun getRestApiJsonService(
            @Path("pageType") pageType: String,
            @Path("topic") topic: String
        ): Call<theRestCallDataClass>
    }

    data class theRestCallDataClass(val extract:String)

    object RetrofitClient {
        fun createRetrofit(baseUrl: String) = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}