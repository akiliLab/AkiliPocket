package design.akililab.akilipocket


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import design.akililab.akilipocket.databinding.FragmentAccountBinding
import androidx.databinding.DataBindingUtil


/**
 * A simple [Fragment] subclass.
 *
 */
class AccountFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentAccountBinding>(inflater,R.layout.fragment_account, container, false)

        return binding.root
    }


}
