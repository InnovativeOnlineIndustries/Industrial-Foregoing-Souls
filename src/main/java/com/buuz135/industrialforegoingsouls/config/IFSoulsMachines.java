package com.buuz135.industrialforegoingsouls.config;

import com.hrznstudio.titanium.annotation.config.ConfigFile;
import com.hrznstudio.titanium.annotation.config.ConfigVal;

@ConfigFile(value = "industrialforegoing-souls-machines")
public class IFSoulsMachines {

    @ConfigVal(comment = "How many souls each pipe can hold")
    @ConfigVal.InRangeInt(min = 0)
    public static int SOUL_AMOUNT_PER_PIPE = 4;
}
