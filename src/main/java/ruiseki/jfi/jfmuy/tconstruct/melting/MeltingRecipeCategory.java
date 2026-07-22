package ruiseki.jfi.jfmuy.tconstruct.melting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Nullable;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import mantle.utils.ItemMetaWrapper;
import ruiseki.jfi.JFI;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IGuiFluidStackGroup;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;
import tconstruct.TConstruct;
import tconstruct.library.crafting.Smeltery;
import tconstruct.smeltery.TinkerSmeltery;

public class MeltingRecipeCategory implements IRecipeCategory<MeltingRecipeWrapper> {

    public static final String UID = "tconstruct.smeltery.melting";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new MeltingRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
            IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

            registry.addRecipes(getRecipes(), UID);
            registry.addRecipeCatalyst(new ItemStack(TinkerSmeltery.smeltery), UID);

        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null Melting recipe!", t);
        }
    }

    public static List<MeltingRecipeWrapper> getRecipes() {
        List<MeltingRecipeWrapper> recipes = new ArrayList<>();
        ObjectSet<String> processedGroups = new ObjectOpenHashSet<>();

        for (Map.Entry<ItemMetaWrapper, net.minecraftforge.fluids.FluidStack> entry : Smeltery.getSmeltingList()
            .entrySet()) {
            ItemMetaWrapper key = entry.getKey();
            String smeltingGroup = Smeltery.getSmeltingGroup(key);

            if (smeltingGroup != null) {
                if (processedGroups.add(smeltingGroup)) {
                    List<ItemStack> groupItems = Smeltery.getSmeltingGroupItems(smeltingGroup);
                    if (!groupItems.isEmpty()) {
                        recipes.add(new MeltingRecipeWrapper(groupItems));
                    }
                }
            } else {
                ItemStack stack = new ItemStack(key.item, 1, key.meta);
                recipes.add(new MeltingRecipeWrapper(stack));
            }
        }
        return recipes;
    }

    private final IDrawable background;
    private final IDrawable icon;
    private final String title;

    public MeltingRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation guiTex = new ResourceLocation("tinker", "textures/gui/nei/smeltery.png");
        this.background = guiHelper.createDrawable(guiTex, 0, 0, 160, 55);
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(TinkerSmeltery.lavaTank));
        this.title = StatCollector.translateToLocal("tconstruct.nei.melting");
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getModName() {
        return TConstruct.modID;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, MeltingRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();

        itemStacks.init(0, true, 28, 21);

        int capacity = recipeWrapper.getOutputFluid() != null ? recipeWrapper.getOutputFluid().amount : 1000;
        fluidStacks.init(0, false, 115, 20, 18, 18, capacity, true, null);

        itemStacks.set(ingredients);
        fluidStacks.set(ingredients);
    }
}
