package ruiseki.jfi.jfmuy.harvestcraft;

import com.pam.harvestcraft.AnimalTrapRecipeHandler;
import com.pam.harvestcraft.FishTrapRecipeHandler;
import com.pam.harvestcraft.GardenRecipeHandler;

import cpw.mods.fml.common.Loader;
import ruiseki.jfi.jfmuy.harvestcraft.apiary.ApiaryCategory;
import ruiseki.jfi.jfmuy.harvestcraft.churn.ChurnCategory;
import ruiseki.jfi.jfmuy.harvestcraft.garden.GardenCategory;
import ruiseki.jfi.jfmuy.harvestcraft.oven.OvenCategory;
import ruiseki.jfi.jfmuy.harvestcraft.presser.PresserCategory;
import ruiseki.jfi.jfmuy.harvestcraft.quern.QuernCategory;
import ruiseki.jfi.jfmuy.harvestcraft.trap.AnimalTrapCategory;
import ruiseki.jfi.jfmuy.harvestcraft.trap.FishTrapCategory;
import ruiseki.jfi.jfmuy.nei.RecipeHarvester;
import ruiseki.jfmuy.api.IModPlugin;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.ISubtypeRegistry;
import ruiseki.jfmuy.api.JFMUYPlugin;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;
import tonius.neiintegration.mods.harvestcraft.RecipeHandlerApiary;
import tonius.neiintegration.mods.harvestcraft.RecipeHandlerChurn;
import tonius.neiintegration.mods.harvestcraft.RecipeHandlerOven;
import tonius.neiintegration.mods.harvestcraft.RecipeHandlerPresser;
import tonius.neiintegration.mods.harvestcraft.RecipeHandlerQuern;

@JFMUYPlugin(value = "harvestcraft")
public class HarvestCraftPlugin implements IModPlugin {

    @Override
    public void registerSubtypes(ISubtypeRegistry subtypeRegistry) {
        if (Loader.isModLoaded("NotEnoughItems")) {
            try {
                RecipeHarvester.addBlacklistedClass(AnimalTrapRecipeHandler.class);
                RecipeHarvester.addBlacklistedClass(FishTrapRecipeHandler.class);
                RecipeHarvester.addBlacklistedClass(GardenRecipeHandler.class);
            } catch (Throwable ignore) {}

            if (Loader.isModLoaded("neiintegration")) {
                try {
                    RecipeHarvester.addBlacklistedClass(RecipeHandlerApiary.class);
                    RecipeHarvester.addBlacklistedClass(RecipeHandlerChurn.class);
                    RecipeHarvester.addBlacklistedClass(RecipeHandlerOven.class);
                    RecipeHarvester.addBlacklistedClass(RecipeHandlerPresser.class);
                    RecipeHarvester.addBlacklistedClass(RecipeHandlerQuern.class);
                } catch (Throwable ignore) {}
            }
        }
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        ApiaryCategory.register(registry);
        ChurnCategory.register(registry);
        OvenCategory.register(registry);
        PresserCategory.register(registry);
        QuernCategory.register(registry);
        AnimalTrapCategory.register(registry);
        FishTrapCategory.register(registry);
        GardenCategory.register(registry);
    }

    @Override
    public void register(IModRegistry registry) {
        ApiaryCategory.initialize(registry);
        ChurnCategory.initialize(registry);
        OvenCategory.initialize(registry);
        PresserCategory.initialize(registry);
        QuernCategory.initialize(registry);
        AnimalTrapCategory.initialize(registry);
        FishTrapCategory.initialize(registry);
        GardenCategory.initialize(registry);
    }
}
