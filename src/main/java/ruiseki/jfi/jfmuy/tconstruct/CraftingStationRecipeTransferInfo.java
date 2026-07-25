package ruiseki.jfi.jfmuy.tconstruct;

import net.minecraft.inventory.Slot;
import ruiseki.jfmuy.api.recipe.VanillaRecipeCategoryUid;
import ruiseki.jfmuy.api.recipe.transfer.IRecipeTransferInfo;
import tconstruct.tools.inventory.CraftingStationContainer;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

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
        for(int i = 1; i < 10; i++) {
            slots.add(container.getSlot(i));
        }
        return slots;
    }

    @Nonnull
    @Override
    public List<Slot> getInventorySlots(CraftingStationContainer container) {
        List<Slot> slots = new ArrayList<>();

        // skip the actual slots of the crafting table
        for(int i = 10; i < container.inventorySlots.size(); i++) {
            slots.add(container.getSlot(i));
        }
        return slots;
    }
}
