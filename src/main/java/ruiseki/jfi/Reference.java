package ruiseki.jfi;

public class Reference {

    public static final String MOD_ID = Tags.MOD_ID;
    public static final String MOD_NAME = Tags.MOD_NAME;
    public static final String MOD_VERSION = Tags.VERSION;
    public static final String MOD_DEPENDENCIES = "required-after:jfmuy;" + "required-after:okcore;"
        + "after:BigReactors;"
        + "after:Botania;"
        + "after:EnderIO;"
        + "after:exastris;"
        + "after:excompressum;"
        + "after:exnihilo;"
        + "after:IC2;"
        + "after:ImmersiveEngineering;"
        + "after:Mekanism;"
        + "after:MineFactoryReloaded;"
        + "after:NotEnoughItems;"
        + "after:neiintegration;"
        + "after:NEIAddons;"
        + "after:TConstruct;"
        + "after:ThermalDynamics;"
        + "after:ThermalExpansion;"
        + "after:ThermalFoundation;";

    public static final String VERSION_URL = "https://raw.githubusercontent.com/Shigure-Ruiseki/JFI/master/version/version.json";

    public static final String PROXY_COMMON = Tags.MOD_GROUP + ".proxy.CommonProxy";
    public static final String PROXY_CLIENT = Tags.MOD_GROUP + ".proxy.ClientProxy";
    public static final String GUI_FACTORY = Tags.MOD_GROUP + ".GuiConfigOverview$ExtendedConfigGuiFactory";

    public static final String PREFIX_MOD = MOD_ID + ":";
}
