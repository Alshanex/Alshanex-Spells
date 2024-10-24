package net.alshanex.alshanexspells.setup;

import net.alshanex.alshanexspells.AlshanexSpellsMod;
import net.alshanex.alshanexspells.block.ModBlocks;
import net.alshanex.alshanexspells.entity.ModEntities;
import net.alshanex.alshanexspells.entity.renderers.FlowerEntityRenderer;
import net.alshanex.alshanexspells.entity.renderers.FrozenEntityRenderer;
import net.alshanex.alshanexspells.entity.renderers.HikenEntityRenderer;
import net.alshanex.alshanexspells.entity.renderers.PawRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AlshanexSpellsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
    @SubscribeEvent
    public static void rendererRegister(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.HIKEN_ENTITY.get(), (context) -> new HikenEntityRenderer(context, 2f));
        event.registerEntityRenderer(ModEntities.HIBASHIRA_ENTITY.get(), (context) -> new HikenEntityRenderer(context, 5f));
        event.registerEntityRenderer(ModEntities.FROZEN_ENTITY.get(), FrozenEntityRenderer::new);
        event.registerEntityRenderer(ModEntities.SPORE_BLOSSOM_ENTITY.get(), FlowerEntityRenderer::new);
        event.registerEntityRenderer(ModEntities.PAW_ENTITY.get(), PawRenderer::new);
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ICE_SURFACE_BLOCK.get(), RenderType.translucent());
    }
}
