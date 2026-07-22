package ruiseki.jfi.jfmuy.enderio.slicensplice;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.apache.logging.log4j.Level;

import crazypants.enderio.EnderIO;
import crazypants.enderio.machine.recipe.IRecipe;
import crazypants.enderio.machine.slicensplice.ContainerSliceAndSplice;
import crazypants.enderio.machine.slicensplice.SliceAndSpliceRecipeManager;
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

public class SliceAndSpliceRecipeCategory implements IRecipeCategory<SliceAndSpliceRecipeWrapper> {

    public static final String UID = "EnderIOSliceAndSplice";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new SliceAndSpliceRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
            IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

            registry.addRecipes(getRecipes(), UID);
            registry.addRecipeCatalyst(new ItemStack(EnderIO.blockSliceAndSplice), UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    public static List<IRecipeWrapper> getRecipes() {
        List<IRecipeWrapper> recipes = new ArrayList<>();

        for (IRecipe recipe : SliceAndSpliceRecipeManager.getInstance()
            .getRecipes()) {
            if (recipe != null) {
                recipes.add(new SliceAndSpliceRecipeWrapper(recipe));
            }
        }

        return recipes;
    }

    private final IDrawable background;
    private final IDrawableAnimated progressArrow;

    private static final int X_OFF = 30;
    private static final int Y_OFF = 12;

    public SliceAndSpliceRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation backgroundLocation = new ResourceLocation("enderio:textures/gui/23/sliceAndSplice.png");

        this.background = guiHelper.createDrawable(backgroundLocation, 29, 11, 142, 65);

        IDrawableAnimated.StartDirection right = IDrawableAnimated.StartDirection.LEFT;
        this.progressArrow = guiHelper
            .createAnimatedDrawable(guiHelper.createDrawable(backgroundLocation, 177, 14, 23, 17), 120, right, false);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal("enderio.nei.slicensplice");
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
        progressArrow.draw(minecraft, 91 - 16, 38);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, final SliceAndSpliceRecipeWrapper recipeWrapper,
        IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        for (int i = 0; i < 6; i++) {
            Point pos = ContainerSliceAndSplice.INPUT_SLOTS[i];
            guiItemStacks.init(i, true, pos.x - X_OFF, pos.y - Y_OFF);
        }

        Point axePos = ContainerSliceAndSplice.INPUT_SLOTS[6];
        guiItemStacks.init(6, true, axePos.x - X_OFF, axePos.y - Y_OFF);

        Point shearsPos = ContainerSliceAndSplice.INPUT_SLOTS[7];
        guiItemStacks.init(7, true, shearsPos.x - X_OFF, shearsPos.y - Y_OFF);

        Point outPos = ContainerSliceAndSplice.OUTPUT_SLOT;
        guiItemStacks.init(8, false, outPos.x - X_OFF, outPos.y - Y_OFF);

        guiItemStacks.set(ingredients);
    }
}
