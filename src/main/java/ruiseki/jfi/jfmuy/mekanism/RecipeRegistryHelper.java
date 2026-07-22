package ruiseki.jfi.jfmuy.mekanism;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.item.ItemStack;

import mekanism.api.gas.Gas;
import mekanism.api.gas.GasRegistry;
import mekanism.client.gui.GuiChemicalCrystallizer;
import mekanism.client.gui.GuiChemicalDissolutionChamber;
import mekanism.client.gui.GuiChemicalInfuser;
import mekanism.client.gui.GuiChemicalInjectionChamber;
import mekanism.client.gui.GuiChemicalOxidizer;
import mekanism.client.gui.GuiChemicalWasher;
import mekanism.client.gui.GuiCombiner;
import mekanism.client.gui.GuiCrusher;
import mekanism.client.gui.GuiElectrolyticSeparator;
import mekanism.client.gui.GuiEnergizedSmelter;
import mekanism.client.gui.GuiEnrichmentChamber;
import mekanism.client.gui.GuiMetallurgicInfuser;
import mekanism.client.gui.GuiOsmiumCompressor;
import mekanism.client.gui.GuiPRC;
import mekanism.client.gui.GuiPrecisionSawmill;
import mekanism.client.gui.GuiPurificationChamber;
import mekanism.client.gui.GuiRotaryCondensentrator;
import mekanism.client.gui.GuiSolarNeutronActivator;
import mekanism.client.gui.GuiThermalEvaporationController;
import mekanism.common.Tier;
import mekanism.common.base.IFactory;
import mekanism.common.block.BlockBasic;
import mekanism.common.block.BlockMachine.MachineType;
import mekanism.common.inventory.container.ContainerFormulaicAssemblicator;
import mekanism.common.recipe.RecipeHandler.Recipe;
import mekanism.common.recipe.machines.MachineRecipe;
import mekanism.common.recipe.machines.SmeltingRecipe;
import mekanism.common.util.MekanismUtils;
import ruiseki.jfi.jfmuy.mekanism.machine.AdvancedMachineRecipeWrapper;
import ruiseki.jfi.jfmuy.mekanism.machine.ChanceMachineRecipeWrapper;
import ruiseki.jfi.jfmuy.mekanism.machine.MachineRecipeWrapper;
import ruiseki.jfi.jfmuy.mekanism.machine.chemical.ChemicalCrystallizerRecipeWrapper;
import ruiseki.jfi.jfmuy.mekanism.machine.chemical.ChemicalDissolutionChamberRecipeWrapper;
import ruiseki.jfi.jfmuy.mekanism.machine.chemical.ChemicalInfuserRecipeWrapper;
import ruiseki.jfi.jfmuy.mekanism.machine.chemical.ChemicalOxidizerRecipeWrapper;
import ruiseki.jfi.jfmuy.mekanism.machine.chemical.ChemicalWasherRecipeWrapper;
import ruiseki.jfi.jfmuy.mekanism.machine.other.ElectrolyticSeparatorRecipeWrapper;
import ruiseki.jfi.jfmuy.mekanism.machine.other.MetallurgicInfuserRecipeWrapper;
import ruiseki.jfi.jfmuy.mekanism.machine.other.PRCRecipeWrapper;
import ruiseki.jfi.jfmuy.mekanism.machine.other.RotaryCondensentratorRecipeWrapper;
import ruiseki.jfi.jfmuy.mekanism.machine.other.SolarNeutronRecipeWrapper;
import ruiseki.jfi.jfmuy.mekanism.machine.other.ThermalEvaporationRecipeWrapper;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;
import ruiseki.jfmuy.api.recipe.IRecipeWrapperFactory;
import ruiseki.jfmuy.api.recipe.VanillaRecipeCategoryUid;

public class RecipeRegistryHelper {

    public static void registerEnrichmentChamber(IModRegistry registry) {
        if (!MachineType.ENRICHMENT_CHAMBER.isEnabled()) {
            return;
        }
        addRecipes(registry, Recipe.ENRICHMENT_CHAMBER, MachineRecipeWrapper::new);
        registry
            .addRecipeClickArea(GuiEnrichmentChamber.class, 79, 40, 24, 7, Recipe.ENRICHMENT_CHAMBER.getRecipeName());
        registerRecipeItem(registry, MachineType.ENRICHMENT_CHAMBER, Recipe.ENRICHMENT_CHAMBER);
    }

    public static void registerCrusher(IModRegistry registry) {
        if (!MachineType.CRUSHER.isEnabled()) {
            return;
        }
        addRecipes(registry, Recipe.CRUSHER, MachineRecipeWrapper::new);
        registry.addRecipeClickArea(GuiCrusher.class, 79, 40, 24, 7, Recipe.CRUSHER.getRecipeName());
        registerRecipeItem(registry, MachineType.CRUSHER, Recipe.CRUSHER);
    }

    public static void registerCombiner(IModRegistry registry) {
        if (!MachineType.COMBINER.isEnabled()) {
            return;
        }
        addRecipes(registry, Recipe.COMBINER, AdvancedMachineRecipeWrapper::new);
        registry.addRecipeClickArea(GuiCombiner.class, 79, 40, 24, 7, Recipe.COMBINER.getRecipeName());
        registerRecipeItem(registry, MachineType.COMBINER, Recipe.COMBINER);
    }

    public static void registerPurification(IModRegistry registry) {
        if (!MachineType.PURIFICATION_CHAMBER.isEnabled()) {
            return;
        }
        addRecipes(registry, Recipe.PURIFICATION_CHAMBER, AdvancedMachineRecipeWrapper::new);
        registry.addRecipeClickArea(
            GuiPurificationChamber.class,
            79,
            40,
            24,
            7,
            Recipe.PURIFICATION_CHAMBER.getRecipeName());
        registerRecipeItem(registry, MachineType.PURIFICATION_CHAMBER, Recipe.PURIFICATION_CHAMBER);
    }

    public static void registerCompressor(IModRegistry registry) {
        if (!MachineType.OSMIUM_COMPRESSOR.isEnabled()) {
            return;
        }
        addRecipes(registry, Recipe.OSMIUM_COMPRESSOR, AdvancedMachineRecipeWrapper::new);
        registry.addRecipeClickArea(GuiOsmiumCompressor.class, 79, 40, 24, 7, Recipe.OSMIUM_COMPRESSOR.getRecipeName());
        registerRecipeItem(registry, MachineType.OSMIUM_COMPRESSOR, Recipe.OSMIUM_COMPRESSOR);
    }

    public static void registerInjection(IModRegistry registry) {
        if (!MachineType.CHEMICAL_INJECTION_CHAMBER.isEnabled()) {
            return;
        }
        addRecipes(registry, Recipe.CHEMICAL_INJECTION_CHAMBER, AdvancedMachineRecipeWrapper::new);
        registry.addRecipeClickArea(
            GuiChemicalInjectionChamber.class,
            79,
            40,
            24,
            7,
            Recipe.CHEMICAL_INJECTION_CHAMBER.getRecipeName());
        registerRecipeItem(registry, MachineType.CHEMICAL_INJECTION_CHAMBER, Recipe.CHEMICAL_INJECTION_CHAMBER);
    }

    public static void registerSawmill(IModRegistry registry) {
        if (!MachineType.PRECISION_SAWMILL.isEnabled()) {
            return;
        }
        addRecipes(registry, Recipe.PRECISION_SAWMILL, ChanceMachineRecipeWrapper::new);
        registry.addRecipeClickArea(GuiPrecisionSawmill.class, 79, 40, 24, 7, Recipe.PRECISION_SAWMILL.getRecipeName());
        registerRecipeItem(registry, MachineType.PRECISION_SAWMILL, Recipe.PRECISION_SAWMILL);
    }

    public static void registerMetallurgicInfuser(IModRegistry registry) {
        if (!MachineType.METALLURGIC_INFUSER.isEnabled()) {
            return;
        }
        addRecipes(registry, Recipe.METALLURGIC_INFUSER, MetallurgicInfuserRecipeWrapper::new);
        registry
            .addRecipeClickArea(GuiMetallurgicInfuser.class, 72, 47, 32, 8, Recipe.METALLURGIC_INFUSER.getRecipeName());
        registerRecipeItem(registry, MachineType.METALLURGIC_INFUSER, Recipe.METALLURGIC_INFUSER);
    }

    public static void registerCrystallizer(IModRegistry registry) {
        if (!MachineType.CHEMICAL_CRYSTALLIZER.isEnabled()) {
            return;
        }
        addRecipes(registry, Recipe.CHEMICAL_CRYSTALLIZER, ChemicalCrystallizerRecipeWrapper::new);
        registry.addRecipeClickArea(
            GuiChemicalCrystallizer.class,
            53,
            62,
            48,
            8,
            Recipe.CHEMICAL_CRYSTALLIZER.getRecipeName());
        registerRecipeItem(registry, MachineType.CHEMICAL_CRYSTALLIZER, Recipe.CHEMICAL_CRYSTALLIZER);
    }

    public static void registerDissolution(IModRegistry registry) {
        if (!MachineType.CHEMICAL_DISSOLUTION_CHAMBER.isEnabled()) {
            return;
        }
        addRecipes(registry, Recipe.CHEMICAL_DISSOLUTION_CHAMBER, ChemicalDissolutionChamberRecipeWrapper::new);
        registry.addRecipeClickArea(
            GuiChemicalDissolutionChamber.class,
            64,
            40,
            48,
            8,
            Recipe.CHEMICAL_DISSOLUTION_CHAMBER.getRecipeName());
        registerRecipeItem(registry, MachineType.CHEMICAL_DISSOLUTION_CHAMBER, Recipe.CHEMICAL_DISSOLUTION_CHAMBER);
    }

    public static void registerChemicalInfuser(IModRegistry registry) {
        if (!MachineType.CHEMICAL_INFUSER.isEnabled()) {
            return;
        }
        addRecipes(registry, Recipe.CHEMICAL_INFUSER, ChemicalInfuserRecipeWrapper::new);
        registry.addRecipeClickArea(GuiChemicalInfuser.class, 47, 39, 28, 8, Recipe.CHEMICAL_INFUSER.getRecipeName());
        registry.addRecipeClickArea(GuiChemicalInfuser.class, 101, 39, 28, 8, Recipe.CHEMICAL_INFUSER.getRecipeName());
        registerRecipeItem(registry, MachineType.CHEMICAL_INFUSER, Recipe.CHEMICAL_INFUSER);
    }

    public static void registerOxidizer(IModRegistry registry) {
        if (!MachineType.CHEMICAL_OXIDIZER.isEnabled()) {
            return;
        }
        addRecipes(registry, Recipe.CHEMICAL_OXIDIZER, ChemicalOxidizerRecipeWrapper::new);
        registry.addRecipeClickArea(GuiChemicalOxidizer.class, 64, 40, 48, 8, Recipe.CHEMICAL_OXIDIZER.getRecipeName());
        registerRecipeItem(registry, MachineType.CHEMICAL_OXIDIZER, Recipe.CHEMICAL_OXIDIZER);
    }

    public static void registerWasher(IModRegistry registry) {
        if (!MachineType.CHEMICAL_WASHER.isEnabled()) {
            return;
        }
        addRecipes(registry, Recipe.CHEMICAL_WASHER, ChemicalWasherRecipeWrapper::new);
        registry.addRecipeClickArea(GuiChemicalWasher.class, 61, 39, 55, 8, Recipe.CHEMICAL_WASHER.getRecipeName());
        registerRecipeItem(registry, MachineType.CHEMICAL_WASHER, Recipe.CHEMICAL_WASHER);
    }

    public static void registerNeutronActivator(IModRegistry registry) {
        if (!MachineType.SOLAR_NEUTRON_ACTIVATOR.isEnabled()) {
            return;
        }
        addRecipes(registry, Recipe.SOLAR_NEUTRON_ACTIVATOR, SolarNeutronRecipeWrapper::new);
        registry.addRecipeClickArea(
            GuiSolarNeutronActivator.class,
            64,
            39,
            48,
            8,
            Recipe.SOLAR_NEUTRON_ACTIVATOR.getRecipeName());
        registerRecipeItem(registry, MachineType.SOLAR_NEUTRON_ACTIVATOR, Recipe.SOLAR_NEUTRON_ACTIVATOR);
    }

    public static void registerSeparator(IModRegistry registry) {
        if (!MachineType.ELECTROLYTIC_SEPARATOR.isEnabled()) {
            return;
        }
        addRecipes(registry, Recipe.ELECTROLYTIC_SEPARATOR, ElectrolyticSeparatorRecipeWrapper::new);
        registry.addRecipeClickArea(
            GuiElectrolyticSeparator.class,
            80,
            30,
            16,
            6,
            Recipe.ELECTROLYTIC_SEPARATOR.getRecipeName());
        registerRecipeItem(registry, MachineType.ELECTROLYTIC_SEPARATOR, Recipe.ELECTROLYTIC_SEPARATOR);
    }

    public static void registerEvaporationPlant(IModRegistry registry) {
        addRecipes(registry, Recipe.THERMAL_EVAPORATION_PLANT, ThermalEvaporationRecipeWrapper::new);
        registry.addRecipeClickArea(
            GuiThermalEvaporationController.class,
            49,
            20,
            78,
            38,
            Recipe.THERMAL_EVAPORATION_PLANT.getRecipeName());
        registry.addRecipeCatalyst(
            BlockBasic.BasicType.THERMAL_EVAPORATION_CONTROLLER.getStack(),
            Recipe.THERMAL_EVAPORATION_PLANT.getRecipeName());
    }

    public static void registerReactionChamber(IModRegistry registry) {
        if (!MachineType.PRESSURIZED_REACTION_CHAMBER.isEnabled()) {
            return;
        }
        addRecipes(registry, Recipe.PRESSURIZED_REACTION_CHAMBER, PRCRecipeWrapper::new);
        registry.addRecipeClickArea(GuiPRC.class, 75, 37, 36, 10, Recipe.PRESSURIZED_REACTION_CHAMBER.getRecipeName());
        registerRecipeItem(registry, MachineType.PRESSURIZED_REACTION_CHAMBER, Recipe.PRESSURIZED_REACTION_CHAMBER);
    }

    public static void registerCondensentrator(IModRegistry registry) {
        if (!MachineType.ROTARY_CONDENSENTRATOR.isEnabled()) {
            return;
        }
        List<RotaryCondensentratorRecipeWrapper> condensentratorRecipes = new ArrayList<>();
        List<RotaryCondensentratorRecipeWrapper> decondensentratorRecipes = new ArrayList<>();
        for (Gas gas : GasRegistry.getRegisteredGasses()) {
            if (gas.hasFluid()) {
                condensentratorRecipes.add(new RotaryCondensentratorRecipeWrapper(gas.getFluid(), gas, true));
                decondensentratorRecipes.add(new RotaryCondensentratorRecipeWrapper(gas.getFluid(), gas, false));
            }
        }
        String condensentrating = "mekanism.rotary_condensentrator_condensentrating";
        String decondensentrating = "mekanism.rotary_condensentrator_decondensentrating";
        registry.addRecipes(condensentratorRecipes, condensentrating);
        registry.addRecipes(decondensentratorRecipes, decondensentrating);
        registry
            .addRecipeClickArea(GuiRotaryCondensentrator.class, 64, 39, 48, 8, condensentrating, decondensentrating);
        registry.addRecipeCatalyst(MachineType.ROTARY_CONDENSENTRATOR.getStack(), condensentrating, decondensentrating);
    }

    public static void registerSmelter(IModRegistry registry) {
        if (!MachineType.ENERGIZED_SMELTER.isEnabled()) {
            return;
        }

        registry
            .handleRecipes(SmeltingRecipe.class, MachineRecipeWrapper::new, Recipe.ENERGIZED_SMELTER.getRecipeName());

        Collection<SmeltingRecipe> recipeList = Recipe.ENERGIZED_SMELTER.get()
            .values();
        List<MachineRecipeWrapper> wrappers = recipeList.stream()
            .map(MachineRecipeWrapper::new)
            .collect(Collectors.toList());

        registry.addRecipes(wrappers, Recipe.ENERGIZED_SMELTER.getRecipeName());

        registry.addRecipeClickArea(
            GuiEnergizedSmelter.class,
            79,
            40,
            24,
            7,
            Recipe.ENERGIZED_SMELTER.getRecipeName(),
            VanillaRecipeCategoryUid.SMELTING);

        registerVanillaSmeltingRecipeCatalyst(registry);

        registerRecipeItem(registry, MachineType.ENERGIZED_SMELTER, Recipe.ENERGIZED_SMELTER);
    }

    public static void registerFormulaicAssemblicator(IModRegistry registry) {
        if (!MachineType.FORMULAIC_ASSEMBLICATOR.isEnabled()) {
            return;
        }
        registry.addRecipeCatalyst(MachineType.FORMULAIC_ASSEMBLICATOR.getStack(), VanillaRecipeCategoryUid.CRAFTING);
        registry.getRecipeTransferRegistry()
            .addRecipeTransferHandler(
                ContainerFormulaicAssemblicator.class,
                VanillaRecipeCategoryUid.CRAFTING,
                20,
                9,
                35,
                36);
    }

    private static void registerVanillaSmeltingRecipeCatalyst(IModRegistry registry) {
        registry.addRecipeCatalyst(MachineType.ENERGIZED_SMELTER.getStack(), VanillaRecipeCategoryUid.SMELTING);
        for (Tier.FactoryTier tier : Tier.FactoryTier.values()) {
            ItemStack factoryStack = MekanismUtils.getFactory(tier, IFactory.RecipeType.SMELTING);
            registry.addRecipeCatalyst(factoryStack, VanillaRecipeCategoryUid.SMELTING);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static <RECIPE extends MachineRecipe> void addRecipes(IModRegistry registry, Recipe type,
        IRecipeWrapperFactory<RECIPE> factory) {
        String recipeCategoryUid = type.getRecipeName();
        Class recipeClass = null;

        for (java.lang.reflect.Field field : Recipe.class.getDeclaredFields()) {
            if (Class.class.isAssignableFrom(field.getType())) {
                try {
                    field.setAccessible(true);
                    Object val = field.get(type);
                    if (val instanceof Class && MachineRecipe.class.isAssignableFrom((Class<?>) val)) {
                        recipeClass = (Class) val;
                        break;
                    }
                } catch (Exception ignored) {}
            }
        }

        if (recipeClass != null) {
            registry.handleRecipes(recipeClass, factory, recipeCategoryUid);

            List<IRecipeWrapper> wrappers = new ArrayList<>();
            for (Object recipeObj : type.get()
                .values()) {
                if (recipeObj instanceof MachineRecipe) {
                    wrappers.add(factory.getRecipeWrapper((RECIPE) recipeObj));
                }
            }

            registry.addRecipes(wrappers, recipeCategoryUid);
        } else {
            System.err.println("[JFI Error] can't find recipeClass for Recipe: " + recipeCategoryUid);
        }
    }

    private static void registerRecipeItem(IModRegistry registry, MachineType type, Recipe recipe) {
        registry.addRecipeCatalyst(type.getStack(), recipe.getRecipeName());

        IFactory.RecipeType factoryType = null;
        for (IFactory.RecipeType t : IFactory.RecipeType.values()) {
            if (t.getStack() != null && t.getStack()
                .isItemEqual(type.getStack())) {
                factoryType = t;
                break;
            }
        }

        if (factoryType != null) {
            for (Tier.FactoryTier tier : Tier.FactoryTier.values()) {
                ItemStack factoryStack = MekanismUtils.getFactory(tier, factoryType);
                registry.addRecipeCatalyst(factoryStack, recipe.getRecipeName());
            }
        }
    }
}
