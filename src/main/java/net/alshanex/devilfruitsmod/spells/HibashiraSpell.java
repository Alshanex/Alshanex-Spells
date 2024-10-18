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
import io.redspace.ironsspellbooks.entity.spells.magma_ball.FireField;
import io.redspace.ironsspellbooks.entity.spells.target_area.TargetedAreaEntity;
import io.redspace.ironsspellbooks.network.spell.ClientboundParticleShockwave;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.setup.Messages;
import io.redspace.ironsspellbooks.spells.TargetAreaCastData;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.alshanex.devilfruitsmod.entity.custom.HibashiraEntity;
import net.alshanex.devilfruitsmod.util.DFSpellAnimations;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.List;
import java.util.Optional;

import static io.redspace.ironsspellbooks.api.util.Utils.random;

@AutoSpellConfig
public class HibashiraSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(IronsSpellbooks.MODID, "hibashira");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.damage", Utils.stringTruncation(getDamage(spellLevel, caster), 2)),
                Component.translatable("ui.irons_spellbooks.radius", Utils.stringTruncation(getRadius(caster), 1))
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.UNCOMMON)
            .setSchoolResource(SchoolRegistry.FIRE_RESOURCE)
            .setMaxLevel(10)
            .setCooldownSeconds(60)
            .build();

    public HibashiraSpell() {
        this.manaCostPerLevel = 5;
        this.baseSpellPower = 8;
        this.spellPowerPerLevel = 1;
        this.castTime = 50 - 5;
        this.baseManaCost = 100;

    }

    @Override
    public int getCastTime(int spellLevel) {
        return (castTime + 5 * spellLevel) * 2;
    }

    @Override
    public CastType getCastType() {
        return CastType.CONTINUOUS;
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
        return Optional.of(SoundRegistry.SCORCH_PREPARE.get());
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        Vec3 targetArea = entity.position();
        float radius = getRadius(entity);
        var radiusSqr = radius * radius;
        var damage = getDamage(spellLevel, entity);
        var source = getDamageSource(entity);
        level.getEntitiesOfClass(LivingEntity.class, new AABB(targetArea.subtract(radius, radius, radius), targetArea.add(radius, radius, radius)),
                        livingEntity -> livingEntity != entity &&
                                horizontalDistanceSqr(livingEntity, targetArea) < radiusSqr &&
                                livingEntity.isPickable() &&
                                !DamageSources.isFriendlyFireBetween(livingEntity, entity) &&
                                Utils.hasLineOfSight(level, targetArea.add(0, 1.5, 0), livingEntity.getBoundingBox().getCenter(), true))
                .forEach(livingEntity -> {
                    DamageSources.applyDamage(livingEntity, damage, source);
                    DamageSources.ignoreNextKnockback(livingEntity);
                });

        HibashiraEntity fire = new HibashiraEntity(level);
        fire.setOwner(entity);
        fire.setDuration(200);
        fire.setDamage(damage * .1f);
        fire.setRadius(radius);
        fire.setCircular();
        fire.moveTo(targetArea.x, targetArea.y, targetArea.z);
        level.addFreshEntity(fire);

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    @Override
    public void onServerCastTick(Level level, int spellLevel, LivingEntity entity, @Nullable MagicData playerMagicData) {
        var radius = getRadius(entity);
        Vec3 targetArea = entity.position();

        if (playerMagicData != null && (playerMagicData.getCastDurationRemaining() + 1) % 20 == 0) {
            level.playSound(null, targetArea.x, targetArea.y, targetArea.z, SoundRegistry.FIRE_CAST.get(), SoundSource.PLAYERS, 2, random.nextIntBetweenInclusive(8, 12) * .1f);
        }

        if (playerMagicData != null && (playerMagicData.getCastDurationRemaining() + 1) % 40 == 0) {
            MagicManager.spawnParticles(level, new BlastwaveParticleOptions(SchoolRegistry.FIRE.get().getTargetingColor(), radius * 2), entity.getX(), entity.getY() + .165f, entity.getZ(), 1, 0, 0, 0, 0, true);
            Messages.sendToPlayersTrackingEntity(new ClientboundParticleShockwave(new Vec3(entity.getX(), entity.getY() + .165f, entity.getZ()), radius * 2, ParticleRegistry.FIRE_PARTICLE.get()), entity, true);
            level.getEntities(entity, entity.getBoundingBox().inflate(radius * 2, 4, radius * 2), (target) -> !DamageSources.isFriendlyFireBetween(target, entity) && Utils.hasLineOfSight(level, entity, target, true)).forEach(target -> {
                if (target instanceof LivingEntity livingEntity && livingEntity.distanceToSqr(entity) < (radius * 2) * (radius * 2)) {
                    livingEntity.setSecondsOnFire(5);
                    MagicManager.spawnParticles(level, ParticleHelper.EMBERS, livingEntity.getX(), livingEntity.getY() + livingEntity.getBbHeight() * .5f, livingEntity.getZ(), 50, livingEntity.getBbWidth() * .5f, livingEntity.getBbHeight() * .5f, livingEntity.getBbWidth() * .5f, .03, false);
                }
            });
        }
    }

    @Override
    public void onServerCastComplete(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData, boolean cancelled) {
        level.getEntitiesOfClass(HibashiraEntity.class, new AABB(entity.position().subtract(10, 10, 10), entity.position().add(10, 10, 10)),
                        hibashiraEntity -> horizontalDistanceSqrHibashira(hibashiraEntity, entity.position()) < 100 &&
                                hibashiraEntity.getOwner() == entity)
                .forEach(hibashiraEntity -> {
                    hibashiraEntity.remove(Entity.RemovalReason.DISCARDED);
                });
        super.onServerCastComplete(level, spellLevel, entity, playerMagicData, cancelled);
    }

    private float horizontalDistanceSqr(LivingEntity livingEntity, Vec3 vec3) {
        var dx = livingEntity.getX() - vec3.x;
        var dz = livingEntity.getZ() - vec3.z;
        return (float) (dx * dx + dz * dz);
    }

    private float horizontalDistanceSqrHibashira(HibashiraEntity livingEntity, Vec3 vec3) {
        var dx = livingEntity.getX() - vec3.x;
        var dz = livingEntity.getZ() - vec3.z;
        return (float) (dx * dx + dz * dz);
    }

    @Override
    public SpellDamageSource getDamageSource(@Nullable Entity projectile, Entity attacker) {
        return super.getDamageSource(projectile, attacker).setFireTime(3);
    }

    private float getDamage(int spellLevel, LivingEntity caster) {
        return getSpellPower(spellLevel, caster);
    }

    private float getRadius(LivingEntity caster) {
        return 4f;
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return DFSpellAnimations.HIBASHIRA_ANIMATION;
    }
}
