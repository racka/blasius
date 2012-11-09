package hu.nooon.blasius.client.resource;

import com.google.gwt.core.client.Callback;
import hu.nooon.blasius.client.widgets.MitsouGallery;
import org.sgx.raphael4gwt.raphael.Paper;
import org.sgx.raphael4gwt.raphael.Set;

public class BlasiusBundle extends ResourceUtils {

    public String getBackgroundURL() {
        return getFileURL("Common", "background.jpg");
    }

    private StringBuilder about = new StringBuilder("");
    public void getAboutText(Callback callback) {
        getTextResource(about, "Text_HU", "about.txt", callback);
    }

    private StringBuilder contact = new StringBuilder("");
    public void getContactText(Callback callback) {
        getTextResource(contact, "Text_HU", "contact.txt", callback);
    }

    private StringBuilder order = new StringBuilder("");
    public void getOrderText(Callback callback) {
        getTextResource(order, "Text_HU", "order.txt", callback);
    }

    private Set bass4String;
    public void getBass4StringCover(Paper paper, Callback callback) {
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


    private Set bass5String;
    public void getBass5StringCover(Paper paper, Callback callback) {
        if (bass5String == null) {
            bass5String = paper.set();
        }
        getSVGImage(paper, bass5String, "5StringBasses", "cover.jpg", callback);
    }

    private MitsouGallery bass5StringGallery;
    public MitsouGallery getBass5StringGallery(Paper paper, int x, int y, int width, int thumbWidth, int thumbHeight) {
        if (bass5StringGallery == null) {
            bass5StringGallery = new MitsouGallery(paper, getFolderFileURLs("5StringBasses", "cover.jpg"), x, y, width, thumbWidth, thumbHeight);
            bass5StringGallery.fetchImages();
        }

        return bass5StringGallery;
    }

    private Set endorsers;
    public void getEndorsersCover(Paper paper, Callback callback) {
        if (endorsers == null) {
            endorsers = paper.set();
        }
        getSVGImage(paper, endorsers, "Endorsers", "cover.jpg", callback);
    }

    private MitsouGallery endorsersGallery;
    public MitsouGallery getEndorsersGallery(Paper paper, int x, int y, int width, int thumbWidth, int thumbHeight) {
        if (endorsersGallery == null) {
            endorsersGallery = new MitsouGallery(paper, getFolderFileURLs("Endorsers", "cover.jpg"), x, y, width, thumbWidth, thumbHeight);
            endorsersGallery.fetchImages();
        }

        return endorsersGallery;
    }

    private Set exhibitions;
    public void getExhibitionsCover(Paper paper, Callback callback) {
        if (exhibitions == null) {
            exhibitions = paper.set();
        }
        getSVGImage(paper, exhibitions, "Exhibitions", "cover.jpg", callback);
    }

    private MitsouGallery exhibitionsGallery;
    public MitsouGallery getExhibitionsGallery(Paper paper, int x, int y, int width, int thumbWidth, int thumbHeight) {
        if (exhibitionsGallery == null) {
            exhibitionsGallery = new MitsouGallery(paper, getFolderFileURLs("Exhibitions", "cover.jpg"), x, y, width, thumbWidth, thumbHeight);
            exhibitionsGallery.fetchImages();
        }

        return exhibitionsGallery;
    }

    private Set owners;
    public void getOwnersCover(Paper paper, Callback callback) {
        if (owners == null) {
            owners = paper.set();
        }
        getSVGImage(paper, owners, "Owners", "cover.jpg", callback);
    }

    private MitsouGallery ownersGallery;
    public MitsouGallery getOwnersGallery(Paper paper, int x, int y, int width, int thumbWidth, int thumbHeight) {
        if (ownersGallery == null) {
            ownersGallery = new MitsouGallery(paper, getFolderFileURLs("Owners", "cover.jpg"), x, y, width, thumbWidth, thumbHeight);
            ownersGallery.fetchImages();
        }

        return ownersGallery;
    }

}
