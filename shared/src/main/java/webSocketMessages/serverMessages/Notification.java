package webSocketMessages.serverMessages;

class Notification extends ServerMessage {

    public Notification(String message) {
        super(ServerMessageType.NOTIFICATION);
    }

}