package com.tadashi;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(BetterBlastFurnace.MOD_ID)
public class BetterBlastFurnace {
    public static final String MOD_ID = "tfcbetterbf";

    public BetterBlastFurnace(final IEventBus bus) {
        Registry.ITEMS.register(bus);
        Registry.BLOCKS.register(bus);
        bus.addListener(Registry::buildContents);
    }
}
