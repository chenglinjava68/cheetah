package org.cheetah.repository;
/**
 * @author Max
 * @email max@tagsdata.com
 * @date 2014年8月28日 下午1:50:22
 * @version 1.0
 */
public abstract class Dialect {
	public enum Type {
		MYSQL,
		ORACLE
	}
	
	public abstract String getPagingSql(String sql, int firstResults, int maxResults);
}
