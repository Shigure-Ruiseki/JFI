package ruiseki.jfi.jfmuy.ic2.machine.blastfurnace;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.apache.logging.log4j.Level;

import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import ic2.api.recipe.Recipes;
import ic2.core.IC2;
import ic2.core.Ic2Items;
import ic2.core.block.machine.gui.GuiBlastFurnace;
import ruiseki.jfi.JFI;
import ruiseki.jfi.jfmuy.ic2.machine.MachineRecipeCategory;
import ruiseki.jfi.jfmuy.ic2.machine.MachineRecipeWrapper;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IDrawableAnimated;
import ruiseki.jfmuy.api.gui.IDrawableStatic;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class BlastFurnaceCategory extends MachineRecipeCategory<MachineRecipeWrapper> {

    public static final String UID = "ic2.blastfurnace";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new BlastFurnaceCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            registry.addRecipes(getRecipes(), UID);
            registry.addRecipeClickArea(GuiBlastFurnace.class, 41, 4, 80, 42, UID);
            registry.addRecipeCatalyst(Ic2Items.blastfurnace, UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Failed to initialize Blast Furnace recipe category!", t);
        }
    }

    public static List<MachineRecipeWrapper> getRecipes() {
        List<MachineRecipeWrapper> recipes = new ArrayList<>();
        if (Recipes.blastfurance != null && Recipes.blastfurance.getRecipes() != null) {
            for (Map.Entry<IRecipeInput, RecipeOutput> entry : Recipes.blastfurance.getRecipes()
                .entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null && !entry.getValue().items.isEmpty()) {
                    recipes.add(new MachineRecipeWrapper(entry.getKey(), entry.getValue()));
                }
            }
        }
        return recipes;
    }

    private final IDrawableAnimated customProgressBar;
    private final IDrawableStatic heatIndicator;
    private final IDrawableStatic airBar;
    private final RenderItem itemRenderer = new RenderItem();

    public BlastFurnaceCategory(IGuiHelper guiHelper) {
        super(guiHelper, IC2.textureDomain + ":textures/gui/GUIBlastFurnace.png", 15, 18, 155, 65);

        ResourceLocation location = new ResourceLocation(IC2.textureDomain + ":textures/gui/GUIBlastFurnace.png");

        IDrawableStatic progressDrawable = guiHelper.createDrawable(location, 176, 51, 27, 27);
        this.customProgressBar = guiHelper
            .createAnimatedDrawable(progressDrawable, 20, IDrawableAnimated.StartDirection.BOTTOM, false);

        this.heatIndicator = guiHelper.createDrawable(location, 176, 8, 14, 14);
        this.airBar = guiHelper.createDrawable(location, 176, 0, 23, 8);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StatCollector.canTranslate("ic2.blockBlastFurnace")
            ? StatCollector.translateToLocal("ic2.blockBlastFurnace")
            : "Blast Furnace";
    }

    @Override
    protected int getInputPosX() {
        return 19;
    }

    @Override
    protected int getInputPosY() {
        return 14;
    }

    @Override
    protected int getOutputPosX() {
        return 118;
    }

    @Override
    protected int getOutputPosY() {
        return 37;
    }

    @Override
    protected boolean isOutputsVertical() {
        return false;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        this.customProgressBar.draw(minecraft, 60, 16);
        this.heatIndicator.draw(minecraft, 80, 48);
        this.airBar.draw(minecraft, 55, 51);

        if (Ic2Items.airCell != null) {
            RenderHelper.enableGUIStandardItemLighting();
            this.itemRenderer
                .renderItemIntoGUI(minecraft.fontRenderer, minecraft.getTextureManager(), Ic2Items.airCell, 11, 38); // 15
                                                                                                                     // -
                                                                                                                     // 15,
                                                                                                                     // 38
                                                                                                                     // -
                                                                                                                     // 18
            RenderHelper.disableStandardItemLighting();
        }
    }
}
