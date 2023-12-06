package util;


import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.Serializable;
import java.util.Properties;

public class MailUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    public static boolean send(String destinatario, String assunto, String texto) {
        try {
            String host = "sandbox.smtp.mailtrap.io";
            String porta = "2525";
            String usuario = "a1f65d16aff90a"; //seu usuário do mailtrap
            String senha = "e4156147fa2cd8"; //sua senha do mailtrap

            String remetente = "nome@dominio.com";
            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", porta);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(usuario, senha);
                }
            });

            MimeMessage mensagem = new MimeMessage(session);
            mensagem.setFrom(new InternetAddress(remetente));
            mensagem.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            mensagem.setSubject(assunto);
            mensagem.setText(texto);

            Transport.send(mensagem);

            return true;

        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

}



