package net.freedomserg.restaurant.core.model.exception;

public class RestaurantException extends RuntimeException {

    public RestaurantException() {
        super();
    }

    public RestaurantException(String message) {
        super(message);
    }
}
