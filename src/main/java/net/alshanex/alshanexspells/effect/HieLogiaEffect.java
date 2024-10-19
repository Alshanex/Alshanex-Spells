package net.alshanex.alshanexspells.effect;

import io.redspace.ironsspellbooks.effect.MagicMobEffect;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class HieLogiaEffect extends MagicMobEffect {
    public HieLogiaEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int pAmplifier) {
        if(entity instanceof ServerPlayer player){
            if(player.getTicksFrozen() > 0){
                player.setTicksFrozen(0);
            }
            if(player.hasEffect(MobEffectRegistry.CHILLED.get())){
                player.removeEffect(MobEffectRegistry.CHILLED.get());
            }
        }
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        return ret;
    }
}
