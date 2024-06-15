package com.ikerleon.birdwmod.blocks;

import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class BirdFeederBlock extends Block{

    public static final BooleanProperty FILLED = BooleanProperty.of("filled");
    public static final EnumProperty<EnumBlockHalf> HALF = EnumProperty.of("half", EnumBlockHalf.class);

    Random random = new Random();

    private static final VoxelShape BASE_BOX = VoxelShapes.cuboid(0.0625 * 5, 0, 0.0625 * 5, 0.0625 * 11, 0.0625 * 1, 0.0625 * 11);
    private static final VoxelShape STICK_BOX = VoxelShapes.cuboid(0.0625 * 6.5, 0.0625 * 1, 0.0625 * 6.5, 0.0625 * 11.5, 0.0625 * 13, 0.0625 * 11.5);
    private static final VoxelShape PLATAFORM_BOX = VoxelShapes.cuboid(0.0625 * 6, 0.0625 * 13, 0.0625 * 6, 0.0625 * 10, 0.0625 * 14, 0.0625 * 10);
    private static final VoxelShape PLATAFORM2_BOX = VoxelShapes.cuboid(0.0625 * 4, 0.0625 * 14, 0.0625 * 4, 0.0625 * 12, 0.0625 * 16, 0.0625 * 12);

    private static final VoxelShape FEEDER_BASE_BOX = VoxelShapes.cuboid(0.0625 * 0, 0.0625 * 0, 0.0625 * 0, 0.0625 * 16, 0.0625 * 1, 0.0625 * 16);
    private static final VoxelShape FEEDER_BACK_BOX = VoxelShapes.cuboid(0.0625 * 0, 0.0625 * 1, 0.0625 * 15, 0.0625 * 16, 0.0625 * 3, 0.0625 * 16);
    private static final VoxelShape FEEDER_FRONT_BOX = VoxelShapes.cuboid(0.0625 * 0, 0.0625 * 0, 0.0625 * 0, 0.0625 * 16, 0.0625 * 2, 0.0625 * 1);
    private static final VoxelShape FEEDER_LEFT_BOX = VoxelShapes.cuboid(0.0625 * 15, 0.0625 * 1, 0.0625 * 1, 0.0625 * 16, 0.0625 * 3, 0.0625 * 15);
    private static final VoxelShape FEEDER_RIGHT_BOX = VoxelShapes.cuboid(0.0625 * 0, 0.0625 * 1, 0.0625 * 1, 0.0625 * 1, 0.0625 * 3, 0.0625 * 15);
    private static final VoxelShape FEEDER_STICK_BOX = VoxelShapes.cuboid(0.0625 * 6.5, 0.0625 * 1, 0.0625 * 6.5, 0.0625 * 9.5, 0.0625 * 8, 0.0625 * 9.5);
    private static final VoxelShape FEEDER_PLATAFORM_BOX = VoxelShapes.cuboid(0.0625 * 5.5, 0.0625 * 8, 0.0625 * 5.5, 0.0625 * 10.5, 0.0625 * 9, 0.0625 * 10.5);
    private static final VoxelShape FEEDER_STICK2_BOX = VoxelShapes.cuboid(0.0625 * 6.5, 0.0625 * 9, 0.0625 * 6.5, 0.0625 * 9.5, 0.0625 * 15, 0.0625 * 9.5);
    private static final VoxelShape FEEDER_TOP_BOX = VoxelShapes.cuboid(0.0625 * 7, 0.0625 * 15, 0.0625 * 7, 0.0625 * 9, 0.0625 * 16, 0.0625 * 9);

    public BirdFeederBlock() {
        super(AbstractBlock.Settings.copy(Blocks.ACACIA_PLANKS).nonOpaque());

        setDefaultState(getStateManager().getDefaultState().with(FILLED, false).with(HALF, EnumBlockHalf.LOWER));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FILLED).add(HALF);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        world.setBlockState(pos.up(), this.getDefaultState().with(HALF, EnumBlockHalf.UPPER), 2);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if(state.get(HALF) == EnumBlockHalf.LOWER) {

            return VoxelShapes.union(STICK_BOX, PLATAFORM_BOX, BASE_BOX, PLATAFORM2_BOX);
        }
        else if(state.get(HALF) == EnumBlockHalf.UPPER) {

            return VoxelShapes.union(FEEDER_BASE_BOX, FEEDER_BACK_BOX, FEEDER_FRONT_BOX, FEEDER_LEFT_BOX, FEEDER_RIGHT_BOX, FEEDER_STICK_BOX, FEEDER_PLATAFORM_BOX, FEEDER_STICK2_BOX, FEEDER_TOP_BOX);
        }
        else{
            return VoxelShapes.fullCube();
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if(state.get(HALF) == EnumBlockHalf.LOWER) {

            return VoxelShapes.union(STICK_BOX, PLATAFORM_BOX, BASE_BOX, PLATAFORM2_BOX);
        }
        else if(state.get(HALF) == EnumBlockHalf.UPPER) {

            return VoxelShapes.union(FEEDER_BASE_BOX, FEEDER_BACK_BOX, FEEDER_FRONT_BOX, FEEDER_LEFT_BOX, FEEDER_RIGHT_BOX, FEEDER_STICK_BOX, FEEDER_PLATAFORM_BOX, FEEDER_STICK2_BOX, FEEDER_TOP_BOX);
        }
        else{
            return VoxelShapes.fullCube();
        }
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        ItemStack stack = player.getMainHandStack();

        if (world.getBlockState(pos).get(HALF)== EnumBlockHalf.UPPER && !world.getBlockState(pos).get(FILLED)) {
            if (stack.getItem() == Items.WHEAT_SEEDS && stack.getCount() >= 20) {
                world.setBlockState(pos, state.with(FILLED, true));
                stack.decrement(20);
                return ActionResult.SUCCESS;
            }
            else {
                return super.onUse(state, world, pos, player, hit);
            }
        }
        else {
            return super.onUse(state, world, pos, player, hit);
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    //TODO
    //@Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        //super.randomTick(state, world, pos, random);

        if(random.nextInt(10)==1) {
            if (state.get(HALF) == EnumBlockHalf.UPPER) {
                if (state.get(FILLED)) {
                    world.setBlockState(pos, state.with(FILLED, false));
                }
            }
        }
    }


    @Override
    public void neighborUpdate(BlockState state, World worldIn, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (state.get(HALF) == EnumBlockHalf.UPPER)
        {
            BlockPos blockpos = pos.down();
            BlockState iblockstate = worldIn.getBlockState(blockpos);

            if (iblockstate.getBlock() != this)
            {
                if(state.get(FILLED) && !worldIn.isClient()){
                    ItemStack seeds = new ItemStack(Items.WHEAT_SEEDS, this.random.nextInt(20));
                    dropStack(worldIn, pos, seeds);
                }
                worldIn.removeBlock(pos, false);
            }
            else if (block != this)
            {
                iblockstate.neighborUpdate(worldIn, blockpos, block, fromPos, notify);
            }
        }
        else {
            BlockPos blockpos1 = pos.up();
            BlockState iblockstate1 = worldIn.getBlockState(blockpos1);

            if (iblockstate1.getBlock() != this) {
                worldIn.removeBlock(pos, false);
            }
        }
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if(state.get(HALF)== EnumBlockHalf.UPPER){
            if(state.get(FILLED) && !world.isClient()){
                ItemStack seeds = new ItemStack(Items.WHEAT_SEEDS, this.random.nextInt(20));
                dropStack(world, pos, seeds);
            }
        }
        return super.onBreak(world, pos, state, player);
    }

    public enum EnumBlockHalf implements StringIdentifiable
    {
        UPPER,
        LOWER;

        public String toString()
        {
            return this.getName();
        }

        public String getName()
        {
            return this == UPPER ? "upper" : "lower";
        }

        @Override
        public String asString() {
            return this == UPPER ? "upper" : "lower";
        }
    }
}
