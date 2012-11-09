package hu.nooon.blasius.client.resource;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Window;
import com.reveregroup.gwt.imagepreloader.ImageLoadEvent;
import com.reveregroup.gwt.imagepreloader.ImageLoadHandler;
import com.reveregroup.gwt.imagepreloader.ImagePreloader;
import org.sgx.raphael4gwt.raphael.Paper;
import org.sgx.raphael4gwt.raphael.Set;

public class ResourceUtils extends GoogleDriveClientUtils {

    public void getSVGImage(final Paper paper, final Set shape, String folder, String fileName, final Callback callback) {

        if (shape.size() == 0) {
            ImagePreloader.load(getFileURL(folder, fileName), new ImageLoadHandler() {
                @Override
                public void imageLoaded(ImageLoadEvent imageLoadEvent) {
                    shape.push(paper.image(imageLoadEvent.getImageUrl(), 0, 0,
                            imageLoadEvent.getDimensions().getWidth(),
                            imageLoadEvent.getDimensions().getHeight()));
                    callback.onFailure(shape);
                }
            });


        } else {
            callback.onSuccess(shape);
        }
    }

    public void getTextResource(final StringBuilder text, String folder, String fileName, final Callback callback) {

        if (text.length() == 0) {
            RequestBuilder request = new RequestBuilder(RequestBuilder.GET, GWT.getModuleBaseURL() + getFileStreamURL(folder, fileName));
            request.setCallback(new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    text.append(response.getText());
                    callback.onFailure(text.toString());
                }

                @Override
                public void onError(Request request, Throwable exception) {
                }
            });
            try {
                request.send();
            } catch (RequestException e) {
                Window.alert("Google Drive error! Please reload the page!");
            }

        } else {
            callback.onSuccess(text.toString());
        }
    }



}
