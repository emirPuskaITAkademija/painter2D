package commons;

@FunctionalInterface
public interface Refreshable {

    void refresh();

    Refreshable EMPTY = () -> {
        //EMPTY BLOCK
    };
}
