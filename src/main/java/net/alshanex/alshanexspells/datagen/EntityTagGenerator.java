package net.alshanex.alshanexspells.datagen;

import io.redspace.ironsspellbooks.entity.mobs.MagicSummon;
import net.alshanex.alshanexspells.AlshanexSpellsMod;
import net.alshanex.alshanexspells.util.AUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class EntityTagGenerator extends EntityTypeTagsProvider {
    public static TagKey<EntityType<?>> FREEZEABLE_ENTITIES = createKey("freezeable_entities");

    public EntityTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable final ExistingFileHelper existingFileHelper) {
        super(output, provider, AlshanexSpellsMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        BuiltInRegistries.ENTITY_TYPE.stream()
                .filter(entityType -> entityType.getCategory() != MobCategory.MISC)
                .filter(entityType -> !entityType.getBaseClass().isAssignableFrom(MagicSummon.class))
                .filter(entityType -> !AUtils.isNonFreezeable(entityType))
                .forEach(entityType -> tag(FREEZEABLE_ENTITIES).add(entityType));

        tag(FREEZEABLE_ENTITIES)
                .add(EntityType.VILLAGER)
                .add(EntityType.IRON_GOLEM);
    }

    private static TagKey<EntityType<?>> createKey(final String name) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(AlshanexSpellsMod.MOD_ID, name));
    }
}
