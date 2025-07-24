package modals;

/**
 *
 * @author Oshan
 */
public class Product {

    private int productId;
    private String productName;
	private String productNumber;

	public Product(int productId,String productName,String productNo) {
        this.productId=productId;
        this.productName = productName;
    }

    public Product() {}

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductLocation() {}

	public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public void setProductDetails() {}

    public void setProductQty() {}

    public void setProductCost() {}

    public void setProductSelling() {}

    @Override
    public String toString() {
        return productName+" ("+productNumber+")";
    }
}
