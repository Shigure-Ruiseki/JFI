package ruiseki.jfi.jfmuy.mfr.sludgeboiler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.WeightedRandom.Item;
import net.minecraftforge.fluids.FluidRegistry;

import org.apache.logging.log4j.Level;

import cofh.lib.util.WeightedRandomItemStack;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.setup.MFRThings;
import powercrystals.minefactoryreloaded.tile.machine.TileEntitySludgeBoiler;
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

public class SludgeBoilerCategory implements IRecipeCategory<SludgeBoilerWrapper> {

    public static final String UID = "minefactoryreloaded.sludgeboiler";

    public static int energyPerOperation;
    public static int sludgePerOperation;
    public static int totalWeight = 0;

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new SludgeBoilerCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            TileEntitySludgeBoiler dummy = new TileEntitySludgeBoiler();
            sludgePerOperation = dummy.getWorkMax() * 10;
            energyPerOperation = dummy.getActivationEnergy() * dummy.getWorkMax();

            List<Item> drops = MFRRegistry.getSludgeDrops();
            totalWeight = 0;
            for (Item drop : drops) {
                totalWeight += drop.itemWeight;
            }

            List<SludgeBoilerWrapper> recipes = new ArrayList<>();
            if (FluidRegistry.getFluid("sludge") != null && totalWeight > 0) {
                for (Item drop : drops) {
                    if (drop instanceof WeightedRandomItemStack) {
                        WeightedRandomItemStack stackDrop = (WeightedRandomItemStack) drop;
                        recipes.add(
                            new SludgeBoilerWrapper(
                                FluidRegistry.getFluidStack("sludge", sludgePerOperation),
                                stackDrop.getStack(),
                                drop.itemWeight,
                                totalWeight));
                    }
                }
            }

            registry.addRecipes(recipes, UID);

            registry.addRecipeCatalyst(new ItemStack(MFRThings.machineBlocks.get(0), 1, 9), UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    private final IDrawable background;
    private final IDrawable arrowOverlay;
    private final IDrawable tankOverlay;
    private final IDrawable energyBar;
    private final IDrawable workBar;

    public SludgeBoilerCategory(IGuiHelper guiHelper) {
        ResourceLocation guiTexture = new ResourceLocation("minefactoryreloaded", "textures/gui/sludgeboiler.png");
        ResourceLocation overlayTexture = new ResourceLocation("jfi", "textures/gui/mfr/overlays.png");

        this.background = guiHelper.createDrawable(guiTexture, 11, 13, 160, 65);
        this.arrowOverlay = guiHelper.createDrawable(overlayTexture, 0, 15, 22, 15);
        this.tankOverlay = guiHelper.createDrawable(guiTexture, 176, 0, 16, 60);

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
        return StatCollector.translateToLocal("tile.mfr.machine.sludgeboiler.name");
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
    public void setRecipe(IRecipeLayout recipeLayout, SludgeBoilerWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();

        itemStacks.init(0, false, 48, 24);

        fluidStacks.init(0, true, 111, 2, 16, 60, 4000, true, tankOverlay);

        itemStacks.set(ingredients);
        fluidStacks.set(ingredients);
    }
}
