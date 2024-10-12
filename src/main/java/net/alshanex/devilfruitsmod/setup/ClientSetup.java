package net.alshanex.devilfruitsmod.setup;

import net.alshanex.devilfruitsmod.DevilFruitsMod;
import net.alshanex.devilfruitsmod.entity.ModEntities;
import net.alshanex.devilfruitsmod.entity.custom.HikenEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DevilFruitsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
    @SubscribeEvent
    public static void rendererRegister(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.HIKEN_ENTITY.get(), (context) -> new HikenEntityRenderer(context, 2f));
        event.registerEntityRenderer(ModEntities.HIBASHIRA_ENTITY.get(), (context) -> new HikenEntityRenderer(context, 5f));
    }
}
