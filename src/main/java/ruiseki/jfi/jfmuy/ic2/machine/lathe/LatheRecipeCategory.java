package ruiseki.jfi.jfmuy.ic2.machine.lathe;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.apache.logging.log4j.Level;

import ic2.api.item.ILatheItem;
import ic2.core.IC2;
import ic2.core.Ic2Items;
import ic2.core.block.machine.gui.GuiLathe;
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

public class LatheRecipeCategory implements IRecipeCategory<LatheRecipeWrapper> {

    public static final String UID = "ic2.lathe";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new LatheRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            registry.addRecipes(getRecipes(), UID);
            registry.addRecipeClickArea(GuiLathe.class, 35, 34, 120, 20, UID);
            registry.addRecipeCatalyst(Ic2Items.lathe, UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Error registering Lathe recipes!", t);
        }
    }

    public static List<LatheRecipeWrapper> getRecipes() {
        List<LatheRecipeWrapper> recipes = new ArrayList<>();
        for (Object object : Item.itemRegistry) {
            Item item = (Item) object;
            if (item instanceof ILatheItem) {
                ItemStack stack = new ItemStack(item);
                recipes.add(new LatheRecipeWrapper(stack));
            }
        }
        return recipes;
    }

    private final IDrawable background;

    public LatheRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation guiTex = new ResourceLocation(IC2.textureDomain, "textures/gui/GUILathe.png");
        this.background = guiHelper.createDrawable(guiTex, 5, 5, 160, 75);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal("ic2.Lathe.gui.name");
    }

    @Override
    public String getModName() {
        return IC2.MODID;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, LatheRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, true, 4, 6);
        itemStacks.init(1, true, 4, 24);
        itemStacks.init(2, false, 4, 51);

        itemStacks.set(ingredients);
    }
}
