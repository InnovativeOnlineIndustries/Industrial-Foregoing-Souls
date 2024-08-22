package com.buuz135.industrialforegoingsouls.block;

import com.buuz135.industrialforegoingsouls.IndustrialForegoingSouls;
import com.buuz135.industrialforegoingsouls.block.tile.SoulSurgeBlockEntity;
import com.hrznstudio.titanium.block.BasicTileBlock;
import com.hrznstudio.titanium.block.RotatableBlock;
import com.hrznstudio.titanium.block_network.INetworkDirectionalConnection;
import com.hrznstudio.titanium.util.FacingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public class SoulSurgeBlock extends BasicTileBlock<SoulSurgeBlockEntity> implements INetworkDirectionalConnection, SimpleWaterloggedBlock {

    public static final BooleanProperty ENABLED = BooleanProperty.create("enabled");
    public static final BooleanProperty TOP_CONN = BooleanProperty.create("top");
    public static final BooleanProperty UP_CONN = BooleanProperty.create("up");
    public static final BooleanProperty DOWN_CONN = BooleanProperty.create("down");
    public static final BooleanProperty EAST_CONN = BooleanProperty.create("east");
    public static final BooleanProperty WEST_CONN = BooleanProperty.create("west");

    public static VoxelShape NORTH = Stream.of(
            Block.box(4, 4, 2, 12, 12, 11),
            Block.box(4, 4, 3, 12, 12, 10),
            Block.box(3, 3, 3, 13, 13, 5),
            Block.box(3, 3, 11, 13, 13, 16),
            Block.box(3, 3, 11, 13, 13, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static VoxelShape EAST = Stream.of(
            Block.box(5, 4, 4, 14, 12, 12),
            Block.box(6, 4, 4, 13, 12, 12),
            Block.box(11, 3, 3, 13, 13, 13),
            Block.box(0, 3, 3, 5, 13, 13),
            Block.box(0, 3, 3, 5, 13, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static VoxelShape SOUTH = Stream.of(
            Block.box(4, 4, 5, 12, 12, 14),
            Block.box(4, 4, 6, 12, 12, 13),
            Block.box(3, 3, 11, 13, 13, 13),
            Block.box(3, 3, 0, 13, 13, 5),
            Block.box(3, 3, 0, 13, 13, 5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static VoxelShape WEST = Stream.of(
            Block.box(2, 4, 4, 11, 12, 12),
            Block.box(3, 4, 4, 10, 12, 12),
            Block.box(3, 3, 3, 5, 13, 13),
            Block.box(11, 3, 3, 16, 13, 13),
            Block.box(11, 3, 3, 16, 13, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static VoxelShape UP = Stream.of(
            Block.box(4, 5, 4, 12, 14, 12),
            Block.box(4, 6, 4, 12, 13, 12),
            Block.box(3, 11, 3, 13, 13, 13),
            Block.box(3, 0, 3, 13, 5, 13),
            Block.box(3, 0, 3, 13, 5, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static VoxelShape DOWN = Stream.of(
            Block.box(4, 2, 4, 12, 11, 12),
            Block.box(4, 3, 4, 12, 10, 12),
            Block.box(3, 3, 3, 13, 5, 13),
            Block.box(3, 11, 3, 13, 16, 13),
            Block.box(3, 11, 3, 13, 16, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final Map<BlockState, VoxelShape> SHAPE_CACHE = new HashMap<>();
    private static final Map<BlockState, VoxelShape> COLL_SHAPE_CACHE = new HashMap<>();

    public SoulSurgeBlock() {
        super("soul_surge", Properties.copy(Blocks.IRON_BLOCK).forceSolidOn(), SoulSurgeBlockEntity.class);
        this.registerDefaultState(this.defaultBlockState().setValue(ENABLED, true).setValue(TOP_CONN, false).setValue(BlockStateProperties.WATERLOGGED, false));
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
        var pipeBlockstate = context.getLevel().getBlockState(context.getClickedPos().relative(context.getClickedFace()));
        var pipeConnection = pipeBlockstate.getBlock() instanceof SoulPipeBlock || pipeBlockstate.getBlock().equals(IndustrialForegoingSouls.SOUL_SURGE_BLOCK.getKey().get());
        var eastConnection = checkSurgeConnection(Direction.EAST, FacingUtil.Sideness.LEFT, context.getClickedFace(), context.getLevel(), context.getClickedPos());
        var westConnection = checkSurgeConnection(Direction.WEST, FacingUtil.Sideness.RIGHT, context.getClickedFace(), context.getLevel(), context.getClickedPos());
        var upConnection = checkSurgeConnection(Direction.SOUTH, FacingUtil.Sideness.TOP, context.getClickedFace(), context.getLevel(), context.getClickedPos());
        var downConnection = checkSurgeConnection(Direction.NORTH, FacingUtil.Sideness.BOTTOM, context.getClickedFace(), context.getLevel(), context.getClickedPos());

        var state = this.defaultBlockState();
        var fluid = context.getLevel().getFluidState(context.getClickedPos());
        if (fluid.is(FluidTags.WATER) && fluid.getAmount() == 8)
            state = state.setValue(BlockStateProperties.WATERLOGGED, true);

        return state
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
        return relativeBlockstate.getBlock() instanceof SoulPipeBlock || relativeBlockstate.getBlock().equals(IndustrialForegoingSouls.SOUL_SURGE_BLOCK.getKey().get());
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block blockIn, BlockPos fromPos, boolean p_220069_6_) {
        super.neighborChanged(state, level, pos, blockIn, fromPos, p_220069_6_);
        if (!level.isClientSide) {
            var facing = state.getValue(RotatableBlock.FACING_ALL);
            var needStateRefresh = false;
            var originalPipeConnection = state.getValue(TOP_CONN);
            var pipeBlockstate = level.getBlockState(pos.relative(facing));
            var pipeConnection = pipeBlockstate.getBlock() instanceof SoulPipeBlock || pipeBlockstate.getBlock().equals(IndustrialForegoingSouls.SOUL_SURGE_BLOCK.getKey().get());
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
        stateBuilder.add(BlockStateProperties.WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState p_56969_) {
        return p_56969_.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_56969_);
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
