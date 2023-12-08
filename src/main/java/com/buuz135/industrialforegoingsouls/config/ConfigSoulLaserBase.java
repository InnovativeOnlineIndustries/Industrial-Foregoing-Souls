package com.buuz135.industrialforegoingsouls.config;

import com.hrznstudio.titanium.annotation.config.ConfigFile;
import com.hrznstudio.titanium.annotation.config.ConfigVal;

@ConfigFile.Child(IFSoulsMachines.class)
public class ConfigSoulLaserBase {

    @ConfigVal.InRangeInt(min = 1)
    @ConfigVal(comment = "Max soul storage tank amount")
    public static int SOUL_STORAGE_AMOUNT = 1350;

    @ConfigVal(comment = "Max progress of the machine")
    @ConfigVal.InRangeInt(min = 1)
    public static int MAX_PROGRESS = 20;

    @ConfigVal(comment = "Kill the warden when it's life reaches near to 0 or keep it alive")
    public static boolean KILL_WARDEN = false;

    @ConfigVal(comment = "Damage done to the warden when an operation is done")
    @ConfigVal.InRangeInt(min = 0)
    public static int DAMAGE_PER_OPERATION = 4;

    @ConfigVal(comment = "Souls generated when an operation is done")
    @ConfigVal.InRangeInt(min = 1)
    public static int SOULS_PER_OPERATION = 2;
}
