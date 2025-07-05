package com.leo.jstages.plugins.item_stages;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import net.darkhax.itemstages.Restriction;
import net.darkhax.itemstages.RestrictionManager;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Predicate;

public class ItemStagesKJS extends KubeJSPlugin {

    @Override
    public void registerBindings(BindingsEvent event) {
        if (!event.getType().isServer() && !event.getType().isClient()) return;
        if (!ModList.get().isLoaded("itemstages")) return;

        event.add("ItemStages", ItemStagesMethods.class);
    }

    public interface ItemStagesMethods {

        static Restriction restrictEnchantment(Enchantment enchantment, String... stages) {
            return restrictEnchantment(enchantment, true, true, stages);
        }

        static Restriction restrictEnchantment(Enchantment enchantment, boolean checkBook, boolean checkItem, String... stages) {
            return restrictItemInternal(item -> checkItemEnchantment(item, enchantment, checkBook, checkItem), stages);
        }

        static Restriction restrictMod(String modid, String... stages) {
            return restrictMod(modid, null, stages);
        }

        static Restriction restrictMod(String modid, Predicate<ItemStack> toIgnore, String... stages) {
            if(toIgnore == null) return restrictItemInternal(item -> modid.equalsIgnoreCase(ForgeRegistries.ITEMS.getKey(item.getItem()).getNamespace()), stages);
            return restrictItemInternal(item -> modid.equalsIgnoreCase(ForgeRegistries.ITEMS.getKey(item.getItem()).getNamespace()) && !toIgnore.test(item), stages);
        }

        static Restriction restrictRarity(Rarity rarity, String... stages) {
            return restrictItemInternal(item -> item.getRarity() == rarity, stages);
        }

        static Restriction restrictItem(InputItem item, String... stages) {
            return restrictItemInternal(item.ingredient, stages);
        }

        static Restriction restrictIngredient(Ingredient ingredient, String... stages) {
            return restrictItemInternal(ingredient, stages);
        }

        private static Restriction restrictItemInternal(Predicate<ItemStack> item, String... stages) {
            return createRestriction(stages).restrict(item);
        }

        static Restriction createRestriction(String... stages) {
            if (stages.length == 0)
                throw new IllegalArgumentException("[JStages ItemStages]: Cannot create a restriction with 0 stages!");

            Restriction restriction = new Restriction(stages);
            RestrictionManager.INSTANCE.addRestriction(restriction);
            return restriction;
        }

        private static boolean checkItemEnchantment(ItemStack stack, Enchantment enchantment, boolean checkBook, boolean checkItem) {
            if (!stack.hasTag()) return false;

            if (checkBook && stack.getItem() instanceof EnchantedBookItem && EnchantmentHelper.getEnchantments(stack).getOrDefault(enchantment, 0) > 0) {
                return true;
            }

            return checkItem && stack.getEnchantmentLevel(enchantment) > 0;
        }
    }
}
