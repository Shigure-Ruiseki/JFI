package ruiseki.jfi.jfmuy.mfr.grinder;

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
import powercrystals.minefactoryreloaded.tile.machine.TileEntityGrinder;
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

public class GrinderCategory implements IRecipeCategory<GrinderWrapper> {

    public static final String UID = "minefactoryreloaded.grinder";

    public static int energyPerOperation;

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new GrinderCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            registry.addRecipes(getRecipes(), GrinderCategory.UID);
            registry.addRecipeCatalyst(new ItemStack(MFRThings.machineBlocks.get(0), 1, 13), GrinderCategory.UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    private static List<GrinderWrapper> getRecipes() {
        TileEntityGrinder dummy = new TileEntityGrinder();
        energyPerOperation = dummy.getActivationEnergy() * dummy.getWorkMax();

        List<GrinderWrapper> recipes = new ArrayList<>();
        recipes.add(new GrinderWrapper());

        return recipes;
    }

    private final IDrawable background;
    private final IDrawable arrowOverlay;
    private final IDrawable mobIconOverlay;
    private final IDrawable energyBar;
    private final IDrawable workBar;

    public GrinderCategory(IGuiHelper guiHelper) {
        ResourceLocation guiTexture = new ResourceLocation("minefactoryreloaded", "textures/gui/grinder.png");
        ResourceLocation overlayTexture = new ResourceLocation("jfi", "textures/gui/mfr/overlays.png");

        this.background = guiHelper.createDrawable(guiTexture, 11, 13, 160, 65);

        this.arrowOverlay = guiHelper.createDrawable(overlayTexture, 0, 0, 22, 15);
        this.mobIconOverlay = guiHelper.createDrawable(overlayTexture, 16, 64, 16, 16);

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
        return StatCollector.translateToLocal("tile.mfr.machine.grinder.name");
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
        this.arrowOverlay.draw(minecraft, 74, 25);
        this.mobIconOverlay.draw(minecraft, 44, 24);
        this.energyBar.draw(minecraft, 129, 0);
        this.workBar.draw(minecraft, 139, 0);
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        if (mouseX >= 44 && mouseX <= 60 && mouseY >= 24 && mouseY <= 40) {
            return Arrays.asList(
                StatCollector.translateToLocal("jfi.mfr.grinder.mobs"),
                EnumChatFormatting.GRAY + StatCollector.translateToLocal("jfi.mfr.grinder.mobs.1"),
                EnumChatFormatting.GRAY + StatCollector.translateToLocal("jfi.mfr.grinder.mobs.2"));
        } else if (mouseX >= 129 && mouseX <= 137 && mouseY >= 0 && mouseY <= 62) {
            return Collections.singletonList(energyPerOperation + " RF");
        }
        return Collections.emptyList();
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, GrinderWrapper recipeWrapper, IIngredients ingredients) {
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();

        fluidStacks.init(0, false, 111, 2, 16, 60, 4000, true, null);

        fluidStacks.set(ingredients);
    }
}
