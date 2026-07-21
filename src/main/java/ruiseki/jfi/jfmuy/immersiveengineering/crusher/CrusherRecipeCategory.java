package ruiseki.jfi.jfmuy.immersiveengineering.crusher;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.apache.logging.log4j.Level;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import blusunrize.immersiveengineering.api.crafting.CrusherRecipe;
import blusunrize.immersiveengineering.client.ClientUtils;
import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.blocks.metal.BlockMetalMultiblocks;
import blusunrize.immersiveengineering.common.blocks.metal.TileEntityCrusher;
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

public class CrusherRecipeCategory implements IRecipeCategory<CrusherRecipeWrapper> {

    public static final String UID = "immersiveengineering.crusher";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new CrusherRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
            IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

            registry.addRecipes(getRecipes(), UID);
            registry.addRecipeCatalyst(
                new ItemStack(IEContent.blockMetalMultiblocks, 1, BlockMetalMultiblocks.META_crusher),
                UID);

        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    public static List<CrusherRecipeWrapper> getRecipes() {
        List<CrusherRecipeWrapper> recipes = new ArrayList<>();
        for (CrusherRecipe recipe : CrusherRecipe.recipeList) {
            if (recipe != null && recipe.output != null) {
                recipes.add(new CrusherRecipeWrapper(recipe));
            }
        }
        return recipes;
    }

    private static TileEntityCrusher dummyTile;
    private final IDrawable background;
    private final IDrawable slotDrawable;
    private final String title;

    public CrusherRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(166, 60);
        this.slotDrawable = guiHelper.getSlotDrawable();
        this.title = StatCollector.translateToLocal("tile.ImmersiveEngineering.metalMultiblock.crusher.name");
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
        GL11.glPushMatrix();

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager()
            .bindTexture(new ResourceLocation("textures/gui/container/furnace.png"));
        GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
        minecraft.currentScreen.drawTexturedModalRect(18, -85, 82, 35, 20, 15);
        GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);

        GL11.glTranslatef(40.0F, 40.0F, 100.0F);
        GL11.glRotatef(-15.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(200.0F, 0.0F, 1.0F, 0.0F);
        GL11.glScalef(10.0F, -10.0F, 10.0F);

        RenderHelper.enableGUIStandardItemLighting();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);

        if (dummyTile == null) {
            dummyTile = new TileEntityCrusher();
            dummyTile.pos = 17;
            dummyTile.formed = true;
        }

        ClientUtils.bindAtlas(0);
        ClientUtils.tes()
            .startDrawingQuads();
        ClientUtils.handleStaticTileRenderer(dummyTile, false);
        ClientUtils.tes()
            .draw();
        TileEntityRendererDispatcher.instance.renderTileEntityAt(dummyTile, 0.0D, 0.0D, 0.0D, 0.0F);

        RenderHelper.disableStandardItemLighting();
        GL11.glPopMatrix();
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CrusherRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, true, 70, 0);
        itemStacks.setBackground(0, slotDrawable);

        itemStacks.init(1, false, 70, 40);
        itemStacks.setBackground(1, slotDrawable);

        int secondaryCount = recipeWrapper.getSecondaryOutputsCount();
        for (int i = 0; i < secondaryCount; i++) {
            int slotIndex = 2 + i;
            int posX = 90 + i * 18;
            itemStacks.init(slotIndex, false, posX, 40);
            itemStacks.setBackground(slotIndex, slotDrawable);
        }

        itemStacks.set(ingredients);
    }
}
