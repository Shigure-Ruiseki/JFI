package ruiseki.jfi.jfmuy.immersiveengineering.blueprint;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.apache.logging.log4j.Level;

import blusunrize.immersiveengineering.api.crafting.BlueprintCraftingRecipe;
import blusunrize.immersiveengineering.common.IEContent;
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

public class BlueprintRecipeCategory implements IRecipeCategory<BlueprintRecipeWrapper> {

    public static final String UID = "immersiveengineering.blueprint";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new BlueprintRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
            IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

            registry.addRecipes(getRecipes(), UID);
            registry.addRecipeCatalyst(new ItemStack(IEContent.blockWoodenDevice, 1, 5), UID);

        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    public static List<BlueprintRecipeWrapper> getRecipes() {
        List<BlueprintRecipeWrapper> recipes = new ArrayList<>();
        for (int s = 0; s < BlueprintCraftingRecipe.blueprintCategories.size(); ++s) {
            String category = BlueprintCraftingRecipe.blueprintCategories.get(s);
            List<BlueprintCraftingRecipe> list = BlueprintCraftingRecipe.recipeList.get(category);
            if (list != null) {
                for (BlueprintCraftingRecipe recipe : list) {
                    if (recipe != null) {
                        recipes.add(new BlueprintRecipeWrapper(s, recipe));
                    }
                }
            }
        }
        return recipes;
    }

    private final IDrawable background;
    private final IDrawable slotDrawable;
    private final String title;

    public BlueprintRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation texture = new ResourceLocation("immersiveengineering", "textures/gui/workbench.png");
        this.background = guiHelper.createDrawable(texture, 5, 11, 166, 65);
        this.slotDrawable = guiHelper.getSlotDrawable();
        this.title = StatCollector
            .translateToLocal("tile.ImmersiveEngineering.woodenDevice.modificationWorkbench.name");
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
        return "Immersive Engineering";
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, BlueprintRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, true, 19, 11);
        itemStacks.setBackground(0, slotDrawable);

        for (int i = 0; i < 6; i++) {
            int posX = 75 + (i % 2) * 18;
            int posY = 10 + (i / 2) * 18;
            itemStacks.init(1 + i, true, posX, posY);
            itemStacks.setBackground(1 + i, slotDrawable);
        }

        itemStacks.init(7, false, 128, 28);
        itemStacks.setBackground(7, slotDrawable);

        itemStacks.set(ingredients);
    }
}
