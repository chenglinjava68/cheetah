package org.cheetah.repository;
/**
 * @author Max
 * @email max@tagsdata.com
 * @date 2014年8月28日 下午1:52:11
 * @version 1.0
 */
public class MysqlDialect extends Dialect{

	@Override
	public String getPagingSql(String sql, int firstResults, int maxResults) {
		sql = sql.trim();
		StringBuilder pagesql = new StringBuilder();
		pagesql.append(sql);
		pagesql.append(" limit " + firstResults + ", " + maxResults);
		return pagesql.toString();
	}

}
