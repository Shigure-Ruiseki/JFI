package ruiseki.jfi.jfmuy.enderio.alloy;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.apache.logging.log4j.Level;

import crazypants.enderio.EnderIO;
import crazypants.enderio.machine.alloy.AlloyRecipeManager;
import crazypants.enderio.machine.recipe.IRecipe;
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
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class AlloySmelterRecipeCategory implements IRecipeCategory<AlloySmelterRecipeWrapper> {

    public static final String UID = "enderio.alloysmelter";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new AlloySmelterRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
            IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

            registry.addRecipes(getRecipes(), UID);
            registry.addRecipeCatalyst(new ItemStack(EnderIO.blockAlloySmelter), UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    public static List<IRecipeWrapper> getRecipes() {
        List<IRecipeWrapper> recipes = new ArrayList<>();

        for (IRecipe recipe : AlloyRecipeManager.getInstance()
            .getRecipes()) {
            if (recipe != null) {
                recipes.add(new AlloySmelterRecipeWrapper(recipe));
            }
        }

        for (IRecipe recipe : AlloyRecipeManager.getInstance()
            .getVanillaRecipe()
            .getAllRecipes()) {
            if (recipe != null) {
                recipes.add(new AlloySmelterRecipeWrapper(recipe));
            }
        }

        return recipes;
    }

    private final IDrawable background;
    private final IDrawableAnimated progressBarLeft;
    private final IDrawableAnimated progressBarRight;

    public AlloySmelterRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper
            .createDrawable(new ResourceLocation("enderio:textures/gui/nei/alloySmelter.png"), 27, 0, 120, 65);

        this.progressBarLeft = guiHelper.createAnimatedDrawable(
            guiHelper.createDrawable(new ResourceLocation("enderio:textures/gui/nei/alloySmelter.png"), 166, 0, 22, 13),
            200,
            IDrawableAnimated.StartDirection.BOTTOM,
            false);

        this.progressBarRight = guiHelper.createAnimatedDrawable(
            guiHelper.createDrawable(new ResourceLocation("enderio:textures/gui/nei/alloySmelter.png"), 166, 0, 22, 13),
            200,
            IDrawableAnimated.StartDirection.BOTTOM,
            false);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal("enderio.nei.alloysmelter");
    }

    @Override
    public String getModName() {
        return EnderIO.MODID;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        progressBarLeft.draw(minecraft, 24, 31);
        progressBarRight.draw(minecraft, 72, 31);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, AlloySmelterRecipeWrapper recipeWrapper,
        IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(0, true, 23 - 1, 13 - 1);
        guiItemStacks.init(1, true, 48 - 1, 3 - 1);
        guiItemStacks.init(2, true, 72 - 1, 13 - 1);

        guiItemStacks.init(3, false, 48 - 1, 42 - 1);

        guiItemStacks.set(ingredients);
    }
}
