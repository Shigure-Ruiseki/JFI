package ruiseki.jfi.jfmuy.tconstruct.interpreter;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import cpw.mods.fml.common.registry.GameData;
import ruiseki.jfmuy.api.ISubtypeRegistry;

public class PatternSubtypeInterpreter implements ISubtypeRegistry.ISubtypeInterpreter {

    @Override
    public String apply(ItemStack itemStack) {
        if (itemStack == null) {
            return "";
        }

        String subtype = String.valueOf(itemStack.getItemDamage());

        if (itemStack.hasTagCompound()) {
            NBTTagCompound tag = itemStack.getTagCompound();

            if (tag.hasKey("InfiTool")) {
                NBTTagCompound infiTag = tag.getCompoundTag("InfiTool");
                if (infiTag.hasKey("RenderItem")) {
                    int itemID = infiTag.getInteger("RenderItem");
                    Item partItem = Item.getItemById(itemID);
                    if (partItem != null) {
                        subtype += ":" + getItemIdentifier(partItem);
                    }
                }
            } else if (tag.hasKey("Material")) {
                int materialID = tag.getInteger("Material");
                subtype += ":mat_" + materialID;
            }
        }

        return subtype;
    }

    private String getItemIdentifier(Item item) {
        String id = GameData.getItemRegistry()
            .getNameForObject(item);
        if (id != null) {
            return id;
        }
        return item.getUnlocalizedName();
    }
}
