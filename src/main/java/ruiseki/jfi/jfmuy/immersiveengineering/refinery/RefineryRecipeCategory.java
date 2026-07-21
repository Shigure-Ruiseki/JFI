package ruiseki.jfi.jfmuy.immersiveengineering.refinery;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.apache.logging.log4j.Level;

import blusunrize.immersiveengineering.api.energy.DieselHandler;
import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.blocks.metal.BlockMetalMultiblocks;
import ruiseki.jfi.JFI;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IGuiFluidStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class RefineryRecipeCategory implements IRecipeCategory<RefineryRecipeWrapper> {

    public static final String UID = "immersiveengineering.refinery";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new RefineryRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            registry.addRecipes(getRecipes(), UID);

            registry.addRecipeCatalyst(
                new ItemStack(IEContent.blockMetalMultiblocks, 1, BlockMetalMultiblocks.META_refinery),
                UID);

        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null refinery recipe!", t);
        }
    }

    public static List<RefineryRecipeWrapper> getRecipes() {
        List<RefineryRecipeWrapper> recipes = new ArrayList<>();

        if (DieselHandler.refineryList != null) {
            for (DieselHandler.RefineryRecipe recipe : DieselHandler.refineryList) {
                if (recipe != null) {
                    recipes.add(new RefineryRecipeWrapper(recipe));
                }
            }
        }
        return recipes;
    }

    private final IDrawable background;
    private final IDrawable tankOverlay;
    private final String title;

    public RefineryRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation guiTex = new ResourceLocation("immersiveengineering:textures/gui/refinery.png");
        this.background = guiHelper.createDrawable(guiTex, 0, 0, 176, 76);
        this.tankOverlay = guiHelper.createDrawable(guiTex, 177, 31, 20, 51);
        this.title = StatCollector.translateToLocal("tile.ImmersiveEngineering.metalMultiblock.refinery.name");
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
        tankOverlay.draw(minecraft, 11, 18);
        tankOverlay.draw(minecraft, 59, 18);
        tankOverlay.draw(minecraft, 107, 18);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, RefineryRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();

        fluidStacks.init(0, true, 13, 20, 16, 47, 12000, true, null);
        fluidStacks.init(1, true, 61, 20, 16, 47, 12000, true, null);
        fluidStacks.init(2, false, 109, 20, 16, 47, 12000, true, null);

        fluidStacks.set(ingredients);
    }
}
