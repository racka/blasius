package hu.nooon.blasius.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.*;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import hu.nooon.blasius.client.event.DriveInitEvent;
import hu.nooon.blasius.client.event.DriveInitEventHandler;
import hu.nooon.blasius.client.event.SequenceEvent;
import hu.nooon.blasius.client.event.SequenceEventHandler;
import hu.nooon.blasius.client.resource.BlasiusBundle;
import hu.nooon.blasius.client.widgets.AlteratingProxyShape;
import hu.nooon.blasius.client.widgets.CustomGrid;
import hu.nooon.blasius.client.widgets.FadedObject;
import hu.nooon.blasius.client.widgets.MitsouGallery;
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

    private static final int menuItemWidth = 200;
    private static final int titleHeight = 80;
    private static final int headerHeight = 50;
    private static final int clientHeight = tileHeight;

    private static final int facebookFeedY = titleHeight + headerHeight + 50;
    private static final int canvasWidth = 5 * tileWidth;
    private static final int canvasHeight = facebookFeedY + 558;
    private static final double menuOpacity = .4;

    private BlasiusBundle clientBundle = new BlasiusBundle();

    private final EventBus eventBus = GWT.create(SimpleEventBus.class);

    //font-family: 'Playfair Display', serif;
    //font-family: 'Stoke', serif;
    //font-family: 'Adamina', serif;


    private final Attrs titleTextAttrs =
            Attrs.create().fontFamily("Chau Philomene One, sans-serif").fontSize(50).fill("white").textAnchor("start");
    private final Attrs menuTextAttrs =
            Attrs.create().fontFamily("Playfair Display, cursive").fontSize(16).fill("black").textAnchor("start");
    private final Attrs clientTextAttrs =
            Attrs.create().fontFamily("Adamina, serif").fontSize(16).fill("white").textAnchor("start");
    private final Attrs bigMenuTextAttrs =
            Attrs.create().fontFamily("Chau Philomene One, sans-serif").fontSize(50).fill("white").textAnchor("start");


    private Paper paper;
    private CustomGrid clientGrid, menuGrid;
    private java.util.Set<Set> modifiedLayer, clonedLayer;
    private SiteAudio audio;

    public void onModuleLoad() {

        hideFaceBook();

        Document.get().getBody().getStyle().setMargin(0, Style.Unit.PX);
        final DivElement divElement = Document.get().createDivElement();
        Document.get().getBody().appendChild(divElement);
        divElement.getStyle().setZIndex(5);
        divElement.getStyle().setWidth(canvasWidth, Style.Unit.PX);
        divElement.getStyle().setPosition(Style.Position.ABSOLUTE);
        divElement.getStyle().setLeft((Window.getClientWidth() - canvasWidth) / 2, Style.Unit.PX);
        divElement.getStyle().setTop(0, Style.Unit.PX);

        eventBus.addHandler(SequenceEvent.TYPE, new SequenceEventHandler() {
            @Override
            public void invoke(SequenceEvent event) {
                final List sequence = event.getSequence();
                final com.google.gwt.core.client.Callback callback = event.getCallback();
                if (!sequence.isEmpty() && callback != null) {
                    callback.onSuccess(sequence.subList(1, sequence.size()));
                }
            }
        });

        eventBus.addHandler(DriveInitEvent.TYPE, new DriveInitEventHandler() {
            @Override
            public void onDriveInit(DriveInitEvent event) {
                Document.get().getBody().getStyle().setBackgroundImage("url(" + clientBundle.getBackgroundURL() + ")");

                initRaphael(divElement);
                createTitle();
                createHeader();
                createClient();
                homeScreen();
            }
        });

        try {
            clientBundle.initHierarchy(eventBus).send();
        } catch (RequestException e) {
            Window.alert("Google Drive access error! Please reload the page.");
        }


    }

    private void initRaphael(DivElement divElement) {
        paper = Raphael.paper(divElement, canvasWidth, canvasHeight);
        clonedLayer = new HashSet<Set>();
        modifiedLayer = new HashSet<Set>();
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
                homeScreen();
            }
        });

    }

    private void createHeader() {


        Rectangle headerRectangle = Raphael.createRectangle(0, titleHeight, canvasWidth, headerHeight - 15);
        Rect headerBkg = paper.rect(headerRectangle);
        headerBkg.attr(Attrs.create().stroke("black").strokeWidth(3).fill("white").opacity(.7));

        menuGrid = new CustomGrid(0, titleHeight, 1, 5, menuItemWidth, headerHeight, 20, 20);

        FadedObject menuHome = new FadedObject(paper.text(0, 0, "Home").attr(menuTextAttrs), menuOpacity);
        menuGrid.putShapeToGrid(0, 0, menuHome.getShape()).animate(Raphael.animation(Attrs.create().opacity(menuOpacity), 100, Raphael.EASING_LINEAR));
        FadedObject menuNews = new FadedObject(paper.text(0, 0, "News").attr(menuTextAttrs), menuOpacity);
        menuGrid.putShapeToGrid(1, 0, menuNews.getShape()).animate(Raphael.animation(Attrs.create().opacity(menuOpacity), 100, Raphael.EASING_LINEAR));
        FadedObject menuAbout = new FadedObject(paper.text(0, 0, "About us").attr(menuTextAttrs), menuOpacity);
        menuGrid.putShapeToGrid(2, 0, menuAbout.getShape()).animate(Raphael.animation(Attrs.create().opacity(menuOpacity), 100, Raphael.EASING_LINEAR));
        FadedObject menuContact = new FadedObject(paper.text(0, 0, "Contact").attr(menuTextAttrs), menuOpacity);
        menuGrid.putShapeToGrid(3, 0, menuContact.getShape()).animate(Raphael.animation(Attrs.create().opacity(menuOpacity), 100, Raphael.EASING_LINEAR));
        FadedObject menuOrder = new FadedObject(paper.text(0, 0, "Order").attr(menuTextAttrs), menuOpacity);
        menuGrid.putShapeToGrid(4, 0, menuOrder.getShape()).animate(Raphael.animation(Attrs.create().opacity(menuOpacity), 100, Raphael.EASING_LINEAR));


        menuHome.getShape().click(new MouseEventListener() {
            @Override
            public void notifyMouseEvent(NativeEvent nativeEvent) {
                homeScreen();
            }
        });
        menuNews.getShape().click(new MouseEventListener() {
            @Override
            public void notifyMouseEvent(NativeEvent e) {
                clearScreen();
                showFaceBook();
            }
        });
        menuAbout.getShape().click(getAboutPage());
        menuContact.getShape().click(getContactPage());
        menuOrder.getShape().click(getOrderPage());
    }

    private MitsouGallery bass4StringGallery, bass5StringGallery;
    private MitsouGallery endorsersGallery, exhibitionsGallery, ownersGallery;

    private void hideGallery() {
        if (bass4StringGallery != null) {
            bass4StringGallery.hide(true);
        }
        if (bass5StringGallery != null) {
            bass5StringGallery.hide(true);
        }
        if (endorsersGallery != null) {
            endorsersGallery.hide(true);
        }
        if (exhibitionsGallery != null) {
            exhibitionsGallery.hide(true);
        }
        if (ownersGallery != null) {
            ownersGallery.hide(true);
        }
    }

    private void createClient() {
        clientGrid = new CustomGrid(102, titleHeight + headerHeight, 2, 8, tileWidth / 2, tileHeight + 20);
    }

    private void homeScreen() {

        clearScreen();

        clientBundle.getBass4StringCover(paper, new com.google.gwt.core.client.Callback() {
            @Override
            public void onFailure(Object reason) {
                Shape shape = (Shape) reason;
                clientGrid.putShapeToGrid(0, 0, shape);
                bigMenu((Set) shape, "4 string basses").flip(true);
                shape.click(get4StringBassesGallery());
            }

            @Override
            public void onSuccess(Object result) {
                Shape shape = (Shape) result;
                clientGrid.putShapeToGrid(0, 0, shape);
                bigMenu((Set) shape, "4 string basses").flip(true);
            }
        });

        clientBundle.getBass5StringCover(paper, new com.google.gwt.core.client.Callback() {
            @Override
            public void onFailure(Object reason) {
                Shape shape = (Shape) reason;
                clientGrid.putShapeToGrid(4, 0, shape);
                bigMenu((Set) shape, "5 string basses").flip(true);
                shape.click(get5StringBassesGallery());
            }

            @Override
            public void onSuccess(Object result) {
                Shape shape = (Shape) result;
                clientGrid.putShapeToGrid(4, 0, shape);
                bigMenu((Set) shape, "5 string basses").flip(true);
            }
        });

        clientBundle.getEndorsersCover(paper, new com.google.gwt.core.client.Callback() {
            @Override
            public void onFailure(Object reason) {
                Shape shape = (Shape) reason;
                clientGrid.putShapeToGrid(1, 1, shape);
                bigMenu((Set) shape, "Endorsers").flip(true);
                shape.click(getEndorsersGallery());
            }

            @Override
            public void onSuccess(Object result) {
                Shape shape = (Shape) result;
                clientGrid.putShapeToGrid(1, 1, shape);
                bigMenu((Set) shape, "Endorsers").flip(true);
            }
        });

        clientBundle.getExhibitionsCover(paper, new com.google.gwt.core.client.Callback() {
            @Override
            public void onFailure(Object reason) {
                Shape shape = (Shape) reason;
                clientGrid.putShapeToGrid(3, 1, shape);
                bigMenu((Set) shape, "Exhibitions").flip(true);
                shape.click(getExhibitionsGallery());
            }

            @Override
            public void onSuccess(Object result) {
                Shape shape = (Shape) result;
                clientGrid.putShapeToGrid(3, 1, shape);
                bigMenu((Set) shape, "Exhibitions").flip(true);
            }
        });

        clientBundle.getOwnersCover(paper, new com.google.gwt.core.client.Callback() {
            @Override
            public void onFailure(Object reason) {
                Shape shape = (Shape) reason;
                clientGrid.putShapeToGrid(5, 1, shape);
                bigMenu((Set) shape, "Owners").flip(true);
                shape.click(getOwnersGallery());
            }

            @Override
            public void onSuccess(Object result) {
                Shape shape = (Shape) result;
                clientGrid.putShapeToGrid(5, 1, shape);
                bigMenu((Set) shape, "Owners").flip(true);
            }
        });

    }

    private MouseEventListener get4StringBassesGallery() {
        return new MouseEventListener() {
            @Override
            public void notifyMouseEvent(NativeEvent nativeEvent) {
                clearScreen();
                bass4StringGallery = clientBundle.getBass4StringGallery(paper, 0, titleHeight + headerHeight, canvasWidth, thumbWidth, thumbHeight);
                bass4StringGallery.show(true);
            }
        };
    }

    private MouseEventListener get5StringBassesGallery() {
        return new MouseEventListener() {
            @Override
            public void notifyMouseEvent(NativeEvent nativeEvent) {
                clearScreen();
                bass5StringGallery = clientBundle.getBass5StringGallery(paper, 0, titleHeight + headerHeight, canvasWidth, thumbWidth, thumbHeight);
                bass5StringGallery.show(true);
            }
        };
    }

    private MouseEventListener getEndorsersGallery() {
        return new MouseEventListener() {
            @Override
            public void notifyMouseEvent(NativeEvent nativeEvent) {
                clearScreen();
                endorsersGallery = clientBundle.getEndorsersGallery(paper, 0, titleHeight + headerHeight, canvasWidth, thumbWidth, thumbHeight);
                endorsersGallery.show(true);
            }
        };
    }

    private MouseEventListener getExhibitionsGallery() {
        return new MouseEventListener() {
            @Override
            public void notifyMouseEvent(NativeEvent nativeEvent) {
                clearScreen();
                exhibitionsGallery = clientBundle.getExhibitionsGallery(paper, 0, titleHeight + headerHeight, canvasWidth, thumbWidth, thumbHeight);
                exhibitionsGallery.show(true);
            }
        };
    }

    private MouseEventListener getOwnersGallery() {
        return new MouseEventListener() {
            @Override
            public void notifyMouseEvent(NativeEvent nativeEvent) {
                clearScreen();
                ownersGallery = clientBundle.getOwnersGallery(paper, 0, titleHeight + headerHeight, canvasWidth, thumbWidth, thumbHeight);
                ownersGallery.show(true);
            }
        };
    }


    private MouseEventListener getAboutPage() {
        return new MouseEventListener() {
            @Override
            public void notifyMouseEvent(NativeEvent nativeEvent) {
                clearScreen();

                final int actualY = headerHeight + titleHeight;
                Rect bkg = paper.rect(canvasWidth, actualY, canvasWidth, tileHeight * 2);
                bkg.attr(Attrs.create().fill("black").opacity(0).strokeWidth(0));
                clonedLayer.add(paper.set().push(bkg));

                bkg.animate(Raphael.animation(Attrs.create().x(0).opacity(.7), 1000, Raphael.EASING_LINEAR,
                        new Callback() {
                            @Override
                            public void call(Shape shape) {

                                clientBundle.getAboutText(new com.google.gwt.core.client.Callback() {
                                    @Override
                                    public void onFailure(Object reason) {
                                        String text = (String) reason;
                                        Shape txt = createTextLines(10, actualY + 20, 30, text, clientTextAttrs)
                                                .attr(Attrs.create().opacity(0));
                                        clonedLayer.add((Set) txt);
                                        txt.animate(Raphael.animation(Attrs.create().opacity(1), 500, Raphael.EASING_LINEAR));
                                    }

                                    @Override
                                    public void onSuccess(Object result) {
                                        String text = (String) result;
                                        Shape txt = createTextLines(10, actualY + 20, 30, text, clientTextAttrs)
                                                .attr(Attrs.create().opacity(0));
                                        clonedLayer.add((Set) txt);
                                        txt.animate(Raphael.animation(Attrs.create().opacity(1), 500, Raphael.EASING_LINEAR));
                                    }
                                });
                            }
                        }));
            }
        };
    }

    private MouseEventListener getContactPage() {
        return new MouseEventListener() {
            @Override
            public void notifyMouseEvent(NativeEvent nativeEvent) {
                clearScreen();

                final int actualY = headerHeight + titleHeight;
                Rect bkg = paper.rect(canvasWidth, actualY, canvasWidth, tileHeight * 2);
                bkg.attr(Attrs.create().fill("black").opacity(0).strokeWidth(0));
                clonedLayer.add(paper.set().push(bkg));

                bkg.animate(Raphael.animation(Attrs.create().x(0).opacity(.7), 1000, Raphael.EASING_LINEAR,
                        new Callback() {
                            @Override
                            public void call(Shape shape) {

                                clientBundle.getContactText(new com.google.gwt.core.client.Callback() {
                                    @Override
                                    public void onFailure(Object reason) {
                                        String text = (String) reason;
                                        Shape txt = createTextLines(10, actualY + 20, 30, text, clientTextAttrs)
                                                .attr(Attrs.create().opacity(0));
                                        clonedLayer.add((Set) txt);
                                        txt.animate(Raphael.animation(Attrs.create().opacity(1), 500, Raphael.EASING_LINEAR));
                                    }

                                    @Override
                                    public void onSuccess(Object result) {
                                        String text = (String) result;
                                        Shape txt = createTextLines(10, actualY + 20, 30, text, clientTextAttrs)
                                                .attr(Attrs.create().opacity(0));
                                        clonedLayer.add((Set) txt);
                                        txt.animate(Raphael.animation(Attrs.create().opacity(1), 500, Raphael.EASING_LINEAR));
                                    }
                                });
                            }
                        }));
            }
        };
    }


    private MouseEventListener getOrderPage() {
        return new MouseEventListener() {
            @Override
            public void notifyMouseEvent(NativeEvent nativeEvent) {
                clearScreen();

                final int actualY = headerHeight + titleHeight;
                Rect bkg = paper.rect(canvasWidth, actualY, canvasWidth, tileHeight * 2);
                bkg.attr(Attrs.create().fill("black").opacity(0).strokeWidth(0));
                clonedLayer.add(paper.set().push(bkg));

                bkg.animate(Raphael.animation(Attrs.create().x(0).opacity(.7), 1000, Raphael.EASING_LINEAR,
                        new Callback() {
                            @Override
                            public void call(Shape shape) {

                                clientBundle.getOrderText(new com.google.gwt.core.client.Callback() {
                                    @Override
                                    public void onFailure(Object reason) {
                                        String text = (String) reason;
                                        Shape txt = createTextLines(10, actualY + 20, 30, text, clientTextAttrs)
                                                .attr(Attrs.create().opacity(0));
                                        clonedLayer.add((Set) txt);
                                        txt.animate(Raphael.animation(Attrs.create().opacity(1), 500, Raphael.EASING_LINEAR));
                                    }

                                    @Override
                                    public void onSuccess(Object result) {
                                        String text = (String) result;
                                        Shape txt = createTextLines(10, actualY + 20, 30, text, clientTextAttrs)
                                                .attr(Attrs.create().opacity(0));
                                        clonedLayer.add((Set) txt);
                                        txt.animate(Raphael.animation(Attrs.create().opacity(1), 500, Raphael.EASING_LINEAR));
                                    }
                                });
                            }
                        }));
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

    private void showFaceBook() {
        Rect bkg = paper.rect(0, facebookFeedY, canvasWidth, 558);
        bkg.attr(Attrs.create().fill("white").opacity(.7).strokeWidth(0));
        clonedLayer.add(paper.set().push(bkg));

        Element facebookElement = Document.get().getBody().getElementsByTagName("fb:like-box").getItem(0);
        facebookElement.getStyle().setDisplay(Style.Display.BLOCK);
        facebookElement.getStyle().setZIndex(100);
        facebookElement.getStyle().setPosition(Style.Position.ABSOLUTE);
        facebookElement.getStyle().setTop(facebookFeedY, Style.Unit.PX);
        facebookElement.getStyle().setLeft((Window.getClientWidth() - canvasWidth) / 2, Style.Unit.PX);
    }

    private void hideFaceBook() {
        Element facebook = Document.get().getBody().getElementsByTagName("fb:like-box").getItem(0);
        facebook.getStyle().setDisplay(Style.Display.NONE);
    }


}