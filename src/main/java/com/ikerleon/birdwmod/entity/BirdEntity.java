package com.ikerleon.birdwmod.entity;

import com.ikerleon.birdwmod.blocks.InitBlocks;
import com.ikerleon.birdwmod.blocks.RingingNetBlock;
import com.ikerleon.birdwmod.entity.goal.BirdSwimGoal;
import com.ikerleon.birdwmod.entity.goal.EatFromFeedersGoal;
import com.ikerleon.birdwmod.entity.goal.FollowLeaderGoal;
import com.ikerleon.birdwmod.entity.move.MoveControlFlying;
import com.ikerleon.birdwmod.items.InitItems;
import com.ikerleon.birdwmod.items.ItemBirdSpawnEgg;
import com.ikerleon.birdwmod.util.SoundHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.keyframe.event.SoundKeyframeEvent;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class BirdEntity extends AnimalEntity implements GeoEntity {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    private final RawAnimation FLYING_ANIM = RawAnimation.begin().then("animation.bat.fly", Animation.LoopType.LOOP);
    private final RawAnimation IDLE_ANIM = RawAnimation.begin().then("animation.bat.idle", Animation.LoopType.LOOP);
    //Variables
    protected static final TrackedData<Integer> GENDER;
    protected static final TrackedData<Integer> VARIANT;
    protected static final TrackedData<Boolean> SLEEPING;
    private static final TrackedData<Integer> RING_COLOR;
    private static final TrackedData<Boolean> RINGED;

    private final Settings settings;

    public float timer;
    public int timeUntilNextFeather;
    protected boolean blink = false;
    private byte nextBlink = 0;
    private byte blinkTime = 0;
    private byte blinkSec = 0;
    private BirdEntity leader;
    private int groupSize = 1;

    public enum MeatSize{SMALL, MEDIUM, BIG};
    public enum CallType{BOTH_CALL, MALES_ONLY, GENDERED_CALLS, NO_CALL, MOCKINGBIRD};
    public enum FeatherType{BOTH_DROP, MALES_ONLY, GENDERED_DROPS};
    public enum AwakeTime{DIURNAL, NOCTURNAL};

    //Entity constructor and stuff
    public BirdEntity(EntityType<? extends AnimalEntity> type, World worldIn, Settings settings) {
        super(type, worldIn);
        this.settings = settings;
        this.setGender(random.nextInt(2));
        this.setVariant(getRandom().nextInt(getBirdVariants()));
        this.timeUntilNextFeather = getRandom().nextInt(10000) + 10000;
        this.moveControl = new MoveControlFlying(this, 30, false);
        this.goalSelector.add(1, new BirdSwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 2.0D));
        this.goalSelector.add(2, new FleeEntityGoal<>(this, OcelotEntity.class, 15.0F, 1.5D, 2D));
        this.goalSelector.add(2, new FleeEntityGoal<>(this, CatEntity.class, 15.0F, 1.5D, 2D));
        if (this.settings.doesGoToFeeders) this.goalSelector.add(3, new EatFromFeedersGoal(this));
        this.goalSelector.add(4, new FleeEntityGoal<>(this, PlayerEntity.class, 15.0F, 1.0D, 1.2D));
        //this.goalSelector.add(5, new FlyOntoTreeGoal(this, 1.0D));
        if (this.settings.doesGroupBird) this.goalSelector.add(5, new FollowLeaderGoal(this));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));

        this.ignoreCameraFrustum = true;
    }

    public static class Settings {
        int birdVariants = 1;
        int birdVariantsFemaleSpecific = 1;
        boolean dimorphic = false;
        double movementSpeed = 0.2D;
        double flightSpeed = 0.7D;
        double maxHealth = 5.0D;
        float width = 0.3f;
        float height = 0.3f;
        float factor = 1f;
        boolean doesGoInWater = false;
        boolean doesGoToFeeders = false;
        boolean doesGroupBird = false;
        String path = "eurasian_bullfinch";
        MeatSize meatSize = MeatSize.SMALL;
        CallType callType = CallType.BOTH_CALL;
        FeatherType featherType = FeatherType.BOTH_DROP;
        AwakeTime awakeTime = AwakeTime.DIURNAL;
        Item featherItem = null;
        Item featherItemFemaleSpecific = null;
        SoundEvent callSound = null;
        SoundEvent callSoundFemaleSpecific = null;
        SoundEvent flyingSound = null;
        public enum BiomeTemperature{FROZEN, COLD, TEMPERATE, WARM, HOT, ALL_WARMER_THAN_COLD, UNSPECIFIED};
        List<BiomeDescriptor> spawnBiomes = new ArrayList<>() {};
        int prevalence = 15;
        int minGroupSize = 1;
        int maxGroupSize = 1;

        public static class BiomeDescriptor {
            //private final Biome.Category biomeCategory;
            private final BiomeTemperature temperature;

            public BiomeDescriptor(/*Biome.Category biomeCategory,*/ BiomeTemperature temperature) {
                //this.biomeCategory = biomeCategory;
                this.temperature = temperature;
            }

            //public Biome.Category getBiomeCategory() { return this.biomeCategory;}
            public double[] getTemperatureRange() {
                // These ranges are min ([0]) exclusive, max ([1]) inclusive. Overlap is OK.
                double[] temperature = new double[2];
                // for reference: https://minecraft.fandom.com/wiki/Biome#List_of_Overworld_climates
                switch(this.temperature){
                    case FROZEN:
                        temperature[0] = -10;
                        temperature[1] = 0.1;
                    case COLD:
                        temperature[0] = 0.1;
                        temperature[1] = 0.55;
                    case TEMPERATE:
                        temperature[0] = 0.45;
                        temperature[1] = 0.75;
                    case WARM:
                        temperature[0] = 0.75;
                        temperature[1] = 0.95;
                        break;
                    case HOT:
                        temperature[0] = 0.9;
                        temperature[1] = 10;
                        break;
                    case ALL_WARMER_THAN_COLD: // Used if we don't care how hot it gets
                        temperature[0] = 0.55;
                        temperature[1] = 10;
                    case UNSPECIFIED:
                    default:
                        temperature[0] = -10;
                        temperature[1] = 10;
                        break;
                }
                return temperature;}
        }
//TODO
        /*public Settings withSpawnBiome(Biome.Category biome, BiomeTemperature temperature) {
            this.spawnBiomes.add(new BiomeDescriptor(biome, temperature));
            return this;
        }

        public Settings withSpawnBiome(Biome.Category biome) {
            this.spawnBiomes.add(new BiomeDescriptor(biome, BiomeTemperature.UNSPECIFIED));
            return this;
        }*/

        public String spawnBiomesAsString() {
            StringBuilder builder = new StringBuilder();
            for(int i=0; i < spawnBiomes.size(); i++){
                BiomeDescriptor descriptor = spawnBiomes.get(i);
                switch(descriptor.temperature){
                    case HOT -> builder.append("Hot");
                    case WARM -> builder.append("Warm");
                    case TEMPERATE -> builder.append("Temperate");
                    case COLD -> builder.append("Cold");
                    case FROZEN -> builder.append("Frozen");
                    case ALL_WARMER_THAN_COLD -> builder.append("Non-cold");
                    case UNSPECIFIED -> builder.append("Any");
                }
                builder.append(" ");
                //TODO
                //String rawName = descriptor.biomeCategory.asString();
                //builder.append(rawName.substring(0, 1).toUpperCase());
                //builder.append(rawName.substring(1).replaceAll("_", " "));
                if(i != spawnBiomes.size() - 1){builder.append(", ");}
            }
            return builder.toString();
        }

        public Settings withCall(SoundEvent callSound, SoundEvent callSoundFemaleSpecific){
            this.callSound = callSound;
            this.callSoundFemaleSpecific = callSoundFemaleSpecific;
            return this;
        }

        public Settings withCall(SoundEvent callSound){
            return this.withCall(callSound, null);
        }

        public Settings withFlyingSound(SoundEvent flyingSound){
            this.flyingSound = flyingSound;
            return this;
        }

        public Settings withPath(String path){
            this.path = path;
            return this;
        }

        public Settings withScaleFactor(Float factor){
            this.factor = factor;
            return this;
        }

        public Settings isDimorphic(){
            this.dimorphic = true;
            return this;
        }

        public Settings withDimensions(float width, float height){
            this.width = width;
            this.height = height;
            return this;
        }

        public Settings withFeather(Item featherItem, Item femaleSpecificFeatherItem){
            this.featherItem = featherItem;
            this.featherItemFemaleSpecific = femaleSpecificFeatherItem;
            return this;
        }

        public Settings withFeather(Item featherItem){
            return this.withFeather(featherItem, null);
        }

        public Settings withVariants(int numVariants){
            this.birdVariants = numVariants;
            return this;
        }

        public Settings withVariants(int numVariants, int numVariantsFemaleSpecific){
            this.birdVariants = numVariants;
            this.birdVariants = numVariantsFemaleSpecific;
            return this;
        }

        public Settings withBirdAttributes(double movementSpeed, double flightSpeed, double maxHealth){
            this.movementSpeed = movementSpeed;
            this.flightSpeed = flightSpeed;
            this.maxHealth = maxHealth;
            return this;
        }

        public Settings goesToFeeders(){
            this.doesGoToFeeders = true;
            return this;
        }

        public Settings isWaterBird(){
            this.doesGoInWater = true;
            return this;
        }

        public Settings isGroupBird(){
            this.doesGroupBird = true;
            return this;
        }

        public Settings withMeatSize(MeatSize meatSize){
            this.meatSize = meatSize;
            return this;
        }

        public Settings withCallType(CallType callType){
            this.callType = callType;
            return this;
        }

        public Settings withFeatherType(FeatherType featherType){
            this.featherType = featherType;
            return this;
        }

        public Settings withAwakeTime(AwakeTime awakeTime){
            this.awakeTime = awakeTime;
            return this;
        }

        public Settings withSpawnGroupSize(int minGroupSize, int maxGroupSize){
            this.minGroupSize = minGroupSize;
            this.maxGroupSize = maxGroupSize;
            return this;
        }

        public Settings withPrevalence(int prevalence){
            this.prevalence = prevalence;
            return this;
        }

        public DefaultAttributeContainer.Builder createBirdAttributes() {
            return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, this.movementSpeed).add(EntityAttributes.GENERIC_FLYING_SPEED, this.flightSpeed).add(EntityAttributes.GENERIC_MAX_HEALTH, this.maxHealth);
        }

        public List<BiomeDescriptor> getSpawnBiomes(){ return this.spawnBiomes; }
    }

//    // Required by GeckoLib
//    private AnimationFactory factory = new AnimationFactory(this);
//
//    // TODO: need to pass something in here in place of predicate (https://geckolib.com/en/latest/3.0.0/entity_animations/)
//    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
//    {
//
//        if(Objects.equals(event.getController().getName(), "songcontroller")){
//            AnimationController controller = event.getController();
//            controller.markNeedsReload();
//            if (controller.getAnimationState() == AnimationState.Stopped && this.isOnGround() && random.nextInt(100)<2) {
//                controller.setAnimation(new AnimationBuilder().addAnimation("song", false));
//            }
//        }
//        return PlayState.CONTINUE;
//    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<BirdEntity> songcontroller = new AnimationController<>(this, "songcontroller", 20, this::predicate);
        songcontroller.setSoundKeyframeHandler(this::soundListener);
        controllers.add(songcontroller);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }

    private PlayState predicate(AnimationState<BirdEntity> event) {
        AnimationController<BirdEntity> controller = event.getController();
        if(Objects.equals(event.getController().getName(), "songcontroller")){
            controller.forceAnimationReset();
            if (controller.getAnimationState() == AnimationController.State.STOPPED && this.isOnGround() && random.nextInt(100)<2) {
                controller.setAnimation(RawAnimation.begin().then("song", Animation.LoopType.PLAY_ONCE));
            }
        }
        if(!this.isOnGround()) {
            if(controller.getCurrentAnimation() != null) {
                if(controller.getCurrentRawAnimation() != FLYING_ANIM) {
                    controller.setAnimation(FLYING_ANIM);
                }
            } else {
                controller.setAnimation(FLYING_ANIM);
            }
        } else {
            if(controller.getCurrentAnimation() != null) {
                if(controller.getCurrentRawAnimation() != IDLE_ANIM) {
                    controller.setAnimation(IDLE_ANIM);
                }
            } else {
                controller.setAnimation(IDLE_ANIM);
            }   
        }
        return PlayState.CONTINUE;
    }

    private void soundListener(SoundKeyframeEvent<BirdEntity> event) {
        BirdEntity bird = event.getAnimatable();
        if(this.getId() != bird.getId()){ return; }
        if(bird.getWorld().isClient()) {
            System.out.println("3");
            switch (settings.callType) {
                case BOTH_CALL:
                    bird.getWorld().playSound(bird.getX(), bird.getY(), bird.getZ(), settings.callSound, SoundCategory.AMBIENT, bird.getSoundVolume(), getSoundPitch(), false);
                    break;
                case MALES_ONLY:
                    if (this.getGender() == 0)
                        bird.getWorld().playSound(bird.getX(), bird.getY(), bird.getZ(), settings.callSound, SoundCategory.AMBIENT, bird.getSoundVolume(), getSoundPitch(), false);
                    break;
                case GENDERED_CALLS:
                    if (this.getGender() == 0)
                        bird.getWorld().playSound(bird.getX(), bird.getY(), bird.getZ(), settings.callSound, SoundCategory.AMBIENT, bird.getSoundVolume(), getSoundPitch(), false);
                    else
                        bird.getWorld().playSound(bird.getX(), bird.getY(), bird.getZ(), settings.callSoundFemaleSpecific, SoundCategory.AMBIENT, bird.getSoundVolume(), getSoundPitch(), false);
                    break;
                case MOCKINGBIRD:  // A very special case!
                    // 50% chance to mimic
                    if (getRandom().nextBoolean())
                        this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), BirdSettings.MOCKINGBIRD_MIMICKABLE.get(getRandom().nextInt(BirdSettings.MOCKINGBIRD_MIMICKABLE.size())), SoundCategory.AMBIENT, this.getSoundVolume(), getSoundPitch(), false);
                    else {
                        if (this.getGender() == 0)
                            this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), SoundHandler.MOCKINGBIRD_SONG, SoundCategory.AMBIENT, this.getSoundVolume(), getSoundPitch(), false);
                        else
                            this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), SoundHandler.MOCKINGBIRD_CALL, SoundCategory.AMBIENT, this.getSoundVolume(), getSoundPitch(), false);
                    }
                    break;
            }
        }
    }

    static {
        GENDER = DataTracker.registerData(BirdEntity.class, TrackedDataHandlerRegistry.INTEGER);
        VARIANT = DataTracker.registerData(BirdEntity.class, TrackedDataHandlerRegistry.INTEGER);
        SLEEPING = DataTracker.registerData(BirdEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        RING_COLOR = DataTracker.registerData(BirdEntity.class, TrackedDataHandlerRegistry.INTEGER);
        RINGED = DataTracker.registerData(BirdEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height() * 0.75F;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(VARIANT, 0);
        builder.add(GENDER, 0);
        builder.add(SLEEPING, false);
        builder.add(RING_COLOR, DyeColor.GRAY.getId());
        builder.add(RINGED, false);
    }

    //NBT write and read methods
    public void writeCustomDataToNbt(NbtCompound tagCompound) {
        super.writeCustomDataToNbt(tagCompound);
        tagCompound.putInt("Gender", this.getGender());
        tagCompound.putInt("Variant", this.getVariant());
        tagCompound.putBoolean("Sleeping", this.isSleeping());
        tagCompound.putInt("CollarColor", (byte)this.getRingColor().getId());
        tagCompound.putBoolean("Ringed", this.hasBeenRinged());
    }

    public void readCustomDataFromNbt(NbtCompound tagCompound) {
        super.writeCustomDataToNbt(tagCompound);
        this.setGender(tagCompound.getInt("Gender"));
        this.setVariant(tagCompound.getInt("Variant"));
        this.setSleeping(tagCompound.getBoolean("Sleeping"));
        this.setRinged(tagCompound.getBoolean("Ringed"));
        if (tagCompound.contains("CollarColor", 99))
        {
            this.setRingColor(DyeColor.byId(tagCompound.getInt("CollarColor")));
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    public static boolean canSpawnThere(EntityType<? extends HostileEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return canMobSpawn(type, world, spawnReason, pos, random);
    }

    //Flying code
    @Override
    protected EntityNavigation createNavigation(World world) {
        if (this.isBaby()) {
            return super.createNavigation(world);
        }
        BirdNavigation pathnavigateflying = new BirdNavigation(this, world);
        pathnavigateflying.setCanSwim(true);
        pathnavigateflying.setCanEnterOpenDoors(true);
        return pathnavigateflying;
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) { }

    @Override
    public void move(MovementType type, Vec3d movement) {
        if(this.isInNet()){
            if(this.getWorld().getBlockState(new BlockPos(this.getBlockX(), this.getBlockY(), this.getBlockZ())).get(RingingNetBlock.DIRECTION) == RingingNetBlock.EnumBlockDirection.NORTH) {
                this.setPos(this.getPos().x, this.getPos().y, (this.getPos().z - this.getPos().z % 1));
            }
            else{
                this.setPos((this.getPos().x - this.getPos().x % 1), this.getPos().y, this.getPos().z);
            }
        }
        else{
            super.move(type, movement);
        }
    }

    //Entity stuff
    public int getLimitPerChunk() {
        return this.getMaxGroupSize();
    }

    public int getMaxGroupSize() {
        return super.getLimitPerChunk();
    }

    protected boolean hasSelfControl() {
        return !this.hasLeader();
    }

    public boolean hasLeader() {
        return this.leader != null && this.leader.isAlive();
    }

    public BirdEntity joinGroupOf(BirdEntity groupLeader) {
        this.leader = groupLeader;
        groupLeader.increaseGroupSize();
        return groupLeader;
    }

    public void leaveGroup() {
        this.leader.decreaseGroupSize();
        this.leader = null;
    }

    private void increaseGroupSize() {
        ++this.groupSize;
    }

    private void decreaseGroupSize() {
        --this.groupSize;
    }

    public boolean canHaveMoreBirdInGroup() {
        return this.hasOtherBirdInGroup() && this.groupSize < this.getMaxGroupSize();
    }

    public boolean hasOtherBirdInGroup() {
        return this.groupSize > 1;
    }

    public boolean isCloseEnoughToLeader() {
        return this.squaredDistanceTo(this.leader) <= 121.0D;
    }

    public void moveTowardLeader() {
        if (this.hasLeader()) {
            this.getNavigation().startMovingTo(this.leader, 1.0D);
        }

    }

    public void pullInOtherBird(Stream<? extends BirdEntity> bird) {
        bird.limit((long)(this.getMaxGroupSize() - this.groupSize)).filter((birdx) -> {
            return birdx != this;
        }).forEach((birdx) -> {
            birdx.joinGroupOf(this);
        });
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @org.jetbrains.annotations.Nullable EntityData entityData) {
        super.initialize((ServerWorldAccess) world, difficulty, spawnReason, entityData);
        if (entityData == null) {
            entityData = new BirdEntity.BirdData(this);
        } else {
            this.joinGroupOf(((BirdEntity.BirdData)entityData).leader);
        }
        return entityData;
    }

    @Override
    public void tickMovement() {
        switch(settings.awakeTime) {
            case DIURNAL:
                if(this.isOnGround()) {
                    setSleeping(getWorld().getTimeOfDay() >= 12969 && getWorld().getTimeOfDay() <= 23031);
                }
                break;
            case NOCTURNAL:
                if(this.isOnGround()) {
                    setSleeping(getWorld().getTimeOfDay() <= 12969);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown enum for awake time, check AwakeTime!");
        }

        if(this.isInNet()){
            this.fallDistance = 0.0F;
            this.setOnGround(true);
        }

        if((!isOnGround() && !isTouchingWater()) || (this.isAquatic() && isTouchingWater()) || (isSleeping())) {
            timer+=0.05F;
        }
        else{
            timer = 0.0F;
        }
        if(!this.isSleeping()) {
            if (this.random.nextInt(5) == 3) {
                this.blinkSec = ((byte) (getRandom().nextInt((byte) 50) + 30));
            }
            if (!this.blink) {
                this.nextBlink = ((byte) (this.nextBlink + 1));
            }
            if (this.nextBlink >= this.blinkSec) {
                this.blink = true;
                this.nextBlink = 0;
            }
            if (this.blink) {
                this.blinkTime = ((byte) (this.blinkTime + 1));
            }
            if (this.blinkTime >= 3) {
                this.blink = false;
                this.blinkTime = 0;
            }
        }
        if(this.hasBeenRinged()){
            this.setPersistent();
        }
        super.tickMovement();
    }

    public void tick() {
        super.tick();
        if (this.hasOtherBirdInGroup() && this.getWorld().random.nextInt(200) == 1) {
            List<? extends BirdEntity> list = this.getWorld().getNonSpectatingEntities(this.getClass(), this.getBoundingBox().expand(8.0D, 8.0D, 8.0D));
            if (list.size() <= 1) {
                this.groupSize = 1;
            }
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getStackInHand(hand);

        if (itemstack.getItem() == InitItems.RING)
        {
            if(this.hasBeenRinged() == false){
                this.setRinged(true);
                player.experienceLevel = player.experienceLevel + 1;
                if (!player.isCreative())
                {
                    itemstack.decrement(1);
                }
            }
            return ActionResult.SUCCESS;

        }
        else if (itemstack.getItem() instanceof ItemBirdSpawnEgg)
        {
            ItemBirdSpawnEgg egg = (ItemBirdSpawnEgg)itemstack.getItem();

            if(egg.entityType == this.getType()){
                if (!this.getWorld().isClient())
                {
                    MobEntity mobEntity3;
                    mobEntity3 = this.createChild((ServerWorld)this.getWorld(), this);
                    if(mobEntity3 == null) return ActionResult.FAIL;
                    mobEntity3.setBaby(true);
                    mobEntity3.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
                    getWorld().spawnEntity(mobEntity3);
                    if (itemstack.getName() != null) {
                        mobEntity3.setCustomName(itemstack.getName());
                    }

                    if (!player.isCreative()) {
                        itemstack.decrement(1);
                    }
                }
            }
            return ActionResult.SUCCESS;

        }
        else if (itemstack.getItem() instanceof DyeItem && this.hasBeenRinged())
        {
            DyeItem dye = (DyeItem) itemstack.getItem();
            DyeColor enumdyecolor = dye.getColor();

            if (enumdyecolor != this.getRingColor())
            {
                this.setRingColor(enumdyecolor);

                if (!player.isCreative())
                {
                    itemstack.decrement(1);
                }

                return ActionResult.SUCCESS;
            }
        }
        return super.interactMob(player, hand);
    }

    //Sleeping code
    public boolean isSleeping() {
        return this.dataTracker.get(SLEEPING);
    }
    public void setSleeping(boolean value) {
        this.dataTracker.set(SLEEPING, Boolean.valueOf(value));
    }

    public boolean isInNet(){
        Block block = this.getWorld().getBlockState(new BlockPos(this.getBlockPos())).getBlock();
        if(block == InitBlocks.RINGING_NET) {
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    protected float getSoundVolume() {
        return 0.5F;
    }

    //Blinking code
    public boolean getBlinking()
    {
        return this.blink;
    }


    //NBT Tags getters and setters
    public int getGender() {
        return this.dataTracker.get(GENDER);
    }

    public void setGender(int value) {
        this.dataTracker.set(GENDER, value);
    }

    public int getVariant() {
        return this.dataTracker.get(VARIANT);
    }

    public void setVariant(int value) {
        this.dataTracker.set(VARIANT, value);
    }

    public DyeColor getRingColor()
    {
        return DyeColor.byId(this.dataTracker.get(RING_COLOR) & 15);
    }

    public void setRingColor(DyeColor collarcolor)
    {
        this.dataTracker.set(RING_COLOR, collarcolor.getId());
    }

    public boolean hasBeenRinged() {
        return this.dataTracker.get(RINGED);
    }

    public void setRinged(boolean value) {
        this.dataTracker.set(RINGED, value);
    }

    public static class BirdData extends PassiveData {
        public final BirdEntity leader;

        public BirdData(BirdEntity leader) {
            super(true);
            this.leader = leader;
        }
    }

    public int getBirdVariants() {
        return settings.birdVariants;
    }

    public int getBirdVariantsFemaleSpecific() { return settings.birdVariantsFemaleSpecific; }

    public boolean isDimorphic(){return settings.dimorphic;}

    public String getPath() { return settings.path; }
    public float getScaleFactor() { return settings.factor; }

    public Item getFeatherItem() {
        String featherPath = "birdwmod:feather_"+getPath();
        switch(settings.featherType) {
            // For now, every case but a female gendered drop is a no-op
            case GENDERED_DROPS:
                if (this.getGender() == 1) {featherPath += "_female";}
            case BOTH_DROP:
            default:
                // TODO: was the third case a bug?
        }
        return Registries.ITEM.get(Identifier.of(featherPath));
    }

    @Override
    public void mobTick() {
        if (!this.getWorld().isClient() && !this.isBaby() && --this.timeUntilNextFeather <= 0)
        {
            this.dropItem(getFeatherItem(), 1);
            this.timeUntilNextFeather = this.random.nextInt(10000) + 10000;
        }
        super.mobTick();
        if(this.isAquatic() && this.isTouchingWater() && !this.isBaby()){
            this.upwardSpeed=0;
        }
    }

    @Override
    protected void dropLoot(DamageSource source, boolean causedByPlayer) {
        Item cookedItem;
        Item rawItem;
        switch (settings.meatSize) {
            case SMALL -> {
                cookedItem = InitItems.SMALLCOOCKEDMEAT;
                rawItem = InitItems.SMALLRAWMEAT;
            }
            case MEDIUM -> {
                cookedItem = InitItems.MEDIUMCOOCKEDMEAT;
                rawItem = InitItems.MEDIUMRAWMEAT;
            }
            case BIG -> {
                cookedItem = InitItems.BIGCOOCKEDMEAT;
                rawItem = InitItems.BIGRAWMEAT;
            }
            default -> throw new IllegalArgumentException("Unknown enum for bird meat, check MeatSize!");
        }
        if(this.isOnFire())
            this.dropItem(cookedItem, 1);
        else
            this.dropItem(rawItem, 1);
    }


    @Override
    protected SoundEvent getAmbientSound() {
        if(settings.flyingSound != null && !isSleeping() && !this.isOnGround()){
            return settings.flyingSound;
        }
        return null;
    }

    @Override
    public void playAmbientSound() {
        switch(settings.callType) {
            case BOTH_CALL:
                if (this.isOnGround() && !isSleeping()) {

                }
                break;
            case MALES_ONLY:
                if (this.isOnGround() && !isSleeping() && this.getGender() == 0) {
                    //return callSound;
                }
                break;
            case GENDERED_CALLS:
                if (this.isOnGround() && !isSleeping()) {
                    if (this.getGender() == 0) {
                        //return callSound;
                    } else {
                        //return callSoundFemaleSpecific;
                    }
                }
                break;
            case NO_CALL:
                break;
            case MOCKINGBIRD:  // A very special case!
                if(!isSleeping()) {
                    // 50% chance to mimic
                    if (getRandom().nextBoolean()) {
                        // return BirdSettings.MOCKINGBIRD_MIMICKABLE.get(getRandom().nextInt(BirdSettings.MOCKINGBIRD_MIMICKABLE.size()));
                    } else {
                        if (this.getGender() == 0) {
                            //return SoundHandler.MOCKINGBIRD_SONG;
                        } else {
                            //return SoundHandler.MOCKINGBIRD_CALL;
                        }
                    }
                }
                break;
        }
        super.playAmbientSound();
    }

    public boolean goesToFeeders() {
        return true;
    }

    public boolean isAquatic() {
        return false;
    }

    public boolean isGroupBird() {
        return true;
    }

    @Override
    public @org.jetbrains.annotations.Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return (BirdEntity)this.getType().create(world);
    }
}

