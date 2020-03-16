package com.carrati.chucknorrisfacts.utils

import android.content.Context
import android.content.Intent
import com.carrati.chucknorrisfacts.domain.entities.Fact
import java.lang.Exception

class ShareHandler {

    fun share(fact: Fact, context: Context): Boolean {
        try {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "Ei, você sabia?\n\"${fact.value}\"\n${fact.url}")
                putExtra(Intent.EXTRA_SUBJECT, "Share a fact")
            }
            val shareIntent = Intent.createChooser(sendIntent, "Share using:")
            context.startActivity(shareIntent)
            return true
        } catch (e: Exception){
            return false
        }
    }

}