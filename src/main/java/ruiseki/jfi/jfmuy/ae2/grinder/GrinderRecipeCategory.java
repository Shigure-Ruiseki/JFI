package ruiseki.jfi.jfmuy.ae2.grinder;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.Level;

import appeng.api.AEApi;
import appeng.api.features.IGrinderEntry;
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

public class GrinderRecipeCategory implements IRecipeCategory<GrinderRecipeWrapper> {

    public static final String UID = "ae2.grinder";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new GrinderRecipeCategory(guiHelper));
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
                        .grindStone()
                        .maybeItem()
                        .orNull()),
                UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null grinder recipe initialization!", t);
        }
    }

    public static List<GrinderRecipeWrapper> getRecipes() {
        List<GrinderRecipeWrapper> recipes = new ArrayList<>();
        for (IGrinderEntry recipe : AEApi.instance()
            .registries()
            .grinder()
            .getRecipes()) {
            recipes.add(new GrinderRecipeWrapper(recipe));
        }
        return recipes;
    }

    private final IDrawable background;
    private final IDrawable background2;

    public GrinderRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(160, 70);
        this.background2 = guiHelper
            .createDrawable(new ResourceLocation("appliedenergistics2", "textures/guis/grinder.png"), 75, 32, 96, 54);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return GuiText.GrindStone.getLocal();
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
    public void drawExtras(Minecraft minecraft) {
        IRecipeCategory.super.drawExtras(minecraft);
        background2.draw(minecraft, 34, 18);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, GrinderRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        // Input
        itemStacks.init(0, true, 38, 25);

        // Main Output
        itemStacks.init(1, false, 70, 48);

        // Optional Output 1
        itemStacks.init(2, false, 88, 48);

        // Optional Output 2
        itemStacks.init(3, false, 106, 48);

        itemStacks.set(ingredients);
    }
}
