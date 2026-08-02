package ruiseki.jfi.jfmuy.ae2.transfer;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.Slot;

import appeng.container.implementations.ContainerCraftingTerm;
import appeng.container.slot.SlotCraftingMatrix;
import appeng.container.slot.SlotCraftingTerm;
import appeng.container.slot.SlotPlayerHotBar;
import appeng.container.slot.SlotPlayerInv;
import ruiseki.jfmuy.api.recipe.VanillaRecipeCategoryUid;
import ruiseki.jfmuy.api.recipe.transfer.IRecipeTransferInfo;

public class AE2CraftingRecipeTransferInfo implements IRecipeTransferInfo<ContainerCraftingTerm> {

    @Override
    public Class<ContainerCraftingTerm> getContainerClass() {
        return ContainerCraftingTerm.class;
    }

    @Override
    public String getRecipeCategoryUid() {
        return VanillaRecipeCategoryUid.CRAFTING;
    }

    @Override
    public boolean canHandle(ContainerCraftingTerm container) {
        return true;
    }

    @Override
    public List<Slot> getRecipeSlots(ContainerCraftingTerm container) {
        List<Slot> recipeSlots = new ArrayList<>();
        // Sắp xếp các slot crafting matrix theo đúng slotIndex (0-8)
        for (Slot slot : (List<Slot>) container.inventorySlots) {
            if (slot instanceof SlotCraftingMatrix) {
                recipeSlots.add(slot);
            }
        }
        recipeSlots.sort((a, b) -> Integer.compare(a.getSlotIndex(), b.getSlotIndex()));
        return recipeSlots;
    }

    @Override
    public List<Slot> getInventorySlots(ContainerCraftingTerm container) {
        List<Slot> invSlots = new ArrayList<>();
        for (Slot slot : (List<Slot>) container.inventorySlots) {
            if (slot instanceof SlotPlayerInv || slot instanceof SlotPlayerHotBar) {
                invSlots.add(slot);
            }
        }
        return invSlots;
    }

    @Override
    public int getOutputSlot(ContainerCraftingTerm container) {
        for (Slot slot : (List<Slot>) container.inventorySlots) {
            if (slot instanceof SlotCraftingTerm) {
                return slot.slotNumber;
            }
        }
        return -1;
    }
}
