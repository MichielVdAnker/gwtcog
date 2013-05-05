package gwtcog.core;

import com.google.gwt.core.client.JavaScriptObject;

public class JSOTool {

    @SuppressWarnings("unchecked")
    public static <T extends JavaScriptObject> T deepCopy(T obj)
    {
        return (T) deepCopyImpl(obj);
    }

    private static native JavaScriptObject deepCopyImpl(JavaScriptObject obj) /*-{

        if (obj == null) return obj;

        var copy;        

        if (obj instanceof Array) {
             // Handle Array
            copy = [];
            for (var i = 0, len = obj.length; i < len; i++) {
                if (obj[i] == null || typeof obj[i] != "object") copy[i] = obj[i];
                else copy[i] = @gwtcog.core.JSOTool::deepCopyImpl(Lcom/google/gwt/core/client/JavaScriptObject;)(obj[i]);
            }
        } else {
            // Handle Object
            copy = {};
            for (var attr in obj) {
                if (obj.hasOwnProperty(attr)) {
                    if (obj[attr] == null || typeof obj[attr] != "object") copy[attr] = obj[attr];
                    else copy[attr] = @gwtcog.core.JSOTool::deepCopyImpl(Lcom/google/gwt/core/client/JavaScriptObject;)(obj[attr]);
                }
            }
        }        
        return copy;
    }-*/;

} 