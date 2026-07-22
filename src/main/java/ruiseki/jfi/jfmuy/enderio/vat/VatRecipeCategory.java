package ruiseki.jfi.jfmuy.enderio.vat;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.apache.logging.log4j.Level;

import crazypants.enderio.EnderIO;
import crazypants.enderio.fluid.Fluids;
import crazypants.enderio.machine.recipe.IRecipe;
import crazypants.enderio.machine.vat.VatRecipeManager;
import ruiseki.jfi.JFI;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IDrawableAnimated;
import ruiseki.jfmuy.api.gui.IGuiFluidStackGroup;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class VatRecipeCategory implements IRecipeCategory<VatRecipeWrapper> {

    public static final String UID = "EnderIOVat";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new VatRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
            IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

            registry.addRecipes(getRecipes(), UID);
            registry.addRecipeCatalyst(new ItemStack(EnderIO.blockVat), UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    public static List<IRecipeWrapper> getRecipes() {
        List<IRecipeWrapper> recipes = new ArrayList<>();

        for (IRecipe recipe : VatRecipeManager.getInstance()
            .getRecipes()) {
            if (recipe != null) {
                recipes.add(new VatRecipeWrapper(recipe));
            }
        }

        return recipes;
    }

    private final IDrawable background;
    private final IDrawableAnimated progressBar;

    public VatRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation backgroundLocation = new ResourceLocation("enderio:textures/gui/23/vat.png");
        this.background = guiHelper.createDrawable(backgroundLocation, 27, 11, 128, 70);
        this.progressBar = guiHelper.createAnimatedDrawable(
            guiHelper.createDrawable(new ResourceLocation("enderio:textures/gui/23/vat.png"), 176, 0, 22, 13),
            200,
            IDrawableAnimated.StartDirection.BOTTOM,
            false);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal("enderio.nei.vat");
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
        progressBar.draw(minecraft, 55, 53);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, final VatRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();

        guiItemStacks.init(0, true, 28, 0);
        guiItemStacks.init(1, true, 77, 0);

        fluidStacks.init(2, true, 3, 1, 15, 47, 8000, false, null);
        fluidStacks.init(3, false, 105, 1, 15, 47, 8000, false, null);

        guiItemStacks.set(ingredients);
        fluidStacks.set(ingredients);

        fluidStacks.addTooltipCallback((slotIndex, input, fluidStack, tooltip) -> {
            if (fluidStack == null || fluidStack.getFluid() == null) {
                return;
            }

            Minecraft mc = Minecraft.getMinecraft();
            int ticks = (int) (mc.theWorld.getTotalWorldTime() / 30);

            int idx1 = !recipeWrapper.getSlot1Multipliers()
                .isEmpty() ? ticks
                    % recipeWrapper.getSlot1Multipliers()
                        .size()
                    : 0;
            int idx2 = !recipeWrapper.getSlot2Multipliers()
                .isEmpty() ? ticks
                    % recipeWrapper.getSlot2Multipliers()
                        .size()
                    : 0;

            tooltip.clear();
            tooltip.add(
                fluidStack.getFluid()
                    .getLocalizedName(fluidStack));

            if (slotIndex == 2) { // Input Tank
                int amount = recipeWrapper.getCurrentInputFluidAmount(idx1, idx2);
                tooltip.add(EnumChatFormatting.GRAY.toString() + amount + " " + Fluids.MB());
            } else if (slotIndex == 3) { // Output Tank
                int amount = recipeWrapper.getCurrentResultFluidAmount(idx1, idx2);
                tooltip.add(EnumChatFormatting.GRAY.toString() + amount + " " + Fluids.MB());
            }
        });
    }
}
