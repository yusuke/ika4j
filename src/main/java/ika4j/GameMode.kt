package ika4j

import org.json.JSONObject

data class GameMode(val key:String, val name:String) {
    constructor(gameMode:JSONObject): this(gameMode.getString("key"), gameMode.getString("name"))
}