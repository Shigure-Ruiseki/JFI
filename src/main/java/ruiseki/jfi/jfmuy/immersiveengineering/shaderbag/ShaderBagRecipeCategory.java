package ruiseki.jfi.jfmuy.immersiveengineering.shaderbag;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.Level;

import blusunrize.immersiveengineering.api.shader.ShaderRegistry;
import blusunrize.immersiveengineering.common.IEContent;
import cofh.lib.util.helpers.StringHelper;
import ruiseki.jfi.JFI;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.ICraftingGridHelper;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IDrawableStatic;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;
import ruiseki.jfmuy.api.recipe.VanillaRecipeCategoryUid;

public class ShaderBagRecipeCategory implements IRecipeCategory<ShaderBagRecipeWrapper> {

    public static final String UID = "immersiveengineering.shaderbag";

    private static final int craftOutputSlot = 0;
    private static final int craftInputSlot1 = 1;

    public static final int width = 116;
    public static final int height = 54;

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        registry.addRecipeCategories(new ShaderBagRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            registry.addRecipes(getRecipes(), UID);
            registry.addRecipeCatalyst(new ItemStack(IEContent.itemShaderBag), UID);
            registry.getRecipeTransferRegistry()
                .copyRecipeTransferHandlers(VanillaRecipeCategoryUid.CRAFTING, UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null refinery recipe!", t);
        }
    }

    public static List<ShaderBagRecipeWrapper> getRecipes() {
        List<ShaderBagRecipeWrapper> recipes = new LinkedList<>();

        if (ShaderRegistry.sortedRarityMap != null) {
            for (int i = 1; i < ShaderRegistry.sortedRarityMap.size(); ++i) {
                EnumRarity rarity = (EnumRarity) ShaderRegistry.sortedRarityMap.get(i);
                recipes.add(new ShaderBagRecipeWrapper(rarity, true));
                recipes.add(new ShaderBagRecipeWrapper(rarity, false));
            }
        }
        return recipes;
    }

    private final IDrawableStatic background;
    private final String localizedName;
    private final ICraftingGridHelper craftingGridHelper;

    public ShaderBagRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation("minecraft", "textures/gui/container/crafting_table.png");

        this.background = guiHelper.createDrawable(location, 29, 16, width, height);
        this.localizedName = StringHelper.localize("item.ImmersiveEngineering.shaderBag.name");
        this.craftingGridHelper = guiHelper.createCraftingGridHelper(craftInputSlot1, craftOutputSlot);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return localizedName;
    }

    @Override
    public String getModName() {
        return "Immersive Engineering";
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, ShaderBagRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        guiItemStacks.init(craftOutputSlot, false, 94, 18);

        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                int index = craftInputSlot1 + x + (y * 3);
                guiItemStacks.init(index, true, x * 18, y * 18);
            }
        }

        craftingGridHelper.setInputs(guiItemStacks, ingredients.getInputs(VanillaTypes.ITEM));
        guiItemStacks.set(
            craftOutputSlot,
            ingredients.getOutputs(VanillaTypes.ITEM)
                .get(0));
    }
}
