package ruiseki.jfi.proxy;

import ruiseki.jfi.JFI;
import ruiseki.okcore.init.ModBase;
import ruiseki.okcore.proxy.CommonProxyComponent;

public class CommonProxy extends CommonProxyComponent {

    @Override
    public ModBase getMod() {
        return JFI._instance;
    }
}
