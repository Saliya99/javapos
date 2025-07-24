package modals;

/**
 * @author Oshan
 */
public class PriceBatch {

	private int itemPBId;
	private String itemName;
	private double itemCost;
	private double itemSelling;

	public PriceBatch(int itemPBId, String itemName, double itemCost) {
		this.itemPBId = itemPBId;
		this.itemName = itemName;
		this.itemCost = itemCost;
	}

	public PriceBatch() {}

	public int getProductItemPBID() {
		System.out.println("returning ID" + itemPBId);
		return itemPBId;
	}

	public void setProductItemPBID(int itemPBID) {

		this.itemPBId = itemPBID;
	}

	public void setProductItemName(String itemName) {
		this.itemName = itemName;
	}

	public Double getProductItemCost() {
		return itemCost;
	}

	public void setProductItemCost(Double itemCost) {
		this.itemCost = itemCost;
	}

	public Double getProductItemSelling() {
		return itemSelling;
	}

	public void setProductItemSelling(Double itemSelling) {
		this.itemSelling = itemSelling;
	}

	@Override
	public String toString() {
		return itemName + "(" + itemPBId + ")";
	}
}
