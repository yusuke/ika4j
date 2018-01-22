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

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@SuppressWarnings("WeakerAccess")
public class Ika4J {
    private final IKAAccessor ikaAccessor;

    public Ika4J(String iksmSession) {
        ikaAccessor = new IKAAccessor(iksmSession);
    }

    private LocalDateTime lastCachedDateTime = LocalDateTime.of(2017,7,21,0,0);
    private Schedules cachedSchedules = null;
    String cachedSchedulesJSON = null;
    public Schedules getSchedules() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        // call the api only when
        if (cachedSchedules == null || now.isAfter(lastCachedDateTime)) {
            lastCachedDateTime = now;
            cachedSchedulesJSON = ikaAccessor.getScheduleJSON();
            cachedSchedules =  new Schedules(cachedSchedulesJSON);
        }
        return cachedSchedules;
    }
}
