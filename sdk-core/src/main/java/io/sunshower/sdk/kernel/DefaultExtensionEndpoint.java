package io.sunshower.sdk.kernel;

import io.sunshower.common.Identifier;
import io.sunshower.kernel.api.PluginManager;
import io.sunshower.sdk.core.model.Extensions;
import io.sunshower.sdk.kernel.model.ExtensionPointDescriptorElement;
import io.sunshower.sdk.kernel.model.PluginState;
import io.sunshower.sdk.kernel.model.PluginUploadFault;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultExtensionEndpoint implements ExtensionEndpoint {

    @Inject
    private Extensions extensions;
    
    @Inject
    private PluginManager pluginManager; 
    
    
    @Override
    @PreAuthorize("hasAuthority('admin')")
    public List<ExtensionPointDescriptorElement> list() {
        return pluginManager
                .getExtensionPoints()
                .stream()
                .map(extensions::toElement)
                .collect(Collectors.toList());
        
    }

    @Override
    @PreAuthorize("hasAuthority('admin')")
    public ExtensionPointDescriptorElement get(Identifier id) {
        return list().get(0);
    }

    @Override
    public void deploy(HttpServletRequest req, HttpServletResponse res) throws PluginUploadFault {

    }

    @Override
    public void setState(String pluginId, PluginState state) {

    }

    @Override
    public PluginState getState(String id) {
        return null;
    }
}
