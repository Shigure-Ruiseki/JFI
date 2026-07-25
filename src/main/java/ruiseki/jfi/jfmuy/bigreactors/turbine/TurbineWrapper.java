package ruiseki.jfi.jfmuy.bigreactors.turbine;

import ruiseki.jfi.util.OreDictHelper;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class TurbineWrapper implements IRecipeWrapper {

    private final TurbineEntry turbineEntry;

    public TurbineWrapper(TurbineEntry turbineEntry) {
        this.turbineEntry = turbineEntry;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.ITEM, OreDictHelper.oreDictToItemStacks(turbineEntry.getMaterial()));
    }
}
