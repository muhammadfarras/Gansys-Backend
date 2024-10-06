package com.mfm.gansys.telegram

import com.mfm.gansys.jdbc.QuerySQL
import com.mfm.gansys.jdbc.model.UserTelegram
import com.mfm.gansys.telegram.service.EmailWorkerTelegram
import com.mfm.gansys.telegram.service.ServiceRegister
import com.mfm.gansys.telegram.service.ServiceSatu
import com.mfm.gansys.utils.CostumeHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.message.Message
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.meta.generics.TelegramClient
import java.io.File
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Component
class NardiBot : SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {


    @set: Autowired
    lateinit var emailWorkerTelegram: EmailWorkerTelegram

    @set: Autowired
    lateinit var querySQL: QuerySQL


    @Value("\${telegram.bot.token}")
    var tokenBot:String =""

    var telegramClient:TelegramClient

    init {

        println("Our toke")

        telegramClient = OkHttpTelegramClient(getBotToken())
        println("after this ")
    }

    override fun getBotToken(): String {
        println("Token Bot ${tokenBot}")


        // sue token bot gua taruh sini biar saat buat bukan di init
        telegramClient = OkHttpTelegramClient(tokenBot)
        return this.tokenBot
    }

    override fun getUpdatesConsumer(): LongPollingUpdateConsumer {
        return this
    }

    override fun consume(update: Update?) {

        // must setup connection
        this.querySQL.connection.setUp()

        val messageResponse : String = update!!.message.text

        if (update!!.hasMessage() && update.message.hasText()) {
            println(update.message.text)
            if (update.message.text.equals("/start", ignoreCase = true)) {
                try {
                    telegramClient.execute(ServiceSatu.response.greetingMessage(update))
//                    log("Farras", "AS", java.lang.Long.toString(1), "asd", "asd")
                } catch (e: TelegramApiException) {
                    throw RuntimeException(e)
                }
            }

            // check register
            else if (messageResponse.contains("/register", ignoreCase = true)){

                val userTelegram:UserTelegram? = querySQL.getUserTelegramById(update.message.from.id)

                if (userTelegram == null){

                    // Jika user sudah terdaftar namun belum aktif dan masa berlaku masih sudah selesai


                    if (messageResponse.contains("@vensys.co.id",ignoreCase = true) || messageResponse.contains("maruffarras@gmail.com",ignoreCase = true) ) {

                        telegramClient.execute(ServiceRegister.Register.initGreatingForRgestration(update))


                        if (messageResponse.split(" ").isEmpty()){
                            // User tidak memasukan email
                        }

                        // gather data
                        val email = messageResponse.split(" ")[1]

                        val nameAplicantRegister  = "${update.message.from.firstName} ${update.message.from.lastName}"
                        val applicantId = update.message.from.id

                        // Buat aktivasi kode dan tanggal kadarluasa
                        val expiredDate = LocalDateTime.now().plusSeconds(300).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        val codeActvation = CostumeHelper.getRandomNumberString()


                        // insert into Database
                        this.querySQL.insertRegistrasi(applicantId,nameAplicantRegister,email,codeActvation,expiredDate.toString())


                        this.emailWorkerTelegram.setUp()
                        val responeEmail = this.emailWorkerTelegram.sentEmail("maruffarras@gmail.com", codeActvation, expiredDate.toString())
                        if (responeEmail.status == EmailWorkerTelegram.StatusEmail.OK){
                            // kirim pesan email berhasil terkirim
                            telegramClient.execute(ServiceRegister.Register.registerConfirmation(update, querySQL))
                        }
                        else {
                            telegramClient.execute(ServiceRegister.Register.errorResponeRegistration(update))
                        }
                    }
                    else {
                        // kirim pesan jika email tidak valid
                        telegramClient.execute(ServiceRegister.Register.errorRegisterEmail(update))
                    }
                }
                else{


                    // Check apaka user sudah aktive atau belum
                    val isActivate = userTelegram.isActivate == 1

                    if (isActivate){
                        // notifikasi user sudah terdaftar
                        telegramClient.execute(ServiceRegister.Register.notificationUserIsExist(update))
                    }
                    else {
                        val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                        // check apakah user activation code kadarluasa atau tidak
                         val dateTimeAcivation = LocalDateTime.parse(userTelegram.codeActivationLife,dateTimeFormatter)
                        if (dateTimeAcivation.isAfter(LocalDateTime.now())){
                            // PLease check your email your code activation is still on
                            telegramClient.execute(ServiceRegister.Register.notifCodeIsNotExpiredYed(update))

                        }
                        else {
                            // Delete from database, and sen dnotece you have to new register
                            querySQL.deleteRegister(update.message.from.id)
                            telegramClient.execute(ServiceRegister.Register.notifCodeIsExpired(update))
                        }
                    }

                }

            }

            else if (update!!.message.text.equals("/test_pdf", ignoreCase = true)) {
                try {
                    val sendMessage =
                        SendMessage.builder().text("Prosess start").chatId(update!!.message.chatId.toString()).build()
                    val message: Message = telegramClient.execute<Message, SendMessage>(sendMessage)
                    val message2: Message = telegramClient.execute(
                        ServiceSatu.response.infoPDF(
                            message.messageId,
                            sendMessage.chatId,
                            "PDF sudah diterima"
                        )
                    )
                    val message3: Serializable = telegramClient.execute(
                        ServiceSatu.response.updateInfoPDF(
                            message2.messageId,
                            sendMessage.chatId,
                            "Pengjujian PDF"
                        )
                    )
//                    GeneratePDF.processPDF(
//                        "C:\\Users\\Administrator\\Downloads\\sample_def_aset.pdf",
//                        "farras",
//                        "123123"
//                    )
                    println("Masuk sini pdf")
                    val host = "temp\\signed"
                    val inputFile = InputFile(File("$host\\sample_def_signed.pdf"))
                    telegramClient.execute(
                        ServiceSatu.response.sendResponseOk(
                            inputFile,
                            update!!.message.chatId.toString(),
                            "Ini caption-nya mas bro"
                        )
                    )
                } catch (e: TelegramApiException) {
                    throw RuntimeException(e)
                }
            }
        } else {
            println(update)
            println("Disini")
        }
    }
}