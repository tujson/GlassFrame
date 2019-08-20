package dev.synople.glassframe

import com.google.android.glass.media.Sounds
import com.google.android.glass.widget.CardBuilder
import com.google.android.glass.widget.CardScrollAdapter
import com.google.android.glass.widget.CardScrollView

import android.app.Activity
import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView

class MainActivity : Activity() {

    private lateinit var cardScroller: CardScrollView

    private var view: View? = null

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)

        view = buildView()

        cardScroller = CardScrollView(this)
        cardScroller.adapter = object : CardScrollAdapter() {
            override fun getCount(): Int {
                return 1
            }

            override fun getItem(position: Int): Any? {
                return view
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
                return view
            }

            override fun getPosition(item: Any): Int {
                return if (view == item) {
                    0
                } else AdapterView.INVALID_POSITION
            }
        }

        cardScroller.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val am = getSystemService(Context.AUDIO_SERVICE) as AudioManager
            am.playSoundEffect(Sounds.DISALLOWED)
        }

        setContentView(cardScroller)
    }

    override fun onResume() {
        super.onResume()
        cardScroller.activate()
    }

    override fun onPause() {
        cardScroller.deactivate()
        super.onPause()
    }

    private fun buildView(): View {
        val card = CardBuilder(this, CardBuilder.Layout.TEXT)

        card.setText(R.string.hello_world)
        return card.view
    }

}
