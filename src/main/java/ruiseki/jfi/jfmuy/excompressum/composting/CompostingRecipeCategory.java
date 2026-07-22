package ruiseki.jfi.jfmuy.excompressum.composting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.blay09.mods.excompressum.ExCompressum;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.Level;

import exnihilo.ENBlocks;
import exnihilo.registries.CompostRegistry;
import exnihilo.registries.helpers.Compostable;
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

public class CompostingRecipeCategory implements IRecipeCategory<CompostingRecipeWrapper> {

    public static final String UID = "excompressum.composting";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new CompostingRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            registry.addRecipes(getRecipes(), UID);
            for (int i = 0; i < 6; ++i) {
                registry.addRecipeCatalyst(new ItemStack(ENBlocks.Barrel, 1, i), UID);
            }
            registry.addRecipeCatalyst(new ItemStack(ENBlocks.BarrelStone), UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Failed to initialize Composting recipes!", t);
        }
    }

    public static List<CompostingRecipeWrapper> getRecipes() {
        List<CompostingRecipeWrapper> recipes = new ArrayList<>();
        List<ItemStack> currentInputs = new ArrayList<>();
        List<Float> currentValues = new ArrayList<>();

        try {
            for (Map.Entry<ItemInfo, Compostable> entry : CompostRegistry.entries.entrySet()) {
                ItemInfo itemInfo = entry.getKey();
                Compostable compostable = entry.getValue();

                if (itemInfo != null && compostable != null) {
                    ItemStack inputStack = itemInfo.getStack();
                    if (inputStack != null) {
                        int neededCount = (int) Math.ceil(1.0D / compostable.value);
                        inputStack.stackSize = Math.max(1, neededCount);

                        currentInputs.add(inputStack);
                        currentValues.add(compostable.value);

                        if (currentInputs.size() >= 45) {
                            recipes.add(
                                new CompostingRecipeWrapper(
                                    new ArrayList<>(currentInputs),
                                    new ArrayList<>(currentValues),
                                    new ItemStack(Blocks.dirt)));
                            currentInputs.clear();
                            currentValues.clear();
                        }
                    }
                }
            }

            if (!currentInputs.isEmpty()) {
                recipes.add(new CompostingRecipeWrapper(currentInputs, currentValues, new ItemStack(Blocks.dirt)));
            }
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Error reading CompostRegistry for JFMUY!", t);
        }

        return recipes;
    }

    private final IDrawable background;

    public CompostingRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper
            .createDrawable(new ResourceLocation("excompressum:textures/gui/neiComposting.png"), 0, 0, 166, 130);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return "Composting (Barrel)";
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
    public void setRecipe(IRecipeLayout recipeLayout, CompostingRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, false, 73, 3);

        int slotIndex = 1;
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 9; col++) {
                int x = 2 + col * 18;
                int y = 36 + row * 18;
                itemStacks.init(slotIndex, true, x, y);
                slotIndex++;
            }
        }

        itemStacks.set(ingredients);

        itemStacks.addTooltipCallback((slotIdx, isInput, stack, tooltip) -> {
            if (isInput && slotIdx >= 1) {
                int inputIdx = slotIdx - 1;
                Float val = recipeWrapper.getValueForIndex(inputIdx);
                if (val != null) {
                    tooltip.add(
                        "§7Fill Amount: " + String.format("%.1f%%", val * 100.0F)
                            + " ("
                            + String.format("%.3f", val)
                            + ")");
                }
            }
        });
    }
}
