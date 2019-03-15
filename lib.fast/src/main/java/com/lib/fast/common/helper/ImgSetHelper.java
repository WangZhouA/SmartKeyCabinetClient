package com.lib.fast.common.helper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;

import com.lib.fast.common.utils.FileProvider7;
import com.lib.fast.common.utils.FileUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by siwei on 2018/3/25.
 * 头像设置helper
 */

public class ImgSetHelper {

    private Context mContext;
    private static final String IMAGE_SET_FRAGMENT_TAG = "image_set_helper_fragment";
    public static final int REQUEST_CODE_TAKE_PHOTO = 0x110;
    public static final int REQ_PERMISSION_CODE_TAKE_PHOTO = 0X112;
    public static final int REQ_CROP = 0X113;
    public static final int REQ_SELECT_PHOTO_AND_CROP = 0x114;
    public static final int REQ_SELECT_PHOTO = 0x114;
    private String TAKE_PHOTO_DIR;//拍照文件的目录

    private String mCurrentPhotoPath;
    private String cropPhotoPath;
    private ImageSetFragment mImageSetFragment;
    private SetListener mListener;

    public ImgSetHelper(FragmentActivity activity, String cropPhotoPath) {
        this.mContext = activity;
        this.cropPhotoPath = cropPhotoPath;
        TAKE_PHOTO_DIR = FileUtils.getAppFileDir(activity, "takephoto").getPath();
        mImageSetFragment = getImageSetFragmentByActivity(activity);
    }

    public ImgSetHelper(FragmentActivity activity) {
        this.mContext = activity;
        this.cropPhotoPath = FileUtils.getAppFileDir(activity).getPath()+"/crop_img.jpg";
        TAKE_PHOTO_DIR = FileUtils.getAppFileDir(activity, "takephoto").getPath();
        mImageSetFragment = getImageSetFragmentByActivity(activity);
    }

    public ImgSetHelper(Fragment fragment, String cropPhotoPath) {
        this.mContext = fragment.getContext();
        this.cropPhotoPath = cropPhotoPath;
        TAKE_PHOTO_DIR = FileUtils.getAppFileDir(mContext, "takephoto").getPath();
        mImageSetFragment = getImageSetFragmentByV4Fragment(fragment);
    }

    public ImgSetHelper(Fragment fragment) {
        this.mContext = fragment.getContext();
        this.cropPhotoPath = FileUtils.getAppFileDir(mContext).getPath()+"/crop_img.jpg";
        TAKE_PHOTO_DIR = FileUtils.getAppFileDir(mContext, "takephoto").getPath();
        mImageSetFragment = getImageSetFragmentByV4Fragment(fragment);
    }

    public void setListener(SetListener listener) {
        this.mListener = listener;
    }


    /**
     * 拍照并切图
     */
    public void takePhotoAndCrop() {
        if (ContextCompat.checkSelfPermission(mImageSetFragment.getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            mImageSetFragment.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQ_PERMISSION_CODE_TAKE_PHOTO);

        } else if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            mImageSetFragment.requestPermissions(new String[]{Manifest.permission.CAMERA},
                    REQ_PERMISSION_CODE_TAKE_PHOTO);
        } else {
            takePhoto();
        }
    }

    /**
     * 选择图片并切图
     */
    public void selectPhotoAndCrop() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mImageSetFragment.startActivityForResult(intent, REQ_SELECT_PHOTO_AND_CROP);
    }

    /**
     * 选择图片并切图
     */
    public void selectPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mImageSetFragment.startActivityForResult(intent, REQ_SELECT_PHOTO);
    }

    //data中读取图片地址
    private String handleImage(Intent data) {
        Uri uri = data.getData();
        String imagePath = null;
        if (Build.VERSION.SDK_INT >= 19) {
            if (DocumentsContract.isDocumentUri(mContext, uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                    String id = docId.split(":")[1];
                    String selection = MediaStore.Images.Media._ID + "=" + id;
                    imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("" +
                            "content://downloads/public_downloads"), Long.valueOf(docId));
                    imagePath = getImagePath(contentUri, null);
                }
            } else if ("content".equals(uri.getScheme())) {
                imagePath = getImagePath(uri, null);
            }
        } else {
            imagePath = getImagePath(uri, null);
        }
        return imagePath;
    }

    private String getImagePath(Uri uri, String seletion) {
        String path = null;
        Cursor cursor = mContext.getContentResolver().query(uri, null, seletion, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    public void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(mContext.getPackageManager()) != null) {
            String filename = new SimpleDateFormat("yyyyMMdd-HHmmss")
                    .format(new Date()) + ".png";
            File file = new File(TAKE_PHOTO_DIR, filename);
            mCurrentPhotoPath = file.getPath();

            Uri fileUri = FileProvider7.getUriForFile(mContext, file);

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            mImageSetFragment.startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PHOTO);
        }
    }

    //切图
    private void crop(String imagePath) {
        File cropImageFile = new File(cropPhotoPath);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(getImageContentUri(new File(imagePath)), "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 500);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cropImageFile));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        mImageSetFragment.startActivityForResult(intent, REQ_CROP);
    }

    //把fileUri转换成ContentUri
    private Uri getImageContentUri(File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = mContext.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return mContext.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    private void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQ_PERMISSION_CODE_TAKE_PHOTO) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhotoAndCrop();
            } else {
                dispatchOnSetFaild();
            }
        }
    }

    private void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_TAKE_PHOTO) {
                crop(mCurrentPhotoPath);
            } else if (requestCode == REQ_SELECT_PHOTO_AND_CROP && data != null) {
                crop(handleImage(data));
            } else if (requestCode == REQ_CROP) {
                dispatchOnCropSuccess(new File(cropPhotoPath));
            }else if(requestCode == REQ_SELECT_PHOTO){
                dispatchOnCropSuccess(new File(handleImage(data)));
            }else{
                dispatchOnSetFaild();
            }
        }
    }

    //删除拍照留下的中间图片
    private void deleteTakePhotoFile(File file){
        if(file.isDirectory()){
            File[] fs = file.listFiles();
            for(File file1 : fs){
                deleteTakePhotoFile(file1);
            }
        }else{
            file.delete();
        }
    }

    private void dispatchOnCropSuccess(File file) {
        if (mListener != null) {
            mListener.onCropSuccess(file);
        }
        //删除拍照时候产生的中间文件
        deleteTakePhotoFile(new File(TAKE_PHOTO_DIR));
    }

    private void dispatchOnSetFaild() {
        if (mListener != null) {
            mListener.onCropFaild();
        }
        //删除拍照时候产生的中间文件
        deleteTakePhotoFile(new File(TAKE_PHOTO_DIR));
    }

    public interface SetListener {

        /**
         * 图片裁剪成功
         */
        void onCropSuccess(File file);

        /**
         * 图片裁剪失败
         */
        void onCropFaild();
    }

    private ImageSetFragment getImageSetFragmentByActivity(FragmentActivity activity){
        ImageSetFragment imageSetFragment = findImageSetFragmentByActivity(activity);
        if(imageSetFragment == null){
            imageSetFragment = new ImageSetFragment();
            imageSetFragment.setImageSetHelper(this);
            FragmentManager fragmentManager= activity.getSupportFragmentManager();
            fragmentManager.beginTransaction().add(imageSetFragment, IMAGE_SET_FRAGMENT_TAG).commit();
            fragmentManager.executePendingTransactions();
        }
        return imageSetFragment;
    }

    private ImageSetFragment findImageSetFragmentByActivity(FragmentActivity activity) {
        return (ImageSetFragment) activity.getSupportFragmentManager().findFragmentByTag(IMAGE_SET_FRAGMENT_TAG);
    }

    private ImageSetFragment getImageSetFragmentByV4Fragment(Fragment fragment){
        ImageSetFragment imageSetFragment = findImageSetFragmentByV4Fragment(fragment);
        if(imageSetFragment == null){
            imageSetFragment = new ImageSetFragment();
            imageSetFragment.setImageSetHelper(this);
            FragmentManager fragmentManager= fragment.getChildFragmentManager();
            fragmentManager.beginTransaction().add(imageSetFragment, IMAGE_SET_FRAGMENT_TAG).commit();
            fragmentManager.executePendingTransactions();
        }
        return imageSetFragment;
    }

    private ImageSetFragment findImageSetFragmentByV4Fragment(Fragment fragment) {
        return (ImageSetFragment) fragment.getChildFragmentManager().findFragmentByTag(IMAGE_SET_FRAGMENT_TAG);
    }


    @SuppressLint("ValidFragment")
    public static class ImageSetFragment extends Fragment{

        private ImgSetHelper mImgSetHelper;

        private void setImageSetHelper(ImgSetHelper helper){
            mImgSetHelper = helper;
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            mImgSetHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            mImgSetHelper.onActivityResult(requestCode, resultCode, data);
        }

        @Override
        public void onDetach() {
            super.onDetach();
            if(mImgSetHelper != null){
                mImgSetHelper.release();
                mImgSetHelper = null;
            }
        }
    }

    private void release(){
        mContext = null;
    }
}
