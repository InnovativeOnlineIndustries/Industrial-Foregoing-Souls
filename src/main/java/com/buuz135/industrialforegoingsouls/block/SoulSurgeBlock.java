package com.buuz135.industrialforegoingsouls.block;

import com.buuz135.industrialforegoingsouls.IndustrialForegoingSouls;
import com.buuz135.industrialforegoingsouls.block.tile.SoulSurgeBlockEntity;
import com.hrznstudio.titanium.block.BasicTileBlock;
import com.hrznstudio.titanium.block.RotatableBlock;
import com.hrznstudio.titanium.block_network.INetworkDirectionalConnection;
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

    public static VoxelShape NORTH = Stream.of(
            Block.box(5, 5, 0, 5, 11, 5),
            Block.box(11, 5, 0, 11, 11, 5),
            Block.box(5, 5, 0, 11, 5, 5),
            Block.box(5, 11, 0, 11, 11, 5),
            Block.box(7, 7, 0, 9, 9, 7),
            Block.box(4, 4, 5, 12, 12, 14),
            Block.box(3, 3, 6, 13, 13, 8),
            Block.box(3, 3, 14, 13, 13, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static VoxelShape EAST = Stream.of(
            Block.box(11, 5, 5, 16, 11, 5),
            Block.box(11, 5, 11, 16, 11, 11),
            Block.box(11, 5, 5, 16, 5, 11),
            Block.box(11, 11, 5, 16, 11, 11),
            Block.box(9, 7, 7, 16, 9, 9),
            Block.box(2, 4, 4, 11, 12, 12),
            Block.box(8, 3, 3, 10, 13, 13),
            Block.box(0, 3, 3, 2, 13, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static VoxelShape SOUTH = Stream.of(
            Block.box(11, 5, 11, 11, 11, 16),
            Block.box(5, 5, 11, 5, 11, 16),
            Block.box(5, 5, 11, 11, 5, 16),
            Block.box(5, 11, 11, 11, 11, 16),
            Block.box(7, 7, 9, 9, 9, 16),
            Block.box(4, 4, 2, 12, 12, 11),
            Block.box(3, 3, 8, 13, 13, 10),
            Block.box(3, 3, 0, 13, 13, 2)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static VoxelShape WEST = Stream.of(
            Block.box(0, 5, 11, 5, 11, 11),
            Block.box(0, 5, 5, 5, 11, 5),
            Block.box(0, 5, 5, 5, 5, 11),
            Block.box(0, 11, 5, 5, 11, 11),
            Block.box(0, 7, 7, 7, 9, 9),
            Block.box(5, 4, 4, 14, 12, 12),
            Block.box(6, 3, 3, 8, 13, 13),
            Block.box(14, 3, 3, 16, 13, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static VoxelShape UP = Stream.of(
            Block.box(5, 11, 11, 11, 16, 11),
            Block.box(5, 11, 5, 11, 16, 5),
            Block.box(5, 11, 5, 5, 16, 11),
            Block.box(11, 11, 5, 11, 16, 11),
            Block.box(7, 9, 7, 9, 16, 9),
            Block.box(4, 2, 4, 12, 11, 12),
            Block.box(3, 8, 3, 13, 10, 13),
            Block.box(3, 0, 3, 13, 2, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static VoxelShape DOWN = Stream.of(
            Block.box(5, 0, 5, 11, 5, 5),
            Block.box(5, 0, 11, 11, 5, 11),
            Block.box(5, 0, 5, 5, 5, 11),
            Block.box(11, 0, 5, 11, 5, 11),
            Block.box(7, 0, 7, 9, 7, 9),
            Block.box(4, 5, 4, 12, 14, 12),
            Block.box(3, 6, 3, 13, 8, 13),
            Block.box(3, 14, 3, 13, 16, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();


    public SoulSurgeBlock() {
        super("soul_surge", Properties.copy(Blocks.IRON_BLOCK), SoulSurgeBlockEntity.class);
        this.registerDefaultState(this.defaultBlockState().setValue(ENABLED, true));
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
        return state.getValue(RotatableBlock.FACING_ALL) == direction;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_196258_1_) {
        return this.defaultBlockState().setValue(RotatableBlock.FACING_ALL, p_196258_1_.getClickedFace()).setValue(ENABLED, !Boolean.valueOf(p_196258_1_.getLevel().hasNeighborSignal(p_196258_1_.getClickedPos())));
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block blockIn, BlockPos fromPos, boolean p_220069_6_) {
        super.neighborChanged(state, level, pos, blockIn, fromPos, p_220069_6_);
        if (!level.isClientSide) {
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
