package ika4j

import org.json.JSONObject

data class Stage internal constructor(val id: Int, val image: String, val name: String) {
    internal constructor(stage: JSONObject): this(stage.getInt("id"), stage.getString("image"),
            stage.getString("name"))
}