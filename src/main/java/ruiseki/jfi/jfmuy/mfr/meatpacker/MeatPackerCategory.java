package ruiseki.jfi.jfmuy.mfr.meatpacker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidRegistry;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.registry.GameRegistry;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.setup.MFRThings;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityMeatPacker;
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

public class MeatPackerCategory implements IRecipeCategory<MeatPackerWrapper> {

    public static final String UID = "minefactoryreloaded.meatpacker";

    public static int energyPerOperation;
    public static int fluidPerOperation;

    private static Item meatIngot;
    private static Item meatNugget;

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new MeatPackerCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            meatIngot = GameRegistry.findItem("MineFactoryReloaded", "meat.ingot.raw");
            meatNugget = GameRegistry.findItem("MineFactoryReloaded", "meat.nugget.raw");
            if (meatIngot == null || meatNugget == null) {
                meatIngot = GameRegistry.findItem("MineFactoryReloaded", "item.mfr.meat.ingot.raw");
                meatNugget = GameRegistry.findItem("MineFactoryReloaded", "item.mfr.meat.nugget.raw");
            }

            TileEntityMeatPacker dummy = new TileEntityMeatPacker();
            fluidPerOperation = dummy.getWorkMax() * 2;
            energyPerOperation = dummy.getActivationEnergy() * dummy.getWorkMax();

            List<MeatPackerWrapper> recipes = new ArrayList<>();
            if (meatIngot != null && FluidRegistry.getFluid("meat") != null) {
                recipes.add(
                    new MeatPackerWrapper(
                        FluidRegistry.getFluidStack("meat", fluidPerOperation),
                        new ItemStack(meatIngot)));
            }
            if (meatNugget != null && FluidRegistry.getFluid("pinkslime") != null) {
                recipes.add(
                    new MeatPackerWrapper(
                        FluidRegistry.getFluidStack("pinkslime", fluidPerOperation),
                        new ItemStack(meatNugget)));
            }

            registry.addRecipes(recipes, UID);

            registry.addRecipeCatalyst(new ItemStack(MFRThings.machineBlocks.get(0), 1, 14), UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    private final IDrawable background;
    private final IDrawable arrowOverlay;
    private final IDrawable tankOverlay;
    private final IDrawable energyBar;
    private final IDrawable workBar;

    public MeatPackerCategory(IGuiHelper guiHelper) {
        ResourceLocation guiTexture = new ResourceLocation("minefactoryreloaded", "textures/gui/meatpacker.png");
        ResourceLocation overlayTexture = new ResourceLocation("jfi", "textures/gui/mfr/overlays.png");

        this.background = guiHelper.createDrawable(guiTexture, 11, 13, 160, 65);
        this.arrowOverlay = guiHelper.createDrawable(overlayTexture, 0, 15, 22, 15);
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
        return StatCollector.translateToLocal("tile.mfr.machine.meatpacker.name");
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
    public void setRecipe(IRecipeLayout recipeLayout, MeatPackerWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();

        itemStacks.init(0, false, 48, 24);

        fluidStacks.init(0, true, 111, 2, 16, 60, 4000, true, tankOverlay);

        itemStacks.set(ingredients);
        fluidStacks.set(ingredients);
    }
}
