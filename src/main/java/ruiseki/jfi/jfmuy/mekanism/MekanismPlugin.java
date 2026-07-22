package ruiseki.jfi.jfmuy.mekanism;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import mekanism.api.gas.Gas;
import mekanism.api.gas.GasRegistry;
import mekanism.api.gas.GasStack;
import mekanism.client.gui.element.GuiProgress.ProgressBar;
import mekanism.common.MekanismBlocks;
import mekanism.common.MekanismItems;
import mekanism.common.base.IFactory;
import mekanism.common.base.ITierItem;
import mekanism.common.block.BlockMachine.MachineType;
import mekanism.common.inventory.container.ContainerRobitInventory;
import mekanism.common.item.ItemBlockEnergyCube;
import mekanism.common.item.ItemBlockGasTank;
import mekanism.common.recipe.RecipeHandler.Recipe;
import ruiseki.jfi.jfmuy.mekanism.gas.GasStackHelper;
import ruiseki.jfi.jfmuy.mekanism.gas.GasStackRenderer;
import ruiseki.jfi.jfmuy.mekanism.machine.AdvancedMachineRecipeCategory;
import ruiseki.jfi.jfmuy.mekanism.machine.ChanceMachineRecipeCategory;
import ruiseki.jfi.jfmuy.mekanism.machine.MachineRecipeCategory;
import ruiseki.jfi.jfmuy.mekanism.machine.chemical.ChemicalCrystallizerRecipeCategory;
import ruiseki.jfi.jfmuy.mekanism.machine.chemical.ChemicalDissolutionChamberRecipeCategory;
import ruiseki.jfi.jfmuy.mekanism.machine.chemical.ChemicalInfuserRecipeCategory;
import ruiseki.jfi.jfmuy.mekanism.machine.chemical.ChemicalOxidizerRecipeCategory;
import ruiseki.jfi.jfmuy.mekanism.machine.chemical.ChemicalWasherRecipeCategory;
import ruiseki.jfi.jfmuy.mekanism.machine.other.ElectrolyticSeparatorRecipeCategory;
import ruiseki.jfi.jfmuy.mekanism.machine.other.MetallurgicInfuserRecipeCategory;
import ruiseki.jfi.jfmuy.mekanism.machine.other.PRCRecipeCategory;
import ruiseki.jfi.jfmuy.mekanism.machine.other.RotaryCondensentratorRecipeCategory;
import ruiseki.jfi.jfmuy.mekanism.machine.other.SolarNeutronRecipeCategory;
import ruiseki.jfi.jfmuy.mekanism.machine.other.ThermalEvaporationRecipeCategory;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IModPlugin;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.ISubtypeRegistry;
import ruiseki.jfmuy.api.JFMUYPlugin;
import ruiseki.jfmuy.api.ingredients.IIngredientBlacklist;
import ruiseki.jfmuy.api.ingredients.IModIngredientRegistration;
import ruiseki.jfmuy.api.recipe.IIngredientType;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;
import ruiseki.jfmuy.api.recipe.VanillaRecipeCategoryUid;
import ruiseki.okcore.fluid.FluidHelpers;

@JFMUYPlugin("Mekanism")
public class MekanismPlugin implements IModPlugin {

    public static final IIngredientType<GasStack> TYPE_GAS = () -> GasStack.class;

    public static final ISubtypeRegistry.ISubtypeInterpreter NBT_INTERPRETER = itemStack -> {
        if (itemStack == null || itemStack.getItem() == null) {
            return "";
        }

        String ret = Integer.toString(itemStack.getItemDamage());

        if (itemStack.getItem() instanceof ITierItem) {
            ret += ":" + ((ITierItem) itemStack.getItem()).getBaseTier(itemStack)
                .getName();
        }

        if (itemStack.getItem() instanceof IFactory) {
            int recipeTypeOrdinal = ((IFactory) itemStack.getItem()).getRecipeType(itemStack);
            IFactory.RecipeType[] values = IFactory.RecipeType.values();
            if (recipeTypeOrdinal >= 0 && recipeTypeOrdinal < values.length) {
                IFactory.RecipeType recipeType = values[recipeTypeOrdinal];
                ret += ":" + recipeType.getUnlocalizedName();
            }
        }

        if (itemStack.getItem() instanceof ItemBlockGasTank) {
            GasStack gasStack = ((ItemBlockGasTank) itemStack.getItem()).getGas(itemStack);
            if (gasStack != null && gasStack.getGas() != null) {
                ret += ":" + gasStack.getGas()
                    .getName();
            }
        }

        if (itemStack.getItem() instanceof ItemBlockEnergyCube) {
            ret += ":" + (((ItemBlockEnergyCube) itemStack.getItem()).getEnergy(itemStack) > 0 ? "filled" : "empty");
        }

        return ret.toLowerCase(Locale.ROOT);
    };

    @Override
    public void registerSubtypes(ISubtypeRegistry registry) {
        registry.registerSubtypeInterpreter(Item.getItemFromBlock(MekanismBlocks.EnergyCube), NBT_INTERPRETER);
        registry.registerSubtypeInterpreter(Item.getItemFromBlock(MekanismBlocks.MachineBlock), NBT_INTERPRETER);
        registry.registerSubtypeInterpreter(Item.getItemFromBlock(MekanismBlocks.MachineBlock2), NBT_INTERPRETER);
        registry.registerSubtypeInterpreter(Item.getItemFromBlock(MekanismBlocks.MachineBlock3), NBT_INTERPRETER);
        registry.registerSubtypeInterpreter(Item.getItemFromBlock(MekanismBlocks.BasicBlock), NBT_INTERPRETER);
        registry.registerSubtypeInterpreter(Item.getItemFromBlock(MekanismBlocks.BasicBlock2), NBT_INTERPRETER);
        registry.registerSubtypeInterpreter(Item.getItemFromBlock(MekanismBlocks.GasTank), NBT_INTERPRETER);
        registry.registerSubtypeInterpreter(Item.getItemFromBlock(MekanismBlocks.CardboardBox), NBT_INTERPRETER);
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registry) {
        List<GasStack> list = GasRegistry.getRegisteredGasses()
            .stream()
            .filter(Gas::isVisible)
            .map(g -> new GasStack(g, FluidHelpers.BUCKET_VOLUME))
            .collect(Collectors.toList());
        registry.register(MekanismPlugin.TYPE_GAS, list, new GasStackHelper(), new GasStackRenderer());
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper guiHelper = registry.getJFMUYHelpers()
            .getGuiHelper();

        addRecipeCategory(
            registry,
            MachineType.CHEMICAL_CRYSTALLIZER,
            new ChemicalCrystallizerRecipeCategory(guiHelper));
        addRecipeCategory(
            registry,
            MachineType.CHEMICAL_DISSOLUTION_CHAMBER,
            new ChemicalDissolutionChamberRecipeCategory(guiHelper));
        addRecipeCategory(registry, MachineType.CHEMICAL_INFUSER, new ChemicalInfuserRecipeCategory(guiHelper));
        addRecipeCategory(registry, MachineType.CHEMICAL_OXIDIZER, new ChemicalOxidizerRecipeCategory(guiHelper));
        addRecipeCategory(registry, MachineType.CHEMICAL_WASHER, new ChemicalWasherRecipeCategory(guiHelper));
        addRecipeCategory(
            registry,
            MachineType.ELECTROLYTIC_SEPARATOR,
            new ElectrolyticSeparatorRecipeCategory(guiHelper));
        addRecipeCategory(registry, MachineType.METALLURGIC_INFUSER, new MetallurgicInfuserRecipeCategory(guiHelper));
        addRecipeCategory(registry, MachineType.PRESSURIZED_REACTION_CHAMBER, new PRCRecipeCategory(guiHelper));

        addRecipeCategory(
            registry,
            MachineType.ROTARY_CONDENSENTRATOR,
            new RotaryCondensentratorRecipeCategory(guiHelper, true));
        addRecipeCategory(
            registry,
            MachineType.ROTARY_CONDENSENTRATOR,
            new RotaryCondensentratorRecipeCategory(guiHelper, false));

        addRecipeCategory(registry, MachineType.SOLAR_NEUTRON_ACTIVATOR, new SolarNeutronRecipeCategory(guiHelper));

        addRecipeCategory(
            registry,
            MachineType.COMBINER,
            new AdvancedMachineRecipeCategory(
                guiHelper,
                Recipe.COMBINER.getRecipeName(),
                "tile.MachineBlock.Combiner.name",
                ProgressBar.STONE));

        addRecipeCategory(
            registry,
            MachineType.PURIFICATION_CHAMBER,
            new AdvancedMachineRecipeCategory(
                guiHelper,
                Recipe.PURIFICATION_CHAMBER.getRecipeName(),
                "tile.MachineBlock.PurificationChamber.name",
                ProgressBar.RED));
        addRecipeCategory(
            registry,
            MachineType.OSMIUM_COMPRESSOR,
            new AdvancedMachineRecipeCategory(
                guiHelper,
                Recipe.OSMIUM_COMPRESSOR.getRecipeName(),
                "tile.MachineBlock.OsmiumCompressor.name",
                ProgressBar.RED));
        addRecipeCategory(
            registry,
            MachineType.CHEMICAL_INJECTION_CHAMBER,
            new AdvancedMachineRecipeCategory(
                guiHelper,
                Recipe.CHEMICAL_INJECTION_CHAMBER.getRecipeName(),
                "tile.MachineBlock2.ChemicalInjectionChamber.name",
                ProgressBar.YELLOW));

        addRecipeCategory(
            registry,
            MachineType.PRECISION_SAWMILL,
            new ChanceMachineRecipeCategory(
                guiHelper,
                Recipe.PRECISION_SAWMILL.getRecipeName(),
                "tile.MachineBlock2.PrecisionSawmill.name",
                ProgressBar.PURPLE));

        addRecipeCategory(
            registry,
            MachineType.ENRICHMENT_CHAMBER,
            new MachineRecipeCategory(
                guiHelper,
                Recipe.ENRICHMENT_CHAMBER.getRecipeName(),
                "tile.MachineBlock.EnrichmentChamber.name",
                ProgressBar.BLUE));
        addRecipeCategory(
            registry,
            MachineType.CRUSHER,
            new MachineRecipeCategory(
                guiHelper,
                Recipe.CRUSHER.getRecipeName(),
                "tile.MachineBlock.Crusher.name",
                ProgressBar.CRUSH));
        addRecipeCategory(
            registry,
            MachineType.ENERGIZED_SMELTER,
            new MachineRecipeCategory(
                guiHelper,
                Recipe.ENERGIZED_SMELTER.getRecipeName(),
                "tile.MachineBlock.EnergizedSmelter.name",
                ProgressBar.BLUE));

        // There is no config option to disable the thermal evaporation plant
        registry.addRecipeCategories(new ThermalEvaporationRecipeCategory(guiHelper));
    }

    private void addRecipeCategory(IRecipeCategoryRegistration registry, MachineType type,
        BaseRecipeCategory category) {
        if (type.isEnabled()) {
            registry.addRecipeCategories(category);
        }
    }

    @Override
    public void register(IModRegistry registry) {

        registry.addAdvancedGuiHandlers(new GuiElementHandler());

        // Blacklist
        IIngredientBlacklist ingredientBlacklist = registry.getJFMUYHelpers()
            .getIngredientBlacklist();
        ingredientBlacklist.addIngredientToBlacklist(new ItemStack(MekanismItems.ItemProxy));
        ingredientBlacklist.addIngredientToBlacklist(new ItemStack(MekanismBlocks.BoundingBlock));

        // Register the recipes and their catalysts if enabled
        RecipeRegistryHelper.registerEnrichmentChamber(registry);
        RecipeRegistryHelper.registerCrusher(registry);
        RecipeRegistryHelper.registerCombiner(registry);
        RecipeRegistryHelper.registerPurification(registry);
        RecipeRegistryHelper.registerCompressor(registry);
        RecipeRegistryHelper.registerInjection(registry);
        RecipeRegistryHelper.registerSawmill(registry);
        RecipeRegistryHelper.registerMetallurgicInfuser(registry);
        RecipeRegistryHelper.registerCrystallizer(registry);
        RecipeRegistryHelper.registerDissolution(registry);
        RecipeRegistryHelper.registerChemicalInfuser(registry);
        RecipeRegistryHelper.registerOxidizer(registry);
        RecipeRegistryHelper.registerWasher(registry);
        RecipeRegistryHelper.registerNeutronActivator(registry);
        RecipeRegistryHelper.registerSeparator(registry);
        RecipeRegistryHelper.registerEvaporationPlant(registry);
        RecipeRegistryHelper.registerReactionChamber(registry);
        RecipeRegistryHelper.registerCondensentrator(registry);
        RecipeRegistryHelper.registerSmelter(registry);
        RecipeRegistryHelper.registerFormulaicAssemblicator(registry);
        registry.getRecipeTransferRegistry()
            .addRecipeTransferHandler(ContainerRobitInventory.class, VanillaRecipeCategoryUid.CRAFTING, 1, 9, 10, 36);
    }
}
