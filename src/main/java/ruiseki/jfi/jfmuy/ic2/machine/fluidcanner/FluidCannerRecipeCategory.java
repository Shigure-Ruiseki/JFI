package ruiseki.jfi.jfmuy.ic2.machine.fluidcanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import org.apache.logging.log4j.Level;

import ic2.api.recipe.ICannerEnrichRecipeManager;
import ic2.api.recipe.Recipes;
import ic2.core.IC2;
import ic2.core.Ic2Items;
import ic2.core.block.machine.gui.GuiCanner;
import ruiseki.jfi.JFI;
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
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class FluidCannerRecipeCategory implements IRecipeCategory<FluidCannerRecipeWrapper> {

    public static final String UID = "ic2.fluidcanner";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new FluidCannerRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            registry.addRecipes(getRecipes(), UID);
            registry.addRecipeClickArea(GuiCanner.class, 84, 19, 25, 16, UID);
            registry.addRecipeCatalyst(Ic2Items.canner, UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null fluid canner recipe!", t);
        }
    }

    public static List<FluidCannerRecipeWrapper> getRecipes() {
        List<FluidCannerRecipeWrapper> recipes = new ArrayList<>();
        if (Recipes.cannerEnrich != null && Recipes.cannerEnrich.getRecipes() != null) {
            for (Map.Entry<ICannerEnrichRecipeManager.Input, FluidStack> entry : Recipes.cannerEnrich.getRecipes()
                .entrySet()) {
                ICannerEnrichRecipeManager.Input input = entry.getKey();
                FluidStack outputFluid = entry.getValue();
                if (input != null && input.additive != null && input.fluid != null && outputFluid != null) {
                    recipes.add(new FluidCannerRecipeWrapper(input.additive, input.fluid, outputFluid));
                }
            }
        }
        return recipes;
    }

    private final IDrawable background;
    private final IDrawableAnimated progressBar;
    private final IDrawableAnimated cannerAnimation;

    public FluidCannerRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation guiTex = new ResourceLocation("ic2:textures/gui/GUICanner.png");
        this.background = guiHelper.createDrawable(guiTex, 5, 16, 140, 85);

        IDrawableStatic progressStatic = guiHelper.createDrawable(guiTex, 233, 0, 22, 16);
        this.progressBar = guiHelper
            .createAnimatedDrawable(progressStatic, 200, IDrawableAnimated.StartDirection.LEFT, false);

        IDrawableStatic cannerStatic = guiHelper.createDrawable(guiTex, 176, 0, 14, 14);
        this.cannerAnimation = guiHelper
            .createAnimatedDrawable(cannerStatic, 200, IDrawableAnimated.StartDirection.TOP, false);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return "Fluid Canning Machine";
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
        this.progressBar.draw(minecraft, 70, 6);
        this.cannerAnimation.draw(minecraft, 3, 45);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, FluidCannerRecipeWrapper recipeWrapper,
        IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();

        itemStacks.init(0, true, 74, 27);

        fluidStacks.init(0, true, 38, 30, 12, 47, 8000, true, null);

        fluidStacks.init(1, false, 116, 30, 12, 47, 8000, true, null);

        itemStacks.set(ingredients);
        fluidStacks.set(ingredients);
    }
}
