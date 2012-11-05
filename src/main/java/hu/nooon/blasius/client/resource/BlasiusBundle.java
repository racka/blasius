package hu.nooon.blasius.client.resource;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.Request;
import com.google.gwt.user.client.Window;
import com.reveregroup.gwt.imagepreloader.ImageLoadEvent;
import com.reveregroup.gwt.imagepreloader.ImageLoadHandler;
import com.reveregroup.gwt.imagepreloader.ImagePreloader;
import org.sgx.raphael4gwt.raphael.Image;
import org.sgx.raphael4gwt.raphael.Paper;
import org.sgx.raphael4gwt.raphael.Shape;

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

            RequestBuilder request = new RequestBuilder(RequestBuilder.GET, getStreamURL("Common", "about.txt", "text/plain"));
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



    private Image bass4String;
    public void getBass4String(Paper paper, final Callback callback) {
        getSVGImage(bass4String, "4StringBasses", "cover.jpg", "image/jpg", callback);
    }


    public void getSVGImage(final Shape shape, String folder, String fileName, String mime, final Callback callback) {

        if (shape == null) {

            ImagePreloader.load(getStreamURL(folder, fileName, mime), new ImageLoadHandler() {
                @Override
                public void imageLoaded(ImageLoadEvent imageLoadEvent) {
                    Window.alert("url: " + imageLoadEvent.getImageUrl());
                    callback.onFailure(imageLoadEvent);
                }
            });


        } else {
            callback.onSuccess(shape);
        }
    }




}
