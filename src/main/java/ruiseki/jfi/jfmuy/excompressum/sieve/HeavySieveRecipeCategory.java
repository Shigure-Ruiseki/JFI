package ruiseki.jfi.jfmuy.excompressum.sieve;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.blay09.mods.excompressum.ExCompressum;
import net.blay09.mods.excompressum.ModBlocks;
import net.blay09.mods.excompressum.registry.HeavySieveRegistry;
import net.blay09.mods.excompressum.registry.data.ItemAndMetadata;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.Level;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;

import exnihilo.registries.helpers.SiftingResult;
import ruiseki.jfi.JFI;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class HeavySieveRecipeCategory implements IRecipeCategory<HeavySieveRecipeWrapper> {

    public static final String UID = "excompressum.heavySieve";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new HeavySieveRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            registry.addRecipes(getRecipes(), UID);

            for (int i = 0; i < 6; ++i) {
                registry.addRecipeCatalyst(new ItemStack(ModBlocks.heavySieve, 1, i), UID);
            }
            registry.addRecipeCatalyst(new ItemStack(ModBlocks.autoHeavySieve), UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Failed to initialize Heavy Sieve recipes!", t);
        }
    }

    public static List<HeavySieveRecipeWrapper> getRecipes() {
        List<HeavySieveRecipeWrapper> recipes = new ArrayList<>();

        try {
            Multimap<ItemAndMetadata, SiftingResult> siftables = HeavySieveRegistry.getSiftables();

            for (ItemAndMetadata sourceInfo : siftables.keySet()) {
                ItemStack inputStack = sourceInfo.createStack(1);
                Collection<SiftingResult> results = siftables.get(sourceInfo);
                Multiset<ItemAndMetadata> countedSet = HashMultiset.create();

                for (SiftingResult result : results) {
                    countedSet.add(new ItemAndMetadata(result.item, result.meta));
                }

                List<ItemStack> resultStacks = Lists.newArrayList();
                for (ItemAndMetadata resultInfo : countedSet.elementSet()) {
                    resultStacks.add(resultInfo.createStack(countedSet.count(resultInfo)));
                }

                int resultCount = resultStacks.size();
                if (resultCount > 45) {
                    for (int i = 0; i < resultCount; i += 45) {
                        recipes.add(
                            new HeavySieveRecipeWrapper(
                                inputStack,
                                resultStacks.subList(i, Math.min(resultCount, i + 45))));
                    }
                } else {
                    recipes.add(new HeavySieveRecipeWrapper(inputStack, resultStacks));
                }
            }
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Error reading HeavySieve Registry for JFMUY!", t);
        }

        return recipes;
    }

    private final IDrawable background;

    public HeavySieveRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper
            .createDrawable(new ResourceLocation("exnihilo:textures/sieveNEI.png"), 0, 0, 166, 130);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return "Heavy Sieve";
    }

    @Override
    public String getModName() {
        return ExCompressum.MOD_ID;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, HeavySieveRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, true, 73, 3);

        int slotIndex = 1;
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 9; col++) {
                int x = 2 + col * 18;
                int y = 36 + row * 18;
                itemStacks.init(slotIndex, false, x, y);
                slotIndex++;
            }
        }

        itemStacks.set(ingredients);

        itemStacks.addTooltipCallback((slotIdx, isInput, stack, tooltip) -> {
            if (!isInput && stack != null) {
                ItemStack sourceStack = recipeWrapper.getInput();
                if (sourceStack != null) {
                    tooltip.add(EnumChatFormatting.GRAY + "Drop Chance:");

                    Multiset<String> condensedTooltip = HashMultiset.create();
                    Block block = Block.getBlockFromItem(sourceStack.getItem());

                    for (SiftingResult result : HeavySieveRegistry
                        .getSiftingOutput(block, sourceStack.getItemDamage())) {
                        ItemStack resultStack = new ItemStack(result.item, 1, result.meta);
                        if (stack.isItemEqual(resultStack)) {
                            int chance = (int) Math.round(100.0D * (1.0D / (double) result.rarity));
                            condensedTooltip.add(chance + "%");
                        }
                    }

                    for (String line : condensedTooltip.elementSet()) {
                        tooltip.add("  * " + condensedTooltip.count(line) + "x " + line);
                    }
                }
            }
        });
    }
}
