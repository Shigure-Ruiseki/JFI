package ruiseki.jfi.jfmuy.exnihilo.sieve;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.Level;

import exnihilo.ENBlocks;
import exnihilo.registries.SieveRegistry;
import exnihilo.registries.helpers.SiftingResult;
import exnihilo.utils.ItemInfo;
import ruiseki.jfi.JFI;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class SieveRecipeCategory implements IRecipeCategory<SieveRecipeWrapper> {

    public static final String UID = "exnihilo.sieve";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new SieveRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            registry.addRecipes(getRecipes(), UID);
            for (int i = 0; i < 6; ++i) {
                registry.addRecipeCatalyst(new ItemStack(ENBlocks.Sieve, 1, i), UID);
            }
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    public static List<SieveRecipeWrapper> getRecipes() {
        List<SieveRecipeWrapper> recipes = new ArrayList<>();

        for (ItemInfo inputInfo : SieveRegistry.getSiftables()
            .keySet()) {
            List<SiftingResult> outputs = SieveRegistry.getSiftingOutput(inputInfo);
            if (outputs != null && !outputs.isEmpty()) {
                List<ItemStack> rawOutputs = new ArrayList<>();
                for (SiftingResult result : outputs) {
                    rawOutputs.add(new ItemStack(result.item, 1, result.meta));
                }

                if (rawOutputs.size() > 45) {
                    for (int i = 0; i < rawOutputs.size(); i += 45) {
                        List<ItemStack> subList = rawOutputs.subList(i, Math.min(rawOutputs.size(), i + 45));
                        recipes.add(new SieveRecipeWrapper(inputInfo, outputs, subList));
                    }
                } else {
                    recipes.add(new SieveRecipeWrapper(inputInfo, outputs, rawOutputs));
                }
            }
        }

        return recipes;
    }

    private final IDrawable background;

    public SieveRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper
            .createDrawable(new ResourceLocation("exnihilo:textures/sieveNEI.png"), 0, 0, 166, 130);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return "Ex Nihilo Sieve";
    }

    @Override
    public String getModName() {
        return "Ex Nihilo";
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, SieveRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        // Slot Input
        itemStacks.init(0, true, 73, 3);

        List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);
        for (int i = 0; i < outputs.size() && i < 45; i++) {
            int col = i % 9;
            int row = i / 9;
            int slotIndex = 1 + i;

            itemStacks.init(slotIndex, false, 2 + col * 18, 36 + row * 18);
        }

        itemStacks.addTooltipCallback(recipeWrapper);
        itemStacks.set(ingredients);
    }
}
