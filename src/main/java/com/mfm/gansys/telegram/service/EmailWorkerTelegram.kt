package com.mfm.gansys.telegram.service

import com.mfm.gansys.utils.CostumeHelper
import org.simplejavamail.api.email.Email
import org.simplejavamail.api.mailer.Mailer
import org.simplejavamail.email.EmailBuilder
import org.simplejavamail.mailer.MailerBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component


@Component
class EmailWorkerTelegram {

    lateinit var mailer:Mailer

    @Value("\${email.username}")
    var usernameEmail:String = ""

    @Value("\${email.password}")
    var passwordEmail:String = ""

    @Value("\${email.hostname}")
    var hostEmail:String = ""

    @Value("\${email.port}")
    var portEmail:String = "0"

    init {
        // hanya ini biar ambil component
        println("Start component")
    }

    fun setUp() {
        //        System.out.println("Password after decrypt "+passwordDecoded);  //dikomen privasi pass
        // Create email buildernya
        println("User Name : ${usernameEmail}, paswd ${passwordEmail}, hostname ${hostEmail}, port ${portEmail}")
        mailer = MailerBuilder.withSMTPServer(
            this.hostEmail,
            Integer.valueOf(this.portEmail), this.usernameEmail, this.passwordEmail
        ).buildMailer()
    }


    fun sentEmail(targetMail:String, codeActivation:String, expiredDate:String):ResponeEmail{

        val responeEmail:ResponeEmail = ResponeEmail()

        val htmlBody = """
            <p>Gunakan kode berikut untuk tahap registrasi terdaftar BotNardi</p>
            <p><b>${codeActivation}</b></p>
            <p>Kode diatas hanya aktif selama 5 menit</p>
        """.trimIndent()

        // buat object email
        val email:Email = EmailBuilder.startingBlank().from(this.usernameEmail).to(targetMail)
            .withSubject("Register NardiBot Venturium System Indonesia").withHTMLText(htmlBody).buildEmail()

        this.mailer.testConnection()

        // validate the email
        this.mailer.validate(email)

        // set the email
        this.mailer.sendMail(email)

        responeEmail.status = StatusEmail.OK
        responeEmail.message = "Email has been sent succsessfully"

        return responeEmail

    }


    enum class StatusEmail{
        OK,NO
    }


    class ResponeEmail {
        lateinit var status:StatusEmail
        lateinit var message:String
    }


}