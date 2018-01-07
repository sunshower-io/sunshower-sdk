package io.sunshower.sdk.kernel;

import io.sunshower.sdk.kernel.model.PluginState;
import io.sunshower.sdk.kernel.model.PluginUploadFault;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DefaultPluginEndpoint implements PluginEndpoint {
    

    @Override
    public void setState(String pluginId, PluginState state) {

    }

    @Override
    public PluginState getState(String id) {
        return null;
    }



    @Override
    public void deploy(
            HttpServletRequest req, 
            HttpServletResponse res
    ) throws PluginUploadFault {

    }
}
