package hu.nooon.blasius.client.event;

import com.google.gwt.core.client.Callback;
import com.google.gwt.event.shared.GwtEvent;

import java.util.List;

public class SequenceEvent extends GwtEvent<SequenceEventHandler> {

    public static Type<SequenceEventHandler> TYPE = new Type<SequenceEventHandler>();

    private List sequence;
    private Callback callback;

    public SequenceEvent(List sequence, Callback callback) {
        this.sequence = sequence;
        this.callback = callback;
    }

    @Override
    public Type<SequenceEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SequenceEventHandler sequenceEventHandler) {
        sequenceEventHandler.invoke(this);
    }

    public List getSequence() {
        return sequence;
    }

    public Callback getCallback() {
        return callback;
    }
}
