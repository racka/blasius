package hu.nooon.blasius.client.resource;

import com.google.gwt.core.client.Callback;
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


}
