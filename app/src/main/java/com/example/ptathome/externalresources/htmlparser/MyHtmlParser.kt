package com.example.ptathome.externalresources.htmlparser

import org.jsoup.Jsoup

// TODO: Expand to extract paragraphs (p),
//  sections (sections),
//  headings (h2-4) and
//  table related data (tbody and td)
fun parseHtml(htmlMessage:String){
    val x = Jsoup.parse(htmlMessage)
    val y =
        x.select("section, h2, h3, h4, p, tbody td")
    for(i in y){
        if(i.`is`("h2"))
            println("h2 = ${i.text()}")

        if(i.`is`("h3"))
            println("h3 = ${i.text()}")

        if(i.`is`("h4"))
            println("h4 = ${i.text()}")
    }
}