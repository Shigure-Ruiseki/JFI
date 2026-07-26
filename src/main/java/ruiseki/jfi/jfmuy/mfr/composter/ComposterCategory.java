package ruiseki.jfi.jfmuy.mfr.composter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.registry.GameRegistry;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.setup.MFRThings;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityComposter;
import ruiseki.jfi.JFI;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IDrawableAnimated;
import ruiseki.jfmuy.api.gui.IGuiFluidStackGroup;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class ComposterCategory implements IRecipeCategory<ComposterWrapper> {

    public static final String UID = "minefactoryreloaded.composter";

    public static int sewagePerOperation;
    public static int energyPerOperation;
    private static Item fertilizer;

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new ComposterCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            registry.addRecipes(getRecipes(), ComposterCategory.UID);
            registry.addRecipeCatalyst(new ItemStack(MFRThings.machineBlocks.get(0), 1, 11), ComposterCategory.UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    private static List<ComposterWrapper> getRecipes() {
        fertilizer = GameRegistry.findItem("MineFactoryReloaded", "fertilizer");
        if (fertilizer == null) {
            fertilizer = GameRegistry.findItem("MineFactoryReloaded", "item.mfr.fertilizer");
        }

        TileEntityComposter dummy = new TileEntityComposter();
        sewagePerOperation = dummy.getWorkMax() * 20;
        energyPerOperation = dummy.getActivationEnergy() * dummy.getWorkMax();

        List<ComposterWrapper> recipes = new ArrayList<>();
        if (fertilizer != null) {
            recipes.add(new ComposterWrapper(new ItemStack(fertilizer), sewagePerOperation));
        }

        return recipes;
    }

    private final IDrawable background;
    private final IDrawable arrowOverlay;
    private final IDrawable energyBar;
    private final IDrawable workBar;

    public ComposterCategory(IGuiHelper guiHelper) {
        ResourceLocation guiTexture = new ResourceLocation("minefactoryreloaded", "textures/gui/composter.png");
        this.background = guiHelper.createDrawable(guiTexture, 11, 13, 160, 65);

        this.arrowOverlay = guiHelper
            .createDrawable(new ResourceLocation("jfi", "textures/gui/mfr/overlays.png"), 0, 15, 22, 15);

        this.energyBar = guiHelper.createDrawable(guiTexture, 176, 58, 8, 62);

        this.workBar = guiHelper.createAnimatedDrawable(
            guiHelper.createDrawable(guiTexture, 185, 58, 8, 62),
            40,
            IDrawableAnimated.StartDirection.BOTTOM,
            false);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal("tile.mfr.machine.composter.name");
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
    public void setRecipe(IRecipeLayout recipeLayout, ComposterWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();

        fluidStacks.init(0, true, 111, 2, 16, 60, 4000, true, null);

        itemStacks.init(0, false, 48, 24);

        fluidStacks.set(ingredients);
        itemStacks.set(ingredients);
    }
}
