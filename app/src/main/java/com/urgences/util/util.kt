package com.urgences.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.telephony.SmsManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.urgences.model.Pro

fun allowPermissions(context: Context, permissions: List<String>) {

    var listPermissions = ArrayList<String>()

    for (permission in permissions) {
        if (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED)
        {
            listPermissions.add(permission)
        }
    }


    if (listPermissions.size > 0)
    {
        //val listPermissions = listOf<String>(
        //        permission
        //)
        ActivityCompat.requestPermissions(context as Activity, listPermissions.toTypedArray(), 123)
    }
}

fun checkPermission(context: Context, permission:String): Boolean {
    if (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED)
    {
        return false
    }
    return true
}

fun launchCall(context: Context, name:String, number:String) {
    val builder = AlertDialog.Builder(context)
    // Display a message on alert dialog
    builder.setMessage("Voulez vous vraiment contacter " + name + "(" + number + ")")

    // Set a positive button and its click listener on alert dialog
    builder.setPositiveButton("Oui, contacter"){dialog, which ->

        if (checkPermission(context, Manifest.permission.CALL_PHONE)) {
            val call:Uri = Uri.parse("tel:" + number)
            val intent: Intent = Intent(Intent.ACTION_CALL, call)
            Toast.makeText(context, name +" ("+number+") " +"Appel en cours ...", Toast.LENGTH_SHORT).show()// Initialize a new instance of
            context.startActivity(intent)
        } else {
            var permission_appel: String = Manifest.permission.CALL_PHONE;
            var listpermission = ArrayList<String>()
            listpermission.add(permission_appel)
            allowPermissions(context, listpermission)
        }

    }

    builder.setNeutralButton("Annuler"){_,_ ->

    }

    // Finally, make the alert dialog using builder
    val dialog: AlertDialog = builder.create()

    // Display the alert dialog on app interface
    dialog.show()


}


fun launchSms(context:Context, pro: Pro){
    val alert = AlertDialog.Builder(context)
    var message: EditText?=null

    // Builder
    with (alert) {
        setTitle("Veuillez écrire ci-dessous le message à envoyé aux urgences")

        val input = EditText(context)
        input.setLines(8)
        val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT)

        input.layoutParams = lp
        alert.setView(input) // uncomment this line


        alert.setPositiveButton("Envoyer") {alert, which ->
            val builder = AlertDialog.Builder(context!!)
            // Display a message on alert dialog
            builder.setMessage("Voulez vous vraiment envoyer ce message à " + pro.name)

            // Set a positive button and its click listener on alert dialog
            builder.setPositiveButton("Oui, envoyer"){dialog, which ->
                Toast.makeText(context, "Sms envoyé!", Toast.LENGTH_SHORT).show()// Initialize a new instance of
                val smsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(pro.number.toString(), null, input.text.toString(), null, null)

            }

            builder.setNeutralButton("Annuler"){_,_ ->

            }

            // Finally, make the alert dialog using builder
            val dialog: AlertDialog = builder.create()

            // Display the alert dialog on app interface
            dialog.show()



        }

        alert.setNegativeButton("Annuler") {
            dialog, whichButton ->
            //showMessage("Close the game or anything!")
            dialog.dismiss()
        }
    }

    // Dialog
    val dialog = alert.create()
    dialog.show()
}