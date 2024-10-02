package com.mfm.gansys.telegram

import com.mfm.gansys.telegram.service.ServiceSatu
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


@Component
class NardiBot : SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {


    @Value("\${telegram.bot.token}")
    lateinit var tokenBot:String

    lateinit var telegramClient:TelegramClient

    init {
        telegramClient = OkHttpTelegramClient(botToken)
    }

    override fun getBotToken(): String {
        return this.tokenBot
    }

    override fun getUpdatesConsumer(): LongPollingUpdateConsumer {
        return this
    }

    override fun consume(update: Update?) {

        if (update!!.hasMessage() && update.message.hasText()) {
            println(update.message.text)
            if (update.message.text.equals("/start", ignoreCase = true)) {
                try {
                    telegramClient.execute(ServiceSatu.response.greetingMessage(update))
//                    log("Farras", "AS", java.lang.Long.toString(1), "asd", "asd")
                } catch (e: TelegramApiException) {
                    throw RuntimeException(e)
                }
            } else if (update!!.message.text.equals("/test_pdf", ignoreCase = true)) {
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