package net.alshanex.devilfruitsmod.event;

import com.google.common.eventbus.Subscribe;
import io.redspace.ironsspellbooks.entity.mobs.MagicSummon;
import net.alshanex.devilfruitsmod.DevilFruitsMod;
import net.alshanex.devilfruitsmod.effect.ModEffects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

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
                            for (int i = 0; i < 20; i++) {
                                double offsetX = (Math.random() - 0.5) * 0.5;
                                double offsetY = (Math.random() - 0.5) * 0.5;
                                double offsetZ = (Math.random() - 0.5) * 0.5;

                                serverLevel.sendParticles(ParticleTypes.FLAME,
                                        event.getProjectile().getX() + offsetX,
                                        event.getProjectile().getY() + offsetY,
                                        event.getProjectile().getZ() + offsetZ,
                                        1, 0, 0, 0, 0);
                            }
                        }
                        event.getProjectile().setSecondsOnFire(5);
                    }
                }
            }
        }


    }
}
