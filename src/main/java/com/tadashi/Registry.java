package com.tadashi;

import com.tadashi.item.Insulation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.tadashi.BetterBlastFurnace.MOD_ID;
import static net.dries007.tfc.common.TFCCreativeTabs.DECORATIONS;
import static net.dries007.tfc.common.TFCCreativeTabs.METAL;
import static net.dries007.tfc.common.blocks.TFCBlocks.FIRE_BRICKS;
import static net.dries007.tfc.common.items.TFCItems.METAL_ITEMS;
import static net.dries007.tfc.util.Metal.Default.WROUGHT_IRON;
import static net.dries007.tfc.util.Metal.ItemType.TUYERE;
import static net.dries007.tfc.util.registry.RegistrationHelpers.registerBlock;
import static net.minecraft.world.item.CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS;
import static net.minecraft.world.level.material.MapColor.COLOR_RED;
import static net.minecraftforge.registries.ForgeRegistries.BLOCKS;
import static net.minecraftforge.registries.ForgeRegistries.ITEMS;

public class Registry {
    public static final DeferredRegister<Item> MOD_ITEMS = DeferredRegister.create(ITEMS, MOD_ID);
    public static final DeferredRegister<Block> MOD_BLOCKS = DeferredRegister.create(BLOCKS, MOD_ID);

    public static final RegistryObject<Item> INSULATION = MOD_ITEMS.register("insulation", Insulation::new);

    public static final RegistryObject<Block> INSULATED_FIRE_BRICKS = registerBlock(MOD_BLOCKS, MOD_ITEMS,
            "insulated_fire_bricks",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(COLOR_RED).requiresCorrectToolForDrops().strength(2.0f, 6.0f)),
            b -> new BlockItem(b, new Item.Properties()));

    public static void addItemsToCreativeTabs(final BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == METAL.tab().get()) {
            event.getEntries().putAfter(METAL_ITEMS.get(WROUGHT_IRON).get(TUYERE).get().getDefaultInstance(), INSULATION.get().getDefaultInstance(), PARENT_AND_SEARCH_TABS);
        }
        if (event.getTab() == DECORATIONS.tab().get()) {
            event.getEntries().putAfter(FIRE_BRICKS.get().asItem().getDefaultInstance(), INSULATED_FIRE_BRICKS.get().asItem().getDefaultInstance(), PARENT_AND_SEARCH_TABS);
        }
    }
}
