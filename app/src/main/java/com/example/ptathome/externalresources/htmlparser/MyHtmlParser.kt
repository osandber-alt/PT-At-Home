package com.example.ptathome.externalresources.htmlparser

import org.jsoup.Jsoup

// TODO: Expand to extract paragraphs (p),
//  sections (sections),
//  headings (h2-4) and
//  table related data (tbody and td)

class MyHtmlParser {

    companion object{

    fun parseHtml(htmlMessage: String) {
        val x = Jsoup.parse(htmlMessage)
        val y = x.select("section, h2, h3, h4, p, tbody td")
        for (i in y) {
            if (i.`is`("h2"))
                println("h2 = ${i.text()}")

            if (i.`is`("h3"))
                println("h3 = ${i.text()}")

            if (i.`is`("h4"))
                println("h4 = ${i.text()}")
        }
    }

        // todo: implement css selector parameter that takes into consideration the following HTML tags:
        // <b>
        // <a>
        // fix also so that tables are created
    fun parseHtml2(htmlMessage: String): MutableList<Pair<String, String>> {
        val x = Jsoup.parse(htmlMessage)
        //val y = x.select("section, h2, h3, h4, p, tbody td")//("tbody td")
        //val y = x.select("section h2")
        //val yy = x.select("section h3")
        val yyy = x.select("section h2, section h3, section h4")
        val yyyy = x.select("section h2, p, tbody td, section h3, p, tbody td, section h4, p, tbody td")

        //val z = mutableListOf<Pair<String,String>>()
        //val zz = mutableListOf<Pair<String,String>>()
        //val zzz = mutableListOf<Pair<String,String>>()
        val zzzz = mutableListOf<Pair<String,String>>()

        var index = -1
        //for (i in y) {
        //    index++
        //    //println("Print out element ${index}= ${i.text()}")
        //    //println(i.tagName())
        //    z.add(Pair(i.text(),i.tagName()))
        //}

         //index = -1
        for (i in yyyy) {
            index++
            //println("Print out element ${index}= ${i.text()}")
            //println(i.tagName())
            zzzz.add(Pair(i.text(),i.tagName()))
        }

        //for (i in z){
        //    println("Text:\n${i.first}\ntagName = ${i.second}\n\n")
        //}

        //for (i in zzzz){
            //println("Text:\n${i.first}\ntagName = ${i.second}\n\n")
        //}

        return zzzz
    }
    }
}