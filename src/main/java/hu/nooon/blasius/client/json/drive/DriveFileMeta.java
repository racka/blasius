package hu.nooon.blasius.client.json.drive;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class DriveFileMeta extends JavaScriptObject {

    protected DriveFileMeta() {}

    public final native String getId() /*-{ return this.id; }-*/;
    public final native String getTitle() /*-{ return this.title; }-*/;
    public final native String getParentId() /*-{ return this.parent; }-*/;
    public final native String getMimeType() /*-{ return this.mimeType; }-*/;

    public static native JsArray<DriveFileMeta> getAsArray(String json) /*-{ return eval(json); }-*/;
}
