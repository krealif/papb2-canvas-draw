package com.example.canvasdraw;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Canvas canvas;
    private Paint paint = new Paint();
    private Bitmap bitmap;
    private ImageView imageView;

    private int frameCount = 0;

    private int colorBlack;
    private int colorWhite;
    private int colorGreen400;
    private int colorGreen500;
    private int colorGreen600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // color
        colorBlack = ResourcesCompat.getColor(getResources(),
                R.color.black, null);
        colorWhite = ResourcesCompat.getColor(getResources(),
                R.color.white, null);
        colorGreen400 = ResourcesCompat.getColor(getResources(),
                R.color.green_400, null);
        colorGreen500 = ResourcesCompat.getColor(getResources(),
                R.color.green_500, null);
        colorGreen600 = ResourcesCompat.getColor(getResources(),
                R.color.green_600, null);

        imageView = findViewById(R.id.image_view);
        imageView.setOnClickListener(view -> {
            drawSomething(view);
        });
    }

    private void drawSomething(View view) {
        int width = view.getWidth();
        int halfWidth = width/2;

        int height = view.getHeight();
        int halfHeight = height/2;

        // face coordinate
        Point faceA = new Point(halfWidth-240, halfHeight-180);
        Point faceB = new Point(halfWidth+240, halfHeight-180);
        Point faceC = new Point(halfWidth, halfHeight+320);

        switch (frameCount) {
            case 0:
                // init canvas
                bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                imageView.setImageBitmap(bitmap);
                canvas = new Canvas(bitmap);

                // MOUSE FACE
                // make triangle path from coordinate
                Path path = new Path();
                path.setFillType(Path.FillType.EVEN_ODD);
                path.lineTo(faceA.x, faceA.y);
                path.lineTo(faceB.x, faceB.y);
                path.lineTo(faceC.x, faceC.y);
                path.lineTo(faceA.x, faceA.y);
                path.close();

                // draw face to canvas
                paint.setColor(colorGreen500);
                canvas.drawPath(path, paint);
                break;
            case 1:
                // MOUSE EYE
                Point leftEye = new Point(faceA.x+170, faceA.y+145);
                Point rightEye = new Point(faceB.x-170, faceB.y+145);

                paint.setColor(colorWhite);
                canvas.drawCircle(leftEye.x, leftEye.y, 58, paint);
                canvas.drawCircle(rightEye.x, rightEye.y, 58, paint);

                // EYEBALL
                paint.setColor(colorBlack);
                canvas.drawOval(new RectF(leftEye.x-20, leftEye.y-6,
                        leftEye.x+20, leftEye.y+42), paint);
                canvas.drawOval(new RectF(rightEye.x-20, rightEye.y-6,
                        rightEye.x+20, rightEye.y+42), paint);

                paint.setColor(colorWhite);
                canvas.drawCircle(leftEye.x-2, leftEye.y+12, 11, paint);
                canvas.drawCircle(rightEye.x+2, rightEye.y+12, 11, paint);
                break;
            case 2:
                // MOUSE EAR
                // coordinate
                Point leftEar = new Point(faceA.x+30, faceA.y-10);
                Point rightEar = new Point(faceB.x-30, faceB.y-10);

                // draw ear to canvas
                paint.setColor(colorGreen600);
                canvas.drawCircle(leftEar.x, leftEar.y, 130, paint);
                canvas.drawCircle(rightEar.x, rightEar.y, 130, paint);

                paint.setColor(colorGreen400);
                canvas.drawCircle(leftEar.x+20, leftEar.y+20, 90, paint);
                canvas.drawCircle(rightEar.x-20, rightEar.y+20, 90, paint);
                break;
            case 3:
                // MOUSE NOSE
                paint.setColor(colorBlack);
                canvas.drawCircle(faceC.x, faceC.y-60, 80, paint);
                break;
            case 4:
                // MOUSE WHISKERS
                paint.setColor(colorBlack);
                canvas.drawRect(new Rect(300, 1112,
                        780, 1137), paint);
                break;
            case 5:
                canvas.save();
                canvas.rotate(20, faceC.x, faceC.y-60);
                canvas.drawRect(new Rect(300, 1112,
                        780, 1137), paint);
                canvas.restore();
                break;
            case 6:
                canvas.save();
                canvas.rotate(-20, faceC.x, faceC.y-60);
                canvas.drawRect(new Rect(300, 1112,
                        780, 1137), paint);
                canvas.restore();
                break;
            default:
                break;
        }

        frameCount++;
        view.invalidate();
    }
}