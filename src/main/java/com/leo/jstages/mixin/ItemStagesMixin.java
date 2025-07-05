package com.leo.jstages.mixin;

import net.darkhax.itemstages.ItemStages;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStages.class)
public abstract class ItemStagesMixin {

    @Inject(method = "onItemTooltip", at = @At(value = "HEAD"), remap = false, cancellable = true)
    public void jstages$onItemTooltip(ItemTooltipEvent event, CallbackInfo ci) {
        /*if (event.getEntity() == null) {
            ci.cancel();
            return;
        }

        Player player = event.getEntity();
        IStageData data = GameStageSaveHandler.getClientData();
        ItemStack stack = event.getItemStack();
        Restriction restriction = RestrictionManager.INSTANCE.getRestriction(player, data, stack);
        if (restriction == null) {
            ci.cancel();
            return;
        }

        List<Component> tooltips = event.getToolTip();
        tooltips.clear();

        Component hiddenName = restriction.getHiddenName(stack);

        if (hiddenName != null) {
            tooltips.add(hiddenName);
        }*/

        ci.cancel();
    }

}
