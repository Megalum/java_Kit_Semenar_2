package src.client;

import src.server.ServerWindow;

public class Client {
    private String name;
    private ClientView clientView;
    private ServerWindow server;
    private boolean connected;

    public Client(ClientView clientView, ServerWindow server) {
        this.clientView = clientView;
        this.server = server;
    }

    public boolean connectToServer(String name){
        this.name = name;
        if (server.server.clientAdd(this)){
            printText("Вы успешно подключились!\n");
            connected = true;
            String log = server.server.getHistory();
            if (log != null){
                printText(log);
            }
            return true;
        } else {
            printText("Подключение не удалось");
            return false;
        }
    }

    //мы посылаем
    public void sendMessage(String message){
        if (connected) {
            if (!message.isEmpty()) {
                server.server.sendMessage(name + ": " + message);
            }
        } else {
            printText("Нет подключения к серверу");
        }
    }
    //нам посылают
    public void serverAnswer(String answer){
        printText(answer);
    }

    public void disconnect(){
        if (connected) {
            connected = false;
            clientView.disconnectFromServer();
            server.server.clientDel(this);
            printText("Вы были отключены от сервера!");
        }
    }

    public String getName() {
        return name;
    }

    private void printText(String text){
        clientView.showMessage(text);
    }

}