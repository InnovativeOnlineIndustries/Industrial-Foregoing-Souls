package com.buuz135.industrialforegoingsouls.block;

import com.buuz135.industrialforegoingsouls.IndustrialForegoingSouls;
import com.buuz135.industrialforegoingsouls.block.tile.SoulPipeBlockEntity;
import com.buuz135.industrialforegoingsouls.config.IFSoulsClient;
import com.google.common.collect.ImmutableMap;
import com.hrznstudio.titanium.block.BasicTileBlock;
import com.hrznstudio.titanium.block_network.INetworkDirectionalConnection;
import com.hrznstudio.titanium.block_network.NetworkManager;
import com.hrznstudio.titanium.block_network.element.NetworkElement;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.mutable.MutableObject;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

public class SoulPipeBlock extends BasicTileBlock<SoulPipeBlockEntity> implements INetworkDirectionalConnection {

    public static final Map<Direction, EnumProperty<PipeState>> DIRECTIONS = new HashMap<>();
    public static final Map<Direction, VoxelShape> DIR_SHAPES = ImmutableMap.<Direction, VoxelShape>builder()
            .put(Direction.UP, Block.box(5, 10, 5, 11, 16, 11))
            .put(Direction.DOWN, Block.box(5, 0, 5, 11, 6, 11))
            .put(Direction.NORTH, Block.box(5, 5, 0, 11, 11, 6))
            .put(Direction.SOUTH, Block.box(5, 5, 10, 11, 11, 16))
            .put(Direction.EAST, Block.box(10, 5, 5, 16, 11, 11))
            .put(Direction.WEST, Block.box(0, 5, 5, 6, 11, 11))
            .build();
    private static final Map<BlockState, VoxelShape> SHAPE_CACHE = new HashMap<>();
    private static final Map<BlockState, VoxelShape> COLL_SHAPE_CACHE = new HashMap<>();
    private static final VoxelShape CENTER_SHAPE = Block.box(5, 5, 5, 11, 11, 11);

    static {
        for (Direction value : Direction.values()) {
            DIRECTIONS.put(value, EnumProperty.create(value.getName().toLowerCase(Locale.ROOT), PipeState.class));
        }
    }

    public SoulPipeBlock() {
        super("soul_network_pipe", Properties.copy(Blocks.IRON_BLOCK), SoulPipeBlockEntity.class);
        setItemGroup(IndustrialForegoingSouls.TAB);
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<?> getTileEntityFactory() {
        return (pos, state) -> new SoulPipeBlockEntity(this, IndustrialForegoingSouls.SOUL_PIPE_BLOCK.getRight().get(), pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(DIRECTIONS.values().toArray(Property[]::new));
        builder.add(BlockStateProperties.WATERLOGGED);
    }

    private BlockState createState(Level world, BlockPos pos, BlockState curr) {
        var state = this.defaultBlockState();
        var fluid = world.getFluidState(pos);
        if (fluid.is(FluidTags.WATER) && fluid.getAmount() == 8)
            state = state.setValue(BlockStateProperties.WATERLOGGED, true);

        for (var dir : Direction.values()) {
            var prop = DIRECTIONS.get(dir);
            var type = this.getConnectionType(world, pos, dir, state);
            state = state.setValue(prop, type);
        }
        return state;
    }

    protected PipeState getConnectionType(Level world, BlockPos pos, Direction direction, BlockState state) {
        var relativeState = world.getBlockState(pos.relative(direction));
        if (relativeState.getBlock() instanceof SoulPipeBlock) {
            return PipeState.PIPE;
        }
        if (relativeState.getBlock() instanceof INetworkDirectionalConnection networkDirectionalConnection && networkDirectionalConnection.canConnect(relativeState, direction.getOpposite())) {
            return PipeState.BLOCK;
        }
        return PipeState.NO;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.createState(context.getLevel(), context.getClickedPos(), this.defaultBlockState());
    }

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        var newState = this.createState(worldIn, pos, state);
        if (newState != state) {
            worldIn.setBlockAndUpdate(pos, newState);
        }
        if (!worldIn.isClientSide) {
            NetworkElement pipe = NetworkManager.get(worldIn).getElement(pos);

            if (pipe != null && pipe.getNetwork() != null) {
                pipe.getNetwork().scanGraph(worldIn, pos);
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return this.cacheAndGetShape(state, worldIn, pos, s -> s.getShape(worldIn, pos, context), SHAPE_CACHE, null);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return this.cacheAndGetShape(state, worldIn, pos, s -> s.getCollisionShape(worldIn, pos, context), COLL_SHAPE_CACHE, s -> {
            // make the shape a bit higher so we can jump up onto a higher block
            var newShape = new MutableObject<VoxelShape>(Shapes.empty());
            s.forAllBoxes((x1, y1, z1, x2, y2, z2) -> newShape.setValue(Shapes.join(Shapes.create(x1, y1, z1, x2, y2 + 3 / 16F, z2), newShape.getValue(), BooleanOp.OR)));
            return newShape.getValue().optimize();
        });
    }

    private VoxelShape cacheAndGetShape(BlockState state, BlockGetter worldIn, BlockPos pos, Function<BlockState, VoxelShape> coverShapeSelector, Map<BlockState, VoxelShape> cache, Function<VoxelShape, VoxelShape> shapeModifier) {
        var shape = cache.get(state);
        if (shape == null) {
            shape = CENTER_SHAPE;
            for (var entry : DIRECTIONS.entrySet()) {
                if (state.getValue(entry.getValue()) != PipeState.NO)
                    shape = Shapes.or(shape, DIR_SHAPES.get(entry.getKey()));
            }
            if (shapeModifier != null)
                shape = shapeModifier.apply(shape);
            cache.put(state, shape);
        }
        return shape;
    }

    @Override
    public boolean canConnect(BlockState state, Direction direction) {
        return true;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        for (Direction direction : Direction.values()) {
            var connected = state.getValue(DIRECTIONS.get(direction)) != PipeState.NO;
            var opositteConnected = state.getValue(DIRECTIONS.get(direction.getOpposite())) != PipeState.NO;
            if (random.nextDouble() <= IFSoulsClient.SOUL_PIPES_PARTICLES) {
                if (connected && opositteConnected) {
                    var vector = direction.getNormal();
                    var velocity = 12D;
                    var border = 0.34D;
                    var x = 0D;
                    var y = 0D;
                    var z = 0D;
                    if (direction.getAxis() == Direction.Axis.X) {
                        y += 0.5D + (random.nextBoolean() ? -border : border);
                        x += 0.5D;
                        z += 0.5D + (random.nextBoolean() ? -border : border);
                    } else if (direction.getAxis() == Direction.Axis.Z) {
                        y += 0.5D + (random.nextBoolean() ? -border : border);
                        x += 0.5D + (random.nextBoolean() ? -border : border);
                        z += 0.5D;
                    } else {
                        y += 0.5D;
                        x += 0.5D + (random.nextBoolean() ? -border : border);
                        z += 0.5D + (random.nextBoolean() ? -border : border);
                    }
                    level.addParticle(ParticleTypes.SCULK_SOUL, pos.getX() + x, pos.getY() + y, pos.getZ() + z, vector.getX() / velocity, vector.getY() / velocity, vector.getZ() / velocity);
                }
            }
        }
    }

    public enum PipeState implements StringRepresentable {
        NO,
        PIPE,
        BLOCK;

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase();
        }
    }
}
