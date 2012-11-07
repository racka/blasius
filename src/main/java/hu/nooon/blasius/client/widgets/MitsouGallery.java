package hu.nooon.blasius.client.widgets;

import com.google.gwt.dom.client.NativeEvent;
import com.reveregroup.gwt.imagepreloader.ImageLoadEvent;
import com.reveregroup.gwt.imagepreloader.ImageLoadHandler;
import com.reveregroup.gwt.imagepreloader.ImagePreloader;
import org.sgx.raphael4gwt.raphael.Paper;
import org.sgx.raphael4gwt.raphael.Raphael;
import org.sgx.raphael4gwt.raphael.Set;
import org.sgx.raphael4gwt.raphael.Shape;
import org.sgx.raphael4gwt.raphael.base.Attrs;
import org.sgx.raphael4gwt.raphael.event.Callback;
import org.sgx.raphael4gwt.raphael.event.MouseEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MitsouGallery implements CustomLayer {

    private Paper paper;
    private List<FadedObject> thumbnails;
    private final Set thumbnailSet;
    private Map<Integer, Shape> images;
    private Shape actualImage;
    private Shape thumbFrame;
    private int x, y, width, thumbWidth, thumbHeight;
    private List<String> imageDownloadURLs;

    public MitsouGallery(Paper paper, List<String> imageDownloadURLs, int x, int y, int width, int thumbWidth, int thumbHeight) {

        this.paper = paper;
        this.imageDownloadURLs = imageDownloadURLs;
        this.x = x;
        this.y = y;
        this.width = width;
        this.thumbWidth = thumbWidth;
        this.thumbHeight = thumbHeight;

        this.images = new HashMap<Integer, Shape>();
        this.thumbnailSet = paper.set();
        this.thumbnails = new ArrayList<FadedObject>();
    }

    public void fetchImages() {
        addImages(imageDownloadURLs);
    }


    public void addImages(final List<String> imageDownloadURLs) {

        if (!imageDownloadURLs.isEmpty()) {

            String imageDownloadURL = imageDownloadURLs.get(0);

            ImagePreloader.load(imageDownloadURL, new ImageLoadHandler() {
                @Override
                public void imageLoaded(ImageLoadEvent imageLoadEvent) {

                    addImageThumb(paper.image(imageLoadEvent.getImageUrl(), 0, 0, thumbWidth, thumbHeight));

                    int actualX = x + ((width - imageLoadEvent.getDimensions().getWidth()) / 2);
                    Shape image = paper.image(imageLoadEvent.getImageUrl(), 0, 0, imageLoadEvent.getDimensions().getWidth(), imageLoadEvent.getDimensions().getHeight());
                    image.attr(Attrs.create().x(actualX).y(y));
                    Shape frame = paper.rect(actualX, y, imageLoadEvent.getDimensions().getWidth(), imageLoadEvent.getDimensions().getHeight());
                    frame.attr(Attrs.create().stroke("black").strokeWidth(5));
                    Shape imageWithFrame = paper.set().push(image).push(frame);
                    images.put(images.size(), imageWithFrame);

                    imageWithFrame.attr(Attrs.create().opacity(0)).hide();

                    MitsouGallery.this.addImages(imageDownloadURLs.subList(1, imageDownloadURLs.size()));
                }
            });
        }

    }


    private MouseEventListener thumbEvent;

    public void addImageThumb(Shape thumb) {

        thumb.attr(Attrs.create().x(x + this.thumbnails.size() * thumbWidth).y(y));

        FadedObject thumbnail = new FadedObject(thumb, .1);
        this.thumbnails.add(thumbnail);
        this.thumbnailSet.push(thumb);

        final int index = thumbnails.size() - 1;
        thumb.click(new MouseEventListener() {
            @Override
            public void notifyMouseEvent(NativeEvent nativeEvent) {
                showNewShape(index);
            }
        });


        final int sumWidth = thumbWidth * thumbnails.size();
        final int maxDelta = sumWidth - width;
        if (maxDelta > 0) {

            if (thumbEvent != null) {
                this.thumbnailSet.unmouseMove(thumbEvent);
            }

            thumbEvent = new MouseEventListener() {
                @Override
                public void notifyMouseEvent(NativeEvent nativeEvent) {
                    int deltaX = (int) Math.floor(maxDelta * ((double) (nativeEvent.getClientX() - x) / (double) (width)));

                    for (int i = 0; i < thumbnailSet.size(); i++) {
                        thumbnailSet.item(i).attr(Attrs.create().x(x - deltaX + i * thumbWidth));
                    }

                }
            };

            this.thumbnailSet.mouseMove(thumbEvent);
        }

        if (thumbFrame == null) {
            thumbFrame = paper.rect(x, y, width, thumbHeight);
            thumbFrame.attr(Attrs.create().stroke("white").strokeWidth(3).opacity(.2));
        }

        thumbnailSet.toFront();

    }

    private void showNewShape(final int index) {
        if (images.size() > index) {
            if (actualImage != null) {
                actualImage.stop();
                actualImage.animate(
                        Raphael.animation(Attrs.create().opacity(0), 200, Raphael.EASING_LINEAR,
                                new Callback() {
                                    @Override
                                    public void call(Shape shape) {
                                        images.get(index).toBack().show().animate(Raphael.animation(
                                                Attrs.create().opacity(1), 200, Raphael.EASING_LINEAR));
                                        actualImage = images.get(index);
                                    }
                                }).delay(getRandom(500)));
            } else {
                images.get(index).toBack().show().animate(Raphael.animation(
                        Attrs.create().opacity(1),
                        200, Raphael.EASING_LINEAR, new Callback() {
                    @Override
                    public void call(Shape src) {
                        actualImage = images.get(index);
                    }
                }).delay(getRandom(500)));
            }
        }
    }

    private int getRandom(int max) {
        int nextRandom = (int) Math.floor(com.google.gwt.user.client.Random.nextDouble() * max);
        return nextRandom != 0 ? nextRandom : max;
    }

    @Override
    public CustomLayer clear() {
        return this;
    }

    @Override
    public CustomLayer show(boolean animated) {
        thumbnailSet.show().toFront();
        thumbFrame.show().toFront();
        showNewShape(0);
        return this;
    }

    @Override
    public CustomLayer hide(boolean animated) {
        actualImage.toBack().hide();
        thumbnailSet.toBack().hide();
        thumbFrame.toBack().hide();
        return this;
    }

    @Override
    public CustomLayer removeShape(int index, boolean animated) {
        return this;
    }
}
