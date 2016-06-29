package com.nilhcem.droidconat.scraper.model.output

enum class Room(val id: Int, val roomName: String) {

    NONE(0, ""),
    ROOM_1(1, "Room 1"),
    ROOM_2(2, "Room 2"),
    ROOM_3(3, "Room 3");

    companion object {
        fun getRoomId(name: String) = Room.values().filter { name == it.roomName }.map { it.id }.getOrElse(0, { NONE.id })
    }
}
