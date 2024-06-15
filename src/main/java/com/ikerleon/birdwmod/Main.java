package com.ikerleon.birdwmod;

import com.ikerleon.birdwmod.world.biome.InitBiomes;
import com.ikerleon.birdwmod.blocks.InitBlocks;
import com.ikerleon.birdwmod.entity.InitEntities;
import com.ikerleon.birdwmod.world.gen.InitGeneration;
import com.ikerleon.birdwmod.items.InitItems;
import com.ikerleon.birdwmod.world.surfacebuilder.InitSurfaceBuilders;
import com.ikerleon.birdwmod.util.SoundHandler;
import com.terraformersmc.terraform.config.BiomeConfigHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.impl.itemgroup.FabricItemGroupBuilderImpl;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class Main implements ModInitializer {

	public static final String ModID = "birdwmod";

	public static final BiomeConfigHandler BIOME_CONFIG_HANDLER = new BiomeConfigHandler(ModID);


	public static final RegistryKey<ItemGroup> THE_BIRDWATCHING_MOD = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(ModID, "birdwatchingmodtab"));
	public static final RegistryKey<ItemGroup> THE_BIRDWATCHING_MOD_SPAWN_EGGS = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(ModID, "birdwatchingmodtab"));

	@Override
	public void onInitialize() {
		Registry.register(Registries.ITEM_GROUP, THE_BIRDWATCHING_MOD, FabricItemGroup.builder()
				.displayName(Text.translatable("itemGroup.birdwmod.birdwatchingmodtab"))
				.icon(() -> new ItemStack(InitItems.BINOCULAR_PROFFESIONAL)).build()
		);
		Registry.register(Registries.ITEM_GROUP, THE_BIRDWATCHING_MOD_SPAWN_EGGS, FabricItemGroup.builder()
				.displayName(Text.translatable("itemGroup.birdwmod.birdwatchingmodspawneggstab"))
				.icon(() -> new ItemStack(InitItems.EURASIANBULLFINCH_SPAWNEGG)).build()
		);

		SoundHandler.register();
		InitItems.registerItems();
		InitBlocks.registerBlocks();
		InitEntities.registerAttributes();
		//TODO
		//InitSurfaceBuilders.register();
		InitBiomes.register();
		InitGeneration.register();
	}
}
