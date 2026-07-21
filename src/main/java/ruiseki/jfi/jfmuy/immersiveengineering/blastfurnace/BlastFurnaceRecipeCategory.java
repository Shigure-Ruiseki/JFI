package ruiseki.jfi.jfmuy.immersiveengineering.blastfurnace;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.apache.logging.log4j.Level;

import blusunrize.immersiveengineering.api.crafting.BlastFurnaceRecipe;
import blusunrize.immersiveengineering.common.IEContent;
import ruiseki.jfi.JFI;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IDrawableAnimated;
import ruiseki.jfmuy.api.gui.IDrawableStatic;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class BlastFurnaceRecipeCategory implements IRecipeCategory<BlastFurnaceRecipeWrapper> {

    public static final String UID = "immersiveengineering.blast_furnace";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new BlastFurnaceRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
            IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

            registry.addRecipes(getRecipes(guiHelper), UID);

            registry.addRecipeCatalyst(new ItemStack(IEContent.blockStoneDevice, 1, 2), UID);

        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    public static List<BlastFurnaceRecipeWrapper> getRecipes(IGuiHelper guiHelper) {
        List<BlastFurnaceRecipeWrapper> recipes = new ArrayList<>();
        for (BlastFurnaceRecipe recipe : BlastFurnaceRecipe.recipeList) {
            if (recipe != null) {
                recipes.add(new BlastFurnaceRecipeWrapper(recipe));
            }
        }
        return recipes;
    }

    private final IDrawable background;
    private final IDrawableAnimated flameAnimation;
    private final IDrawableAnimated progressArrow;
    private final String title;

    public BlastFurnaceRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation texture = new ResourceLocation("immersiveengineering", "textures/gui/blastFurnace.png");

        this.background = guiHelper.createDrawable(texture, 0, 8, 176, 68);

        IDrawableStatic staticFlame = guiHelper.createDrawable(texture, 179, 0, 14, 14);
        this.flameAnimation = guiHelper
            .createAnimatedDrawable(staticFlame, 200, IDrawableAnimated.StartDirection.TOP, true);

        IDrawableStatic staticArrow = guiHelper.createDrawable(texture, 179, 14, 24, 17);
        this.progressArrow = guiHelper
            .createAnimatedDrawable(staticArrow, 200, IDrawableAnimated.StartDirection.LEFT, false);

        this.title = StatCollector.translateToLocal("desc.ImmersiveEngineering.name.blastFurnace");
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
        flameAnimation.draw(minecraft, 56, 28);
        progressArrow.draw(minecraft, 78, 26);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, BlastFurnaceRecipeWrapper recipeWrapper,
        IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, true, 51, 8);

        itemStacks.init(1, true, 51, 44);

        itemStacks.init(2, false, 111, 8);

        itemStacks.init(3, false, 111, 44);

        itemStacks.set(ingredients);
    }
}
