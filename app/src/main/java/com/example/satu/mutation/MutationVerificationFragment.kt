import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.satu.R

class MutationVerificationFragment : Fragment() {

    private val passwordList = mutableListOf<Int>() // To keep track of input

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_mutation_verification, container, false)

        // Find the views
        val llEnterPassword = view.findViewById<LinearLayout>(R.id.llEnterPassword)
        val llNumberPin = view.findViewById<LinearLayout>(R.id.llNumberPin)
        val tvShowPassword = view.findViewById<TextView>(R.id.tvShowPassword)

        // Set the initial visibility of llNumberPin to GONE
        llNumberPin.visibility = View.GONE

        // Set an OnClickListener on llEnterPassword to change the visibility of llNumberPin and update tvShowPassword
        llEnterPassword.setOnClickListener {
            llNumberPin.visibility = View.VISIBLE

            // Update drawable for tvShowPassword
            val drawableStart = ContextCompat.getDrawable(requireContext(), R.drawable.ic_eye) // Example drawable
            tvShowPassword.setCompoundDrawablesWithIntrinsicBounds(drawableStart, null, null, null)

            // Adjust padding for tvShowPassword
            tvShowPassword.setPadding(
                tvShowPassword.paddingStart,
                (tvShowPassword.paddingTop + 10.dpToPx()).toInt(), // Increase top padding
                tvShowPassword.paddingEnd,
                tvShowPassword.paddingBottom
            )
        }

        // Number buttons
        val numberButtons = listOf(
            R.id.tvNumber1, R.id.tvNumber2, R.id.tvNumber3,
            R.id.tvNumber4, R.id.tvNumber5, R.id.tvNumber6,
            R.id.tvNumber7, R.id.tvNumber8, R.id.tvNumber9,
            R.id.tvNumber0
        )

        for (id in numberButtons) {
            view.findViewById<TextView>(id).setOnClickListener { v ->
                handleNumberClick(v as TextView)
            }
        }

        // Delete button
        view.findViewById<View>(R.id.ivDelete).setOnClickListener {
            handleDelete()
        }

        return view
    }

    private fun handleNumberClick(view: TextView) {
        val number = view.text.toString().toInt()
        val passwordListSize = passwordList.size

        if (passwordListSize < 6) {
            passwordList.add(number)
            updatePasswordViews()
        }
    }

    private fun handleDelete() {
        if (passwordList.isNotEmpty()) {
            passwordList.removeAt(passwordList.size - 1)
            updatePasswordViews()
        }
    }

    private fun updatePasswordViews() {
        val drawableFilled = ContextCompat.getDrawable(requireContext(), R.drawable.ic_circle)
        val drawableEmpty = ContextCompat.getDrawable(requireContext(), R.drawable.ic_min)

        val passwordViews = listOf(
            R.id.tvPassword1, R.id.tvPassword2, R.id.tvPassword3,
            R.id.tvPassword4, R.id.tvPassword5, R.id.tvPassword6
        ).map { id -> view?.findViewById<TextView>(id) }

        passwordViews.forEachIndexed { index, textView ->
            textView?.setCompoundDrawablesWithIntrinsicBounds(null, null, null,
                if (index < passwordList.size) drawableFilled else drawableEmpty)
        }
    }

    // Extension function to convert dp to px
    private fun Int.dpToPx(): Float {
        return this * resources.displayMetrics.density
    }
}
