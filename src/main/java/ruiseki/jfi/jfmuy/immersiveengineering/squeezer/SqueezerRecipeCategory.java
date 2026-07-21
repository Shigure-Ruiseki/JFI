package ruiseki.jfi.jfmuy.immersiveengineering.squeezer;

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
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class SqueezerRecipeCategory implements IRecipeCategory<SqueezerRecipeWrapper> {

    public static final String UID = "immersiveengineering.squeezer";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new SqueezerRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            registry.addRecipes(getRecipes(), UID);

            registry.addRecipeCatalyst(
                new ItemStack(IEContent.blockMetalMultiblocks, 1, BlockMetalMultiblocks.META_squeezer),
                UID);

        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null squeezer recipe!", t);
        }
    }

    public static List<SqueezerRecipeWrapper> getRecipes() {
        List<SqueezerRecipeWrapper> recipes = new ArrayList<>();

        if (DieselHandler.squeezerList != null) {
            for (DieselHandler.SqueezerRecipe recipe : DieselHandler.squeezerList) {
                if (recipe != null) {
                    recipes.add(new SqueezerRecipeWrapper(recipe));
                }
            }
        }
        return recipes;
    }

    private final IDrawable background;
    private final IDrawable tankOverlay;
    private final String title;

    public SqueezerRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation texture = new ResourceLocation("immersiveengineering", "textures/gui/fluidProducer.png");
        this.background = guiHelper.createDrawable(texture, 5, 8, 166, 68);
        this.tankOverlay = guiHelper.createDrawable(texture, 177, 31, 20, 51);
        this.title = StatCollector
            .translateToLocal("tile.ImmersiveEngineering.metalMultiblock.industrialSqueezer.name");
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
        tankOverlay.draw(minecraft, 104, 11);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, SqueezerRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();

        itemStacks.init(0, true, 18, 8);

        itemStacks.init(1, false, 85, 26);

        fluidStacks.init(0, false, 106, 13, 16, 47, 12000, true, null);

        itemStacks.set(ingredients);
        fluidStacks.set(ingredients);
    }
}
