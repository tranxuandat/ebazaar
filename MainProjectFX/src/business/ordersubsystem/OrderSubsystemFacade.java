package business.ordersubsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import middleware.exceptions.DatabaseException;
import business.exceptions.BackendException;
import business.externalinterfaces.CustomerProfile;
import business.externalinterfaces.Order;
import business.externalinterfaces.OrderItem;
import business.externalinterfaces.OrderSubsystem;
import business.externalinterfaces.ShoppingCart;

public class OrderSubsystemFacade implements OrderSubsystem {
	private static final Logger LOG = 
			Logger.getLogger(OrderSubsystemFacade.class.getPackage().getName());
	CustomerProfile custProfile;
	    
    public OrderSubsystemFacade(CustomerProfile custProfile){
        this.custProfile = custProfile;
    }
	
	
	
	/** Used whenever an order item needs to be created from outside the order subsystem */
    public static OrderItem createOrderItem(Integer prodId,Integer orderId, String quantityReq, String totalPrice) {
    	return null;
    }
    
    /** to create an Order object from outside the subsystem */
    public static Order createOrder(Integer orderId, String orderDate, String totalPrice) {
    	return null;
    }
    
    ///////////// Methods internal to the Order Subsystem -- NOT public
    List<Integer> getAllOrderIds() throws DatabaseException {
        
        DbClassOrder dbClass = new DbClassOrder();
        return dbClass.getAllOrderIds(custProfile);
        
    }
    List<OrderItem> getOrderItems(Integer orderId) throws DatabaseException {
        DbClassOrder dbClass = new DbClassOrder();
        return dbClass.getOrderItems(orderId);
    }
    
    OrderImpl getOrderData(Integer orderId) throws DatabaseException {
    	DbClassOrder dbClass = new DbClassOrder();
    	return dbClass.getOrderData(orderId);
    }



	@Override
	public List<Order> getOrderHistory() throws BackendException {
		try {
			DbClassOrder dbClass = new DbClassOrder();
			List<Order> orders = new ArrayList<Order>();
	        List<Integer> orderIds = dbClass.getAllOrderIds(custProfile);
	        for (int orderId : orderIds) {
	        	List<OrderItem> orderItems = dbClass.getOrderItems(orderId);
	        	OrderImpl order = dbClass.getOrderData(orderId);
	        	order.setOrderItems(orderItems);
	        	orders.add(order);
	        }
	        return orders;
		} catch (DatabaseException e) {
			throw new BackendException(e);
		}
	}



	@Override
	public void submitOrder(ShoppingCart shopCart) throws BackendException {
		try {
			DbClassOrder dbClass = new DbClassOrder();
			dbClass.submitOrder(shopCart);
		} catch (DatabaseException e) {
			throw new BackendException(e);
		}
		
	}
}
