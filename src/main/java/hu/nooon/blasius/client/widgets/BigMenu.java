package hu.nooon.blasius.client.widgets;

import com.google.gwt.dom.client.NativeEvent;
import org.sgx.raphael4gwt.raphael.Paper;
import org.sgx.raphael4gwt.raphael.Set;
import org.sgx.raphael4gwt.raphael.base.Attrs;
import org.sgx.raphael4gwt.raphael.event.HoverListener;

import java.util.Arrays;

public class BigMenu {

    private Set inner;

    public BigMenu(Paper paper, Set menu) {

        inner = menu;

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
    }
}
