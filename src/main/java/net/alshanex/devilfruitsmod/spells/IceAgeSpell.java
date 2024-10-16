package net.alshanex.devilfruitsmod.spells;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import io.redspace.ironsspellbooks.entity.mobs.frozen_humanoid.FrozenHumanoid;
import io.redspace.ironsspellbooks.network.spell.ClientboundFrostStepParticles;
import io.redspace.ironsspellbooks.network.spell.ClientboundParticleShockwave;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.setup.Messages;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.alshanex.devilfruitsmod.block.ModBlocks;
import net.alshanex.devilfruitsmod.datagen.EntityTagGenerator;
import net.alshanex.devilfruitsmod.entity.custom.FrozenEntity;
import net.alshanex.devilfruitsmod.util.DFUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@AutoSpellConfig
public class IceAgeSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(IronsSpellbooks.MODID, "ice_age");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.damage", Utils.stringTruncation(getDamage(spellLevel, caster), 2)),
                Component.translatable("ui.irons_spellbooks.radius", Utils.stringTruncation(getRadius(spellLevel, caster), 2))
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.LEGENDARY)
            .setSchoolResource(SchoolRegistry.ICE_RESOURCE)
            .setMaxLevel(1)
            .setCooldownSeconds(60)
            .build();

    public IceAgeSpell() {
        this.manaCostPerLevel = 5;
        this.baseSpellPower = 10;
        this.spellPowerPerLevel = 3;
        this.castTime = 20;
        this.baseManaCost = 150;
    }

    @Override
    public CastType getCastType() {
        return CastType.LONG;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    @Override
    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of(SoundRegistry.FROSTWAVE_PREPARE.get());
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        float radius = getRadius(spellLevel, entity);
        MagicManager.spawnParticles(level, new BlastwaveParticleOptions(SchoolRegistry.ICE.get().getTargetingColor(), radius), entity.getX(), entity.getY() + .165f, entity.getZ(), 1, 0, 0, 0, 0, true);
        Messages.sendToPlayersTrackingEntity(new ClientboundParticleShockwave(new Vec3(entity.getX(), entity.getY() + .165f, entity.getZ()), radius, ParticleRegistry.SNOWFLAKE_PARTICLE.get()), entity, true);
        level.getEntities(entity, entity.getBoundingBox().inflate(radius, 4, radius), (target) -> !DamageSources.isFriendlyFireBetween(target, entity) && Utils.hasLineOfSight(level, entity, target, true)).forEach(target -> {
            if (target instanceof LivingEntity livingEntity && livingEntity.distanceToSqr(entity) < radius * radius && !DamageSources.isFriendlyFireBetween(target, entity)) {
                DamageSources.applyDamage(livingEntity, getDamage(spellLevel, entity), getDamageSource(entity));
                livingEntity.addEffect(new MobEffectInstance(MobEffectRegistry.CHILLED.get(), getDuration(spellLevel, entity)));
                MagicManager.spawnParticles(level, ParticleHelper.SNOWFLAKE, livingEntity.getX(), livingEntity.getY() + livingEntity.getBbHeight() * .5f, livingEntity.getZ(), 50, livingEntity.getBbWidth() * .5f, livingEntity.getBbHeight() * .5f, livingEntity.getBbWidth() * .5f, .03, false);

                if((livingEntity.getHealth() < (livingEntity.getMaxHealth() * 0.1) || livingEntity.getHealth() <= getDamage(spellLevel, entity))){
                    if(!(livingEntity instanceof Player) && livingEntity.getType().is(EntityTagGenerator.FREEZEABLE_ENTITIES)){
                        FrozenEntity frozenEntity = FrozenEntity.buildFrozenEntity(livingEntity, entity);
                        frozenEntity.absMoveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), livingEntity.getYRot(), livingEntity.getXRot());
                        frozenEntity.yBodyRot = livingEntity.getYRot();
                        frozenEntity.setShatterDamage(getDamage(spellLevel, entity));
                        frozenEntity.setDeathTimer(200);
                        livingEntity.addEffect(new MobEffectInstance(MobEffectRegistry.TRUE_INVISIBILITY.get(), Integer.MAX_VALUE, 0, false, false));
                        livingEntity.kill();
                        livingEntity.remove(Entity.RemovalReason.KILLED);
                        level.addFreshEntity(frozenEntity);
                    }
                }
            }
        });

        BlockPos center = entity.blockPosition();
        Set<Block> excludedBlocks = DFUtils.nonFreezeableBlocks();
        Set<Block> iceableBlocks = DFUtils.iceableBlocks();

        for (int x = (int) Math.floor(-radius); x <= Math.ceil(radius); x++) {
            for (int y = -3; y <= 5; y++) {
                for (int z = (int) Math.floor(-radius); z <= Math.ceil(radius); z++) {
                    BlockPos currentPos = center.offset(x, y, z);
                    var blockState = level.getBlockState(currentPos);

                    double distance = Math.sqrt(x * x + z * z);

                    boolean isAboveCenter = currentPos.equals(center.above()) || currentPos.equals(center.above(2));

                    if (distance <= radius && !excludedBlocks.contains(blockState.getBlock()) &&
                            blockState.getBlock() != Blocks.WATER && !blockState.isAir() && blockState.getBlock() != ModBlocks.ICE_SURFACE_BLOCK.get()
                    && blockState.getBlock() != Blocks.AIR) {

                        for (Direction direction : Direction.values()) {
                            BlockPos adjacentPos = currentPos.relative(direction);
                            var adjacentState = level.getBlockState(adjacentPos);

                            if (adjacentState.isAir() || iceableBlocks.contains(adjacentState.getBlock())) {
                                BlockState iceSurfaceState = ModBlocks.ICE_SURFACE_BLOCK.get().defaultBlockState()
                                        .setValue(MultifaceBlock.getFaceProperty(direction.getOpposite()), true);

                                level.setBlockAndUpdate(adjacentPos, iceSurfaceState);
                            } else if (adjacentState.getBlock() == ModBlocks.ICE_SURFACE_BLOCK.get()) {
                                BlockState updatedState = adjacentState.setValue(MultifaceBlock.getFaceProperty(direction.getOpposite()), true);
                                level.setBlockAndUpdate(adjacentPos, updatedState);
                            }
                        }

                    } else if (distance <= radius && blockState.getBlock() == Blocks.WATER && !isAboveCenter) {
                        level.setBlockAndUpdate(currentPos, Blocks.ICE.defaultBlockState());
                    }
                }
            }
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    public float getRadius(int spellLevel, LivingEntity caster) {
        return 6 + spellLevel * 10 * .75f;
    }

    @Override
    public SpellDamageSource getDamageSource(@Nullable Entity projectile, Entity attacker) {
        return super.getDamageSource(projectile, attacker).setFreezeTicks(100);
    }

    private float getDamage(int spellLevel, LivingEntity caster) {
        return this.getSpellPower(spellLevel, caster) * 0.6f;
    }

    public int getDuration(int spellLevel, LivingEntity caster) {
        return (int) (getSpellPower(spellLevel, caster) * 20);
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.CHARGE_RAISED_HAND;
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return SpellAnimations.TOUCH_GROUND_ANIMATION;
    }
}
