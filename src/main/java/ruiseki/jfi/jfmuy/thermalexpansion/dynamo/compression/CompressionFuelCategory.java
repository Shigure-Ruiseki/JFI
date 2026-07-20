package ruiseki.jfi.jfmuy.thermalexpansion.dynamo.compression;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import org.apache.logging.log4j.Level;

import cofh.lib.util.helpers.StringHelper;
import cofh.thermalexpansion.block.dynamo.BlockDynamo;
import cofh.thermalexpansion.block.dynamo.TileDynamoCompression;
import cofh.thermalexpansion.gui.client.dynamo.GuiDynamoCompression;
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

public class CompressionFuelCategory extends BaseFuelCategory<CompressionFuelWrapper> {

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        registry.addRecipeCategories(new CompressionFuelCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
            IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

            registry.addRecipes(getRecipes(registry, guiHelper), RecipeUidsTE.DYNAMO_COMPRESSION);
            registry.addRecipeClickArea(GuiDynamoCompression.class, 115, 35, 16, 16, RecipeUidsTE.DYNAMO_COMPRESSION);
            registry.addRecipeCatalyst(BlockDynamo.dynamoCompression, RecipeUidsTE.DYNAMO_COMPRESSION);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null fuel!", t);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<CompressionFuelWrapper> getRecipes(IModRegistry registry, IGuiHelper guiHelper) {
        List<CompressionFuelWrapper> recipes = new ArrayList<>();

        try {
            Field field = TileDynamoCompression.class.getDeclaredField("fuels");
            field.setAccessible(true);

            TObjectIntHashMap<Fluid> fuelMap = (TObjectIntHashMap<Fluid>) field.get(null);

            if (fuelMap != null) {
                Object[] fuelFluids = fuelMap.keys();

                for (Object obj : fuelFluids) {
                    if (obj instanceof Fluid) {
                        Fluid fluid = (Fluid) obj;
                        FluidStack fuelStack = new FluidStack(fluid, FluidHelpers.BUCKET_VOLUME);
                        int energy = TileDynamoCompression.getFuelEnergy(fuelStack);

                        if (energy > 0) {
                            recipes.add(new CompressionFuelWrapper(guiHelper, fuelStack, energy));
                        }
                    }
                }
            }
        } catch (Exception e) {
            JFI.okLog(Level.ERROR, "Failed to access fuels map via reflection!", e);
        }

        return recipes;
    }

    IDrawableStatic tank;

    public CompressionFuelCategory(IGuiHelper guiHelper) {
        background = guiHelper
            .drawableBuilder(
                new ResourceLocation("thermalexpansion:textures/gui/dynamo/DynamoCompression.png"),
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
        localizedName = StringHelper.localize("tile.thermalexpansion.dynamo.compression.name");

        tank = Drawables.getDrawables(guiHelper)
            .getTank(Drawables.TANK_SHORT);
    }

    @Nonnull
    @Override
    public String getUid() {
        return RecipeUidsTE.DYNAMO_COMPRESSION;
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {
        super.drawExtras(minecraft);
        tank.draw(minecraft, 33, 7);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CompressionFuelWrapper recipeWrapper, IIngredients ingredients) {
        List<List<FluidStack>> inputs = ingredients.getInputs(VanillaTypes.FLUID);
        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

        guiFluidStacks.init(0, true, 34, 8, 16, 30, FluidHelpers.BUCKET_VOLUME, false, null);

        if (!inputs.isEmpty() && !inputs.get(0)
            .isEmpty()) {
            guiFluidStacks.set(0, inputs.get(0));
        }
    }
}
