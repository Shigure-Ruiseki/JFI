package ruiseki.jfi.jfmuy.ae2.crafting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import appeng.container.implementations.ContainerPatternTerm;
import appeng.core.AELog;
import appeng.util.Platform;
import ruiseki.jfi.JFI;
import ruiseki.jfi.network.packet.ae2.PacketCraftingPatternSlots;
import ruiseki.jfmuy.api.gui.IGuiIngredient;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.recipe.transfer.IRecipeTransferError;
import ruiseki.jfmuy.api.recipe.transfer.IRecipeTransferHandler;

public class AE2CraftingPatternHandler implements IRecipeTransferHandler<ContainerPatternTerm> {

    public AE2CraftingPatternHandler() {}

    @Override
    public Class<ContainerPatternTerm> getContainerClass() {
        return ContainerPatternTerm.class;
    }

    @Override
    @Nullable
    public IRecipeTransferError transferRecipe(ContainerPatternTerm container, IRecipeLayout recipeLayout,
        EntityPlayer player, boolean maxTransfer, boolean doTransfer) {
        if (!doTransfer) {
            return null;
        }

        try {
            boolean isCraftingRecipe = isCraftingLayout(recipeLayout);

            Map<Integer, ItemStack> inputItems = new HashMap<>();
            Map<Integer, ItemStack> outputItems = new HashMap<>();

            IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
            if (guiItemStacks != null && guiItemStacks.getGuiIngredients() != null) {
                for (Map.Entry<Integer, ? extends IGuiIngredient<ItemStack>> entry : guiItemStacks.getGuiIngredients()
                    .entrySet()) {
                    IGuiIngredient<ItemStack> ingredient = entry.getValue();
                    if (ingredient == null) continue;

                    List<ItemStack> allIngredients = ingredient.getAllIngredients();
                    if (allIngredients == null || allIngredients.isEmpty()) continue;

                    ItemStack selectedStack = getPrioritizedStack(allIngredients);
                    if (selectedStack != null) {
                        ItemStack finalStack = selectedStack.copy();
                        int slotIndex = entry.getKey();

                        if (ingredient.isInput()) {
                            int inIndex = slotIndex + (isCraftingRecipe ? -1 : 0);
                            if (isCraftingRecipe) {
                                if (inIndex < 0 || inIndex >= 9) continue;
                                finalStack.stackSize = 1;
                            } else {
                                if (inIndex < 0) continue;
                            }
                            inputItems.put(inIndex, finalStack);
                        } else {
                            outputItems.put(slotIndex, finalStack);
                        }
                    }
                }
            }

            JFI._instance.getPacketHandler()
                .sendToServer(new PacketCraftingPatternSlots(inputItems, false, isCraftingRecipe));

            if (!isCraftingRecipe && (!outputItems.isEmpty())) {
                JFI._instance.getPacketHandler()
                    .sendToServer(new PacketCraftingPatternSlots(outputItems, true, false));
            }

        } catch (Exception e) {
            AELog.error("Failed to transfer Recipe with Fluids to AE2 Pattern Terminal", e);
        }

        return null;
    }

    private boolean isCraftingLayout(IRecipeLayout recipeLayout) {
        return recipeLayout.getItemStacks() != null && recipeLayout.getItemStacks()
            .getGuiIngredients()
            .size() <= 10;
    }

    private ItemStack getPrioritizedStack(List<ItemStack> ingredients) {
        for (ItemStack is : ingredients) {
            if (is != null && Platform.isRecipePrioritized(is)) {
                return is;
            }
        }
        return ingredients.get(0);
    }
}
