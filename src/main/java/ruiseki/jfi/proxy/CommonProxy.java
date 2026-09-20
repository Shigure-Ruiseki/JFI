package ruiseki.jfi.proxy;

import ruiseki.jfi.JFI;
import ruiseki.jfi.network.packet.ae2.PacketCraftingPatternSlots;
import ruiseki.okcore.enums.Mods;
import ruiseki.okcore.init.ModBase;
import ruiseki.okcore.network.PacketHandler;
import ruiseki.okcore.proxy.CommonProxyComponent;

public class CommonProxy extends CommonProxyComponent {

    @Override
    public ModBase getMod() {
        return JFI._instance;
    }

    @Override
    public void registerPacketHandlers(PacketHandler packetHandler) {
        super.registerPacketHandlers(packetHandler);

        if (Mods.AppliedEnergistics2.isModLoaded()) {
            packetHandler.register(PacketCraftingPatternSlots.class);
        }
    }
}
