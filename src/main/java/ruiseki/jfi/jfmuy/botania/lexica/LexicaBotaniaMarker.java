package ruiseki.jfi.jfmuy.botania.lexica;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import ruiseki.jfmuy.api.recipe.IRecipeWrapper;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.LexiconCategory;
import vazkii.botania.api.lexicon.LexiconEntry;

public class LexicaBotaniaMarker {

    public static List<IRecipeWrapper> getValidRecipes() {
        List<IRecipeWrapper> recipeWrappers = new ArrayList<>();

        for (LexiconCategory category : BotaniaAPI.getAllCategories()) {
            for (LexiconEntry entry : category.entries) {
                ItemStack icon = entry.getIcon();
                if (icon != null && icon.getItem() != null) {
                    recipeWrappers.add(new LexicaBotaniaWrapper(icon, entry));
                }
            }
        }

        return recipeWrappers;
    }
}
