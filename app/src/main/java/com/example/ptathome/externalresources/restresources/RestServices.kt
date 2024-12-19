package com.example.ptathome.externalresources.restresources

import android.content.Context
import javax.inject.Inject

class WikipediaRestService @Inject constructor(

): GenericRestService(
    "https://en.wikipedia.org/api/rest_v1/",
    "nothing",
    "summary",
    "Lord"

)

//TODO: Implement later
class YoutubeRestService @Inject constructor():
    GenericRestService(
        "youtube base url","youtube api key",
        "test topic",
        "Test title"
    )
