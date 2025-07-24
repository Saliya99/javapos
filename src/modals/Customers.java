package modals;

/**
 * @author Oshan
 */
public class Customers {


	private int clientId;
	private String clientFirstName;
	private String clientLastName;

	public Customers(int clientId, String clientFirstName, String clientLastName) {
		this.clientId = clientId;
		this.clientFirstName = clientFirstName;
		this.clientLastName = clientLastName;
	}

	public Customers() {
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public void setClientFirstName(String clientFirstName) {
		this.clientFirstName = clientFirstName;
	}

	public void setClientLastName(String clientLastName) {
		this.clientLastName = clientLastName;
	}

	public void setClientContactNumber() {}

	@Override
	public String toString() {
		return clientFirstName + " " + clientLastName;
	}
}
