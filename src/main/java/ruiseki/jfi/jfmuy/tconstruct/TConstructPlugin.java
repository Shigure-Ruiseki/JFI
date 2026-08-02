package ruiseki.jfi.jfmuy.tconstruct;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.Loader;
import ruiseki.jfi.jfmuy.tconstruct.alloying.AlloyingRecipeCategory;
import ruiseki.jfi.jfmuy.tconstruct.casting.CastingRecipeCategory;
import ruiseki.jfi.jfmuy.tconstruct.dryingrack.DryingRackRecipeCategory;
import ruiseki.jfi.jfmuy.tconstruct.interpreter.PatternSubtypeInterpreter;
import ruiseki.jfi.jfmuy.tconstruct.interpreter.ToolPartSubtypeInterpreter;
import ruiseki.jfi.jfmuy.tconstruct.interpreter.ToolSubtypeInterpreter;
import ruiseki.jfi.jfmuy.tconstruct.melting.MeltingRecipeCategory;
import ruiseki.jfi.jfmuy.tconstruct.tools.ToolMaterialsRecipeCategory;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModPlugin;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.ISubtypeRegistry;
import ruiseki.jfmuy.api.JFMUYPlugin;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;
import ruiseki.jfmuy.api.recipe.VanillaRecipeCategoryUid;
import ruiseki.jfmuy.plugins.nei.RecipeHarvester;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.tools.ToolCore;
import tconstruct.library.util.IToolPart;
import tconstruct.plugins.nei.RecipeHandlerAlloying;
import tconstruct.plugins.nei.RecipeHandlerCastingBasin;
import tconstruct.plugins.nei.RecipeHandlerCastingTable;
import tconstruct.plugins.nei.RecipeHandlerDryingRack;
import tconstruct.plugins.nei.RecipeHandlerMelting;
import tconstruct.plugins.nei.RecipeHandlerToolMaterials;
import tconstruct.smeltery.TinkerSmeltery;
import tconstruct.tools.TinkerTools;

@JFMUYPlugin(value = "TConstruct")
public class TConstructPlugin implements IModPlugin {

    public static IJFMUYHelpers jeiHelpers;

    @Override
    public void registerSubtypes(ISubtypeRegistry registry) {
        if (Loader.isModLoaded("NotEnoughItems")) {
            try {
                RecipeHarvester.addBlacklistedClass(RecipeHandlerDryingRack.class);
                RecipeHarvester.addBlacklistedClass(RecipeHandlerToolMaterials.class);
                RecipeHarvester.addBlacklistedClass(RecipeHandlerMelting.class);
                RecipeHarvester.addBlacklistedClass(RecipeHandlerAlloying.class);
                RecipeHarvester.addBlacklistedClass(RecipeHandlerCastingTable.class);
                RecipeHarvester.addBlacklistedClass(RecipeHandlerCastingBasin.class);
            } catch (Throwable ignore) {}
        }

        // tool parts
        ToolPartSubtypeInterpreter toolPartInterpreter = new ToolPartSubtypeInterpreter();
        for (Item item : TConstructRegistry.itemDirectory.values()) {
            if (item instanceof IToolPart) {
                registry.registerSubtypeInterpreter(item, toolPartInterpreter);
            }
        }

        // tool
        ToolSubtypeInterpreter toolInterpreter = new ToolSubtypeInterpreter();
        for (ToolCore tool : TConstructRegistry.tools) {
            registry.registerSubtypeInterpreter(tool, toolInterpreter);
        }

        PatternSubtypeInterpreter patternInterpreter = new PatternSubtypeInterpreter();
        registry.registerSubtypeInterpreter(TinkerTools.woodPattern, patternInterpreter);
        registry.registerSubtypeInterpreter(TinkerSmeltery.metalPattern, patternInterpreter);
        registry.registerSubtypeInterpreter(TinkerSmeltery.clayPattern, patternInterpreter);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        AlloyingRecipeCategory.register(registry);
        CastingRecipeCategory.register(registry);
        DryingRackRecipeCategory.register(registry);
        MeltingRecipeCategory.register(registry);
        ToolMaterialsRecipeCategory.register(registry);
    }

    @Override
    public void register(IModRegistry registry) {
        AlloyingRecipeCategory.initialize(registry);
        CastingRecipeCategory.initialize(registry);
        DryingRackRecipeCategory.initialize(registry);
        MeltingRecipeCategory.initialize(registry);
        ToolMaterialsRecipeCategory.initialize(registry);

        registry.addRecipeCatalyst(new ItemStack(TinkerTools.craftingStationWood), VanillaRecipeCategoryUid.CRAFTING);
        registry.getRecipeTransferRegistry()
            .addRecipeTransferHandler(new CraftingStationRecipeTransferInfo());
    }
}
