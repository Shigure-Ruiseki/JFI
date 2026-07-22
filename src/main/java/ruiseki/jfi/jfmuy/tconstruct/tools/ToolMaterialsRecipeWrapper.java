package ruiseki.jfi.jfmuy.tconstruct.tools;

import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.tools.ArrowMaterial;
import tconstruct.library.tools.BowMaterial;
import tconstruct.library.tools.ToolMaterial;
import tconstruct.library.util.ColorUtils;
import tconstruct.library.util.HarvestLevels;

public class ToolMaterialsRecipeWrapper implements IRecipeWrapper {

    private final List<ItemStack> toolParts;
    private ToolMaterial material;
    private BowMaterial bowMaterial;
    private ArrowMaterial arrowMaterial;

    public ToolMaterialsRecipeWrapper(List<ItemStack> toolParts, int materialID) {
        this.toolParts = toolParts;
        this.material = TConstructRegistry.getMaterial(materialID);
    }

    public ToolMaterialsRecipeWrapper(List<ItemStack> toolParts, int materialID, boolean arrowBow) {
        this.toolParts = toolParts;
        this.arrowMaterial = TConstructRegistry.getArrowMaterial(materialID);
        this.bowMaterial = TConstructRegistry.getBowMaterial(materialID);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, Collections.singletonList(toolParts));
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        if (this.material != null) {
            minecraft.fontRenderer.drawString(
                EnumChatFormatting.BOLD + this.material.localizedName(),
                35,
                10,
                ColorUtils.materialName.getColor(),
                false);

            minecraft.fontRenderer.drawString(
                StatCollector.translateToLocal("gui.partcrafter4") + this.material.durability,
                35,
                20,
                ColorUtils.materialDurability.getColor(),
                false);

            minecraft.fontRenderer.drawString(
                StatCollector.translateToLocal("gui.partcrafter5") + this.material.handleModifier + "x",
                35,
                30,
                ColorUtils.materialHandleModifier1.getColor(),
                false);

            minecraft.fontRenderer.drawString(
                StatCollector.translateToLocal("gui.partcrafter11")
                    + Math.round(this.material.durability * this.material.handleModifier),
                35,
                40,
                ColorUtils.materialHandleModifier2.getColor(),
                false);

            minecraft.fontRenderer.drawString(
                StatCollector.translateToLocal("gui.partcrafter6") + (this.material.miningspeed / 100F),
                35,
                50,
                ColorUtils.materialMiningSpeed.getColor(),
                false);

            minecraft.fontRenderer.drawString(
                StatCollector.translateToLocal("gui.partcrafter7")
                    + HarvestLevels.getHarvestLevelName(this.material.harvestLevel),
                35,
                60,
                ColorUtils.materialHarvestLevel.getColor(),
                false);

            String heart = (this.material.attack == 2) ? StatCollector.translateToLocal("gui.partcrafter8")
                : StatCollector.translateToLocal("gui.partcrafter9");

            if (this.material.attack() % 2 == 0) {
                minecraft.fontRenderer.drawString(
                    StatCollector.translateToLocal("gui.partcrafter10") + (this.material.attack / 2) + heart,
                    35,
                    70,
                    ColorUtils.materialAttack1.getColor(),
                    false);
            } else {
                minecraft.fontRenderer.drawString(
                    StatCollector.translateToLocal("gui.partcrafter10") + (this.material.attack / 2F) + heart,
                    35,
                    70,
                    ColorUtils.materialAttack2.getColor(),
                    false);
            }

            int abilityY = 85;
            if (this.material.reinforced > 0) {
                minecraft.fontRenderer.drawString(
                    getReinforcedString(this.material.reinforced),
                    35,
                    85,
                    ColorUtils.materialReinforced.getColor(),
                    false);
                abilityY += 10;
            }

            String ability = this.material.ability();
            if (ability != null) {
                if (this.material.stonebound != 0) {
                    minecraft.fontRenderer.drawString(
                        ability + " (" + Math.abs(this.material.stonebound) + ")",
                        35,
                        abilityY,
                        ColorUtils.materialStonebound.getColor(),
                        false);
                } else {
                    minecraft.fontRenderer
                        .drawString(ability, 35, abilityY, ColorUtils.materialAbility.getColor(), false);
                }
            }
        }

        if (this.bowMaterial != null) {
            int y = 20;
            int x = 35;
            minecraft.fontRenderer.drawString(
                EnumChatFormatting.BOLD + StatCollector.translateToLocal("tconstruct.nei.projectilematerials"),
                35,
                10,
                ColorUtils.materialBow.getColor(),
                false);
            minecraft.fontRenderer.drawString(
                StatCollector.translateToLocal("gui.toolstation6") + this.bowMaterial.drawspeed,
                x,
                y,
                ColorUtils.materialBowDrawSpeed.getColor(),
                false);
            y += 10;
            minecraft.fontRenderer.drawString(
                StatCollector.translateToLocal("gui.toolstation7") + this.bowMaterial.flightSpeedMax,
                x,
                y,
                ColorUtils.materialBowFlightSpeedMax.getColor(),
                false);
        }

        if (this.arrowMaterial != null) {
            int y = 50;
            int x = 35;
            minecraft.fontRenderer.drawString(
                StatCollector.translateToLocal("gui.toolstation8") + this.arrowMaterial.mass,
                x,
                y,
                ColorUtils.materialArrowMass.getColor(),
                false);
            y += 10;
            minecraft.fontRenderer.drawString(
                StatCollector.translateToLocal("gui.toolstation22") + this.arrowMaterial.breakChance,
                x,
                y,
                ColorUtils.materialArrowBreakChance.getColor(),
                false);
        }
    }

    public static String getReinforcedString(int reinforced) {
        if (reinforced > 9) return "Unbreakable";
        String ret = "Reinforced ";
        switch (reinforced) {
            case 1:
                ret += "I";
                break;
            case 2:
                ret += "II";
                break;
            case 3:
                ret += "III";
                break;
            case 4:
                ret += "IV";
                break;
            case 5:
                ret += "V";
                break;
            case 6:
                ret += "VI";
                break;
            case 7:
                ret += "VII";
                break;
            case 8:
                ret += "VIII";
                break;
            case 9:
                ret += "IX";
                break;
            default:
                ret += "X";
                break;
        }
        return ret;
    }
}
