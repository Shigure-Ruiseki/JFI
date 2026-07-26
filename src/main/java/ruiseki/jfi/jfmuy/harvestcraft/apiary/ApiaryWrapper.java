package ruiseki.jfi.jfmuy.harvestcraft.apiary;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.pam.harvestcraft.ItemRegistry;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class ApiaryWrapper implements IRecipeWrapper {

    public static final Map<Item, Float> BEE_PRODUCTS = new LinkedHashMap<>();

    static {
        if (ItemRegistry.waxcombItem != null) BEE_PRODUCTS.put(ItemRegistry.waxcombItem, 0.5F);
        if (ItemRegistry.honeycombItem != null) BEE_PRODUCTS.put(ItemRegistry.honeycombItem, 0.45F);
        if (ItemRegistry.grubItem != null) BEE_PRODUCTS.put(ItemRegistry.grubItem, 0.05F);
    }

    private final ItemStack inputQueen;
    private final List<ItemStack> outputs = new ArrayList<>();
    private final List<Float> chances = new ArrayList<>();

    public ApiaryWrapper() {
        this.inputQueen = new ItemStack(ItemRegistry.queenbeeItem);

        for (Entry<Item, Float> entry : BEE_PRODUCTS.entrySet()) {
            this.outputs.add(new ItemStack(entry.getKey()));
            this.chances.add(entry.getValue());
        }
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        if (this.inputQueen != null) {
            ingredients.setInput(VanillaTypes.ITEM, this.inputQueen);
        }
        if (!this.outputs.isEmpty()) {
            ingredients.setOutputs(VanillaTypes.ITEM, this.outputs);
        }
    }
}
