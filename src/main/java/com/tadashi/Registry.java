package com.tadashi;

import static com.tadashi.BetterBlastFurnace.MOD_ID;
import static net.dries007.tfc.common.TFCCreativeTabs.DECORATIONS;
import static net.dries007.tfc.common.TFCCreativeTabs.METAL;
import static net.dries007.tfc.common.blocks.TFCBlocks.FIRE_BRICKS;
import static net.dries007.tfc.common.items.TFCItems.METAL_ITEMS;
import static net.dries007.tfc.util.Metal.WROUGHT_IRON;
import static net.dries007.tfc.util.Metal.ItemType.TUYERE;
import static net.dries007.tfc.util.registry.RegistrationHelpers.registerBlock;
import static net.minecraft.world.item.CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS;

import com.tadashi.item.Insulation;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Registry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, MOD_ID);

    public static final DeferredHolder<Item, Item> INSULATION = ITEMS.register("insulation", Insulation::new);

    public static final DeferredHolder<Block, Block> INSULATED_FIRE_BRICKS = registerBlock(BLOCKS, ITEMS,
            "insulated_fire_bricks",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).requiresCorrectToolForDrops()
                    .strength(2.0F, 6.0F)),
            b -> new BlockItem(b, new Item.Properties()));

    @SubscribeEvent
    public static void buildContents(final BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == METAL.tab().getKey()) {
            event.insertAfter(METAL_ITEMS.get(WROUGHT_IRON).get(TUYERE).get().getDefaultInstance(),
                    INSULATION.get().getDefaultInstance(), PARENT_AND_SEARCH_TABS);
        }
        if (event.getTabKey() == DECORATIONS.tab().getKey()) {
            event.insertAfter(FIRE_BRICKS.get().asItem().getDefaultInstance(),
                    INSULATED_FIRE_BRICKS.get().asItem().getDefaultInstance(), PARENT_AND_SEARCH_TABS);
        }
    }
}
