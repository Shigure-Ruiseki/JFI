package ruiseki.jfi.jfmuy.thermalexpansion.dynamo.enervation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.Level;

import cofh.api.energy.IEnergyContainerItem;
import cofh.lib.util.helpers.StringHelper;
import cofh.thermalexpansion.block.dynamo.BlockDynamo;
import cofh.thermalexpansion.block.dynamo.TileDynamoEnervation;
import cofh.thermalexpansion.gui.client.dynamo.GuiDynamoEnervation;
import ruiseki.jfi.JFI;
import ruiseki.jfi.jfmuy.thermalexpansion.Drawables;
import ruiseki.jfi.jfmuy.thermalexpansion.RecipeUidsTE;
import ruiseki.jfi.jfmuy.thermalexpansion.dynamo.BaseFuelCategory;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class EnervationFuelCategory extends BaseFuelCategory<EnervationFuelWrapper> {

    public static boolean enable = true;

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        registry.addRecipeCategories(new EnervationFuelCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
            IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

            registry.addRecipes(getRecipes(guiHelper), RecipeUidsTE.DYNAMO_ENERVATION);
            registry.addRecipeClickArea(GuiDynamoEnervation.class, 115, 35, 16, 16, RecipeUidsTE.DYNAMO_ENERVATION);
            registry.addRecipeCatalyst(BlockDynamo.dynamoEnervation, RecipeUidsTE.DYNAMO_ENERVATION);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null fuel!", t);
        }
    }

    public static List<EnervationFuelWrapper> getRecipes(IGuiHelper guiHelper) {
        List<EnervationFuelWrapper> recipes = new ArrayList<>();

        ItemStack redstoneStack = new ItemStack(Items.redstone);
        int redstoneEnergy = TileDynamoEnervation.getEnergyValue(redstoneStack);
        if (redstoneEnergy > 0) {
            recipes.add(new EnervationFuelWrapper(guiHelper, redstoneStack, redstoneEnergy));
        }

        ItemStack redstoneBlockStack = new ItemStack(Blocks.redstone_block);
        int blockEnergy = TileDynamoEnervation.getEnergyValue(redstoneBlockStack);
        if (blockEnergy > 0) {
            recipes.add(new EnervationFuelWrapper(guiHelper, redstoneBlockStack, blockEnergy));
        }

        for (Object obj : Item.itemRegistry) {
            if (obj instanceof Item item) {
                if (item instanceof IEnergyContainerItem energyItem) {
                    try {
                        List<ItemStack> subItems = new ArrayList<>();
                        item.getSubItems(item, item.getCreativeTab(), subItems);

                        for (ItemStack stack : subItems) {
                            if (stack != null) {
                                ItemStack chargedStack = stack.copy();
                                int maxStored = energyItem.getMaxEnergyStored(chargedStack);

                                if (maxStored > 0) {
                                    energyItem.receiveEnergy(chargedStack, maxStored, false);
                                    int energyValue = TileDynamoEnervation.getEnergyValue(chargedStack);

                                    if (energyValue > 0) {
                                        recipes.add(
                                            new EnervationFuelWrapper(guiHelper, chargedStack, energyValue, maxStored));
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        JFI.okLog(
                            Level.ERROR,
                            "Exception thrown while getting Enervation Dynamo container fuels: "
                                + item.getUnlocalizedName(),
                            e);
                    }
                }
            }
        }
        return recipes;
    }

    public EnervationFuelCategory(IGuiHelper guiHelper) {
        background = guiHelper
            .drawableBuilder(
                new ResourceLocation("thermalexpansion:textures/gui/dynamo/DynamoEnervation.png"),
                26,
                11,
                70,
                62)
            .addPadding(0, 0, 16, 78)
            .build();
        energyMeter = Drawables.getDrawables(guiHelper)
            .getEnergyEmpty();
        durationEmpty = Drawables.getDrawables(guiHelper)
            .getScale(Drawables.SCALE_FLUX);
        localizedName = StringHelper.localize("tile.thermalexpansion.dynamo.enervation.name");
    }

    @Nonnull
    @Override
    public String getUid() {
        return RecipeUidsTE.DYNAMO_ENERVATION;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, EnervationFuelWrapper recipeWrapper, IIngredients ingredients) {
        List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(0, true, 33, 23);

        if (!inputs.isEmpty() && !inputs.get(0)
            .isEmpty()) {
            guiItemStacks.set(0, inputs.get(0));
        }
    }
}
