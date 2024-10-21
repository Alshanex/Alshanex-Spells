package net.alshanex.alshanexspells.datagen;

import net.alshanex.alshanexspells.AlshanexSpellsMod;
import net.alshanex.alshanexspells.item.ModItems;
import net.alshanex.alshanexspells.loot.AddItemModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifiersProvider(PackOutput output) {
        super(output, AlshanexSpellsMod.MOD_ID);
    }

    @Override
    protected void start() {
        add("fire_fruit_from_buried_treasure", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/buried_treasure")).build(),
                LootItemRandomChanceCondition.randomChance(0.03f).build()}, ModItems.MERAMERA.get()));

        add("ice_fruit_from_buried_treasure", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/buried_treasure")).build(),
                LootItemRandomChanceCondition.randomChance(0.03f).build()}, ModItems.HIEHIE.get()));
    }
}
