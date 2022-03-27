package com.appg.BottomNavigation.Home.FruitNinja.GamePlay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.appg.R;
import com.appg.splashscreen.SplashScreen;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FnGamePlay extends AppCompatActivity {

    String link;

    ArrayList<String> list = new ArrayList<String>();

    SwipeRefreshLayout swipeRefreshLayout;
    public ProgressBar progressbar;

    private WebView webview;

    private ValueCallback<Uri> mUploadMessage;
    private Uri mCapturedImageURI = null;
    private ValueCallback<Uri[]> mFilePathCallback;
    private String mCameraPhotoPath;
    private static final int INPUT_FILE_REQUEST_CODE = 1;
    private static final int FILECHOOSER_RESULTCODE = 1;
    public int a=0,b=0;

    public int stads=0;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {

            if (requestCode != INPUT_FILE_REQUEST_CODE || mFilePathCallback == null)
            {
                super.onActivityResult(requestCode, resultCode, data);
                return;
            }
            Uri[] results = null;
            // Check that the response is a good one
            if (resultCode == Activity.RESULT_OK)
            {
                if (data == null)
                {
                    // If there is not data, then we may have taken a photo
                    if (mCameraPhotoPath != null)
                    {
                        results = new Uri[]{Uri.parse(mCameraPhotoPath)};
                    }
                }   else {
                    String dataString = data.getDataString();
                    if (dataString != null) {
                        results = new Uri[]{Uri.parse(dataString)};
                    }
                }
            }
            mFilePathCallback.onReceiveValue(results);
            mFilePathCallback = null;
        }
        else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT)
        {
            if (requestCode != FILECHOOSER_RESULTCODE || mUploadMessage == null)
            {
                super.onActivityResult(requestCode, resultCode, data);
                return;
            }

            if (requestCode == FILECHOOSER_RESULTCODE)
            {

                if (null == this.mUploadMessage)
                {
                    return;
                }
                Uri result = null;
                try
                {
                    if (resultCode != RESULT_OK)
                    {
                        result = null;
                    }
                    else
                    {
                        result = data == null ? mCapturedImageURI : data.getData();
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "activity :" + e,
                            Toast.LENGTH_LONG).show();
                }
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        }
        return;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fn_game_play);


        Intent intent = getIntent();
        list=intent.getStringArrayListExtra("url");

        Log.d("link",list.toString());
        //String receivedName =  intent.getStringExtra("name");
         link=list.get(0);
         Log.d("ls",link);


//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressbar = (ProgressBar)findViewById(R.id.pr);
        webview = (WebView) findViewById(R.id.wb);
       // swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.sw);

      //  getSupportActionBar().setTitle(receivedName);
        getSupportActionBar().hide();


        progressbar.setMax(100);
        progressbar.setVisibility(View.GONE);

        webview.getSettings().setPluginState(WebSettings.PluginState.ON);

        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setAllowContentAccess(true);
        webview.getSettings().supportZoom();


        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setPluginState(WebSettings.PluginState.ON);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webview.getSettings().setUseWideViewPort(true);


        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.d("Failure Url :" , failingUrl);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                Log.d("Ssl Error:",handler.toString() + "error:" +  error);
                handler.proceed();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


        // download code

        webview.setDownloadListener(
                new DownloadListener()
                {
                    public void onDownloadStart(String url, String userAgent,
                                                String contentDisposition, String mimetype,
                                                long contentLength)
                    {
                        DownloadManager.Request request = new DownloadManager.Request(
                                Uri.parse(url));
                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "download");
                        DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                        dm.enqueue(request);

                    }
                });

        //refresh code
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
//        {
//            public void onRefresh()
//            {
//                if(CheckNetwork.isInternetAvailable(FnGamePlay.this))
//                {
//                    if(b==1) {
//                        FnGamePlay.this.webview.reload();
//                    }
//                    else if(b==0)
//                    {
//                        webview.loadUrl(link);
//
//                    }
//                    if(  progressbar.getVisibility() == progressbar.GONE )
//                    {
//                        progressbar.setVisibility(ProgressBar.VISIBLE);
//
//                    }
//                    FnGamePlay.this.swipeRefreshLayout.setRefreshing(false);
//                }
//                else
//                {
//                    Toast.makeText(FnGamePlay.this,"No Internet Connection",3000).show();
////                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "No Internet Connection", Snackbar.LENGTH_LONG);
////                    snackbar.show();
//                    FnGamePlay.this.swipeRefreshLayout.setRefreshing(false);
//                    progressbar.setVisibility(ProgressBar.GONE);
//
//                }
//            }
//        });


        // external link

        if(FnGamePlay.CheckNetwork.isInternetAvailable(FnGamePlay.this)) //returns true if internet available
        {
            b=1;

            new Handler().postDelayed(new Runnable() {
                public void run() {

                    webview.loadUrl(list.get(1));
                }
            }, 82 * 1000);

            new Handler().postDelayed(new Runnable() {
                public void run() {

                    webview.loadUrl(list.get(2));
                }
            }, 82*2 * 1000);

            new Handler().postDelayed(new Runnable() {
                public void run() {

                    webview.loadUrl(list.get(3));
                }
            }, 82*3 * 1000);

            new Handler().postDelayed(new Runnable() {
                public void run() {

                    webview.loadUrl(list.get(4));
                }
            }, 82*4 * 1000);

            new Handler().postDelayed(new Runnable() {
                public void run() {

                    webview.loadUrl(list.get(5));
                }
            }, 82*5 * 1000);

            new Handler().postDelayed(new Runnable() {
                public void run() {

                    webview.loadUrl(list.get(6));
                }
            }, 82*6 * 1000);

            new Handler().postDelayed(new Runnable() {
                public void run() {

                    webview.loadUrl(list.get(7));
                }
            }, 82*7 * 1000);

            new Handler().postDelayed(new Runnable() {
                public void run() {

                    webview.loadUrl(list.get(8));
                }
            }, 82*8 * 1000);


            webview.loadUrl(link);

        }
        else
        {
            Toast.makeText(FnGamePlay.this,"No Internet Connection",1000).show();
//            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "No Internet Connection", Snackbar.LENGTH_LONG);
//            snackbar.show();


            FnGamePlay.this.swipeRefreshLayout.setRefreshing(false);
            progressbar.setVisibility(ProgressBar.GONE);
        }


        webview.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                // is this a play store URL?
                String partialUrl = "/store/apps/details?id=";
                if (url.contains(partialUrl))
                {


                    // extract the app id from the URL
                    int pos = url.indexOf(partialUrl) + partialUrl.length();
                    String appId = url.substring(pos);

                    try {
                        // open the google play app
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("market://details?id=" + appId));
                        FnGamePlay.this.startActivity(intent);
                        return true;  // we overrode the url load

                    } catch (ActivityNotFoundException e) {
                        // no google play app, load URL in device browser
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        FnGamePlay.this.startActivity(intent);
                        return true;
                    }
                }
                return false;  // no override, let the webview load this url
            }

        });

        webview.setWebChromeClient(new FnGamePlay.ChromeClient());



    }


    public class ChromeClient extends WebChromeClient
    {
        // progress called first time
        @Override
        public void onProgressChanged(WebView view,int progress)
        {
            progressbar.setProgress(progress);
            if(progress <100 && progressbar.getVisibility() == progressbar.GONE && a==0)
            {
                progressbar.setVisibility(ProgressBar.VISIBLE);
                a++;
            }
            if(progress == 100 )
            {
                progressbar.setVisibility(ProgressBar.GONE);
                a++;
            }
        }

        // show the web page in webview but not in web browser
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //1 option
            // getApplicationContext().startActivity(
            // new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            // 2 option
            //view.loadUrl(url);

            webview.loadUrl(link);
            return true;
        }






        // For Android 5.0
        public boolean onShowFileChooser(WebView view, ValueCallback<Uri[]> filePath, WebChromeClient.FileChooserParams fileChooserParams) {
            // Double check that we don't have any existing callbacks
            if (mFilePathCallback != null) {
                mFilePathCallback.onReceiveValue(null);
            }
            mFilePathCallback = filePath;

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                    takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
                } catch (IOException ex) {
                    // Error occurred while creating the File
                    //  Log.e(Common.TAG, "Unable to create Image File", ex);
                }

                // Continue only if the File was successfully created
                if (photoFile != null) {
                    mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(photoFile));
                } else {
                    takePictureIntent = null;
                }
            }

            Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
            contentSelectionIntent.setType("image/*");

            Intent[] intentArray;
            if (takePictureIntent != null) {
                intentArray = new Intent[]{takePictureIntent};
            } else {
                intentArray = new Intent[0];
            }

            Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
            chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
            chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);

            startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);

            return true;

        }

        // openFileChooser for Android 3.0+
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType)
        {

            mUploadMessage = uploadMsg;
            // Create AndroidExampleFolder at sdcard
            // Create AndroidExampleFolder at sdcard

            File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "AndroidExampleFolder");

            if (!imageStorageDir.exists()) {
                // Create AndroidExampleFolder at sdcard
                imageStorageDir.mkdirs();
            }

            // Create camera captured image file path and name
            File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");

            mCapturedImageURI = Uri.fromFile(file);

            // Camera capture image intent
            final Intent captureIntent = new Intent(
                    android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);

            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");

            // Create file chooser intent
            Intent chooserIntent = Intent.createChooser(i, "Image Chooser");

            // Set camera intent to file chooser
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Parcelable[] { captureIntent });

            // On select image call onActivityResult method of activity
            startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
        }

        // openFileChooser for Android < 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg)
        {
            openFileChooser(uploadMsg, "");
        }

        //openFileChooser for other Android versions
        public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                    String acceptType,
                                    String capture)
        {

            openFileChooser(uploadMsg, acceptType);
        }

    }

    private File createImageFile() throws IOException
    {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return imageFile;
    }


    public static class CheckNetwork
    {
        private  static final String TAG = FnGamePlay.CheckNetwork.class.getSimpleName();
        public static boolean isInternetAvailable(Context context)
        {
            NetworkInfo info = (NetworkInfo) ((ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

            if (info == null)
            {
                Log.d(TAG,"no internet connection");
                return false;
            }
            else
            {
                if(info.isConnected())
                {
                    Log.d(TAG," internet connection available...");
                    return true;
                }
                else
                {
                    Log.d(TAG," internet connection");
                    return true;
                }

            }
        }
    }
}