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
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class Schedules {
    private List<マッチ> ガチマッチlist = new ArrayList<>();
    private List<マッチ> リーグマッチlist = new ArrayList<>();
    private List<マッチ> レギュラーマッチlist = new ArrayList<>();

    public Schedules(String iksmSession){
        String scheduleJSON = IKAAccessor.getSchedule(iksmSession);
        this.init(scheduleJSON);

    }
    public static Schedules fromRawJSON(String rawJSON){
        Schedules schedules = new Schedules();
        schedules.init(rawJSON);
        return schedules;
    }

    private Schedules(){}

    private void init(String rawJson)  {
        try {
            JSONObject json = new JSONObject(rawJson);
            JSONArray gachiArray = json.getJSONArray("gachi");
            for (int i = 0; i < gachiArray.length(); i++) {
                ガチマッチlist.add(new マッチ(gachiArray.getJSONObject(i)));
            }

            JSONArray leagueArray = json.getJSONArray("league");
            for (int i = 0; i < leagueArray.length(); i++) {
                リーグマッチlist.add(new マッチ(leagueArray.getJSONObject(i)));
            }

            JSONArray regularArray = json.getJSONArray("regular");
            for (int i = 0; i < regularArray.length(); i++) {
                レギュラーマッチlist.add(new マッチ(regularArray.getJSONObject(i)));
            }
        }catch(JSONException e){
            throw new IllegalStateException(e);
        }
    }

    private マッチ pick(List<マッチ> maches, LocalDateTime 日付時刻) {
        for (マッチ match : maches) {
            if (match.is開催期間(日付時刻)) {
                return match;
            }
        }
        throw new IllegalStateException("指定時刻のマッチがない");
    }

    private マッチ pickCurrent(List<マッチ> maches) {
        for (マッチ match : maches) {
            if (match.isCurrent()) {
                return match;
            }
        }
        throw new IllegalStateException("現在のマッチがない");
    }

    private マッチ pickNext(List<マッチ> maches) {
        for (マッチ match : maches) {
            if (match.isNext()) {
                return match;
            }
        }
        throw new IllegalStateException("ツギのマッチがない");
    }

    public List<マッチ> getガチマッチList() {
        return ガチマッチlist;
    }

    public List<マッチ> getリーグマッチList() {
        return リーグマッチlist;
    }

    public List<マッチ> getレギュラーマッチList() {
        return レギュラーマッチlist;
    }

    public マッチ getガチマッチ(LocalDateTime 指定日時) {
        return pick(ガチマッチlist, 指定日時);
    }

    public マッチ getリーグマッチ(LocalDateTime 指定日時) {
        return pick(リーグマッチlist, 指定日時);
    }

    public マッチ getレギュラーマッチ(LocalDateTime 指定日時) {
        return pick(レギュラーマッチlist, 指定日時);
    }


    public マッチ get現在のガチマッチ() {
        return pickCurrent(ガチマッチlist);
    }

    public マッチ get現在のリーグマッチ() {
        return pickCurrent(リーグマッチlist);
    }

    public マッチ get現在のレギュラーマッチ() {
        return pickCurrent(レギュラーマッチlist);
    }

    public マッチ get次のガチマッチ() {
        return pickNext(ガチマッチlist);
    }

    public マッチ get次のリーグマッチ() {
        return pickNext(ガチマッチlist);
    }

    public マッチ get次のレギュラーマッチ() {
        return pickNext(ガチマッチlist);
    }

}