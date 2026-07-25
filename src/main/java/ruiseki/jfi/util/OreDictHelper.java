package ruiseki.jfi.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

import ruiseki.okcore.datastructure.NonNullList;

public class OreDictHelper {

    public static List<ItemStack> oreDictToItemStacks(String oreDictEntry) {
        if (oreDictEntry == null || oreDictEntry.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(OreDictionary.getOres(oreDictEntry));
    }

    public static NonNullList<ItemStack> oreDictToItemStacks(List<String> oreDictEntries) {
        NonNullList<ItemStack> result = NonNullList.create();
        if (oreDictEntries == null || oreDictEntries.isEmpty()) {
            return result;
        }

        for (String entry : oreDictEntries) {
            if (entry != null && !entry.isEmpty()) {
                result.addAll(OreDictionary.getOres(entry));
            }
        }
        return result;
    }

    public static boolean doesOreExist(String oreDictEntry) {
        return oreDictEntry != null && OreDictionary.doesOreNameExist(oreDictEntry)
            && !OreDictionary.getOres(oreDictEntry)
                .isEmpty();
    }

    public static boolean doesFluidExist(String fluidDictEntry) {
        return fluidDictEntry != null && FluidRegistry.isFluidRegistered(fluidDictEntry);
    }
}
