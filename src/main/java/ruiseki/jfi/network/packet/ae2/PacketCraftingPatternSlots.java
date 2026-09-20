package ruiseki.jfi.network.packet.ae2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import appeng.api.storage.StorageName;
import appeng.api.storage.data.IAEItemStack;
import appeng.container.implementations.ContainerPatternTerm;
import appeng.util.item.AEItemStack;
import ruiseki.okcore.network.CodecField;
import ruiseki.okcore.network.ExtendedBuffer;
import ruiseki.okcore.network.PacketCodec;

public class PacketCraftingPatternSlots extends PacketCodec {

    public Map<Integer, ItemStack> itemStacks;

    @CodecField
    public boolean isOutput;

    @CodecField
    public boolean isCraftingMode;

    public PacketCraftingPatternSlots() {}

    public PacketCraftingPatternSlots(Map<Integer, ItemStack> itemStacks, boolean isOutput, boolean isCraftingMode) {
        this.itemStacks = itemStacks;
        this.isOutput = isOutput;
        this.isCraftingMode = isCraftingMode;
    }

    @Override
    public void encode(ExtendedBuffer output) {
        super.encode(output);

        // Encode ItemStacks
        if (itemStacks == null) {
            output.writeInt(0);
        } else {
            output.writeInt(itemStacks.size());
            for (Map.Entry<Integer, ItemStack> entry : itemStacks.entrySet()) {
                output.writeInt(entry.getKey());
                boolean hasStack = entry.getValue() != null;
                output.writeBoolean(hasStack);
                if (hasStack) {
                    try {
                        output.writeItemStackToBuffer(entry.getValue());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    @Override
    public void decode(ExtendedBuffer input) {
        super.decode(input);

        // Decode ItemStacks
        int itemSize = input.readInt();
        itemStacks = new HashMap<>(itemSize);
        for (int i = 0; i < itemSize; i++) {
            int slotIndex = input.readInt();
            boolean hasStack = input.readBoolean();
            ItemStack stack = null;
            if (hasStack) {
                try {
                    stack = input.readItemStackFromBuffer();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            itemStacks.put(slotIndex, stack);
        }
    }

    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    public void actionClient(World world, EntityPlayer player) {
        // NOOP
    }

    @Override
    public void actionServer(World world, EntityPlayerMP player) {
        if (player.openContainer instanceof ContainerPatternTerm term) {
            if (!isOutput) {
                term.setCraftingMode(isCraftingMode);
            }

            StorageName targetInv = isOutput ? StorageName.CRAFTING_OUTPUT : StorageName.CRAFTING_INPUT;

            int maxSlots;
            if (!isOutput && term.isCraftingMode()) {
                maxSlots = 9;
            } else {
                maxSlots = isOutput
                    ? (term.getPatternOutputsWidth() * term.getPatternOutputsHeigh() * term.getPatternOutputPages())
                    : (term.getPatternInputsWidth() * term.getPatternInputsHeigh() * term.getPatternInputPages());
            }

            for (int i = 0; i < maxSlots; i++) {
                term.updateVirtualSlot(targetInv, i, null);
            }

            if (itemStacks != null) {
                for (Map.Entry<Integer, ItemStack> entry : itemStacks.entrySet()) {
                    Integer slotIndex = entry.getKey();
                    ItemStack stack = entry.getValue();
                    if (slotIndex != null && slotIndex >= 0 && slotIndex < maxSlots && stack != null) {
                        IAEItemStack aeStack = AEItemStack.create(stack);
                        if (aeStack != null) {
                            if (!isOutput && term.isCraftingMode()) {
                                aeStack.setStackSize(1);
                            }
                            term.updateVirtualSlot(targetInv, slotIndex, aeStack);
                        }
                    }
                }
            }

            term.detectAndSendChanges();
        }
    }
}
