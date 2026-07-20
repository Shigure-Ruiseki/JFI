package ruiseki.jfi.jfmuy.immersiveengineering.cokeoven;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.apache.logging.log4j.Level;

import blusunrize.immersiveengineering.api.crafting.CokeOvenRecipe;
import blusunrize.immersiveengineering.common.IEContent;
import ruiseki.jfi.JFI;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IGuiFluidStackGroup;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class CokeOvenRecipeCategory implements IRecipeCategory<CokeOvenRecipeWrapper> {

    public static final String UID = "immersiveengineering.coke_oven";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new CokeOvenRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
            IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

            registry.addRecipes(getRecipes(guiHelper), UID);

            registry.addRecipeCatalyst(new ItemStack(IEContent.blockStoneDevice, 1, 1), UID);

        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    public static List<CokeOvenRecipeWrapper> getRecipes(IGuiHelper guiHelper) {
        List<CokeOvenRecipeWrapper> recipes = new ArrayList<>();
        for (CokeOvenRecipe recipe : CokeOvenRecipe.recipeList) {
            if (recipe != null) {
                recipes.add(new CokeOvenRecipeWrapper(recipe));
            }
        }
        return recipes;
    }

    private final IDrawable background;
    private final IDrawable tankOverlay;
    private final String title;

    public CokeOvenRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation texture = new ResourceLocation("immersiveengineering", "textures/gui/cokeOven.png");
        this.background = guiHelper.createDrawable(texture, 0, 8, 176, 68);
        this.tankOverlay = guiHelper.createDrawable(texture, 175, 31, 20, 51);
        this.title = StatCollector.translateToLocal("tile.ImmersiveEngineering.stoneDevice.cokeOven.name");
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getModName() {
        return "Immersive Engineering";
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        tankOverlay.draw(minecraft, 127, 10);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CokeOvenRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();

        itemStacks.init(0, true, 29, 26);
        itemStacks.init(1, false, 83, 26);

        fluidStacks.init(0, false, 129, 12, 16, 47, 12000, true, null);

        itemStacks.set(ingredients);
        fluidStacks.set(ingredients);
    }
}
