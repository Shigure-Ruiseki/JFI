package ruiseki.jfi.jfmuy.ic2.machine.solidcanner;

import ic2.api.recipe.ICannerBottleRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import ic2.api.recipe.Recipes;
import ic2.core.IC2;
import ic2.core.Ic2Items;
import ic2.core.block.machine.gui.GuiMacerator;
import ic2.core.block.machine.gui.GuiSolidCanner;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.apache.logging.log4j.Level;
import ruiseki.jfi.JFI;
import ruiseki.jfi.jfmuy.ic2.machine.MachineRecipeWrapper;
import ruiseki.jfi.jfmuy.ic2.machine.macerator.MaceratorCategory;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SolidCannerRecipeCategory implements IRecipeCategory<SolidCannerRecipeWrapper> {

    public static final String UID = "ic2.solidcanner";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new SolidCannerRecipeCategory(guiHelper));
    }
    public static void initialize(IModRegistry registry) {
        try {
            registry.addRecipes(getRecipes(), UID);
            registry.addRecipeClickArea(GuiSolidCanner.class, 84, 19, 25, 16, UID);
            registry.addRecipeCatalyst(Ic2Items.canner, UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    public static List<SolidCannerRecipeWrapper> getRecipes() {
        List<SolidCannerRecipeWrapper> recipes = new ArrayList<>();
        if (Recipes.cannerBottle != null && Recipes.cannerBottle.getRecipes() != null) {
            for (Map.Entry<ICannerBottleRecipeManager.Input, RecipeOutput> entry : Recipes.cannerBottle.getRecipes().entrySet()) {
                ICannerBottleRecipeManager.Input input = entry.getKey();
                RecipeOutput output = entry.getValue();
                if (input != null && output != null && !output.items.isEmpty()) {
                    recipes.add(new SolidCannerRecipeWrapper(input.container, input.fill, output));
                }
            }
        }
        return recipes;
    }

    private final IDrawable background;
    private final IDrawableAnimated progressBar;
    private final IDrawableAnimated cannerAnimation;

    public SolidCannerRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation guiTex = new ResourceLocation("ic2:textures/gui/GUISolidCanner.png");
        this.background = guiHelper.createDrawable(guiTex, 5, 16, 140, 65);

        IDrawableStatic progressStatic = guiHelper.createDrawable(guiTex, 176, 14, 25, 16);
        this.progressBar = guiHelper.createAnimatedDrawable(progressStatic, 200, IDrawableAnimated.StartDirection.LEFT, false);

        IDrawableStatic cannerStatic = guiHelper.createDrawable(guiTex, 176, 0, 14, 14);
        this.cannerAnimation = guiHelper.createAnimatedDrawable(cannerStatic, 200, IDrawableAnimated.StartDirection.TOP, false);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return "Canning Machine";
    }

    @Override
    public String getModName() {
        return IC2.MODID;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        this.progressBar.draw(minecraft, 83, 20);
        this.cannerAnimation.draw(minecraft, 2, 29);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, SolidCannerRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, true, 61, 19);
        itemStacks.init(1, true, 31, 19);
        itemStacks.init(2, false, 110, 19);

        itemStacks.set(ingredients);
    }
}
