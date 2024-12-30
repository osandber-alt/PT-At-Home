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

    fun parseHtml2(htmlMessage: String): MutableList<Pair<String, String>> {
        val x = Jsoup.parse(htmlMessage)
        val y = x.select("section, h2, h3, h4, p, tbody td")//("tbody td")

        val z = mutableListOf<Pair<String,String>>()

        var index = -1
        for (i in y) {
            index++
            //println("Print out element ${index}= ${i.text()}")
            //println(i.tagName())
            z.add(Pair(i.text(),i.tagName()))
        }
        return z
    }
    }
}