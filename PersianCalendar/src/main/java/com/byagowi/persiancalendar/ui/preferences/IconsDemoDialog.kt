package com.byagowi.persiancalendar.ui.preferences

import android.graphics.Color
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.byagowi.persiancalendar.R
import com.byagowi.persiancalendar.utils.createStatusIcon
import com.byagowi.persiancalendar.utils.dp
import com.byagowi.persiancalendar.utils.getDayIconResource
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.ShapeAppearanceModel

// Debug only dialog to check validity of dynamic icons generation
fun Fragment.showIconsDemoDialog() {
    val context = layoutInflater.context
    AlertDialog.Builder(context)
        .setView(RecyclerView(context).also {
            it.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
                override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {}
                override fun getItemCount() = 62
                override fun getItemViewType(position: Int) = position
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
                    object : RecyclerView.ViewHolder(FrameLayout(context).also { frameLayout ->
                        frameLayout.setPadding(4.dp)
                        ShapeableImageView(context).apply {
                            layoutParams = ViewGroup.LayoutParams(36.dp, 36.dp)
                            setBackgroundColor(Color.BLACK)
                            // must be applied via or extend theme shape appearance
                            shapeAppearanceModel = ShapeAppearanceModel.Builder()
                                .setAllCornerSizes(10.dp.toFloat())
                                .build()

                            val day = viewType / 2 + 1
                            if (viewType.mod(2) == 0) {
                                setImageResource(getDayIconResource(day))
                            } else {
                                setImageBitmap(createStatusIcon(context, day))
                            }
                        }.also(frameLayout::addView)
                    }) {}
            }
            it.layoutManager = GridLayoutManager(context, 8)
            it.setBackgroundColor(Color.WHITE)
        })
        .setNegativeButton(R.string.cancel, null)
        .show()
}
