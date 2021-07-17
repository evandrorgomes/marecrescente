package br.org.cancer.modred.test.util;

import java.util.List;

public class RetornoPaginacao {
	private Boolean hasNext;
	private Integer number;
	private Boolean hasContent;
	private Boolean last;
	private Boolean first;
	private Integer totalPages;
	private Integer totalElements;
	private List<Object> content;

	/**
	 * @return the hasNext
	 */
	public Boolean getHasNext() {
		return hasNext;
	}

	/**
	 * @param hasNext the hasNext to set
	 */
	public void setHasNext(Boolean hasNext) {
		this.hasNext = hasNext;
	}

	/**
	 * @return the number
	 */
	public Integer getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(Integer number) {
		this.number = number;
	}

	/**
	 * @return the hasContent
	 */
	public Boolean getHasContent() {
		return hasContent;
	}

	/**
	 * @param hasContent the hasContent to set
	 */
	public void setHasContent(Boolean hasContent) {
		this.hasContent = hasContent;
	}

	/**
	 * @return the last
	 */
	public Boolean getLast() {
		return last;
	}

	/**
	 * @param last the last to set
	 */
	public void setLast(Boolean last) {
		this.last = last;
	}

	/**
	 * @return the first
	 */
	public Boolean getFirst() {
		return first;
	}

	/**
	 * @param first the first to set
	 */
	public void setFirst(Boolean first) {
		this.first = first;
	}

	/**
	 * @return the totalPages
	 */
	public Integer getTotalPages() {
		return totalPages;
	}

	/**
	 * @param totalPages the totalPages to set
	 */
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * @return the totalElements
	 */
	public Integer getTotalElements() {
		return totalElements;
	}

	/**
	 * @param totalElements the totalElements to set
	 */
	public void setTotalElements(Integer totalElements) {
		this.totalElements = totalElements;
	}

	/**
	 * @return the content
	 */
	public List<Object> getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(List<Object> content) {
		this.content = content;
	}

}
