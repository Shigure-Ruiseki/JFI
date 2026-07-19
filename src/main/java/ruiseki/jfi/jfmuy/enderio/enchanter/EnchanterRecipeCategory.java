package ruiseki.jfi.jfmuy.enderio.enchanter;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import crazypants.enderio.EnderIO;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;

public class EnchanterRecipeCategory implements IRecipeCategory<EnchanterRecipeWrapper> {

    public static final String UID = "EnderIOEnchanter";
    private final IDrawable background;

    public EnchanterRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper
            .createDrawable(new ResourceLocation("enderio:textures/gui/23/enchanter.png"), 25, 16, 128, 55);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal("enderio.nei.enchanter");
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
    public void drawExtras(Minecraft minecraft) {}

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, EnchanterRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(0, true, 2 - 1, 19 - 1);
        guiItemStacks.init(1, true, 51 - 1, 19 - 1);
        guiItemStacks.init(2, false, 109 - 1, 19 - 1);

        guiItemStacks.set(ingredients);

        recipeWrapper.setRecipeLayout(recipeLayout);
    }
}
