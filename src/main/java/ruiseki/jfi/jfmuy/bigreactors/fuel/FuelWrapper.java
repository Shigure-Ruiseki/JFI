package ruiseki.jfi.jfmuy.bigreactors.fuel;

import ruiseki.jfi.util.OreDictHelper;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class FuelWrapper implements IRecipeWrapper {

    private final FuelEntry fuelEntry;

    public FuelWrapper(FuelEntry fuelEntry) {
        this.fuelEntry = fuelEntry;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.ITEM, OreDictHelper.oreDictToItemStacks(fuelEntry.getFuel()));
        ingredients.setOutputs(VanillaTypes.ITEM, OreDictHelper.oreDictToItemStacks(fuelEntry.getWaste()));
    }
}
