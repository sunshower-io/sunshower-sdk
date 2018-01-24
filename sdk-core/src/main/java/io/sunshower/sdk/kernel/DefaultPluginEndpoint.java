package io.sunshower.sdk.kernel;

import io.sunshower.common.Identifier;
import io.sunshower.kernel.api.PluginManager;
import io.sunshower.sdk.kernel.model.PluginDescriptorElement;
import io.sunshower.sdk.kernel.model.PluginState;
import io.sunshower.sdk.kernel.model.PluginUploadFault;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DefaultPluginEndpoint implements PluginEndpoint {
    
    @Inject
    private PluginManager pluginManager;
    

    @Override
    public void setState(String pluginId, PluginState state) {

    }

    @Override
    public PluginState getState(String id) {
        return null;
    }


    @Override
    public List<PluginDescriptorElement> list() {
        return null;
    }

    @Override
    public PluginDescriptorElement get(Identifier id) {
        return null;
    }

    @Override
    public void deploy(
            HttpServletRequest req, 
            HttpServletResponse res
    ) throws PluginUploadFault {

    }
}
