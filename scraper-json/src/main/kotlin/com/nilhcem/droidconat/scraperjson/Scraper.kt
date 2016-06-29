package com.nilhcem.droidconat.scraperjson

import com.nilhcem.droidconat.scraperjson.api.DroidconApi
import com.nilhcem.droidconat.scraperjson.model.Mapper
import com.nilhcem.droidconat.scraperjson.model.output.Session
import com.nilhcem.droidconat.scraperjson.model.output.Speaker

class Scraper(val api: DroidconApi) {

    lateinit var speakers: List<Speaker>
    lateinit var sessions: List<Session>

    init {
        println("Start scraping")

        val speakersMap = api.getSpeakers().execute().body()
                .mapIndexed { i, speaker -> speaker.id to Mapper.convertSpeaker(i, speaker) }
                .toMap()

        val sessionsMap = api.getSessions().execute().body()
                .filter { it.id != 999 }
                .map { it.id to it }
                .toMap()

        val scheduleDays = api.getSchedule().execute().body()

        speakers = speakersMap.values.toList()
        sessions = Mapper.convertSession(speakersMap, sessionsMap, scheduleDays)
    }
}
