package net.alshanex.devilfruitsmod.item;

import net.alshanex.devilfruitsmod.DevilFruitsMod;
import net.alshanex.devilfruitsmod.item.custom.HieHieItem;
import net.alshanex.devilfruitsmod.item.custom.MeraMeraItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, DevilFruitsMod.MOD_ID);


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

    public static final RegistryObject<Item> MERAMERA = ITEMS.register("meramerafruit", MeraMeraItem::new);
    public static final RegistryObject<Item> HIEHIE = ITEMS.register("hiehiefruit", HieHieItem::new);
}
