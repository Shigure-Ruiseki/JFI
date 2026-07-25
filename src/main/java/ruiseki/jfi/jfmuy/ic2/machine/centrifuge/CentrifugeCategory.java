package ruiseki.jfi.jfmuy.ic2.machine.centrifuge;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.Level;

import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import ic2.api.recipe.Recipes;
import ic2.core.IC2;
import ic2.core.Ic2Items;
import ic2.core.block.machine.gui.GuiCentrifuge;
import ruiseki.jfi.JFI;
import ruiseki.jfi.jfmuy.ic2.machine.MachineRecipeCategory;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IDrawableAnimated;
import ruiseki.jfmuy.api.gui.IDrawableStatic;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class CentrifugeCategory extends MachineRecipeCategory<CentrifugeRecipeWrapper> {

    public static final String UID = "ic2.centrifuge";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new CentrifugeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            registry.addRecipes(getRecipes(), UID);
            registry.addRecipeClickArea(GuiCentrifuge.class, 38, 0, 80, 42, UID);
            registry.addRecipeCatalyst(Ic2Items.centrifuge, UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    public static List<CentrifugeRecipeWrapper> getRecipes() {
        List<CentrifugeRecipeWrapper> recipes = new ArrayList<>();
        if (Recipes.centrifuge != null && Recipes.centrifuge.getRecipes() != null) {
            for (Map.Entry<IRecipeInput, RecipeOutput> entry : Recipes.centrifuge.getRecipes()
                .entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null && !entry.getValue().items.isEmpty()) {
                    recipes.add(new CentrifugeRecipeWrapper(entry.getKey(), entry.getValue()));
                }
            }
        }
        return recipes;
    }

    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT_START = 1;

    private final IDrawableAnimated progressBar;
    private final IDrawableAnimated powerIcon;
    private final IDrawableStatic heatIcon;
    private final IDrawableStatic heatGauge;

    public CentrifugeCategory(IGuiHelper guiHelper) {
        super(guiHelper, IC2.textureDomain + ":textures/gui/GUITermalCentrifuge.png", 3, 14, 146, 68);

        ResourceLocation guiTexture = new ResourceLocation(IC2.textureDomain + ":textures/gui/GUITermalCentrifuge.png");

        IDrawableStatic progressStatic = guiHelper.createDrawable(guiTexture, 176, 50, 5, 30);
        this.progressBar = guiHelper
            .createAnimatedDrawable(progressStatic, 40, IDrawableAnimated.StartDirection.BOTTOM, false);

        IDrawableStatic powerStatic = guiHelper.createDrawable(guiTexture, 176, 0, 14, 14);
        this.powerIcon = guiHelper
            .createAnimatedDrawable(powerStatic, 20, IDrawableAnimated.StartDirection.BOTTOM, false);

        this.heatIcon = guiHelper.createDrawable(guiTexture, 176, 36, 14, 14);

        this.heatGauge = guiHelper.createDrawable(guiTexture, 176, 28, 23, 8);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return "Centrifuge";
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        this.progressBar.draw(minecraft, 80, 10);
        this.powerIcon.draw(minecraft, 8, 27);
        this.heatIcon.draw(minecraft, 89, 48);
        this.heatGauge.draw(minecraft, 64, 51);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CentrifugeRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(INPUT_SLOT, true, 7, 6);

        int outputCount = recipeWrapper.getOutputs()
            .size();
        for (int i = 0; i < outputCount; i++) {
            guiItemStacks.init(OUTPUT_SLOT_START + i, false, 120, 3 + (i * 18));
        }

        guiItemStacks.set(ingredients);
    }
}
