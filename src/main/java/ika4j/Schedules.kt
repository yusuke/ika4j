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

import org.json.JSONException
import org.json.JSONObject

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.ArrayList
import java.util.NoSuchElementException

class Schedules private constructor(
        val rankedBattles: ArrayList<Match> = ArrayList(),
        val leagueBattles: ArrayList<Match> = ArrayList(),
        val regularBattles: ArrayList<Match> = ArrayList()) {

    val currentRankedBattle: Match
        get() = pick(rankedBattles, LocalDateTime.now())

    val currentLeagueBattle: Match
        get() = pick(leagueBattles, LocalDateTime.now())

    val currentRegularBattle: Match
        get() = pick(regularBattles, LocalDateTime.now())

    val nextRankedBattle: Match
        get() = pick(rankedBattles, LocalDateTime.now().plus(2, ChronoUnit.HOURS))

    val nextLeagueBattle: Match
        get() = pick(leagueBattles, LocalDateTime.now().plus(2, ChronoUnit.HOURS))

    val nextRegularBattle: Match
        get() = pick(regularBattles, LocalDateTime.now().plus(2, ChronoUnit.HOURS))

    private fun init(rawJson: String) {
        try {
            val json = JSONObject(rawJson)
            val gachiArray = json.getJSONArray("gachi")
            for (i in 0 until gachiArray.length()) {
                rankedBattles.add(createMatch(gachiArray.getJSONObject(i)))
            }

            val leagueArray = json.getJSONArray("league")
            for (i in 0 until leagueArray.length()) {
                leagueBattles.add(createMatch(leagueArray.getJSONObject(i)))
            }

            val regularArray = json.getJSONArray("regular")
            for (i in 0 until regularArray.length()) {
                regularBattles.add(createMatch(regularArray.getJSONObject(i)))
            }
        } catch (e: JSONException) {
            throw IllegalStateException(e)
        }
    }

    private fun createMatch(gachi: JSONObject): Match {
        return Match(
                rule = gachi.getJSONObject("rule").getString("name"),
                startTime = LocalDateTime.ofEpochSecond(gachi.getLong("start_time"), 0, ZoneOffset.of("+9")),
                endTime = LocalDateTime.ofEpochSecond(gachi.getLong("end_time"), 0, ZoneOffset.of("+9")),
                stageA = gachi.getJSONObject("stage_a").getString("name"),
                stageB = gachi.getJSONObject("stage_b").getString("name"))
    }


    private fun pick(maches: List<Match>, dateTime: LocalDateTime): Match {
        for (match in maches) {
            if (match.isLiveAt(dateTime)) {
                return match
            }
        }
        throw NoSuchElementException("No match found at " + dateTime)
    }

    fun getRankedBattleAt(dateTime: LocalDateTime): Match {
        return pick(rankedBattles, dateTime)
    }

    fun getLeagueBattleAt(dateTime: LocalDateTime): Match {
        return pick(leagueBattles, dateTime)
    }

    fun getRegularBattleAt(dateTime: LocalDateTime): Match {
        return pick(regularBattles, dateTime)
    }

    companion object {
        @JvmStatic
        fun fromRawJSON(rawJSON: String): Schedules {
            val schedules = Schedules()
            schedules.init(rawJSON)
            return schedules
        }
    }

}
