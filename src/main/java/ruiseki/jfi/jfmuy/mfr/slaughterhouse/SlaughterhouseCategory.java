package ruiseki.jfi.jfmuy.mfr.slaughterhouse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidRegistry;

import org.apache.logging.log4j.Level;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.setup.MFRThings;
import powercrystals.minefactoryreloaded.tile.machine.TileEntitySlaughterhouse;
import ruiseki.jfi.JFI;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IDrawableAnimated;
import ruiseki.jfmuy.api.gui.IGuiFluidStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class SlaughterhouseCategory implements IRecipeCategory<SlaughterhouseWrapper> {

    public static final String UID = "minefactoryreloaded.slaughterhouse";

    public static int energyPerOperation;

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new SlaughterhouseCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            TileEntitySlaughterhouse dummy = new TileEntitySlaughterhouse();
            energyPerOperation = dummy.getActivationEnergy() * dummy.getWorkMax();

            List<SlaughterhouseWrapper> recipes = new ArrayList<>();
            if (FluidRegistry.getFluid("meat") != null && FluidRegistry.getFluid("pinkslime") != null) {
                recipes.add(new SlaughterhouseWrapper());
            }

            registry.addRecipes(recipes, UID);

            registry.addRecipeCatalyst(new ItemStack(MFRThings.machineBlocks.get(1), 1, 13), UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Error loading Slaughterhouse recipes!", t);
        }
    }

    private final IDrawable background;
    private final IDrawable arrowOverlay;
    private final IDrawable tankOverlay;
    private final IDrawable energyBar;
    private final IDrawable workBar;

    public SlaughterhouseCategory(IGuiHelper guiHelper) {
        ResourceLocation guiTexture = new ResourceLocation("minefactoryreloaded", "textures/gui/slaughterhouse.png");
        ResourceLocation overlayTexture = new ResourceLocation("jfi", "textures/gui/mfr/overlays.png");

        this.background = guiHelper.createDrawable(guiTexture, 11, 13, 160, 65);
        this.arrowOverlay = guiHelper.createDrawable(overlayTexture, 0, 0, 22, 15);
        this.tankOverlay = guiHelper.createDrawable(guiTexture, 176, 0, 16, 60);

        this.energyBar = guiHelper.createDrawable(guiTexture, 176, 58, 8, 62);

        this.workBar = guiHelper.createAnimatedDrawable(
            guiHelper.createDrawable(guiTexture, 185, 58, 8, 62),
            20,
            IDrawableAnimated.StartDirection.BOTTOM,
            false);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal("tile.mfr.machine.slaughterhouse.name");
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
        this.arrowOverlay.draw(minecraft, 56, 25);
        this.energyBar.draw(minecraft, 129, 0);
        this.workBar.draw(minecraft, 139, 0);
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        if (mouseX >= 129 && mouseX <= 137 && mouseY >= 0 && mouseY <= 62) {
            return Collections.singletonList(energyPerOperation + " RF");
        }
        return Collections.emptyList();
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, SlaughterhouseWrapper recipeWrapper, IIngredients ingredients) {
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();

        fluidStacks.init(0, false, 111, 2, 16, 60, 4000, true, tankOverlay);

        fluidStacks.init(1, false, 91, 2, 16, 60, 4000, true, tankOverlay);

        fluidStacks.set(ingredients);
    }
}
