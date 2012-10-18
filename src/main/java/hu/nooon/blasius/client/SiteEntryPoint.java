package hu.nooon.blasius.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.*;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import hu.nooon.blasius.client.event.AnimationEvent;
import hu.nooon.blasius.client.event.AnimationEventHandler;
import hu.nooon.blasius.client.resource.SiteClientBundle;
import hu.nooon.blasius.client.widgets.*;
import org.sgx.raphael4gwt.raphael.*;
import org.sgx.raphael4gwt.raphael.base.Attrs;
import org.sgx.raphael4gwt.raphael.base.Rectangle;
import org.sgx.raphael4gwt.raphael.event.Callback;
import org.sgx.raphael4gwt.raphael.event.HoverListener;
import org.sgx.raphael4gwt.raphael.event.MouseEventListener;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


public class SiteEntryPoint implements EntryPoint {


    private static final int tileWidth = 204;
    private static final int tileHeight = 225;
    private static final int thumbWidth = 128;
    private static final int thumbHeight = 113;

    private static final int menuItemWidth = 180;
    private static final int titleHeight = 80;
    private static final int headerHeight = 50;
    private static final int clientHeight = 4 * tileHeight;

    private static final int canvasWidth = 5 * tileWidth;
    private static final int canvasHeight = titleHeight + headerHeight + clientHeight;
    private static final double menuOpacity = .4;

    private SiteClientBundle clientBundle = SiteClientBundle.INSTANCE;

    private final EventBus eventBus = GWT.create(SimpleEventBus.class);

    //font-family: 'Playfair Display', serif;
    //font-family: 'Stoke', serif;
    //font-family: 'Adamina', serif;


    private final Attrs titleTextAttrs =
            Attrs.create().fontFamily("Chau Philomene One, sans-serif").fontSize(50).fill("white").textAnchor("start");
    private final Attrs menuTextAttrs =
            Attrs.create().fontFamily("Playfair Display, cursive").fontSize(16).fill("black").textAnchor("start");
    private final Attrs bigMenuTextAttrs =
            Attrs.create().fontFamily("Chau Philomene One, sans-serif").fontSize(50).fill("white").textAnchor("start");


    private Paper paper;
    private CustomGrid clientGrid, menuGrid;
    private java.util.Set<Set> modifiedLayer, clonedLayer;
    private ImageRepository images;
    private SiteAudio audio;

    public void onModuleLoad() {

        Element facebook = Document.get().getBody().getElementsByTagName("fb:like-box").getItem(0);
        facebook.getStyle().setDisplay(Style.Display.NONE);
        facebook.getStyle().setZIndex(10);
        facebook.getStyle().setPosition(Style.Position.ABSOLUTE);
        facebook.getStyle().setTop(titleHeight + headerHeight + 50, Style.Unit.PX);
        facebook.getStyle().setLeft((Window.getClientWidth() - canvasWidth) / 2, Style.Unit.PX);

        Document.get().getBody().getStyle().setMargin(0, Style.Unit.PX);
        Document.get().getBody().getStyle().setBackgroundImage("url(" + clientBundle.background().getSafeUri().asString() + ")");
        DivElement divElement = Document.get().createDivElement();
        Document.get().getBody().appendChild(divElement);
        divElement.getStyle().setZIndex(5);
        divElement.getStyle().setWidth(canvasWidth, Style.Unit.PX);
        divElement.getStyle().setPosition(Style.Position.ABSOLUTE);
        divElement.getStyle().setLeft((Window.getClientWidth() - canvasWidth) / 2, Style.Unit.PX);
        divElement.getStyle().setTop(0, Style.Unit.PX);

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

        createHeader();

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
        images = new ImageRepository(paper);

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

        Shape title = paper.text(250, 30, "Blasius Guitars and Basses").attr(titleTextAttrs);
        title.click(new MouseEventListener() {
            @Override
            public void notifyMouseEvent(NativeEvent nativeEvent) {
                homeScreen(false);
            }
        });

    }

    private void createHeader() {


        Rectangle headerRectangle = Raphael.createRectangle(0, titleHeight, canvasWidth, headerHeight - 15);
        Rect headerBkg = paper.rect(headerRectangle);
        headerBkg.attr(Attrs.create().stroke("black").strokeWidth(3).fill("white").opacity(.7));

        menuGrid = new CustomGrid(0, titleHeight, 1, 7, menuItemWidth, headerHeight, 20, 20);

        FadedObject menuHome = new FadedObject(paper.text(0, 0, "Home").attr(menuTextAttrs), menuOpacity);
        menuGrid.putShapeToGrid(0, 0, menuHome.getShape()).animate(Raphael.animation(Attrs.create().opacity(menuOpacity), 100, Raphael.EASING_LINEAR));

        FadedObject menuNews = new FadedObject(paper.text(0, 0, "News").attr(menuTextAttrs), menuOpacity);
        menuGrid.putShapeToGrid(1, 0, menuNews.getShape()).animate(Raphael.animation(Attrs.create().opacity(menuOpacity), 100, Raphael.EASING_LINEAR));


        FadedObject menuNewGuitars = new FadedObject(paper.text(0, 0, "New guitars").attr(menuTextAttrs), menuOpacity);
        menuGrid.putShapeToGrid(2, 0, menuNewGuitars.getShape()).animate(Raphael.animation(Attrs.create().opacity(menuOpacity), 100, Raphael.EASING_LINEAR));
        FadedObject menuFinish = new FadedObject(paper.text(0, 0, "Finishing").attr(menuTextAttrs), menuOpacity);
        menuGrid.putShapeToGrid(3, 0, menuFinish.getShape()).animate(Raphael.animation(Attrs.create().opacity(menuOpacity), 100, Raphael.EASING_LINEAR));
        FadedObject menuArchive = new FadedObject(paper.text(0, 0, "Archive").attr(menuTextAttrs), menuOpacity);
        menuGrid.putShapeToGrid(4, 0, menuArchive.getShape()).animate(Raphael.animation(Attrs.create().opacity(menuOpacity), 100, Raphael.EASING_LINEAR));
        FadedObject menuExhibitions = new FadedObject(paper.text(0, 0, "Exhibitions").attr(menuTextAttrs), menuOpacity);
        menuGrid.putShapeToGrid(5, 0, menuExhibitions.getShape()).animate(Raphael.animation(Attrs.create().opacity(menuOpacity), 100, Raphael.EASING_LINEAR));
        FadedObject menuOwners = new FadedObject(paper.text(0, 0, "Owners").attr(menuTextAttrs), menuOpacity);
        menuGrid.putShapeToGrid(6, 0, menuOwners.getShape()).animate(Raphael.animation(Attrs.create().opacity(menuOpacity), 100, Raphael.EASING_LINEAR));


        menuHome.getShape().click(new MouseEventListener() {
            @Override
            public void notifyMouseEvent(NativeEvent nativeEvent) {
                homeScreen(false);
            }
        });
        menuNews.getShape().click(getNewsPage());
        menuNewGuitars.getShape().click(getNewGalleryPage());
        menuFinish.getShape().click(getFinishGalleryPage());
        menuArchive.getShape().click(getArchiveGalleryPage());
        menuExhibitions.getShape().click(getExhibitionsGalleryPage());
        menuOwners.getShape().click(getOwnersGalleryPage());
    }

    private MitsouGallery newGallery, archiveGallery, finishGallery, exhibitionsGallery, ownersGallery;

    private MitsouGallery getArchiveGallery() {
        if (archiveGallery == null) {
            archiveGallery = new MitsouGallery(paper,
                    Arrays.asList(clientBundle.ar1(), clientBundle.ar2(), clientBundle.ar4(), clientBundle.ar5(), clientBundle.ar6(),
                            clientBundle.ar7(), clientBundle.ar8(), clientBundle.ar9(), clientBundle.ar10(), clientBundle.ar3()),
                    Arrays.asList(clientBundle.ar1(), clientBundle.ar2(), clientBundle.ar4(), clientBundle.ar5(), clientBundle.ar6(),
                            clientBundle.ar7(), clientBundle.ar8(), clientBundle.ar9(), clientBundle.ar10(), clientBundle.ar3()),
                    0, titleHeight + headerHeight, canvasWidth, clientHeight,
                    thumbWidth, thumbHeight);

        }
        return archiveGallery;
    }

    private MitsouGallery getNewGallery() {
        if (newGallery == null) {
            newGallery = new MitsouGallery(paper,
                    Arrays.asList(clientBundle.n1(), clientBundle.n2(), clientBundle.n3(), clientBundle.n4(), clientBundle.n5(),
                            clientBundle.n6(), clientBundle.n7(), clientBundle.n8(), clientBundle.n9(), clientBundle.n10()),
                    Arrays.asList(clientBundle.n1(), clientBundle.n2(), clientBundle.n3(), clientBundle.n4(), clientBundle.n5(),
                            clientBundle.n6(), clientBundle.n7(), clientBundle.n8(), clientBundle.n9(), clientBundle.n10()),
                    0, titleHeight + headerHeight, canvasWidth, clientHeight,
                    thumbWidth, thumbHeight);

        }
        return newGallery;
    }


    private MitsouGallery getFinishGallery() {
        if (finishGallery == null) {
            finishGallery = new MitsouGallery(paper,
                    Arrays.asList(clientBundle.f1(), clientBundle.f2(), clientBundle.f3(), clientBundle.f4(), clientBundle.f5(),
                            clientBundle.f6(), clientBundle.f7(), clientBundle.f8(), clientBundle.f9(), clientBundle.f10()),
                    Arrays.asList(clientBundle.f1(), clientBundle.f2(), clientBundle.f3(), clientBundle.f4(), clientBundle.f5(),
                            clientBundle.f6(), clientBundle.f7(), clientBundle.f8(), clientBundle.f9(), clientBundle.f10()),
                    0, titleHeight + headerHeight, canvasWidth, clientHeight,
                    thumbWidth, thumbHeight);

        }
        return finishGallery;
    }


    private MitsouGallery getExhibitionsGallery() {
        if (exhibitionsGallery == null) {
            exhibitionsGallery = new MitsouGallery(paper,
                    Arrays.asList(clientBundle.ex1(), clientBundle.ex2(), clientBundle.ex3(), clientBundle.ex4(), clientBundle.ex5(),
                            clientBundle.ex6(), clientBundle.ex7(), clientBundle.ex8(), clientBundle.ex9(), clientBundle.ex10()),
                    Arrays.asList(clientBundle.ex1(), clientBundle.ex2(), clientBundle.ex3(), clientBundle.ex4(), clientBundle.ex5(),
                            clientBundle.ex6(), clientBundle.ex7(), clientBundle.ex8(), clientBundle.ex9(), clientBundle.ex10()),
                    0, titleHeight + headerHeight, canvasWidth, clientHeight,
                    thumbWidth, thumbHeight);

        }
        return exhibitionsGallery;
    }

    private MitsouGallery getOwnersGallery() {
        if (ownersGallery == null) {
            ownersGallery = new MitsouGallery(paper,
                    Arrays.asList(clientBundle.o1(), clientBundle.o2(), clientBundle.o3(), clientBundle.o4(), clientBundle.o5(),
                            clientBundle.o6(), clientBundle.o7(), clientBundle.o8(), clientBundle.o9(), clientBundle.o10()),
                    Arrays.asList(clientBundle.o1(), clientBundle.o2(), clientBundle.o3(), clientBundle.o4(), clientBundle.o5(),
                            clientBundle.o6(), clientBundle.o7(), clientBundle.o8(), clientBundle.o9(), clientBundle.o10()),
                    0, titleHeight + headerHeight, canvasWidth, clientHeight,
                    thumbWidth, thumbHeight);

        }
        return ownersGallery;
    }


    private void hideGallery() {
        if (ownersGallery != null) {
            ownersGallery.hide(true);
        }
        if (finishGallery != null) {
            finishGallery.hide(true);
        }
        if (newGallery != null) {
            newGallery.hide(true);
        }
        if (exhibitionsGallery != null) {
            exhibitionsGallery.hide(true);
        }
        if (archiveGallery != null) {
            archiveGallery.hide(true);
        }
    }

    private void createClient() {
        clientGrid = new CustomGrid(0, titleHeight + headerHeight, 2, 5, tileWidth, tileHeight);
    }

    private void homeScreen(boolean initial) {

        clearScreen();

        Shape facebook = paper.image(clientBundle.facebook(), 0, 0, clientBundle.facebook().getWidth(), clientBundle.facebook().getHeight());
        Set facebookBigMenu = paper.set().push(facebook);
        clientGrid.putShapeToGrid(0, 0, facebookBigMenu);


        clientGrid.putShapeToGrid(0, 1, images.getMenuArchive());
        clientGrid.putShapeToGrid(1, 1, images.getMenuExhibitions());
        clientGrid.putShapeToGrid(2, 1, images.getMenuFinish());
        clientGrid.putShapeToGrid(3, 1, images.getMenuNew());
        clientGrid.putShapeToGrid(4, 1, images.getMenuOwners());

        bigMenu(facebookBigMenu, "Visit Blasius on facebook!").flip(true);
        bigMenu(images.getMenuArchive(), "Archive").flip(true);
        bigMenu(images.getMenuExhibitions(), "Exhibitions").flip(true);
        bigMenu(images.getMenuFinish(), "Finishing new series").flip(true);
        bigMenu(images.getMenuNew(), "New guitars").flip(true);
        bigMenu(images.getMenuOwners(), "Owners").flip(true);

        if (initial) {
            facebook.click(new MouseEventListener() {
                @Override
                public void notifyMouseEvent(NativeEvent nativeEvent) {
                    Window.open("http://www.facebook.com/BlasiusGuitarsandBasses", "_blank", "");
                }
            });

            images.getMenuArchive().click(getArchiveGalleryPage());
            images.getMenuExhibitions().click(getExhibitionsGalleryPage());
            images.getMenuNew().click(getNewGalleryPage());
            images.getMenuFinish().click(getFinishGalleryPage());
            images.getMenuOwners().click(getOwnersGalleryPage());
        }

    }

    private MouseEventListener getArchiveGalleryPage() {
        return new MouseEventListener() {
            @Override
            public void notifyMouseEvent(NativeEvent nativeEvent) {
                clearScreen();
                getArchiveGallery().show(true);
            }
        };
    }

    private MouseEventListener getNewGalleryPage() {
        return new MouseEventListener() {
            @Override
            public void notifyMouseEvent(NativeEvent nativeEvent) {
                clearScreen();
                getNewGallery().show(true);
            }
        };
    }

    private MouseEventListener getFinishGalleryPage() {
        return new MouseEventListener() {
            @Override
            public void notifyMouseEvent(NativeEvent nativeEvent) {
                clearScreen();
                getFinishGallery().show(true);
            }
        };
    }

    private MouseEventListener getExhibitionsGalleryPage() {
        return new MouseEventListener() {
            @Override
            public void notifyMouseEvent(NativeEvent nativeEvent) {
                clearScreen();
                getExhibitionsGallery().show(true);
            }
        };
    }

    private MouseEventListener getOwnersGalleryPage() {
        return new MouseEventListener() {
            @Override
            public void notifyMouseEvent(NativeEvent nativeEvent) {
                clearScreen();
                getOwnersGallery().show(true);
            }
        };
    }

    private MouseEventListener getNewsPage() {
        return new MouseEventListener() {
            @Override
            public void notifyMouseEvent(NativeEvent nativeEvent) {
                clearScreen();

                Rect bkg = paper.rect(0, headerHeight + titleHeight, canvasWidth, clientHeight);
                bkg.attr(Attrs.create().fill("white").opacity(.7).strokeWidth(0));
                clonedLayer.add(paper.set().push(bkg));

                Element facebook = Document.get().getBody().getElementsByTagName("fb:like-box").getItem(0);
                facebook.getStyle().setDisplay(Style.Display.BLOCK);
                facebook.getStyle().setZIndex(100);
            }
        };
    }


    private AlteratingProxyShape bigMenu(Set menu, String text) {

        Shape txt = paper.text((int) menu.getBBox().getX() + 10, (int) menu.getBBox().getY() + 50, text)
                .attr(bigMenuTextAttrs);
        Set inner = paper.set().push(menu).push(txt);

        final AlteratingProxyShape proxy = new AlteratingProxyShape(inner,
                Arrays.asList(Attrs.create().opacity(1), Attrs.create().opacity(0)),
                Arrays.asList(Attrs.create().opacity(.6), Attrs.create().opacity(1)), 200);
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
        hideFaceBook();
    }


    private void cleanLayers() {
        disposeClonedLayer();
        disposeModifiedLayer();
    }

    private void disposeClonedLayer() {
        for (final Set obsoleteShape : clonedLayer) {
            obsoleteShape.remove();
        }
        clonedLayer.clear();
    }

    private void disposeModifiedLayer() {
        for (final Set obsolete : modifiedLayer) {
            obsolete.stop().animate(Raphael.animation(Attrs.create().opacity(0), 1000, Raphael.EASING_LINEAR, new Callback() {
                @Override
                public void call(Shape shape) {
                    obsolete.hide();
                }
            })).toBack();
        }
        modifiedLayer.clear();
    }

    private void hideFaceBook() {
        Element facebook = Document.get().getBody().getElementsByTagName("fb:like-box").getItem(0);
        facebook.getStyle().setDisplay(Style.Display.NONE);
    }


}