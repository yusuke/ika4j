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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SchedulesTest {

    @org.junit.jupiter.api.Test
    void get現在のガチマッチ() {
        String schedulesJSON;
        try (Stream<String> s = new BufferedReader(new InputStreamReader(SchedulesTest.class.getResourceAsStream("/schedules.json"))).lines()) {
            schedulesJSON = s.collect(Collectors.joining());
        }

        Schedules schedules = Schedules.fromRawJSON(schedulesJSON);
        LocalDateTime 日時 = LocalDateTime.of(2018, 1, 20, 10, 30);
        assertAll(
                () -> assertEquals("ガチエリア", schedules.getRankedBattleAt(日時).getRule().getName()),
                () -> assertEquals("ガンガゼ野外音楽堂", schedules.getRankedBattleAt(日時).getStageA()),
                () -> assertEquals("エンガワ河川敷", schedules.getRankedBattleAt(日時).getStageB()),

                () -> assertEquals("ガチアサリ", schedules.getLeagueBattleAt(日時).getRule().getName()),
                () -> assertEquals("マンタマリア号", schedules.getLeagueBattleAt(日時).getStageA()),
                () -> assertEquals("デボン海洋博物館", schedules.getLeagueBattleAt(日時).getStageB()),

                () -> assertEquals("ナワバリバトル", schedules.getRegularBattleAt(日時).getRule().getName()),
                () -> assertEquals("Ｂバスパーク", schedules.getRegularBattleAt(日時).getStageA()),
                () -> assertEquals("モズク農園", schedules.getRegularBattleAt(日時).getStageB())
        );
    }
}
