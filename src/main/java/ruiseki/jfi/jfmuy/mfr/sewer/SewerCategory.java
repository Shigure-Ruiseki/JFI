package ruiseki.jfi.jfmuy.mfr.sewer;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidRegistry;

import org.apache.logging.log4j.Level;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.setup.MFRThings;
import ruiseki.jfi.JFI;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IGuiFluidStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class SewerCategory implements IRecipeCategory<SewerWrapper> {

    public static final String UID = "minefactoryreloaded.sewer";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new SewerCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            List<SewerWrapper> recipes = new ArrayList<>();
            if (FluidRegistry.getFluid("sewage") != null) {
                recipes.add(new SewerWrapper(false));
            }
            if (FluidRegistry.getFluid("mobessence") != null) {
                recipes.add(new SewerWrapper(true));
            }

            registry.addRecipes(recipes, UID);

            registry.addRecipeCatalyst(new ItemStack(MFRThings.machineBlocks.get(0), 1, 10), UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Error loading Sewer recipes!", t);
        }
    }

    private final IDrawable background;
    private final IDrawable arrowOverlay;
    private final IDrawable tankOverlay;
    private final IDrawable animalIcon;
    private final IDrawable xpIcon;

    public SewerCategory(IGuiHelper guiHelper) {
        ResourceLocation guiTexture = new ResourceLocation("minefactoryreloaded", "textures/gui/sewer.png");
        ResourceLocation overlayTexture = new ResourceLocation("jfi", "textures/gui/mfr/overlays.png");

        this.background = guiHelper.createDrawable(guiTexture, 11, 13, 160, 65);
        this.arrowOverlay = guiHelper.createDrawable(overlayTexture, 0, 0, 22, 15);
        this.tankOverlay = guiHelper.createDrawable(guiTexture, 176, 0, 16, 60);

        this.animalIcon = guiHelper.createDrawable(overlayTexture, 0, 64, 16, 16);
        this.xpIcon = guiHelper.createDrawable(overlayTexture, 0, 80, 16, 16);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal("tile.mfr.machine.sewer.name");
    }

    @Override
    public String getModName() {
        return MineFactoryReloadedCore.modName;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        this.arrowOverlay.draw(minecraft, 76, 25);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, SewerWrapper recipeWrapper, IIngredients ingredients) {
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();

        if (!recipeWrapper.isEssenceRecipe()) {
            fluidStacks.init(0, false, 141, 2, 16, 60, 4000, true, tankOverlay);
        } else {
            fluidStacks.init(0, false, 121, 2, 16, 60, 4000, true, tankOverlay);
        }

        fluidStacks.set(ingredients);
    }
}
