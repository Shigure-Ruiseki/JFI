package ruiseki.jfi.jfmuy.botania.brewery;

import net.minecraft.item.ItemStack;

import ruiseki.jfmuy.api.ISubtypeRegistry.ISubtypeInterpreter;
import vazkii.botania.common.item.brew.ItemBrewBase;

public class BrewerySubtype implements ISubtypeInterpreter {

    public static final BrewerySubtype INSTANCE = new BrewerySubtype();

    @Override
    public String apply(ItemStack stack) {
        if (stack.getItem() instanceof ItemBrewBase brewBase) {
            return brewBase.getBrew(stack)
                .getKey();
        }
        return "none";
    }
}
