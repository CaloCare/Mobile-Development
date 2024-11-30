package com.dicoding.calocare.ui.media_chooser

import android.content.pm.PackageManager
import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.dicoding.calocare.R
import com.dicoding.calocare.databinding.FragmentAddFoodBinding
import com.dicoding.calocare.databinding.FragmentMediaChooserBinding
import com.dicoding.calocare.util.getImageUri
import com.dicoding.calocare.util.uriToFile

class MediaChooserFragment : Fragment() {

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 1001
    }

    private var currentImageUri: Uri? = null
    private lateinit var binding: FragmentMediaChooserBinding
    private val viewModel: MediaChooserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMediaChooserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageButtonGallery.setOnClickListener {
            startGallery()
        }
        binding.imageButtonCamera.setOnClickListener {
            checkCameraPermission()
        }

        binding.analyzeButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_mediaChooser_to_navigation_form)
        }
    }

    // Fungsi untuk memulai Gallery
    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    // Fungsi untuk memulai Camera
    private fun startCamera() {
        currentImageUri = getImageUri(requireContext())
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        } else {
            currentImageUri = null
        }
    }

    // Fungsi untuk menampilkan gambar
    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    // Fungsi untuk mengunggah gambar ke Machine learning
//    fun uploadImage() {
//        currentImageUri?.let { uri ->
//            val imageFile = uriToFile(uri, requireContext()).reduceFileImage()
//            viewModel.uploadImage(imageFile)
//        } ?: showToast("No Image Selected")
//    }

    // Fungsi untuk memeriksa izin kamera
    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
        } else {
            startCamera()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCamera()
                } else {
                    showToast("Camera permission is required to use the camera.")
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}