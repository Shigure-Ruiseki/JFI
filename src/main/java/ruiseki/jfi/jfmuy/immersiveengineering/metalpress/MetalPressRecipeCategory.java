package ruiseki.jfi.jfmuy.immersiveengineering.metalpress;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.apache.logging.log4j.Level;
import org.lwjgl.opengl.GL11;

import blusunrize.immersiveengineering.api.crafting.MetalPressRecipe;
import blusunrize.immersiveengineering.client.ClientUtils;
import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.blocks.metal.BlockMetalMultiblocks;
import blusunrize.immersiveengineering.common.blocks.metal.TileEntityMetalPress;
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

public class MetalPressRecipeCategory implements IRecipeCategory<MetalPressRecipeWrapper> {

    public static final String UID = "immersiveengineering.metalPress";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new MetalPressRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            registry.addRecipes(getRecipes(), UID);

            registry.addRecipeCatalyst(
                new ItemStack(IEContent.blockMetalMultiblocks, 1, BlockMetalMultiblocks.META_metalPress),
                UID);

        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    public static List<MetalPressRecipeWrapper> getRecipes() {
        List<MetalPressRecipeWrapper> recipes = new ArrayList<>();

        if (MetalPressRecipe.recipeList != null) {
            for (MetalPressRecipe recipe : MetalPressRecipe.recipeList.values()) {
                if (recipe != null) {
                    recipes.add(new MetalPressRecipeWrapper(recipe));
                }
            }
        }
        return recipes;
    }

    private static TileEntityMetalPress dummyTile;
    private final IDrawable background;
    private final String title;

    public MetalPressRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(166, 54);
        this.title = StatCollector.translateToLocal("tile.ImmersiveEngineering.metalMultiblock.metalPress.name");
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
        ClientUtils.drawSlot(60, 19, 16, 16);
        ClientUtils.drawSlot(91, 19, 16, 16);
        ClientUtils.drawSlot(123, 19, 20, 20);

        try {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glPushMatrix();

            GL11.glTranslatef(36.0F, 38.0F, 100.0F);
            GL11.glRotatef(-25.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(-120.0F, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(9.0F, -9.0F, 9.0F);

            if (dummyTile == null) {
                dummyTile = new TileEntityMetalPress();
                dummyTile.pos = 4;
                dummyTile.formed = true;
            }

            RenderHelper.enableStandardItemLighting();
            ClientUtils.bindAtlas(0);
            ClientUtils.tes()
                .startDrawingQuads();
            ClientUtils.handleStaticTileRenderer(dummyTile, false);
            ClientUtils.tes()
                .draw();
            TileEntityRendererDispatcher.instance.renderTileEntityAt(dummyTile, 0.0D, 0.0D, 0.0D, 0.0F);
            RenderHelper.disableStandardItemLighting();

            GL11.glPopMatrix();
        } catch (Exception e) {
            // Ignored render exceptions
        }
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, MetalPressRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, true, 59, 18);
        itemStacks.init(1, true, 90, 18);
        itemStacks.init(2, false, 122, 18);

        itemStacks.set(ingredients);
    }
}
