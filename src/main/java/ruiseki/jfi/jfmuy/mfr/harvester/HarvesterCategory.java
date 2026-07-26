package ruiseki.jfi.jfmuy.mfr.harvester;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.apache.logging.log4j.Level;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.setup.MFRThings;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityHarvester;
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

public class HarvesterCategory implements IRecipeCategory<HarvesterWrapper> {

    public static final String UID = "minefactoryreloaded.harvester";

    public static int sludgePerOperation = 10;
    public static int energyPerOperation;

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new HarvesterCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            registry.addRecipes(getRecipes(), HarvesterCategory.UID);
            registry.addRecipeCatalyst(new ItemStack(MFRThings.machineBlocks.get(0), 1, 1), HarvesterCategory.UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    private static List<HarvesterWrapper> getRecipes() {
        TileEntityHarvester dummy = new TileEntityHarvester();
        energyPerOperation = dummy.getActivationEnergy() * dummy.getWorkMax();

        List<HarvesterWrapper> recipes = new ArrayList<>();
        recipes.add(new HarvesterWrapper(sludgePerOperation));

        return recipes;
    }

    private final IDrawable background;
    private final IDrawable arrowOverlay;
    private final IDrawable harvestableIconOverlay;
    private final IDrawable energyBar;
    private final IDrawable workBar;

    public HarvesterCategory(IGuiHelper guiHelper) {
        ResourceLocation guiTexture = new ResourceLocation("minefactoryreloaded", "textures/gui/harvester.png");
        ResourceLocation overlayTexture = new ResourceLocation("jfi", "textures/gui/mfr/overlays.png");

        this.background = guiHelper.createDrawable(guiTexture, 11, 13, 160, 65);

        this.arrowOverlay = guiHelper.createDrawable(overlayTexture, 0, 0, 22, 15);
        this.harvestableIconOverlay = guiHelper.createDrawable(overlayTexture, 0, 48, 16, 16);

        this.energyBar = guiHelper.createDrawable(guiTexture, 176, 58, 8, 62);

        this.workBar = guiHelper.createAnimatedDrawable(
            guiHelper.createDrawable(guiTexture, 185, 58, 8, 62),
            10,
            IDrawableAnimated.StartDirection.BOTTOM,
            false);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal("tile.mfr.machine.harvester.name");
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
        this.harvestableIconOverlay.draw(minecraft, 48, 24);
        this.arrowOverlay.draw(minecraft, 76, 25);
        this.energyBar.draw(minecraft, 129, 0);
        this.workBar.draw(minecraft, 139, 0);
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        if (mouseX >= 48 && mouseX <= 64 && mouseY >= 24 && mouseY <= 40) {
            return Arrays.asList(
                StatCollector.translateToLocal("jfi.mfr.harvester.harvestables"),
                EnumChatFormatting.GRAY + StatCollector.translateToLocal("jfi.mfr.harvester.harvestables.1"));
        } else if (mouseX >= 129 && mouseX <= 137 && mouseY >= 0 && mouseY <= 62) {
            return Collections.singletonList(energyPerOperation + " RF");
        }
        return Collections.emptyList();
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, HarvesterWrapper recipeWrapper, IIngredients ingredients) {
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();

        fluidStacks.init(0, false, 111, 2, 16, 60, 4000, true, null);

        fluidStacks.set(ingredients);
    }
}
