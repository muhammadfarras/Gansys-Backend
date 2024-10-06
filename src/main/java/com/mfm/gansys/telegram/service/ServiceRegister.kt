package com.mfm.gansys.telegram.service

import com.mfm.gansys.jdbc.QuerySQL
import com.vdurmont.emoji.EmojiParser
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

class ServiceRegister {



    companion object Register {
        fun errorRegisterEmail(update: Update): SendMessage {


            val builderString = StringBuilder()
            builderString.append("Mohon maaf ${update.message.from.firstName} ${update.message.from.lastName} register anda ditolak\r\n\r\n")
            builderString.append("Alamat email yang anda masukan tidak tepat Contoh /register fulan@gmail.com")

            val messageText = EmojiParser.parseToUnicode(builderString.toString())
            val sm = SendMessage(update.message.chatId.toString(), messageText);
            sm.replyToMessageId = update.message.getMessageId()
            sm.enableHtml(true)
            return sm
        }

        fun initGreatingForRgestration(update: Update): SendMessage {


            val builderString = StringBuilder()
            builderString.append("Halo ${update.message.from.firstName} ${update.message.from.lastName} register anda sedang kami proses, mohon menunggu kami untuk mengirimkan kode aktivasi")

            val messageText = EmojiParser.parseToUnicode(builderString.toString())
            val sm = SendMessage(update.message.chatId.toString(), messageText);
            sm.replyToMessageId = update.message.messageId
            sm.enableHtml(true)
            return sm
        }

        fun errorResponeRegistration(update: Update): SendMessage {


            val builderString = StringBuilder()
            builderString.append("Halo ${update.message.from.firstName} ${update.message.from.lastName} ada kendala teknis di NardiBot, mohon kontak https://t.me/browntofu")

            val messageText = EmojiParser.parseToUnicode(builderString.toString())
            val sm = SendMessage(update.message.chatId.toString(), messageText);
            sm.enableHtml(true)
            return sm
        }



        fun notificationUserIsExist(update: Update): SendMessage {

            val builderString = StringBuilder()
            builderString.append("Mohon maaf ${update.message.from.firstName} ${update.message.from.lastName} register anda ditolak\r\n\r\n")
            builderString.append("Alamat email yang anda masukan tidak tepat. Contoh /register fulan@gmail.com")

            val messageText = EmojiParser.parseToUnicode(builderString.toString())
            val sm = SendMessage(update.message.chatId.toString(), messageText);
            sm.enableHtml(true)
            return sm
        }

        fun notifCodeIsNotExpiredYed(update: Update): SendMessage {

            val builderString = StringBuilder()
            builderString.append("Mohon maaf ${update.message.from.firstName} ${update.message.from.lastName}, kode aktivasi anda masih aktif, silahkan register ulang menggunakan kode aktivasi yang kami kirim lewat email\r\n\r\n")
            builderString.append("Contoh /register fulan@gmail.com")

            val messageText = EmojiParser.parseToUnicode(builderString.toString())
            val sm = SendMessage(update.message.chatId.toString(), messageText);
            sm.replyToMessageId = update.message.messageId
            sm.enableHtml(true)
            return sm
        }


        fun notifCodeIsExpired(update: Update): SendMessage {

            val builderString = StringBuilder()
            builderString.append("Mohon maaf ${update.message.from.firstName} ${update.message.from.lastName}, anda sudah register, namun kode aktivasi anda sudah kadarluasa, mohon untuk register ulang\r\n\r\n")
            builderString.append("<b>Contoh : </b> <i>/register_konfirmasi 111222</i>")

            val messageText = EmojiParser.parseToUnicode(builderString.toString())
            val sm = SendMessage(update.message.chatId.toString(), messageText);
            sm.replyToMessageId = update.message.messageId
            sm.enableHtml(true)
            return sm
        }

        fun registerConfirmation(update: Update, sqlQuery: QuerySQL): SendMessage {


            val email = sqlQuery.getEmailBYid(update.message.from.id)

            val builderString = StringBuilder()
            builderString.append("Kode aktivasi telah dikirim ke ${email}, gunakan kode tersebut untuk registrasi konfirmasi di nardi bot\r\n\r\n")
            builderString.append("<b>Contoh : </b> <i>/register_konfirmasi 111222</i>")

            val messageText = EmojiParser.parseToUnicode(builderString.toString())
            val sm = SendMessage(update.message.chatId.toString(), messageText);
            sm.enableHtml(true)
            return sm
        }
    }
}