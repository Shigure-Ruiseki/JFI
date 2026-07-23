package ruiseki.jfi.jfmuy.ic2.machine.orewashing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.apache.logging.log4j.Level;

import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import ic2.api.recipe.Recipes;
import ic2.core.IC2;
import ic2.core.Ic2Items;
import ic2.core.block.machine.gui.GuiOreWashing;
import ruiseki.jfi.JFI;
import ruiseki.jfi.jfmuy.ic2.machine.MachineRecipeCategory;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IDrawableAnimated;
import ruiseki.jfmuy.api.gui.IDrawableStatic;
import ruiseki.jfmuy.api.gui.IGuiFluidStackGroup;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class OreWashingCategory extends MachineRecipeCategory<OreWashingRecipeWrapper> {

    public static final String UID = "ic2.oreWashing";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new OreWashingCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            registry.addRecipes(getRecipes(), UID);
            registry.addRecipeClickArea(GuiOreWashing.class, 55, 9, 22, 21, UID);
            registry.addRecipeCatalyst(Ic2Items.orewashingplant, UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    public static List<OreWashingRecipeWrapper> getRecipes() {
        List<OreWashingRecipeWrapper> recipes = new ArrayList<>();
        if (Recipes.oreWashing != null && Recipes.oreWashing.getRecipes() != null) {
            for (Map.Entry<IRecipeInput, RecipeOutput> entry : Recipes.oreWashing.getRecipes()
                .entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null && !entry.getValue().items.isEmpty()) {
                    recipes.add(new OreWashingRecipeWrapper(entry.getKey(), entry.getValue()));
                }
            }
        }
        return recipes;
    }

    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT_START = 1;
    public static final int WATER_TANK_INDEX = 0;

    private final IDrawableAnimated progressBar;
    private final IDrawable blank;
    private final IDrawableStatic fluidOverlay;

    public OreWashingCategory(IGuiHelper guiHelper) {
        super(guiHelper, IC2.textureDomain + ":textures/gui/GUIOreWashingPlant.png", 58, 14, 88, 65);
        ResourceLocation guiTexture = new ResourceLocation(IC2.textureDomain + ":textures/gui/GUIOreWashingPlant.png");
        this.blank = guiHelper.createBlankDrawable(140,  65);

        IDrawableStatic progressStatic = guiHelper.createDrawable(guiTexture, 176, 117, 20, 19);
        this.progressBar = guiHelper.createAnimatedDrawable(progressStatic, 20, IDrawableAnimated.StartDirection.LEFT, false);

        this.fluidOverlay = guiHelper.createDrawable(guiTexture, 176, 69, 12, 47);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal("ic2.blockOreWashingPlant");
    }

    @Override
    public IDrawable getBackground() {
        return blank;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        this.background.draw(minecraft, 54, 0);
        this.progressBar.draw(minecraft, 98, 24);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, OreWashingRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(INPUT_SLOT, true, 99, 2);

        int outputCount = recipeWrapper.getOutputs()
            .size();
        for (int i = 0; i < outputCount; i++) {
            guiItemStacks.init(OUTPUT_SLOT_START + i, false, 81 + (i * 18), 47);
        }

        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
        guiFluidStacks.init(WATER_TANK_INDEX, true, 60, 10, 12, 47, 8000, true, fluidOverlay);

        guiItemStacks.set(ingredients);
        guiFluidStacks.set(ingredients);
    }
}
