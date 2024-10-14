package net.alshanex.devilfruitsmod.effect;

import net.alshanex.devilfruitsmod.DevilFruitsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECT_DEFERRED_REGISTER = DeferredRegister.create(Registries.MOB_EFFECT, DevilFruitsMod.MOD_ID);

    public static void register(IEventBus eventBus) {
        MOB_EFFECT_DEFERRED_REGISTER.register(eventBus);
    }

    public static final RegistryObject<MobEffect> MERA_LOGIA_EFFECT = MOB_EFFECT_DEFERRED_REGISTER.register("mera_logia", () -> new MeraLogiaEffect(MobEffectCategory.BENEFICIAL, 0x9f0be3));
    public static final RegistryObject<MobEffect> ICE_LOGIA_EFFECT = MOB_EFFECT_DEFERRED_REGISTER.register("ice_logia", () -> new HieLogiaEffect(MobEffectCategory.BENEFICIAL, 0x9f0be3));
}
