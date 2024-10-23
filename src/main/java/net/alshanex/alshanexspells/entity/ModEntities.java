package net.alshanex.alshanexspells.entity;

import net.alshanex.alshanexspells.AlshanexSpellsMod;
import net.alshanex.alshanexspells.entity.custom.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, AlshanexSpellsMod.MOD_ID);

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

    public static final RegistryObject<EntityType<HikenEntity>> HIKEN_ENTITY =
            ENTITY_TYPES.register("hiken", () -> EntityType.Builder.<HikenEntity>of(HikenEntity::new, MobCategory.MISC)
                    .sized(2f, 2f)
                    .clientTrackingRange(4)
                    .build(new ResourceLocation(AlshanexSpellsMod.MOD_ID, "hiken").toString()));

    public static final RegistryObject<EntityType<HibashiraEntity>> HIBASHIRA_ENTITY =
            ENTITY_TYPES.register("hibashira", () -> EntityType.Builder.<HibashiraEntity>of(HibashiraEntity::new, MobCategory.MISC)
                    .sized(4f, .8f)
                    .clientTrackingRange(64)
                    .build(new ResourceLocation(AlshanexSpellsMod.MOD_ID, "hibashira").toString()));

    public static final RegistryObject<EntityType<FrozenEntity>> FROZEN_ENTITY =
            ENTITY_TYPES.register("frozen_entity", () -> EntityType.Builder.<FrozenEntity>of(FrozenEntity::new, MobCategory.CREATURE)
                    .sized(.5f, .5f)
                    .clientTrackingRange(64)
                    .build(new ResourceLocation(AlshanexSpellsMod.MOD_ID, "frozen_entity").toString()));

    public static final RegistryObject<EntityType<FlowerEntity>> SPORE_BLOSSOM_ENTITY =
            ENTITY_TYPES.register("flower_entity", () -> EntityType.Builder.<FlowerEntity>of(FlowerEntity::new, MobCategory.MISC)
                    .sized(1.25f, 1)
                    .clientTrackingRange(64)
                    .build(new ResourceLocation(AlshanexSpellsMod.MOD_ID, "flower_entity").toString()));

    public static final RegistryObject<EntityType<PawEntity>> PAW_ENTITY =
            ENTITY_TYPES.register("paw_entity", () -> EntityType.Builder.<PawEntity>of(PawEntity::new, MobCategory.MISC)
                    .sized(1.2f, 1.5f)
                    .clientTrackingRange(64)
                    .build(new ResourceLocation(AlshanexSpellsMod.MOD_ID, "paw_entity").toString()));
}
