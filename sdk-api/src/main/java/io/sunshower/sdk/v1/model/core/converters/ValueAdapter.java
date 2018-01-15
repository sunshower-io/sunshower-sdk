package io.sunshower.sdk.v1.model.core.converters;

import io.sunshower.common.Identifier;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ValueAdapter extends XmlAdapter<String, Object> {
    
    
    @Override
    public Object unmarshal(String v) throws Exception {
        if(v == null) {
            return null;
        }
        v = v.trim();
        String lv = v.toLowerCase();
        if("false".equals(lv) || "true".equals(lv)) {
            return Boolean.valueOf(lv);
        }
        return Identifier.valueOf(v);
        
    }

    @Override
    public String marshal(Object v) throws Exception {
        if(v == null) {
            return null;
        }
        return v.toString();
    }
}
