package ruiseki.jfi.jfmuy.bigreactors.reactor;

public class ReactorEntry {

    private final String material;
    private final boolean block;

    public ReactorEntry(String material, boolean block) {
        this.material = material;
        this.block = block;
    }

    public static ReactorEntry newBlock(String blockName) {
        return new ReactorEntry(blockName, true);
    }

    public static ReactorEntry newFluid(String fluidName) {
        return new ReactorEntry(fluidName, false);
    }

    public String getMaterial() {
        return material;
    }

    public boolean isBlock() {
        return block;
    }
}
