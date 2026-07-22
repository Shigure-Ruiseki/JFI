package ruiseki.jfi.jfmuy.tconstruct.alloying;

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
import ruiseki.jfmuy.api.gui.IGuiFluidStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;
import tconstruct.TConstruct;
import tconstruct.library.crafting.AlloyMix;
import tconstruct.library.crafting.Smeltery;
import tconstruct.smeltery.TinkerSmeltery;

public class AlloyingRecipeCategory implements IRecipeCategory<AlloyingRecipeWrapper> {

    public static final String UID = "tconstruct.smeltery.alloying";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new AlloyingRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
            IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

            registry.addRecipes(getRecipes(), UID);

            registry.addRecipeCatalyst(new ItemStack(TinkerSmeltery.smeltery), UID);

        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null Alloying recipe!", t);
        }
    }

    public static List<AlloyingRecipeWrapper> getRecipes() {
        List<AlloyingRecipeWrapper> recipes = new ArrayList<>();
        for (AlloyMix alloy : Smeltery.getAlloyList()) {
            if (alloy != null && alloy.result != null && alloy.mixers != null && !alloy.mixers.isEmpty()) {
                recipes.add(new AlloyingRecipeWrapper(alloy));
            }
        }
        return recipes;
    }

    private final IDrawable background;
    private final String title;

    public AlloyingRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation guiTex = new ResourceLocation("tinker", "textures/gui/nei/smeltery.png");
        this.background = guiHelper.createDrawable(guiTex, 0, 62, 160, 65);
        this.title = StatCollector.translateToLocal("tconstruct.nei.alloying");
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
    public void setRecipe(IRecipeLayout recipeLayout, AlloyingRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();

        int maxCapacity = recipeWrapper.getMaxInputAmount();
        fluidStacks.init(0, false, 118, 9, 18, 32, maxCapacity, true, null);

        int mixerCount = recipeWrapper.getRecipe().mixers.size();
        if (mixerCount > 0) {
            int totalWidth = 36;
            int width = totalWidth / mixerCount;

            for (int i = 0; i < mixerCount; i++) {
                int tankWidth = (i == mixerCount - 1) ? (totalWidth - width * i) : width;
                int xPos = 21 + width * i;
                fluidStacks.init(1 + i, true, xPos, 9, tankWidth, 32, maxCapacity, true, null);
            }
        }

        fluidStacks.set(ingredients);
    }
}
