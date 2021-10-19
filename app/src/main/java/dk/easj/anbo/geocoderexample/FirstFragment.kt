package dk.easj.anbo.geocoderexample

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dk.easj.anbo.geocoderexample.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // geocoding: Address -> (latitude, longitude)
        val geocoder = Geocoder(activity)
        val locationName = "Maglegårdsvej 2, 4000 Roskilde, Denmark"
        binding.textviewFirst.text = "Geocoding: Address -> (lat, long)\n"
        binding.textviewFirst.append(locationName + "\n")
        val addressList: MutableList<Address> = geocoder.getFromLocationName(locationName, 5)
        for (address in addressList) {
            binding.textviewFirst.append("Lat: ${address.latitude} Long: ${address.longitude}\n")
        }

        // reverse geocoding: (latitude, longitude) -> Addresses
        val latitude = 55.63
        val longitude = 12.08
        binding.textviewAnother.text = "Reverse geocoding: (lat, long) -> addresses\n"
        binding.textviewAnother.append("Lat: $latitude Long: $longitude\n")
        val addressList2 = geocoder.getFromLocation(latitude, longitude, 5)
        for (addr in addressList2) {
            binding.textviewAnother.append(addr.getAddressLine(0) + "\n")
        }

        /* binding.buttonFirst.setOnClickListener {
             findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
         }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}