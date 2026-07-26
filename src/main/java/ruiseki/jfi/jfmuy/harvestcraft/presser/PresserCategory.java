package ruiseki.jfi.jfmuy.harvestcraft.presser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.apache.logging.log4j.Level;

import com.pam.harvestcraft.BlockRegistry;
import com.pam.harvestcraft.PresserRecipes;

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

public class PresserCategory implements IRecipeCategory<PresserWrapper> {

    public static final String UID = "harvestcraft.presser";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new PresserCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            registry.addRecipes(getRecipes(), UID);

            if (BlockRegistry.pamPresser != null) {
                registry.addRecipeCatalyst(new ItemStack(BlockRegistry.pamPresser), UID);
            }
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    private static List<PresserWrapper> getRecipes() {
        List<PresserWrapper> recipes = new ArrayList<>();
        Map<ItemStack, ItemStack[]> pressingList = PresserRecipes.pressing()
            .getPressingList();

        if (pressingList != null) {
            for (Map.Entry<ItemStack, ItemStack[]> entry : pressingList.entrySet()) {
                ItemStack input = entry.getKey();
                ItemStack[] outputs = entry.getValue();

                if (input != null && outputs != null && outputs.length == 2) {
                    recipes.add(new PresserWrapper(input, outputs[0], outputs[1]));
                }
            }
        }
        return recipes;
    }

    private final IDrawable background;

    public PresserCategory(IGuiHelper guiHelper) {
        ResourceLocation guiTexture = new ResourceLocation("neiintegration", "textures/harvestcraft/presser.png");
        this.background = guiHelper.createDrawable(guiTexture, 0, 0, 160, 65);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal("tile.presser.name");
    }

    @Override
    public String getModName() {
        return "Pam's HarvestCraft";
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, PresserWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, true, 71, 8);
        itemStacks.init(1, false, 53, 39);
        itemStacks.init(2, false, 89, 39);

        itemStacks.set(ingredients);
    }
}
