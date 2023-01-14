package com.example.mynous

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream
import java.io.File


class CustomDialog(context: Context,
                   id: String,
                   title: String,
                   description: String,
                   image: String) {

        init {
            // build custom dialog
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            // dialog can cancelable
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.custom_layout)
            val txtId = dialog.findViewById(R.id.id) as TextView
            val txtTitle = dialog.findViewById(R.id.title) as TextView
            val txtDescription = dialog.findViewById(R.id.description) as TextView
            val imageView = dialog.findViewById(R.id.ivImage) as ImageView

            // set image to imageview
            Glide.with(context)
                .load(image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)

            //set item data to textview
            txtId.text = "ID : "+ id
            txtTitle.text = "Title : " + title
            txtDescription.text = "Description : " + description

            // close button for back to list activity
            val yesBtn = dialog.findViewById(R.id.ivClose) as ImageView
            yesBtn.setOnClickListener {
                dialog.dismiss()
            }

            //save image to internal storage for share via email
            Picasso.get().load(image).into(object : com.squareup.picasso.Target {
                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                    Toast.makeText(context, e!!.message, Toast.LENGTH_LONG).show()
                }

                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    // loaded bitmap is here (bitmap)
                    getImageUri(context, bitmap!!, title)
                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}

            })

            // share button for share title and description
            val shareBtn = dialog.findViewById(R.id.btnShare) as ImageView
            shareBtn.setOnClickListener {

                // select image from internal storage
                val root = Environment.getExternalStorageDirectory()
                val pathToMyAttachedFile = title + ".png"
                val file = File(root, pathToMyAttachedFile)
                val uri = Uri.fromFile(file)

                // intent for sharing all data via email
                val mIntent = Intent(Intent.ACTION_SENDTO)
                mIntent.putExtra(Intent.EXTRA_SUBJECT, title)
                mIntent.putExtra(Intent.EXTRA_TEXT, description)
                mIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                mIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                mIntent.putExtra(Intent.EXTRA_STREAM, uri)
                mIntent.type = "image/png"
                mIntent.data = Uri.parse("mailto:")

                try {
                    context.startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
                }
                catch (e: Exception){
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }
            }

            dialog.show()
        }


    fun getImageUri(inContext: Context, inImage: Bitmap, title: String): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, title, null)
        return Uri.parse(path)
    }

}