package hu.nooon.blasius.client.widgets;

public interface CustomLayer {

    CustomLayer clear();

    CustomLayer show(boolean animated);
    CustomLayer hide(boolean animated);

    CustomLayer removeShape(int index, boolean animated);

}
