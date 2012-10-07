package hu.nooon.blasius.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import hu.nooon.blasius.client.event.AnimationEvent;
import hu.nooon.blasius.client.event.AnimationEventHandler;
import hu.nooon.blasius.client.resource.SiteClientBundle;
import hu.nooon.blasius.client.widgets.*;
import org.sgx.raphael4gwt.raphael.*;
import org.sgx.raphael4gwt.raphael.Set;
import org.sgx.raphael4gwt.raphael.base.Attrs;
import org.sgx.raphael4gwt.raphael.base.Rectangle;
import org.sgx.raphael4gwt.raphael.event.Callback;
import org.sgx.raphael4gwt.raphael.event.HoverListener;
import org.sgx.raphael4gwt.raphael.event.MouseEventListener;

import java.util.*;


public class SiteEntryPoint implements EntryPoint {


    private static final int tileWidth = 256;
    private static final int tileHeight = 225;
    private static final int thumbWidth = 128;
    private static final int thumbHeight = 113;
    private static final int landWidth = 1024;
    private static final int landHeight = 675;
    private static final int portWidth = 768;
    private static final int portHeight = 900;


    private static final int titleHeight = 80;
    private static final int clientHeight = 4 * tileHeight;

    private static final int canvasWidth = 4 * tileWidth;
    private static final int canvasHeight = titleHeight + clientHeight;

    private static final double menuOpacity = .7;


    private SiteClientBundle clientBundle = SiteClientBundle.INSTANCE;

    private final EventBus eventBus = GWT.create(SimpleEventBus.class);

    private final Attrs titleTextAttrs =
            Attrs.create().fontFamily("Permanent Marker, cursive").fontSize(50).fill("black").textAnchor("start");
    private final Attrs bigMenuTextAttrs =
            Attrs.create().fontFamily("Permanent Marker, cursive").fontSize(60).fill("black").textAnchor("start");


    private Paper paper;
    private CustomGrid clientGrid;
    private java.util.Set<Set> modifiedLayer, clonedLayer;
    private ImageRepository images;
    private SiteAudio audio;

    public void onModuleLoad() {

        Document.get().getBody().getStyle().setMargin(0, Style.Unit.PX);
        Document.get().getBody().getStyle().setBackgroundImage("url(" + clientBundle.background().getSafeUri().asString() + ")");
        DivElement divElement = Document.get().createDivElement();
        Document.get().getBody().appendChild(divElement);
        divElement.getStyle().setWidth(canvasWidth, Style.Unit.PX);
        divElement.getStyle().setProperty("margin", "0px auto");

        eventBus.addHandler(AnimationEvent.TYPE, new AnimationEventHandler() {
            @Override
            public void animate(AnimationEvent event) {
                final List<FutureProxyShape> sequence = event.getSequence();
                if (!sequence.isEmpty()) {
                    Callback callback = sequence.size() > 1 ?
                            new Callback() {
                                @Override
                                public void call(Shape shape) {
                                    eventBus.fireEvent(new AnimationEvent(sequence.subList(1, sequence.size())));
                                }
                            } : null;

                    sequence.get(0).animate(callback);
                }
            }
        });

        initRaphael(divElement);

        createTitle();

        createClient();

        initResources();

        homeScreen(true);
    }

    private void initRaphael(DivElement divElement) {
        paper = Raphael.paper(divElement, canvasWidth, canvasHeight);
        clonedLayer = new HashSet<Set>();
        modifiedLayer = new HashSet<Set>();
    }


    private void initResources() {
        images = new ImageRepository(paper, tileWidth, tileHeight, thumbWidth, thumbHeight, landWidth, landHeight, portWidth, portHeight);

//        audio = new SiteAudio();
    }

    private Shape createTextLines(int x, int y, int lineHeight, String txt, Attrs textAttrs) {
        Set shape = paper.set();
        String[] lines = txt.split("\n");

        for (int i = 0; i < lines.length; i++) {
            shape.push(paper.text(x, y + i * lineHeight, lines[i]).attr(textAttrs));
        }

        return shape;
    }

    private Shape cloneSet(Set original) {
        Set result = paper.set();
        for (int i = 0; i < original.size(); i++) {
            result.push(original.item(i).clone());
        }

        return result;
    }

    private void createTitle() {

        Shape title = paper.text(150, 30, "Blasius Guitars and Basses").attr(titleTextAttrs);
        title.click(new MouseEventListener() {
            @Override
            public void notifyMouseEvent(NativeEvent nativeEvent) {
                homeScreen(false);
            }
        });

    }

    private MitsouGallery newGallery, archiveGallery, finishGallery, exhibitionsGallery, ownersGallery, actualGallery;

    private MitsouGallery getArchiveGallery() {
        if (archiveGallery == null) {
            archiveGallery = new MitsouGallery(paper,
                    Arrays.asList(clientBundle.ar1(), clientBundle.ar2(), clientBundle.ar4(), clientBundle.ar5(), clientBundle.ar6(),
                            clientBundle.ar7(),clientBundle.ar8(),clientBundle.ar9(),clientBundle.ar10(),clientBundle.ar3()),
                    Arrays.asList(clientBundle.ar1(), clientBundle.ar2(), clientBundle.ar4(), clientBundle.ar5(), clientBundle.ar6(),
                            clientBundle.ar7(),clientBundle.ar8(),clientBundle.ar9(),clientBundle.ar10(),clientBundle.ar3()),
                    0, titleHeight, tileWidth * 4, clientHeight,
                    thumbWidth, thumbHeight);

        }
        return archiveGallery;
    }

    private MitsouGallery getNewGallery() {
        if (newGallery == null) {
            newGallery = new MitsouGallery(paper,
                    Arrays.asList(clientBundle.n1(), clientBundle.n2(),clientBundle.n3(),clientBundle.n4(),clientBundle.n5(),
                            clientBundle.n6(),clientBundle.n7(),clientBundle.n8(),clientBundle.n9(),clientBundle.n10()),
                    Arrays.asList(clientBundle.n1(), clientBundle.n2(),clientBundle.n3(),clientBundle.n4(),clientBundle.n5(),
                            clientBundle.n6(),clientBundle.n7(),clientBundle.n8(),clientBundle.n9(),clientBundle.n10()),
                    0, titleHeight, tileWidth * 4, clientHeight,
                    thumbWidth, thumbHeight);

        }
        return newGallery;
    }


    private MitsouGallery getFinishGallery() {
        if (finishGallery == null) {
            finishGallery = new MitsouGallery(paper,
                    Arrays.asList(clientBundle.f1(), clientBundle.f2(),clientBundle.f3(),clientBundle.f4(),clientBundle.f5(),
                            clientBundle.f6(),clientBundle.f7(),clientBundle.f8(),clientBundle.f9(),clientBundle.f10()),
                    Arrays.asList(clientBundle.f1(), clientBundle.f2(),clientBundle.f3(),clientBundle.f4(),clientBundle.f5(),
                            clientBundle.f6(),clientBundle.f7(),clientBundle.f8(),clientBundle.f9(),clientBundle.f10()),
                    0, titleHeight, tileWidth * 4, clientHeight,
                    thumbWidth, thumbHeight);

        }
        return finishGallery;
    }


    private MitsouGallery getExhibitionsGallery() {
        if (finishGallery == null) {
            finishGallery = new MitsouGallery(paper,
                    Arrays.asList(clientBundle.ex1(), clientBundle.ex2(),clientBundle.ex3(),clientBundle.ex4(),clientBundle.ex5(),
                            clientBundle.ex6(),clientBundle.ex7(),clientBundle.ex8(),clientBundle.ex9(),clientBundle.ex10()),
                    Arrays.asList(clientBundle.ex1(), clientBundle.ex2(),clientBundle.ex3(),clientBundle.ex4(),clientBundle.ex5(),
                            clientBundle.ex6(),clientBundle.ex7(),clientBundle.ex8(),clientBundle.ex9(),clientBundle.ex10()),
                    0, titleHeight, tileWidth * 4, clientHeight,
                    thumbWidth, thumbHeight);

        }
        return finishGallery;
    }

    private MitsouGallery getOwnersGallery() {
        if (finishGallery == null) {
            finishGallery = new MitsouGallery(paper,
                    Arrays.asList(clientBundle.o1(), clientBundle.o2(),clientBundle.o3(),clientBundle.o4(),clientBundle.o5(),
                            clientBundle.o6(),clientBundle.o7(),clientBundle.o8(),clientBundle.o9(),clientBundle.o10()),
                    Arrays.asList(clientBundle.o1(), clientBundle.o2(),clientBundle.o3(),clientBundle.o4(),clientBundle.o5(),
                            clientBundle.o6(),clientBundle.o7(),clientBundle.o8(),clientBundle.o9(),clientBundle.o10()),
                    0, titleHeight, tileWidth * 4, clientHeight,
                    thumbWidth, thumbHeight);

        }
        return finishGallery;
    }


    private void hideGallery() {
        if (actualGallery != null) {
            actualGallery.hide(true);
        }
    }


    private void createClient() {
        Rectangle clientRectangle = Raphael.createRectangle(0, titleHeight, canvasWidth, clientHeight);
        Rect clientBkg = paper.rect(clientRectangle);
        clientBkg.attr(Attrs.create().strokeWidth(0));
        clientGrid = new CustomGrid(0, titleHeight, 4, 4, tileWidth, tileHeight);
    }

    private void homeScreen(boolean initial) {

        clearScreen();

        clientGrid.putShapeToGrid(0, 0, images.getMenuNew());
        clientGrid.putShapeToGrid(0, 2, images.getMenuFinish());
        clientGrid.putShapeToGrid(2, 0, images.getMenuArchive());
        clientGrid.putShapeToGrid(2, 1, images.getMenuExhibitions());
        clientGrid.putShapeToGrid(2, 2, images.getMenuOwners());

        bigMenu(images.getMenuNew(), "New guitars").flip(true);
        bigMenu(images.getMenuFinish(), "Finishing new series").flip(true);
        bigMenu(images.getMenuArchive(), "Archive").flip(true);
        bigMenu(images.getMenuExhibitions(), "Exhibitions").flip(true);
        bigMenu(images.getMenuOwners(), "Owners").flip(true);

        if (initial) {
            images.getMenuNew().click(getNewGalleryPage());
            images.getMenuFinish().click(getFinishGalleryPage());
            images.getMenuExhibitions().click(getExhibitionsGalleryPage());
            images.getMenuArchive().click(getArchiveGalleryPage());
            images.getMenuOwners().click(getOwnersGalleryPage());
        }


    }

    private MouseEventListener getArchiveGalleryPage() {
        return new MouseEventListener() {
            @Override
            public void notifyMouseEvent(NativeEvent nativeEvent) {
                clearScreen();
                actualGallery = getArchiveGallery();
                actualGallery.show(true);

            }
        };
    }

    private MouseEventListener getNewGalleryPage() {
        return new MouseEventListener() {
            @Override
            public void notifyMouseEvent(NativeEvent nativeEvent) {
                clearScreen();
                actualGallery = getNewGallery();
                actualGallery.show(true);
            }
        };
    }

    private MouseEventListener getFinishGalleryPage() {
        return new MouseEventListener() {
            @Override
            public void notifyMouseEvent(NativeEvent nativeEvent) {
                clearScreen();
                actualGallery = getFinishGallery();
                actualGallery.show(true);
            }
        };
    }

    private MouseEventListener getExhibitionsGalleryPage() {
        return new MouseEventListener() {
            @Override
            public void notifyMouseEvent(NativeEvent nativeEvent) {
                clearScreen();
                actualGallery = getExhibitionsGallery();
                actualGallery.show(true);
            }
        };
    }

    private MouseEventListener getOwnersGalleryPage() {
        return new MouseEventListener() {
            @Override
            public void notifyMouseEvent(NativeEvent nativeEvent) {
                clearScreen();
                actualGallery = getOwnersGallery();
                actualGallery.show(true);
            }
        };
    }

    private AlteratingProxyShape bigMenu(Set menu, String text) {

        Shape txt = paper.text((int) menu.getBBox().getX() + 10, (int) menu.getBBox().getY() + 50, text)
                .attr(bigMenuTextAttrs);
        Set inner = paper.set().push(menu).push(txt);

        final AlteratingProxyShape proxy = new AlteratingProxyShape(inner,
                Arrays.asList(Attrs.create().opacity(1), Attrs.create().opacity(0)),
                Arrays.asList(Attrs.create().opacity(.5), Attrs.create().opacity(1)), 200);
        inner.hover(new HoverListener() {
            @Override
            public void hoverIn(NativeEvent nativeEvent) {
                proxy.flip(false);
            }

            @Override
            public void hoverOut(NativeEvent nativeEvent) {
                proxy.flip(true);
            }
        });

        clonedLayer.add(paper.set().push(txt));
        return proxy;
    }

    public native void safari(Paper paper)/*-{
        paper.safari();
    }-*/;


    private void clearScreen() {
        clientGrid.clear();
        cleanLayers();
        hideGallery();
    }


    private void cleanLayers() {
        disposeClonedLayer();
        disposeModifiedLayer();
    }

    private void disposeClonedLayer() {

        if (clonedLayer != null) {
            for (final Set obsolete : clonedLayer) {
                obsolete.stop().animate(Raphael.animation(Attrs.create().opacity(0), 1000, Raphael.EASING_LINEAR, new Callback() {
                    @Override
                    public void call(Shape shape) {
                        for (int i = 0; i < obsolete.size(); i++) {
                            obsolete.item(i).remove();
                        }
                    }
                }));
            }
            clonedLayer.clear();
        }
    }

    private void disposeModifiedLayer() {
        if (modifiedLayer != null) {
            for (final Set obsolete : modifiedLayer) {
                obsolete.stop().animate(Raphael.animation(Attrs.create().opacity(0), 1000, Raphael.EASING_LINEAR, new Callback() {
                    @Override
                    public void call(Shape shape) {
                        obsolete.hide();
                    }
                }));
            }
            modifiedLayer.clear();
        }
    }

}