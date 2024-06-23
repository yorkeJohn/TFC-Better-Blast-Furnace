package com.tadashi.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import static com.tadashi.Registry.INSULATED_FIRE_BRICKS;
import static net.dries007.tfc.common.blocks.TFCBlocks.FIRE_BRICKS;
import static net.dries007.tfc.util.Helpers.isBlock;
import static net.dries007.tfc.util.Helpers.playSound;
import static net.minecraft.sounds.SoundEvents.METAL_PLACE;
import static net.minecraft.world.InteractionResult.PASS;
import static net.minecraft.world.InteractionResult.sidedSuccess;

public class Insulation extends Item {

    public Insulation() {
        super(new Item.Properties());
    }

    @Override
    public @NotNull InteractionResult useOn(final UseOnContext ctx) {
        final Level level = ctx.getLevel();
        final BlockPos pos = ctx.getClickedPos();
        final BlockState block = level.getBlockState(pos);
        final Player player = ctx.getPlayer();

        if (player == null) {
            return PASS;
        }

        final ItemStack stack = player.getItemInHand(ctx.getHand());
        if (!isBlock(block, FIRE_BRICKS.get())) {
            return PASS;
        }

        level.setBlockAndUpdate(pos, INSULATED_FIRE_BRICKS.get().defaultBlockState());
        stack.shrink(1);
        playSound(level, pos, METAL_PLACE);
        return sidedSuccess(level.isClientSide);
    }
}
