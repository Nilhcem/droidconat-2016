package com.nilhcem.droidconat.scraper

import com.nilhcem.droidconat.scraper.model.Room
import com.nilhcem.droidconat.scraper.model.Session
import com.nilhcem.droidconat.scraper.model.Speaker
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.safety.Whitelist
import org.jsoup.select.Elements
import kotlin.text.RegexOption.IGNORE_CASE

class Scraper {

    val speakers = mutableListOf<Speaker>()
    val sessions = mutableListOf<Session>()

    companion object {
        val BASE_URL = "https://droidcon.at"
        val SPEAKERS_URL = "$BASE_URL/speakers/"

        val NAME_REGEX = Regex("(.*)<span>.*")
        val IMG_REGEX = Regex(".*url\\(([^\\)]*)\\).*")
    }

    init {
        println("Start scraping")
        jsoup(SPEAKERS_URL).select(".modals .people-modal .modal-body")
                .forEachIndexed { i, it ->
                    val speakerPhoto = "$BASE_URL${IMG_REGEX.find(it.select(".people-img").attr("style"))!!.groups[1]!!.value}"
                    val speakerName = NAME_REGEX.find(it.select(".name").fmtText())!!.groups[1]!!.value.trim()
                    val speakerTitle = with (it.select(".position").fmtText()) { if (startsWith(", ")) substring(2) else this }
                    val speakerBio = it.select(".about").fmtText()
                    val links = it.select(".social li a").map { it.attr("href") }
                    val twitterUrl = links.filter { it.contains("twitter.com/", true) }.firstOrNull()
                    val twitterHandler = getHandleFromUrl(twitterUrl)
                    val githubUrl = links.filter { it.contains("github.com/", true) }.firstOrNull()
                    val githubHandle = getHandleFromUrl(githubUrl)
                    val websiteUrl = links.filter { !it.equals(twitterUrl) && !it.equals(githubUrl) }.firstOrNull()
                    speakers.add(Speaker(i + 1, speakerName, speakerTitle, speakerPhoto, speakerBio, websiteUrl, twitterHandler, githubHandle))

                    val sessionTitle = it.select("h4").first().fmtText()
                    val sessionDesc = it.select(".theme-description").fmtText()
                    sessions.add(Session(i + 1, sessionTitle, sessionDesc, listOf(i + 1), "2016-09-16 10:00", 45, Room.ROOM_1.id))
                }
    }

    private fun getHandleFromUrl(url: String?): String? {
        if (url == null) {
            return null
        }

        val urlWithoutLastSlash = if (url.last() == '/') url.substring(0, url.length - 1) else url
        return urlWithoutLastSlash.substring(urlWithoutLastSlash.lastIndexOf("/") + 1)
    }

    private fun jsoup(url: String, nbRetries: Int = 3): Document {
        (0..nbRetries).forEach {
            try {
                return Jsoup.connect(url).get()
            } catch (e: Exception) {
                System.err.println("Error: ${e.message}. Retry")
                if (it == nbRetries - 1) {
                    throw e
                }
            }
        }
        throw IllegalStateException("This should not happen")
    }

    private fun Element.fmtText() = formatText(html())
    private fun Elements.fmtText() = formatText(html())
    private fun formatText(html: String) = Jsoup.clean(html, "", Whitelist.basic(),
            Document.OutputSettings().prettyPrint(false))
            .replace(Regex("&nbsp;", IGNORE_CASE), " ")
            .replace(Regex("&amp;", IGNORE_CASE), "&")
            .replace(Regex("&gt;", IGNORE_CASE), ">").replace(Regex("&lt;", IGNORE_CASE), "<")
            .replace(Regex("<br[\\s/]*>", IGNORE_CASE), "\n")
            .replace(Regex("<p>", IGNORE_CASE), "").replace(Regex("</p>", IGNORE_CASE), "\n")
            .replace(Regex("</?ul>", IGNORE_CASE), "")
            .replace(Regex("<li>", IGNORE_CASE), "• ").replace(Regex("</li>", IGNORE_CASE), "\n")
            .replace(Regex("\n\n• ", IGNORE_CASE), "\n• ")
            .replace(Regex("<a\\s[^>]*>", IGNORE_CASE), "").replace(Regex("</a>", IGNORE_CASE), "")
            .replace(Regex("</?strong>", IGNORE_CASE), "")
            .replace(Regex("</?em>", IGNORE_CASE), "")
            .replace(Regex("\\s*\n\\s*"), "\n").replace(Regex("^\n"), "").replace(Regex("\n$"), "")
}
