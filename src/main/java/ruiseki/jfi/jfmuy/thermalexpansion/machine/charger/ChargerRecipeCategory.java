package ruiseki.jfi.jfmuy.thermalexpansion.machine.charger;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.Level;

import cofh.api.energy.IEnergyContainerItem;
import cofh.lib.inventory.ComparableItemStackSafe;
import cofh.lib.util.helpers.StringHelper;
import cofh.thermalexpansion.block.machine.BlockMachine;
import cofh.thermalexpansion.gui.client.machine.GuiCharger;
import cofh.thermalexpansion.util.crafting.ChargerManager;
import ruiseki.jfi.JFI;
import ruiseki.jfi.jfmuy.thermalexpansion.Drawables;
import ruiseki.jfi.jfmuy.thermalexpansion.RecipeUidsTE;
import ruiseki.jfi.jfmuy.thermalexpansion.machine.BaseRecipeCategory;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IDrawableStatic;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;
import ruiseki.okcore.datastructure.NonNullList;

public class ChargerRecipeCategory extends BaseRecipeCategory<ChargerRecipeWrapper> {

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new ChargerRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
            IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

            registry.addRecipes(getRecipes(guiHelper), RecipeUidsTE.CHARGER);
            registry.addRecipeClickArea(GuiCharger.class, 79, 53, 18, 16, RecipeUidsTE.CHARGER);
            registry.addRecipeCatalyst(BlockMachine.charger, RecipeUidsTE.CHARGER);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    public static List<ChargerRecipeWrapper> getRecipes(IGuiHelper guiHelper) {
        List<ChargerRecipeWrapper> recipes = new ArrayList<>();

        for (ChargerManager.RecipeCharger recipe : ChargerManager.getRecipeList()) {
            if (recipe != null) {
                recipes.add(new ChargerRecipeWrapper(guiHelper, recipe));
            }
        }

        HashSet<ComparableItemStackSafe> processedStacks = new HashSet<>();

        for (Object itemObj : Item.itemRegistry) {
            if (itemObj instanceof IEnergyContainerItem) {
                Item item = (Item) itemObj;
                try {
                    NonNullList<ItemStack> list = NonNullList.create();

                    CreativeTabs tab = item.getCreativeTab();
                    if (tab == null) {
                        tab = CreativeTabs.tabAllSearch;
                    }

                    item.getSubItems(item, tab, list);

                    for (ItemStack chargable : list) {
                        if (chargable == null) continue;

                        IEnergyContainerItem energyContainerItem = (IEnergyContainerItem) item;
                        int maxEnergyStored = energyContainerItem.getMaxEnergyStored(chargable);

                        if (maxEnergyStored > 0
                            && energyContainerItem.receiveEnergy(chargable, Integer.MAX_VALUE, true) > 0) {

                            ItemStack input = chargable.copy();
                            ItemStack output = chargable.copy();

                            energyContainerItem.extractEnergy(input, Integer.MAX_VALUE, false);
                            energyContainerItem.receiveEnergy(output, Integer.MAX_VALUE, false);

                            if (!processedStacks.add(new ComparableItemStackSafe(input))) {
                                continue;
                            }

                            try {
                                Constructor<ChargerManager.RecipeCharger> constructor = ChargerManager.RecipeCharger.class
                                    .getDeclaredConstructor(ItemStack.class, ItemStack.class, int.class);

                                constructor.setAccessible(true);

                                ChargerManager.RecipeCharger recipe = constructor
                                    .newInstance(input, output, maxEnergyStored);
                                recipes.add(new ChargerRecipeWrapper(guiHelper, recipe));
                            } catch (Exception e) {
                                JFI.okLog(
                                    Level.ERROR,
                                    "Failed to instantiate RecipeCharger via reflection for item: "
                                        + item.getUnlocalizedName(),
                                    e);
                            }
                        }
                    }
                } catch (Throwable t) {
                    JFI.okLog(
                        Level.ERROR,
                        "Exception thrown while getting Charger recipes for item: " + item.getUnlocalizedName(),
                        t);
                }
            }
        }
        return recipes;
    }

    protected IDrawableStatic progress;

    public ChargerRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper
            .drawableBuilder(new ResourceLocation("thermalexpansion:textures/gui/machine/Charger.png"), 62, 11, 88, 62)
            .addPadding(0, 0, 16, 40)
            .build();
        energyMeter = Drawables.getDrawables(guiHelper)
            .getEnergyEmpty();
        localizedName = StringHelper.localize("tile.thermalexpansion.machine.charger.name");
        progress = Drawables.getDrawables(guiHelper)
            .getScale(Drawables.SCALE_FLUX);
    }

    @Nonnull
    @Override
    public String getUid() {
        return RecipeUidsTE.CHARGER;
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {
        progress.draw(minecraft, 34, 43);
        energyMeter.draw(minecraft, 2, 8);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, ChargerRecipeWrapper recipeWrapper, IIngredients ingredients) {
        List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
        List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);

        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(0, true, 33, 23);
        guiItemStacks.init(1, false, 78, 23);

        guiItemStacks.set(0, inputs.get(0));
        guiItemStacks.set(1, outputs.get(0));
    }
}
