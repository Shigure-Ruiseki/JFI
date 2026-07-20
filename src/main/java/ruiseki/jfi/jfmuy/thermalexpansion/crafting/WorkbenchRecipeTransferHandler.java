package ruiseki.jfi.jfmuy.thermalexpansion.crafting;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import cofh.core.network.PacketHandler;
import cofh.core.network.PacketTileInfo;
import cofh.thermalexpansion.block.workbench.TileWorkbench.PacketInfoID;
import cofh.thermalexpansion.gui.container.ContainerWorkbench;
import ruiseki.jfmuy.api.gui.IGuiIngredient;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.recipe.transfer.IRecipeTransferError;
import ruiseki.jfmuy.api.recipe.transfer.IRecipeTransferHandler;
import ruiseki.jfmuy.util.Log;

public class WorkbenchRecipeTransferHandler implements IRecipeTransferHandler<ContainerWorkbench> {

    private static Field craftResultField;

    static {
        try {
            craftResultField = ContainerWorkbench.class.getDeclaredField("craftResult");
            craftResultField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            Log.get()
                .error("Failed to find craftResult field in ContainerWorkbench", e);
        }
    }

    @Override
    public Class<ContainerWorkbench> getContainerClass() {
        return ContainerWorkbench.class;
    }

    @Override
    public IRecipeTransferError transferRecipe(ContainerWorkbench container, IRecipeLayout recipeLayout,
        EntityPlayer player, boolean maxTransfer, boolean doTransfer) {
        if (doTransfer) {
            Map<Integer, ? extends IGuiIngredient<ItemStack>> guiIngredients = recipeLayout.getItemStacks()
                .getGuiIngredients();

            int matrixSize = container.craftMatrix.getSizeInventory();
            for (int i = 0; i < matrixSize; i++) {
                container.craftMatrix.setInventorySlotContents(i, null);
            }

            if (craftResultField != null) {
                try {
                    IInventory craftResult = (IInventory) craftResultField.get(container);
                    if (craftResult != null) {
                        for (int i = 0; i < craftResult.getSizeInventory(); i++) {
                            craftResult.setInventorySlotContents(i, null);
                        }
                    }
                } catch (IllegalAccessException e) {
                    Log.get()
                        .error("Failed to access craftResult in ContainerWorkbench", e);
                }
            }

            PacketTileInfo packet = PacketTileInfo.newPacket(container.baseTile);
            packet.addByte(PacketInfoID.NEI_SUP.ordinal());
            boolean hasChanges = false;

            for (Map.Entry<Integer, ? extends IGuiIngredient<ItemStack>> entry : guiIngredients.entrySet()) {
                int recipeSlot = entry.getKey();
                List<ItemStack> allIngredients = entry.getValue()
                    .getAllIngredients();

                if (recipeSlot != 0 && !allIngredients.isEmpty()) {
                    int matrixIndex = recipeSlot - 1;
                    ItemStack ingredient = allIngredients.getFirst();

                    container.craftMatrix.setInventorySlotContents(matrixIndex, ingredient);

                    for (Slot slot : container.inventorySlots) {
                        if (slot.inventory == container.craftMatrix && slot.getSlotIndex() == matrixIndex) {
                            hasChanges = true;
                            packet.addByte(slot.getSlotIndex());
                            packet.addItemStack(ingredient);
                            break;
                        }
                    }
                }
            }

            packet.addByte(-1);

            container.onCraftMatrixChanged(container.craftMatrix);

            if (player.worldObj.isRemote && hasChanges) {
                PacketHandler.sendToServer(packet);
            }
        }
        return null;
    }
}
