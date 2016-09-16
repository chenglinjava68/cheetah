package org.cheetah.commons.excel;

import java.lang.reflect.Type;

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
     * 说对应方法名称或者字段名
     */
    private String targetName;

    private Type type;

    public boolean targetNameisField() {
        return !targetName.startsWith("get") && !targetName.startsWith("setValue");
    }

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

    public String getTargetName()
    {
        return targetName;
    }

    public void setTargetName(String targetName)
    {
        this.targetName = targetName;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public ExcelHeader(String title, int order, String targetName, Type type)
    {
        super();
        this.title = title;
        this.order = order;
        this.targetName = targetName;
        this.type = type;
    }

    @Override
    public int compareTo(ExcelHeader o)
    {
        return order > o.order ? 1 : (order < o.order ? -1 : 0);
    }

    @Override
    public String toString()
    {
        return order + "," + title + "," + targetName + "," + type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExcelHeader that = (ExcelHeader) o;

        if (order != that.order) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return type != null ? type.equals(that.type) : that.type == null;

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + order;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
