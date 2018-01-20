package ika4j;

import org.json.JSONException;

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
                () -> assertEquals("ガチエリア", schedules.getガチマッチ(日時).getルール名()),
                () -> assertEquals("ガンガゼ野外音楽堂", schedules.getガチマッチ(日時).getステージA()),
                () -> assertEquals("エンガワ河川敷", schedules.getガチマッチ(日時).getステージB()),

                () -> assertEquals("ガチアサリ", schedules.getリーグマッチ(日時).getルール名()),
                () -> assertEquals("マンタマリア号", schedules.getリーグマッチ(日時).getステージA()),
                () -> assertEquals("デボン海洋博物館", schedules.getリーグマッチ(日時).getステージB()),

                () -> assertEquals("ナワバリバトル", schedules.getレギュラーマッチ(日時).getルール名()),
                () -> assertEquals("Ｂバスパーク", schedules.getレギュラーマッチ(日時).getステージA()),
                () -> assertEquals("モズク農園", schedules.getレギュラーマッチ(日時).getステージB())
        );
    }
}
