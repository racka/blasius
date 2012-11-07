package hu.nooon.blasius.client.resource;

import com.google.gwt.core.client.Callback;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.EventBus;
import com.reveregroup.gwt.imagepreloader.ImageLoadEvent;
import com.reveregroup.gwt.imagepreloader.ImageLoadHandler;
import com.reveregroup.gwt.imagepreloader.ImagePreloader;
import hu.nooon.blasius.client.event.SequenceEvent;
import hu.nooon.blasius.client.widgets.MitsouGallery;
import org.sgx.raphael4gwt.raphael.Paper;
import org.sgx.raphael4gwt.raphael.Set;

import java.util.List;

public class BlasiusBundle extends DriveBundle {

    public com.google.gwt.user.client.ui.Image getGWTImage(String folder, String fileName) {
        return new com.google.gwt.user.client.ui.Image(getFileDownloadURL(folder, fileName));
    }


    public com.google.gwt.user.client.ui.Image getBackground() {
        return getGWTImage("Common", "background.jpg");
    }


    private String about;
    public void getAbout(final Callback callback) {

        if (about == null) {

            RequestBuilder request = new RequestBuilder(RequestBuilder.GET, getFileDownloadURL("Common", "about.txt"));
            request.setCallback(new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    about = response.getText();
                    callback.onFailure(about);
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
        getSVGImage(paper, bass4String, "4StringBasses", "cover.jpg", callback);
    }

    /**
     * 0, titleHeight + headerHeight, canvasWidth, clientHeight,
     thumbWidth, thumbHeight
     */
    private MitsouGallery bass4StringGallery;
    public MitsouGallery getBass4StringGallery(Paper paper, EventBus eventBus, int x, int y, int width, int thumbWidth, int thumbHeight) {
        if (bass4StringGallery == null) {

            bass4StringGallery = new MitsouGallery(paper, eventBus, x, y, width, thumbWidth, thumbHeight);

            List<String> fileIds = getFolderFileURLs("4StringBasses");

            eventBus.fireEvent(new SequenceEvent(fileIds, new Callback() {
                @Override
                public void onFailure(Object reason) {
                }

                @Override
                public void onSuccess(Object result) {
                    bass4StringGallery.addImages((List<String>) result);
                }
            }));

        }

        return bass4StringGallery;
    }


    public void getSVGImage(final Paper paper, final Set shape, String folder, String fileName, final Callback callback) {

        if (shape.size() == 0) {
            ImagePreloader.load(getFileDownloadURL(folder, fileName), new ImageLoadHandler() {
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
