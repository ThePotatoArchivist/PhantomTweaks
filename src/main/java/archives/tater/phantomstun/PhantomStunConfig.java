package archives.tater.phantomstun;

import eu.midnightdust.lib.config.MidnightConfig;

@SuppressWarnings("unused")
public class PhantomStunConfig extends MidnightConfig {
    @Entry public static boolean phantomStun = true;
    @Entry public static boolean phantomSizeTweak = true;
    @Entry public static boolean phantomSpawnHealthTweak = true;
    @Entry public static boolean phantomSpawnTimingTweak = true;
    @Entry public static boolean phantomSpawnCountTweak = true;
    @Comment public static Comment text1;
}
