package net.alshanex.devilfruitsmod.event;

import com.google.common.eventbus.Subscribe;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.compat.Curios;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.mobs.MagicSummon;
import io.redspace.ironsspellbooks.entity.mobs.frozen_humanoid.FrozenHumanoid;
import io.redspace.ironsspellbooks.network.ClientboundSyncMana;
import io.redspace.ironsspellbooks.setup.Messages;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.alshanex.devilfruitsmod.DevilFruitsMod;
import net.alshanex.devilfruitsmod.effect.ModEffects;
import net.alshanex.devilfruitsmod.item.ModItems;
import net.alshanex.devilfruitsmod.util.DFUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
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
import java.util.Set;

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
        public static void onEntityAttacked(LivingHurtEvent event) {
            if (event.getEntity() instanceof ServerPlayer player) {
                MagicData magicData = MagicData.getPlayerMagicData(player);
                if (player.hasEffect(ModEffects.ICE_LOGIA_EFFECT.get()) && magicData.getMana() >= 20 && !(DFUtils.isFireDamage(event.getSource().type()))) {
                    BlockPos blockBelowPos = player.blockPosition().below();
                    Block blockBelow = player.level().getBlockState(blockBelowPos).getBlock();
                    if (DFUtils.isIceOrSnow(blockBelow)) {
                        Vec3 attackDirection = event.getSource().getSourcePosition();
                        if (attackDirection != null) {
                            FrozenHumanoid shadow = new FrozenHumanoid(player.level(), player);
                            shadow.setShatterDamage(Math.max(event.getAmount() * 0.1f, 5f));
                            shadow.setDeathTimer(20);
                            player.level().addFreshEntity(shadow);
                            MagicManager.spawnParticles(player.level(), ParticleHelper.ICY_FOG, player.getX(), player.getY(), player.getZ(), 4, 0, 0, 0, .3, true);

                            Vec3 knockbackDirection = player.position().subtract(attackDirection).normalize();
                            double knockbackStrength = 1.5;

                            player.setDeltaMovement(player.getDeltaMovement().add(knockbackDirection.scale(knockbackStrength)));
                            player.hurtMarked = true;

                            magicData.setMana(magicData.getMana() - 20);
                            Messages.sendToPlayer(new ClientboundSyncMana(magicData), player);
                            event.setCanceled(true);
                        }
                    }
                }
            }
        }

        @SubscribeEvent
        public static  void onCurioChange(CurioChangeEvent event){
            if(event.getIdentifier().equals(Curios.SPELLBOOK_SLOT)){
                if(event.getTo().is(ModItems.MERAMERA.get())) {
                    event.getEntity().addEffect(new MobEffectInstance(ModEffects.MERA_LOGIA_EFFECT.get(), Integer.MAX_VALUE, 0, false, false));
                } else if(event.getTo().is(ModItems.HIEHIE.get())) {
                    event.getEntity().addEffect(new MobEffectInstance(ModEffects.ICE_LOGIA_EFFECT.get(), Integer.MAX_VALUE, 0, false, false));
                }
            }
        }

        @SubscribeEvent
        public static  void onCurioUnequip(CurioUnequipEvent event){
            if(event.getStack().is(ModItems.MERAMERA.get()) || event.getStack().is(ModItems.HIEHIE.get())){
                event.setResult(Event.Result.DENY);
            }
        }

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if (event.player.hasEffect(ModEffects.ICE_LOGIA_EFFECT.get())) {
                Level world = event.player.level();

                BlockPos playerPos = event.player.blockPosition();

                if (event.player.onGround()) {
                    int radius = 2;
                    for (BlockPos blockPos : BlockPos.betweenClosed(playerPos.offset(-radius, -1, -radius), playerPos.offset(radius, -1, radius))) {
                        BlockState blockState = world.getBlockState(blockPos);
                        if (blockState.getBlock() == Blocks.WATER) {
                            world.setBlockAndUpdate(blockPos, Blocks.FROSTED_ICE.defaultBlockState());
                            world.scheduleTick(blockPos, Blocks.FROSTED_ICE, 120);
                        }
                    }
                }
            }
        }
    }
}
