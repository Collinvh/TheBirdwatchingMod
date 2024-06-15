package com.ikerleon.birdwmod.blocks;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
public class LichenBlock extends Block {
    public static final BooleanProperty UP;
    public static final BooleanProperty NORTH;
    public static final BooleanProperty EAST;
    public static final BooleanProperty SOUTH;
    public static final BooleanProperty WEST;
    public static final Map<Direction, BooleanProperty> FACING_PROPERTIES;
    private static final VoxelShape UP_SHAPE;
    private static final VoxelShape EAST_SHAPE;
    private static final VoxelShape WEST_SHAPE;
    private static final VoxelShape SOUTH_SHAPE;
    private static final VoxelShape NORTH_SHAPE;
    private final Map<BlockState, VoxelShape> shapesByState;


    public LichenBlock() {
        super(AbstractBlock.Settings.copy(Blocks.TALL_GRASS).replaceable().noCollision().ticksRandomly().strength(0.2F).sounds(BlockSoundGroup.GLOW_LICHEN).nonOpaque().luminance((a) -> 7));
        this.setDefaultState(this.stateManager.getDefaultState().with(UP, false).with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false));
        this.shapesByState = ImmutableMap.copyOf(this.stateManager.getStates().stream().collect(Collectors.toMap(Function.identity(), LichenBlock::getShapeForState)));
    }

    private static VoxelShape getShapeForState(BlockState state) {
        VoxelShape voxelShape = VoxelShapes.empty();
        if (state.get(UP)) {
            voxelShape = UP_SHAPE;
        }

        if (state.get(NORTH)) {
            voxelShape = VoxelShapes.union(voxelShape, SOUTH_SHAPE);
        }

        if (state.get(SOUTH)) {
            voxelShape = VoxelShapes.union(voxelShape, NORTH_SHAPE);
        }

        if (state.get(EAST)) {
            voxelShape = VoxelShapes.union(voxelShape, WEST_SHAPE);
        }

        if (state.get(WEST)) {
            voxelShape = VoxelShapes.union(voxelShape, EAST_SHAPE);
        }

        return voxelShape.isEmpty() ? VoxelShapes.fullCube() : voxelShape;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.shapesByState.get(state);
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return this.hasAdjacentBlocks(this.getPlacementShape(state, world, pos));
    }

    private boolean hasAdjacentBlocks(BlockState state) {
        return this.getAdjacentBlockCount(state) > 0;
    }

    private int getAdjacentBlockCount(BlockState state) {
        int i = 0;

        for (BooleanProperty booleanProperty : FACING_PROPERTIES.values()) {
            if (state.get(booleanProperty)) {
                ++i;
            }
        }

        return i;
    }

    private boolean shouldHaveSide(BlockView world, BlockPos pos, Direction side) {
        if (side == Direction.DOWN) {
            return false;
        } else {
            BlockPos blockPos = pos.offset(side);
            if (shouldConnectTo(world, blockPos, side)) {
                return true;
            } else if (side.getAxis() == Direction.Axis.Y) {
                return false;
            } else {
                BooleanProperty booleanProperty = FACING_PROPERTIES.get(side);
                BlockState blockState = world.getBlockState(pos.up());
                return blockState.isOf(this) && blockState.get(booleanProperty);
            }
        }
    }

    public static boolean shouldConnectTo(BlockView world, BlockPos pos, Direction direction) {
        BlockState blockState = world.getBlockState(pos);
        return Block.isFaceFullSquare(blockState.getCollisionShape(world, pos), direction.getOpposite());
    }

    private BlockState getPlacementShape(BlockState state, BlockView world, BlockPos pos) {
        BlockPos blockPos = pos.up();
        if (state.get(UP)) {
            state = state.with(UP, shouldConnectTo(world, blockPos, Direction.DOWN));
        }

        BlockState blockState = null;
        Iterator<Direction> var6 = Direction.Type.HORIZONTAL.iterator();

        while(true) {
            Direction direction;
            BooleanProperty booleanProperty;
            do {
                if (!var6.hasNext()) {
                    return state;
                }

                direction = var6.next();
                booleanProperty = getFacingProperty(direction);
            } while(!(Boolean)state.get(booleanProperty));

            boolean bl = this.shouldHaveSide(world, pos, direction);
            if (!bl) {
                if (blockState == null) {
                    blockState = world.getBlockState(blockPos);
                }

                bl = blockState.isOf(this) && blockState.get(booleanProperty);
            }

            state = state.with(booleanProperty, bl);
        }
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN) {
            return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        } else {
            BlockState blockState = this.getPlacementShape(state, world, pos);
            return !this.hasAdjacentBlocks(blockState) ? Blocks.AIR.getDefaultState() : blockState;
        }
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(4) == 0) {
            Direction direction = Direction.random(random);
            BlockPos blockPos = pos.up();
            BlockPos blockPos2;
            BlockState blockState3;
            Direction direction5;
            if (direction.getAxis().isHorizontal() && !(Boolean)state.get(getFacingProperty(direction))) {
                if (this.canGrowAt(world, pos)) {
                    blockPos2 = pos.offset(direction);
                    blockState3 = world.getBlockState(blockPos2);
                    if (blockState3.isAir()) {
                        direction5 = direction.rotateYClockwise();
                        Direction direction3 = direction.rotateYCounterclockwise();
                        boolean bl = state.get(getFacingProperty(direction5));
                        boolean bl2 = state.get(getFacingProperty(direction3));
                        BlockPos blockPos3 = blockPos2.offset(direction5);
                        BlockPos blockPos4 = blockPos2.offset(direction3);
                        if (bl && shouldConnectTo(world, blockPos3, direction5)) {
                            world.setBlockState(blockPos2, this.getDefaultState().with(getFacingProperty(direction5), true), Block.NOTIFY_LISTENERS);
                        } else if (bl2 && shouldConnectTo(world, blockPos4, direction3)) {
                            world.setBlockState(blockPos2, this.getDefaultState().with(getFacingProperty(direction3), true), Block.NOTIFY_LISTENERS);
                        } else {
                            Direction direction4 = direction.getOpposite();
                            if (bl && world.isAir(blockPos3) && shouldConnectTo(world, pos.offset(direction5), direction4)) {
                                world.setBlockState(blockPos3, this.getDefaultState().with(getFacingProperty(direction4), true), Block.NOTIFY_LISTENERS);
                            } else if (bl2 && world.isAir(blockPos4) && shouldConnectTo(world, pos.offset(direction3), direction4)) {
                                world.setBlockState(blockPos4, this.getDefaultState().with(getFacingProperty(direction4), true), Block.NOTIFY_LISTENERS);
                            } else if ((double)random.nextFloat() < 0.05D && shouldConnectTo(world, blockPos2.up(), Direction.UP)) {
                                world.setBlockState(blockPos2, this.getDefaultState().with(UP, true), Block.NOTIFY_LISTENERS);
                            }
                        }
                    } else if (shouldConnectTo(world, blockPos2, direction)) {
                        world.setBlockState(pos, state.with(getFacingProperty(direction), true), Block.NOTIFY_LISTENERS);
                    }

                }
            } else {
                if (direction == Direction.UP && pos.getY() < world.getTopY() - 1) {
                    if (this.shouldHaveSide(world, pos, direction)) {
                        world.setBlockState(pos, state.with(UP, true), Block.NOTIFY_LISTENERS);
                        return;
                    }

                    if (world.isAir(blockPos)) {
                        if (!this.canGrowAt(world, pos)) {
                            return;
                        }

                        BlockState blockState2 = state;
                        Iterator<Direction> var17 = Direction.Type.HORIZONTAL.iterator();

                        while(true) {
                            do {
                                if (!var17.hasNext()) {
                                    if (this.hasHorizontalSide(blockState2)) {
                                        world.setBlockState(blockPos, blockState2, Block.NOTIFY_LISTENERS);
                                    }

                                    return;
                                }

                                direction5 = var17.next();
                            } while(!random.nextBoolean() && shouldConnectTo(world, blockPos.offset(direction5), direction5));

                            blockState2 = blockState2.with(getFacingProperty(direction5), false);
                        }
                    }
                }

                if (pos.getY() > world.getBottomY()) {
                    blockPos2 = pos.down();
                    blockState3 = world.getBlockState(blockPos2);
                    if (blockState3.isAir() || blockState3.isOf(this)) {
                        BlockState blockState4 = blockState3.isAir() ? this.getDefaultState() : blockState3;
                        BlockState blockState5 = this.getGrownState(state, blockState4, random);
                        if (blockState4 != blockState5 && this.hasHorizontalSide(blockState5)) {
                            world.setBlockState(blockPos2, blockState5, Block.NOTIFY_LISTENERS);
                        }
                    }
                }

            }
        }
    }

    private BlockState getGrownState(BlockState above, BlockState state, Random random) {
        for (Direction direction : Direction.Type.HORIZONTAL) {
            if (random.nextBoolean()) {
                BooleanProperty booleanProperty = getFacingProperty(direction);
                if (above.get(booleanProperty)) {
                    state = state.with(booleanProperty, true);
                }
            }
        }

        return state;
    }

    private boolean hasHorizontalSide(BlockState state) {
        return state.get(NORTH) || state.get(EAST) || state.get(SOUTH) || state.get(WEST);
    }

    private boolean canGrowAt(BlockView world, BlockPos pos) {
        Iterable<BlockPos> iterable = BlockPos.iterate(pos.getX() - 4, pos.getY() - 1, pos.getZ() - 4, pos.getX() + 4, pos.getY() + 1, pos.getZ() + 4);
        int j = 5;

        for (BlockPos blockPos : iterable) {
            if (world.getBlockState(blockPos).isOf(this)) {
                --j;
                if (j <= 0) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        BlockState blockState = context.getWorld().getBlockState(context.getBlockPos());
        if (blockState.isOf(this)) {
            return this.getAdjacentBlockCount(blockState) < FACING_PROPERTIES.size();
        } else {
            return super.canReplace(state, context);
        }
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        boolean bl = blockState.isOf(this);
        BlockState blockState2 = bl ? blockState : this.getDefaultState();
        Direction[] var5 = ctx.getPlacementDirections();

        for (Direction direction : var5) {
            if (direction != Direction.DOWN) {
                BooleanProperty booleanProperty = getFacingProperty(direction);
                boolean bl2 = bl && blockState.get(booleanProperty);
                if (!bl2 && this.shouldHaveSide(ctx.getWorld(), ctx.getBlockPos(), direction)) {
                    return blockState2.with(booleanProperty, true);
                }
            }
        }

        return bl ? blockState2 : null;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(UP, NORTH, EAST, SOUTH, WEST);
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return switch (rotation) {
            case CLOCKWISE_180 ->
                    state.with(NORTH, state.get(SOUTH)).with(EAST, state.get(WEST)).with(SOUTH, state.get(NORTH)).with(WEST, state.get(EAST));
            case COUNTERCLOCKWISE_90 ->
                    state.with(NORTH, state.get(EAST)).with(EAST, state.get(SOUTH)).with(SOUTH, state.get(WEST)).with(WEST, state.get(NORTH));
            case CLOCKWISE_90 ->
                    state.with(NORTH, state.get(WEST)).with(EAST, state.get(NORTH)).with(SOUTH, state.get(EAST)).with(WEST, state.get(SOUTH));
            default -> state;
        };
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return switch (mirror) {
            case LEFT_RIGHT -> state.with(NORTH, state.get(SOUTH)).with(SOUTH, state.get(NORTH));
            case FRONT_BACK -> state.with(EAST, state.get(WEST)).with(WEST, state.get(EAST));
            default -> super.mirror(state, mirror);
        };
    }

    public static BooleanProperty getFacingProperty(Direction direction) {
        return FACING_PROPERTIES.get(direction);
    }

    static {
        UP = ConnectingBlock.UP;
        NORTH = ConnectingBlock.NORTH;
        EAST = ConnectingBlock.EAST;
        SOUTH = ConnectingBlock.SOUTH;
        WEST = ConnectingBlock.WEST;
        FACING_PROPERTIES = ConnectingBlock.FACING_PROPERTIES.entrySet().stream().filter((entry) -> {
            return entry.getKey() != Direction.DOWN;
        }).collect(Util.toMap());
        UP_SHAPE = Block.createCuboidShape(0.0D, 15.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        EAST_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
        WEST_SHAPE = Block.createCuboidShape(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        SOUTH_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
        NORTH_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);
    }
}
