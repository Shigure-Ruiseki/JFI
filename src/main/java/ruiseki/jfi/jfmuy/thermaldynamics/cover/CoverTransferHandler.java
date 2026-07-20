package ruiseki.jfi.jfmuy.thermaldynamics.cover;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.recipe.VanillaRecipeCategoryUid;
import ruiseki.jfmuy.api.recipe.transfer.IRecipeTransferError;
import ruiseki.jfmuy.api.recipe.transfer.IRecipeTransferHandler;
import ruiseki.jfmuy.recipes.RecipeTransferRegistry;

public class CoverTransferHandler implements IRecipeTransferHandler<Container> {

    private final Class<? extends Container> containerClass;
    private final RecipeTransferRegistry registry;

    public CoverTransferHandler(Class<? extends Container> containerClass, RecipeTransferRegistry registry) {
        this.containerClass = containerClass;
        this.registry = registry;
    }

    @Override
    public Class<Container> getContainerClass() {
        return (Class<Container>) this.containerClass;
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public IRecipeTransferError transferRecipe(Container container, IRecipeLayout recipeLayout, EntityPlayer player,
        boolean maxTransfer, boolean doTransfer) {
        IRecipeTransferHandler handler = registry.getRecipeTransferHandlers()
            .get(container.getClass(), VanillaRecipeCategoryUid.CRAFTING);
        if (handler != null) {
            return handler.transferRecipe(container, recipeLayout, player, maxTransfer, doTransfer);
        }
        return null;
    }
}
