package net.alshanex.alshanexspells.setup;


import net.alshanex.alshanexspells.AlshanexSpellsMod;
import net.alshanex.alshanexspells.entity.ModEntities;
import net.alshanex.alshanexspells.entity.custom.FrozenEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AlshanexSpellsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonSetup {
    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event) {
        event.put(ModEntities.FROZEN_ENTITY.get(), FrozenEntity.frozenEntity().build());
    }
}
