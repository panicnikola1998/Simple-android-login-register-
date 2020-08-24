package cm.com.example.util

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object Utils {
    fun setImageFromUrl(context: Context, url: String, id: AppCompatImageView) {
        Glide.with(context)
            .load(url)
            .apply(RequestOptions.circleCropTransform())
            .into(id)
    }

    fun setImageFromDrawable(context: Context, image: Int, id: AppCompatImageView){
        Glide.with(context)
            .load(image)
            .apply(RequestOptions.circleCropTransform())
            .into(id)
    }
}