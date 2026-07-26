package ruiseki.jfi.jfmuy.mfr.lavafabricator;

import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.apache.logging.log4j.Level;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.setup.MFRThings;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityLavaFabricator;
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

public class LavaFabricatorCategory implements IRecipeCategory<LavaFabricatorWrapper> {

    public static final String UID = "minefactoryreloaded.lavafabricator";

    public static final int LAVA_PER_OPERATION = 20;
    public static int energyPerOperation;

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new LavaFabricatorCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            TileEntityLavaFabricator dummy = new TileEntityLavaFabricator();
            energyPerOperation = dummy.getActivationEnergy();

            registry.addRecipes(Collections.singletonList(new LavaFabricatorWrapper()), UID);

            registry.addRecipeCatalyst(new ItemStack(MFRThings.machineBlocks.get(1), 1, 5), UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Error loading Lava Fabricator recipes!", t);
        }
    }

    private final IDrawable background;
    private final IDrawable tankOverlay;
    private final IDrawable energyBar;
    private final IDrawable workBar;

    public LavaFabricatorCategory(IGuiHelper guiHelper) {
        ResourceLocation guiTexture = new ResourceLocation("minefactoryreloaded", "textures/gui/lavafabricator.png");

        this.background = guiHelper.createDrawable(guiTexture, 11, 13, 160, 65);
        this.tankOverlay = guiHelper.createDrawable(guiTexture, 176, 0, 16, 60);
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
        return StatCollector.translateToLocal("tile.mfr.machine.lavafabricator.name");
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
    public void setRecipe(IRecipeLayout recipeLayout, LavaFabricatorWrapper recipeWrapper, IIngredients ingredients) {
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();

        fluidStacks.init(0, false, 111, 2, 16, 60, 4000, true, tankOverlay);
        fluidStacks.set(ingredients);
    }
}
