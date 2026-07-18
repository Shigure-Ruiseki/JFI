package ruiseki.jfi.jfmuy.botania.orechid;

import java.util.Map;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import vazkii.botania.api.BotaniaAPI;

public class OrechidIgnemRecipeWrapper extends OrechidRecipeWrapper {

    public OrechidIgnemRecipeWrapper(Map.Entry<String, Integer> entry) {
        super(entry);
    }

    @Override
    protected ItemStack getInputStack() {
        return new ItemStack(Blocks.netherrack, 64);
    }

    public Map<String, Integer> getOreWeights() {
        return BotaniaAPI.oreWeightsNether;
    }
}
