package ruiseki.jfi.jfmuy.enderio.enchanter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import crazypants.enderio.machine.enchanter.EnchanterRecipe;
import crazypants.enderio.machine.enchanter.TileEnchanter;
import crazypants.enderio.machine.recipe.RecipeInput;
import ruiseki.jfmuy.api.gui.IGuiIngredient;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;
import ruiseki.okcore.helper.LangHelpers;

public class EnchanterRecipeWrapper implements IRecipeWrapper {

    private final EnchanterRecipe recipe;
    private IRecipeLayout recipeLayout;

    public EnchanterRecipeWrapper(EnchanterRecipe recipe) {
        this.recipe = recipe;
    }

    public void setRecipeLayout(IRecipeLayout recipeLayout) {
        this.recipeLayout = recipeLayout;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        int maxLevel = recipe.getEnchantment()
            .getMaxLevel();

        List<ItemStack> slot0 = new ArrayList<>();
        slot0.add(new ItemStack(Items.writable_book));

        List<ItemStack> slot1 = new ArrayList<>();
        List<ItemStack> baseInputs = getInputs(recipe.getInput());
        List<ItemStack> outputs = new ArrayList<>();

        long time = Minecraft.getMinecraft().theWorld != null ? Minecraft.getMinecraft().theWorld.getTotalWorldTime()
            : 0;
        int oreCycle = (int) ((time / 20) % Math.max(1, baseInputs.size()));
        ItemStack activeBaseInput = !baseInputs.isEmpty() ? baseInputs.get(oreCycle) : null;

        for (int level = 1; level <= maxLevel; level++) {
            if (activeBaseInput != null) {
                ItemStack inputCopy = activeBaseInput.copy();
                inputCopy.stackSize = recipe.getItemsPerLevel() * level;
                slot1.add(inputCopy);
            }

            ItemStack outputBook = new ItemStack(Items.enchanted_book);
            EnchantmentData enchData = new EnchantmentData(recipe.getEnchantment(), level);
            Items.enchanted_book.addEnchantment(outputBook, enchData);
            outputs.add(outputBook);
        }

        List<List<ItemStack>> allInputs = new ArrayList<>();
        allInputs.add(slot0);
        allInputs.add(slot1);

        ingredients.setInputLists(VanillaTypes.ITEM, allInputs);
        ingredients.setOutputLists(VanillaTypes.ITEM, Arrays.asList(outputs));
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        int currentLevel = 1;
        int maxLevel = recipe.getEnchantment()
            .getMaxLevel();

        if (recipeLayout != null) {
            IGuiIngredient<ItemStack> outputSlot = recipeLayout.getItemStacks()
                .getGuiIngredients()
                .get(2);
            if (outputSlot != null) {
                ItemStack currentDisplayedItem = outputSlot.getDisplayedIngredient();
                if (currentDisplayedItem != null && currentDisplayedItem.getItem() == Items.enchanted_book) {
                    java.util.Map<Integer, Integer> enchants = net.minecraft.enchantment.EnchantmentHelper
                        .getEnchantments(currentDisplayedItem);
                    for (int id : enchants.keySet()) {
                        if (id == recipe.getEnchantment().effectId) {
                            currentLevel = enchants.get(id);
                            break;
                        }
                    }
                }
            }
        }

        String enchantName = maxLevel > 1 ? recipe.getEnchantment()
            .getTranslatedName(currentLevel)
            : StatCollector.translateToLocal(
                recipe.getEnchantment()
                    .getName());

        int nameWidth = minecraft.fontRenderer.getStringWidth(enchantName);
        minecraft.fontRenderer.drawString(enchantName, (recipeWidth - nameWidth) / 2, 5, 0x808080, false);

        int cost = TileEnchanter.getEnchantmentCost(recipe, currentLevel);
        if (cost > 0) {
            String costString = LangHelpers.localize("container.repair.cost", cost);
            int costWidth = minecraft.fontRenderer.getStringWidth(costString);
            minecraft.fontRenderer.drawString(costString, (recipeWidth - costWidth) / 2, 41, 0x80FF20, true);
        }
    }

    private List<ItemStack> getInputs(RecipeInput input) {
        List<ItemStack> result = new ArrayList<>();
        if (input.getInput() != null) {
            result.add(input.getInput());
        }
        ItemStack[] equivs = input.getEquivelentInputs();
        if (equivs != null && equivs.length > 0) {
            result.addAll(Arrays.asList(equivs));
        }
        return result;
    }

    public EnchanterRecipe getRecipe() {
        return this.recipe;
    }
}
