package com.leo.jstages;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(JStages.MODID)
public class JStages {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "jstages";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public JStages() {

    }
}
