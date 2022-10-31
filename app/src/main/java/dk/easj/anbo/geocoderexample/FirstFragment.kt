package dk.easj.anbo.geocoderexample

import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
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
        val geocoder = Geocoder(requireActivity())
        val locationName = "Maglegårdsvej 2, 4000 Roskilde, Denmark"
        binding.textviewFirst.text = "Geocoding: Address -> (lat, long)\n"
        binding.textviewFirst.append(locationName + "\n")

        Log.d("APPLE", "Build version: " + Build.VERSION.SDK_INT)

        // https://stackoverflow.com/questions/71427908/android-geocoder-void-getfromlocation-how-to-get-addresses
        /*val geocodeListener = @RequiresApi(33)
        object : Geocoder.GeocodeListener {
            override fun onGeocode(addressList: MutableList<Address>) {
                if (addressList.isNullOrEmpty()) {
                    binding.textviewFirst.text = "No addresses"
                } else {
                    for (address in addressList) {
                        binding.textviewFirst.append("Lat: ${address.latitude} Long: ${address.longitude}\n")
                    }
                }
            }
        }*/

       /* if (Build.VERSION.SDK_INT >= 33) {
            // declare here the geocodeListener, as it requires Android API 33
            geocoder.getFromLocationName(locationName, 5, geocodeListener)
        } else {*/
            val addressList = geocoder.getFromLocationName(locationName, 5)
            // For Android SDK < 33, the addresses list will be still obtained from the getFromLocation() method
            if (addressList.isNullOrEmpty()) {
                binding.textviewFirst.text = "No addresses"
            } else
                for (address in addressList) {
                    binding.textviewFirst.append("Lat: ${address.latitude} Long: ${address.longitude}\n")
                }
        //}

        // reverse geocoding: (latitude, longitude) -> Addresses
        val latitude = 55.63
        val longitude = 12.08
        binding.textviewAnother.text = "Reverse geocoding: (lat, long) -> addresses\n"
        binding.textviewAnother.append("Lat: $latitude Long: $longitude\n")
        val addressList2 = geocoder.getFromLocation(latitude, longitude, 5)
        if (addressList2.isNullOrEmpty()) {
            binding.textviewAnother.text = "No addresses"
        } else
            for (address in addressList2) {
                binding.textviewAnother.append(address.getAddressLine(0) + "\n")
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