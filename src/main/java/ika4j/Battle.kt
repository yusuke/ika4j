/*
 * Copyright 2018 Yusuke Yamamoto
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package ika4j

import org.json.JSONObject
import java.time.LocalDateTime
import java.time.ZoneOffset

data class Battle(val id:Long,
                  val rule: Rule,
                  val stageA: Stage,
                  val stageB: Stage,
                  val startTime: LocalDateTime,
                  val endTime: LocalDateTime) {
    constructor(battle: JSONObject) : this(
            id = battle.getLong("id"),
            rule = Rule(battle.getJSONObject("rule").getString("key"), battle.getJSONObject("rule").getString("multiline_name"), battle.getJSONObject("rule").getString("name")),
            startTime = LocalDateTime.ofEpochSecond(battle.getLong("start_time"), 0, ZoneOffset.of("+9")),
            endTime = LocalDateTime.ofEpochSecond(battle.getLong("end_time"), 0, ZoneOffset.of("+9")),
            stageA = Stage(battle.getJSONObject("stage_a")),
            stageB = Stage(battle.getJSONObject("stage_b")))

    internal fun isLiveAt(dateTime: LocalDateTime): Boolean {
        return dateTime.isAfter(startTime) && dateTime.isBefore(endTime)
    }
}
