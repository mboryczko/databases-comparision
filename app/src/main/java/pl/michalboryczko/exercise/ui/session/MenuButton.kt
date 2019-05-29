package pl.michalboryczko.exercise.ui.session

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.rectangle.view.*
import pl.michalboryczko.exercise.R

class MenuButton (context: Context, attrs: AttributeSet): RelativeLayout(context, attrs) {

    init {
        inflate(context, R.layout.rectangle, this)
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.MenuButton)
        rectangleBackground.setBackgroundColor(attributes.getColor(R.styleable.MenuButton_color, Color.GRAY))
        menuButtonText.text = attributes.getText(R.styleable.MenuButton_buttonText)
        menuButtonText.setTextColor(attributes.getColor(R.styleable.MenuButton_textColor, Color.WHITE))
        attributes.recycle()
    }
}