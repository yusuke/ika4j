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
        LocalDateTime dateTime = LocalDateTime.of(2018, 1, 20, 10, 30);
        Battle rankedBattle = schedules.getRankedBattleAt(dateTime);
        Battle leagueBattle = schedules.getLeagueBattleAt(dateTime);
        Battle regularBattle = schedules.getRegularBattleAt(dateTime);
        assertAll(
                () -> assertEquals("ガチエリア", rankedBattle.getRule().getName()),
                () -> assertEquals("gachi", rankedBattle.getGameMode().getKey()),
                () -> assertEquals("ガチマッチ", rankedBattle.getGameMode().getName()),
                () -> assertEquals("ガンガゼ野外音楽堂", rankedBattle.getStageA().getName()),
                () -> assertEquals(2, rankedBattle.getStageA().getId()),
                () -> assertEquals("エンガワ河川敷", rankedBattle.getStageB().getName()),
                () -> assertEquals(9, rankedBattle.getStageB().getId()),

                () -> assertEquals("ガチアサリ", leagueBattle.getRule().getName()),
                () -> assertEquals("league", leagueBattle.getGameMode().getKey()),
                () -> assertEquals("リーグマッチ", leagueBattle.getGameMode().getName()),
                () -> assertEquals("マンタマリア号", leagueBattle.getStageA().getName()),
                () -> assertEquals(6, leagueBattle.getStageA().getId()),
                () -> assertEquals("デボン海洋博物館", leagueBattle.getStageB().getName()),
                () -> assertEquals(12, leagueBattle.getStageB().getId()),

                () -> assertEquals("ナワバリバトル", regularBattle.getRule().getName()),
                () -> assertEquals("regular", regularBattle.getGameMode().getKey()),
                () -> assertEquals("レギュラーマッチ", regularBattle.getGameMode().getName()),
                () -> assertEquals("Ｂバスパーク", regularBattle.getStageA().getName()),
                () -> assertEquals(11, regularBattle.getStageA().getId()),
                () -> assertEquals("モズク農園", regularBattle.getStageB().getName()),
                () -> assertEquals(10, regularBattle.getStageB().getId())
        );
    }
}
