package ruiseki.jfi.jfmuy.excompressum.barrel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.blay09.mods.excompressum.ExCompressum;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import exnihilo.ENBlocks;
import exnihilo.data.ModData;
import exnihilo.registries.BarrelRecipeRegistry;
import exnihilo.registries.helpers.FluidItemCombo;
import exnihilo.utils.ItemInfo;
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

public class BarrelProcessRecipeCategory implements IRecipeCategory<BarrelProcessRecipeWrapper> {

    public static final String UID = "excompressum.barrelProcess";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new BarrelProcessRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            registry.addRecipes(getRecipes(), UID);
            for (int i = 0; i < 6; ++i) {
                registry.addRecipeCatalyst(new ItemStack(ENBlocks.Barrel, 1, i), UID);
            }
            registry.addRecipeCatalyst(new ItemStack(ENBlocks.BarrelStone), UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    public static List<BarrelProcessRecipeWrapper> getRecipes() {
        List<BarrelProcessRecipeWrapper> recipes = new ArrayList<>();

        // 1. Slime Recipe
        if (ModData.ALLOW_BARREL_RECIPE_SLIME) {
            recipes.add(
                new BarrelProcessRecipeWrapper(
                    new ItemStack(Items.water_bucket),
                    new ItemStack(Items.milk_bucket),
                    new ItemStack(Items.slime_ball, 4)));
        }

        // 2. Witch Water / Soulsand Recipes
        Item witchWaterBucket = GameRegistry.findItem("exnihilo", "bucket_witchwater");
        if (ModData.ALLOW_BARREL_RECIPE_SOULSAND && witchWaterBucket != null) {
            recipes.add(
                new BarrelProcessRecipeWrapper(
                    new ItemStack(Items.water_bucket),
                    new ItemStack(Items.mushroom_stew),
                    new ItemStack(witchWaterBucket, 1)));

            ItemStack spores = GameRegistry.findItemStack("exnihilo", "spores", 1);
            if (spores != null) {
                recipes.add(
                    new BarrelProcessRecipeWrapper(
                        new ItemStack(Items.water_bucket),
                        spores,
                        new ItemStack(witchWaterBucket, 1)));
            }
        }

        // 3. Dynamic Barrel Recipes from BarrelRecipeRegistry via Reflection
        try {
            Map<FluidItemCombo, ItemInfo> registryRecipes = ReflectionHelper
                .getPrivateValue(BarrelRecipeRegistry.class, null, "recipes");

            if (registryRecipes != null) {
                for (Map.Entry<FluidItemCombo, ItemInfo> entry : registryRecipes.entrySet()) {
                    Fluid inputFluid = entry.getKey()
                        .getInputFluid();
                    ItemStack fluidItem;

                    if (inputFluid == FluidRegistry.WATER) {
                        fluidItem = new ItemStack(Items.water_bucket);
                    } else if (inputFluid == FluidRegistry.LAVA) {
                        fluidItem = new ItemStack(Items.lava_bucket);
                    } else if (inputFluid == FluidRegistry.getFluid("witchwater") && witchWaterBucket != null) {
                        fluidItem = new ItemStack(witchWaterBucket);
                    } else if (inputFluid != null && inputFluid.getBlock() != null) {
                        fluidItem = new ItemStack(inputFluid.getBlock());
                    } else {
                        continue;
                    }

                    ItemStack inputItem = entry.getKey()
                        .getInputItem()
                        .getStack();
                    ItemStack outputStack = entry.getValue()
                        .getStack();

                    if (inputItem != null && outputStack != null) {
                        recipes.add(new BarrelProcessRecipeWrapper(fluidItem, inputItem, outputStack));
                    }
                }
            }
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Failed to load Barrel Process recipes from Ex Nihilo registry!", t);
        }

        return recipes;
    }

    private final IDrawable background;

    public BarrelProcessRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper
            .createDrawable(new ResourceLocation("excompressum:textures/gui/neiBarrelProcess.png"), 0, 0, 166, 58);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return "Barrel Recipe";
    }

    @Override
    public String getModName() {
        return ExCompressum.MOD_ID;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, BarrelProcessRecipeWrapper recipeWrapper,
        IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, true, 18, 8);

        itemStacks.init(1, true, 70, 20);

        itemStacks.init(2, false, 130, 20);

        itemStacks.set(ingredients);

    }
}
