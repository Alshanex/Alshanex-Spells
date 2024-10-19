package net.alshanex.alshanexspells.datagen;

import net.alshanex.alshanexspells.AlshanexSpellsMod;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifiersProvider(PackOutput output) {
        super(output, AlshanexSpellsMod.MOD_ID);
    }

    @Override
    protected void start() {

    }
}
