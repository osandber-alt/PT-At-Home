package com.example.ptathome.externalresources.restresources

import javax.inject.Inject

/**
 *
 * The specific Wikipedia and Youtube rest service implementations
 *
 * @author Oscar Sandberg
 *
 */

/**
 *
 * The specific Wikipedia rest service implementation
 *
 */
class WikipediaRestService @Inject constructor(
): GenericRestService(
    "https://en.wikipedia.org/api/rest_v1/",
    "nothing",
    listOf("summary","html"),
    "Shoulder"
)

/**
 *
 * The specific Youtube rest service implementation
 *
 */
class YoutubeRestService @Inject constructor():
    GenericRestService(
        "https://youtube.googleapis.com/youtube/v3/",
        "AIzaSyDwnst2BtTY4jWPGVztQRHGdTshYT-2hCM",
        listOf("exercise","rehab"),
        "shoulder"
    )
