package com.mfm.gansys.jdbc

import com.mfm.gansys.jdbc.model.UserTelegram
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.sql.SQLException


@Component
class QuerySQL {


    @set:Autowired
    lateinit var connection:DBconnection


    fun insertRegistrasi(userId: Long, name:String, email:String, codeActivation:String, expiredDate:String):Unit{
        val query = "INSERT INTO users_telegram VALUES(?,?,?,0,getdate(),?,?)"

        try {
            val preparedStatement = this.connection.connection.prepareStatement(query)
            preparedStatement.setLong(1,userId)
            preparedStatement.setString(2,name)
            preparedStatement.setString(3,email)
            preparedStatement.setString(4,codeActivation)
            preparedStatement.setString(5,expiredDate)
            preparedStatement.execute()
        }
        catch (ex:SQLException){
            ex.printStackTrace()
        }
    }

    fun deleteRegister(userId: Long){
        val query = "DELETE FROM users_telegram WHERE id_user = ?"

        try {
            val preparedStatement = this.connection.connection.prepareStatement(query)
            preparedStatement.setLong(1,userId)
            preparedStatement.execute()
        }
        catch (ex:SQLException){
            ex.printStackTrace()
        }
    }

    fun getUserTelegramById(userId:Long):UserTelegram?{
        val query = "SELECT id_user, name, email, isActive, dateJoined, codeActivation, convert(varchar(24) ,codeActivationLife,121) as codeActivationLife FROM users_telegram WHERE id_user = ?"
        try {
            val preparedStatement = this.connection.connection.prepareStatement(query)
            preparedStatement.setLong(1, userId)

            val resultSet = preparedStatement.executeQuery()

            while (resultSet.next()){
                val userTelegram = UserTelegram(
                    resultSet.getLong("id_user"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getInt("isActive"),
                    resultSet.getString("dateJoined"),
                    resultSet.getString("codeActivation"),
                    resultSet.getString("codeActivationLife")
                )

                return userTelegram
            }
        }
        catch (ex:SQLException){
            ex.printStackTrace()
        }

        // Tidak ada return value-nya
        return null
    }

    fun getEmailBYid (userId: Long):String{
        val query = "SELECT email from users_telegram where id_user = ?"
        try {
            val preparedStatement = this.connection.connection.prepareStatement(query)
            preparedStatement.setLong(1,userId)
            val resultSet = preparedStatement.executeQuery()

            while (resultSet.next()){
                return resultSet.getString("email")
            }
        }
        catch (ex:SQLException){
            ex.printStackTrace()
        }
        return "NOT YET PROVIDED"
    }

}