package ruiseki.jfi.jfmuy.tconstruct;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.inventory.Slot;

import ruiseki.jfmuy.api.recipe.VanillaRecipeCategoryUid;
import ruiseki.jfmuy.api.recipe.transfer.IRecipeTransferInfo;
import tconstruct.tools.inventory.CraftingStationContainer;

public class CraftingStationRecipeTransferInfo implements IRecipeTransferInfo<CraftingStationContainer> {

    @Nonnull
    @Override
    public Class<CraftingStationContainer> getContainerClass() {
        return CraftingStationContainer.class;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid() {
        return VanillaRecipeCategoryUid.CRAFTING;
    }

    @Override
    public boolean canHandle(CraftingStationContainer container) {
        return true;
    }

    @Nonnull
    @Override
    public List<Slot> getRecipeSlots(CraftingStationContainer container) {
        List<Slot> slots = new ArrayList<>();
        // Slots 1 to 9 correspond to the 3x3 crafting grid
        for (int i = 1; i <= 9; i++) {
            slots.add(container.getSlot(i));
        }
        return slots;
    }

    @Nonnull
    @Override
    public List<Slot> getInventorySlots(CraftingStationContainer container) {
        List<Slot> slots = new ArrayList<>();

        // Player Inventory (10 to 45) + Attached Chest Slots (46+)
        for (int i = 10; i < container.inventorySlots.size(); i++) {
            Slot slot = container.getSlot(i);
            if (slot != null) {
                slots.add(slot);
            }
        }
        return slots;
    }

    @Override
    public int getOutputSlot() {
        return 0;
    }
}
