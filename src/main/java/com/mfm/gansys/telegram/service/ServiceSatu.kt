package com.mfm.gansys.telegram.service

import com.vdurmont.emoji.EmojiParser
import org.telegram.telegrambots.meta.api.methods.send.SendDocument
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton


class ServiceSatu {

    companion object response {
        fun reimburseResponse(update: Update): SendMessage {
            println(update)
            var url = InlineKeyboardButton.builder()
                .text("Tutorial")
                .url("https://core.telegram.org/bots/api")
                .build();
            val sm : SendMessage = SendMessage.builder().chatId(update.message.chatId.toString()).text("Halo ${update.message.from.userName}")
                .build()
            return sm

        }

        fun greetingMessage(update: Update): SendMessage {

            val builderString = StringBuilder()
            builderString.append("PT.Venturium System Indonesia\r\n\r\n")
            builderString.append("Halo ${update.message.from.userName}, perkenalkan saya <b>Bapak Nardi Bot :robot_face:</b>, Robot Development Venturium System Indonesia.")
            builderString.append("Ada yang bisa saya bantu ?")

            val messageText = EmojiParser.parseToUnicode(builderString.toString())
            val sm = SendMessage(update.message.chatId.toString(), messageText);
            sm.enableHtml(true)
            return sm
        }
        fun infoPDF(messageReply: Int, chatId:String, status:String): SendMessage {
            val builderString = StringBuilder()
            builderString.append("Terimakasih, berikut update informasi\r\n")
            builderString.append("Status <b><i>${status}</i></b>")

            val messageText = EmojiParser.parseToUnicode(builderString.toString())
            val sm = SendMessage(chatId, messageText);
            sm.enableHtml(true)
            sm.replyToMessageId = messageReply
            return sm
        }

        fun updateInfoPDF(messageReply: Int, chatId:String, status:String): EditMessageText {
            val builderString = StringBuilder()
            builderString.append("Terimakasih, berikut update informasi\r\n")
            builderString.append("Status <b><i>${status}</i></b>")

            val messageText = EmojiParser.parseToUnicode(builderString.toString())
            val sm = EditMessageText(messageText);
            sm.enableHtml(true)
            sm.messageId = messageReply
            sm.chatId = chatId
            return sm
        }

        fun sendResponseOk(file : InputFile, chartId:String, caption: String): SendDocument {
            val builderString = StringBuilder()

            val sd = SendDocument.builder()
                .chatId(chartId)
                .document(file)
                .caption(caption)
                .build()

            return sd
        }
    }


}