package chat.ros.testing2.helpers.mailclient;

public interface IMailClient {

    /**
     * <p>getting subject from last the last mail</p>
     * @param subject
     * @return
     */
    boolean getSubjectFromLastMail(String subject);

    /**
     * <p>getting content from the last mail</p>
     * @param content
     * @return
     */
    boolean getContentFromLastMail(String content);

    /**
     * <p>delete all mail from mail server</p>
     */
    void deleteAllMailFromMailServer();

}
