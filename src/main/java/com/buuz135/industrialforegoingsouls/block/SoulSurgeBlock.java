package com.buuz135.industrialforegoingsouls.block;

import com.buuz135.industrialforegoingsouls.IndustrialForegoingSouls;
import com.buuz135.industrialforegoingsouls.block.tile.SoulSurgeBlockEntity;
import com.hrznstudio.titanium.block.BasicTileBlock;
import com.hrznstudio.titanium.block.RotatableBlock;
import com.hrznstudio.titanium.block_network.INetworkDirectionalConnection;
import com.hrznstudio.titanium.util.FacingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class SoulSurgeBlock extends BasicTileBlock<SoulSurgeBlockEntity> implements INetworkDirectionalConnection {

    public static final BooleanProperty ENABLED = BooleanProperty.create("enabled");
    public static final BooleanProperty TOP_CONN = BooleanProperty.create("top");
    public static final BooleanProperty UP_CONN = BooleanProperty.create("up");
    public static final BooleanProperty DOWN_CONN = BooleanProperty.create("down");
    public static final BooleanProperty EAST_CONN = BooleanProperty.create("east");
    public static final BooleanProperty WEST_CONN = BooleanProperty.create("west");

    public static VoxelShape NORTH = Stream.of(
            Block.box(5, 5, 0, 5, 11, 5),
            Block.box(11, 5, 0, 11, 11, 5),
            Block.box(5, 5, 0, 11, 5, 5),
            Block.box(4, 4, 5, 12, 12, 14),
            Block.box(3, 3, 6, 13, 13, 8),
            Block.box(3, 3, 14, 13, 13, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static VoxelShape EAST = Stream.of(
            Block.box(11, 5, 5, 16, 11, 5),
            Block.box(11, 5, 11, 16, 11, 11),
            Block.box(11, 5, 5, 16, 5, 11),
            Block.box(2, 4, 4, 11, 12, 12),
            Block.box(8, 3, 3, 10, 13, 13),
            Block.box(0, 3, 3, 2, 13, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static VoxelShape SOUTH = Stream.of(
            Block.box(11, 5, 11, 11, 11, 16),
            Block.box(5, 5, 11, 5, 11, 16),
            Block.box(5, 5, 11, 11, 5, 16),
            Block.box(4, 4, 2, 12, 12, 11),
            Block.box(3, 3, 8, 13, 13, 10),
            Block.box(3, 3, 0, 13, 13, 2)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static VoxelShape WEST = Stream.of(
            Block.box(0, 5, 11, 5, 11, 11),
            Block.box(0, 5, 5, 5, 11, 5),
            Block.box(0, 5, 5, 5, 5, 11),
            Block.box(5, 4, 4, 14, 12, 12),
            Block.box(6, 3, 3, 8, 13, 13),
            Block.box(14, 3, 3, 16, 13, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static VoxelShape UP = Stream.of(
            Block.box(5, 11, 11, 11, 16, 11),
            Block.box(5, 11, 5, 11, 16, 5),
            Block.box(5, 11, 5, 5, 16, 11),
            Block.box(4, 2, 4, 12, 11, 12),
            Block.box(3, 8, 3, 13, 10, 13),
            Block.box(3, 0, 3, 13, 2, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static VoxelShape DOWN = Stream.of(
            Block.box(5, 0, 5, 11, 5, 5),
            Block.box(5, 0, 11, 11, 5, 11),
            Block.box(5, 0, 5, 5, 5, 11),
            Block.box(4, 5, 4, 12, 14, 12),
            Block.box(3, 6, 3, 13, 8, 13),
            Block.box(3, 14, 3, 13, 16, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();


    public SoulSurgeBlock() {
        super("soul_surge", Properties.copy(Blocks.IRON_BLOCK), SoulSurgeBlockEntity.class);
        this.registerDefaultState(this.defaultBlockState().setValue(ENABLED, true).setValue(TOP_CONN, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(RotatableBlock.FACING_ALL);
        if (direction == Direction.UP) {
            return UP;
        }
        if (direction == Direction.DOWN) {
            return DOWN;
        }
        if (direction == Direction.EAST) {
            return EAST;
        }
        if (direction == Direction.WEST) {
            return WEST;
        }
        if (direction == Direction.SOUTH) {
            return SOUTH;
        }
        return NORTH;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(RotatableBlock.FACING_ALL);
        if (direction == Direction.UP) {
            return UP;
        }
        if (direction == Direction.DOWN) {
            return DOWN;
        }
        if (direction == Direction.EAST) {
            return EAST;
        }
        if (direction == Direction.WEST) {
            return WEST;
        }
        if (direction == Direction.SOUTH) {
            return SOUTH;
        }
        return NORTH;
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<?> getTileEntityFactory() {
        return (pos, state) -> new SoulSurgeBlockEntity(this, IndustrialForegoingSouls.SOUL_SURGE_BLOCK.getRight().get(), pos, state);
    }

    @Override
    public boolean canConnect(BlockState state, Direction direction) {
        var pointing = state.getValue(RotatableBlock.FACING_ALL);
        if (pointing.getAxis().isVertical()) {
            if (direction == Direction.EAST) {
                return state.getValue(EAST_CONN);
            }
            if (direction == Direction.WEST) {
                return state.getValue(WEST_CONN);
            }
            if (direction == Direction.NORTH) {
                return state.getValue(DOWN_CONN);
            }
            if (direction == Direction.SOUTH) {
                return state.getValue(UP_CONN);
            }
        } else {
            var sideness = FacingUtil.getFacingRelative(pointing, direction);
            if (sideness == FacingUtil.Sideness.LEFT) {
                return state.getValue(EAST_CONN);
            }
            if (sideness == FacingUtil.Sideness.RIGHT) {
                return state.getValue(WEST_CONN);
            }
            if (sideness == FacingUtil.Sideness.BOTTOM) {
                return state.getValue(DOWN_CONN);
            }
            if (sideness == FacingUtil.Sideness.TOP) {
                return state.getValue(UP_CONN);
            }
        }
        return state.getValue(RotatableBlock.FACING_ALL) == direction;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var pipeConnection = context.getLevel().getBlockState(context.getClickedPos().relative(context.getClickedFace())).getBlock().equals(IndustrialForegoingSouls.SOUL_PIPE_BLOCK.getKey().get());
        if (!pipeConnection) {
            var relativeBlockstate = context.getLevel().getBlockState(context.getClickedPos().relative(context.getClickedFace().getAxis().isVertical() ? context.getClickedFace() : FacingUtil.getFacingFromSide(context.getClickedFace(), FacingUtil.Sideness.FRONT)));
            pipeConnection = relativeBlockstate.getBlock().equals(IndustrialForegoingSouls.SOUL_SURGE_BLOCK.getKey().get()) && relativeBlockstate.getValue(RotatableBlock.FACING_ALL) == context.getClickedFace().getOpposite();
        }
        var eastConnection = checkSurgeConnection(Direction.EAST, FacingUtil.Sideness.LEFT, context.getClickedFace(), context.getLevel(), context.getClickedPos());
        var westConnection = checkSurgeConnection(Direction.WEST, FacingUtil.Sideness.RIGHT, context.getClickedFace(), context.getLevel(), context.getClickedPos());
        var upConnection = checkSurgeConnection(Direction.SOUTH, FacingUtil.Sideness.TOP, context.getClickedFace(), context.getLevel(), context.getClickedPos());
        var downConnection = checkSurgeConnection(Direction.NORTH, FacingUtil.Sideness.BOTTOM, context.getClickedFace(), context.getLevel(), context.getClickedPos());

        return this.defaultBlockState()
                .setValue(RotatableBlock.FACING_ALL, context.getClickedFace())
                .setValue(TOP_CONN, pipeConnection)
                .setValue(EAST_CONN, eastConnection)
                .setValue(WEST_CONN, westConnection)
                .setValue(UP_CONN, upConnection)
                .setValue(DOWN_CONN, downConnection)
                .setValue(ENABLED, !Boolean.valueOf(context.getLevel().hasNeighborSignal(context.getClickedPos())));
    }

    private boolean checkSurgeConnection(Direction direction, FacingUtil.Sideness sideness, Direction clickedFace, Level level, BlockPos clickedPos) {
        var relativeBlockstate = level.getBlockState(clickedPos.relative(clickedFace.getAxis().isVertical() ? direction : FacingUtil.getFacingFromSide(clickedFace, sideness)));
        return relativeBlockstate.getBlock().equals(IndustrialForegoingSouls.SOUL_SURGE_BLOCK.getKey().get()) && relativeBlockstate.getValue(RotatableBlock.FACING_ALL) == clickedFace;
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block blockIn, BlockPos fromPos, boolean p_220069_6_) {
        super.neighborChanged(state, level, pos, blockIn, fromPos, p_220069_6_);
        if (!level.isClientSide) {
            var facing = state.getValue(RotatableBlock.FACING_ALL);
            var needStateRefresh = false;
            var originalPipeConnection = state.getValue(TOP_CONN);
            var pipeConnection = level.getBlockState(pos.relative(facing)).getBlock().equals(IndustrialForegoingSouls.SOUL_PIPE_BLOCK.getKey().get());
            if (!pipeConnection) {
                var relativeBlockstate = level.getBlockState(pos.relative(facing.getAxis().isVertical() ? facing : FacingUtil.getFacingFromSide(facing, FacingUtil.Sideness.FRONT)));
                pipeConnection = relativeBlockstate.getBlock().equals(IndustrialForegoingSouls.SOUL_SURGE_BLOCK.getKey().get()) && relativeBlockstate.getValue(RotatableBlock.FACING_ALL) == facing.getOpposite();
            }
            state = state.setValue(TOP_CONN, pipeConnection);
            if (originalPipeConnection != pipeConnection) {
                needStateRefresh = true;
            }
            var eastConnection = checkSurgeConnection(Direction.EAST, FacingUtil.Sideness.LEFT, facing, level, pos);
            var originalConnection = state.getValue(EAST_CONN);
            state = state.setValue(EAST_CONN, eastConnection);
            if (eastConnection != originalConnection) {
                needStateRefresh = true;
            }
            var westConnection = checkSurgeConnection(Direction.WEST, FacingUtil.Sideness.RIGHT, facing, level, pos);
            originalConnection = state.getValue(WEST_CONN);
            state = state.setValue(WEST_CONN, westConnection);
            if (westConnection != originalConnection) {
                needStateRefresh = true;
            }
            var upConnection = checkSurgeConnection(Direction.SOUTH, FacingUtil.Sideness.TOP, facing, level, pos);
            originalConnection = state.getValue(UP_CONN);
            state = state.setValue(UP_CONN, upConnection);
            if (upConnection != originalConnection) {
                needStateRefresh = true;
            }
            var downConnection = checkSurgeConnection(Direction.NORTH, FacingUtil.Sideness.BOTTOM, facing, level, pos);
            originalConnection = state.getValue(DOWN_CONN);
            state = state.setValue(DOWN_CONN, downConnection);
            if (downConnection != originalConnection) {
                needStateRefresh = true;
            }
            if (needStateRefresh) {
                level.setBlock(pos, state, 2);
            }

            boolean flag = state.getValue(ENABLED);
            boolean hasSignal = level.hasNeighborSignal(pos);
            if (flag == hasSignal) {
                level.setBlock(pos, state.setValue(ENABLED, !hasSignal), 2);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        super.createBlockStateDefinition(stateBuilder);
        if (RotatableBlock.RotationType.SIX_WAY.getProperties() != null)
            stateBuilder.add(RotatableBlock.RotationType.SIX_WAY.getProperties());
        stateBuilder.add(ENABLED);
        stateBuilder.add(TOP_CONN);
        stateBuilder.add(UP_CONN);
        stateBuilder.add(DOWN_CONN);
        stateBuilder.add(EAST_CONN);
        stateBuilder.add(WEST_CONN);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        if (RotatableBlock.RotationType.SIX_WAY.getProperties().length > 0) {
            return state.setValue(RotatableBlock.RotationType.SIX_WAY.getProperties()[0], rot.rotate(state.getValue(RotatableBlock.RotationType.SIX_WAY.getProperties()[0])));
        }
        return super.rotate(state, rot);
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        if (RotatableBlock.RotationType.SIX_WAY.getProperties().length > 0) {
            return state.rotate(mirrorIn.getRotation(state.getValue(RotatableBlock.RotationType.SIX_WAY.getProperties()[0])));
        }
        return super.mirror(state, mirrorIn);
    }


}
