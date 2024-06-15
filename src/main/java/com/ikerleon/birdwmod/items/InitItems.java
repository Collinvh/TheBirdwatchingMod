package com.ikerleon.birdwmod.items;

import com.ikerleon.birdwmod.entity.InitEntities;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class InitItems {

    //birwatching utils
    public static final Item BINOCULAR_BASIC = new ItemBinocular(15);
    public static final Item BINOCULAR_MEDIUM = new ItemBinocular(10);
    public static final Item BINOCULAR_PROFFESIONAL = new ItemBinocular(5);

    public static final Item RING = new ItemBirdwmodBasic();

    public static final Item BIRD_GUIDE = new ItemBirdGuide();

    //Spawn eggs
    public static final Item EURASIANBULLFINCH_SPAWNEGG = new ItemBirdSpawnEgg(InitEntities.EURASIAN_BULLFINCH_ENTITY);
    public static final Item REDNECKEDNIGHTJAR_SPAWNEGG = new ItemBirdSpawnEgg(InitEntities.RED_NECKED_NIGHTJAR_ENTITY);
    public static final Item REDFLANKEDBLUETAIL_SPAWNEGG = new ItemBirdSpawnEgg(InitEntities.RED_FLANKED_BLUETAIL_ENTITY);
    public static final Item STELLERSEIDER_SPAWNEGG = new ItemBirdSpawnEgg(InitEntities.STELLERS_EIDER_ENTITY);

    public static final Item KILLDEER_SPAWNEGG = new ItemBirdSpawnEgg(InitEntities.KILLDEER_ENTITY);
    public static final Item EASTERNBLUEBIRD_SPAWNEGG = new ItemBirdSpawnEgg(InitEntities.EASTERN_BLUEBIRD_ENTITY);
    public static final Item NORTHERNMOCKINGBIRD_SPAWNEGG = new ItemBirdSpawnEgg(InitEntities.NORTHERN_MOCKINGBIRD_ENTITY);
    public static final Item GREENHERON_SPAWNEGG = new ItemBirdSpawnEgg(InitEntities.GREEN_HERON_ENTITY);

    public static final Item HOATZIN_SPAWNEGG = new ItemBirdSpawnEgg(InitEntities.HOATZIN_ENTITY);
    public static final Item TURQUOISEBROWEDMOTMOT_SPAWNEGG = new ItemBirdSpawnEgg(InitEntities.MOTMOT_ENTITY);
    public static final Item KINGOFSAXONY_SPAWNEGG = new ItemBirdSpawnEgg(InitEntities.KING_OF_SAXONY_ENTITY);

    public static final Item GREATGREYOWL_SPAWNEGG = new ItemBirdSpawnEgg(InitEntities.GREAT_GREY_OWL_ENTITY);
    public static final Item BROWNBOOBY_SPAWNEGG = new ItemBirdSpawnEgg(InitEntities.BROWN_BOOBY_ENTITY);

    public static final Item RAZORBILL_SPAWNEGG = new ItemBirdSpawnEgg(InitEntities.RAZORBILL_ENTITY);
    public static final Item HIMALAYANMONAL_SPAWNEGG = new ItemBirdSpawnEgg(InitEntities.HIMALAYAN_MONAL_ENTITY);
    public static final Item SABINESGULL_SPAWNEGG = new ItemBirdSpawnEgg(InitEntities.SABINES_GULL_ENTITY);

    //feathers
    public static final Item EASTERNBLUEBIRDFEATHER_FEMALE = new ItemBirdwmodBasic();
    public static final Item EASTERNBLUEBIRDFEATHER_MALE = new ItemBirdwmodBasic();
    public static final Item EURASIANBULLFINCHDFEATHER_FEMALE = new ItemBirdwmodBasic();
    public static final Item EURASIANBULLFINCHDFEATHER_MALE = new ItemBirdwmodBasic();
    public static final Item GREENHERONFEATHER = new ItemBirdwmodBasic();
    public static final Item KILLDEERFEATHER = new ItemBirdwmodBasic();
    public static final Item NORTHERNMOCKINGBIRDFEATHER = new ItemBirdwmodBasic();
    public static final Item REDFLANCKEDBLUETAILFEATHER_FEMALE = new ItemBirdwmodBasic();
    public static final Item REDFLANCKEDBLUETAILFEATHER_MALE = new ItemBirdwmodBasic();
    public static final Item REDNECKEDNIGHTJARFEATHER = new ItemBirdwmodBasic();
    public static final Item STELLERSEIDERFEATHER_FEMALE = new ItemBirdwmodBasic();
    public static final Item STELLERSEIDERFEATHER_MALE = new ItemBirdwmodBasic();
    public static final Item KINGOFSAXONYFEATHER_MALE = new ItemBirdwmodBasic();
    public static final Item KINGOFSAXONYFEATHER_FEMALE = new ItemBirdwmodBasic();
    public static final Item MOTMOTFEATHER = new ItemBirdwmodBasic();
    public static final Item HOATZINFEATHER = new ItemBirdwmodBasic();
    public static final Item GREATGREYOWLFEATHER = new ItemBirdwmodBasic();
    public static final Item BROWNBOOBYFEATHER = new ItemBirdwmodBasic();
    public static final Item RAZORBILLFEATHER = new ItemBirdwmodBasic();
    public static final Item HIMALAYANMONALMALEFEATHER = new ItemBirdwmodBasic();
    public static final Item HIMALAYANMONALFEMALEFEATHER = new ItemBirdwmodBasic();
    public static final Item SABINESGULLFEATHER = new ItemBirdwmodBasic();

    //meats
    public static final Item BIGRAWMEAT = new ItemBirdwmodBasic(new Item.Settings().food(new FoodComponent.Builder().nutrition(3).alwaysEdible().build()));
    public static final Item SMALLRAWMEAT = new ItemBirdwmodBasic(new Item.Settings().food(new FoodComponent.Builder().nutrition(1).alwaysEdible().build()));
    public static final Item MEDIUMRAWMEAT = new ItemBirdwmodBasic(new Item.Settings().food(new FoodComponent.Builder().nutrition(2).alwaysEdible().build()));

    public static final Item BIGCOOCKEDMEAT = new ItemBirdwmodBasic(new Item.Settings().food(new FoodComponent.Builder().nutrition(8).alwaysEdible().build()));
    public static final Item SMALLCOOCKEDMEAT = new ItemBirdwmodBasic(new Item.Settings().food(new FoodComponent.Builder().nutrition(3).alwaysEdible().build()));
    public static final Item MEDIUMCOOCKEDMEAT = new ItemBirdwmodBasic(new Item.Settings().food(new FoodComponent.Builder().nutrition(4).alwaysEdible().build()));

    public static void registerItems(){
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "binocular_basic"), BINOCULAR_BASIC);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "binocular_medium"), BINOCULAR_MEDIUM);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "binocular_pro"), BINOCULAR_PROFFESIONAL);

        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "ring"), RING);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "bird_guide"), BIRD_GUIDE);

        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "spawnegg_eurasian_bullfinch"), EURASIANBULLFINCH_SPAWNEGG);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "spawnegg_rednecked_nightjar"), REDNECKEDNIGHTJAR_SPAWNEGG);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "spawnegg_redflanked_bluetail"), REDFLANKEDBLUETAIL_SPAWNEGG);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "spawnegg_stellers_eider"), STELLERSEIDER_SPAWNEGG);

        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "spawnegg_killdeer"), KILLDEER_SPAWNEGG);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "spawnegg_eastern_bluebird"), EASTERNBLUEBIRD_SPAWNEGG);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "spawnegg_northern_mockingbird"), NORTHERNMOCKINGBIRD_SPAWNEGG);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "spawnegg_green_heron"), GREENHERON_SPAWNEGG);

        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "spawnegg_hoatzin"), HOATZIN_SPAWNEGG);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "spawnegg_turquoisebrowed_motmot"), TURQUOISEBROWEDMOTMOT_SPAWNEGG);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "spawnegg_kingofsaxony_bird_of_paradise"), KINGOFSAXONY_SPAWNEGG);

        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "spawnegg_great_grey_owl"), GREATGREYOWL_SPAWNEGG);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "spawnegg_brown_booby"), BROWNBOOBY_SPAWNEGG);

        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "spawnegg_razorbill"), RAZORBILL_SPAWNEGG);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "spawnegg_himalayan_monal"), HIMALAYANMONAL_SPAWNEGG);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "spawnegg_sabines_gull"), SABINESGULL_SPAWNEGG);

        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "feather_eastern_bluebird_female"), EASTERNBLUEBIRDFEATHER_FEMALE);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "feather_eastern_bluebird"), EASTERNBLUEBIRDFEATHER_MALE);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "feather_eurasian_bullfinch_female"), EURASIANBULLFINCHDFEATHER_FEMALE);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "feather_eurasian_bullfinch"), EURASIANBULLFINCHDFEATHER_MALE);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "feather_green_heron"), GREENHERONFEATHER);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "feather_killdeer"), KILLDEERFEATHER);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "feather_northern_mockingbird"), NORTHERNMOCKINGBIRDFEATHER);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "feather_redflanked_bluetail_female"), REDFLANCKEDBLUETAILFEATHER_FEMALE);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "feather_redflanked_bluetail"), REDFLANCKEDBLUETAILFEATHER_MALE);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "feather_red_necked_nightjar"), REDNECKEDNIGHTJARFEATHER);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "feather_stellers_eider_female"), STELLERSEIDERFEATHER_FEMALE);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "feather_stellers_eider"), STELLERSEIDERFEATHER_MALE);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "feather_king_of_saxony"), KINGOFSAXONYFEATHER_MALE);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "feather_king_of_saxony_female"), KINGOFSAXONYFEATHER_FEMALE);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "feather_motmot"), MOTMOTFEATHER);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "feather_hoatzin"), HOATZINFEATHER);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "feather_great_grey_owl"), GREATGREYOWLFEATHER);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "feather_brown_booby"), BROWNBOOBYFEATHER);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "feather_razorbill"), RAZORBILLFEATHER);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "feather_himalayan_monal"), HIMALAYANMONALMALEFEATHER);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "feather_himalayan_monal_female"), HIMALAYANMONALFEMALEFEATHER);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "feather_sabines_gull"), SABINESGULLFEATHER);

        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "meatraw_big"), BIGRAWMEAT);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "meatraw_small"), SMALLRAWMEAT);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "meatraw_medium"), MEDIUMRAWMEAT);

        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "meatcooked_big"), BIGCOOCKEDMEAT);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "meatcooked_small"), SMALLCOOCKEDMEAT);
        Registry.register(Registries.ITEM, Identifier.of("birdwmod", "meatcooked_medium"), MEDIUMCOOCKEDMEAT);

    }
}
