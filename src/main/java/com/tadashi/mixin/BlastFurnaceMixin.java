package com.tadashi.mixin;

import net.dries007.tfc.common.blocks.devices.BlastFurnaceBlock;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.MultiBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

import static com.tadashi.Registry.INSULATED_FIRE_BRICKS;
import static net.dries007.tfc.common.blocks.TFCBlocks.MOLTEN;
import static net.dries007.tfc.util.Helpers.isBlock;

@Mixin(BlastFurnaceBlock.class)
public abstract class BlastFurnaceMixin {

    private static final MultiBlock BLAST_FURNACE_CHIMNEY = getBlastFurnaceChimney();
    private static final MultiBlock INSULATED_CHIMNEY;
    private static final Predicate<BlockState> IS_INSULATED = b -> isBlock(b, INSULATED_FIRE_BRICKS.get());

    static {
        INSULATED_CHIMNEY = new MultiBlock()
                .match(new BlockPos(0, 0, 0), state -> state.isAir() || isBlock(state, MOLTEN.get()))
                .match(new BlockPos(0, 0, 1), IS_INSULATED)
                .match(new BlockPos(0, 0, -1), IS_INSULATED)
                .match(new BlockPos(1, 0, 0), IS_INSULATED)
                .match(new BlockPos(-1, 0, 0), IS_INSULATED);
    }

    @Accessor("BLAST_FURNACE_CHIMNEY")
    private static MultiBlock getBlastFurnaceChimney() {
        throw new AssertionError();
    }

    @Inject(method = "getChimneyLevels", at = @At("HEAD"), cancellable = true)
    private static void inject_getChimneyLevels(final Level level, final BlockPos pos, final CallbackInfoReturnable<Integer> cir) {
        final int maxHeight = TFCConfig.SERVER.blastFurnaceMaxChimneyHeight.get();
        for (int i = 0; i < maxHeight; i++) {
            final BlockPos center = pos.above(i + 1);
            if (!BLAST_FURNACE_CHIMNEY.test(level, center) && !INSULATED_CHIMNEY.test(level, center)) {
                cir.setReturnValue(i);
                return;
            }
        }
        cir.setReturnValue(maxHeight);
    }
}
