package ruiseki.jfi.jfmuy.tconstruct.dryingrack;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.apache.logging.log4j.Level;

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
import tconstruct.TConstruct;
import tconstruct.armor.TinkerArmor;
import tconstruct.library.crafting.DryingRackRecipes;
import tconstruct.library.crafting.DryingRackRecipes.DryingRecipe;

public class DryingRackRecipeCategory implements IRecipeCategory<DryingRackRecipeWrapper> {

    public static final String UID = "tconstruct.dryingrack";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new DryingRackRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
            IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

            registry.addRecipes(getRecipes(), UID);

            registry.addRecipeCatalyst(new ItemStack(TinkerArmor.dryingRack), UID);

        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null Drying Rack recipe!", t);
        }
    }

    public static List<DryingRackRecipeWrapper> getRecipes() {
        List<DryingRackRecipeWrapper> recipes = new ArrayList<>();
        if (DryingRackRecipes.recipes != null) {
            for (DryingRecipe drying : DryingRackRecipes.recipes) {
                if (drying != null && drying.input != null && drying.result != null) {
                    recipes.add(new DryingRackRecipeWrapper(drying));
                }
            }
        }
        return recipes;
    }

    private final IDrawable background;
    private final String title;

    public DryingRackRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation guiTex = new ResourceLocation("tinker", "textures/gui/nei/dryingrack.png");
        this.background = guiHelper.createDrawable(guiTex, 15, 0, 128, 56);
        this.title = StatCollector.translateToLocal("tconstruct.nei.dryingrack");
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getModName() {
        return TConstruct.modID;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, DryingRackRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, true, 27, 18);

        itemStacks.init(1, false, 82, 18);

        itemStacks.set(ingredients);
    }
}
