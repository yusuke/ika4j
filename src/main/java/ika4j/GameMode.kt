package ika4j

import org.json.JSONObject

data class GameMode internal constructor(val key:String, val name:String) {
    internal constructor(gameMode:JSONObject): this(gameMode.getString("key"), gameMode.getString("name"))
}