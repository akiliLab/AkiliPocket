package design.akililab.akilipocket.profile


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import design.akililab.akilipocket.databinding.FragmentProfileBinding
import androidx.databinding.DataBindingUtil
import design.akililab.akilipocket.R


class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentProfileBinding>(inflater,
            R.layout.fragment_profile, container, false)

        val homeActivity: AppCompatActivity = (activity as AppCompatActivity)

        homeActivity.setSupportActionBar(binding.profileToolBar)

        homeActivity.supportActionBar!!.setDisplayShowTitleEnabled(false)


        return binding.root
    }


}
