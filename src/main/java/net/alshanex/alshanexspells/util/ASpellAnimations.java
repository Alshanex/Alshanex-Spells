package net.alshanex.alshanexspells.util;

import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import net.alshanex.alshanexspells.AlshanexSpellsMod;
import net.minecraft.resources.ResourceLocation;

public class ASpellAnimations {
    public static ResourceLocation ANIMATION_RESOURCE = new ResourceLocation(AlshanexSpellsMod.MOD_ID, "animation");

    public static final AnimationHolder TOUCH_GROUND_ANIMATION = new AnimationHolder("alshanexspells:touch_ground", false, true);
    public static final AnimationHolder FIST_START_ANIMATION = new AnimationHolder("alshanexspells:fist_start", false, true);
    public static final AnimationHolder FIST_RELEASE_ANIMATION = new AnimationHolder("alshanexspells:fist_release", true, true);
    public static final AnimationHolder HIBASHIRA_ANIMATION = new AnimationHolder("alshanexspells:fire_pilar", false, true);
}
