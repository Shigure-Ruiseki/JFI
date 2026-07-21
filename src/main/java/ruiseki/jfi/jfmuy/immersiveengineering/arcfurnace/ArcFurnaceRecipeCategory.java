package ruiseki.jfi.jfmuy.immersiveengineering.arcfurnace;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.apache.logging.log4j.Level;

import blusunrize.immersiveengineering.api.crafting.ArcFurnaceRecipe;
import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.blocks.metal.BlockMetalMultiblocks;
import ruiseki.jfi.JFI;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IDrawableAnimated;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class ArcFurnaceRecipeCategory implements IRecipeCategory<ArcFurnaceRecipeWrapper> {

    public static final String UID = "immersiveengineering.arc_furnace";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new ArcFurnaceRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
            IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

            registry.addRecipes(getRecipes(guiHelper), UID);
            registry.addRecipeCatalyst(
                new ItemStack(IEContent.blockMetalMultiblocks, 1, BlockMetalMultiblocks.META_arcFurnace),
                UID);

        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    public static List<ArcFurnaceRecipeWrapper> getRecipes(IGuiHelper guiHelper) {
        List<ArcFurnaceRecipeWrapper> recipes = new ArrayList<>();
        for (ArcFurnaceRecipe recipe : ArcFurnaceRecipe.recipeList) {
            if (recipe != null && recipe.input != null) {
                recipes.add(new ArcFurnaceRecipeWrapper(recipe));
            }
        }
        return recipes;
    }

    private final IDrawable background;
    private final IDrawableAnimated progressBar;
    private final IDrawable slotDrawable;
    private final IDrawable progressBg;
    private final String title;

    public ArcFurnaceRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation furnaceTex = new ResourceLocation("textures/gui/container/furnace.png");

        this.background = guiHelper.createBlankDrawable(140, 60);

        progressBg = guiHelper.createDrawable(furnaceTex, 80, 35, 22, 16);
        this.progressBar = guiHelper.createAnimatedDrawable(
            guiHelper.createDrawable(furnaceTex, 177, 14, 22, 16),
            44,
            IDrawableAnimated.StartDirection.LEFT,
            false);

        this.slotDrawable = guiHelper.getSlotDrawable();

        this.title = StatCollector.translateToLocal("tile.ImmersiveEngineering.metalMultiblock.arcFurnace.name");
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
        progressBg.draw(minecraft, 52, 22);
        progressBar.draw(minecraft, 52, 22);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, ArcFurnaceRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, true, 8, 0);
        for (int i = 0; i < 4; i++) {
            itemStacks.init(1 + i, true, (i % 2) * 18, 24 + (i / 2) * 18);
        }

        int outputSlotStartIndex = 5;
        int outputCount = recipeWrapper.getOutputCount();
        for (int i = 0; i < outputCount; i++) {
            int relX = 100 + (i % 2) * 18;
            int relY = 0 + (i / 2) * 18;
            itemStacks.init(outputSlotStartIndex + i, false, relX, relY);
        }

        int slagSlotIndex = outputSlotStartIndex + outputCount;
        if (recipeWrapper.getRecipe().slag != null) {
            itemStacks.init(slagSlotIndex, false, 54, 42);
        }

        itemStacks.set(ingredients);

        itemStacks.setBackground(0, this.slotDrawable);
        for (int i = 0; i < 4; i++) {
            itemStacks.setBackground(1 + i, this.slotDrawable);
        }
        for (int i = 0; i < outputCount; i++) {
            itemStacks.setBackground(outputSlotStartIndex + i, this.slotDrawable);
        }
        if (recipeWrapper.getRecipe().slag != null) {
            itemStacks.setBackground(slagSlotIndex, this.slotDrawable);
        }
    }
}
