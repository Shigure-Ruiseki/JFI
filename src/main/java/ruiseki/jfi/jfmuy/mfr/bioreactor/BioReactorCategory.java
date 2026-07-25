package ruiseki.jfi.jfmuy.mfr.bioreactor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.apache.logging.log4j.Level;

import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.IFactoryPlantable;
import powercrystals.minefactoryreloaded.setup.MFRThings;
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

public class BioReactorCategory implements IRecipeCategory<BioReactorWrapper> {

    public static final String UID = "minefactoryreloaded.bioreactor";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new BioReactorCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            registry.addRecipes(getRecipes(), BioReactorCategory.UID);
            registry.addRecipeCatalyst(new ItemStack(MFRThings.machineBlocks.get(1), 1, 10), BioReactorCategory.UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    private static List<BioReactorWrapper> getRecipes() {

        List<BioReactorWrapper> recipes = new ArrayList<>();
        Map<Item, IFactoryPlantable> plantables = MFRRegistry.getPlantables();

        if (plantables != null && !plantables.isEmpty()) {
            for (Map.Entry<Item, IFactoryPlantable> entry : plantables.entrySet()) {
                Item item = entry.getKey();
                IFactoryPlantable plantableHandler = entry.getValue();

                if (item != null && plantableHandler != null) {
                    List<ItemStack> subItems = new ArrayList<>();

                    item.getSubItems(item, null, subItems);

                    if (subItems.isEmpty()) {
                        ItemStack defaultStack = new ItemStack(item, 1, 0);
                        if (plantableHandler.canBePlanted(defaultStack, true)) {
                            recipes.add(new BioReactorWrapper(defaultStack));
                        }
                    } else {
                        List<ItemStack> validPlantables = new ArrayList<>();
                        for (ItemStack stack : subItems) {
                            if (stack != null && plantableHandler.canBePlanted(stack, true)) {
                                validPlantables.add(stack);
                            }
                        }
                        if (!validPlantables.isEmpty()) {
                            recipes.add(new BioReactorWrapper(validPlantables));
                        }
                    }
                }
            }
        }

        return recipes;
    }

    private final IDrawable background;
    private final IDrawable arrowOverlay;
    private final IDrawable efficiencyBar;
    private final IDrawable bufferBar;

    public BioReactorCategory(IGuiHelper guiHelper) {

        ResourceLocation guiTexture = new ResourceLocation("minefactoryreloaded", "textures/gui/bioreactor.png");
        this.background = guiHelper.createDrawable(guiTexture, 7, 14, 160, 65);

        this.arrowOverlay = guiHelper
            .createDrawable(new ResourceLocation("jfi", "textures/gui/mfr/overlays.png"), 0, 0, 22, 15);

        this.efficiencyBar = guiHelper.createDrawable(guiTexture, 176, 58, 8, 19);

        this.bufferBar = guiHelper.createAnimatedDrawable(
            guiHelper.createDrawable(guiTexture, 185, 58, 8, 62),
            120,
            IDrawableAnimated.StartDirection.BOTTOM,
            false);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal("tile.mfr.machine.bioreactor.name");
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
        this.arrowOverlay.draw(minecraft, 73, 11);
        this.efficiencyBar.draw(minecraft, 143, 42);
        this.bufferBar.draw(minecraft, 152, -1);
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        if (mouseX >= 132 && mouseX <= 140 && mouseY >= 2 && mouseY <= 62) {
            return Arrays.asList(
                StatCollector.translateToLocal("jfi.mfr.bioreactor.efficiency"),
                EnumChatFormatting.GRAY + StatCollector.translateToLocal("jfi.mfr.bioreactor.efficiency.1"),
                EnumChatFormatting.GRAY + StatCollector.translateToLocal("jfi.mfr.bioreactor.efficiency.2"),
                EnumChatFormatting.GRAY + StatCollector.translateToLocal("jfi.mfr.bioreactor.efficiency.3"));
        }
        return Collections.emptyList();
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, BioReactorWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();

        itemStacks.init(0, true, 0, 0);

        fluidStacks.init(0, false, 125, 1, 16, 60, 4000, true, null);

        itemStacks.set(ingredients);
        fluidStacks.set(ingredients);

    }
}
