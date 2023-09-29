package se.miun.dt176g.xxxxyyyy.reactive;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import se.miun.dt176g.xxxxyyyy.reactive.support.Constants;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

/**
 * <h1>Client</h1>
 * //Incoming connections(receiving drawing events/objects from others over the network)should be represented as Observables.
 * @author 	Emma Pesjak
 * @version 1.0
 * @since 	2023-09-28
 */
public class Client implements ConnectionHandler, Serializable {
    private Socket socket;
    private MainFrame mainFrame;
    private static final Menu menu = new Menu();
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private DrawingPanel drawingPanel;
    private static final long serialVersionUID = 1L;
    private Drawing drawing;
    private Client client;

    /**
     * Main starting point of the application for a client.
     * @param args not applicable here.
     */
    public static void main(String[] args) {
        Client client = new Client(); // Create an instance of Client.
        MainFrame frame = new MainFrame(client, menu); // Create an instance of MainFrame and pass the client instance.
        client.setMainFrame(frame); // Set the MainFrame for the created client object.
        // Make sure GUI is created on the event dispatching thread.
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMainFrame(MainFrame frame) {
        this.mainFrame = frame;
    }

    public void connectToServer() {
        client = this;

        // Create a worker thread to avoid blocking the Swing EDT.
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    socket = new Socket(Constants.ADDRESS, Constants.PORT);

                    drawing = new Drawing();
                    drawingPanel = new DrawingPanel(drawing, menu, client);

                    mainFrame.setUpDrawing(drawingPanel);
                    mainFrame.setStatusMessage(Constants.CLIENT_CONNECT_MSG);

                    // Initialize the outputStream
                    outputStream = new ObjectOutputStream(socket.getOutputStream());
                    inputStream = new ObjectInputStream(socket.getInputStream());

                    while (true) {
                        Object receivedObject = inputStream.readObject();
                        // Draw the received shape without emitting it as an event
                        handleReceivedObject(receivedObject);
                    }

                } catch (IOException e) {
                    mainFrame.setUpFailedToConnect();
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        worker.execute(); // Start the worker thread.

}

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleReceivedObject(Object receivedObject) {
        SwingUtilities.invokeLater(() -> {
            if (receivedObject instanceof String) {  // HAHA SNYGGASTE LÖSNINGEN NÅGONSIN
                drawingPanel.clearDrawing();
            } else if (receivedObject instanceof Shape) {
                drawing.addShape((Shape) receivedObject);
                drawingPanel.repaint();
            }
        });
    }

    // HÄR FÅR JAG FAN IN SHAPEN NÄR DEN RITAS!!
    // method to handle new shapes received from DrawingPanel
    public void sendShapeToServer(Shape shape) {
        try {
            outputStream.writeObject(shape);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearEvent() {
        drawingPanel.clearDrawing();
        try {
            outputStream.writeObject("clear");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
