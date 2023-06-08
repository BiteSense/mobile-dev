package com.c23ps323.bitesense.ui.preview

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.c23ps323.bitesense.R
import com.c23ps323.bitesense.data.Result
import com.c23ps323.bitesense.databinding.FragmentPreviewBinding
import com.c23ps323.bitesense.ui.scannedProduct.ScannedProductFragment
import com.c23ps323.bitesense.utils.ViewModelFactory
import com.c23ps323.bitesense.utils.reduceFileImage
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class PreviewFragment : Fragment() {
    private var _binding: FragmentPreviewBinding? = null
    private val binding get() = _binding!!

    private val previewViewModel: PreviewViewModel by viewModels {
        ViewModelFactory(requireContext())
    }
    private var myFile: File? = null
    private var uri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPreviewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get File from bundle
        myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable(EXTRA_PICTURE, File::class.java)
        } else {
            arguments?.getSerializable(EXTRA_PICTURE)
        } as? File

        // Get Uri from bundle
        uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(EXTRA_URI, Uri::class.java)
        } else {
            arguments?.getParcelable(EXTRA_URI)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(arguments != null) {
            if(uri != null) {
                binding.ivPreview.setImageURI(uri)
            }

            myFile?.let { file ->
                binding.ivPreview.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }

        binding.btnClose.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.btnAnalyze.setOnClickListener {
//            uploadProduct()
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_camera, ScannedProductFragment(), ScannedProductFragment::class.java.simpleName)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun uploadProduct() {
        if (myFile != null) {
            val file = reduceFileImage(myFile as File)
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
            val imageMultiPart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile,
            )
            previewViewModel.uploadProduct(imageMultiPart).observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    when(result) {
                        is Result.Loading -> showLoading(true)
                        is Result.Success -> {
                            showLoading(false)
                            Toast.makeText(
                                requireContext(),
                                "Uploading",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is Result.Error -> {
                            showLoading(false)
                            Toast.makeText(
                                requireContext(),
                                result.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(show: Boolean) {
        if (show) {
            binding.apply {
                progressBar.visibility = View.VISIBLE
                btnClose.visibility = View.GONE
                btnAnalyze.visibility = View.GONE
                ivPreview.visibility = View.GONE
            }
        } else {
            binding.apply {
                progressBar.visibility = View.GONE
                btnClose.visibility = View.VISIBLE
                btnAnalyze.visibility = View.VISIBLE
                ivPreview.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val EXTRA_URI = "extra_uri"
        const val EXTRA_PICTURE = "extra_picture"
    }
}