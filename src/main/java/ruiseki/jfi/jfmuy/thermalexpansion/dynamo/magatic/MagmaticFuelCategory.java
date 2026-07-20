package ruiseki.jfi.jfmuy.thermalexpansion.dynamo.magatic;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import org.apache.logging.log4j.Level;

import cofh.lib.util.helpers.StringHelper;
import cofh.thermalexpansion.block.dynamo.BlockDynamo;
import cofh.thermalexpansion.block.dynamo.TileDynamoMagmatic;
import cofh.thermalexpansion.gui.client.dynamo.GuiDynamoMagmatic;
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
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;
import ruiseki.okcore.fluid.FluidHelpers;

public class MagmaticFuelCategory extends BaseFuelCategory<MagmaticFuelWrapper> {

    public static boolean enable = true;

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        registry.addRecipeCategories(new MagmaticFuelCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
            IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

            registry.addRecipes(getRecipes(registry, guiHelper), RecipeUidsTE.DYNAMO_MAGMATIC);
            registry.addRecipeClickArea(GuiDynamoMagmatic.class, 115, 35, 16, 16, RecipeUidsTE.DYNAMO_MAGMATIC);
            registry.addRecipeCatalyst(BlockDynamo.dynamoMagmatic, RecipeUidsTE.DYNAMO_MAGMATIC);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null fuel!", t);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<MagmaticFuelWrapper> getRecipes(IModRegistry registry, IGuiHelper guiHelper) {
        List<MagmaticFuelWrapper> recipes = new ArrayList<>();
        Set<Fluid> specificFuels = null;

        try {
            Field field = TileDynamoMagmatic.class.getDeclaredField("fuels");
            field.setAccessible(true);
            TObjectIntHashMap<Fluid> fuelMap = (TObjectIntHashMap<Fluid>) field.get(null);
            if (fuelMap != null) {
                specificFuels = fuelMap.keySet();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (specificFuels != null) {
            for (Fluid fluid : specificFuels) {
                if (fluid != null) {
                    FluidStack fuelStack = new FluidStack(fluid, FluidHelpers.BUCKET_VOLUME);
                    int energy = TileDynamoMagmatic.getFuelEnergy(fuelStack);
                    if (energy > 0) {
                        recipes.add(new MagmaticFuelWrapper(guiHelper, fuelStack, energy));
                    }
                }
            }
        }

        return recipes;
    }

    IDrawableStatic tank;

    public MagmaticFuelCategory(IGuiHelper guiHelper) {
        background = guiHelper
            .drawableBuilder(
                new ResourceLocation("thermalexpansion:textures/gui/dynamo/DynamoMagmatic.png"),
                26,
                11,
                70,
                62)
            .addPadding(0, 0, 16, 78)
            .build();
        energyMeter = Drawables.getDrawables(guiHelper)
            .getEnergyEmpty();
        durationEmpty = Drawables.getDrawables(guiHelper)
            .getScale(Drawables.SCALE_FLAME);
        localizedName = StringHelper.localize("tile.thermalexpansion.dynamo.magmatic.name");

        tank = Drawables.getDrawables(guiHelper)
            .getTank(Drawables.TANK_SHORT);
    }

    @Nonnull
    @Override
    public String getUid() {
        return RecipeUidsTE.DYNAMO_MAGMATIC;
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {
        super.drawExtras(minecraft);
        tank.draw(minecraft, 33, 7);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, MagmaticFuelWrapper recipeWrapper, IIngredients ingredients) {
        List<List<FluidStack>> inputs = ingredients.getInputs(VanillaTypes.FLUID);

        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

        guiFluidStacks.init(0, true, 34, 8, 16, 30, FluidHelpers.BUCKET_VOLUME, false, null);

        if (!inputs.isEmpty() && !inputs.get(0)
            .isEmpty()) {
            guiFluidStacks.set(0, inputs.get(0));
        }
    }
}
