package ruiseki.jfi.jfmuy.thermalexpansion.crafting.machine;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import cofh.lib.util.helpers.AugmentHelper;
import cofh.lib.util.helpers.ItemHelper;
import cofh.thermalexpansion.block.simple.BlockFrame;
import cofh.thermalexpansion.util.crafting.RecipeMachine;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.wrapper.ICraftingRecipeWrapper;

public class CraftingMachineRecipeWrapper implements ICraftingRecipeWrapper {

    protected final RecipeMachine rawRecipe;
    protected final List<List<ItemStack>> inputs;
    protected final List<ItemStack> outputs;

    public CraftingMachineRecipeWrapper(RecipeMachine recipe) {
        this.rawRecipe = recipe;
        this.inputs = new ArrayList<>();
        this.outputs = new ArrayList<>();

        setupRecipeData();
    }

    @SuppressWarnings("unchecked")
    private void setupRecipeData() {
        Object[] rawInputs = rawRecipe.getInput();
        if (rawInputs != null) {
            for (Object inputObj : rawInputs) {
                if (inputObj instanceof ItemStack) {
                    inputs.add(Collections.singletonList((ItemStack) inputObj));
                } else if (inputObj instanceof List) {
                    inputs.add((List<ItemStack>) inputObj);
                } else {
                    inputs.add(Collections.emptyList());
                }
            }
        }

        ItemStack defaultOutput = rawRecipe.getRecipeOutput();
        if (defaultOutput != null) {
            int targetSlot = 3;
            try {
                Field field = RecipeMachine.class.getDeclaredField("targetSlot");
                field.setAccessible(true);
                targetSlot = field.getInt(rawRecipe);
            } catch (Exception ignored) {}

            if (inputs.size() > targetSlot && !inputs.get(targetSlot)
                .isEmpty()) {
                List<ItemStack> centerFrames = inputs.get(targetSlot);

                for (ItemStack frame : centerFrames) {
                    if (frame == null) continue;

                    ItemStack tieredOutput = defaultOutput.copy();

                    ItemStack[] augments = null;
                    try {
                        Field augField = RecipeMachine.class.getDeclaredField("augments");
                        augField.setAccessible(true);
                        augments = (ItemStack[]) augField.get(rawRecipe);
                    } catch (Exception ignored) {}

                    if (augments != null) {
                        AugmentHelper.writeAugments(tieredOutput, augments);
                    }

                    if (tieredOutput.stackTagCompound == null) {
                        tieredOutput.stackTagCompound = new NBTTagCompound();
                    }

                    // Xử lý set Level cho output tương tự CachedMachineRecipe của NEI
                    if (ItemHelper.itemsEqualWithMetadata(frame, BlockFrame.frameMachineBasic)) {
                        tieredOutput.stackTagCompound.setByte("Level", (byte) 0);
                    } else if (ItemHelper.itemsEqualWithMetadata(frame, BlockFrame.frameMachineHardened)) {
                        tieredOutput.stackTagCompound.setByte("Level", (byte) 1);
                    } else if (ItemHelper.itemsEqualWithMetadata(frame, BlockFrame.frameMachineReinforced)) {
                        tieredOutput.stackTagCompound.setByte("Level", (byte) 2);
                    } else if (ItemHelper.itemsEqualWithMetadata(frame, BlockFrame.frameMachineResonant)) {
                        tieredOutput.stackTagCompound.setByte("Level", (byte) 3);
                    }

                    if (!outputs.contains(tieredOutput)) {
                        outputs.add(tieredOutput);
                    }
                }
            }

            if (outputs.isEmpty()) {
                ItemStack tieredOutput = defaultOutput.copy();
                ItemStack[] augments = null;
                try {
                    Field augField = RecipeMachine.class.getDeclaredField("augments");
                    augField.setAccessible(true);
                    augments = (ItemStack[]) augField.get(rawRecipe);
                } catch (Exception ignored) {}

                if (augments != null) {
                    AugmentHelper.writeAugments(tieredOutput, augments);
                }
                outputs.add(tieredOutput);
            }
        }
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, inputs);
        ingredients.setOutputs(VanillaTypes.ITEM, outputs);
    }
}
