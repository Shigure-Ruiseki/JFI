package ruiseki.jfi.jfmuy.excompressum.hammer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.blay09.mods.excompressum.ExCompressum;
import net.blay09.mods.excompressum.ModBlocks;
import net.blay09.mods.excompressum.ModItems;
import net.blay09.mods.excompressum.registry.CompressedHammerRegistry;
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

import exnihilo.registries.helpers.Smashable;
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

public class CompressedHammerRecipeCategory implements IRecipeCategory<CompressedHammerRecipeWrapper> {

    public static final String UID = "excompressum.compressedHammer";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new CompressedHammerRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            registry.addRecipes(getRecipes(), UID);

            registry.addRecipeCatalyst(new ItemStack(ModItems.compressedHammerWood), UID);
            registry.addRecipeCatalyst(new ItemStack(ModItems.compressedHammerStone), UID);
            registry.addRecipeCatalyst(new ItemStack(ModItems.compressedHammerIron), UID);
            registry.addRecipeCatalyst(new ItemStack(ModItems.compressedHammerGold), UID);
            registry.addRecipeCatalyst(new ItemStack(ModItems.compressedHammerDiamond), UID);
            registry.addRecipeCatalyst(new ItemStack(ModBlocks.autoCompressedHammer), UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Failed to initialize Compressed Hammer recipes!", t);
        }
    }

    public static List<CompressedHammerRecipeWrapper> getRecipes() {
        List<CompressedHammerRecipeWrapper> recipes = new ArrayList<>();

        try {
            Multimap<ItemAndMetadata, Smashable> smashables = CompressedHammerRegistry.getSmashables();

            for (ItemAndMetadata sourceInfo : smashables.keySet()) {
                ItemStack inputStack = sourceInfo.createStack(1);
                Collection<Smashable> results = smashables.get(sourceInfo);
                Multiset<ItemAndMetadata> countedSet = HashMultiset.create();

                for (Smashable result : results) {
                    countedSet.add(new ItemAndMetadata(result.item, result.meta));
                }

                List<ItemStack> resultStacks = Lists.newArrayList();
                for (ItemAndMetadata resultInfo : countedSet.elementSet()) {
                    resultStacks.add(resultInfo.createStack(countedSet.count(resultInfo)));
                }

                int resultCount = resultStacks.size();
                if (resultCount > 9) {
                    for (int i = 0; i < resultCount; i += 9) {
                        recipes.add(
                            new CompressedHammerRecipeWrapper(
                                inputStack,
                                resultStacks.subList(i, Math.min(resultCount, i + 9))));
                    }
                } else {
                    recipes.add(new CompressedHammerRecipeWrapper(inputStack, resultStacks));
                }
            }
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Error reading Compressed Hammer registry for JFMUY!", t);
        }

        return recipes;
    }

    private final IDrawable background;

    public CompressedHammerRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper
            .createDrawable(new ResourceLocation("exnihilo:textures/hammerNEI.png"), 0, 0, 166, 58);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return "Compressed Hammer";
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
    public void setRecipe(IRecipeLayout recipeLayout, CompressedHammerRecipeWrapper recipeWrapper,
        IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, true, 73, 3);

        for (int i = 0; i < 9; i++) {
            int x = 2 + i * 18;
            int y = 36;
            itemStacks.init(1 + i, false, x, y);
        }

        itemStacks.set(ingredients);

        itemStacks.addTooltipCallback((slotIdx, isInput, stack, tooltip) -> {
            if (!isInput && stack != null) {
                ItemStack sourceStack = recipeWrapper.getInput();
                if (sourceStack != null) {
                    tooltip.add(EnumChatFormatting.GRAY + "Drop Chance:");

                    Multiset<String> condensedTooltip = HashMultiset.create();
                    Block block = Block.getBlockFromItem(sourceStack.getItem());

                    for (Smashable smashable : CompressedHammerRegistry
                        .getSmashables(block, sourceStack.getItemDamage())) {
                        ItemStack smashableStack = new ItemStack(smashable.item, 1, smashable.meta);
                        if (stack.isItemEqual(smashableStack)) {
                            int chance = (int) (100.0F * smashable.chance);
                            int fortune = (int) (100.0F * smashable.luckMultiplier);

                            if (fortune > 0) {
                                condensedTooltip.add(
                                    chance + "%"
                                        + EnumChatFormatting.BLUE
                                        + " (+"
                                        + fortune
                                        + "% luck bonus)"
                                        + EnumChatFormatting.RESET);
                            } else {
                                condensedTooltip.add(chance + "%");
                            }
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
