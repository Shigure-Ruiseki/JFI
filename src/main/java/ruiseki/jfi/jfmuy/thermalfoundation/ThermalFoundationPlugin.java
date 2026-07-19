package ruiseki.jfi.jfmuy.thermalfoundation;

import net.minecraft.item.ItemStack;

import cofh.thermalfoundation.core.TFProps;
import cofh.thermalfoundation.item.Equipment;
import cofh.thermalfoundation.item.VanillaEquipment;
import ruiseki.jfmuy.api.IModPlugin;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.JFMUYPlugin;
import ruiseki.jfmuy.api.ingredients.IIngredientBlacklist;

@JFMUYPlugin
public class ThermalFoundationPlugin implements IModPlugin {

    @Override
    public void register(IModRegistry registry) {
        if (!TFProps.showDisabledEquipment) {

            IIngredientBlacklist blacklist = registry.getJFMUYHelpers()
                .getIngredientBlacklist();

            for (Equipment equipment : Equipment.values()) {
                if (!equipment.enableArmor) {
                    blacklist.addIngredientToBlacklist(new ItemStack(equipment.armorHelmet.getItem(), 1, 32767));
                    blacklist.addIngredientToBlacklist(new ItemStack(equipment.armorPlate.getItem(), 1, 32767));
                    blacklist.addIngredientToBlacklist(new ItemStack(equipment.armorLegs.getItem(), 1, 32767));
                    blacklist.addIngredientToBlacklist(new ItemStack(equipment.armorBoots.getItem(), 1, 32767));
                }

                if (!equipment.enableTools[0])
                    blacklist.addIngredientToBlacklist(new ItemStack(equipment.toolSword.getItem(), 1, 32767));
                if (!equipment.enableTools[1])
                    blacklist.addIngredientToBlacklist(new ItemStack(equipment.toolShovel.getItem(), 1, 32767));
                if (!equipment.enableTools[2])
                    blacklist.addIngredientToBlacklist(new ItemStack(equipment.toolPickaxe.getItem(), 1, 32767));
                if (!equipment.enableTools[3])
                    blacklist.addIngredientToBlacklist(new ItemStack(equipment.toolAxe.getItem(), 1, 32767));
                if (!equipment.enableTools[4])
                    blacklist.addIngredientToBlacklist(new ItemStack(equipment.toolHoe.getItem(), 1, 32767));
                if (!equipment.enableTools[5])
                    blacklist.addIngredientToBlacklist(new ItemStack(equipment.toolShears.getItem(), 1, 32767));
                if (!equipment.enableTools[6])
                    blacklist.addIngredientToBlacklist(new ItemStack(equipment.toolFishingRod.getItem(), 1, 32767));
                if (!equipment.enableTools[7])
                    blacklist.addIngredientToBlacklist(new ItemStack(equipment.toolSickle.getItem(), 1, 32767));
                if (!equipment.enableTools[8])
                    blacklist.addIngredientToBlacklist(new ItemStack(equipment.toolBow.getItem(), 1, 32767));
            }

            for (VanillaEquipment vanillaEquip : VanillaEquipment.values()) {
                if (!vanillaEquip.enableTools[0])
                    blacklist.addIngredientToBlacklist(new ItemStack(vanillaEquip.toolShears.getItem(), 1, 32767));
                if (!vanillaEquip.enableTools[1])
                    blacklist.addIngredientToBlacklist(new ItemStack(vanillaEquip.toolFishingRod.getItem(), 1, 32767));
                if (!vanillaEquip.enableTools[2])
                    blacklist.addIngredientToBlacklist(new ItemStack(vanillaEquip.toolSickle.getItem(), 1, 32767));
                if (!vanillaEquip.enableTools[3])
                    blacklist.addIngredientToBlacklist(new ItemStack(vanillaEquip.toolBow.getItem(), 1, 32767));
            }
        }
    }
}
