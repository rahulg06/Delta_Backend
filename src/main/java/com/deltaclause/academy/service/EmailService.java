package com.deltaclause.academy.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired(required = false)
    private JavaMailSender mailSender;

    /**
     * Sends a beautiful dynamic HTML registration verification letter containing the OTP code.
     */
    public void sendOtpEmail(String recipientEmail, String studentName, String otpCode) {
        if (mailSender == null) {
            System.out.println("[MOCK SMTP] Dispatching OTP to " + recipientEmail + ": " + otpCode);
            return;
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(recipientEmail);
            helper.setSubject("Deltaclause Academy - Verify Your Registration");

            String htmlBody = "<div style=\"font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; border: 1px solid #e2e8f0; border-radius: 12px; background-color: #ffffff;\">"
                    + "  <div style=\"text-align: center; margin-bottom: 24px;\">"
                    + "    <h2 style=\"color: #4f46e5; margin: 0; font-size: 26px; font-weight: 700;\">Deltaclause</h2>"
                    + "    <p style=\"color: #64748b; font-size: 11px; margin: 4px 0 0 0; text-transform: uppercase; letter-spacing: 1px;\">Intelligent Project Academy</p>"
                    + "  </div>"
                    + "  <hr style=\"border: none; border-top: 1px solid #f1f5f9; margin-bottom: 24px;\" />"
                    + "  <p style=\"font-size: 16px; color: #1e293b; margin: 0 0 16px 0;\">Dear " + studentName + ",</p>"
                    + "  <p style=\"font-size: 14px; color: #475569; line-height: 22px; margin: 0 0 24px 0;\">"
                    + "    Thank you for initiating your enrollment at Deltaclause. To complete your secure account registration and access your dashboard catalog, please verify your email using the 6-digit passcode provided below:"
                    + "  </p>"
                    + "  <div style=\"background-color: #f8fafc; border: 1px solid #cbd5e1; border-radius: 8px; padding: 16px; text-align: center; margin-bottom: 24px;\">"
                    + "    <span style=\"font-family: 'Courier New', Courier, monospace; font-size: 36px; font-weight: 700; color: #0f172a; letter-spacing: 6px;\">" + otpCode + "</span>"
                    + "  </div>"
                    + "  <p style=\"font-size: 12px; color: #94a3b8; line-height: 18px; margin: 0 0 24px 0; text-align: center;\">"
                    + "    This verification passcode is confidential and valid for exactly <strong>5 minutes</strong>. If you did not request this email, please secure your credentials immediately."
                    + "  </p>"
                    + "  <hr style=\"border: none; border-top: 1px solid #f1f5f9; margin-bottom: 20px;\" />"
                    + "  <div style=\"text-align: center; color: #94a3b8; font-size: 11px;\">"
                    + "    Deltaclause Intelligent Academy &bull; verify@deltaclause.com &bull; https://deltaclause.com"
                    + "  </div>"
                    + "</div>";

            helper.setText(htmlBody, true);
            mailSender.send(message);

        } catch (Exception e) {
            System.err.println("[SMTP FAILURE] Unable to route verification email to " + recipientEmail + " due to connection or certificate/SSL issues: " + e.getMessage());
            System.err.println("[FALLBACK ACTIVE] Please use this 6-digit code to complete registration: >>> " + otpCode + " <<<");
        }
    }

    /**
     * Sends a beautiful dynamic HTML password reset OTP code.
     */
    public void sendPasswordResetOtpEmail(String recipientEmail, String studentName, String otpCode) {
        if (mailSender == null) {
            System.out.println("[MOCK SMTP] Dispatching Password Reset OTP to " + recipientEmail + ": " + otpCode);
            return;
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(recipientEmail);
            helper.setSubject("Deltaclause Academy - Password Reset Code");

            String htmlBody = "<div style=\"font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; border: 1px solid #e2e8f0; border-radius: 12px; background-color: #ffffff;\">"
                    + "  <div style=\"text-align: center; margin-bottom: 24px;\">"
                    + "    <h2 style=\"color: #4f46e5; margin: 0; font-size: 26px; font-weight: 700;\">Deltaclause</h2>"
                    + "    <p style=\"color: #64748b; font-size: 11px; margin: 4px 0 0 0; text-transform: uppercase; letter-spacing: 1px;\">Intelligent Project Academy</p>"
                    + "  </div>"
                    + "  <hr style=\"border: none; border-top: 1px solid #f1f5f9; margin-bottom: 24px;\" />"
                    + "  <p style=\"font-size: 16px; color: #1e293b; margin: 0 0 16px 0;\">Dear " + studentName + ",</p>"
                    + "  <p style=\"font-size: 14px; color: #475569; line-height: 22px; margin: 0 0 24px 0;\">"
                    + "    We received a request to reset your Deltaclause account password. To complete your password reset, please enter the temporary 6-digit confirmation OTP code provided below:"
                    + "  </p>"
                    + "  <div style=\"background-color: #f8fafc; border: 1px solid #cbd5e1; border-radius: 8px; padding: 16px; text-align: center; margin-bottom: 24px;\">"
                    + "    <span style=\"font-family: 'Courier New', Courier, monospace; font-size: 36px; font-weight: 700; color: #ef4444; letter-spacing: 6px;\">" + otpCode + "</span>"
                    + "  </div>"
                    + "  <p style=\"font-size: 12px; color: #94a3b8; line-height: 18px; margin: 0 0 24px 0; text-align: center;\">"
                    + "    This password recovery passcode is confidential and valid for exactly <strong>5 minutes</strong>. If you did not request a password change, please ignore this email and verify your login status immediately."
                    + "  </p>"
                    + "  <hr style=\"border: none; border-top: 1px solid #f1f5f9; margin-bottom: 20px;\" />"
                    + "  <div style=\"text-align: center; color: #94a3b8; font-size: 11px;\">"
                    + "    Deltaclause Intelligent Academy &bull; verify@deltaclause.com &bull; https://deltaclause.com"
                    + "  </div>"
                    + "</div>";

            helper.setText(htmlBody, true);
            mailSender.send(message);

        } catch (Exception e) {
            System.err.println("[SMTP FAILURE] Unable to route password reset email to " + recipientEmail + " due to connection issues: " + e.getMessage());
            System.err.println("[FALLBACK ACTIVE] Please use this 6-digit code to complete password reset: >>> " + otpCode + " <<<");
        }
    }

    /**
     * Sends a beautiful dynamic HTML Offer Letter to the student after payment approval.
     */
    public void sendOfferLetterEmail(String recipientEmail, String studentName, String internshipTitle) {
        if (mailSender == null) {
            System.out.println("[MOCK SMTP] Dispatching Offer Letter to " + recipientEmail + " for " + internshipTitle);
            return;
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(recipientEmail);
            helper.setSubject("Deltaclause Academy - Internship Offer Letter: " + internshipTitle);

            String htmlBody = "<div style=\"font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; max-width: 650px; margin: auto; padding: 30px; border: 1px solid #e2e8f0; border-radius: 16px; background-color: #ffffff; color: #1e293b;\">"
                    + "  <div style=\"text-align: center; margin-bottom: 30px;\">"
                    + "    <h2 style=\"color: #4f46e5; margin: 0; font-size: 28px; font-weight: 800; tracking-tight;\">Deltaclause</h2>"
                    + "    <p style=\"color: #64748b; font-size: 11px; margin: 4px 0 0 0; text-transform: uppercase; letter-spacing: 1.5px;\">Intelligent Project Academy</p>"
                    + "  </div>"
                    + "  <hr style=\"border: none; border-top: 1px solid #f1f5f9; margin-bottom: 30px;\" />"
                    + "  "
                    + "  <h3 style=\"color: #0f172a; font-size: 20px; font-weight: 700; margin-top: 0; margin-bottom: 16px;\">INTERNSHIP OFFER LETTER</h3>"
                    + "  "
                    + "  <p style=\"font-size: 15px; line-height: 24px; margin: 0 0 16px 0;\">Dear <strong>" + studentName + "</strong>,</p>"
                    + "  "
                    + "  <p style=\"font-size: 14px; line-height: 24px; color: #475569; margin: 0 0 20px 0;\">"
                    + "    Congratulations! On behalf of <strong>Deltaclause Intelligent Academy</strong>, we are thrilled to offer you a position as an <strong>Industrial Program Intern</strong> specializing in <strong>" + internshipTitle + "</strong>."
                    + "  </p>"
                    + "  "
                    + "  <p style=\"font-size: 14px; line-height: 24px; color: #475569; margin: 0 0 20px 0;\">"
                    + "    Your payment reference screenshot has been verified and approved by our administrators. Your learning access and task sheets have been unlocked on your dashboard. Below are your core program parameters:"
                    + "  </p>"
                    + "  "
                    + "  <div style=\"background-color: #f8fafc; border: 1px solid #e2e8f0; border-radius: 12px; padding: 20px; margin-bottom: 30px;\">"
                    + "    <table style=\"width: 100%; border-collapse: collapse;\">"
                    + "      <tr>"
                    + "        <td style=\"padding: 6px 0; font-size: 13px; color: #64748b; width: 35%; font-weight: 600;\">Program Title:</td>"
                    + "        <td style=\"padding: 6px 0; font-size: 13px; color: #0f172a; font-weight: 700;\">" + internshipTitle + "</td>"
                    + "      </tr>"
                    + "      <tr>"
                    + "        <td style=\"padding: 6px 0; font-size: 13px; color: #64748b; font-weight: 600;\">Candidate:</td>"
                    + "        <td style=\"padding: 6px 0; font-size: 13px; color: #0f172a; font-weight: 700;\">" + studentName + "</td>"
                    + "      </tr>"
                    + "      <tr>"
                    + "        <td style=\"padding: 6px 0; font-size: 13px; color: #64748b; font-weight: 600;\">Status:</td>"
                    + "        <td style=\"padding: 6px 0; font-size: 13px; color: #10b981; font-weight: 700; text-transform: uppercase;\">Approved & Active</td>"
                    + "      </tr>"
                    + "      <tr>"
                    + "        <td style=\"padding: 6px 0; font-size: 13px; color: #64748b; font-weight: 600;\">Location:</td>"
                    + "        <td style=\"padding: 6px 0; font-size: 13px; color: #0f172a; font-weight: 700;\">Remote (Deltaclause Portal)</td>"
                    + "      </tr>"
                    + "    </table>"
                    + "  </div>"
                    + "  "
                    + "  <p style=\"font-size: 14px; line-height: 24px; color: #475569; margin: 0 0 24px 0;\">"
                    + "    To begin, please log in to your Deltaclause account, download the <strong>Task Sheet PDF</strong> in your progress section, and build standard modules. Once ready, commit your codebase to GitHub and submit for evaluation."
                    + "  </p>"
                    + "  "
                    + "  <p style=\"font-size: 14px; line-height: 24px; color: #475569; margin: 0 0 24px 0;\">"
                    + "    Welcome to a premium engineering community. We hope this program serves as an accelerator for your software developer career."
                    + "  </p>"
                    + "  "
                    + "  <p style=\"font-size: 13.5px; line-height: 22px; color: #0f172a; margin: 30px 0 0 0;\">"
                    + "    Sincerely,<br />"
                    + "    <strong>HR and Operations Team</strong><br />"
                    + "    Deltaclause Intelligent Academy"
                    + "  </p>"
                    + "  "
                    + "  <hr style=\"border: none; border-top: 1px solid #f1f5f9; margin-top: 40px; margin-bottom: 20px;\" />"
                    + "  <div style=\"text-align: center; color: #94a3b8; font-size: 11px;\">"
                    + "    Deltaclause Intelligent Academy &bull; verify@deltaclause.com &bull; https://deltaclause.com"
                    + "  </div>"
                    + "</div>";

            helper.setText(htmlBody, true);
            mailSender.send(message);

        } catch (Exception e) {
            System.err.println("[SMTP FAILURE] Unable to route Offer Letter to " + recipientEmail + ": " + e.getMessage());
        }
    }

    /**
     * Sends a beautiful dynamic HTML Completion Certificate Email to the student after task evaluation.
     */
    public void sendCertificateEmail(String recipientEmail, String studentName, String internshipTitle, String certificateId) {
        if (mailSender == null) {
            System.out.println("[MOCK SMTP] Dispatching Certificate to " + recipientEmail + " for " + internshipTitle + " with ID " + certificateId);
            return;
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(recipientEmail);
            helper.setSubject("Deltaclause Academy - Internship Completion Certificate: " + internshipTitle);

            String htmlBody = "<div style=\"font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; max-width: 650px; margin: auto; padding: 30px; border: 1px solid #e2e8f0; border-radius: 16px; background-color: #ffffff; color: #1e293b;\">"
                    + "  <div style=\"text-align: center; margin-bottom: 30px;\">"
                    + "    <h2 style=\"color: #10b981; margin: 0; font-size: 28px; font-weight: 800; tracking-tight;\">Deltaclause</h2>"
                    + "    <p style=\"color: #64748b; font-size: 11px; margin: 4px 0 0 0; text-transform: uppercase; letter-spacing: 1.5px;\">Intelligent Project Academy</p>"
                    + "  </div>"
                    + "  <hr style=\"border: none; border-top: 1px solid #f1f5f9; margin-bottom: 30px;\" />"
                    + "  "
                    + "  <h3 style=\"color: #0f172a; font-size: 20px; font-weight: 700; margin-top: 0; margin-bottom: 16px; text-align: center;\">🎓 CERTIFICATE OF INTERNSHIP COMPLETION</h3>"
                    + "  "
                    + "  <p style=\"font-size: 15px; line-height: 24px; margin: 0 0 16px 0;\">Dear <strong>" + studentName + "</strong>,</p>"
                    + "  "
                    + "  <p style=\"font-size: 14px; line-height: 24px; color: #475569; margin: 0 0 20px 0;\">"
                    + "    It is with immense pride that we congratulate you on successfully graduating and completing your industrial program! Your task sheets and code submissions have been graded and approved by Deltaclause Review Board."
                    + "  </p>"
                    + "  "
                    + "  <p style=\"font-size: 14px; line-height: 24px; color: #475569; margin: 0 0 20px 0;\">"
                    + "    We are delighted to present you with your official Certification of Competency. This credential acknowledges your professional standard of development during the program:"
                    + "  </p>"
                    + "  "
                    + "  <div style=\"background-color: #f0fdf4; border: 1px solid #bbf7d0; border-radius: 12px; padding: 20px; margin-bottom: 30px;\">"
                    + "    <table style=\"width: 100%; border-collapse: collapse;\">"
                    + "      <tr>"
                    + "        <td style=\"padding: 6px 0; font-size: 13px; color: #475569; width: 35%; font-weight: 600;\">Program Title:</td>"
                    + "        <td style=\"padding: 6px 0; font-size: 13px; color: #166534; font-weight: 700;\">" + internshipTitle + "</td>"
                    + "      </tr>"
                    + "      <tr>"
                    + "        <td style=\"padding: 6px 0; font-size: 13px; color: #475569; font-weight: 600;\">Graduated Intern:</td>"
                    + "        <td style=\"padding: 6px 0; font-size: 13px; color: #166534; font-weight: 700;\">" + studentName + "</td>"
                    + "      </tr>"
                    + "      <tr>"
                    + "        <td style=\"padding: 6px 0; font-size: 13px; color: #475569; font-weight: 600;\">Certificate ID:</td>"
                    + "        <td style=\"padding: 6px 0; font-size: 13px; color: #166534; font-weight: 700; font-family: monospace;\">" + certificateId + "</td>"
                    + "      </tr>"
                    + "      <tr>"
                    + "        <td style=\"padding: 6px 0; font-size: 13px; color: #475569; font-weight: 600;\">Verification:</td>"
                    + "        <td style=\"padding: 6px 0; font-size: 13px; color: #10b981; font-weight: 700;\">100% Verified Cryptographic Lock</td>"
                    + "      </tr>"
                    + "    </table>"
                    + "  </div>"
                    + "  "
                    + "  <p style=\"font-size: 14px; line-height: 24px; color: #475569; margin: 0 0 24px 0;\">"
                    + "    Your certificate record is now active in our global blockchain-ready student register. Employers and universities can verify this credential using the ID on our <strong>Verifier Desk</strong> portal."
                    + "  </p>"
                    + "  "
                    + "  <p style=\"font-size: 14px; line-height: 24px; color: #475569; margin: 0 0 24px 0;\">"
                    + "    We wish you the absolute best in your future developer career! Keep building, and keep pushing limits."
                    + "  </p>"
                    + "  "
                    + "  <p style=\"font-size: 13.5px; line-height: 22px; color: #0f172a; margin: 30px 0 0 0;\">"
                    + "    Warmest regards,<br />"
                    + "    <strong>Board of Directors</strong><br />"
                    + "    Deltaclause Intelligent Academy & Labs"
                    + "  </p>"
                    + "  "
                    + "  <hr style=\"border: none; border-top: 1px solid #f1f5f9; margin-top: 40px; margin-bottom: 20px;\" />"
                    + "  <div style=\"text-align: center; color: #94a3b8; font-size: 11px;\">"
                    + "    Deltaclause Intelligent Academy &bull; verify@deltaclause.com &bull; https://deltaclause.com"
                    + "  </div>"
                    + "</div>";

            helper.setText(htmlBody, true);
            mailSender.send(message);

        } catch (Exception e) {
            System.err.println("[SMTP FAILURE] Unable to route Completion Certificate to " + recipientEmail + ": " + e.getMessage());
        }
    }
}
