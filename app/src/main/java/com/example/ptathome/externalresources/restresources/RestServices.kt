package com.example.ptathome.externalresources.restresources

import javax.inject.Inject

class WikipediaRestService @Inject constructor(
): GenericRestService(
    "https://en.wikipedia.org/api/rest_v1/",
    "nothing",
    listOf("summary","html"),
    "Shoulder"
)

//TODO: Implement later
class YoutubeRestService @Inject constructor():
    GenericRestService(
        "https://youtube.googleapis.com/youtube/v3/",
        "AIzaSyDwnst2BtTY4jWPGVztQRHGdTshYT-2hCM",
        listOf("exercise","rehab"),
        "shoulder"
    )
