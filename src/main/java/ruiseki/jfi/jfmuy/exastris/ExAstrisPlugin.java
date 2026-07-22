package ruiseki.jfi.jfmuy.exastris;

import net.minecraft.item.ItemStack;

import ExAstris.ExAstrisBlock;
import ruiseki.jfi.jfmuy.exnihilo.hammer.HammerRecipeCategory;
import ruiseki.jfi.jfmuy.exnihilo.sieve.SieveRecipeCategory;
import ruiseki.jfmuy.api.IModPlugin;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.JFMUYPlugin;

@JFMUYPlugin(value = "exastris")
public class ExAstrisPlugin implements IModPlugin {

    @Override
    public void register(IModRegistry registry) {
        registry.addRecipeCatalyst(new ItemStack(ExAstrisBlock.HammerAutomatic), HammerRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ExAstrisBlock.SieveAutomatic), SieveRecipeCategory.UID);
    }
}
