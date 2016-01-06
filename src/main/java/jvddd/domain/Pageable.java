package jvddd.domain;

/**
 * 分页接口
 * 
 * @author Max
 * @date 2014-2-13 下午2:49:02
 * @version 1.0
 */
public interface Pageable {

	/**
	 * 数据偏移量
	 * @return
	 */
	int getOffset();

	/**
	 * 每页记录数
	 * 
	 * @return
	 */
	int getPageSize();

	/**
	 * 当前页号
	 * 
	 * @return
	 */
	int getPageNo();

	/**
	 * 返回下页的页号
	 */
	int getNextPage();

	/**
	 * 返回上页的页号
	 */
	int getPrePage();

	int first();
}
