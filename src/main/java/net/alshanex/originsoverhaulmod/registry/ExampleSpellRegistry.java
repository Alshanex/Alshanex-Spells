package net.alshanex.originsoverhaulmod.registry;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.alshanex.originsoverhaulmod.spells.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
public class ExampleSpellRegistry {
    public static final ResourceKey<Registry<AbstractSpell>> SPELL_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(IronsSpellbooks.MODID, "spells"));
    private static final DeferredRegister<AbstractSpell> SPELLS = DeferredRegister.create(SPELL_REGISTRY_KEY, IronsSpellbooks.MODID);
    public static void register(IEventBus eventBus) {
        SPELLS.register(eventBus);
    }

    private static RegistryObject<AbstractSpell> registerSpell(AbstractSpell spell) {
        return SPELLS.register(spell.getSpellName(), () -> spell);
    }
    public static final RegistryObject<AbstractSpell> WATER_SLASH = registerSpell(new WaterSlash());
    public static final RegistryObject<AbstractSpell> WATER_CUT = registerSpell(new WaterCut());
    public static final RegistryObject<AbstractSpell> ENDER_STOMP = registerSpell(new EnderStomp());
    public static final RegistryObject<AbstractSpell> PETRIFY = registerSpell(new Petrify());
    public static final RegistryObject<AbstractSpell> AERIAL_DASH = registerSpell(new AerialDash());
    public static final RegistryObject<AbstractSpell> FIRE_FLOWER = registerSpell(new FireFlowerSpell());
    public static final RegistryObject<AbstractSpell> THUNDER_FLOWER = registerSpell(new ThunderFlowerSpell());
    public static final RegistryObject<AbstractSpell> ENDER_FLOWER = registerSpell(new EnderFlowerSpell());
    public static final RegistryObject<AbstractSpell> SHIELD_FLOWER = registerSpell(new ShieldFlowerSpell());
}
