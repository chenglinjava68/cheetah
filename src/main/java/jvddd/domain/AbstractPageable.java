package jvddd.domain;

/**
 * Created by Max on 2016/1/6.
 */
public abstract class AbstractPageable implements Pageable {
    private final int page;
    private final int size;

    public AbstractPageable(int page, int size) {
        if(page < 0) {
            throw new IllegalArgumentException("Page index must not be less than zero!");
        } else if(size < 1) {
            throw new IllegalArgumentException("Page size must not be less than one!");
        } else {
            this.page = page;
            this.size = size;
        }
    }

    @Override
    public int getOffset() {
        return this.page * this.size;
    }

    @Override
    public int getPageSize() {
        return this.size;
    }

    @Override
    public int getPageNo() {
        return this.page;
    }

    public boolean hasPrevious() {
        return this.page > 0;
    }

}
