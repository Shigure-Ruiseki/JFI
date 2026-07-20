package ruiseki.jfi.jfmuy.thermalexpansion.machine.furnace;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import cofh.lib.util.helpers.StringHelper;
import cofh.thermalexpansion.block.machine.BlockMachine;
import cofh.thermalexpansion.item.TEAugments;
import cofh.thermalexpansion.util.crafting.FurnaceManager;
import ruiseki.jfi.jfmuy.thermalexpansion.RecipeUidsTE;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;

public class FurnaceRecipeCategoryFood extends FurnaceRecipeCategory {

    public static void initialize(IModRegistry registry) {

        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        registry.addRecipes(getRecipes(guiHelper), RecipeUidsTE.FURNACE_FOOD);
        registry.addRecipeCatalyst(TEAugments.machineFurnaceFood, RecipeUidsTE.FURNACE_FOOD);
        registry.addRecipeCatalyst(BlockMachine.furnace, RecipeUidsTE.FURNACE_FOOD);
    }

    public static List<FurnaceRecipeWrapper> getRecipes(IGuiHelper guiHelper) {

        List<FurnaceRecipeWrapper> recipes = new ArrayList<>();

        for (FurnaceManager.RecipeFurnace recipe : FurnaceManager.getRecipeList()) {
            if (recipe.isOutputFood()) {
                recipes.add(new FurnaceRecipeWrapper(guiHelper, recipe, RecipeUidsTE.FURNACE_FOOD));
            }
        }
        return recipes;
    }

    public FurnaceRecipeCategoryFood(IGuiHelper guiHelper) {

        super(guiHelper);

        localizedName = StringHelper.localize("item.thermalexpansion.augment.machineFurnaceFood.name");
    }

    @Nonnull
    @Override
    public String getUid() {

        return RecipeUidsTE.FURNACE_FOOD;
    }

}
