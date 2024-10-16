package net.alshanex.devilfruitsmod.block;

import net.alshanex.devilfruitsmod.DevilFruitsMod;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.client.model.obj.ObjMaterialLibrary;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, DevilFruitsMod.MOD_ID);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    public static final RegistryObject<Block> ICE_SURFACE_BLOCK = BLOCKS.register("ice_surface_block",
            () -> new IceSurfaceBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.ICE)
                    .strength(0.5F)
                    .sound(SoundType.GLASS)
                    .friction(0.98f)
                    .noOcclusion()
                    .isViewBlocking((state, world, pos) -> false)
                    .isSuffocating((state, world, pos) -> false)
                    .pushReaction(PushReaction.DESTROY)));
}
