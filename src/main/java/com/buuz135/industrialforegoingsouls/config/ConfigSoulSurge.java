package com.buuz135.industrialforegoingsouls.config;

import com.hrznstudio.titanium.annotation.config.ConfigFile;
import com.hrznstudio.titanium.annotation.config.ConfigVal;

@ConfigFile.Child(IFSoulsMachines.class)
public class ConfigSoulSurge {

    @ConfigVal.InRangeInt(min = 1)
    @ConfigVal(comment = "How long in ticks a soul last to accelerate ticks")
    public static int SOUL_TIME = 15 * 20;

    @ConfigVal(comment = "How many extra ticks the surge will accelerate")
    @ConfigVal.InRangeInt(min = 1)
    public static int ACCELERATION_TICK = 4;

}
