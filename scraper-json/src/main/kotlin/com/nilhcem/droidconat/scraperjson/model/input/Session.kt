package com.nilhcem.droidconat.scraperjson.model.input

data class Session(val id: Int, val title: String, val place: String?, val service: Boolean?, val description: String?, val subtype: String?, val speakers: List<String>?, val complexity: String?)
