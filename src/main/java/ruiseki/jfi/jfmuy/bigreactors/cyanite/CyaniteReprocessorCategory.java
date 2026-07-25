package ruiseki.jfi.jfmuy.bigreactors.cyanite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.relauncher.ReflectionHelper;
import erogenousbeef.bigreactors.api.data.OreDictToReactantMapping;
import erogenousbeef.bigreactors.api.data.ReactantData;
import erogenousbeef.bigreactors.api.registry.Reactants;
import erogenousbeef.bigreactors.common.BigReactors;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IDrawableAnimated;
import ruiseki.jfmuy.api.gui.IGuiFluidStackGroup;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class CyaniteReprocessorCategory implements IRecipeCategory<CyaniteReprocessorWrapper> {

    public static final String UID = "bigreactors.cyaniteReprocessor";

    public static void register(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(
            new CyaniteReprocessorCategory(
                registry.getJFMUYHelpers()
                    .getGuiHelper()));
    }

    public static void initialize(IModRegistry registry) {
        if (BigReactors.blockDevice != null) {
            registry.addRecipeCatalyst(new ItemStack(BigReactors.blockDevice, 1, 0), CyaniteReprocessorCategory.UID);
        }

        List<CyaniteReprocessorWrapper> recipes = new ArrayList<>();

        List<ItemStack> blutoniums = OreDictionary.getOres("ingotBlutonium");
        if (blutoniums == null || blutoniums.isEmpty()) {
            return;
        }
        ItemStack blutoniumOutput = blutoniums.get(0);

        try {
            Map<String, OreDictToReactantMapping> solidToReactant = ReflectionHelper
                .getPrivateValue(Reactants.class, null, "_solidToReactant");

            if (solidToReactant != null) {
                for (OreDictToReactantMapping mapping : solidToReactant.values()) {
                    ReactantData data = Reactants.getReactant(mapping.getProduct());
                    if (data != null && data.isWaste()) {
                        List<ItemStack> ores = OreDictionary.getOres(mapping.getSource());
                        if (ores != null && !ores.isEmpty()) {
                            recipes.add(new CyaniteReprocessorWrapper(ores, blutoniumOutput));
                        }
                    }
                }
            }
        } catch (Exception ignored) {}

        registry.addRecipes(recipes, CyaniteReprocessorCategory.UID);
    }

    private final IDrawable icon;
    private final IDrawable background;
    private final IDrawable progressBar;

    public CyaniteReprocessorCategory(IGuiHelper guiHelper) {
        if (BigReactors.blockDevice != null) {
            this.icon = guiHelper.createDrawableIngredient(new ItemStack(BigReactors.blockDevice, 1, 0));
        } else {
            this.icon = null;
        }

        this.background = guiHelper
            .createDrawable(new ResourceLocation("bigreactors:textures/gui/CyaniteReprocessor.png"), 8, 16, 160, 65);

        this.progressBar = guiHelper.createAnimatedDrawable(
            guiHelper.createDrawable(
                new ResourceLocation("bigreactors:textures/gui/CyaniteReprocessor.png"),
                0,
                176,
                27,
                18),
            200,
            IDrawableAnimated.StartDirection.LEFT,
            false);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal("tile.blockBRDevice.0.name");
    }

    @Override
    public String getModName() {
        return BigReactors.NAME;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        this.progressBar.draw(minecraft, 69, 24);
        minecraft.fontRenderer.drawString("2000 RF", 64, 49, 0x808080);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CyaniteReprocessorWrapper recipeWrapper,
        IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();

        fluidStacks.init(0, true, 9, 1, 16, 62, 5000, true, null);

        itemStacks.init(0, true, 35, 24);

        itemStacks.init(1, false, 107, 24);

        itemStacks.set(ingredients);
        fluidStacks.set(ingredients);
    }
}
