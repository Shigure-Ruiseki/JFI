package ruiseki.jfi.jfmuy.tconstruct.interpreter;

import net.minecraft.item.ItemStack;

import ruiseki.jfmuy.api.ISubtypeRegistry;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.tools.ToolMaterial;

public class ToolPartSubtypeInterpreter implements ISubtypeRegistry.ISubtypeInterpreter {

    @Override
    public String apply(ItemStack itemStack) {
        if (itemStack == null) {
            return "";
        }

        int meta = itemStack.getItemDamage();
        String result = String.valueOf(meta);

        if (TConstructRegistry.toolMaterials.containsKey(meta)) {
            ToolMaterial mat = TConstructRegistry.toolMaterials.get(meta);
            if (mat != null) {
                return result + ":"
                    + mat.name()
                        .toLowerCase();
            }
        }

        return result;
    }
}
