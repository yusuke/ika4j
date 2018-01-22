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

public class Main {
    public static void main(String[] args) {
        String iksm_session = "………";
        // https://app.splatoon2.nintendo.net/api/schedules の情報を取得
        ika4j.Schedules schedules = new Ika4J(iksm_session).getSchedules();
        Battle 現在のガチマッチ = schedules.getCurrentRankedBattle();
        System.out.println(現在のガチマッチ.getRule().getName());
        System.out.println(現在のガチマッチ.getStageA().getName());
        System.out.println(現在のガチマッチ.getStageB().getName());
        Battle 現在のリーグマッチ = schedules.getCurrentLeagueBattle();
        System.out.println(現在のリーグマッチ.getRule().getName());
        System.out.println(現在のリーグマッチ.getStageA().getName());
        System.out.println(現在のリーグマッチ.getStageB().getName());

        for (Battle Battle : schedules.getRankedBattles()) {
            System.out.println(Battle.getRule().getName());
            System.out.println(Battle.getStartTime());
        }
    }
}
