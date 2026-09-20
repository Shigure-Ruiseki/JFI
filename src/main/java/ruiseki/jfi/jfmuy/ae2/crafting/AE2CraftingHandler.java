package ruiseki.jfi.jfmuy.ae2.crafting;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import appeng.container.AEBaseContainer;
import appeng.container.slot.SlotCraftingMatrix;
import appeng.container.slot.SlotFakeCraftingMatrix;
import appeng.core.AELog;
import appeng.core.sync.network.NetworkHandler;
import appeng.core.sync.packets.PacketNEIRecipe;
import appeng.util.Platform;
import ruiseki.jfmuy.api.gui.IGuiIngredient;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.recipe.transfer.IRecipeTransferError;
import ruiseki.jfmuy.api.recipe.transfer.IRecipeTransferHandler;

public class AE2CraftingHandler<C extends AEBaseContainer> implements IRecipeTransferHandler<C> {

    private final Class<C> containerClass;

    public AE2CraftingHandler(Class<C> containerClass) {
        this.containerClass = containerClass;
    }

    @Override
    public Class<C> getContainerClass() {
        return this.containerClass;
    }

    @Override
    @Nullable
    public IRecipeTransferError transferRecipe(C container, IRecipeLayout recipeLayout, EntityPlayer player,
        boolean maxTransfer, boolean doTransfer) {
        if (!doTransfer) {
            return null;
        }

        try {
            NBTTagCompound recipeNBT = packIngredients(container, recipeLayout, false);
            PacketNEIRecipe packet = new PacketNEIRecipe(recipeNBT);

            if (packet.size() >= 32 * 1024) {
                AELog.warn("Recipe has too many variants, reduced version will be used");
                recipeNBT = packIngredients(container, recipeLayout, true);
                packet = new PacketNEIRecipe(recipeNBT);
            }

            NetworkHandler.instance.sendToServer(packet);
        } catch (Exception e) {
            AELog.error("Failed to transfer recipe from JFMUY to AE2 Terminal", e);
        }

        return null;
    }

    private static boolean testSize(NBTTagCompound recipe) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream outputStream = new DataOutputStream(bytes);
        CompressedStreamTools.writeCompressed(recipe, outputStream);
        return bytes.size() > 3 * 1024;
    }

    public static NBTTagCompound packIngredients(Container container, IRecipeLayout recipeLayout, boolean limited)
        throws IOException {

        NBTTagCompound recipeNBT = new NBTTagCompound();
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        Map<Integer, ? extends IGuiIngredient<ItemStack>> ingredients = guiItemStacks.getGuiIngredients();

        for (Map.Entry<Integer, ? extends IGuiIngredient<ItemStack>> entry : ingredients.entrySet()) {

            if (!entry.getValue()
                .isInput()) {
                continue;
            }

            int recipeSlotIndex = entry.getKey() - 1;
            if (recipeSlotIndex < 0 || recipeSlotIndex >= 9) {
                continue;
            }

            List<ItemStack> allIngredients = entry.getValue()
                .getAllIngredients();
            if (allIngredients.isEmpty()) {
                continue;
            }

            for (Slot slot : container.inventorySlots) {
                if (slot instanceof SlotCraftingMatrix || slot instanceof SlotFakeCraftingMatrix) {
                    if (slot.getSlotIndex() == recipeSlotIndex) {

                        NBTTagList tags = new NBTTagList();
                        List<ItemStack> prioritizedList = new LinkedList<>();

                        for (ItemStack is : allIngredients) {
                            if (Platform.isRecipePrioritized(is)) {
                                prioritizedList.add(0, is);
                            } else {
                                prioritizedList.add(is);
                            }
                        }

                        for (ItemStack is : prioritizedList) {
                            NBTTagCompound tag = new NBTTagCompound();
                            is.writeToNBT(tag);
                            tag.setShort("Count", (short) is.stackSize);
                            tags.appendTag(tag);

                            if (limited) {
                                NBTTagCompound test = new NBTTagCompound();
                                test.setTag("#" + slot.getSlotIndex(), tags);
                                if (testSize(test)) {
                                    break;
                                }
                            }
                        }

                        recipeNBT.setTag("#" + slot.getSlotIndex(), tags);
                        break;
                    }
                }
            }
        }
        return recipeNBT;
    }
}
