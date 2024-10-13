package net.alshanex.devilfruitsmod.util;

import net.minecraft.world.level.block.*;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;
import java.util.Set;

public class DFUtils {

    public static Set<Block> nonFreezeableBlocks(){
        Set<Block> excludedBlocks = new HashSet<>();

        for (Block block : ForgeRegistries.BLOCKS) {
            if (block instanceof TrapDoorBlock || block instanceof DoorBlock || block instanceof SignBlock || block instanceof BedBlock
                    || block instanceof AbstractBannerBlock || block instanceof AbstractCandleBlock || block instanceof AbstractCauldronBlock
                    || block instanceof AbstractChestBlock<?> || block instanceof AbstractFurnaceBlock
                    || block instanceof AbstractSkullBlock || block instanceof AnvilBlock || block instanceof BarrelBlock || block instanceof BarrierBlock
                    || block instanceof CoralBlock || block instanceof CoralFanBlock || block instanceof CoralPlantBlock
                    || block instanceof CoralWallFanBlock || block instanceof AttachedStemBlock || block instanceof AmethystBlock
                    || block instanceof PressurePlateBlock || block instanceof RailBlock || block instanceof BeehiveBlock
                    || block instanceof BellBlock || block instanceof BrewingStandBlock || block instanceof ButtonBlock
                    || block instanceof FlowerBlock || block instanceof TorchBlock || block instanceof CampfireBlock
                    || block instanceof SugarCaneBlock || block instanceof CactusBlock || block instanceof CarpetBlock
                    || block instanceof CakeBlock || block instanceof CommandBlock || block instanceof CraftingTableBlock
                    || block instanceof ShulkerBoxBlock || block instanceof EnchantmentTableBlock
                    || block instanceof BeaconBlock || block instanceof FlowerPotBlock || block instanceof EndGatewayBlock
                    || block instanceof EndPortalBlock || block instanceof EndRodBlock || block instanceof NetherPortalBlock
                    || block instanceof EndPortalFrameBlock || block instanceof SaplingBlock || block instanceof RedStoneWireBlock
                    || block instanceof ComparatorBlock || block instanceof RepeaterBlock || block instanceof MushroomBlock
                    || block instanceof SpawnerBlock || block instanceof CropBlock || block instanceof LadderBlock
                    || block instanceof LeverBlock || block instanceof PumpkinBlock || block instanceof MelonBlock
                    || block instanceof WaterlilyBlock || block instanceof NetherWartBlock || block instanceof SculkShriekerBlock
                    || block instanceof SculkSensorBlock || block instanceof SculkVeinBlock || block instanceof VineBlock
                    || block instanceof DragonEggBlock || block instanceof TripWireBlock || block instanceof TripWireHookBlock
                    || block instanceof HopperBlock || block instanceof DaylightDetectorBlock || block instanceof ChorusFlowerBlock
                    || block instanceof ChorusPlantBlock || block instanceof StructureBlock || block instanceof CartographyTableBlock
                    || block instanceof RespawnAnchorBlock || block instanceof BambooStalkBlock || block instanceof BambooSaplingBlock) {
                excludedBlocks.add(block);
            }
        }

        return excludedBlocks;
    }
}
