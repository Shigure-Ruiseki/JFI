package ruiseki.jfi.jfmuy.thermalexpansion.dynamo.steam;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.Level;

import cofh.lib.inventory.ComparableItemStack;
import cofh.lib.util.helpers.StringHelper;
import cofh.thermalexpansion.block.dynamo.BlockDynamo;
import cofh.thermalexpansion.block.dynamo.TileDynamoSteam;
import cofh.thermalexpansion.gui.client.dynamo.GuiDynamoSteam;
import gnu.trove.map.hash.TObjectIntHashMap;
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

public class SteamFuelCategory extends BaseFuelCategory<SteamFuelWrapper> {

    public static boolean enable = true;

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        registry.addRecipeCategories(new SteamFuelCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
            IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

            registry.addRecipes(getRecipes(registry, guiHelper), RecipeUidsTE.DYNAMO_STEAM);
            registry.addRecipeClickArea(GuiDynamoSteam.class, 115, 35, 16, 16, RecipeUidsTE.DYNAMO_STEAM);
            registry.addRecipeCatalyst(BlockDynamo.dynamoSteam, RecipeUidsTE.DYNAMO_STEAM);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<SteamFuelWrapper> getRecipes(IModRegistry registry, IGuiHelper guiHelper) {
        List<SteamFuelWrapper> recipes = new ArrayList<>();
        Set<ComparableItemStack> specificFuels = null;

        try {
            Field field = TileDynamoSteam.class.getDeclaredField("fuels");
            field.setAccessible(true);
            TObjectIntHashMap<ComparableItemStack> fuelMap = (TObjectIntHashMap<ComparableItemStack>) field.get(null);
            if (fuelMap != null) {
                specificFuels = fuelMap.keySet();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (specificFuels != null) {
            for (ComparableItemStack fuel : specificFuels) {
                ItemStack fuelStack = fuel.toItemStack();
                int energy = TileDynamoSteam.getEnergyValue(fuelStack);
                if (energy > 0) {
                    recipes.add(new SteamFuelWrapper(guiHelper, fuelStack, energy));
                }
            }
        }

        for (ItemStack fuel : registry.getIngredientRegistry()
            .getFuels()) {
            if (specificFuels != null && specificFuels.contains(new ComparableItemStack(fuel))) {
                continue;
            }
            int energy = TileDynamoSteam.getEnergyValue(fuel);
            if (energy > 0) {
                recipes.add(new SteamFuelWrapper(guiHelper, fuel, energy));
            }
        }
        return recipes;
    }

    public SteamFuelCategory(IGuiHelper guiHelper) {
        background = guiHelper
            .drawableBuilder(
                new ResourceLocation("thermalexpansion:textures/gui/dynamo/DynamoSteam.png"),
                26,
                11,
                70,
                62)
            .addPadding(0, 0, 16, 78)
            .build();
        energyMeter = Drawables.getDrawables(guiHelper)
            .getEnergyEmpty();
        durationEmpty = Drawables.getDrawables(guiHelper)
            .getScale(Drawables.SCALE_FLAME);
        localizedName = StringHelper.localize("tile.thermalexpansion.dynamo.steam.name");
    }

    @Nonnull
    @Override
    public String getUid() {
        return RecipeUidsTE.DYNAMO_STEAM;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, SteamFuelWrapper recipeWrapper, IIngredients ingredients) {
        List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);

        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(0, true, 33, 23);

        if (!inputs.isEmpty() && !inputs.get(0)
            .isEmpty()) {
            guiItemStacks.set(0, inputs.get(0));
        }
    }
}
