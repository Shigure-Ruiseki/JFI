package ruiseki.jfi.jfmuy.botania;

import static vazkii.botania.common.lib.LibBlockNames.SUBTILE_ORECHID;
import static vazkii.botania.common.lib.LibBlockNames.SUBTILE_ORECHID_IGNEM;
import static vazkii.botania.common.lib.LibBlockNames.SUBTILE_PUREDAISY;

import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import org.jetbrains.annotations.NotNull;

import ruiseki.jfi.jfmuy.botania.brewery.BreweryRecipeCategory;
import ruiseki.jfi.jfmuy.botania.brewery.BreweryRecipeWrapper;
import ruiseki.jfi.jfmuy.botania.brewery.BrewerySubtype;
import ruiseki.jfi.jfmuy.botania.crafting.AncientWillRecipeWrapper;
import ruiseki.jfi.jfmuy.botania.crafting.CompositeLensRecipeWrapper;
import ruiseki.jfi.jfmuy.botania.crafting.SpecialFloatingFlowerMarker;
import ruiseki.jfi.jfmuy.botania.crafting.TerraPickTippingRecipeWrapper;
import ruiseki.jfi.jfmuy.botania.elventrade.ElvenTradeRecipeCategory;
import ruiseki.jfi.jfmuy.botania.elventrade.ElvenTradeRecipeWrapper;
import ruiseki.jfi.jfmuy.botania.lexica.LexicaBotaniaCategory;
import ruiseki.jfi.jfmuy.botania.lexica.LexicaBotaniaMarker;
import ruiseki.jfi.jfmuy.botania.manapool.ManaPoolRecipeCategory;
import ruiseki.jfi.jfmuy.botania.manapool.ManaPoolRecipeWrapper;
import ruiseki.jfi.jfmuy.botania.orechid.OrechidIgnemRecipeCategory;
import ruiseki.jfi.jfmuy.botania.orechid.OrechidIgnemRecipeWrapper;
import ruiseki.jfi.jfmuy.botania.orechid.OrechidRecipeCategory;
import ruiseki.jfi.jfmuy.botania.orechid.OrechidRecipeWrapper;
import ruiseki.jfi.jfmuy.botania.petalapothecary.PetalApothecaryRecipeCategory;
import ruiseki.jfi.jfmuy.botania.petalapothecary.PetalApothecaryRecipeWrapper;
import ruiseki.jfi.jfmuy.botania.puredaisy.PureDaisyRecipeCategory;
import ruiseki.jfi.jfmuy.botania.puredaisy.PureDaisyRecipeWrapper;
import ruiseki.jfi.jfmuy.botania.runicaltar.RunicAltarRecipeCategory;
import ruiseki.jfi.jfmuy.botania.runicaltar.RunicAltarRecipeWrapper;
import ruiseki.jfmuy.api.ICollapsibleGroupRegistry;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IJFMUYRuntime;
import ruiseki.jfmuy.api.IModPlugin;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.IRecipeRegistry;
import ruiseki.jfmuy.api.ISubtypeRegistry;
import ruiseki.jfmuy.api.JFMUYPlugin;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;
import ruiseki.jfmuy.api.recipe.VanillaRecipeCategoryUid;
import ruiseki.jfmuy.ingredients.group.CollapsibleGroupRegistry;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeBrew;
import vazkii.botania.api.recipe.RecipeElvenTrade;
import vazkii.botania.api.recipe.RecipeManaInfusion;
import vazkii.botania.api.recipe.RecipePetals;
import vazkii.botania.api.recipe.RecipePureDaisy;
import vazkii.botania.api.recipe.RecipeRuneAltar;
import vazkii.botania.client.gui.crafting.ContainerCraftingHalo;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.crafting.recipe.AncientWillRecipe;
import vazkii.botania.common.crafting.recipe.CompositeLensRecipe;
import vazkii.botania.common.crafting.recipe.TerraPickTippingRecipe;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;
import vazkii.botania.common.item.brew.ItemBrewFlask;
import vazkii.botania.common.item.brew.ItemBrewVial;

@JFMUYPlugin
public class BotaniaPlugin implements IModPlugin {

    @Override
    public void registerSubtypes(@NotNull ISubtypeRegistry subtypeRegistry) {
        subtypeRegistry.registerSubtypeInterpreter(
            Item.getItemFromBlock(ModBlocks.specialFlower),
            ItemBlockSpecialFlower::getType);
        subtypeRegistry.registerSubtypeInterpreter(
            Item.getItemFromBlock(ModBlocks.floatingSpecialFlower),
            ItemBlockSpecialFlower::getType);
        subtypeRegistry.registerSubtypeInterpreter(ModItems.brewVial, BrewerySubtype.INSTANCE);
        subtypeRegistry.registerSubtypeInterpreter(ModItems.brewFlask, BrewerySubtype.INSTANCE);
        subtypeRegistry.registerSubtypeInterpreter(ModItems.incenseStick, BrewerySubtype.INSTANCE);
        subtypeRegistry.registerSubtypeInterpreter(ModItems.bloodPendant, BrewerySubtype.INSTANCE);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jfmuyHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jfmuyHelpers.getGuiHelper();
        registry.addRecipeCategories(
            new BreweryRecipeCategory(guiHelper),
            new PureDaisyRecipeCategory(guiHelper),
            new RunicAltarRecipeCategory(guiHelper), // Runic must come before petals. See williewillus/Botania#172
            new PetalApothecaryRecipeCategory(guiHelper),
            new ElvenTradeRecipeCategory(guiHelper),
            new ManaPoolRecipeCategory(guiHelper),
            new OrechidRecipeCategory(guiHelper),
            new OrechidIgnemRecipeCategory(guiHelper),
            new LexicaBotaniaCategory(guiHelper));
    }

    public static boolean doesOreExist(String key) {
        return OreDictionary.doesOreNameExist(key);
    }

    @Override
    public void register(IModRegistry registry) {
        registry.handleRecipes(RecipeBrew.class, BreweryRecipeWrapper::new, BreweryRecipeCategory.UID);
        registry.handleRecipes(RecipePureDaisy.class, PureDaisyRecipeWrapper::new, PureDaisyRecipeCategory.UID);
        registry.handleRecipes(RecipeRuneAltar.class, RunicAltarRecipeWrapper::new, RunicAltarRecipeCategory.UID); // Runic
                                                                                                                   // must
                                                                                                                   // come
                                                                                                                   // before
                                                                                                                   // petals.
                                                                                                                   // See
                                                                                                                   // williewillus/Botania#172
        registry
            .handleRecipes(RecipePetals.class, PetalApothecaryRecipeWrapper::new, PetalApothecaryRecipeCategory.UID);
        registry.handleRecipes(RecipeElvenTrade.class, ElvenTradeRecipeWrapper::new, ElvenTradeRecipeCategory.UID);
        registry.handleRecipes(RecipeManaInfusion.class, ManaPoolRecipeWrapper::new, ManaPoolRecipeCategory.UID);

        registry.addRecipes(SpecialFloatingFlowerMarker.getValidRecipes(), VanillaRecipeCategoryUid.CRAFTING);
        registry
            .handleRecipes(AncientWillRecipe.class, AncientWillRecipeWrapper::new, VanillaRecipeCategoryUid.CRAFTING);
        registry.handleRecipes(
            TerraPickTippingRecipe.class,
            TerraPickTippingRecipeWrapper::new,
            VanillaRecipeCategoryUid.CRAFTING);
        registry.handleRecipes(
            CompositeLensRecipe.class,
            r -> new CompositeLensRecipeWrapper(),
            VanillaRecipeCategoryUid.CRAFTING);

        registry.addRecipes(BotaniaAPI.brewRecipes, BreweryRecipeCategory.UID);
        registry.addRecipes(BotaniaAPI.pureDaisyRecipes, PureDaisyRecipeCategory.UID);
        registry.addRecipes(BotaniaAPI.petalRecipes, PetalApothecaryRecipeCategory.UID);
        registry.addRecipes(BotaniaAPI.elvenTradeRecipes, ElvenTradeRecipeCategory.UID);
        registry.addRecipes(BotaniaAPI.runeAltarRecipes, RunicAltarRecipeCategory.UID);
        registry.addRecipes(BotaniaAPI.manaInfusionRecipes, ManaPoolRecipeCategory.UID);

        registry.addRecipes(
            BotaniaAPI.oreWeights.entrySet()
                .stream()
                .filter(e -> doesOreExist(e.getKey()))
                .map(OrechidRecipeWrapper::new)
                .sorted()
                .collect(Collectors.toList()),
            OrechidRecipeCategory.UID);

        registry.addRecipes(
            BotaniaAPI.oreWeightsNether.entrySet()
                .stream()
                .filter(e -> doesOreExist(e.getKey()))
                .map(OrechidIgnemRecipeWrapper::new)
                .sorted()
                .collect(Collectors.toList()),
            OrechidIgnemRecipeCategory.UID);

        registry.addRecipes(LexicaBotaniaMarker.getValidRecipes(), LexicaBotaniaCategory.UID);

        registry.addRecipeCatalyst(new ItemStack(ModBlocks.brewery), BreweryRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.alfPortal), ElvenTradeRecipeCategory.UID);

        for (int i = 0; i < 4; i++) {
            registry.addRecipeCatalyst(new ItemStack(ModBlocks.pool, 1, i), ManaPoolRecipeCategory.UID);
        }

        for (int i = 0; i < 9; i++) {
            registry.addRecipeCatalyst(new ItemStack(ModBlocks.altar, 1, i), PetalApothecaryRecipeCategory.UID);
        }

        registry.addRecipeCatalyst(ItemBlockSpecialFlower.ofType(SUBTILE_ORECHID), OrechidRecipeCategory.UID);
        registry.addRecipeCatalyst(
            ItemBlockSpecialFlower.ofType(new ItemStack(ModBlocks.floatingSpecialFlower), SUBTILE_ORECHID),
            OrechidRecipeCategory.UID);
        registry
            .addRecipeCatalyst(ItemBlockSpecialFlower.ofType(SUBTILE_ORECHID_IGNEM), OrechidIgnemRecipeCategory.UID);
        registry.addRecipeCatalyst(
            ItemBlockSpecialFlower.ofType(new ItemStack(ModBlocks.floatingSpecialFlower), SUBTILE_ORECHID_IGNEM),
            OrechidIgnemRecipeCategory.UID);
        registry.addRecipeCatalyst(ItemBlockSpecialFlower.ofType(SUBTILE_PUREDAISY), PureDaisyRecipeCategory.UID);
        registry.addRecipeCatalyst(
            ItemBlockSpecialFlower.ofType(new ItemStack(ModBlocks.floatingSpecialFlower), SUBTILE_PUREDAISY),
            PureDaisyRecipeCategory.UID);

        registry.addRecipeCatalyst(new ItemStack(ModBlocks.runeAltar), RunicAltarRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModItems.autocraftingHalo), VanillaRecipeCategoryUid.CRAFTING);
        registry.addRecipeCatalyst(new ItemStack(ModItems.craftingHalo), VanillaRecipeCategoryUid.CRAFTING);

        registry.addRecipeCatalyst(new ItemStack(ModItems.lexicon), LexicaBotaniaCategory.UID);

        registry.getRecipeTransferRegistry()
            .addRecipeTransferHandler(ContainerCraftingHalo.class, VanillaRecipeCategoryUid.CRAFTING, 1, 9, 10, 36);
    }

    @Override
    public void registerCollapsibleGroups(ICollapsibleGroupRegistry r) {
        CollapsibleGroupRegistry registry = (CollapsibleGroupRegistry) r;
        registry.defaultNewGroup("botania_vial", "Vial Potions")
            .addAny(VanillaTypes.ITEM, stack -> stack.getItem() instanceof ItemBrewVial)
            .build();
        registry.defaultNewGroup("botania_flask", "Flask Potions")
            .addAny(VanillaTypes.ITEM, stack -> stack.getItem() instanceof ItemBrewFlask)
            .build();
    }

    @Override
    public void onRuntimeAvailable(IJFMUYRuntime JFMUYRuntime) {
        IRecipeRegistry recipeRegistry = JFMUYRuntime.getRecipeRegistry();
        for (RecipeElvenTrade recipe : BotaniaAPI.elvenTradeRecipes) {
            List<Object> inputs = recipe.getInputs();
            ItemStack output = recipe.getOutput();
            if (inputs.size() == 1 && output != null
                && inputs.getFirst() instanceof ItemStack
                && ItemStack.areItemStacksEqual((ItemStack) inputs.getFirst(), output)) {
                IRecipeWrapper wrapper = recipeRegistry.getRecipeWrapper(recipe, ElvenTradeRecipeCategory.UID);
                if (wrapper != null) {
                    recipeRegistry.hideRecipe(wrapper, ElvenTradeRecipeCategory.UID);
                }
            }
        }
    }
}
