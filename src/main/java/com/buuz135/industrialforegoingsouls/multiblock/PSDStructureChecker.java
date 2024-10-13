package com.buuz135.industrialforegoingsouls.multiblock;

import com.buuz135.industrialforegoingsouls.block.tile.DriveFrameTile;
import com.hrznstudio.titanium.util.FacingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.BiPredicate;

public class PSDStructureChecker {

    private boolean isFormed;

    private final BiPredicate<Level, BlockPos> frameChecker;
    private final BiPredicate<Level, BlockPos> topBlockChecker;

    public PSDStructureChecker() {
        this.frameChecker = (level, blockPos) -> {
            var tile = level.getBlockEntity(blockPos);
            if (tile instanceof DriveFrameTile driveFrameTile){
                return !driveFrameTile.hasController();
            }
            return false;
        };
        this.topBlockChecker = (level, blockPos) -> {
            var tile = level.getBlockEntity(blockPos);
            if (!(tile instanceof DriveFrameTile) && tile instanceof IMultiblockElement multiblockElement){
                return !multiblockElement.hasController();
            }
            return false;
        };
    }

    @Nullable
    public Pair<BlockPos, BlockPos> checkStructure(Level level, BlockPos controllerPos, Direction facing) {
        var left = FacingUtil.getFacingFromSide(facing, FacingUtil.Sideness.LEFT);
        var right = FacingUtil.getFacingFromSide(facing, FacingUtil.Sideness.RIGHT);
        var leftFrameSize = findUntilStops(level, controllerPos, left, this.frameChecker);
        var rightFrameSize = findUntilStops(level, controllerPos, right, this.frameChecker);

        //CHECK IF FLOOR FULL
        var leftFrameDepth = findUntilStops(level, controllerPos.relative(left, leftFrameSize), facing.getOpposite(), this.frameChecker);
        var rightFrameDepth = findUntilStops(level, controllerPos.relative(right, rightFrameSize), facing.getOpposite(), this.frameChecker);

        if (leftFrameDepth != rightFrameDepth || leftFrameDepth < 3) {
            System.out.println("NOT SAME SIZE OR SMOL");
            return null;
        }
        if (!isFullFloor(level, controllerPos, facing, left, right, leftFrameSize, rightFrameSize, leftFrameDepth)) {
            System.out.println("NOT FULL FLOOR");
            return null;
        }

        //CHECK FOR PILLARS
        var leftFrontPillar = findUntilStops(level, controllerPos.relative(left, leftFrameSize), Direction.UP, this.frameChecker);
        var rightFrontPillar = findUntilStops(level, controllerPos.relative(right, rightFrameSize), Direction.UP, this.frameChecker);
        var leftBackPillar = findUntilStops(level, controllerPos.relative(left, leftFrameSize).relative(facing.getOpposite(), leftFrameDepth), Direction.UP, this.frameChecker);
        var rightBackPillar = findUntilStops(level, controllerPos.relative(right, rightFrameSize).relative(facing.getOpposite(), leftFrameDepth), Direction.UP, this.frameChecker);

        if (leftFrontPillar != rightFrontPillar || leftFrontPillar != leftBackPillar || leftFrontPillar != rightBackPillar|| leftFrontPillar < 2) {
            System.out.println("PILLARS NOT SAME SIZE OR SMOL");
            return null;
        }

        //CHECK FOR INVENTORY
        if (getInventory(level, controllerPos, leftFrontPillar).isEmpty()){
            System.out.println("NO INVENTORY FOUND");
            return null;
        }

        //CHECK FOR CABLES/SURGES
        if (!checkTopFloor(level, controllerPos.relative(Direction.UP, leftBackPillar + 1), facing, left, right, leftFrameSize, rightFrameSize, leftFrameDepth)){
            System.out.println("NOT FULL CEILING");
            return null;
        }

        return Pair.of(controllerPos.relative(left, leftFrameSize), controllerPos.relative(right, rightFrameSize + 1).relative(Direction.UP, leftFrontPillar + 1).relative(facing.getOpposite(), leftFrameDepth + 1));
    }

    private int findUntilStops(Level level, BlockPos controllerPos, Direction facing, BiPredicate<Level, BlockPos> frameChecker) {
        var amount = 0;
        while (frameChecker.test(level, controllerPos.relative(facing, amount + 1))) {
            ++amount;
        }
        return amount;
    }

    private boolean isFullFloor(Level level, BlockPos controllerPos, Direction facing, Direction left, Direction right, int leftFrameSize, int rightFrameSize, int depth) {
        var leftCornerPos = controllerPos.relative(left, leftFrameSize);
        for (int x = 0; x < (leftFrameSize + rightFrameSize + 1) ; x++) {
            var currentRow = leftCornerPos.relative(right, x);
            for (int z = 1; z <= depth; z++) {
                if (!frameChecker.test(level, currentRow.relative(facing.getOpposite(), z))) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkTopFloor(Level level, BlockPos controllerPos, Direction facing, Direction left, Direction right, int leftFrameSize, int rightFrameSize, int depth) {
        var leftCornerPos = controllerPos.relative(left, leftFrameSize);
        for (int x = 0; x < (leftFrameSize + rightFrameSize + 1) ; x++) {
            var currentRow = leftCornerPos.relative(right, x);
            for (int z = 0; z <= depth; z++) {
                if ((x == 0 || x == (leftFrameSize + rightFrameSize)) || (z == 0 || z == depth)){
                    var checkingBlock = currentRow.relative(facing.getOpposite(), z);
                    if (checkingBlock.getX() == controllerPos.getX() && checkingBlock.getZ() == controllerPos.getZ()) continue;
                    //level.setBlock(checkingBlock.above(), Blocks.STONE.defaultBlockState(), 19);
                    if (!topBlockChecker.test(level, checkingBlock)) {
                        System.out.println(checkingBlock);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public Optional<IItemHandler> getInventory(Level level, BlockPos controllerPos, int height){
        var tile = level.getBlockEntity(controllerPos.relative(Direction.UP, height + 1));
        if (tile != null){
            return tile.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.DOWN).resolve();
        }
        return Optional.empty();
    }

    public boolean isFormed() {
        return isFormed;
    }

    public void setFormed(boolean formed) {
        isFormed = formed;
    }
}
