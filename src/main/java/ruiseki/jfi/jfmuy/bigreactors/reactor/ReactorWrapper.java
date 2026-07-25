package ruiseki.jfi.jfmuy.bigreactors.reactor;

import net.minecraftforge.fluids.FluidRegistry;

import ruiseki.jfi.util.OreDictHelper;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;
import ruiseki.okcore.fluid.FluidHelpers;

public class ReactorWrapper implements IRecipeWrapper {

    private final ReactorEntry reactorEntry;

    public ReactorWrapper(ReactorEntry reactorEntry) {
        this.reactorEntry = reactorEntry;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        if (reactorEntry.isBlock()) {
            ingredients.setInputs(VanillaTypes.ITEM, OreDictHelper.oreDictToItemStacks(reactorEntry.getMaterial()));
        } else {
            ingredients.setInput(
                VanillaTypes.FLUID,
                FluidRegistry.getFluidStack(reactorEntry.getMaterial(), FluidHelpers.BUCKET_VOLUME));
        }
    }

    public ReactorEntry getReactorEntry() {
        return reactorEntry;
    }
}
