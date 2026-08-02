package ruiseki.jfi.jfmuy.ae2;

import appeng.container.implementations.ContainerCraftingTerm;
import ruiseki.jfi.jfmuy.ae2.transfer.AE2CraftingRecipeTransferInfo;
import ruiseki.jfi.jfmuy.ae2.transfer.AE2RecipeCraftingHandler;
import ruiseki.jfmuy.Internal;
import ruiseki.jfmuy.api.IModPlugin;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.JFMUYPlugin;
import ruiseki.jfmuy.api.recipe.transfer.IRecipeTransferHandlerHelper;
import ruiseki.jfmuy.startup.StackHelper;

@JFMUYPlugin
public class AE2Plugin implements IModPlugin {

    @Override
    public void register(IModRegistry registry) {
        registry.addAdvancedGuiHandlers(new AE2GuiHandler());

        StackHelper stackHelper = Internal.getStackHelper();
        IRecipeTransferHandlerHelper handlerHelper = registry.getJFMUYHelpers()
            .recipeTransferHandlerHelper();
        AE2CraftingRecipeTransferInfo craftingTermInfo = new AE2CraftingRecipeTransferInfo();
        AE2RecipeCraftingHandler<ContainerCraftingTerm> craftingTermHandler = new AE2RecipeCraftingHandler<>(
            stackHelper,
            handlerHelper,
            craftingTermInfo);
        registry.getRecipeTransferRegistry()
            .addRecipeTransferHandler(craftingTermHandler, craftingTermInfo.getRecipeCategoryUid());
    }
}
