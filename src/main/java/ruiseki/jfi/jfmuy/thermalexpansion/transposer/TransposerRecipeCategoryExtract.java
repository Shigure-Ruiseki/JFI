package ruiseki.jfi.jfmuy.thermalexpansion.transposer;

import static java.util.Collections.singletonList;
import static ruiseki.jfmuy.api.recipe.IFocus.Mode.INPUT;
import static ruiseki.jfmuy.api.recipe.IFocus.Mode.OUTPUT;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import org.apache.logging.log4j.Level;

import cofh.lib.util.helpers.FluidHelper;
import cofh.thermalexpansion.block.machine.BlockMachine;
import cofh.thermalexpansion.util.crafting.TransposerManager;
import cofh.thermalexpansion.util.crafting.TransposerManager.RecipeTransposer;
import ruiseki.jfi.JFI;
import ruiseki.jfi.jfmuy.thermalexpansion.RecipeUidsTE;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IGuiFluidStackGroup;
import ruiseki.jfmuy.api.gui.IGuiIngredient;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IFocus;
import ruiseki.okcore.fluid.FluidHelpers;
import ruiseki.okcore.helper.LangHelpers;

public class TransposerRecipeCategoryExtract extends TransposerRecipeCategory {

    public static void initialize(IModRegistry registry) {
        try {
            IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
            IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

            registry.addRecipes(getRecipes(guiHelper), RecipeUidsTE.TRANSPOSER_EXTRACT);
            registry.addRecipeCatalyst(BlockMachine.transposer, RecipeUidsTE.TRANSPOSER_EXTRACT);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    public static List<TransposerRecipeWrapper> getRecipes(IGuiHelper guiHelper) {

        List<TransposerRecipeWrapper> recipes = new ArrayList<>();

        for (RecipeTransposer recipe : TransposerManager.getExtractionRecipeList()) {
            recipes.add(new TransposerRecipeWrapper(guiHelper, recipe, RecipeUidsTE.TRANSPOSER_EXTRACT));
        }
        return recipes;
    }

    public TransposerRecipeCategoryExtract(IGuiHelper guiHelper) {

        super(guiHelper);

        localizedName += " - " + LangHelpers.localize("gui.thermalexpansion.jei.transposer.modeEmpty");
        icon = guiHelper.createDrawableIngredient(new ItemStack(Items.bucket));
    }

    @Nonnull
    @Override
    public String getUid() {

        return RecipeUidsTE.TRANSPOSER_EXTRACT;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, TransposerRecipeWrapper recipeWrapper, IIngredients ingredients) {

        List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
        List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);
        List<List<FluidStack>> fluids = ingredients.getOutputs(VanillaTypes.FLUID);

        IFocus<?> focus = recipeLayout.getFocus();
        if (focus != null) {
            if (focus.getMode() == INPUT && focus.getValue() instanceof ItemStack) {
                List<FluidStack> focusFluids = new ArrayList<>();
                ItemStack input = (ItemStack) focus.getValue();
                FluidStack contained = FluidHelpers.getFluidContained(input);
                if (contained != null) {
                    for (FluidStack fluid : fluids.get(0)) {
                        if (FluidHelper.isFluidEqual(contained, fluid)) {
                            focusFluids.add(fluid);
                        }
                    }
                    if (focusFluids.size() != fluids.get(0)
                        .size()) {
                        fluids = singletonList(focusFluids);
                    }
                }
            } else if (focus.getMode() == OUTPUT && focus.getValue() instanceof FluidStack) {
                List<ItemStack> focusInputs = new ArrayList<>();
                FluidStack fluid = (FluidStack) focus.getValue();
                for (ItemStack stack : inputs.get(0)) {
                    FluidStack contained = FluidHelpers.getFluidContained(stack);
                    if (contained == null || FluidHelper.isFluidEqual(fluid, contained)) {
                        focusInputs.add(stack);
                    }
                }
                if (focusInputs.size() != inputs.get(0)
                    .size()) {
                    inputs = singletonList(focusInputs);
                }
            }
        }

        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

        Map<Integer, ? extends IGuiIngredient<FluidStack>> fluidIngredients = guiFluidStacks.getGuiIngredients();
        recipeWrapper.setGuiFluids(fluidIngredients);

        guiItemStacks.init(0, true, 30, 10);
        guiItemStacks.init(1, false, 30, 41);
        guiFluidStacks.init(0, false, 103, 1, 16, 60, FluidHelpers.BUCKET_VOLUME, false, tankOverlay);

        guiItemStacks.set(0, inputs.get(0));
        guiItemStacks.set(1, outputs.isEmpty() ? null : outputs.get(0));
        guiFluidStacks.set(0, fluids.get(0));

        guiItemStacks.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {

            if (slotIndex == 1 && recipeWrapper.chance < 100) {
                tooltip.add(LangHelpers.localize("info.cofh.chance") + ": " + recipeWrapper.chance + "%");
            }
        });
    }

}
