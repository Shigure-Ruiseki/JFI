package ruiseki.jfi.jfmuy.harvestcraft.garden;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Nullable;

import com.pam.harvestcraft.BlockRegistry;
import com.pam.harvestcraft.base.BlockGarden;

import cpw.mods.fml.common.registry.GameRegistry;
import ruiseki.jfi.JFI;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class GardenCategory implements IRecipeCategory<GardenWrapper> {

    public static final String UID = "harvestcraft.garden";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new GardenCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            List<GardenWrapper> recipes = getRecipes();
            registry.addRecipes(recipes, UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe for Garden Category!", t);
        }
    }

    private static List<GardenWrapper> getRecipes() {
        List<GardenWrapper> recipes = new ArrayList<>();
        Map<String, Block> gardens = new HashMap<>();

        gardens.put("harvestcraft.nei.garden.berry", BlockRegistry.pamberryGarden);
        gardens.put("harvestcraft.nei.garden.desert", BlockRegistry.pamdesertGarden);
        gardens.put("harvestcraft.nei.garden.grass", BlockRegistry.pamgrassGarden);
        gardens.put("harvestcraft.nei.garden.gourd", BlockRegistry.pamgourdGarden);
        gardens.put("harvestcraft.nei.garden.ground", BlockRegistry.pamgroundGarden);
        gardens.put("harvestcraft.nei.garden.herb", BlockRegistry.pamherbGarden);
        gardens.put("harvestcraft.nei.garden.leafy", BlockRegistry.pamleafyGarden);
        gardens.put("harvestcraft.nei.garden.mushroom", BlockRegistry.pammushroomGarden);
        gardens.put("harvestcraft.nei.garden.stalk", BlockRegistry.pamstalkGarden);
        gardens.put("harvestcraft.nei.garden.textile", BlockRegistry.pamtextileGarden);
        gardens.put("harvestcraft.nei.garden.tropical", BlockRegistry.pamtropicalGarden);
        gardens.put("harvestcraft.nei.garden.water", BlockRegistry.pamwaterGarden);
        gardens.put("harvestcraft.nei.garden.nether", GameRegistry.findBlock("harvestthenether", "netherGarden"));

        for (Map.Entry<String, Block> garden : gardens.entrySet()) {
            String biomeTransKey = garden.getKey();
            Block gardenBlock = garden.getValue();

            if (gardenBlock != null) {
                List<ItemStack> outputs = BlockGarden.getDropList(gardenBlock)
                    .stream()
                    .map(item -> new ItemStack(item, 1))
                    .collect(Collectors.toList());

                recipes.add(new GardenWrapper(new ItemStack(gardenBlock), outputs, biomeTransKey));
            }
        }

        return recipes;
    }

    private final IDrawable background;
    private final IDrawable icon;

    public GardenCategory(IGuiHelper guiHelper) {
        ResourceLocation guiTexture = new ResourceLocation("harvestcraft:textures/gui/gardenNEI.png");
        this.background = guiHelper.createDrawable(guiTexture, 0, 0, 166, 100);
        Block iconBlock = BlockRegistry.pamgrassGarden != null ? BlockRegistry.pamgrassGarden
            : BlockRegistry.pamberryGarden;
        if (iconBlock != null) {
            this.icon = guiHelper.createDrawableIngredient(new ItemStack(iconBlock));
        } else {
            this.icon = null;
        }
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal("harvestcraft.nei.garden.category");
    }

    @Override
    public String getModName() {
        return "Pam's HarvestCraft";
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, GardenWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, true, 73, 3);

        List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);
        int row = 0;
        int col = 0;

        for (int i = 0; i < outputs.size(); i++) {
            itemStacks.init(1 + i, false, 2 + 18 * col, 36 + 18 * row);
            col++;
            if (col > 8) {
                col = 0;
                row++;
            }
        }

        itemStacks.set(ingredients);
    }
}
