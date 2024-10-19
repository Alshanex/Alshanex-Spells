package net.alshanex.alshanexspells.item.custom;

import com.google.common.collect.ImmutableMultimap;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.item.UniqueSpellBook;
import net.alshanex.alshanexspells.registry.ExampleSpellRegistry;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

public class MeraMeraItem extends UniqueSpellBook {
    public MeraMeraItem() {
        super(SpellRarity.LEGENDARY, SpellDataRegistryHolder.of(
                new SpellDataRegistryHolder(ExampleSpellRegistry.HIKEN, 5),
                new SpellDataRegistryHolder(ExampleSpellRegistry.HIBASHIRA, 10)
        ), 0, () -> {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(AttributeRegistry.FIRE_SPELL_POWER.get(), new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", .10, AttributeModifier.Operation.MULTIPLY_BASE));
            builder.put(AttributeRegistry.MAX_MANA.get(), new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", 200, AttributeModifier.Operation.ADDITION));
            return builder.build();
        });
    }
}
