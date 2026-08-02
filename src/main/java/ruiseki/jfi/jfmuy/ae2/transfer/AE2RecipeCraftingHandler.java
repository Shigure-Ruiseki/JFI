package ruiseki.jfi.jfmuy.ae2.transfer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import appeng.core.sync.network.NetworkHandler;
import appeng.core.sync.packets.PacketNEIRecipe;
import appeng.helpers.IContainerCraftingPacket;
import appeng.util.Platform;
import ruiseki.jfmuy.api.gui.IGuiIngredient;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.recipe.transfer.IRecipeCraftingHandler;
import ruiseki.jfmuy.api.recipe.transfer.IRecipeTransferError;
import ruiseki.jfmuy.api.recipe.transfer.IRecipeTransferHandlerHelper;
import ruiseki.jfmuy.api.recipe.transfer.IRecipeTransferInfo;
import ruiseki.jfmuy.config.ServerInfo;
import ruiseki.jfmuy.startup.StackHelper;
import ruiseki.jfmuy.util.Translator;

public class AE2RecipeCraftingHandler<C extends Container> implements IRecipeCraftingHandler<C> {

    private final StackHelper stackHelper;
    private final IRecipeTransferHandlerHelper handlerHelper;
    private final IRecipeTransferInfo<C> transferHelper;

    public AE2RecipeCraftingHandler(StackHelper stackHelper, IRecipeTransferHandlerHelper handlerHelper,
        IRecipeTransferInfo<C> transferHelper) {
        this.stackHelper = stackHelper;
        this.handlerHelper = handlerHelper;
        this.transferHelper = transferHelper;
    }

    @Override
    public Class<C> getContainerClass() {
        return transferHelper.getContainerClass();
    }

    @Nullable
    @Override
    public IRecipeTransferError transferRecipe(C container, IRecipeLayout recipeLayout, EntityPlayer player,
        boolean maxTransfer, boolean doTransfer) {
        return transferRecipe(container, recipeLayout, player, maxTransfer ? Integer.MAX_VALUE : 1, false, doTransfer);
    }

    protected IRecipeTransferError transferRecipe(C container, IRecipeLayout recipeLayout, EntityPlayer player,
        int maxTransfer, boolean performRecipe, boolean doTransfer) {
        if (!ServerInfo.isJFMUYOnServer()) {
            String tooltipMessage = Translator.translateToLocal("jfmuy.y.tooltip.error.recipe.transfer.no.server");
            return handlerHelper.createUserErrorWithTooltip(tooltipMessage);
        }

        if (container == null || !transferHelper.canHandle(container)) {
            return handlerHelper.createInternalError();
        }

        boolean useRealItems = true;
        if (container instanceof IContainerCraftingPacket packetContainer) {
            useRealItems = packetContainer.useRealItems();
        }

        if (doTransfer) {
            try {
                NBTTagCompound recipeNbt = packIngredients(container, recipeLayout, false, useRealItems);
                PacketNEIRecipe packet = new PacketNEIRecipe(
                    packIngredients(container, recipeLayout, false, useRealItems));
                if (packet.size() >= 32 * 1024) {
                    packet = new PacketNEIRecipe(packIngredients(container, recipeLayout, true, useRealItems));
                }
                NetworkHandler.instance.sendToServer(packet);
            } catch (Exception e) {
                return handlerHelper.createInternalError();
            }
        }

        return null;
    }

    private NBTTagCompound packIngredients(C container, IRecipeLayout recipeLayout, boolean limited,
        boolean useRealItems) throws Exception {
        NBTTagCompound recipe = new NBTTagCompound();
        if (recipeLayout == null) {
            return recipe;
        }

        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        if (guiItemStacks == null) {
            return recipe;
        }

        Map<Integer, ? extends IGuiIngredient<ItemStack>> ingredients = guiItemStacks.getGuiIngredients();
        if (ingredients == null) {
            return recipe;
        }

        for (Map.Entry<Integer, ? extends IGuiIngredient<ItemStack>> entry : ingredients.entrySet()) {
            Integer recipeSlotIdx = entry.getKey();
            IGuiIngredient<ItemStack> ingredient = entry.getValue();

            if (recipeSlotIdx == null || ingredient == null) {
                continue;
            }

            List<ItemStack> allIngredients = ingredient.getAllIngredients();
            if (ingredient.isInput() && allIngredients != null && !allIngredients.isEmpty()) {

                int ae2SlotIndex = recipeSlotIdx > 0 ? recipeSlotIdx - 1 : recipeSlotIdx;

                if (ae2SlotIndex < 0 || ae2SlotIndex >= 9) {
                    continue;
                }

                NBTTagList tags = new NBTTagList();

                if (useRealItems) {
                    List<ItemStack> list = new LinkedList<>();
                    for (ItemStack stack : allIngredients) {
                        if (stack == null) continue;
                        if (Platform.isRecipePrioritized(stack)) {
                            list.add(0, stack);
                        } else {
                            list.add(stack);
                        }
                    }

                    for (ItemStack is : list) {
                        NBTTagCompound tag = new NBTTagCompound();
                        is.writeToNBT(tag);
                        tag.setShort("Count", (short) is.stackSize);
                        tags.appendTag(tag);

                        if (limited) {
                            NBTTagCompound test = new NBTTagCompound();
                            test.setTag("#" + ae2SlotIndex, tags);
                            if (testSize(test)) {
                                break;
                            }
                        }
                    }
                } else {
                    ItemStack displayStack = ingredient.getDisplayedIngredient();
                    if (displayStack == null && !allIngredients.isEmpty()) {
                        displayStack = allIngredients.get(0);
                    }

                    if (displayStack != null) {
                        NBTTagCompound tag = new NBTTagCompound();
                        displayStack.writeToNBT(tag);
                        tag.setShort("Count", (short) displayStack.stackSize);
                        tags.appendTag(tag);
                    }
                }

                String key = "#" + ae2SlotIndex;
                recipe.setTag(key, tags);
            }
        }

        return recipe;
    }

    private static boolean testSize(NBTTagCompound recipe) {
        try (ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            DataOutputStream outputStream = new DataOutputStream(bytes)) {
            CompressedStreamTools.writeCompressed(recipe, outputStream);
            outputStream.flush();
            return bytes.size() > 3 * 1024;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public @Nullable IRecipeTransferError craft(C container, IRecipeLayout recipeLayout, EntityPlayer player,
        int amount, boolean doTransfer) {
        return this.transferRecipe(container, recipeLayout, player, amount, true, doTransfer);
    }
}
