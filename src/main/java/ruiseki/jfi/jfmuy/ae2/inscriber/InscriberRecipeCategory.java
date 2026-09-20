package ruiseki.jfi.jfmuy.ae2.inscriber;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.Level;

import appeng.api.AEApi;
import appeng.api.features.IInscriberRecipe;
import appeng.core.localization.GuiText;
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

public class InscriberRecipeCategory implements IRecipeCategory<InscriberRecipeWrapper> {

    public static final String UID = "ae2.inscriber";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new InscriberRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
            IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

            registry.addRecipes(getRecipes(), UID);

            registry.addRecipeCatalyst(
                new ItemStack(
                    AEApi.instance()
                        .definitions()
                        .blocks()
                        .inscriber()
                        .maybeItem()
                        .orNull()),
                UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null inscriber recipe initialization!", t);
        }
    }

    public static List<InscriberRecipeWrapper> getRecipes() {
        List<InscriberRecipeWrapper> recipes = new java.util.ArrayList<>();
        for (IInscriberRecipe recipe : AEApi.instance()
            .registries()
            .inscriber()
            .getRecipes()) {
            recipes.add(new InscriberRecipeWrapper(recipe));
        }
        return recipes;
    }

    private final IDrawable background;

    public InscriberRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(
            new ResourceLocation("appliedenergistics2", "textures/guis/inscriber.png"),
            24,
            11,
            128,
            75);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return GuiText.Inscriber.getLocal();
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
    public void setRecipe(IRecipeLayout recipeLayout, InscriberRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, true, 20, 4); // Top Plate / Optional Ingot
        itemStacks.init(1, true, 38, 27); // Main Input (Central)
        itemStacks.init(2, true, 20, 50); // Bottom Plate / Optional Ingot
        itemStacks.init(3, false, 88, 28); // Output

        itemStacks.set(ingredients);
    }
}
