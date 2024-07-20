package resources;

public enum APIResources {
	
	Login("/api/ecom/auth/login"),
	CreateProduct("/api/ecom/product/add-product"),
	PlaceOrder("/api/ecom/order/create-order"),
	DeleteOrder("/api/ecom/product/delete-product"),
	ViewOrderDetails("/api/ecom/order/get-orders-details");
	private String endPoint;
	
	APIResources(String endPoint){
		this.endPoint=endPoint;
	}
	
	public String getEndPoint() {
		return endPoint;
	}
	
}
