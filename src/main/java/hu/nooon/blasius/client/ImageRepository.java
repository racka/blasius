package hu.nooon.blasius.client;

import com.google.gwt.resources.client.ImageResource;
import hu.nooon.blasius.client.resource.SiteClientBundle;
import org.sgx.raphael4gwt.raphael.Paper;
import org.sgx.raphael4gwt.raphael.Set;
import org.sgx.raphael4gwt.raphael.base.Attrs;

public class ImageRepository {

    private SiteClientBundle clientBundle = SiteClientBundle.INSTANCE;

    private Set menuArchive, menuExhibitions, menuFinish, menuNew, menuOwners;

    private Paper paper;


    public ImageRepository(Paper paper) {
        this.paper = paper;
    }

    private Set addToRepository(Paper paper, ImageResource... resources) {
        Set container = paper.set();
        for (ImageResource resource : resources) {
            container.push(paper.image(resource, 0, 0, resource.getWidth(), resource.getHeight()).hide());
        }
        container.attr(Attrs.create().opacity(0));
        return container;
    }


    public Set getMenuArchive() {
        if (menuArchive == null) {
            menuArchive = addToRepository(paper, clientBundle.archive_cover());
        }
        return menuArchive;
    }

    public Set getMenuExhibitions() {
        if (menuExhibitions == null) {
            menuExhibitions = addToRepository(paper, clientBundle.exhibitions_cover());
        }
        return menuExhibitions;
    }

    public Set getMenuEndorsers() {
        if (menuFinish == null) {
            menuFinish = addToRepository(paper, clientBundle.endorsers_cover());
        }
        return menuFinish;
    }

    public Set getMenuNew() {
        if (menuNew == null) {
            menuNew = addToRepository(paper, clientBundle.new_cover());
        }
        return menuNew;
    }

    public Set getMenuOwners() {
        if (menuOwners == null) {
            menuOwners = addToRepository(paper, clientBundle.owners_cover());
        }
        return menuOwners;
    }
}
