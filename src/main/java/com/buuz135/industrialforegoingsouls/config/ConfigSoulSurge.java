package com.buuz135.industrialforegoingsouls.config;

import com.hrznstudio.titanium.annotation.config.ConfigFile;
import com.hrznstudio.titanium.annotation.config.ConfigVal;

@ConfigFile.Child(IFSoulsMachines.class)
public class ConfigSoulSurge {

    @ConfigVal.InRangeInt(min = 1)
    @ConfigVal(comment = "How long in ticks a soul last to accelerate ticks")
    public static int SOUL_TIME = 15 * 20;

    @ConfigVal(comment = "How many extra ticks the surge will accelerate for tile entities")
    @ConfigVal.InRangeInt(min = 0)
    public static int ACCELERATION_TICK = 4;

    @ConfigVal(comment = "How many extra ticks the surge will accelerate for mobs")
    @ConfigVal.InRangeInt(min = 0)
    public static int ENTITIES_ACCELERATION_TICK = 4;

    @ConfigVal(comment = "How many extra ticks the surge will accelerate for blocks")
    @ConfigVal.InRangeInt(min = 0)
    public static int BLOCK_ACCELERATION_TICK = 4;

    @ConfigVal(comment = "How often a random tick block will be accelerated, by default 3% of the ticks (random)")
    @ConfigVal.InRangeInt(min = 0, max = 1)
    public static double RANDOM_TICK_ACCELERATION_CHANCE = 0.03;
}
