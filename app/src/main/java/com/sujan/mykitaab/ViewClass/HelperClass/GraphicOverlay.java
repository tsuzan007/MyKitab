/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sujan.mykitaab.ViewClass.HelperClass;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.vision.CameraSource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * A view which renders a series of custom graphics to be overlaid on top of an associated preview
 * (i.e., the camera preview).  The creator can add graphics objects, update the objects, and remove
 * them, triggering the appropriate drawing and invalidation within the view.<p>
 *
 * Supports scaling and mirroring of the graphics relative the camera's preview properties.  The
 * idea is that detection items are expressed in terms of a preview size, but need to be scaled up
 * to the full view size, and also mirrored in the case of the front-facing camera.<p>
 *
 * Associated {@link Graphic} items should use the following methods to convert to view coordinates
 * for the graphics that are drawn:
 * <ol>
 * <li>{@link Graphic#scaleX(float)} and {@link Graphic#scaleY(float)} adjust the size of the
 * supplied value from the preview scale to the view scale.</li>
 * <li>{@link Graphic#translateX(float)} and {@link Graphic#translateY(float)} adjust the coordinate
 * from the preview's coordinate system to the view coordinate system.</li>
 * </ol>
 */
public class GraphicOverlay<T extends GraphicOverlay.Graphic> extends android.support.v7.widget.AppCompatImageView  {
    private final Object mLock = new Object();
    private int mPreviewWidth;
    private float mWidthScaleFactor = 1.0f;
    private int mPreviewHeight;
    private float mHeightScaleFactor = 1.0f;
    private int mFacing = CameraSource.CAMERA_FACING_BACK;
    private Set<T> mGraphics = new HashSet<>();
    boolean flgPathDraw = true;
    boolean bfirstpoint = false;
    Point mfirstpoint;
    Point mlastpoint ;
    private ArrayList<Point> points=new ArrayList<Point>();
    Context context;
    private Paint mPaint;
    private Path mPath;
    private Paint mBitmapPaint;
    private Paint circlePaint;
    private Path circlePath;
    Canvas mCanvas;
    private Bitmap mBitmap;

    /**
     * Base class for a custom graphics object to be rendered within the graphic overlay.  Subclass
     * this and implement the {@link Graphic#draw(Canvas)} method to define the
     * graphics element.  Add instances to the overlay using {@link GraphicOverlay#add(Graphic)}.
     */
    public static abstract class Graphic {
        private GraphicOverlay mOverlay;

        public Graphic(GraphicOverlay overlay) {
            mOverlay = overlay;
        }

        /**
         * Draw the graphic on the supplied canvas.  Drawing should use the following methods to
         * convert to view coordinates for the graphics that are drawn:
         * <ol>
         * <li>{@link Graphic#scaleX(float)} and {@link Graphic#scaleY(float)} adjust the size of
         * the supplied value from the preview scale to the view scale.</li>
         * <li>{@link Graphic#translateX(float)} and {@link Graphic#translateY(float)} adjust the
         * coordinate from the preview's coordinate system to the view coordinate system.</li>
         * </ol>
         *
         * @param canvas drawing canvas
         */
        public abstract void draw(Canvas canvas);

        /**
         * Returns true if the supplied coordinates are within this graphic.
         */
        public abstract boolean contains(float x, float y);

        /**
         * Adjusts a horizontal value of the supplied value from the preview scale to the view
         * scale.
         */
        public float scaleX(float horizontal) {
            return horizontal * mOverlay.mWidthScaleFactor;
        }

        /**
         * Adjusts a vertical value of the supplied value from the preview scale to the view scale.
         */
        public float scaleY(float vertical) {
            return vertical * mOverlay.mHeightScaleFactor;
        }

        /**
         * Adjusts the x coordinate from the preview's coordinate system to the view coordinate
         * system.
         */
        public float translateX(float x) {
            if (mOverlay.mFacing == CameraSource.CAMERA_FACING_FRONT) {
                return mOverlay.getWidth() - scaleX(x);
            } else {
                return scaleX(x);
            }
        }

        /**
         * Adjusts the y coordinate from the preview's coordinate system to the view coordinate
         * system.
         */
        public float translateY(float y) {
            return scaleY(y);
        }

        public void postInvalidate() {
            mOverlay.postInvalidate();
        }
    }

    public GraphicOverlay(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
       // init(attrs);

    }
    private void init(@Nullable AttributeSet attrs){
       // this.setOnTouchListener(this);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(12);
        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        circlePaint = new Paint();
        circlePath = new Path();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.BLUE);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeJoin(Paint.Join.MITER);
        circlePaint.setStrokeWidth(4f);
        bfirstpoint=false;

    }

    /**
     * Removes all graphics from the overlay.
     */
    public void clear() {
        synchronized (mLock) {
            mGraphics.clear();
        }
        postInvalidate();
    }

    /**
     * Adds a graphic to the overlay.
     */
    public void add(T graphic) {
        synchronized (mLock) {
            mGraphics.add(graphic);
        }
        postInvalidate();
    }

    /**
     * Removes a graphic from the overlay.
     */
    public void remove(T graphic) {
        synchronized (mLock) {
            mGraphics.remove(graphic);
        }
        postInvalidate();
    }

    /**
     * Returns the first graphic, if any, that exists at the provided absolute screen coordinates.
     * These coordinates will be offset by the relative screen position of this view.
     * @return First graphic containing the point, or null if no text is detected.
     */
    public T getGraphicAtLocation(float rawX, float rawY) {
        synchronized (mLock) {
            // Get the position of this View so the raw location can be offset relative to the view.
            int[] location = new int[2];
            this.getLocationOnScreen(location);
            for (T graphic : mGraphics) {
                if (graphic.contains(rawX - location[0], rawY - location[1])) {
                    return graphic;
                }
            }
            return null;
        }
    }

    /**
     * Sets the camera attributes for size and facing direction, which informs how to transform
     * image coordinates later.
     */
    public void setCameraInfo(int previewWidth, int previewHeight, int facing) {
        synchronized (mLock) {
            mPreviewWidth = previewWidth;
            mPreviewHeight = previewHeight;
            mFacing = facing;
        }
        postInvalidate();
    }

    /**
     * Draws the overlay with its associated graphic objects.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        synchronized (mLock) {
            if ((mPreviewWidth != 0) && (mPreviewHeight != 0)) {
                mWidthScaleFactor = (float) canvas.getWidth() / (float) mPreviewWidth;
                mHeightScaleFactor = (float) canvas.getHeight() / (float) mPreviewHeight;
            }

            for (Graphic graphic : mGraphics) {
                graphic.draw(canvas);
            }
        }
//        canvas.drawBitmap( mBitmap, 0, 0, mBitmapPaint);
//        canvas.drawPath( circlePath,  circlePaint);
//        boolean first = true;
//        for (int i = 0; i < points.size(); i += 2) {
//            android.graphics.Point point = points.get(i);
//            if (first) {
//                first = false;
//                mPath.moveTo(point.x, point.y);
//            } else if (i < points.size() - 1) {
//                android.graphics.Point next = points.get(i + 1);
//                mPath.quadTo(point.x, point.y, next.x, next.y);
//            } else {
//                mlastpoint = points.get(i);
//                mPath.lineTo(point.x, point.y);
//            }
//        }
//        canvas.drawPath( mPath,  mPaint);
    }

//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//
//        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//        mCanvas = new Canvas(mBitmap);
//
//    }
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        float x = event.getX();
//        float y = event.getY();
//
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                touch_start(x, y);
//                invalidate();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                touch_move(x, y);
//                postInvalidate();
//                break;
//            case MotionEvent.ACTION_UP:
//                touch_up();
//                postInvalidate();
//                break;
//        }
//        return true;
//    }
//
//
//
//    public boolean onTouch(View view, MotionEvent event) {
//        // if(event.getAction() != MotionEvent.ACTION_DOWN)
//        // return super.onTouchEvent(event);
//
//        Point point = new Point();
//        point.x = (int) event.getX();
//        point.y = (int) event.getY();
//
//
//        if (flgPathDraw) {
//
//            if (bfirstpoint) {
//
//                if (comparepoint(mfirstpoint, point)) {
//                    // points.add(point);
//                    points.add(mfirstpoint);
//                    flgPathDraw = false;
//                    showcropdialog();
//                } else {
//                    points.add(point);
//                }
//            } else {
//                points.add(point);
//            }
//
//            if (!(bfirstpoint)) {
//
//                mfirstpoint = point;
//                bfirstpoint = true;
//            }
//        }
//
//        invalidate();
//        Log.e("Hi  ==>", "Size: " + point.x + " " + point.y);
//
//        if (event.getAction() == MotionEvent.ACTION_UP) {
//            Log.d("Action up**>>>>", "called");
//
//            if (flgPathDraw) {
//                if (points.size() > 12) {
//                    if (!comparepoint(mfirstpoint, mlastpoint)) {
//                        flgPathDraw = false;
//                        points.add(mfirstpoint);
//                        showcropdialog();
//                    }
//                }
//            }
//        }
//
//        return true;
//    }
//
//    private boolean comparepoint(Point first, Point current) {
//        int left_range_x = (int) (current.x - 3);
//        int left_range_y = (int) (current.y - 3);
//
//        int right_range_x = (int) (current.x + 3);
//        int right_range_y = (int) (current.y + 3);
//
//        if ((left_range_x < first.x && first.x < right_range_x)
//                && (left_range_y < first.y && first.y < right_range_y)) {
//            if (points.size() < 10) {
//                return false;
//            } else {
//                return true;
//            }
//        } else {
//            return false;
//        }
//
//    }
//    private float mX, mY;
//    private static final float TOUCH_TOLERANCE = 4;
//
//    private void touch_start(float x, float y) {
//        mPath.reset();
//        mPath.moveTo(x, y);
//        mX = x;
//        mY = y;
//    }
//
//    private void touch_move(float x, float y) {
//        float dx = Math.abs(x - mX);
//        float dy = Math.abs(y - mY);
//        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
//            mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
//            mX = x;
//            mY = y;
//
//            circlePath.reset();
//            circlePath.addCircle(mX, mY, 30, Path.Direction.CW);
//        }
//    }
//
//    private void touch_up() {
//        mPath.lineTo(mX, mY);
//        circlePath.reset();
//        // commit the path to our offscreen
//        mCanvas.drawPath(mPath,  mPaint);
//        // kill this so we don't double draw
//        mPath.reset();
//    }
//    private void showcropdialog() {
//        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent intent;
//                switch (which) {
//                    case DialogInterface.BUTTON_POSITIVE:
//                        // Yes button clicked
//                        // bfirstpoint = false;
//                        Bitmap bitmap2=mBitmap;
//
//
//                        Canvas canvas = new Canvas(bitmap2);
////                        Paint paint = new Paint();
////                        paint.setAntiAlias(true);
//                        Log.e("########",points.size()+"");
//
//                        Path path = new Path();
//                        for (int i = 0; i < points.size(); i++) {
//                            path.lineTo(points.get(i).x, points.get(i).y);
//                        }
//                        canvas.drawPath(path, mPaint);
//                        //  if (crop) {
//                        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
//
////                        } else {
////                            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
////                        }
//                        //canvas.drawBitmap(bitmap2, 0, 0, paint);
//
////                        intent = new Intent(mContext, CropActivity.class);
////                        intent.putExtra("crop", true);
////                        mContext.startActivity(intent);
//                        break;
//
//                    case DialogInterface.BUTTON_NEGATIVE:
//                        // No button clicked
//                        Log.e(".......","Cancelled is clicked");
//
////                        intent = new Intent(mContext, CropActivity.class);
////                        intent.putExtra("crop", false);
////                        mContext.startActivity(intent);
////
////                        bfirstpoint = false;
////                        // resetView();
//
//                        break;
//                }
//            }
//        };
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setMessage("Do you Want to save Crop or Non-crop image?")
//                .setPositiveButton("Crop", dialogClickListener)
//                .setNegativeButton("Non-crop", dialogClickListener).show()
//                .setCancelable(false);
//    }
}
