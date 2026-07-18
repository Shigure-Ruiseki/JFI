package ruiseki.jfi.jfmuy.botania.lexica;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;
import vazkii.botania.api.lexicon.KnowledgeType;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.lexicon.LexiconRecipeMappings;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.lexicon.page.PageText;

public class LexicaBotaniaWrapper implements IRecipeWrapper {

    private final LexiconEntry entry;
    private final ItemStack outputStack;
    private final ItemStack lexicaBook;

    public LexicaBotaniaWrapper(ItemStack outputStack, LexiconEntry entry) {
        this.entry = entry;
        this.outputStack = outputStack;
        this.lexicaBook = new ItemStack(ModItems.lexicon);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.ITEM, lexicaBook);
        ingredients.setOutput(VanillaTypes.ITEM, outputStack);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        int centerX = recipeWidth / 2;

        String title = EnumChatFormatting.UNDERLINE + StatCollector.translateToLocal(entry.getUnlocalizedName());
        int titleX = centerX - (minecraft.fontRenderer.getStringWidth(title) / 2);
        minecraft.fontRenderer.drawString(title, titleX, 26, 0x006600, false);

        KnowledgeType type = entry.getKnowledgeType();
        String typeName = type.color + StatCollector.translateToLocal(type.getUnlocalizedName())
            .replaceAll("&[0-9a-fklmnor]", "");
        int typeX = centerX - (minecraft.fontRenderer.getStringWidth(typeName) / 2);
        minecraft.fontRenderer.drawString(typeName, typeX, 38, 0x444444, false);

        PageText.renderText(
            5,
            52,
            160,
            200,
            10,
            0x666666,
            "\"" + StatCollector.translateToLocal(entry.getTagline()) + "\"");

        String key = LexiconRecipeMappings.stackToString(outputStack);
        String quickInfo = "botania.nei.quickInfo:" + key;
        String quickInfoLocal = StatCollector.translateToLocal(quickInfo);

        String textString;
        if (GuiScreen.isShiftKeyDown() && GuiScreen.isCtrlKeyDown() && minecraft.gameSettings.advancedItemTooltips) {
            textString = "name: " + key;
        } else if (quickInfo.equals(quickInfoLocal)) {
            textString = StatCollector.translateToLocal("botania.nei.lexicaNoInfo");
        } else {
            String separator = StatCollector.translateToLocal("botania.nei.lexicaSeparator");
            int separatorX = centerX - (minecraft.fontRenderer.getStringWidth(separator) / 2);
            minecraft.fontRenderer.drawString(separator, separatorX, 82, 0x999999, false);
            textString = quickInfoLocal;
        }

        PageText.renderText(5, 92, 160, 200, 10, 0x333333, textString);
    }

    public LexiconEntry getEntry() {
        return entry;
    }
}
