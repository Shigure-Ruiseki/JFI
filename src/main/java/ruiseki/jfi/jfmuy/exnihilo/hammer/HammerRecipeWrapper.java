package ruiseki.jfi.jfmuy.exnihilo.hammer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import exnihilo.registries.HammerRegistry;
import exnihilo.registries.helpers.Smashable;
import exnihilo.utils.ItemInfo;
import ruiseki.jfmuy.api.gui.ITooltipCallback;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class HammerRecipeWrapper implements IRecipeWrapper, ITooltipCallback<ItemStack> {

    private final ItemStack input;
    private final List<ItemStack> outputs;
    private final List<Smashable> rewards;

    public HammerRecipeWrapper(ItemInfo inputInfo, List<Smashable> smashables) {
        this.input = inputInfo.getStack();
        this.rewards = smashables;
        this.outputs = new ArrayList<>();

        Map<ItemInfo, Integer> rewardCounts = new HashMap<>();
        for (Smashable s : smashables) {
            ItemInfo info = new ItemInfo(s.item, s.meta);
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

        for (Smashable smash : HammerRegistry.getRewards(inBlock, meta)) {
            ItemStack rewardStack = new ItemStack(smash.item, 1, smash.meta);

            if (ItemStack.areItemStacksEqual(ingredient, rewardStack)
                || (ingredient.getItem() == smash.item && ingredient.getItemDamage() == smash.meta)) {

                if (!addedHeader) {
                    tooltip.add(EnumChatFormatting.GRAY + "Drop Chance:");
                    addedHeader = true;
                }

                int chance = (int) (100.0F * smash.chance);
                int fortune = (int) (100.0F * smash.luckMultiplier);

                if (fortune > 0) {
                    tooltip.add(
                        "  * " + chance
                            + "%"
                            + EnumChatFormatting.BLUE
                            + " (+"
                            + fortune
                            + "% luck bonus)"
                            + EnumChatFormatting.RESET);
                } else {
                    tooltip.add("  * " + chance + "%");
                }
            }
        }
    }
}
