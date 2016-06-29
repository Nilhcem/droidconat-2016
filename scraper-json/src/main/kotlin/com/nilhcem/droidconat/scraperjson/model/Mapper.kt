package com.nilhcem.droidconat.scraperjson.model

import com.nilhcem.droidconat.scraperjson.model.input.ScheduleDay
import com.nilhcem.droidconat.scraperjson.model.input.SocialLink
import com.nilhcem.droidconat.scraperjson.model.output.Room
import java.text.SimpleDateFormat
import java.util.*
import com.nilhcem.droidconat.scraperjson.model.input.Session as ApiSession
import com.nilhcem.droidconat.scraperjson.model.input.Speaker as ApiSpeaker
import com.nilhcem.droidconat.scraperjson.model.output.Session as AppSession
import com.nilhcem.droidconat.scraperjson.model.output.Speaker as AppSpeaker

object Mapper {

    private val DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)

    fun convertSpeaker(id: Int, speaker: ApiSpeaker): AppSpeaker {
        val name = "${speaker.name} ${speaker.surname}"
        val title = listOfNotNull(speaker.title, speaker.company).joinToString(", ")
        val photo = "https://droidcon.at/img/people/${speaker.thumbnailUrl}"
        val bio = speaker.bio
        val twitter = speaker.social?.filter { it.name == "twitter" }.getLink()
        val github = speaker.social?.filter { it.name == "github" }.getLink()
        val website = speaker.social?.filter { it.name != "twitter" && it.name != "github" }.getLink()

        return AppSpeaker(id + 1, name, title, photo, bio, website, twitter, github)
    }

    fun convertSession(speakersMap: Map<String, AppSpeaker>, sessionsMap: Map<Int, ApiSession>, days: List<ScheduleDay>): List<AppSession> {
        val sessions = mutableListOf<AppSession>()

        days.forEach { day ->
            val roomIds = day.tracks.map { Room.getRoomId(it.title) }

            day.timeslots.forEach { time ->
                val startAt = "${day.date} ${time.startTime}"
                val dateFrom = DATE_FORMAT.parse(startAt)
                val dateTo = DATE_FORMAT.parse("${day.date} ${time.endTime}")
                val duration = ((dateTo.time - dateFrom.time) / 60000).toInt()

                time.sessionIds.forEachIndexed { i, sessionId ->
                    val roomId = roomIds[i]
                    val session = sessionsMap[sessionId]

                    if (session != null) {
                        sessions.add(createSession(sessions.size, speakersMap, startAt, duration, roomId, session))
                    }
                }
            }
        }

        return sessions.toList()
    }

    private fun createSession(id: Int, speakersMap: Map<String, AppSpeaker>, startAt: String, duration: Int, roomId: Int, session: ApiSession): AppSession {
        val title = session.title
        val description = session.description
        val speakersIds = session.speakers?.map { speakersMap[it]?.id }?.filterNotNull()
        val room = if (session.service ?: false) Room.NONE.id else roomId
        return AppSession(id + 1, title, description, speakersIds, startAt, duration, room)
    }

    private fun List<SocialLink>?.getLink() = this?.map { it.link }?.firstOrNull()
}
