package me.bubbles.bosspve.utility;

import java.time.Instant;

public class UtilTime {

    public static long getEpochTimestamp() {
        return Instant.now().getEpochSecond();
    }

}
