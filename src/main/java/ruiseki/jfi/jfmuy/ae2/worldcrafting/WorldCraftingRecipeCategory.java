package ruiseki.jfi.jfmuy.ae2.worldcrafting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import org.apache.logging.log4j.Level;

import appeng.api.AEApi;
import appeng.api.definitions.IDefinitions;
import appeng.api.definitions.IMaterials;
import appeng.core.AEConfig;
import appeng.core.features.AEFeature;
import appeng.core.localization.GuiText;
import ruiseki.jfi.JFI;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class WorldCraftingRecipeCategory implements IRecipeCategory<WorldCraftingRecipeWrapper> {

    public static final String UID = "ae2.world_crafting";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new WorldCraftingRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
            IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

            registry.addRecipeCatalyst(
                new ItemStack(
                    AEApi.instance()
                        .definitions()
                        .materials()
                        .certusQuartzCrystal()
                        .maybeItem()
                        .orNull()),
                UID);
            registry.addRecipes(getRecipes(), UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null grinder recipe initialization!", t);
        }
    }

    public static List<WorldCraftingRecipeWrapper> getRecipes() {
        List<WorldCraftingRecipeWrapper> recipes = new ArrayList<>();
        final IDefinitions definitions = AEApi.instance()
            .definitions();
        final IMaterials materials = definitions.materials();

        if (AEConfig.instance.isFeatureEnabled(AEFeature.CertusQuartzWorldGen)) {
            final String message = GuiText.ChargedQuartz.getLocal() + "\n\n" + GuiText.ChargedQuartzFind.getLocal();
            for (ItemStack stack : materials.certusQuartzCrystalCharged()
                .maybeStack(1)
                .asSet()) {
                recipes.add(new WorldCraftingRecipeWrapper(stack, message));
            }
        }

        if (AEConfig.instance.isFeatureEnabled(AEFeature.MeteoriteWorldGen)) {
            final String pressMsg = GuiText.inWorldCraftingPresses.getLocal();
            for (ItemStack stack : materials.logicProcessorPress()
                .maybeStack(1)
                .asSet()) {
                recipes.add(new WorldCraftingRecipeWrapper(stack, pressMsg));
            }
            for (ItemStack stack : materials.calcProcessorPress()
                .maybeStack(1)
                .asSet()) {
                recipes.add(new WorldCraftingRecipeWrapper(stack, pressMsg));
            }
            for (ItemStack stack : materials.engProcessorPress()
                .maybeStack(1)
                .asSet()) {
                recipes.add(new WorldCraftingRecipeWrapper(stack, pressMsg));
            }
        }

        if (AEConfig.instance.isFeatureEnabled(AEFeature.InWorldFluix)) {
            final String message = GuiText.inWorldFluix.getLocal();
            for (ItemStack stack : materials.fluixCrystal()
                .maybeStack(1)
                .asSet()) {
                recipes.add(new WorldCraftingRecipeWrapper(stack, message));
            }
        }

        if (AEConfig.instance.isFeatureEnabled(AEFeature.InWorldSingularity)) {
            final String message = GuiText.inWorldSingularity.getLocal();
            for (ItemStack stack : materials.qESingularity()
                .maybeStack(1)
                .asSet()) {
                recipes.add(new WorldCraftingRecipeWrapper(stack, message));
            }
        }

        if (AEConfig.instance.isFeatureEnabled(AEFeature.InWorldPurification)) {
            for (ItemStack stack : materials.purifiedCertusQuartzCrystal()
                .maybeStack(1)
                .asSet()) {
                recipes.add(new WorldCraftingRecipeWrapper(stack, GuiText.inWorldPurificationCertus.getLocal()));
            }
            for (ItemStack stack : materials.purifiedNetherQuartzCrystal()
                .maybeStack(1)
                .asSet()) {
                recipes.add(new WorldCraftingRecipeWrapper(stack, GuiText.inWorldPurificationNether.getLocal()));
            }
            for (ItemStack stack : materials.purifiedFluixCrystal()
                .maybeStack(1)
                .asSet()) {
                recipes.add(new WorldCraftingRecipeWrapper(stack, GuiText.inWorldPurificationFluix.getLocal()));
            }
        }

        return recipes;
    }

    private final IDrawable background;

    public WorldCraftingRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(166, 176);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return GuiText.InWorldCrafting.getLocal();
    }

    @Override
    public String getModName() {
        return "Applied Energistics 2";
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, WorldCraftingRecipeWrapper recipeWrapper,
        IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, false, 74, 4);
        itemStacks.set(ingredients);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {

    }
}
