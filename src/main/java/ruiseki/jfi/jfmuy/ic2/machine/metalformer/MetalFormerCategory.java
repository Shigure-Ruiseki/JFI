package ruiseki.jfi.jfmuy.ic2.machine.metalformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import ic2.core.IC2;
import ic2.core.block.machine.gui.GuiMetalFormer;
import ruiseki.jfi.jfmuy.ic2.machine.MachineRecipeCategory;
import ruiseki.jfi.jfmuy.ic2.machine.MachineRecipeWrapper;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IDrawableAnimated;
import ruiseki.jfmuy.api.gui.IDrawableStatic;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;

public abstract class MetalFormerCategory extends MachineRecipeCategory<MachineRecipeWrapper> {

    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT = 1;

    private final IDrawableAnimated progressBar;
    private final IDrawableAnimated energyCharge;
    private final String modeName;

    public MetalFormerCategory(IGuiHelper guiHelper, String modeName) {
        super(guiHelper, IC2.textureDomain + ":textures/gui/GUIMetalFormer.png", 5, 6, 146, 70);

        ResourceLocation guiTexture = new ResourceLocation(IC2.textureDomain + ":textures/gui/GUIMetalFormer.png");

        IDrawableStatic progressStatic = guiHelper.createDrawable(guiTexture, 177, 14, 51, 12);
        this.progressBar = guiHelper
            .createAnimatedDrawable(progressStatic, 40, IDrawableAnimated.StartDirection.LEFT, false);

        IDrawableStatic energyStatic = guiHelper.createDrawable(guiTexture, 176, 0, 14, 14);
        this.energyCharge = guiHelper
            .createAnimatedDrawable(energyStatic, 20, IDrawableAnimated.StartDirection.TOP, true);

        this.modeName = modeName;
    }

    protected static void registerClickArea(IModRegistry registry, String categoryUid) {
        registry.addRecipeClickArea(GuiMetalFormer.class, 50, 38, 46, 9, categoryUid);
    }

    protected static List<MachineRecipeWrapper> getRecipesFromManager(IMachineRecipeManager manager) {
        List<MachineRecipeWrapper> recipes = new ArrayList<>();
        if (manager != null && manager.getRecipes() != null) {
            for (Map.Entry<IRecipeInput, RecipeOutput> entry : manager.getRecipes()
                .entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null && !entry.getValue().items.isEmpty()) {
                    recipes.add(new MachineRecipeWrapper(entry.getKey(), entry.getValue()));
                }
            }
        }
        return recipes;
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal("ic2.blockMetalFormer") + " (" + modeName + ")";
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        progressBar.draw(minecraft, 46, 30);
        energyCharge.draw(minecraft, 12, 30);

        String text = "Mode: " + modeName;
        int textX = 73 - (minecraft.fontRenderer.getStringWidth(text) / 2);
        minecraft.fontRenderer.drawString(text, textX, 8, 0x404040);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, MachineRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(INPUT_SLOT, true, 11, 10);
        guiItemStacks.init(OUTPUT_SLOT, false, 110, 28);

        guiItemStacks.set(ingredients);
    }
}
