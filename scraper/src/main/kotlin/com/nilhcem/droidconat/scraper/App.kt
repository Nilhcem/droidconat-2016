package com.nilhcem.droidconat.scraper

import com.nilhcem.droidconat.scraper.model.Session
import com.nilhcem.droidconat.scraper.model.Speaker
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.File

fun main(args: Array<String>) {
    with (Scraper()) {
        createJsons(speakers, sessions)
    }
}

fun createJsons(speakers: List<Speaker>, sessions: List<Session>) {
    val moshi = Moshi.Builder().build()
    File("output").mkdir()

    File("output/speakers.json").printWriter().use { out ->
        val adapter: JsonAdapter<List<Speaker>> = moshi.adapter(Types.newParameterizedType(List::class.java, Speaker::class.java))
        out.println(adapter.toJson(speakers))
    }

    File("output/sessions.json").printWriter().use { out ->
        val adapter: JsonAdapter<List<Session>> = moshi.adapter(Types.newParameterizedType(List::class.java, Session::class.java))
        out.println(adapter.toJson(sessions))
    }
}
