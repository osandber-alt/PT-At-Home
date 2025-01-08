package com.example.ptathome.externalresources.htmlparser

import org.jsoup.Jsoup

// TODO: Expand to extract paragraphs (p),
//  sections (sections),
//  headings (h2-4) and
//  table related data (tbody and td)
/**
 *
 * Class for parsing HTML response messages, which are represented as a string, with Jsoup
 *
 * @author Oscar Sandberg
 *
 */
class MyHtmlParser {

    companion object{

        /**
         * Old method of parsing a string which represents a HTML response.
         *
         * Present string from a specific Css Query
         *
         * @param htmlMessage, The HTML response represented as a string
         */
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

        /**
         * Current method of parsing a HTML response represented as a string
         *
         * @param htmlMessage, The HTML response message to parse
         *
         * @return a list of Pairs which contain a HTML Elements text and tag
         */
        fun parseHtml2(htmlMessage: String): MutableList<Pair<String, String>> {
            val x = Jsoup.parse(htmlMessage)
            //val y = x.select("section, h2, h3, h4, p, tbody td")//("tbody td")
            // val y = x.select("section h2")
            // val yy = x.select("section h3")
            // val yyy = x.select("section h2, section h3, section h4")

            val y = x   // Get elements which correspond to the cssQuery in this specific order.
                .select(
                "section h2, p, tbody td, section h3, p, tbody td, section h4, p, tbody td"
            )

            //val z = mutableListOf<Pair<String,String>>()
            //val zz = mutableListOf<Pair<String,String>>()
            //val zzz = mutableListOf<Pair<String,String>>()
            val z = mutableListOf<Pair<String,String>>()

            var index = -1
            //for (i in y) {
            //    index++
            //    //println("Print out element ${index}= ${i.text()}")
            //    //println(i.tagName())
            //    z.add(Pair(i.text(),i.tagName()))
            //}

            //index = -1
            for (i in y) {
                index++
                //println("Print out element ${index}= ${i.text()}")
                //println(i.tagName())
                z.add(Pair(i.text(),i.tagName()))       // Add The tag Element text and Element tag
            }

            //for (i in z){
            //    println("Text:\n${i.first}\ntagName = ${i.second}\n\n")
            //}

            //for (i in zzzz){
                //println("Text:\n${i.first}\ntagName = ${i.second}\n\n")
            //}

            return z
        }
    }
}