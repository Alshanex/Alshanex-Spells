package net.alshanex.devilfruitsmod.registry;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.alshanex.devilfruitsmod.spells.HiganSpell;
import net.alshanex.devilfruitsmod.spells.HikenSpell;
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

    public static final RegistryObject<AbstractSpell> HIGAN = registerSpell(new HiganSpell());
    public static final RegistryObject<AbstractSpell> HIKEN = registerSpell(new HikenSpell());

}
