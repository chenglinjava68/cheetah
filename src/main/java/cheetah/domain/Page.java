package cheetah.domain;

import java.util.List;

/**
 * Created by Max on 2016/1/6.
 */
public class Page<T> {
    private long countTotal;
    private List<T> data;
    private int size;
    private int pageNo;
    private int totalPage;

    Page() {
    }

    Page(long countTotal, List<T> data, int size, int pageNo) {
        this.countTotal = countTotal;
        this.data = data;
        this.size = size;
        this.pageNo = pageNo;
    }

    public long countTotal() {
        return countTotal;
    }

    public List<T> data() {
        return data;
    }

    public int size() {
        return size;
    }

    public int pageNo() {
        return pageNo;
    }

    public int totalPage() {
        return (int) (countTotal % size == 0 ? countTotal / size : (countTotal / size) + 1);
    }

    public static <T> Page<T> create(long countTotal, List<T> data, int size, int pageNo) {
        return new Page<T>(countTotal, data, size, pageNo);
    }

}
