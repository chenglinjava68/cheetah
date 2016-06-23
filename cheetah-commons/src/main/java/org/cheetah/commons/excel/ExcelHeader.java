package org.cheetah.commons.excel;

/**
 * 用来存储Excel标题的对象，通过该对象可以获取标题和方法的对应关系
 *
 * @author Max
 * @date 2015/5/29
 */
public class ExcelHeader implements Comparable<ExcelHeader> {
    /**
     * excel的标题名称
     */
    private String title;
    /**
     * 每一个标题的顺序
     */
    private int order;
    /**
     * 说对应方法名称
     */
    private String methodName;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getOrder()
    {
        return order;
    }

    public void setOrder(int order)
    {
        this.order = order;
    }

    public String getMethodName()
    {
        return methodName;
    }

    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }

    public ExcelHeader(String title, int order, String methodName)
    {
        super();
        this.title = title;
        this.order = order;
        this.methodName = methodName;
    }

    @Override
    public int compareTo(ExcelHeader o)
    {
        return order > o.order ? 1 : (order < o.order ? -1 : 0);
    }

    @Override
    public String toString()
    {
        return order + "," + title + "," + methodName;
    }
}