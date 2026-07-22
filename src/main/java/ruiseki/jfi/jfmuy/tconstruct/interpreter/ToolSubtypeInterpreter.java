package ruiseki.jfi.jfmuy.tconstruct.interpreter;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import ruiseki.jfmuy.api.ISubtypeRegistry;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.tools.ToolMaterial;

public class ToolSubtypeInterpreter implements ISubtypeRegistry.ISubtypeInterpreter {

    @Override
    public String apply(ItemStack itemStack) {
        if (itemStack == null) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        builder.append(itemStack.getItemDamage());

        if (itemStack.hasTagCompound() && itemStack.getTagCompound()
            .hasKey("InfiTool")) {
            NBTTagCompound toolTag = itemStack.getTagCompound()
                .getCompoundTag("InfiTool");

            int head = toolTag.getInteger("Head");
            int handle = toolTag.getInteger("Handle");
            int accessary = toolTag.getInteger("Accessary");
            int extra = toolTag.getInteger("Extra");

            builder.append(':')
                .append(getMaterialName(head))
                .append(',')
                .append(getMaterialName(handle));

            if (toolTag.hasKey("Accessary")) {
                builder.append(',')
                    .append(getMaterialName(accessary));
            }
            if (toolTag.hasKey("Extra")) {
                builder.append(',')
                    .append(getMaterialName(extra));
            }
        }

        return builder.toString();
    }

    private String getMaterialName(int matID) {
        ToolMaterial mat = TConstructRegistry.getMaterial(matID);
        if (mat != null && mat.name() != null) {
            return mat.name()
                .toLowerCase();
        }
        return String.valueOf(matID);
    }
}
