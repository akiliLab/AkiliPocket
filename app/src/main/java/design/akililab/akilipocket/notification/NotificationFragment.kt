package design.akililab.akilipocket.notification


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import design.akililab.akilipocket.databinding.FragmentNotificationBinding
import androidx.databinding.DataBindingUtil
import design.akililab.akilipocket.R


class NotificationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentNotificationBinding>(inflater,
            R.layout.fragment_notification, container, false)

        val homeActivity: AppCompatActivity = (activity as AppCompatActivity)

        homeActivity.setSupportActionBar(binding.notificationToolBar)

        homeActivity.supportActionBar!!.setDisplayShowTitleEnabled(false)

        return binding.root
    }


}
