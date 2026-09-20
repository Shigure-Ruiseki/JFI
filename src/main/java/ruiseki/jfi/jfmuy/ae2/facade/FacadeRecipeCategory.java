package ruiseki.jfi.jfmuy.ae2.facade;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.Level;

import appeng.api.AEApi;
import appeng.api.definitions.IDefinitions;
import appeng.api.definitions.IItemDefinition;
import appeng.core.localization.GuiText;
import appeng.items.parts.ItemFacade;
import ruiseki.jfi.JFI;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;
import ruiseki.jfmuy.api.recipe.VanillaRecipeCategoryUid;

public class FacadeRecipeCategory implements IRecipeCategory<FacadeRecipeWrapper> {

    public static final String UID = "ae2.facade_crafting";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new FacadeRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
            IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

            registry.addRecipes(getRecipes(), VanillaRecipeCategoryUid.CRAFTING);
            registry.getRecipeTransferRegistry()
                .copyRecipeTransferHandlers(VanillaRecipeCategoryUid.CRAFTING, UID);
            registry.addRecipeCatalyst(
                new ItemStack(
                    AEApi.instance()
                        .definitions()
                        .items()
                        .facade()
                        .maybeItem()
                        .orNull()),
                UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null facade recipe initialization!", t);
        }
    }

    public static List<FacadeRecipeWrapper> getRecipes() {
        List<FacadeRecipeWrapper> recipes = new ArrayList<>();
        final IDefinitions definitions = AEApi.instance()
            .definitions();

        ItemFacade facade = (ItemFacade) definitions.items()
            .facade()
            .maybeItem()
            .get();
        IItemDefinition anchorDefinition = definitions.parts()
            .cableAnchor();

        final List<ItemStack> facades = facade.getFacades();
        for (final ItemStack anchorStack : anchorDefinition.maybeStack(1)
            .asSet()) {
            for (final ItemStack is : facades) {
                recipes.add(new FacadeRecipeWrapper(facade, anchorStack, is));
            }
        }
        return recipes;
    }

    private final IDrawable background;

    public FacadeRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper
            .createDrawable(new ResourceLocation("textures/gui/container/crafting_table.png"), 29, 16, 116, 54);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return GuiText.FacadeCrafting.getLocal();
    }

    @Override
    public String getModName() {
        return "Applied Energistics 2";
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, FacadeRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                int index = y * 3 + x;
                itemStacks.init(index, true, x * 18, y * 18);
            }
        }

        itemStacks.init(9, false, 94, 18);

        itemStacks.set(ingredients);
    }
}
