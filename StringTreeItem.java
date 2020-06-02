package TC.Utils.StringTree;

public class StringTreeItem<T> {
	private int offset;
	private String str;
	private T value;

	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
	
	public StringTreeItem() {
		
	}
	public StringTreeItem(int poffset, String pstr, T pvalue) {
		offset = poffset;
		str = pstr;
		value = pvalue;
	}
}
