package ruiseki.jfi.jfmuy.thermalexpansion.dynamo.reactant;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import org.apache.logging.log4j.Level;

import cofh.lib.inventory.ComparableItemStack;
import cofh.lib.util.helpers.StringHelper;
import cofh.thermalexpansion.block.dynamo.BlockDynamo;
import cofh.thermalexpansion.block.dynamo.TileDynamoReactant;
import cofh.thermalexpansion.gui.client.dynamo.GuiDynamoReactant;
import gnu.trove.map.hash.TObjectIntHashMap;
import ruiseki.jfi.JFI;
import ruiseki.jfi.jfmuy.thermalexpansion.Drawables;
import ruiseki.jfi.jfmuy.thermalexpansion.RecipeUidsTE;
import ruiseki.jfi.jfmuy.thermalexpansion.dynamo.BaseFuelCategory;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IDrawableStatic;
import ruiseki.jfmuy.api.gui.IGuiFluidStackGroup;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class ReactantFuelCategory extends BaseFuelCategory<ReactantFuelWrapper> {

    public static boolean enable = true;

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        registry.addRecipeCategories(new ReactantFuelCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
            IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

            registry.addRecipes(getRecipes(registry, guiHelper), RecipeUidsTE.DYNAMO_REACTANT);
            registry.addRecipeClickArea(GuiDynamoReactant.class, 115, 35, 16, 16, RecipeUidsTE.DYNAMO_REACTANT);
            registry.addRecipeCatalyst(BlockDynamo.dynamoReactant, RecipeUidsTE.DYNAMO_REACTANT);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null fuel!", t);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<ReactantFuelWrapper> getRecipes(IModRegistry registry, IGuiHelper guiHelper) {
        List<ReactantFuelWrapper> recipes = new ArrayList<>();
        Set<ComparableItemStack> specificReactants = null;
        Set<Fluid> specificFuels = null;

        try {
            Field reactantField = TileDynamoReactant.class.getDeclaredField("reactants");
            reactantField.setAccessible(true);
            TObjectIntHashMap<ComparableItemStack> reactantMap = (TObjectIntHashMap<ComparableItemStack>) reactantField
                .get(null);
            if (reactantMap != null) {
                specificReactants = reactantMap.keySet();
            }

            Field fuelField = TileDynamoReactant.class.getDeclaredField("fuels");
            fuelField.setAccessible(true);
            TObjectIntHashMap<Fluid> fuelMap = (TObjectIntHashMap<Fluid>) fuelField.get(null);
            if (fuelMap != null) {
                specificFuels = fuelMap.keySet();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<FluidStack> validFluids = new ArrayList<>();
        if (specificFuels != null && !specificFuels.isEmpty()) {
            for (Fluid fluid : specificFuels) {
                validFluids.add(new FluidStack(fluid, 1000));
            }
        } else {
            validFluids.add(new FluidStack(FluidRegistry.LAVA, 1000));
        }

        // Tạo tổ hợp recipe cho các item phản ứng
        if (specificReactants != null) {
            for (ComparableItemStack reactant : specificReactants) {
                ItemStack reactantStack = reactant.toItemStack();
                int energy = TileDynamoReactant.getReactantEnergy(reactantStack);
                if (energy > 0) {
                    for (FluidStack fluidStack : validFluids) {
                        recipes.add(new ReactantFuelWrapper(guiHelper, reactantStack, fluidStack, energy));
                    }
                }
            }
        }

        return recipes;
    }

    IDrawableStatic tank;
    IDrawableStatic tankOverlayInput;

    public ReactantFuelCategory(IGuiHelper guiHelper) {
        background = guiHelper
            .drawableBuilder(
                new ResourceLocation("thermalexpansion:textures/gui/dynamo/DynamoReactant.png"),
                26,
                11,
                70,
                62)
            .addPadding(0, 0, 16, 78)
            .build();
        energyMeter = Drawables.getDrawables(guiHelper)
            .getEnergyEmpty();
        durationEmpty = Drawables.getDrawables(guiHelper)
            .getScale(Drawables.SCALE_FLAME_GREEN);
        localizedName = StringHelper.localize("tile.thermalexpansion.dynamo.reactant.name");

        tank = Drawables.getDrawables(guiHelper)
            .getTank(Drawables.TANK_SHORT);
        tankOverlayInput = Drawables.getDrawables(guiHelper)
            .getTankSmallOverlay(Drawables.TANK_SHORT);
    }

    @Nonnull
    @Override
    public String getUid() {
        return RecipeUidsTE.DYNAMO_REACTANT;
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {
        energyMeter.draw(minecraft, 71, 7);
        durationEmpty.draw(minecraft, 22, 43);
        tank.draw(minecraft, 9, 9);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, ReactantFuelWrapper recipeWrapper, IIngredients ingredients) {
        List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
        List<List<FluidStack>> inputFluids = ingredients.getInputs(VanillaTypes.FLUID);

        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

        guiItemStacks.init(0, true, 33, 23);
        guiFluidStacks.init(0, true, 10, 10, 16, 30, 1000, false, tankOverlayInput);

        if (!inputs.isEmpty() && !inputs.get(0)
            .isEmpty()) {
            guiItemStacks.set(0, inputs.get(0));
        }
        if (!inputFluids.isEmpty() && !inputFluids.get(0)
            .isEmpty()) {
            guiFluidStacks.set(0, inputFluids.get(0));
        }
    }
}
