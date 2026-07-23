package ruiseki.jfi.jfmuy.ic2.machine.macerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;

import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import ic2.api.recipe.Recipes;
import ic2.core.IC2;
import ic2.core.Ic2Items;
import ic2.core.block.machine.gui.GuiMacerator;
import ruiseki.jfi.JFI;
import ruiseki.jfi.jfmuy.ic2.machine.MachineRecipeCategory;
import ruiseki.jfi.jfmuy.ic2.machine.MachineRecipeWrapper;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class MaceratorCategory extends MachineRecipeCategory<MachineRecipeWrapper> {

    public static final String UID = "ic2.macerator";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new MaceratorCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            registry.addRecipes(getRecipes(), UID);
            registry.addRecipeClickArea(GuiMacerator.class, 74, 30, 25, 16, UID);
            registry.addRecipeCatalyst(Ic2Items.macerator, UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    public static List<MachineRecipeWrapper> getRecipes() {
        List<MachineRecipeWrapper> recipes = new ArrayList<>();
        if (Recipes.macerator != null && Recipes.macerator.getRecipes() != null) {
            for (Map.Entry<IRecipeInput, RecipeOutput> entry : Recipes.macerator.getRecipes()
                .entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null && !entry.getValue().items.isEmpty()) {
                    recipes.add(new MachineRecipeWrapper(entry.getKey(), entry.getValue()));
                }
            }
        }
        return recipes;
    }

    public MaceratorCategory(IGuiHelper guiHelper) {
        super(guiHelper, IC2.textureDomain + ":textures/gui/GUIMacerator.png");
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return "Macerator";
    }
}
