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
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import cofh.lib.util.helpers.FluidHelper;
import cofh.lib.util.helpers.StringHelper;
import cofh.thermalexpansion.block.machine.BlockMachine;
import cofh.thermalexpansion.gui.client.machine.GuiTransposer;
import cofh.thermalexpansion.util.crafting.TransposerManager;
import cofh.thermalexpansion.util.crafting.TransposerManager.RecipeTransposer;
import ruiseki.jfi.jfmuy.thermalexpansion.RecipeUidsTE;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IGuiFluidStackGroup;
import ruiseki.jfmuy.api.gui.IGuiIngredient;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredientRegistry;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IFocus;
import ruiseki.okcore.fluid.FluidHelpers;

public class TransposerRecipeCategoryFill extends TransposerRecipeCategory {

    public static void initialize(IModRegistry registry) {

        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        registry.addRecipes(getRecipes(guiHelper, registry.getIngredientRegistry()), RecipeUidsTE.TRANSPOSER_FILL);
        registry.addRecipeCatalyst(BlockMachine.transposer, RecipeUidsTE.TRANSPOSER_FILL);
    }

    public static List<TransposerRecipeWrapper> getRecipes(IGuiHelper guiHelper,
        IIngredientRegistry ingredientRegistry) {

        List<TransposerRecipeWrapper> recipes = new ArrayList<>();

        for (RecipeTransposer recipe : TransposerManager.getFillRecipeList()) {
            recipes.add(new TransposerRecipeWrapper(guiHelper, recipe, RecipeUidsTE.TRANSPOSER_FILL));
        }
        return recipes;
    }

    public TransposerRecipeCategoryFill(IGuiHelper guiHelper) {

        super(guiHelper);

        localizedName += " - " + StringHelper.localize("gui.thermalexpansion.jei.transposer.modeFill");
        icon = guiHelper.createDrawableIngredient(new ItemStack(Items.water_bucket));
    }

    @Nonnull
    @Override
    public String getUid() {

        return RecipeUidsTE.TRANSPOSER_FILL;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, TransposerRecipeWrapper recipeWrapper, IIngredients ingredients) {

        List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
        List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);
        List<List<FluidStack>> fluids = ingredients.getInputs(VanillaTypes.FLUID);

        IFocus<?> focus = recipeLayout.getFocus();
        if (focus != null) {
            if (focus.getMode() == OUTPUT && focus.getValue() instanceof ItemStack) {
                List<FluidStack> focusFluids = new ArrayList<>();
                ItemStack output = (ItemStack) focus.getValue();
                FluidStack contained = FluidHelpers.getFluidContained(output);
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
            } else if (focus.getMode() == INPUT && focus.getValue() instanceof FluidStack) {
                List<ItemStack> focusOutputs = new ArrayList<>();
                FluidStack fluid = (FluidStack) focus.getValue();
                for (ItemStack stack : outputs.get(0)) {
                    FluidStack contained = FluidHelpers.getFluidContained(stack);
                    if (contained == null || FluidHelper.isFluidEqual(fluid, contained)) {
                        focusOutputs.add(stack);
                    }
                }
                if (focusOutputs.size() != inputs.get(0)
                    .size()) {
                    outputs = singletonList(focusOutputs);
                }
            }
        }

        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

        Map<Integer, ? extends IGuiIngredient<FluidStack>> fluidIngredients = guiFluidStacks.getGuiIngredients();
        recipeWrapper.setGuiFluids(fluidIngredients);

        guiItemStacks.init(0, true, 30, 10);
        guiItemStacks.init(1, false, 30, 41);
        guiFluidStacks.init(0, true, 103, 1, 16, 60, FluidHelpers.BUCKET_VOLUME, false, tankOverlay);

        guiItemStacks.set(0, inputs.get(0));
        guiItemStacks.set(1, outputs.get(0));
        guiFluidStacks.set(0, fluids.get(0));
    }

}
