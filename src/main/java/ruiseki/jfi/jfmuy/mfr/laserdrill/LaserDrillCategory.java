package ruiseki.jfi.jfmuy.mfr.laserdrill;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.WeightedRandom;

import org.apache.logging.log4j.Level;

import cofh.lib.util.WeightedRandomItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.setup.MFRThings;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityLaserDrill;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityLaserDrillPrecharger;
import ruiseki.jfi.JFI;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IDrawableAnimated;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class LaserDrillCategory implements IRecipeCategory<LaserDrillWrapper> {

    public static final String UID = "minefactoryreloaded.laserdrill";

    public static int energyPerOperation;
    private static List<WeightedRandom.Item> laserOres;
    private static int totalWeight;
    private static Map<Integer, List<ItemStack>> laserPreferredOres;
    private static Item laserFocus;

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new LaserDrillCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            registry.addRecipes(getRecipes(), LaserDrillCategory.UID);
            registry.addRecipeCatalyst(new ItemStack(MFRThings.machineBlocks.get(2), 1, 0), LaserDrillCategory.UID);
            registry.addRecipeCatalyst(new ItemStack(MFRThings.machineBlocks.get(2), 1, 1), LaserDrillCategory.UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    private static List<LaserDrillWrapper> getRecipes() {
        totalWeight = 0;
        laserOres = MFRRegistry.getLaserOres();
        for (WeightedRandom.Item ore : laserOres) {
            totalWeight += ore.itemWeight;
        }

        laserPreferredOres = new HashMap<>();
        for (int i = 0; i <= 15; i++) {
            List<ItemStack> preferredOres = MFRRegistry.getLaserPreferredOres(i);
            laserPreferredOres.put(i, preferredOres);
        }

        TileEntityLaserDrillPrecharger dummyPrecharger = new TileEntityLaserDrillPrecharger();
        TileEntityLaserDrill dummyDrill = new TileEntityLaserDrill();
        energyPerOperation = dummyPrecharger.getActivationEnergy() * dummyDrill.getWorkMax();

        laserFocus = GameRegistry.findItem("MineFactoryReloaded", "laserfocus");
        if (laserFocus == null) {
            laserFocus = GameRegistry.findItem("MineFactoryReloaded", "item.mfr.laserfocus");
        }

        List<LaserDrillWrapper> recipes = new ArrayList<>();
        for (WeightedRandom.Item drop : laserOres) {
            if (drop instanceof WeightedRandomItemStack) {
                boolean hasFocus = false;
                ItemStack dropStack = ((WeightedRandomItemStack) drop).getStack();
                for (int i : laserPreferredOres.keySet()) {
                    List<ItemStack> preferredStacks = laserPreferredOres.get(i);
                    if (preferredStacks != null) {
                        for (ItemStack preferredStack : preferredStacks) {
                            if (areStacksSameTypeCraftingSafe(preferredStack, dropStack)) {
                                ItemStack focusStack = laserFocus != null ? new ItemStack(laserFocus, 1, i) : null;
                                recipes.add(new LaserDrillWrapper(dropStack, drop.itemWeight, totalWeight, focusStack));
                                hasFocus = true;
                            }
                        }
                    }
                }
                if (!hasFocus) {
                    recipes.add(new LaserDrillWrapper(dropStack, drop.itemWeight, totalWeight, null));
                }
            }
        }

        return recipes;
    }

    private static boolean areStacksSameTypeCraftingSafe(ItemStack stack1, ItemStack stack2) {
        return stack1 != null && stack2 != null
            && stack1.getItem() == stack2.getItem()
            && (stack1.getItemDamage() == stack2.getItemDamage() || stack1.getItemDamage() == 32767);
    }

    private final IDrawable background;
    private final IDrawable slotOverlay;
    private final IDrawable arrowOverlay;
    private final IDrawable laserOverlay;
    private final IDrawable energyBar;
    private final IDrawable workBar;

    public LaserDrillCategory(IGuiHelper guiHelper) {
        ResourceLocation guiTexture = new ResourceLocation("minefactoryreloaded", "textures/gui/laserdrill.png");
        ResourceLocation overlayTexture = new ResourceLocation("jfi", "textures/gui/mfr/overlays.png");

        this.background = guiHelper.createDrawable(guiTexture, 11, 13, 160, 65);

        this.slotOverlay = guiHelper.createDrawable(overlayTexture, 0, 30, 18, 18);
        this.arrowOverlay = guiHelper.createDrawable(overlayTexture, 0, 15, 22, 15);
        this.laserOverlay = guiHelper.createDrawable(guiTexture, 176, 0, 16, 60);

        this.energyBar = guiHelper.createDrawable(guiTexture, 176, 58, 8, 62);

        this.workBar = guiHelper.createAnimatedDrawable(
            guiHelper.createDrawable(guiTexture, 185, 58, 8, 62),
            60,
            IDrawableAnimated.StartDirection.BOTTOM,
            false);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal("tile.mfr.machine.laserdrill.name");
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
        this.slotOverlay.draw(minecraft, 20, 24);
        this.arrowOverlay.draw(minecraft, 104, 25);
        this.laserOverlay.draw(minecraft, 111, 2);
        this.energyBar.draw(minecraft, 139, 0);
        this.workBar.draw(minecraft, 149, 0);

        minecraft.fontRenderer.drawString(StatCollector.translateToLocal("jfi.mfr.laserdrill.focus"), 20, 44, 0x808080);
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        if (mouseX >= 139 && mouseX <= 147 && mouseY >= 0 && mouseY <= 62) {
            return Collections.singletonList(energyPerOperation + " RF");
        }
        return Collections.emptyList();
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, LaserDrillWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, true, 20, 24);

        itemStacks.init(1, false, 74, 24);

        itemStacks.set(ingredients);
    }
}
