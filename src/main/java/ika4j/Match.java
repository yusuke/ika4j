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

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@SuppressWarnings("WeakerAccess")
public class Match {
    private final String rule;
    private final String stageA;
    private final String stageB;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public String getRule() {
        return rule;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Match(JSONObject gachi) throws JSONException {
        this.rule = gachi.getJSONObject("rule").getString("name");
        this.startTime = LocalDateTime.ofEpochSecond(gachi.getLong("start_time"), 0, ZoneOffset.of("+9"));
        this.endTime = LocalDateTime.ofEpochSecond(gachi.getLong("end_time"), 0, ZoneOffset.of("+9"));
        this.stageA = gachi.getJSONObject("stage_a").getString("name");
        this.stageB = gachi.getJSONObject("stage_b").getString("name");
    }

    boolean isLiveAt(LocalDateTime dateTime){
        return dateTime.isAfter(startTime) && dateTime.isBefore(endTime);
    }

    public String getStageA() {
        return stageA;
    }

    public String getStageB() {
        return stageB;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match Match = (Match) o;
        return Objects.equals(rule, Match.rule) &&
                Objects.equals(stageA, Match.stageA) &&
                Objects.equals(stageB, Match.stageB) &&
                Objects.equals(startTime, Match.startTime) &&
                Objects.equals(endTime, Match.endTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(rule, stageA, stageB, startTime, endTime);
    }

    @Override
    public String toString() {
        return "Match{" +
                "rule='" + rule + '\'' +
                ", stageA='" + stageA + '\'' +
                ", stageB='" + stageB + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
