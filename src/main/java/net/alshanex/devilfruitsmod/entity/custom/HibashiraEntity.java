package net.alshanex.devilfruitsmod.entity.custom;

import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.alshanex.devilfruitsmod.entity.ModEntities;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

public class HibashiraEntity extends AoeEntity {

    private DamageSource damageSource;

    public HibashiraEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public HibashiraEntity(Level level) {
        this(ModEntities.HIBASHIRA_ENTITY.get(), level);
    }

    @Override
    public void applyEffect(LivingEntity target) {
        if (damageSource == null) {
            damageSource = new DamageSource(DamageSources.getHolderFromResource(target, ISSDamageTypes.FIRE_FIELD), this, getOwner());
        }
        Vec3 knockbackDirection = target.position().subtract(this.position()).normalize().scale(1);
        target.setDeltaMovement(target.getDeltaMovement().add(knockbackDirection));
        target.hurt(damageSource, getDamage());
        target.setSecondsOnFire(3);
    }

    @Override
    protected void checkHits() {
        if (level().isClientSide)
            return;
        List<LivingEntity> targets = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(this.getInflation().x, 15, this.getInflation().z));
        boolean hit = false;
        var radiusSqr = getRadius();
        radiusSqr *= radiusSqr;
        for (LivingEntity target : targets) {
            if (canHitEntity(target) && (!isCircular() || target.distanceToSqr(this) < radiusSqr || (target.getY() > this.getY() && target.getY() < this.getY() + 15))) {
                applyEffect(target);
                hit = true;
            }
        }
        if (hit) {
            this.setRadius(getRadius() + radiusOnUse);
            this.duration += durationOnUse;
            onPostHit();
        }
    }

    @Override
    public float getParticleCount() {
        return 1.2f * getRadius();
    }

    @Override
    protected float particleYOffset() {
        return .25f;
    }

    @Override
    protected float getParticleSpeedModifier() {
        return 1.4f;
    }

    @Override
    public Optional<ParticleOptions> getParticle() {
        return Optional.of(ParticleHelper.FIRE);
    }

    @Override
    public void ambientParticles() {
        if (!level().isClientSide)
            return;

        getParticle().ifPresent((particle) -> {
            float f = getParticleCount() * 0.5f;
            f = Mth.clamp(f * getRadius(), f / 4, f * 10);
            float angularSpeed = 0.15f;

            for (int i = 0; i < f; i++) {
                if (f - i < 1 && random.nextFloat() > f - i)
                    return;

                float r = getRadius() - 3;
                float minRadius = r;
                float maxRadius = r + 1.0f;

                float theta = this.random.nextFloat() * (2 * Mth.PI);

                float distance = Mth.lerp(this.random.nextFloat(), minRadius, maxRadius);

                Vec3 pos = new Vec3(
                        distance * Mth.cos(theta),
                        0.2f,
                        distance * Mth.sin(theta)
                );

                Vec3 motion = new Vec3(
                        -angularSpeed * pos.z,
                        0.03f + this.random.nextDouble() * 0.02f,
                        angularSpeed * pos.x
                );

                for (int yUP = 0; yUP < 15; yUP += 1) {
                    level().addParticle(particle, getX() + pos.x, getY() + pos.y + particleYOffset() + yUP, getZ() + pos.z, motion.x, motion.y, motion.z);
                }
            }
        });
    }
}
