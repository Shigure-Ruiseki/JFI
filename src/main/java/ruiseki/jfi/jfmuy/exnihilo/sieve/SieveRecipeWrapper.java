package ruiseki.jfi.jfmuy.exnihilo.sieve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import exnihilo.registries.SieveRegistry;
import exnihilo.registries.helpers.SiftingResult;
import exnihilo.utils.ItemInfo;
import ruiseki.jfmuy.api.gui.ITooltipCallback;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class SieveRecipeWrapper implements IRecipeWrapper, ITooltipCallback<ItemStack> {

    private final ItemStack input;
    private final List<ItemStack> outputs;

    public SieveRecipeWrapper(ItemInfo inputInfo, List<SiftingResult> allResults, List<ItemStack> pageOutputs) {
        this.input = inputInfo.getStack();
        this.outputs = new ArrayList<>();

        Map<ItemInfo, Integer> rewardCounts = new HashMap<>();
        for (ItemStack stack : pageOutputs) {
            ItemInfo info = new ItemInfo(stack.getItem(), stack.getItemDamage());
            rewardCounts.put(info, rewardCounts.getOrDefault(info, 0) + 1);
        }

        for (Map.Entry<ItemInfo, Integer> entry : rewardCounts.entrySet()) {
            ItemStack stack = entry.getKey()
                .getStack();
            stack.stackSize = entry.getValue();
            this.outputs.add(stack);
        }
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.ITEM, input);
        ingredients.setOutputs(VanillaTypes.ITEM, outputs);
    }

    @Override
    public void onTooltip(int slotIndex, boolean inputSlot, ItemStack ingredient, List<String> tooltip) {
        if (inputSlot || ingredient == null) {
            return;
        }

        Block inBlock = Block.getBlockFromItem(input.getItem());
        int meta = input.getItemDamage();

        boolean addedHeader = false;

        for (SiftingResult result : SieveRegistry.getSiftingOutput(inBlock, meta)) {
            if (ingredient.getItem() == result.item && ingredient.getItemDamage() == result.meta) {
                if (!addedHeader) {
                    tooltip.add(EnumChatFormatting.GRAY + "Drop Chance:");
                    addedHeader = true;
                }

                int chance = (int) Math.round(100.0D * (1.0D / (double) result.rarity));
                tooltip.add("  * " + chance + "%");
            }
        }
    }
}
