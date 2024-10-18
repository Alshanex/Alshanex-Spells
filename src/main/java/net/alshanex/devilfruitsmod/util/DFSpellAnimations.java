package net.alshanex.devilfruitsmod.util;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import net.alshanex.devilfruitsmod.DevilFruitsMod;
import net.minecraft.resources.ResourceLocation;

public class DFSpellAnimations {
    public static ResourceLocation ANIMATION_RESOURCE = new ResourceLocation(DevilFruitsMod.MOD_ID, "animation");

    public static final AnimationHolder TOUCH_GROUND_ANIMATION = new AnimationHolder("devilfruitsmod:touch_ground", false, true);
}
