package ruiseki.jfi.jfmuy.exnihilo.hammer;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.Level;

import exnihilo.ENItems;
import exnihilo.registries.HammerRegistry;
import exnihilo.registries.helpers.Smashable;
import exnihilo.utils.ItemInfo;
import ruiseki.jfi.JFI;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class HammerRecipeCategory implements IRecipeCategory<HammerRecipeWrapper> {

    public static final String UID = "exnihilo.hammer";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new HammerRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
            IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

            registry.addRecipes(getRecipes(), UID);
            registry.addRecipeCatalyst(new ItemStack(ENItems.HammerWood), UID);
            registry.addRecipeCatalyst(new ItemStack(ENItems.HammerStone), UID);
            registry.addRecipeCatalyst(new ItemStack(ENItems.HammerIron), UID);
            registry.addRecipeCatalyst(new ItemStack(ENItems.HammerGold), UID);
            registry.addRecipeCatalyst(new ItemStack(ENItems.HammerDiamond), UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    public static List<HammerRecipeWrapper> getRecipes() {
        List<HammerRecipeWrapper> recipes = new ArrayList<>();

        for (ItemInfo inputInfo : HammerRegistry.getRewards()
            .keySet()) {
            List<Smashable> rewards = HammerRegistry.getRewards(inputInfo);
            if (rewards != null && !rewards.isEmpty()) {
                recipes.add(new HammerRecipeWrapper(inputInfo, rewards));
            }
        }

        return recipes;
    }

    private final IDrawable background;

    public HammerRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper
            .createDrawable(new ResourceLocation("exnihilo:textures/hammerNEI.png"), 0, 0, 166, 58);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return "Ex Nihilo Hammer";
    }

    @Override
    public String getModName() {
        return "Ex Nihilo";
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, HammerRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, true, 73, 3);

        List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);
        for (int i = 0; i < outputs.size() && i < 9; i++) {
            int col = i % 9;
            int row = i / 9;
            int slotIndex = 1 + i;

            itemStacks.init(slotIndex, false, 2 + col * 18, 36 + row * 18);
        }

        itemStacks.addTooltipCallback(recipeWrapper);
        itemStacks.set(ingredients);
    }
}
