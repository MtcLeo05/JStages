package com.leo.jstages.plugins.redimstages;

import com.cpearl.redimstages.DimensionRestriction;
import com.cpearl.redimstages.ReDimensionStages;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;

public class DimensionStagesKJS extends KubeJSPlugin {

    @Override
    public void registerBindings(BindingsEvent event) {
        if (!event.getType().isServer()) return;
        if (!ModList.get().isLoaded("redimstages")) return;

        event.add("DimensionStages", DimStagesMethods.class);
    }

    public interface DimStagesMethods {

        static void restrictDimensionNoMessage(String dimension, String... stages) {
            restrictDimension(dimension, null, stages);
        }

        static void restrictDimension(String dimension, Component message, String... stages) {
            if(stages.length == 0)
                throw new IllegalArgumentException("[JStages DimensionStages]: Cannot create a restriction with 0 stages!");

            if(ResourceLocation.tryParse(dimension) == null)
                throw new IllegalArgumentException("[JStages DimensionStages]: Dimension " + dimension + " is not a valid Id!");

            DimensionRestriction restriction = ReDimensionStages.RESTRICTIONS.addRestriction(ResourceLocation.parse(dimension), new DimensionRestriction());

            for (String stage : stages) {
                restriction.addStage(stage);
            }

            if(message != null) restriction.restrictionMessage = message;
        }

    }
}
