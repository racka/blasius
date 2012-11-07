package hu.nooon.blasius.client.resource;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Window;
import hu.nooon.blasius.client.widgets.MitsouGallery;
import org.sgx.raphael4gwt.raphael.Paper;
import org.sgx.raphael4gwt.raphael.Set;

public class BlasiusBundle extends ResourceUtils {

    public String getBackgroundURL() {
        return getFileURL("Common", "background.jpg");
    }

    private String about;
    public void getAbout(final Callback callback) {

        if (about == null) {

            RequestBuilder request = new RequestBuilder(RequestBuilder.GET, GWT.getModuleBaseURL() + getFileStreamURL("Text_HU", "about.txt"));
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

    private MitsouGallery bass4StringGallery;
    public MitsouGallery getBass4StringGallery(Paper paper, int x, int y, int width, int thumbWidth, int thumbHeight) {
        if (bass4StringGallery == null) {
            bass4StringGallery = new MitsouGallery(paper, getFolderFileURLs("4StringBasses", "cover.jpg"), x, y, width, thumbWidth, thumbHeight);
            bass4StringGallery.fetchImages();
        }

        return bass4StringGallery;
    }






}
