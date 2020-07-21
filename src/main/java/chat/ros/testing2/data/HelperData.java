package chat.ros.testing2.data;

public interface HelperData {

    /****** Команды для проверки каналов ******/
    String commandDBCheckChannel = "sudo -u roschat psql -c \"select * from channels;\" | grep '%1$s'";
    String commandDBCheckTypeChannel = commandDBCheckChannel + "| awk -F\"|\" '{print $2}'";
    String commandDBCheckProvedChannel = commandDBCheckChannel + "| awk -F\"|\" '{print $4}'";

    /****** Команда для проверки СКУД ******/
    String commandDBCheckSKUD = "sudo -u roschat psql -c \"select * from integrations;\" | grep acs";
}
