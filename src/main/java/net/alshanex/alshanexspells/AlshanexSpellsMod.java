package net.alshanex.alshanexspells;

import net.alshanex.alshanexspells.block.ModBlocks;
import net.alshanex.alshanexspells.effect.ModEffects;
import net.alshanex.alshanexspells.entity.ModEntities;
import net.alshanex.alshanexspells.item.ModItems;
import net.alshanex.alshanexspells.loot.ModLootModifiers;
import net.alshanex.alshanexspells.registry.ExampleSpellRegistry;
import net.alshanex.alshanexspells.registry.SoundRegistry;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(AlshanexSpellsMod.MOD_ID)
public class AlshanexSpellsMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "alshanexspells";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogManager.getLogger();
    public AlshanexSpellsMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);

        ModBlocks.register(modEventBus);

        ModEffects.register(modEventBus);

        ExampleSpellRegistry.register(modEventBus);

        ModEntities.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        ModLootModifiers.register(modEventBus);

        SoundRegistry.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if(event.getTabKey() == CreativeModeTabs.COMBAT){
            event.accept(ModItems.MERAMERA);
            event.accept(ModItems.HIEHIE);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }
    }
}
