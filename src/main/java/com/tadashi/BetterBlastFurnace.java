package com.tadashi;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static com.tadashi.Registry.MOD_BLOCKS;
import static com.tadashi.Registry.MOD_ITEMS;

@Mod(BetterBlastFurnace.MOD_ID)
public class BetterBlastFurnace {
    public static final String MOD_ID = "tfcbetterbf";

    public BetterBlastFurnace() {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        MOD_ITEMS.register(bus);
        MOD_BLOCKS.register(bus);
        bus.addListener(Registry::addItemsToCreativeTabs);
    }
}
