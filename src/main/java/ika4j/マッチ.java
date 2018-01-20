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
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@SuppressWarnings("WeakerAccess")
public class マッチ {
    private final String ルール名;
    private final String ステージA;
    private final String ステージB;
    private final LocalDateTime 開始時間;
    private final LocalDateTime 終了時間;

    public String getルール名() {
        return ルール名;
    }

    public LocalDateTime get開始時間() {
        return 開始時間;
    }

    public マッチ(JSONObject gachi) throws JSONException {
        this.ルール名 = gachi.getJSONObject("rule").getString("name");
        this.開始時間 = LocalDateTime.ofEpochSecond(gachi.getLong("start_time"), 0, ZoneOffset.of("+9"));
        this.終了時間 = LocalDateTime.ofEpochSecond(gachi.getLong("end_time"), 0, ZoneOffset.of("+9"));
        this.ステージA = gachi.getJSONObject("stage_a").getString("name");
        this.ステージB = gachi.getJSONObject("stage_b").getString("name");
    }

    public boolean isCurrent() {
        return is開催期間(LocalDateTime.now());
    }

    public boolean isNext() {
        return is開催期間(LocalDateTime.now().plus(2, ChronoUnit.HOURS));
    }

    public boolean is開催期間(LocalDateTime 時間){
        return 時間.isAfter(開始時間) && 時間.isBefore(終了時間);
    }

    public String getステージA() {
        return ステージA;
    }

    public String getステージB() {
        return ステージB;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        マッチ マッチ = (マッチ) o;
        return Objects.equals(ルール名, マッチ.ルール名) &&
                Objects.equals(ステージA, マッチ.ステージA) &&
                Objects.equals(ステージB, マッチ.ステージB) &&
                Objects.equals(開始時間, マッチ.開始時間) &&
                Objects.equals(終了時間, マッチ.終了時間);
    }

    @Override
    public int hashCode() {

        return Objects.hash(ルール名, ステージA, ステージB, 開始時間, 終了時間);
    }

    @Override
    public String toString() {
        return "マッチ{" +
                "ルール名='" + ルール名 + '\'' +
                ", ステージA='" + ステージA + '\'' +
                ", ステージB='" + ステージB + '\'' +
                ", 開始時間=" + 開始時間 +
                ", 終了時間=" + 終了時間 +
                '}';
    }
}
