package com.sujan.mykitaab.ViewClass.Fragments;

import android.Manifest;


import android.app.Fragment;
import android.app.IntentService;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;

import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;

import android.support.v13.app.FragmentCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.squareup.picasso.Picasso;


import com.sujan.mykitaab.R;
import com.sujan.mykitaab.ViewClass.HelperClass.GraphicOverlay;
import com.sujan.mykitaab.ViewClass.HelperClass.MyCustomImageView;
import com.sujan.mykitaab.ViewClass.HelperClass.OcrGraphic;
import com.sujan.mykitaab.ViewClass.HelperClass.SomeView;

import java.io.File;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by macbookpro on 4/16/17.
 */

public class AlbumFragment extends Fragment {


    @BindView(R.id.floatingaction)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.floatingaction2)
    FloatingActionButton floatingActionButton2;
    @BindView(R.id.test)
    GraphicOverlay imageView;
    private SparseArray<TextBlock> items;
    private ArrayList<Rect> listofobject;
    private static final int REQUEST_CODE_FOR_CAMERA = 1;
    private static final int GALLERY_INTENT_CONSTANT = 2;
    final String[] permision = {Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    Intent intent;
    Uri uriSavedImage = null;
    private TextRecognizer textRecognizer;
    private Frame frame;
    File image;
    private OcrGraphic ocrGraphic;
    ArrayList<Rect> rect;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.albumfragment, container, false);
        ButterKnife.bind(this, view);
        textRecognizer = new TextRecognizer.Builder(getActivity()).build();
        if (!textRecognizer.isOperational()) {
            Log.w("..", "Detector dependencies are not yet available.");

            // Check for low storage.  If there is low storage, the native library will not be
            // downloaded, so detection will not become operational.
            IntentFilter lowstorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
            boolean hasLowStorage = getActivity().registerReceiver(null, lowstorageFilter) != null;
            if (hasLowStorage) {
                Toast.makeText(getActivity(), "low memory", Toast.LENGTH_LONG).show();
                Log.w("...", "low memory");
            }
        }


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float rawX = motionEvent.getRawX();
                float rawY = motionEvent.getRawY();
                Log.e("$$$$$", motionEvent.getX() + "..." + motionEvent.getY());
                onTap(rawX, rawY);
                return true;
            }
        });


    }

    private void onTap(float rawX, float rawY) {
//
//        int[] location = new int[2];
//        imageView.getLocationOnScreen(location);
//
//        for (int x = 0; x < items.size(); x++) {
//            TextBlock text = items.valueAt(x);
//            Rect rect = text.getBoundingBox();
//            if (rect.left < rawX - location[0] && rect.right > rawX - location[0] && rect.top < rawY - location[1] && rect.bottom > rawY - location[1]) {
//                if (text != null && text.getValue() != null) {
//                    Log.d("img....", "text data is being spoken! " + text.getValue());
//                    // Speak the string.
//
//                } else {
//                    Log.d("img....", "text data is null");
//                }
//
//            }
//
//
//        }

    }


    @OnClick(R.id.floatingaction)
    public void OnfloatingactioniconClicked(FloatingActionButton floatingActionButton) {
        if (ContextCompat.checkSelfPermission(getActivity(),
                permision[0]) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(getActivity(), permision[1]) == PackageManager.PERMISSION_GRANTED) {
                if(ContextCompat.checkSelfPermission(getActivity(), permision[2]) == PackageManager.PERMISSION_GRANTED){
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

                File imagesFolder = new File(Environment.getExternalStorageDirectory(), "MyImages");
                imagesFolder.mkdirs();

                image = new File(imagesFolder, "QR_" + timeStamp + ".png");
                uriSavedImage = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".provider", image);


                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                startActivityForResult(intent, REQUEST_CODE_FOR_CAMERA);
            }


        }
        } else {

                if (shouldShowRequestPermissionRationale(permision[0]) &&
                        shouldShowRequestPermissionRationale(permision[1])
                        && shouldShowRequestPermissionRationale(permision[2])) {

                    FragmentCompat.requestPermissions(this, permision, REQUEST_CODE_FOR_CAMERA);

                } else {
                    FragmentCompat.requestPermissions(this, permision, REQUEST_CODE_FOR_CAMERA);
                    Toast.makeText(getActivity(), "Access is not granted from user", Toast.LENGTH_LONG).show();
                }
            }




    }

    @OnClick(R.id.floatingaction2)
    public void onFloatingActionButton2Clicked() {

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, GALLERY_INTENT_CONSTANT);

            } else {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, GALLERY_INTENT_CONSTANT);
            }

        } else {
            Log.e("....", "gallery clicked");
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY_INTENT_CONSTANT);


        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.e("...", "inside onrequest permission");
        if (requestCode == REQUEST_CODE_FOR_CAMERA &&
                permissions[0].equals(Manifest.permission.CAMERA) &&
                permissions[1].equals(Manifest.permission.READ_EXTERNAL_STORAGE)&&
                permissions[2].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)&&
                grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                grantResults[2]==PackageManager.PERMISSION_GRANTED) {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
            startActivityForResult(intent, REQUEST_CODE_FOR_CAMERA);
        }
        if (requestCode == GALLERY_INTENT_CONSTANT &&
                permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)&&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.e("....", "gallery clicked");
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY_INTENT_CONSTANT);

        } else {
            Toast.makeText(getActivity(), "Permission must be granted to proceed further", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onActivityResult(final int requestCode, int resultCode, final Intent data) {
        Log.e("....", "inside onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            String realpath = "";
            Bitmap bitmap = null;
            int orientation = 0;

            if (requestCode == REQUEST_CODE_FOR_CAMERA) {
                try {
                    ExifInterface exifInterface = new ExifInterface(image.getAbsolutePath());
                    orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                bitmap= BitmapFactory.decodeFile(image.getAbsolutePath());
                int rotate=rotatebitmap(orientation);
                Picasso.with(getActivity())
                        .load(uriSavedImage)
                        .placeholder(R.drawable.ic_menu_gallery)
                        .error(R.drawable.ic_menu_camera)
                        .rotate(rotate)
                        .into(imageView);
            } else if (requestCode == GALLERY_INTENT_CONSTANT) {
                uriSavedImage = data.getData();
                String path = uriSavedImage.getPath();
                String[] projection = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(uriSavedImage, projection, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    int index = cursor.getColumnIndex(projection[0]);
                    realpath = cursor.getString(index);
                    cursor.close();
                }
                try {
                    ExifInterface exifInterface = new ExifInterface(realpath);
                    orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int rotate=rotatebitmap(orientation);
                Picasso.with(getActivity())
                        .load(uriSavedImage)
                        .placeholder(R.drawable.ic_menu_gallery)
                        .error(R.drawable.ic_menu_camera)
                        .rotate(rotate)
                        .into(imageView);
              bitmap = BitmapFactory.decodeFile(realpath);


            }
            frame = new Frame.Builder().setBitmap(bitmap).build();
            items = textRecognizer.detect(frame);
            for (int i = 0; i < items.size(); ++i) {
                TextBlock item = items.valueAt(i);
                if (item != null && item.getValue() != null) {
                    Log.d("OcrDetectorProcessor", "Text detected! " + item.getValue());
                }
            }
            String result = getTotal(items);
            if (!result.equals("0.0")) {
                Toast.makeText(getActivity(), "Total expenditure is: " + result, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Error reading the picture.", Toast.LENGTH_LONG).show();
            }


//                OcrGraphic ocrGraphic = new OcrGraphic(imageView, item);
//                imageView.add(ocrGraphic);


        }
    }

    public int rotatebitmap(int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return 0;
            case ExifInterface.ORIENTATION_ROTATE_180:
                return 180;
            case ExifInterface.ORIENTATION_ROTATE_90:
                return 90;
            case ExifInterface.ORIENTATION_ROTATE_270:
                return 270;
            default:
                return 0;

        }
        }


    public String getTotal(SparseArray<TextBlock> items) {
        String current;
        double max = 0.0;
        List<? extends Text> textComponents;
        for (int x = 0; x < items.size(); x++) {
            textComponents = items.valueAt(x).getComponents();
            for (Text text : textComponents) {
                current = text.getValue();
                if (current.matches(".[0-9]*\\.[0-9][0-9]")) {
                    if (current.startsWith("$")) {
                        current = current.replace("$", "0");
                    }
                    if (max < Double.parseDouble(current)) {
                        max = Double.parseDouble(current);

                    }

                } else if (current.matches("[0-9]*\\.[0-9][0-9]")) {
                    if (max < Double.parseDouble(current)) {
                        max = Double.parseDouble(current);

                    }

                }

            }
        }

        return max + "";
    }


}
