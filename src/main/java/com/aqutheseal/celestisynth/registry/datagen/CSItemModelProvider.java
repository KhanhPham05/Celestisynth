package com.aqutheseal.celestisynth.registry.datagen;

import com.aqutheseal.celestisynth.Celestisynth;
import com.aqutheseal.celestisynth.registry.CSBlockRegistry;
import com.aqutheseal.celestisynth.registry.CSItemRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CSItemModelProvider extends ItemModelProvider {
    private static final List<RegistryObject<Item>> exemptions = new ArrayList<>();

    public CSItemModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        exemptions.add(CSItemRegistry.SOLARIS);
        exemptions.add(CSItemRegistry.CRESCENTIA);
        exemptions.add(CSItemRegistry.BREEZEBREAKER);

        this.defaultItem(CSItemRegistry.ITEMS.getEntries());

        this.csSinglePredicatedModel(CSItemRegistry.POLTERGEIST, "item/large_axe", csLoc("haunted"), "item/large_axe");
        this.csSinglePredicatedModel(CSItemRegistry.AQUAFLORA, "item/long_blade", csLoc("blooming"), "item/long_blade");

        this.csCustomModel(CSBlockRegistry.SOLAR_CRYSTAL.get().asItem(), getMcLoc("item/generated"));
        this.block(CSBlockRegistry.LUNAR_STONE);
        this.block(CSBlockRegistry.ZEPHYR_DEPOSIT);
    }

    public void defaultItem(Collection<RegistryObject<Item>> items) {
        for (RegistryObject<Item> item : items) {

            if (exemptions.contains(item)) {
                return;
            }

            String name = item.getId().getPath();
            Item getItem = item.get();
            ResourceLocation datagenLoc = new ResourceLocation(Celestisynth.MODID, "item/" + name);
            ModelFile.ExistingModelFile modelType = getItem instanceof DiggerItem || getItem instanceof SwordItem ? getMcLoc("item/handheld") : getMcLoc("item/generated");

            if (getItem instanceof BlockItem) {
                return;
            }

            if (existingFileHelper.exists(datagenLoc, TEXTURE) || !existingFileHelper.exists(datagenLoc, MODEL)) {
                this.getBuilder(name).parent(modelType).texture("layer0", ITEM_FOLDER + "/" + name);
            }
        }
    }

    public void block(RegistryObject<Block> blockItem) {
        String name = blockItem.getId().getPath();
        this.getBuilder(name).parent(getCSLoc("block/" + name));
    }

    public void csCustomModel(RegistryObject<Item> item, ModelFile.ExistingModelFile modelPath) {
        csCustomModel(item.get(), modelPath);
    }

    public void csCustomModel(Item item, ModelFile.ExistingModelFile modelType) {
        String name = ForgeRegistries.ITEMS.getKey(item).getPath();
        this.getBuilder(name).parent(modelType).texture("layer0", ITEM_FOLDER + "/" + name);
    }

    public void csSinglePredicatedModel(RegistryObject<Item> item, String modelPath, ResourceLocation predicate, String predicatedModelPath) {
        String name = item.getId().getPath();
        ModelFile.ExistingModelFile modelType = getCSLoc(modelPath);
        ModelFile.ExistingModelFile predModelType = getCSLoc(predicatedModelPath);
        this.getBuilder(name).parent(modelType).texture("layer0", ITEM_FOLDER + "/" + name).override()
                .predicate(predicate, 1).model(getBuilder(name + "_" + predicate.getPath()));

        this.getBuilder(name + "_" + predicate.getPath()).parent(predModelType).texture("layer0", ITEM_FOLDER + "/" + name + "_" + predicate.getPath());
    }

    public ModelFile.ExistingModelFile getMcLoc(String mcModel) {
        return getExistingFile(mcLoc(mcModel));
    }

    public ModelFile.ExistingModelFile getCSLoc(String csModel) {
        return getExistingFile(csLoc(csModel));
    }

    public ResourceLocation csLoc(String name) {
        return new ResourceLocation(Celestisynth.MODID, name);
    }
}
