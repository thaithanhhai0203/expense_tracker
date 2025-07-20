package com.learn.expense_tracker.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.Body;
import software.amazon.awssdk.services.ses.model.Content;
import software.amazon.awssdk.services.ses.model.Destination;
import software.amazon.awssdk.services.ses.model.Message;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;

@Service
public class EmailService {
  @Value("${spring.mail.from}")
  private String fromEmail;

  private final SesClient sesClient;

  public EmailService(SesClient sesClient) {
    this.sesClient = sesClient;
  }

  public void sendResetPasswordEmail(String toEmail, String resetLink) {
    SendEmailRequest request =
        SendEmailRequest.builder()
            .source(fromEmail)
            .destination(Destination.builder().toAddresses(toEmail).build())
            .message(
                Message.builder()
                    .subject(Content.builder().data("Reset your password").build())
                    .body(
                        Body.builder()
                            .text(
                                Content.builder()
                                    .data("Click here to reset:\n " + resetLink)
                                    .build())
                            .build())
                    .build())
            .build();

    sesClient.sendEmail(request);
  }
}
