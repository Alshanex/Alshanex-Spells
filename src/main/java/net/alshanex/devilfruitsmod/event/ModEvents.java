package net.alshanex.devilfruitsmod.event;

import com.google.common.eventbus.Subscribe;
import io.redspace.ironsspellbooks.compat.Curios;
import io.redspace.ironsspellbooks.entity.mobs.MagicSummon;
import net.alshanex.devilfruitsmod.DevilFruitsMod;
import net.alshanex.devilfruitsmod.effect.ModEffects;
import net.alshanex.devilfruitsmod.item.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.event.CurioChangeEvent;
import top.theillusivec4.curios.api.event.CurioEquipEvent;
import top.theillusivec4.curios.api.event.CurioUnequipEvent;

import java.util.List;
import java.util.Objects;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = DevilFruitsMod.MOD_ID)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void onProjectileImpact(ProjectileImpactEvent event) {
            if (event.getRayTraceResult() instanceof EntityHitResult entityHitResult) {
                if (entityHitResult.getEntity() instanceof ServerPlayer player) {
                    if (player.hasEffect(ModEffects.MERA_LOGIA_EFFECT.get())) {
                        event.setImpactResult(ProjectileImpactEvent.ImpactResult.SKIP_ENTITY);
                        if (player.level() instanceof ServerLevel serverLevel) {

                            Vec3 projectileDirection = event.getProjectile().getDeltaMovement().normalize();

                            double newPosX = event.getProjectile().getX() + projectileDirection.x;
                            double newPosY = event.getProjectile().getY() + projectileDirection.y;
                            double newPosZ = event.getProjectile().getZ() + projectileDirection.z;

                            for (int i = 0; i < 20; i++) {
                                double offsetX = (Math.random() - 0.5) * 0.5;
                                double offsetY = (Math.random() - 0.5) * 0.5;
                                double offsetZ = (Math.random() - 0.5) * 0.5;

                                serverLevel.sendParticles(ParticleTypes.FLAME,
                                        newPosX + offsetX,
                                        newPosY + offsetY,
                                        newPosZ + offsetZ,
                                        1, 0, 0, 0, 0);
                            }
                        }
                        event.getProjectile().setSecondsOnFire(5);
                    }
                }
            }
        }

        @SubscribeEvent
        public static  void onCurioChange(CurioChangeEvent event){
            if(event.getIdentifier().equals(Curios.SPELLBOOK_SLOT)){
                if(event.getTo().is(ModItems.MERAMERA.get())) {
                    event.getEntity().addEffect(new MobEffectInstance(ModEffects.MERA_LOGIA_EFFECT.get(), Integer.MAX_VALUE, 0, false, false));
                    event.getEntity().addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, false, false));
                }
            }
        }

        @SubscribeEvent
        public static  void onCurioUnequip(CurioUnequipEvent event){
            if(event.getStack().is(ModItems.MERAMERA.get())){
                event.setResult(Event.Result.DENY);
            }
        }
    }
}
