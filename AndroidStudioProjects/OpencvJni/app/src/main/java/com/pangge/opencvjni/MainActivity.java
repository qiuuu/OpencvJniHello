package com.pangge.opencvjni;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jakewharton.rxbinding2.internal.Functions;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity{

    @BindView(R.id.btn_gray_process) Button btnProc;

    @BindView(R.id.btn_cartoon_process) Button btnCartoon;

    @BindView(R.id.btn_canny_process) Button btnCanny;

    @BindView(R.id.btn_epf_process) Button btnEpf;

    @BindView(R.id.btn_exit) Button btnExit;

    @BindView(R.id.image_view) ImageView imageView;


    private CompositeDisposable compositeDisposable;


    private Bitmap bmp;
    private int w;
    private int h;
    private int[] pixels;


    public native int[] grayProc(int[] pixels, int w,int h);
    public native int[] cannyProc(int[] pixels, int w,int h);
    public native int[] epfProc(int[] pixels, int w,int h);
    public native int[] cartoonProc(int[] pixels, int w,int h);




    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("opencv_java3");
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        compositeDisposable = new CompositeDisposable();

        ButterKnife.bind(this);
       // btnTest = (Button)findViewById(R.id.btn_canny_process);


        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.tu1);
        w = bmp.getWidth();
        h = bmp.getHeight();

        Log.i("OK","ssuccessful");
        proImage();

        imageView.setImageBitmap(bmp);
       /* btnProc.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        btnCanny.setOnClickListener(this);
        btnCartoon.setOnClickListener(this);*/



        // Example of a call to a native method
       // TextView tv = (TextView) findViewById(R.id.sample_text);
       // tv.setText(stringFromJNI());
    }

    @Override
    protected void onPause() {
        super.onPause();
        compositeDisposable.clear();
    }

    private void proImage(){

        compositeDisposable.add(RxView.clicks(btnProc)
                .flatMap(o -> handlerImage(1))
                //.subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
               // .observeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<int[]>() {
                    @Override
                    public void onComplete() {

                        Log.i("con", "ssuccessful");
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.i(e.toString(), "error");
                    }

                    @Override
                    public void onNext(int[] value) {
                        Bitmap resultImg = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
                        resultImg.setPixels(value, 0, w, 0, 0, w, h);
                        imageView.setImageBitmap(resultImg);

                    }
                }));
        compositeDisposable.add(RxView.clicks(btnExit)
                .flatMap(o -> handlerImage(2))
                //.subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                // .observeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<int[]>() {
                    @Override
                    public void onComplete() {

                        Log.i("con", "ssuccessful");
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.i(e.toString(), "error");
                    }

                    @Override
                    public void onNext(int[] value) {

                        imageView.setImageBitmap(bmp);

                    }
                }));
        compositeDisposable.add(RxView.clicks(btnCanny)
                .flatMap(o -> handlerImage(3))
                //.subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                // .observeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<int[]>() {
                    @Override
                    public void onComplete() {

                        Log.i("con", "ssuccessful");
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.i(e.toString(), "error");
                    }

                    @Override
                    public void onNext(int[] value) {
                        Bitmap resultImg = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
                        resultImg.setPixels(value, 0, w, 0, 0, w, h);
                        imageView.setImageBitmap(resultImg);

                    }
                }));
        compositeDisposable.add(RxView.clicks(btnCartoon)
                .flatMap(o -> handlerImage(4))
                //.subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                // .observeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<int[]>() {
                    @Override
                    public void onComplete() {

                        Log.i("con", "ssuccessful");
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.i(e.toString(), "error");
                    }

                    @Override
                    public void onNext(int[] value) {
                        Bitmap resultImg = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
                        resultImg.setPixels(value, 0, w, 0, 0, w, h);
                        imageView.setImageBitmap(resultImg);

                    }
                }));
        compositeDisposable.add(RxView.clicks(btnEpf)
                .flatMap(o -> handlerImage(5))
                //.subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                // .observeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<int[]>() {
                    @Override
                    public void onComplete() {

                        Log.i("con", "ssuccessful");
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.i(e.toString(), "error");
                    }

                    @Override
                    public void onNext(int[] value) {
                        Bitmap resultImg = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
                        resultImg.setPixels(value, 0, w, 0, 0, w, h);
                        imageView.setImageBitmap(resultImg);

                    }
                }));


    }
    private Observable<int[]> handlerImage(int i){
        return Observable.create(new ObservableOnSubscribe<int[]>() {

            @Override
            public void subscribe(ObservableEmitter<int[]> e) throws Exception {
                pixels = new int[w*h];

                bmp.getPixels(pixels, 0, w,0,0,w,h);


                int[] resultInt;
                switch (i){
                    case 1:
                        resultInt = grayProc(pixels, w, h);
                        e.onNext(resultInt);
                        break;
                    case 2:
                       // resultInt = Proc(pixels, w, h);
                        int[] t = {1,2};
                        e.onNext(t);
                        break;
                    case 3:
                        resultInt = cannyProc(pixels, w, h);
                        e.onNext(resultInt);
                        break;
                    case 4:
                        resultInt = cartoonProc(pixels, w, h);
                        e.onNext(resultInt);
                        break;
                    case 5:
                        resultInt = epfProc(pixels, w, h);
                        e.onNext(resultInt);
                        break;
                    default:
                        int[] t1 = {1,2};
                        e.onNext(t1);
                        break;




                }


            }
        }).subscribeOn(Schedulers.io());
    }


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    //public native String stringFromJNI();
}
