package se.miun.dt176g.xxxxyyyy.reactive;

/**
 * <h1>ConnectionHandler</h1>
 * Interface for server/clients.
 * @author 	Emma Pesjak
 * @version 1.0
 * @since 	2023-10-06
 */
public interface ConnectionHandler {

    /**
     * For emitting that the Drawing should be cleared.
     */
    void clearEvent();

    /**
     * Receives an Object that can be a String for clearing the canvas, that the Server has disconnected, or a
     * Shape to draw on the canvas.
     * @param receivedObject is the received object.
     */
    void handleReceivedObject(Object receivedObject);

    /**
     * Sets the MainFrame.
     * @param mainFrame is the MainFrame.
     */
    void setMainFrame(MainFrame mainFrame);

    /**
     * Handles shutdown gracefully, disposing recourses.
     */
    void shutDown();

    /**
     * Sends a Shape object to the server/clients using the outgoing data observer.
     * @param shape is the Shape to send.
     */
    void sendShape(Shape shape);
}
