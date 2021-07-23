package chat.ros.testing2.server.settings.services;

public interface IServicePage {

    ServicesPage uploadSoundFile(String file);

    ServicesPage clickItemLeftMenu(String item, boolean checkMenu);

    ServicesPage isIconLeftMenu(String item, String className);

}
