package net.alshanex.devilfruitsmod.entity;

import net.alshanex.devilfruitsmod.DevilFruitsMod;
import net.alshanex.devilfruitsmod.entity.custom.HikenEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, DevilFruitsMod.MOD_ID);

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

    public static final RegistryObject<EntityType<HikenEntity>> HIKEN_ENTITY =
            ENTITY_TYPES.register("hiken", () -> EntityType.Builder.<HikenEntity>of(HikenEntity::new, MobCategory.MISC)
                    .sized(2f, 2f)
                    .clientTrackingRange(4)
                    .build(new ResourceLocation(DevilFruitsMod.MOD_ID, "hiken").toString()));
}
