package ruiseki.jfi.jfmuy.tconstruct.tools;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.apache.logging.log4j.Level;

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
import tconstruct.TConstruct;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.CastingRecipe;
import tconstruct.library.crafting.PatternBuilder;
import tconstruct.library.crafting.PatternBuilder.ItemKey;
import tconstruct.library.crafting.PatternBuilder.MaterialSet;
import tconstruct.library.util.IToolPart;
import tconstruct.tools.TinkerTools;
import tconstruct.util.config.PHConstruct;

public class ToolMaterialsRecipeCategory implements IRecipeCategory<ToolMaterialsRecipeWrapper> {

    public static final String UID = "tconstruct.tools.materials";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new ToolMaterialsRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
            IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

            registry.addRecipes(getRecipes(), UID);

            registry.addRecipeCatalyst(new ItemStack(TinkerTools.toolStationWood), UID);
            registry.addRecipeCatalyst(new ItemStack(TinkerTools.toolForge), UID);

        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    public static List<ToolMaterialsRecipeWrapper> getRecipes() {
        List<ToolMaterialsRecipeWrapper> recipes = new ArrayList<>();

        for (int matID : TConstructRegistry.toolMaterials.keySet()) {
            List<ItemStack> toolParts = new ArrayList<>();

            for (ItemKey key : PatternBuilder.instance.materials) {
                MaterialSet set = PatternBuilder.instance.materialSets.get(key.key);
                if (set != null && set.materialID == matID) {
                    addStackIfNotExists(toolParts, new ItemStack(key.item, 1, key.damage));
                }
            }

            for (List list : TConstructRegistry.patternPartMapping.keySet()) {
                if (list.size() >= 3 && (Integer) list.get(2) == matID) {
                    ItemStack partStack = TConstructRegistry.patternPartMapping.get(list);
                    if (partStack != null) {
                        addStackIfNotExists(toolParts, partStack);
                    }
                }
            }

            if (!PHConstruct.craftMetalTools) {
                for (CastingRecipe recipe : TConstructRegistry.getTableCasting()
                    .getCastingRecipes()) {
                    ItemStack castResult = recipe.getResult();
                    if (castResult != null && castResult.getItem() instanceof IToolPart) {
                        if (((IToolPart) castResult.getItem()).getMaterialID(castResult) == matID) {
                            addStackIfNotExists(toolParts, castResult);
                        }
                    }
                }
            }

            if (!toolParts.isEmpty()) {
                if (TConstructRegistry.getMaterial(matID) != null) {
                    recipes.add(new ToolMaterialsRecipeWrapper(toolParts, matID));
                }

                if (TConstructRegistry.getBowMaterial(matID) != null
                    || TConstructRegistry.getArrowMaterial(matID) != null) {
                    recipes.add(new ToolMaterialsRecipeWrapper(toolParts, matID, true));
                }
            }
        }

        return recipes;
    }

    private static void addStackIfNotExists(List<ItemStack> list, ItemStack stackToAdd) {
        if (stackToAdd == null) return;

        for (ItemStack existing : list) {
            if (ItemStack.areItemStacksEqual(existing, stackToAdd)
                && ItemStack.areItemStackTagsEqual(existing, stackToAdd)) {
                return;
            }
        }
        list.add(stackToAdd.copy());
    }

    private final IDrawable background;
    private final String title;

    public ToolMaterialsRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation guiTex = new ResourceLocation("tinker", "textures/gui/nei/toolmaterials.png");
        this.background = guiHelper.createDrawable(guiTex, 0, 0, 160, 130);
        this.title = StatCollector.translateToLocal("tconstruct.nei.toolmaterials");
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
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, ToolMaterialsRecipeWrapper recipeWrapper,
        IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, true, 9, 9);

        itemStacks.set(ingredients);
    }
}
