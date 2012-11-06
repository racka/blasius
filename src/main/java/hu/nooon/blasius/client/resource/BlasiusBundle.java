package hu.nooon.blasius.client.resource;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Window;
import com.reveregroup.gwt.imagepreloader.ImageLoadEvent;
import com.reveregroup.gwt.imagepreloader.ImageLoadHandler;
import com.reveregroup.gwt.imagepreloader.ImagePreloader;
import hu.nooon.blasius.client.widgets.MitsouGallery;
import org.sgx.raphael4gwt.raphael.Paper;
import org.sgx.raphael4gwt.raphael.Set;

import java.util.List;

public class BlasiusBundle extends DriveBundle {

    public com.google.gwt.user.client.ui.Image getImage(String folder, String fileName) {
        String fileId = getFileId(folder, fileName);

        if (!fileId.isEmpty()) {
            String URL = GWT.getModuleBaseURL() + "googledrive?op=stream&mime=image/jpg&fileID=" + fileId;
            return new com.google.gwt.user.client.ui.Image(URL);
        }

        return null;
    }


    public com.google.gwt.user.client.ui.Image getBackground() {
        return getImage("Common", "background.jpg");
    }


    private String about;
    public void getAbout(final Callback callback) {

        if (about == null) {

            RequestBuilder request = new RequestBuilder(RequestBuilder.GET, getStreamURLbyName("Common", "about.txt", "text/plain"));
            request.setCallback(new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    about = response.getText();
                    callback.onSuccess(about);
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
            callback.onSuccess(about);
        }
    }



    private Set bass4String;
    public void getBass4String(Paper paper, Callback callback) {
        if (bass4String == null) {
            bass4String = paper.set();
        }
        getSVGImage(paper, bass4String, "4StringBasses", "cover.jpg", "image/jpg", callback);
    }

    /**
     * 0, titleHeight + headerHeight, canvasWidth, clientHeight,
     thumbWidth, thumbHeight
     */
    private MitsouGallery bass4StringGallery;
    public MitsouGallery getBass4StringGallery(Paper paper, BlasiusBundle clientBundle, int x, int y, int width, int thumbWidth, int thumbHeight) {
        if (bass4StringGallery == null) {

            bass4StringGallery = new MitsouGallery(paper, clientBundle, x, y, width, thumbWidth, thumbHeight);

            List<String> fileIds = getFileIds("4StringBasses");
            bass4StringGallery.addImages(fileIds);
        }

        return bass4StringGallery;
    }


    public void getSVGImage(final Paper paper, final Set shape, String folder, String fileName, String mime, final Callback callback) {

        if (shape.size() == 0) {
            ImagePreloader.load(getStreamURLbyName(folder, fileName, mime), new ImageLoadHandler() {
                @Override
                public void imageLoaded(ImageLoadEvent imageLoadEvent) {
                    shape.push(paper.image(imageLoadEvent.getImageUrl(), 0, 0, imageLoadEvent.getDimensions().getWidth(), imageLoadEvent.getDimensions().getHeight()));
                    callback.onFailure(shape);
                }
            });


        } else {
            callback.onSuccess(shape);
        }
    }




}
