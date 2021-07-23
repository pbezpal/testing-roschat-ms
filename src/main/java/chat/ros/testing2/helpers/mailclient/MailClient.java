package chat.ros.testing2.helpers.mailclient;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Properties;

public class MailClient implements IMailClient {

    private String server = null;
    private String username = null;
    private String password = null;

    public MailClient(String server, String username, String password){
        this.server = server;
        this.username = username;
        this.password = password;
    }

    private Folder connectToMailServer(){
        Session emailSession = Session.getDefaultInstance(new Properties());
        Folder emailFolder;
        try {
            Store store = emailSession.getStore("imaps");
            try {
                store.connect(server, username, password);

                emailFolder = store.getFolder("INBOX");
                emailFolder.open(Folder.READ_WRITE);
            } catch (MessagingException e) {
                e.printStackTrace();
                return null;
            }
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            return null;
        }

        return emailFolder;
    }

    @Override
    public boolean getSubjectFromLastMail(String subject) {
        String getSubject;
        boolean result = false;
        Message[] messages;
        Folder folder = connectToMailServer();
        if(folder != null){
            try {
                messages = folder.search(
                        new FlagTerm(new Flags(Flags.Flag.SEEN), false)
                );
            } catch (MessagingException e) {
                e.printStackTrace();
                try {
                    folder.close(true);
                } catch (MessagingException exit) {
                    e.printStackTrace();
                }
                return result;
            }

            if(messages.length > 0) {
                Arrays.sort(messages, (m1, m2) -> {
                    try {
                        return m2.getSentDate().compareTo(m1.getSentDate());
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }
                });

                for (Message message : messages) {
                    try {
                        getSubject = message.getSubject();
                    } catch (MessagingException e) {
                        e.printStackTrace();
                        return result;
                    }

                    if (getSubject.equals(subject)) {
                        result = true;
                        break;
                    }
                }
            }

            try {
                folder.close(true);
            } catch (MessagingException e) {
                e.printStackTrace();
            }

            return result;
        }
        return result;
    }

    @Override
    public boolean getContentFromLastMail(String content) {
        String getContent = null;
        boolean result = false;
        Message[] messages;
        Folder folder = connectToMailServer();
        if(folder != null){
            try {
                messages = folder.search(
                        new FlagTerm(new Flags(Flags.Flag.SEEN), false)
                );
            } catch (MessagingException e) {
                e.printStackTrace();
                try {
                    folder.close(true);
                } catch (MessagingException exit) {
                    e.printStackTrace();
                }
                return result;
            }

            if(messages.length > 0) {
                Arrays.sort(messages, (m1, m2) -> {
                    try {
                        return m2.getSentDate().compareTo(m1.getSentDate());
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }
                });

                for (Message message : messages) {
                    try {
                        getContent = (String) message.getContent();
                    } catch (UnsupportedEncodingException uex) {
                        try {
                            InputStream is = message.getInputStream();
                            getContent = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (getContent.contains("Тестовое сообщение от Росчата")) result = true;
                }
            }

            try {
                folder.close(true);
            } catch (MessagingException e) {
                e.printStackTrace();
            }

            return result;

        }
        return result;
    }

    @Override
    public void deleteAllMailFromMailServer() {
        Message[] messages;
        Folder folder = connectToMailServer();
        if(folder != null){
            try {
                messages = folder.getMessages();
            } catch (MessagingException e) {
                e.printStackTrace();
                try {
                    folder.close(true);
                } catch (MessagingException exit) {
                    e.printStackTrace();
                }
                return;
            }

            if(messages.length > 0) {
                for (Message message : messages) {
                    try {
                        message.setFlag(Flags.Flag.DELETED, true);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                folder.close(true);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
}
