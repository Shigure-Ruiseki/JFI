package ruiseki.jfi.jfmuy.ae2.crafting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;

import appeng.api.exceptions.MissingIngredientError;
import appeng.api.exceptions.RegistrationError;
import appeng.api.recipes.IIngredient;
import appeng.core.AEConfig;
import appeng.recipes.game.ShapedRecipe;
import appeng.util.Platform;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.wrapper.IShapedCraftingRecipeWrapper;

public class AEShapedRecipeWrapper implements IShapedCraftingRecipeWrapper {

    private final ShapedRecipe recipe;
    private final List<List<ItemStack>> inputs;
    private final ItemStack output;
    private final int width;
    private final int height;

    public AEShapedRecipeWrapper(ShapedRecipe recipe) {
        this.recipe = recipe;
        this.output = recipe.getRecipeOutput();
        this.width = recipe.getWidth();
        this.height = recipe.getHeight();
        this.inputs = new ArrayList<>(9);

        Object[] items = recipe.getIngredients();
        final boolean useSingleItems = AEConfig.instance.disableColoredCableRecipesInNEI();

        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                if (x < width && y < height) {
                    int index = y * width + x;
                    if (index < items.length && items[index] != null) {
                        IIngredient ing = (IIngredient) items[index];
                        try {
                            ItemStack[] is = ing.getItemStackSet();
                            Object preferredObj = useSingleItems ? Platform.findPreferred(is) : is;
                            List<ItemStack> expanded = new ArrayList<>();

                            if (preferredObj instanceof ItemStack preferred) {
                                expanded.add(preferred);
                            } else if (preferredObj instanceof ItemStack[]preferredArray) {
                                Collections.addAll(expanded, preferredArray);
                            } else if (is != null) {
                                Collections.addAll(expanded, is);
                            }

                            inputs.add(expanded);
                        } catch (RegistrationError | MissingIngredientError ignored) {
                            inputs.add(Collections.emptyList());
                        }
                    } else {
                        inputs.add(Collections.emptyList());
                    }
                } else {
                    inputs.add(Collections.emptyList());
                }
            }
        }
    }

    public ShapedRecipe getRecipe() {
        return recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, inputs);
        ingredients.setOutput(VanillaTypes.ITEM, output);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
