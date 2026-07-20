package ruiseki.jfi.jfmuy.immersiveengineering.bottling;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.apache.logging.log4j.Level;
import org.lwjgl.opengl.GL11;

import blusunrize.immersiveengineering.api.crafting.BottlingMachineRecipe;
import blusunrize.immersiveengineering.client.ClientUtils;
import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.blocks.metal.TileEntityBottlingMachine;
import ruiseki.jfi.JFI;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IDrawableAnimated;
import ruiseki.jfmuy.api.gui.IDrawableStatic;
import ruiseki.jfmuy.api.gui.IGuiFluidStackGroup;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class BottlingRecipeCategory implements IRecipeCategory<BottlingRecipeWrapper> {

    public static final String UID = "immersiveengineering.bottling_machine";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new BottlingRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
            IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

            registry.addRecipes(getRecipes(guiHelper), UID);

            registry.addRecipeCatalyst(new ItemStack(IEContent.blockMetalMultiblocks, 1, 0), UID);

        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    public static List<BottlingRecipeWrapper> getRecipes(IGuiHelper guiHelper) {
        List<BottlingRecipeWrapper> recipes = new ArrayList<>();
        for (BottlingMachineRecipe recipe : BottlingMachineRecipe.recipeList) {
            if (recipe != null) {
                recipes.add(new BottlingRecipeWrapper(recipe));
            }
        }
        return recipes;
    }

    private final IDrawable background;
    private final IDrawable tankOverlay;
    private final IDrawableAnimated progressBar;
    private final String title;

    public BottlingRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation guiTex = new ResourceLocation("immersiveengineering", "textures/gui/fluidProducer.png");
        ResourceLocation furnaceTex = new ResourceLocation("textures/gui/container/furnace.png");

        this.background = guiHelper.createDrawable(guiTex, 0, 0, 176, 68);

        this.tankOverlay = guiHelper.createDrawable(guiTex, 179, 33, 16, 47);

        IDrawableStatic staticProgress = guiHelper.createDrawable(furnaceTex, 179, 14, 22, 16);
        this.progressBar = guiHelper
            .createAnimatedDrawable(staticProgress, 30, IDrawableAnimated.StartDirection.LEFT, false);

        this.title = StatCollector.translateToLocal("tile.ImmersiveEngineering.metalMultiblock.bottlingMachine.name");
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
    public void drawExtras(Minecraft minecraft) {
        progressBar.draw(minecraft, 74, 8);

        GL11.glPushMatrix();
        GL11.glColor4f(0.5F, 0.5F, 0.5F, 1.0F);
        tankOverlay.draw(minecraft, 15, 7);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef(89.0F, 50.0F, 100.0F);
        GL11.glRotatef(-45.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
        GL11.glScalef(12.0F, -12.0F, 12.0F);

        TileEntityBottlingMachine tile = new TileEntityBottlingMachine();
        tile.pos = 4;
        tile.formed = true;

        ClientUtils.bindAtlas(0);
        ClientUtils.tes()
            .startDrawingQuads();
        ClientUtils.handleStaticTileRenderer(tile, false);
        ClientUtils.tes()
            .draw();
        TileEntityRendererDispatcher.instance.renderTileEntityAt(tile, 0.0D, 0.0D, 0.0D, 0.0F);
        GL11.glPopMatrix();
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, BottlingRecipeWrapper recipeWrapper,
        IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();

        itemStacks.init(0, true, 45, 7);
        itemStacks.init(1, false, 106, 7);

        fluidStacks.init(0, true, 16, 8, 16, 48, 16000, true, null);

        itemStacks.set(ingredients);
        fluidStacks.set(ingredients);
    }
}
