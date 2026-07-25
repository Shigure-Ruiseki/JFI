package ruiseki.jfi.jfmuy.ic2.machine.blockcutter;

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
import ic2.core.block.machine.gui.GuiBlockCutter;
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

public class BlockCutterCategory extends MachineRecipeCategory<BlockCutterRecipeWrapper> {

    public static final String UID = "ic2.blockcutter";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new BlockCutterCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            registry.addRecipes(getRecipes(), UID);
            registry.addRecipeClickArea(GuiBlockCutter.class, 38, 0, 80, 42, UID);
            registry.addRecipeCatalyst(Ic2Items.blockcutter, UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    public static List<BlockCutterRecipeWrapper> getRecipes() {
        List<BlockCutterRecipeWrapper> recipes = new ArrayList<>();
        if (Recipes.blockcutter != null && Recipes.blockcutter.getRecipes() != null) {
            for (Map.Entry<IRecipeInput, RecipeOutput> entry : Recipes.blockcutter.getRecipes()
                .entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null && !entry.getValue().items.isEmpty()) {
                    recipes.add(new BlockCutterRecipeWrapper(entry.getKey(), entry.getValue()));
                }
            }
        }
        return recipes;
    }

    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT_START = 1;

    private final IDrawableAnimated powerIcon;

    public BlockCutterCategory(IGuiHelper guiHelper) {
        super(guiHelper, IC2.textureDomain + ":textures/gui/GUIBlockCutter.png", 3, 14, 146, 68);

        ResourceLocation guiTexture = new ResourceLocation(IC2.textureDomain + ":textures/gui/GUIBlockCutter.png");

        IDrawableStatic powerStatic = guiHelper.createDrawable(guiTexture, 176, 0, 14, 14);
        this.powerIcon = guiHelper
            .createAnimatedDrawable(powerStatic, 20, IDrawableAnimated.StartDirection.BOTTOM, false);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal("ic2.blockBlockCutter");
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        this.powerIcon.draw(minecraft, 23, 22);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, BlockCutterRecipeWrapper recipeWrapper,
        IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(INPUT_SLOT, true, 22, 2);

        int outputCount = recipeWrapper.getOutputs()
            .size();
        for (int i = 0; i < outputCount; i++) {
            guiItemStacks.init(OUTPUT_SLOT_START + i, false, 112, 20 + (i * 18));
        }

        guiItemStacks.set(ingredients);
    }
}
