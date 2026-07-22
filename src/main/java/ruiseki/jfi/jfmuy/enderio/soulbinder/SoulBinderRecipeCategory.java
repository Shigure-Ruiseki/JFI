package ruiseki.jfi.jfmuy.enderio.soulbinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.apache.logging.log4j.Level;

import crazypants.enderio.EnderIO;
import crazypants.enderio.ModObject;
import crazypants.enderio.machine.IMachineRecipe;
import crazypants.enderio.machine.MachineRecipeRegistry;
import crazypants.enderio.machine.soul.ISoulBinderRecipe;
import ruiseki.jfi.JFI;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IDrawableAnimated;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class SoulBinderRecipeCategory implements IRecipeCategory<SoulBinderRecipeWrapper> {

    public static final String UID = "EnderIOSoulBinder";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new SoulBinderRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
            IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

            registry.addRecipes(getRecipes(), UID);
            registry.addRecipeCatalyst(new ItemStack(EnderIO.blockSoulFuser), UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    public static List<IRecipeWrapper> getRecipes() {
        List<IRecipeWrapper> recipes = new ArrayList<>();

        Map<String, IMachineRecipe> registeredRecipes = MachineRecipeRegistry.instance
            .getRecipesForMachine(ModObject.blockSoulBinder.unlocalisedName);

        if (registeredRecipes != null && !registeredRecipes.isEmpty()) {
            for (IMachineRecipe recipe : registeredRecipes.values()) {
                if (recipe instanceof ISoulBinderRecipe validRecipes) {
                    recipes.add(new SoulBinderRecipeWrapper(validRecipes));
                }
            }
        }

        return recipes;
    }

    private final IDrawable background;
    private final IDrawableAnimated progressArrow;

    public SoulBinderRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation backgroundLocation = new ResourceLocation("enderio:textures/gui/23/soulFuser.png");

        this.background = guiHelper.createDrawable(backgroundLocation, 29, 21, 128, 55);

        IDrawableAnimated.StartDirection right = IDrawableAnimated.StartDirection.LEFT;
        this.progressArrow = guiHelper
            .createAnimatedDrawable(guiHelper.createDrawable(backgroundLocation, 177, 14, 23, 17), 160, right, false);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal("enderio.nei.soulbinder");
    }

    @Override
    public String getModName() {
        return EnderIO.MODID;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        progressArrow.draw(minecraft, 52, 13);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, final SoulBinderRecipeWrapper recipeWrapper,
        IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(0, true, 8, 12);

        guiItemStacks.init(1, true, 29, 12);

        guiItemStacks.init(2, false, 82, 12);

        guiItemStacks.init(3, false, 104, 12);

        guiItemStacks.set(ingredients);
    }
}
