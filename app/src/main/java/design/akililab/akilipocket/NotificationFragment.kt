package design.akililab.akilipocket


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import design.akililab.akilipocket.databinding.FragmentNotificationBinding
import androidx.databinding.DataBindingUtil


class NotificationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentNotificationBinding>(inflater, R.layout.fragment_notification, container, false)

        return binding.root
    }


}
