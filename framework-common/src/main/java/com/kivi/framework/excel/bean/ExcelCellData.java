package com.kivi.framework.excel.bean;

public class ExcelCellData {
	public enum CellStyleEnums{
		cell_normal(1,"普通单元"),
		cell_header(2,"表头单元");
		
		public int code;
		public String desc;
		
		CellStyleEnums(int code, String desc){
			this.code = code;
			this.desc = desc;
		}
	}
	
	private int row ;
	private int col ;
	private int width ;
	private int style ; 
	private boolean isNeedCreate ;
	private boolean isMergeRow ;
	private boolean isMergeCols ;
	private int mergeCells;
	private Object value ;

	
	public ExcelCellData( int row, int col, Object value, boolean isCreate ){
		this.row = row ;
		this.col = col ;
		this.value = value ;
		this.isNeedCreate = isCreate ;
		this.style = CellStyleEnums.cell_normal.code;
		this.isMergeCols = false;
		this.isMergeRow = false;
		this.mergeCells = 0 ;
		this.width = 0 ;
	}
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}

	public void setWidth(int width) {
		if(width>60)
			width = 60;
		this.width = width;
	}

	public int getWidth() {
		return width;
	}

	public void setNeedCreate(boolean isNeedCreate) {
		this.isNeedCreate = isNeedCreate;
	}

	public boolean isNeedCreate() {
		return isNeedCreate;
	}

	/**
	 * @return the style
	 */
	public int getStyle() {
		return style;
	}

	/**
	 * @param style the style to set
	 */
	public void setStyle(int style) {
		this.style = style;
	}

	/**
	 * @return the isMergeRow
	 */
	public boolean isMergeRow() {
		return isMergeRow;
	}

	/**
	 * @param isMergeRow the isMergeRow to set
	 */
	public void setMergeRow(boolean isMergeRow) {
		this.isMergeRow = isMergeRow;
	}

	/**
	 * @return the isMergeCols
	 */
	public boolean isMergeCols() {
		return isMergeCols;
	}

	/**
	 * @param isMergeCols the isMergeCols to set
	 */
	public void setMergeCols(boolean isMergeCols) {
		this.isMergeCols = isMergeCols;
	}

	/**
	 * @return the mergeCells
	 */
	public int getMergeCells() {
		return mergeCells;
	}

	/**
	 * @param mergeCells the mergeCells to set
	 */
	public void setMergeCells(int mergeCells) {
		this.mergeCells = mergeCells;
	}
	
	
}
