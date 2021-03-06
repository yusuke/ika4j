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
import java.time.temporal.ChronoUnit
import java.util.ArrayList
import java.util.NoSuchElementException

class Schedules internal constructor(
        val rankedBattles: ArrayList<Battle>,
        val leagueBattles: ArrayList<Battle>,
        val regularBattles: ArrayList<Battle>) {

    val currentRankedBattle: Battle
        get() = pick(rankedBattles, LocalDateTime.now())

    val currentLeagueBattle: Battle
        get() = pick(leagueBattles, LocalDateTime.now())

    val currentRegularBattle: Battle
        get() = pick(regularBattles, LocalDateTime.now())

    val nextRankedBattle: Battle
        get() = pick(rankedBattles, LocalDateTime.now().plus(2, ChronoUnit.HOURS))

    val nextLeagueBattle: Battle
        get() = pick(leagueBattles, LocalDateTime.now().plus(2, ChronoUnit.HOURS))

    val nextRegularBattle: Battle
        get() = pick(regularBattles, LocalDateTime.now().plus(2, ChronoUnit.HOURS))

    internal constructor(rawJson: String) :this( ArrayList(),ArrayList(),ArrayList() ){
        try {
            val json = JSONObject(rawJson)
            val gachiArray = json.getJSONArray("gachi")
            for (i in 0 until gachiArray.length()) {
                rankedBattles.add(Battle(gachiArray.getJSONObject(i)))
            }

            val leagueArray = json.getJSONArray("league")
            for (i in 0 until leagueArray.length()) {
                leagueBattles.add(Battle(leagueArray.getJSONObject(i)))
            }

            val regularArray = json.getJSONArray("regular")
            for (i in 0 until regularArray.length()) {
                regularBattles.add(Battle(regularArray.getJSONObject(i)))
            }
        } catch (e: JSONException) {
            throw IllegalStateException(e)
        }
    }

    private fun pick(battles: List<Battle>, dateTime: LocalDateTime): Battle {
        for (battle in battles) {
            if (battle.isLiveAt(dateTime)) {
                return battle
            }
        }
        throw NoSuchElementException("No battle found at $dateTime")
    }

    fun getRankedBattleAt(dateTime: LocalDateTime): Battle {
        return pick(rankedBattles, dateTime)
    }

    fun getLeagueBattleAt(dateTime: LocalDateTime): Battle {
        return pick(leagueBattles, dateTime)
    }

    fun getRegularBattleAt(dateTime: LocalDateTime): Battle {
        return pick(regularBattles, dateTime)
    }
}
