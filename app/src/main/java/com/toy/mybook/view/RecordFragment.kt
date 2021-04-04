package com.toy.mybook.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.toy.mybook.DTO.BookDTO
import com.toy.mybook.DTO.ImageDTO
import com.toy.mybook.R
import com.toy.mybook.contract.RecordContract
import com.toy.mybook.databinding.FragmentRecordBinding
import com.toy.mybook.presenter.RecordPresenter
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RecordFragment:Fragment(), RecordContract.View {
    private val TAG="RecordFragment"
    lateinit var binding: FragmentRecordBinding
    lateinit var presenter: RecordContract.Present
    var cameraPermission=false
    val REQUEST_CAMERA_PERMISSION=101
    val REQUEST_IMAGE_CAPTURE=102
    lateinit var currentPhotoPath:String
    lateinit var photoname:String
    var imageList= arrayListOf<ImageDTO>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter=RecordPresenter(this)

        binding= FragmentRecordBinding.inflate(LayoutInflater.from(activity), container, false)
        binding.recycler.adapter=RecordRecyclerviewAdapter()
        binding.recycler.layoutManager=GridLayoutManager(context,3)

        /**
         * presenter한테 퍼미션 확인 요청
         *
         */
//        presenter.checkPermission(this.requireContext())
        ActivityCompat.requestPermissions(this.requireActivity(), arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),REQUEST_CAMERA_PERMISSION)

//        presenter?.getFavoriteBook()
        presenter.getRecordImage()
        binding.ivCamera.setOnClickListener {
            startCapture()
        }
        return binding.root
    }



    override fun setFavoriteBook(bookList: ArrayList<BookDTO>) {

    }

    override fun setRecordImage(imageList: ArrayList<ImageDTO>) {
        Log.i(TAG, "setRecordImage")
        this.imageList=imageList
        binding.recycler.adapter?.notifyDataSetChanged()
    }

    override fun setRecordImage(imgDTO: ImageDTO) {
        imageList.add(imgDTO)
        binding.recycler.adapter?.notifyDataSetChanged()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i(TAG, "onActivityResult")
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==REQUEST_CAMERA_PERMISSION && resultCode== Activity.RESULT_OK) {
            Log.i(TAG, "permission granted")
        }
        if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode==Activity.RESULT_OK){
            val file=File(currentPhotoPath)
            presenter.setRecordImage(photoname, Uri.fromFile(file))
        }
    }

    private fun showToast(message:String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun startCapture(){
        Log.i(TAG, "startCapture")
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also{ takePictureIntent->
            takePictureIntent.resolveActivity(this.requireActivity().packageManager)?.also {
                val photoFile=try{
                    createImageFile()
                }catch (e:IOException){
                    null
                }
                photoFile?.also{
                    val photoURI=FileProvider.getUriForFile(
                            this.requireContext(),
                            "com.toy.mybook.fileprovider",
                            it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    private fun createImageFile():File{
        val timestamp:String=SimpleDateFormat("yyyMMdd_HHmmss").format(Date())
        val storageDir:File?=this.requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
                "JPEG_${timestamp}_",
                ".jpg",
                storageDir
        ).apply {
            currentPhotoPath=absolutePath
            photoname=name
        }
    }

    inner class RecordRecyclerviewAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var width=resources.displayMetrics.widthPixels/3
            var imageview=ImageView(parent.context)
            imageview.layoutParams=LinearLayoutCompat.LayoutParams(width,width)
            return CustomViewHolder(imageview)
        }

        override fun getItemCount(): Int {
            return imageList.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var imageview=(holder as CustomViewHolder).imageview
            Glide.with(holder.itemView.context).load(imageList[position].imgUri).fallback(R.drawable.empty).into(imageview)
        }

        private inner class CustomViewHolder(var imageview:ImageView): RecyclerView.ViewHolder(imageview)
    }
}