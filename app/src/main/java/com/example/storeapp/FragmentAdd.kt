package com.example.storeapp

import android.Manifest
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.storeapp.databinding.FragmentAddBinding
import okhttp3.*
import retrofit2.http.Multipart
import java.io.IOException
//val MEDIA_TYPE_TEXT: MediaType? = "text/plain".toMediaTypeOrNull();


class FragmentAdd : Fragment() {
    private var _binding: FragmentAddBinding?=null
    private val binding get()=_binding!!
    private lateinit var codeScanner: CodeScanner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activityResultLauncher.launch(arrayOf(Manifest.permission.CAMERA))
        _binding= FragmentAddBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        codeScanner = CodeScanner(requireContext(), binding.scannerView)


        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS
        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = true
        codeScanner.decodeCallback = DecodeCallback {
            requireActivity().runOnUiThread {
                val base64Content = Base64.encodeToString(it.text.toByteArray(), Base64.DEFAULT)
                Toast.makeText(
                    requireContext(),
                    "Result ID(x64): ${base64Content}",
                    Toast.LENGTH_LONG
                ).show()
                binding.tvResult.text = "ID: ${it.text} added to Database"
                postBase64Content(base64Content)
            }
        }
        codeScanner.errorCallback = ErrorCallback {
            requireActivity().runOnUiThread {
                Toast.makeText(
                    requireContext(),
                    "Error scanner: ${it.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        activityResultLauncher.launch(arrayOf(Manifest.permission.CAMERA))

        codeScanner.startPreview()
    }

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            // Handle permissions
            permissions.entries.forEach { entry ->
                val permissionName = entry.key
                val isGranted = entry.value

                if (isGranted) {
                    // Setup QR Scanner
//                    onViewCreated()
                } else {
                    Toast.makeText(requireContext(), "Please enable camera permission to use this feature!", Toast.LENGTH_SHORT).show()
                }
            }
        }

    private fun postBase64Content(base64Content: String) {
        val url = "http://192.168.0.117:8080/v1/product/${base64Content}"
        val client = OkHttpClient()
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("base64Content", base64Content)
            .build()

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                requireActivity().runOnUiThread {
                    Toast.makeText(
                        requireContext(),
                        "Error sending data to server: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                requireActivity().runOnUiThread {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "Data sent to server successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Error sending data to server. Response code:",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}
