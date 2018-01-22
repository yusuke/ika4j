/*
 * Copyright 2018 Yusuke Yamamoto
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package ika4j;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@SuppressWarnings("WeakerAccess")
public class Schedules {
    private final List<Match> rankedBattles = new ArrayList<>();
    private final List<Match> leagueBattles = new ArrayList<>();
    private final List<Match> regularBattles = new ArrayList<>();

    static Schedules fromRawJSON(String rawJSON) {
        Schedules schedules = new Schedules();
        schedules.init(rawJSON);
        return schedules;
    }

    private Schedules() {
    }

    private void init(String rawJson) {
        try {
            JSONObject json = new JSONObject(rawJson);
            JSONArray gachiArray = json.getJSONArray("gachi");
            for (int i = 0; i < gachiArray.length(); i++) {
                rankedBattles.add(new Match(gachiArray.getJSONObject(i)));
            }

            JSONArray leagueArray = json.getJSONArray("league");
            for (int i = 0; i < leagueArray.length(); i++) {
                leagueBattles.add(new Match(leagueArray.getJSONObject(i)));
            }

            JSONArray regularArray = json.getJSONArray("regular");
            for (int i = 0; i < regularArray.length(); i++) {
                regularBattles.add(new Match(regularArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
    }

    private Match pick(List<Match> maches, LocalDateTime dateTime) {
        for (Match match : maches) {
            if (match.isLiveAt(dateTime)) {
                return match;
            }
        }
        throw new NoSuchElementException("No match found at " + dateTime);
    }

    public List<Match> getRankedBattles() {
        return rankedBattles;
    }

    public List<Match> getLeagueBattles() {
        return leagueBattles;
    }

    public List<Match> getRegularBattles() {
        return regularBattles;
    }

    public Match getRankedBattleAt(LocalDateTime dateTime) {
        return pick(rankedBattles, dateTime);
    }

    public Match getLeagueBattleAt(LocalDateTime dateTime) {
        return pick(leagueBattles, dateTime);
    }

    public Match getRegularBattleAt(LocalDateTime dateTime) {
        return pick(regularBattles, dateTime);
    }

    public Match getCurrentRankedBattle() {
        return pick(rankedBattles, LocalDateTime.now());
    }

    public Match getCurrentLeagueBattle() {
        return pick(leagueBattles, LocalDateTime.now());
    }

    public Match getCurrentRegularBattle() {
        return pick(regularBattles, LocalDateTime.now());
    }

    public Match getNextRankedBattle() {
        return pick(rankedBattles, LocalDateTime.now().plus(2, ChronoUnit.HOURS));
    }

    public Match getNextLeagueBattle() {
        return pick(leagueBattles, LocalDateTime.now().plus(2, ChronoUnit.HOURS));
    }

    public Match getNextRegularBattle() {
        return pick(regularBattles, LocalDateTime.now().plus(2, ChronoUnit.HOURS));
    }

}
