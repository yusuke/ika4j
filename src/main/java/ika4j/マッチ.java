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
