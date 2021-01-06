package com.example.blackjack

import java.net.URL

//import com.google.gson.
//public static JSON
object MainActivity {
    @JvmStatic
    fun main(args: Array<String>) {
        try {
            val apiURL = URL("https://deckofcardsapi.com/api/deck/new/")
            val connect = apiURL.openConnection()
            //connect.setRequestProperty(,);
            val response = connect.getInputStream()
        } catch (e: Exception) {
            println(e)
            println("Internet connection failed")
        }
        var deckid: String
        //val me = Player("House")
        //println(me.name)
    }
}