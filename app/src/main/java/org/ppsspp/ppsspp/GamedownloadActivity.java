package org.ppsspp.ppsspp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.LinearLayout;
import android.webkit.WebView;
import android.webkit.WebSettings;
import androidx.cardview.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.button.*;
import android.content.Intent;
import android.net.Uri;
import android.content.ClipData;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import arabware.libs.getThumbnail.*;
import org.jetbrains.kotlin.*;
import me.ibrahimsn.particle.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;

public class GamedownloadActivity extends AppCompatActivity {
	
	public final int REQ_CD_FILEMAKER = 101;
	
	private String filetime = "";
	private String filename = "";
	
	private ArrayList<HashMap<String, Object>> download_complete = new ArrayList<>();
	
	private LinearLayout linear9;
	private LinearLayout linear10;
	private LinearLayout linear12;
	private WebView webview1;
	private LinearLayout linear18;
	private LinearLayout linear19;
	private CardView cardview6;
	private LinearLayout linear11;
	private ImageView imageview2;
	private LinearLayout linear13;
	private LinearLayout low;
	private CardView cardview7;
	private ImageView imageview3;
	private LinearLayout linear15;
	private LinearLayout linear16;
	private LinearLayout linear17;
	private ImageView imageview4;
	private TextView textview7;
	private TextView name;
	private TextView textview9;
	private TextView id;
	private TextView textview11;
	private TextView size;
	private MaterialButton materialbutton1;
	
	private Intent i = new Intent();
	private RequestNetwork Net;
	private RequestNetwork.RequestListener _Net_request_listener;
	private Intent Filemaker = new Intent(Intent.ACTION_GET_CONTENT);
	private Calendar Time = Calendar.getInstance();
	private SharedPreferences download_data;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.gamedownload);
		initialize(_savedInstanceState);
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		} else {
			initializeLogic();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear9 = findViewById(R.id.linear9);
		linear10 = findViewById(R.id.linear10);
		linear12 = findViewById(R.id.linear12);
		webview1 = findViewById(R.id.webview1);
		webview1.getSettings().setJavaScriptEnabled(true);
		webview1.getSettings().setSupportZoom(true);
		linear18 = findViewById(R.id.linear18);
		linear19 = findViewById(R.id.linear19);
		cardview6 = findViewById(R.id.cardview6);
		linear11 = findViewById(R.id.linear11);
		imageview2 = findViewById(R.id.imageview2);
		linear13 = findViewById(R.id.linear13);
		low = findViewById(R.id.low);
		cardview7 = findViewById(R.id.cardview7);
		imageview3 = findViewById(R.id.imageview3);
		linear15 = findViewById(R.id.linear15);
		linear16 = findViewById(R.id.linear16);
		linear17 = findViewById(R.id.linear17);
		imageview4 = findViewById(R.id.imageview4);
		textview7 = findViewById(R.id.textview7);
		name = findViewById(R.id.name);
		textview9 = findViewById(R.id.textview9);
		id = findViewById(R.id.id);
		textview11 = findViewById(R.id.textview11);
		size = findViewById(R.id.size);
		materialbutton1 = findViewById(R.id.materialbutton1);
		Net = new RequestNetwork(this);
		Filemaker.setType("*/*");
		Filemaker.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		download_data = getSharedPreferences("download_data", Activity.MODE_PRIVATE);
		
		//webviewOnProgressChanged
		webview1.setWebChromeClient(new WebChromeClient() {
				@Override public void onProgressChanged(WebView view, int _newProgress) {
					
				}
		});
		
		//OnDownloadStarted
		webview1.setDownloadListener(new DownloadListener() {
			public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
				DownloadManager.Request webview1a = new DownloadManager.Request(Uri.parse(url));
				String webview1b = CookieManager.getInstance().getCookie(url);
				webview1a.addRequestHeader("cookie", webview1b);
				webview1a.addRequestHeader("User-Agent", userAgent);
				webview1a.setDescription("Downloading file...");
				webview1a.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype));
				webview1a.allowScanningByMediaScanner(); webview1a.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); webview1a.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimetype));
				
				DownloadManager webview1c = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
				webview1c.enqueue(webview1a);
				showMessage("Downloading File....");
				BroadcastReceiver onComplete = new BroadcastReceiver() {
					public void onReceive(Context ctxt, Intent intent) {
						showMessage("Download Complete!");
						unregisterReceiver(this);
						
					}};
				registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
			}
		});
		
		webview1.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView _param1, String _param2, Bitmap _param3) {
				final String _url = _param2;
				
				super.onPageStarted(_param1, _param2, _param3);
			}
			
			@Override
			public void onPageFinished(WebView _param1, String _param2) {
				final String _url = _param2;
				
				super.onPageFinished(_param1, _param2);
			}
		});
		
		imageview4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				webview1.loadUrl(getIntent().getStringExtra("link"));
			}
		});
		
		//OnTouch
		materialbutton1.setOnTouchListener(new View.OnTouchListener(){
				@Override
				public boolean onTouch(View v, MotionEvent event){
						int ev = event.getAction();
						switch (ev) {
								case MotionEvent.ACTION_DOWN:
								
								 
								
								break;
								case MotionEvent.ACTION_UP:
								
								 
								
								break;
						} return true;
				}
		});
		
		materialbutton1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				{
					/*
DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;        
*/
					
					final AlertDialog.Builder build = new AlertDialog.Builder(GamedownloadActivity.this);
					
					build.setCancelable(false);
					build.setTitle("");
					
					build.setPositiveButton("✖️", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
							
							
									
							}
					});
					
					final LinearLayout container = new LinearLayout(getApplicationContext());
					
					container.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
					
					container.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
					
					container.setOrientation(LinearLayout.VERTICAL);
					
					container.setPadding(8,8,8,8);
					
					/*
container.setMinimumHeight(height/4);
container.setMinimumWidth(width/4);
*/
					
					final LinearLayout progLin = new LinearLayout(getApplicationContext());
					
					progLin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
					
					progLin.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
					
					progLin.setOrientation(LinearLayout.VERTICAL);
					
					progLin.setPadding(4,4,4,4);
					
					final ProgressBar progS = new ProgressBar(getApplicationContext(), null, android.R.attr.progressBarStyleHorizontal);
					
					progS.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
					
					progS.setIndeterminate(false);
					
					
					final LinearLayout progTextLin = new LinearLayout(getApplicationContext());
					
					progTextLin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
					
					progTextLin.setOrientation(LinearLayout.HORIZONTAL);
					
					progTextLin.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
					
					progTextLin.setPadding(7,7,7,7);
					
					final TextView progText = new TextView(getApplicationContext());
					
					progText.setTypeface(null, Typeface.BOLD);
					
					
					progText.setTextColor(Color.parseColor("#FF000000"));
					
					
					
					progTextLin.addView(progText);
					progLin.addView(progS);
					
					final LinearLayout btnLin = new LinearLayout(getApplicationContext());
					
					btnLin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
					
					btnLin.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
					
					btnLin.setOrientation(LinearLayout.HORIZONTAL);
					
					btnLin.setPadding(12,12,12,12);
					
					final Button playPause = new Button(getApplicationContext());
					
					playPause.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 90, 1));
					
					playPause.setTextColor(Color.parseColor("#FF000000"));
					playPause.setText("Download");
					playPause.setTextSize(13);
					playPause.setPadding(0,0,0,0);
					
					final Button cancel = new Button(getApplicationContext());
					cancel.setText("Cancel");
					cancel.setTextColor(Color.parseColor("#FF000000"));
					
					cancel.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 90, 1));
					cancel.setTextSize(13);
					cancel.setPadding(0,0,0,0);
					
					btnLin.addView(playPause);
					btnLin.addView(cancel);
					
					final LinearLayout textLin = new LinearLayout(getApplicationContext());
					
					textLin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
					
					textLin.setGravity(Gravity.BOTTOM|Gravity.LEFT);
					
					textLin.setOrientation(LinearLayout.HORIZONTAL);
					
					textLin.setPadding(5,5,5,5);
					
					
					final TextView pathText = new TextView(getApplicationContext());
					
					
					pathText.setTextColor(Color.parseColor("#FF000000"));
					pathText.setTypeface(null, Typeface.BOLD);
					pathText.setTextSize(14);
					
					android.graphics.drawable.GradientDrawable dr = new android.graphics.drawable.GradientDrawable(
					            GradientDrawable.Orientation.TOP_BOTTOM,
					            new int[] {0xFFcfd8dc, 0xFFeceff1});
					
					dr.setShape(GradientDrawable.RECTANGLE);
					dr.setCornerRadius(10);
					
					if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
						  playPause.setBackgroundDrawable(dr);
						  cancel.setBackgroundDrawable(dr);
						 } else {
						  playPause.setBackground(dr);
						  cancel.setBackground(dr);
						 }
					
					textLin.addView(pathText);
					
					container.addView(textLin);
					container.addView(progTextLin);
					container.addView(progLin);
					container.addView(btnLin);
					
					        URLFile = getIntent().getStringExtra("link");
							dirPath = "/storage/emulated/0/psp/";
							nameFile = getIntent().getStringExtra("name");
					
					if (!dirPath.endsWith("/")){
						pathText.setText(dirPath + "/" + nameFile);
					} else {
						pathText.setText(dirPath+nameFile);
					}
						
							
							progText.setText("");
					
					
					final PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
						.setDatabaseEnabled(true)
						.build();
					PRDownloader.initialize(GamedownloadActivity.this, config);
					
					playPause.setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
									
										if (Status.RUNNING == PRDownloader.getStatus(downloadId)) {
													PRDownloader.pause(downloadId);
													return;
										}
										playPause.setEnabled(false);
										progS.setIndeterminate(true);
										progS.getIndeterminateDrawable().setColorFilter(Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);
										if (Status.PAUSED == PRDownloader.getStatus(downloadId)) {
													PRDownloader.resume(downloadId);
													return;
										}
										downloadId = PRDownloader.download(URLFile, dirPath, nameFile)
											.build()
											.setOnStartOrResumeListener(new OnStartOrResumeListener() {
														@Override
														public void onStartOrResume() {
																	progS.setIndeterminate(false);
																	playPause.setEnabled(true);
																	playPause.setText("Pause");
																	cancel.setEnabled(true);
														}
										})
										.setOnPauseListener(new OnPauseListener() {
													@Override
													public void onPause() {
																playPause.setText("Resume");
													}
										})
										.setOnCancelListener(new OnCancelListener() {
													@Override
													public void onCancel() {
																playPause.setText("Start");
																cancel.setEnabled(false);
																progS.setProgress(0);
																progText.setText("");
																downloadId = 0;
																progS.setIndeterminate(false);
													}
										})
										.setOnProgressListener(new OnProgressListener() {
													@Override
													public void onProgress(Progress progress) {
																long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
																progS.setProgress((int) progressPercent);
																progText.setText(Utilss.getProgressDisplayLine(progress.currentBytes, progress.totalBytes));
																progS.setIndeterminate(false);
																
									
									
													}
										})
										.start(new OnDownloadListener() {
													@Override
													public void onDownloadComplete() {
																playPause.setEnabled(false);
																cancel.setEnabled(false);
																playPause.setText("Completed");
																Toast.makeText(getApplicationContext(), "100 %", Toast.LENGTH_SHORT).show();
									
									if (!dirPath.endsWith("/")){
										downloadedFile = dirPath + "/" + nameFile;
									} else {
										downloadedFile = dirPath + nameFile;
									}
									
									 
																
													}
													@Override
													public void onError(Error error) {
																playPause.setText("Start");
																Toast.makeText(getApplicationContext(), "Some error occurred" + "\n" + error.toString(), Toast.LENGTH_SHORT).show();
																progText.setText("");
																progS.setProgress(0);
																downloadId = 0;
																cancel.setEnabled(false);
																progS.setIndeterminate(false);
																playPause.setEnabled(true);
													}
										});
							}
					});
					
					cancel.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View view) {
											PRDownloader.cancel(downloadId);
											cancel.setEnabled(false);
											progText.setText("");
								}
					});
					
					
					build.setView(container);
					final AlertDialog alerto = build.create();
					alerto.show();
					
				}
				
			}
		});
		
		_Net_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
	}
	
	private void initializeLogic() {
		name.setText(getIntent().getStringExtra("name"));
		id.setText(getIntent().getStringExtra("id"));
		size.setText(getIntent().getStringExtra("Size"));
		Glide.with(getApplicationContext()).load(Uri.parse(getIntent().getStringExtra("img"))).into(imageview3);
		webview1.setDownloadListener(new DownloadListener() {
				public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
						DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
						String cookies = CookieManager.getInstance().getCookie(url);
						request.addRequestHeader("cookie", cookies);
						request.addRequestHeader("User-Agent", userAgent);
						request.setDescription("دانلود کردن فایل...");
						request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype));
						request.allowScanningByMediaScanner(); request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
						java.io.File aatv = new java.io.File(Environment.getExternalStorageDirectory().getPath() + "/Gta go/Download");if(!aatv.exists()){if (!aatv.mkdirs()){ Log.e("TravellerLog ::","Problem creating Image folder");}} request.setDestinationInExternalPublicDir("/psp/game", URLUtil.guessFileName(url, contentDisposition, mimetype));
						DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
						manager.enqueue(request);
						showMessage("دانلود کردن مود....");
						BroadcastReceiver onComplete = new BroadcastReceiver() {
								public void onReceive(Context ctxt, Intent intent) {
										showMessage("دانلود پایان!");
										unregisterReceiver(this);
								}};
						registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
				}
		});
		
		imageview4.setImageResource(R.drawable.downloadcircle);
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			case REQ_CD_FILEMAKER:
			if (_resultCode == Activity.RESULT_OK) {
				ArrayList<String> _filePath = new ArrayList<>();
				if (_data != null) {
					if (_data.getClipData() != null) {
						for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
							ClipData.Item _item = _data.getClipData().getItemAt(_index);
							_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
						}
					}
					else {
						_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
					}
				}
				
			}
			else {
				
			}
			break;
			default:
			break;
		}
	}
	
	public void _download_manager(final WebView _view, final String _path) {
		_view.setDownloadListener(new DownloadListener() {
			public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
				DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
				String cookies = CookieManager.getInstance().getCookie(url);
				request.addRequestHeader("cookie", cookies);
				request.addRequestHeader("User-Agent", userAgent);
				request.setDescription("Downloading file...");
				request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype));
				 filename = URLUtil.guessFileName(url,contentDisposition,mimetype);
				Time = Calendar.getInstance();
				filetime = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss").format(Time.getTime());
				request.allowScanningByMediaScanner(); request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
				java.io.File aatv = new java.io.File(Environment.getExternalStorageDirectory().getPath() + _path);
				
				if(!aatv.exists()){
					if (!aatv.mkdirs()){
						Log.e("TravellerLog ::","Problem creating Image folder");}} request.setDestinationInExternalPublicDir(_path, URLUtil.guessFileName(url, contentDisposition, mimetype));
				DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
				manager.enqueue(request);
				showMessage("Downloading File....");
				BroadcastReceiver onComplete = new BroadcastReceiver() {
					public void onReceive(Context ctxt, Intent intent) {
						if (filename.trim().equals("")) {
							SketchwareUtil.showMessage(getApplicationContext(), "obs bir hata meydana geldi");
						}
						else {
							{
								HashMap<String, Object> _item = new HashMap<>();
								_item.put("name", filename);
								_item.put("time", filetime);
								_item.put("path", _path);
								download_complete.add(_item);
							}
							download_data.edit().putString("download", new Gson().toJson(download_complete)).commit();
						}
						showMessage("Download Complete!");
						unregisterReceiver(this);
					}};
				registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
			}
		});
	}
	
	
	public void _extra() {
	}
	private int downloadId;
	private static String dirPath;
	private String URLFile;
	private String nameFile;
	private String downloadedFile;
	public static final class Utilss {
		    private Utilss() {
			    }
		    public static String getRootDirPath(Context context) {
			        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
				           // java.io.File file = context.getExternalFilesDirs(context.getApplicationContext(), null)[0, ;
				           // return file.getAbsolutePath();
				            return context.getApplicationContext().getFilesDir().getAbsolutePath();
				        } else {
				            return context.getApplicationContext().getFilesDir().getAbsolutePath();
				        }
			    }
		    public static String getProgressDisplayLine(long currentBytes, long totalBytes) {
			        return getBytesToMBString(currentBytes) + "/" + getBytesToMBString(totalBytes);
			    }
		    private static String getBytesToMBString(long bytes){
			        return String.format(Locale.ENGLISH, "%.2fMb", bytes / (1024.00 * 1024.00));
			    }
	}
	
	
	public static class PRDownloader {
		    private PRDownloader() {
			    }
		    public static void initialize(Context context) {
			        initialize(context, PRDownloaderConfig.newBuilder().build());
			    }
		    public static void initialize(Context context, PRDownloaderConfig config) {
			        ComponentHolder.getInstance().init(context, config);
			        DownloadRequestQueue.initialize();
			    }
		    public static DownloadRequestBuilder download(String url, String dirPath, String fileName) {
			        return new DownloadRequestBuilder(url, dirPath, fileName);
			    }
		    public static void pause(int downloadId) {
			        DownloadRequestQueue.getInstance().pause(downloadId);
			    }
		    public static void resume(int downloadId) {
			        DownloadRequestQueue.getInstance().resume(downloadId);
			    }
		    public static void cancel(int downloadId) {
			        DownloadRequestQueue.getInstance().cancel(downloadId);
			    }
		    public static void cancel(Object tag) {
			        DownloadRequestQueue.getInstance().cancel(tag);
			    }
		    public static void cancelAll() {
			        DownloadRequestQueue.getInstance().cancelAll();
			    }
		    public static Status getStatus(int downloadId) {
			        return DownloadRequestQueue.getInstance().getStatus(downloadId);
			    }
		    public static void cleanUp(int days) {
			        Utils.deleteUnwantedModelsAndTempFiles(days);
			    }
		    public static void shutDown() {
			        Core.shutDown();
			    }
	}
	
	public static class Response {
		    private Error error;
		    private boolean isSuccessful;
		    private boolean isPaused;
		    private boolean isCancelled;
		    public Error getError() {
			        return error;
			    }
		    public void setError(Error error) {
			        this.error = error;
			    }
		    public boolean isSuccessful() {
			        return isSuccessful;
			    }
		    public void setSuccessful(boolean successful) {
			        isSuccessful = successful;
			    }
		    public boolean isPaused() {
			        return isPaused;
			    }
		    public void setPaused(boolean paused) {
			        isPaused = paused;
			    }
		    public boolean isCancelled() {
			        return isCancelled;
			    }
		    public void setCancelled(boolean cancelled) {
			        isCancelled = cancelled;
			    }
	}
	
	public static enum Status {
		    QUEUED,
		    RUNNING,
		    PAUSED,
		    COMPLETED,
		    CANCELLED,
		    UNKNOWN
	}
	
	public static class Progress implements java.io.Serializable {
		    public long currentBytes;
		    public long totalBytes;
		    public Progress(long currentBytes, long totalBytes) {
			        this.currentBytes = currentBytes;
			        this.totalBytes = totalBytes;
			    }
		    @Override
		    public String toString() {
			        return "Progress{" +
			                "currentBytes=" + currentBytes +
			                ", totalBytes=" + totalBytes +
			                '}';
			    }
	}
	
	public static enum Priority {
		    LOW,
		    MEDIUM,
		    HIGH,
		    IMMEDIATE
	}
	
	
	public static class PRDownloaderConfig {
		    private int readTimeout;
		    private int connectTimeout;
		    private String userAgent;
		    private HttpClient httpClient;
		    private boolean databaseEnabled;
		    private PRDownloaderConfig(Builder builder) {
			        this.readTimeout = builder.readTimeout;
			        this.connectTimeout = builder.connectTimeout;
			        this.userAgent = builder.userAgent;
			        this.httpClient = builder.httpClient;
			        this.databaseEnabled = builder.databaseEnabled;
			    }
		    public int getReadTimeout() {
			        return readTimeout;
			    }
		    public void setReadTimeout(int readTimeout) {
			        this.readTimeout = readTimeout;
			    }
		    public int getConnectTimeout() {
			        return connectTimeout;
			    }
		    public void setConnectTimeout(int connectTimeout) {
			        this.connectTimeout = connectTimeout;
			    }
		    public String getUserAgent() {
			        return userAgent;
			    }
		    public void setUserAgent(String userAgent) {
			        this.userAgent = userAgent;
			    }
		    public HttpClient getHttpClient() {
			        return httpClient;
			    }
		    public void setHttpClient(HttpClient httpClient) {
			        this.httpClient = httpClient;
			    }
		    public boolean isDatabaseEnabled() {
			        return databaseEnabled;
			    }
		    public void setDatabaseEnabled(boolean databaseEnabled) {
			        this.databaseEnabled = databaseEnabled;
			    }
		    public static Builder newBuilder() {
			        return new Builder();
			    }
		    public static class Builder {
			        int readTimeout = Constants.DEFAULT_READ_TIMEOUT_IN_MILLS;
			        int connectTimeout = Constants.DEFAULT_CONNECT_TIMEOUT_IN_MILLS;
			        String userAgent = Constants.DEFAULT_USER_AGENT;
			        HttpClient httpClient = new DefaultHttpClient();
			        boolean databaseEnabled = false;
			        public Builder setReadTimeout(int readTimeout) {
				            this.readTimeout = readTimeout;
				            return this;
				        }
			        public Builder setConnectTimeout(int connectTimeout) {
				            this.connectTimeout = connectTimeout;
				            return this;
				        }
			        public Builder setUserAgent(String userAgent) {
				            this.userAgent = userAgent;
				            return this;
				        }
			        public Builder setHttpClient(HttpClient httpClient) {
				            this.httpClient = httpClient;
				            return this;
				        }
			        public Builder setDatabaseEnabled(boolean databaseEnabled) {
				            this.databaseEnabled = databaseEnabled;
				            return this;
				        }
			        public PRDownloaderConfig build() {
				            return new PRDownloaderConfig(this);
				        }
			    }
	}
	
	
	public static final class Constants {
		    private Constants() {
			    }
		    public static final int UPDATE = 0x01;
		    public static final String RANGE = "Range";
		    public static final String ETAG = "ETag";
		    public static final String USER_AGENT = "User-Agent";
		    public static final String DEFAULT_USER_AGENT = "Gymkhana-Studio";
		    public static final int DEFAULT_READ_TIMEOUT_IN_MILLS = 20_000;
		    public static final int DEFAULT_CONNECT_TIMEOUT_IN_MILLS = 20_000;
		    public static final int HTTP_RANGE_NOT_SATISFIABLE = 416;
		    public static final int HTTP_TEMPORARY_REDIRECT = 307;
		    public static final int HTTP_PERMANENT_REDIRECT = 308;
	}
	
	public static class Error {
		    private boolean isServerError;
		    private boolean isConnectionError;
		    public boolean isServerError() {
			        return isServerError;
			    }
		    public void setServerError(boolean serverError) {
			        isServerError = serverError;
			    }
		    public boolean isConnectionError() {
			        return isConnectionError;
			    }
		    public void setConnectionError(boolean connectionError) {
			        isConnectionError = connectionError;
			    }
	}
	
	public static interface OnCancelListener {
		    void onCancel();
	}
	
	public static interface OnDownloadListener {
		    void onDownloadComplete();
		    void onError(Error error);
	}
	
	public static interface OnPauseListener {
		    void onPause();
	}
	
	public static interface OnProgressListener {
		    void onProgress(Progress progress);
	}
	
	public static interface OnStartOrResumeListener {
		    void onStartOrResume();
	}
	
	
	
	public static class ProgressHandler extends Handler {
		    private final OnProgressListener listener;
		    public ProgressHandler(OnProgressListener listener) {
			        super(Looper.getMainLooper());
			        this.listener = listener;
			    }
		    @Override
		    public void handleMessage(Message msg) {
			        switch (msg.what) {
				            case Constants.UPDATE:
				                if (listener != null) {
					                    final Progress progress = (Progress) msg.obj;
					                    listener.onProgress(progress);
					                }
				                break;
				            default:
				                super.handleMessage(msg);
				                break;
				        }
			    }
	}
	
	public static class DefaultHttpClient implements HttpClient {
		    private java.net.URLConnection connection;
		    public DefaultHttpClient() {
			    }
		    @SuppressWarnings("CloneDoesntCallSuperClone")
		    @Override
		    public HttpClient clone() {
			        return new DefaultHttpClient();
			    }
		    @Override
		    public void connect(DownloadRequest request) throws java.io.IOException {
			        connection = new java.net.URL(request.getUrl()).openConnection();
			        connection.setReadTimeout(request.getReadTimeout());
			        connection.setConnectTimeout(request.getConnectTimeout());
			        final String range = String.format(Locale.ENGLISH,
			                "bytes=%d-", request.getDownloadedBytes());
			        connection.addRequestProperty(Constants.RANGE, range);
			        connection.addRequestProperty(Constants.USER_AGENT, request.getUserAgent());
			        addHeaders(request);
			        connection.connect();
			    }
		    @Override
		    public int getResponseCode() throws java.io.IOException {
			        int responseCode = 0;
			        if (connection instanceof java.net.HttpURLConnection) {
				            responseCode = ((java.net.HttpURLConnection) connection).getResponseCode();
				        }
			        return responseCode;
			    }
		    @Override
		    public java.io.InputStream getInputStream() throws java.io.IOException {
			        return connection.getInputStream();
			    }
		    @Override
		    public long getContentLength() {
			        String length = connection.getHeaderField("Content-Length");
			        try {
				            return Long.parseLong(length);
				        } catch (NumberFormatException e) {
				            return -1;
				        }
			    }
		    @Override
		    public String getResponseHeader(String name) {
			        return connection.getHeaderField(name);
			    }
		    @Override
		    public void close() {
			    }
		    private void addHeaders(DownloadRequest request) {
			        final HashMap<String, List<String>> headers = request.getHeaders();
			        if (headers != null) {
				            Set<Map.Entry<String, List<String>>> entries = headers.entrySet();
				            for (Map.Entry<String, List<String>> entry : entries) {
					                String name = entry.getKey();
					                List<String> list = entry.getValue();
					                if (list != null) {
						                    for (String value : list) {
							                        connection.addRequestProperty(name, value);
							                    }
						                }
					            }
				        }
			    }
		
	}
	
	public static interface HttpClient extends Cloneable {
		    HttpClient clone();
		    void connect(DownloadRequest request) throws java.io.IOException;
		    int getResponseCode() throws java.io.IOException;
		    java.io.InputStream getInputStream() throws java.io.IOException;
		    long getContentLength();
		    String getResponseHeader(String name);
		    void close();
	}
	
	public static class DownloadRequest {
		    private Priority priority;
		    private Object tag;
		    private String url;
		    private String dirPath;
		    private String fileName;
		    private int sequenceNumber;
		    private java.util.concurrent.Future future;
		    private long downloadedBytes;
		    private long totalBytes;
		    private int readTimeout;
		    private int connectTimeout;
		    private String userAgent;
		    private OnProgressListener onProgressListener;
		    private OnDownloadListener onDownloadListener;
		    private OnStartOrResumeListener onStartOrResumeListener;
		    private OnPauseListener onPauseListener;
		    private OnCancelListener onCancelListener;
		    private int downloadId;
		    private HashMap<String, List<String>> headerMap;
		    private Status status;
		
		    DownloadRequest(DownloadRequestBuilder builder) {
			        this.url = builder.url;
			        this.dirPath = builder.dirPath;
			        this.fileName = builder.fileName;
			        this.headerMap = builder.headerMap;
			        this.priority = builder.priority;
			        this.tag = builder.tag;
			        this.readTimeout =
			                builder.readTimeout != 0 ?
			                        builder.readTimeout :
			                        getReadTimeoutFromConfig();
			        this.connectTimeout =
			                builder.connectTimeout != 0 ?
			                        builder.connectTimeout :
			                        getConnectTimeoutFromConfig();
			        this.userAgent = builder.userAgent;
			    }
		
		    public Priority getPriority() {
			        return priority;
			    }
		
		    public void setPriority(Priority priority) {
			        this.priority = priority;
			    }
		
		    public Object getTag() {
			        return tag;
			    }
		
		    public void setTag(Object tag) {
			        this.tag = tag;
			    }
		
		    public String getUrl() {
			        return url;
			    }
		
		    public void setUrl(String url) {
			        this.url = url;
			    }
		
		    public String getDirPath() {
			        return dirPath;
			    }
		
		    public void setDirPath(String dirPath) {
			        this.dirPath = dirPath;
			    }
		
		    public String getFileName() {
			        return fileName;
			    }
		
		    public void setFileName(String fileName) {
			        this.fileName = fileName;
			    }
		
		    public int getSequenceNumber() {
			        return sequenceNumber;
			    }
		
		    public void setSequenceNumber(int sequenceNumber) {
			        this.sequenceNumber = sequenceNumber;
			    }
		
		    public HashMap<String, List<String>> getHeaders() {
			        return headerMap;
			    }
		
		    public java.util.concurrent.Future getFuture() {
			        return future;
			    }
		
		    public void setFuture(java.util.concurrent.Future future) {
			        this.future = future;
			    }
		
		    public long getDownloadedBytes() {
			        return downloadedBytes;
			    }
		
		    public void setDownloadedBytes(long downloadedBytes) {
			        this.downloadedBytes = downloadedBytes;
			    }
		
		    public long getTotalBytes() {
			        return totalBytes;
			    }
		
		    public void setTotalBytes(long totalBytes) {
			        this.totalBytes = totalBytes;
			    }
		
		    public int getReadTimeout() {
			        return readTimeout;
			    }
		
		    public void setReadTimeout(int readTimeout) {
			        this.readTimeout = readTimeout;
			    }
		
		    public int getConnectTimeout() {
			        return connectTimeout;
			    }
		
		    public void setConnectTimeout(int connectTimeout) {
			        this.connectTimeout = connectTimeout;
			    }
		
		    public String getUserAgent() {
			        if (userAgent == null) {
				            userAgent = ComponentHolder.getInstance().getUserAgent();
				        }
			        return userAgent;
			    }
		
		    public void setUserAgent(String userAgent) {
			        this.userAgent = userAgent;
			    }
		
		    public int getDownloadId() {
			        return downloadId;
			    }
		
		    public void setDownloadId(int downloadId) {
			        this.downloadId = downloadId;
			    }
		
		    public Status getStatus() {
			        return status;
			    }
		
		    public void setStatus(Status status) {
			        this.status = status;
			    }
		
		    public OnProgressListener getOnProgressListener() {
			        return onProgressListener;
			    }
		
		    public DownloadRequest setOnStartOrResumeListener(OnStartOrResumeListener onStartOrResumeListener) {
			        this.onStartOrResumeListener = onStartOrResumeListener;
			        return this;
			    }
		
		    public DownloadRequest setOnProgressListener(OnProgressListener onProgressListener) {
			        this.onProgressListener = onProgressListener;
			        return this;
			    }
		
		    public DownloadRequest setOnPauseListener(OnPauseListener onPauseListener) {
			        this.onPauseListener = onPauseListener;
			        return this;
			    }
		
		    public DownloadRequest setOnCancelListener(OnCancelListener onCancelListener) {
			        this.onCancelListener = onCancelListener;
			        return this;
			    }
		
		    public int start(OnDownloadListener onDownloadListener) {
			        this.onDownloadListener = onDownloadListener;
			        downloadId = Utils.getUniqueId(url, dirPath, fileName);
			        DownloadRequestQueue.getInstance().addRequest(this);
			        return downloadId;
			    }
		
		    public Response executeSync() {
			        downloadId = Utils.getUniqueId(url, dirPath, fileName);
			        return new SynchronousCall(this).execute();
			    }
		
		    public void deliverError(final Error error) {
			        if (status != Status.CANCELLED) {
				            Core.getInstance().getExecutorSupplier().forMainThreadTasks()
				                    .execute(new Runnable() {
					                        public void run() {
						                            if (onDownloadListener != null) {
							                                onDownloadListener.onError(error);
							                            }
						                            finish();
						                        }
					                    });
				        }
			    }
		
		    public void deliverSuccess() {
			        if (status != Status.CANCELLED) {
				            setStatus(Status.COMPLETED);
				            Core.getInstance().getExecutorSupplier().forMainThreadTasks()
				                    .execute(new Runnable() {
					                        public void run() {
						                            if (onDownloadListener != null) {
							                                onDownloadListener.onDownloadComplete();
							                            }
						                            finish();
						                        }
					                    });
				        }
			    }
		
		    public void deliverStartEvent() {
			        if (status != Status.CANCELLED) {
				            Core.getInstance().getExecutorSupplier().forMainThreadTasks()
				                    .execute(new Runnable() {
					                        public void run() {
						                            if (onStartOrResumeListener != null) {
							                                onStartOrResumeListener.onStartOrResume();
							                            }
						                        }
					                    });
				        }
			    }
		
		    public void deliverPauseEvent() {
			        if (status != Status.CANCELLED) {
				            Core.getInstance().getExecutorSupplier().forMainThreadTasks()
				                    .execute(new Runnable() {
					                        public void run() {
						                            if (onPauseListener != null) {
							                                onPauseListener.onPause();
							                            }
						                        }
					                    });
				        }
			    }
		
		    private void deliverCancelEvent() {
			        Core.getInstance().getExecutorSupplier().forMainThreadTasks()
			                .execute(new Runnable() {
				                    public void run() {
					                        if (onCancelListener != null) {
						                            onCancelListener.onCancel();
						                        }
					                    }
				                });
			    }
		
		    public void cancel() {
			        status = Status.CANCELLED;
			        if (future != null) {
				            future.cancel(true);
				        }
			        deliverCancelEvent();
			        Utils.deleteTempFileAndDatabaseEntryInBackground(Utils.getTempPath(dirPath, fileName), downloadId);
			    }
		
		    private void finish() {
			        destroy();
			        DownloadRequestQueue.getInstance().finish(this);
			    }
		
		    private void destroy() {
			        this.onProgressListener = null;
			        this.onDownloadListener = null;
			        this.onStartOrResumeListener = null;
			        this.onPauseListener = null;
			        this.onCancelListener = null;
			    }
		
		    private int getReadTimeoutFromConfig() {
			        return ComponentHolder.getInstance().getReadTimeout();
			    }
		
		    private int getConnectTimeoutFromConfig() {
			        return ComponentHolder.getInstance().getConnectTimeout();
			    }
		
	}
	
	public static class DownloadRequestBuilder implements RequestBuilder {
		    String url;
		    String dirPath;
		    String fileName;
		    Priority priority = Priority.MEDIUM;
		    Object tag;
		    int readTimeout;
		    int connectTimeout;
		    String userAgent;
		    HashMap<String, List<String>> headerMap;
		    public DownloadRequestBuilder(String url, String dirPath, String fileName) {
			        this.url = url;
			        this.dirPath = dirPath;
			        this.fileName = fileName;
			    }
		    @Override
		    public DownloadRequestBuilder setHeader(String name, String value) {
			        if (headerMap == null) {
				            headerMap = new HashMap<>();
				        }
			        List<String> list = headerMap.get(name);
			        if (list == null) {
				            list = new ArrayList<>();
				            headerMap.put(name, list);
				        }
			        if (!list.contains(value)) {
				            list.add(value);
				        }
			        return this;
			    }
		    @Override
		    public DownloadRequestBuilder setPriority(Priority priority) {
			        this.priority = priority;
			        return this;
			    }
		    @Override
		    public DownloadRequestBuilder setTag(Object tag) {
			        this.tag = tag;
			        return this;
			    }
		    @Override
		    public DownloadRequestBuilder setReadTimeout(int readTimeout) {
			        this.readTimeout = readTimeout;
			        return this;
			    }
		    @Override
		    public DownloadRequestBuilder setConnectTimeout(int connectTimeout) {
			        this.connectTimeout = connectTimeout;
			        return this;
			    }
		    @Override
		    public DownloadRequestBuilder setUserAgent(String userAgent) {
			        this.userAgent = userAgent;
			        return this;
			    }
		    public DownloadRequest build() {
			        return new DownloadRequest(this);
			    }
	}
	
	public static interface RequestBuilder {
		    RequestBuilder setHeader(String name, String value);
		    RequestBuilder setPriority(Priority priority);
		    RequestBuilder setTag(Object tag);
		    RequestBuilder setReadTimeout(int readTimeout);
		    RequestBuilder setConnectTimeout(int connectTimeout);
		    RequestBuilder setUserAgent(String userAgent);
	}
	
	public static final class Utils {
		    private final static int MAX_REDIRECTION = 10;
		    private Utils() {
			    }
		    public static String getPath(String dirPath, String fileName) {
			        return dirPath + java.io.File.separator + fileName;
			    }
		    public static String getTempPath(String dirPath, String fileName) {
			        return getPath(dirPath, fileName) + ".temp";
			    }
		    public static void renameFileName(String oldPath, String newPath) throws java.io.IOException {
			        final java.io.File oldFile = new java.io.File(oldPath);
			        try {
				            final java.io.File newFile = new java.io.File(newPath);
				            if (newFile.exists()) {
					                if (!newFile.delete()) {
						                    throw new java.io.IOException("Deletion Failed");
						                }
					            }
				            if (!oldFile.renameTo(newFile)) {
					                throw new java.io.IOException("Rename Failed");
					            }
				        } finally {
				            if (oldFile.exists()) {
					                oldFile.delete();
					            }
				        }
			    }
		
		    public static void deleteTempFileAndDatabaseEntryInBackground(final String path, final int downloadId) {
			        Core.getInstance().getExecutorSupplier().forBackgroundTasks()
			                .execute(new Runnable() {
				                    @Override
				                    public void run() {
					                        ComponentHolder.getInstance().getDbHelper().remove(downloadId);
					                        java.io.File file = new java.io.File(path);
					                        if (file.exists()) {
						                            //noinspection ResultOfMethodCallIgnored
						                            file.delete();
						                        }
					                    }
				                });
			    }
		
		    public static void deleteUnwantedModelsAndTempFiles(final int days) {
			        Core.getInstance().getExecutorSupplier().forBackgroundTasks()
			                .execute(new Runnable() {
				                    @Override
				                    public void run() {
					                        List<DownloadModel> models = ComponentHolder.getInstance()
					                                .getDbHelper()
					                                .getUnwantedModels(days);
					                        if (models != null) {
						                            for (DownloadModel model : models) {
							                                final String tempPath = getTempPath(model.getDirPath(), model.getFileName());
							                                ComponentHolder.getInstance().getDbHelper().remove(model.getId());
							                                java.io.File file = new java.io.File(tempPath);
							                                if (file.exists()) {
								                                    file.delete();
								                                }
							                            }
						                        }
					                    }
				                });
			    }
		
		    public static int getUniqueId(String url, String dirPath, String fileName) {
			        String string = url + java.io.File.separator + dirPath + java.io.File.separator + fileName;
			        byte[]  hash;
			        try {
				            hash = java.security.MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
				        } catch (java.security.NoSuchAlgorithmException e) {
				            throw new RuntimeException("NoSuchAlgorithmException", e);
				        } catch (java.io.UnsupportedEncodingException e) {
				            throw new RuntimeException("UnsupportedEncodingException", e);
				        }
			        StringBuilder hex = new StringBuilder(hash.length * 2);
			
			        for (byte b : hash) {
				            if ((b & 0xFF) < 0x10) hex.append("0");
				            hex.append(Integer.toHexString(b & 0xFF));
				        }
			        return hex.toString().hashCode();
			
			    }
		    public static HttpClient getRedirectedConnectionIfAny(HttpClient httpClient,
		                                                          DownloadRequest request)
		            throws java.io.IOException, IllegalAccessException {
			        int redirectTimes = 0;
			        int code = httpClient.getResponseCode();
			        String location = httpClient.getResponseHeader("Location");
			        while (isRedirection(code)) {
				            if (location == null) {
					                throw new IllegalAccessException("Location is null");
					            }
				            httpClient.close();
				            request.setUrl(location);
				            httpClient = ComponentHolder.getInstance().getHttpClient();
				            httpClient.connect(request);
				            code = httpClient.getResponseCode();
				            location = httpClient.getResponseHeader("Location");
				            redirectTimes++;
				            if (redirectTimes >= MAX_REDIRECTION) {
					                throw new IllegalAccessException("Max redirection done");
					            }
				        }
			        return httpClient;
			    }
		    private static boolean isRedirection(int code) {
			        return code == java.net.HttpURLConnection.HTTP_MOVED_PERM
			                || code == java.net.HttpURLConnection.HTTP_MOVED_TEMP
			                || code == java.net.HttpURLConnection.HTTP_SEE_OTHER
			                || code == java.net.HttpURLConnection.HTTP_MULT_CHOICE
			                || code == Constants.HTTP_TEMPORARY_REDIRECT
			                || code == Constants.HTTP_PERMANENT_REDIRECT;
			    }
	}
	
	
	public static class Core {
		    private static Core instance = null;
		    private final ExecutorSupplier executorSupplier;
		    private Core() {
			        this.executorSupplier = new DefaultExecutorSupplier();
			    }
		    public static Core getInstance() {
			        if (instance == null) {
				            synchronized (Core.class) {
					                if (instance == null) {
						                    instance = new Core();
						                }
					            }
				        }
			        return instance;
			    }
		    public ExecutorSupplier getExecutorSupplier() {
			        return executorSupplier;
			    }
		    public static void shutDown() {
			        if (instance != null) {
				            instance = null;
				        }
			    }
	}
	
	
	public static class PriorityThreadFactory implements java.util.concurrent.ThreadFactory {
		    private final int mThreadPriority;
		    PriorityThreadFactory(int threadPriority) {
			        mThreadPriority = threadPriority;
			    }
		    @Override
		    public Thread newThread(final Runnable runnable) {
			        Runnable wrapperRunnable = new Runnable() {
				            @Override
				            public void run() {
					                try {
						                    android.os.Process.setThreadPriority(mThreadPriority);
						                } catch (Throwable ignored) {
						
						                }
					                runnable.run();
					            }
				        };
			        return new Thread(wrapperRunnable);
			    }
	}
	public static class MainThreadExecutor implements java.util.concurrent.Executor {
		    private final Handler handler = new Handler(Looper.getMainLooper());
		    @Override
		    public void execute(Runnable runnable) {
			        handler.post(runnable);
			    }
	}
	
	public static class DefaultExecutorSupplier implements ExecutorSupplier {
		    private static final int DEFAULT_MAX_NUM_THREADS = 2 * Runtime.getRuntime().availableProcessors() + 1;
		    private final DownloadExecutor networkExecutor;
		    private final java.util.concurrent.Executor backgroundExecutor;
		    private final java.util.concurrent.Executor mainThreadExecutor;
		    DefaultExecutorSupplier() {
			        java.util.concurrent.ThreadFactory backgroundPriorityThreadFactory = new PriorityThreadFactory(android.os.Process.THREAD_PRIORITY_BACKGROUND);
			        networkExecutor = new DownloadExecutor(DEFAULT_MAX_NUM_THREADS, backgroundPriorityThreadFactory);
			        backgroundExecutor = java.util.concurrent.Executors.newSingleThreadExecutor();
			        mainThreadExecutor = new MainThreadExecutor();
			    }
		    @Override
		    public DownloadExecutor forDownloadTasks() {
			        return networkExecutor;
			    }
		    @Override
		    public java.util.concurrent.Executor forBackgroundTasks() {
			        return backgroundExecutor;
			    }
		    @Override
		    public java.util.concurrent.Executor forMainThreadTasks() {
			        return mainThreadExecutor;
			    }
	}
	
	public static interface ExecutorSupplier {
		    DownloadExecutor forDownloadTasks();
		    java.util.concurrent.Executor forBackgroundTasks();
		    java.util.concurrent.Executor forMainThreadTasks();
	}
	
	
	public static class FileDownloadRandomAccessFile implements FileDownloadOutputStream {
		    private final java.io.BufferedOutputStream out;
		    private final java.io.FileDescriptor fd;
		    private final java.io.RandomAccessFile randomAccess;
		    private FileDownloadRandomAccessFile(java.io.File file) throws java.io.IOException {
			        randomAccess = new java.io.RandomAccessFile(file, "rw");
			        fd = randomAccess.getFD();
			        out = new java.io.BufferedOutputStream(new java.io.FileOutputStream(randomAccess.getFD()));
			    }
		    @Override
		    public void write(byte[]  b, int off, int len) throws java.io.IOException {
			        out.write(b, off, len);
			    }
		    @Override
		    public void flushAndSync() throws java.io.IOException {
			        out.flush();
			        fd.sync();
			    }
		    @Override
		    public void close() throws java.io.IOException {
			        out.close();
			        randomAccess.close();
			    }
		    @Override
		    public void seek(long offset) throws java.io.IOException {
			        randomAccess.seek(offset);
			    }
		    @Override
		    public void setLength(long totalBytes) throws java.io.IOException {
			        randomAccess.setLength(totalBytes);
			    }
		    public static FileDownloadOutputStream create(java.io.File file) throws java.io.IOException {
			        return new FileDownloadRandomAccessFile(file);
			    }
	}
	
	public static interface FileDownloadOutputStream {
		    void write(byte b[] , int off, int len) throws java.io.IOException;
		    void flushAndSync() throws java.io.IOException;
		    void close() throws java.io.IOException;
		    void seek(long offset) throws java.io.IOException, IllegalAccessException;
		    void setLength(final long newLength) throws java.io.IOException, IllegalAccessException;
	}
	public static class DownloadRunnable implements Runnable {
		    public final Priority priority;
		    public final int sequence;
		    public final DownloadRequest request;
		    DownloadRunnable(DownloadRequest request) {
			        this.request = request;
			        this.priority = request.getPriority();
			        this.sequence = request.getSequenceNumber();
			    }
		    @Override
		    public void run() {
			        request.setStatus(Status.RUNNING);
			        DownloadTask downloadTask = DownloadTask.create(request);
			        Response response = downloadTask.run();
			        if (response.isSuccessful()) {
				            request.deliverSuccess();
				        } else if (response.isPaused()) {
				            request.deliverPauseEvent();
				        } else if (response.getError() != null) {
				            request.deliverError(response.getError());
				        } else if (!response.isCancelled()) {
				            request.deliverError(new Error());
				        }
			    }
	}
	
	
	public static class SynchronousCall {
		    public final DownloadRequest request;
		    public SynchronousCall(DownloadRequest request) {
			        this.request = request;
			    }
		    public Response execute() {
			        DownloadTask downloadTask = DownloadTask.create(request);
			        return downloadTask.run();
			    }
	}
	
	public static class ComponentHolder {
		    private final static ComponentHolder INSTANCE = new ComponentHolder();
		    private int readTimeout;
		    private int connectTimeout;
		    private String userAgent;
		    private HttpClient httpClient;
		    private DbHelper dbHelper;
		    public static ComponentHolder getInstance() {
			        return INSTANCE;
			    }
		    public void init(Context context, PRDownloaderConfig config) {
			        this.readTimeout = config.getReadTimeout();
			        this.connectTimeout = config.getConnectTimeout();
			        this.userAgent = config.getUserAgent();
			        this.httpClient = config.getHttpClient();
			        this.dbHelper = config.isDatabaseEnabled() ? new AppDbHelper(context) : new NoOpsDbHelper();
			        if (config.isDatabaseEnabled()) {
				            PRDownloader.cleanUp(30);
				        }
			    }
		    public int getReadTimeout() {
			        if (readTimeout == 0) {
				            synchronized (ComponentHolder.class) {
					                if (readTimeout == 0) {
						                    readTimeout = Constants.DEFAULT_READ_TIMEOUT_IN_MILLS;
						                }
					            }
				        }
			        return readTimeout;
			    }
		    public int getConnectTimeout() {
			        if (connectTimeout == 0) {
				            synchronized (ComponentHolder.class) {
					                if (connectTimeout == 0) {
						                    connectTimeout = Constants.DEFAULT_CONNECT_TIMEOUT_IN_MILLS;
						                }
					            }
				        }
			        return connectTimeout;
			    }
		    public String getUserAgent() {
			        if (userAgent == null) {
				            synchronized (ComponentHolder.class) {
					                if (userAgent == null) {
						                    userAgent = Constants.DEFAULT_USER_AGENT;
						                }
					            }
				        }
			        return userAgent;
			    }
		    public DbHelper getDbHelper() {
			        if (dbHelper == null) {
				            synchronized (ComponentHolder.class) {
					                if (dbHelper == null) {
						                    dbHelper = new NoOpsDbHelper();
						                }
					            }
				        }
			        return dbHelper;
			    }
		    public HttpClient getHttpClient() {
			        if (httpClient == null) {
				            synchronized (ComponentHolder.class) {
					                if (httpClient == null) {
						                    httpClient = new DefaultHttpClient();
						                }
					            }
				        }
			        return httpClient.clone();
			    }
	}
	
	
	public static class DownloadTask {
		    private static final int BUFFER_SIZE = 1024 * 4;
		    private static final long TIME_GAP_FOR_SYNC = 2000;
		    private static final long MIN_BYTES_FOR_SYNC = 65536;
		    private final DownloadRequest request;
		    private ProgressHandler progressHandler;
		    private long lastSyncTime;
		    private long lastSyncBytes;
		    private java.io.InputStream inputStream;
		    private FileDownloadOutputStream outputStream;
		    private HttpClient httpClient;
		    private long totalBytes;
		    private int responseCode;
		    private String eTag;
		    private boolean isResumeSupported;
		    private String tempPath;
		    private DownloadTask(DownloadRequest request) {
			        this.request = request;
			    }
		    static DownloadTask create(DownloadRequest request) {
			        return new DownloadTask(request);
			    }
		    Response run() {
			        Response response = new Response();
			        if (request.getStatus() == Status.CANCELLED) {
				            response.setCancelled(true);
				            return response;
				        } else if (request.getStatus() == Status.PAUSED) {
				            response.setPaused(true);
				            return response;
				        }
			        try {
				            if (request.getOnProgressListener() != null) {
					                progressHandler = new ProgressHandler(request.getOnProgressListener());
					            }
				            tempPath = Utils.getTempPath(request.getDirPath(), request.getFileName());
				            java.io.File file = new java.io.File(tempPath);
				            DownloadModel model = getDownloadModelIfAlreadyPresentInDatabase();
				            if (model != null) {
					                if (file.exists()) {
						                    request.setTotalBytes(model.getTotalBytes());
						                    request.setDownloadedBytes(model.getDownloadedBytes());
						                } else {
						                    removeNoMoreNeededModelFromDatabase();
						                    request.setDownloadedBytes(0);
						                    request.setTotalBytes(0);
						                    model = null;
						                }
					            }
				            httpClient = ComponentHolder.getInstance().getHttpClient();
				            httpClient.connect(request);
				            if (request.getStatus() == Status.CANCELLED) {
					                response.setCancelled(true);
					                return response;
					            } else if (request.getStatus() == Status.PAUSED) {
					                response.setPaused(true);
					                return response;
					            }
				            httpClient = Utils.getRedirectedConnectionIfAny(httpClient, request);
				            responseCode = httpClient.getResponseCode();
				            eTag = httpClient.getResponseHeader(Constants.ETAG);
				            if (checkIfFreshStartRequiredAndStart(model)) {
					                model = null;
					            }
				            if (!isSuccessful()) {
					                Error error = new Error();
					                error.setServerError(true);
					                response.setError(error);
					                return response;
					            }
				            setResumeSupportedOrNot();
				            totalBytes = request.getTotalBytes();
				            if (!isResumeSupported) {
					                deleteTempFile();
					            }
				            if (totalBytes == 0) {
					                totalBytes = httpClient.getContentLength();
					                request.setTotalBytes(totalBytes);
					            }
				            if (isResumeSupported && model == null) {
					                createAndInsertNewModel();
					            }
				            if (request.getStatus() == Status.CANCELLED) {
					                response.setCancelled(true);
					                return response;
					            } else if (request.getStatus() == Status.PAUSED) {
					                response.setPaused(true);
					                return response;
					            }
				            request.deliverStartEvent();
				            inputStream = httpClient.getInputStream();
				            byte[]  buff = new byte[BUFFER_SIZE] ;
				            if (!file.exists()) {
					                if (file.getParentFile() != null && !file.getParentFile().exists()) {
						                    if (file.getParentFile().mkdirs()) {
							                        file.createNewFile();
							                    }
						                } else {
						                    file.createNewFile();
						                }
					            }
				            this.outputStream = FileDownloadRandomAccessFile.create(file);
				            if (isResumeSupported && request.getDownloadedBytes() != 0) {
					                outputStream.seek(request.getDownloadedBytes());
					            }
				            if (request.getStatus() == Status.CANCELLED) {
					                response.setCancelled(true);
					                return response;
					            } else if (request.getStatus() == Status.PAUSED) {
					                response.setPaused(true);
					                return response;
					            }
				            do {
					                final int byteCount = inputStream.read(buff, 0, BUFFER_SIZE);
					                if (byteCount == -1) {
						                    break;
						                }
					                outputStream.write(buff, 0, byteCount);
					                request.setDownloadedBytes(request.getDownloadedBytes() + byteCount);
					                sendProgress();
					                syncIfRequired(outputStream);
					                if (request.getStatus() == Status.CANCELLED) {
						                    response.setCancelled(true);
						                    return response;
						                } else if (request.getStatus() == Status.PAUSED) {
						                    sync(outputStream);
						                    response.setPaused(true);
						                    return response;
						                }
					            } while (true);
				            final String path = Utils.getPath(request.getDirPath(), request.getFileName());
				            Utils.renameFileName(tempPath, path);
				            response.setSuccessful(true);
				            if (isResumeSupported) {
					                removeNoMoreNeededModelFromDatabase();
					            }
				        } catch (java.io.IOException | IllegalAccessException e) {
				            if (!isResumeSupported) {
					                deleteTempFile();
					            }
				            Error error = new Error();
				            error.setConnectionError(true);
				            response.setError(error);
				        } finally {
				            closeAllSafely(outputStream);
				        }
			        return response;
			    }
		    private void deleteTempFile() {
			        java.io.File file = new java.io.File(tempPath);
			        if (file.exists()) {
				            file.delete();
				        }
			    }
		    private boolean isSuccessful() {
			        return responseCode >= java.net.HttpURLConnection.HTTP_OK
			                && responseCode < java.net.HttpURLConnection.HTTP_MULT_CHOICE;
			    }
		    private void setResumeSupportedOrNot() {
			        isResumeSupported = (responseCode == java.net.HttpURLConnection.HTTP_PARTIAL);
			    }
		    private boolean checkIfFreshStartRequiredAndStart(DownloadModel model) throws java.io.IOException,
		            IllegalAccessException {
			        if (responseCode == Constants.HTTP_RANGE_NOT_SATISFIABLE || isETagChanged(model)) {
				            if (model != null) {
					                removeNoMoreNeededModelFromDatabase();
					            }
				            deleteTempFile();
				            request.setDownloadedBytes(0);
				            request.setTotalBytes(0);
				            httpClient = ComponentHolder.getInstance().getHttpClient();
				            httpClient.connect(request);
				            httpClient = Utils.getRedirectedConnectionIfAny(httpClient, request);
				            responseCode = httpClient.getResponseCode();
				            return true;
				        }
			        return false;
			    }
		    private boolean isETagChanged(DownloadModel model) {
			        return !(eTag == null || model == null || model.getETag() == null)
			                && !model.getETag().equals(eTag);
			    }
		    private DownloadModel getDownloadModelIfAlreadyPresentInDatabase() {
			        return ComponentHolder.getInstance().getDbHelper().find(request.getDownloadId());
			    }
		    private void createAndInsertNewModel() {
			        DownloadModel model = new DownloadModel();
			        model.setId(request.getDownloadId());
			        model.setUrl(request.getUrl());
			        model.setETag(eTag);
			        model.setDirPath(request.getDirPath());
			        model.setFileName(request.getFileName());
			        model.setDownloadedBytes(request.getDownloadedBytes());
			        model.setTotalBytes(totalBytes);
			        model.setLastModifiedAt(System.currentTimeMillis());
			        ComponentHolder.getInstance().getDbHelper().insert(model);
			    }
		    private void removeNoMoreNeededModelFromDatabase() {
			        ComponentHolder.getInstance().getDbHelper().remove(request.getDownloadId());
			    }
		    private void sendProgress() {
			        if (request.getStatus() != Status.CANCELLED) {
				            if (progressHandler != null) {
					                progressHandler
					                        .obtainMessage(Constants.UPDATE,
					                                new Progress(request.getDownloadedBytes(),
					                                        totalBytes)).sendToTarget();
					            }
				        }
			    }
		    private void syncIfRequired(FileDownloadOutputStream outputStream) {
			        final long currentBytes = request.getDownloadedBytes();
			        final long currentTime = System.currentTimeMillis();
			        final long bytesDelta = currentBytes - lastSyncBytes;
			        final long timeDelta = currentTime - lastSyncTime;
			        if (bytesDelta > MIN_BYTES_FOR_SYNC && timeDelta > TIME_GAP_FOR_SYNC) {
				            sync(outputStream);
				            lastSyncBytes = currentBytes;
				            lastSyncTime = currentTime;
				        }
			    }
		    private void sync(FileDownloadOutputStream outputStream) {
			        boolean success;
			        try {
				            outputStream.flushAndSync();
				            success = true;
				        } catch (java.io.IOException e) {
				            success = false;
				            e.printStackTrace();
				        }
			        if (success && isResumeSupported) {
				            ComponentHolder.getInstance().getDbHelper()
				                    .updateProgress(request.getDownloadId(),
				                            request.getDownloadedBytes(),
				                            System.currentTimeMillis());
				        }
			    }
		    private void closeAllSafely(FileDownloadOutputStream outputStream) {
			        if (httpClient != null) {
				            try {
					                httpClient.close();
					            } catch (Exception e) {
					                e.printStackTrace();
					            }
				        }
			        if (inputStream != null) {
				            try {
					                inputStream.close();
					            } catch (java.io.IOException e) {
					                e.printStackTrace();
					            }
				        }
			        try {
				            if (outputStream != null) {
					                try {
						                    sync(outputStream);
						                } catch (Exception e) {
						                    e.printStackTrace();
						                }
					            }
				
				        } finally {
				            if (outputStream != null)
				                try {
					                    outputStream.close();
					                } catch (java.io.IOException e) {
					                    e.printStackTrace();
					                }
				        }
			    }
	}
	
	
	public static class DownloadRequestQueue {
		    private static DownloadRequestQueue instance;
		    private final Map<Integer, DownloadRequest> currentRequestMap;
		    private final java.util.concurrent.atomic.AtomicInteger sequenceGenerator;
		    private DownloadRequestQueue() {
			        currentRequestMap = new java.util.concurrent.ConcurrentHashMap<>();
			        sequenceGenerator = new java.util.concurrent.atomic.AtomicInteger();
			    }
		    public static void initialize() {
			        getInstance();
			    }
		    public static DownloadRequestQueue getInstance() {
			        if (instance == null) {
				            synchronized (DownloadRequestQueue.class) {
					                if (instance == null) {
						                    instance = new DownloadRequestQueue();
						                }
					            }
				        }
			        return instance;
			    }
		    private int getSequenceNumber() {
			        return sequenceGenerator.incrementAndGet();
			    }
		    public void pause(int downloadId) {
			        DownloadRequest request = currentRequestMap.get(downloadId);
			        if (request != null) {
				            request.setStatus(Status.PAUSED);
				        }
			    }
		    public void resume(int downloadId) {
			        DownloadRequest request = currentRequestMap.get(downloadId);
			        if (request != null) {
				            request.setStatus(Status.QUEUED);
				            request.setFuture(Core.getInstance()
				                    .getExecutorSupplier()
				                    .forDownloadTasks()
				                    .submit(new DownloadRunnable(request)));
				        }
			    }
		    private void cancelAndRemoveFromMap(DownloadRequest request) {
			        if (request != null) {
				            request.cancel();
				            currentRequestMap.remove(request.getDownloadId());
				        }
			    }
		    public void cancel(int downloadId) {
			        DownloadRequest request = currentRequestMap.get(downloadId);
			        cancelAndRemoveFromMap(request);
			    }
		    public void cancel(Object tag) {
			        for (Map.Entry<Integer, DownloadRequest> currentRequestMapEntry : currentRequestMap.entrySet()) {
				            DownloadRequest request = currentRequestMapEntry.getValue();
				            if (request.getTag() instanceof String && tag instanceof String) {
					                final String tempRequestTag = (String) request.getTag();
					                final String tempTag = (String) tag;
					                if (tempRequestTag.equals(tempTag)) {
						                    cancelAndRemoveFromMap(request);
						                }
					            } else if (request.getTag().equals(tag)) {
					                cancelAndRemoveFromMap(request);
					            }
				        }
			    }
		    public void cancelAll() {
			        for (Map.Entry<Integer, DownloadRequest> currentRequestMapEntry : currentRequestMap.entrySet()) {
				            DownloadRequest request = currentRequestMapEntry.getValue();
				            cancelAndRemoveFromMap(request);
				        }
			    }
		    public Status getStatus(int downloadId) {
			        DownloadRequest request = currentRequestMap.get(downloadId);
			        if (request != null) {
				            return request.getStatus();
				        }
			        return Status.UNKNOWN;
			    }
		    public void addRequest(DownloadRequest request) {
			        currentRequestMap.put(request.getDownloadId(), request);
			        request.setStatus(Status.QUEUED);
			        request.setSequenceNumber(getSequenceNumber());
			        request.setFuture(Core.getInstance()
			                .getExecutorSupplier()
			                .forDownloadTasks()
			                .submit(new DownloadRunnable(request)));
			    }
		    public void finish(DownloadRequest request) {
			        currentRequestMap.remove(request.getDownloadId());
			    }
	}
	
	
	public static class DownloadModel {
		    static final String ID = "id";
		    static final String URL = "url";
		    static final String ETAG = "etag";
		    static final String DIR_PATH = "dir_path";
		    static final String FILE_NAME = "file_name";
		    static final String TOTAL_BYTES = "total_bytes";
		    static final String DOWNLOADED_BYTES = "downloaded_bytes";
		    static final String LAST_MODIFIED_AT = "last_modified_at";
		    private int id;
		    private String url;
		    private String eTag;
		    private String dirPath;
		    private String fileName;
		    private long totalBytes;
		    private long downloadedBytes;
		    private long lastModifiedAt;
		    public int getId() {
			        return id;
			    }
		    public void setId(int id) {
			        this.id = id;
			    }
		    public String getUrl() {
			        return url;
			    }
		    public void setUrl(String url) {
			        this.url = url;
			    }
		    public String getETag() {
			        return eTag;
			    }
		    public void setETag(String eTag) {
			        this.eTag = eTag;
			    }
		    public String getDirPath() {
			        return dirPath;
			    }
		    public void setDirPath(String dirPath) {
			        this.dirPath = dirPath;
			    }
		    public String getFileName() {
			        return fileName;
			    }
		    public void setFileName(String fileName) {
			        this.fileName = fileName;
			    }
		    public long getTotalBytes() {
			        return totalBytes;
			    }
		    public void setTotalBytes(long totalBytes) {
			        this.totalBytes = totalBytes;
			    }
		    public long getDownloadedBytes() {
			        return downloadedBytes;
			    }
		    public void setDownloadedBytes(long downloadedBytes) {
			        this.downloadedBytes = downloadedBytes;
			    }
		    public long getLastModifiedAt() {
			        return lastModifiedAt;
			    }
		    public void setLastModifiedAt(long lastModifiedAt) {
			        this.lastModifiedAt = lastModifiedAt;
			    }
	}
	
	
	
	public static class AppDbHelper implements DbHelper {
		
		    public static final String TABLE_NAME = "prdownloader";
		    private final android.database.sqlite.SQLiteDatabase db;
		    public AppDbHelper(Context context) {
			        DatabaseOpenHelper databaseOpenHelper = new DatabaseOpenHelper(context);
			        db = databaseOpenHelper.getWritableDatabase();
			    }
		    @Override
		    public DownloadModel find(int id) {
			        android.database.Cursor cursor = null;
			        DownloadModel model = null;
			        try {
				            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " +
				                    DownloadModel.ID + " = " + id, null);
				            if (cursor != null && cursor.moveToFirst()) {
					                model = new DownloadModel();
					                model.setId(id);
					                model.setUrl(cursor.getString(cursor.getColumnIndex(DownloadModel.URL)));
					                model.setETag(cursor.getString(cursor.getColumnIndex(DownloadModel.ETAG)));
					                model.setDirPath(cursor.getString(cursor.getColumnIndex(DownloadModel.DIR_PATH)));
					                model.setFileName(cursor.getString(cursor.getColumnIndex(DownloadModel.FILE_NAME)));
					                model.setTotalBytes(cursor.getLong(cursor.getColumnIndex(DownloadModel.TOTAL_BYTES)));
					                model.setDownloadedBytes(cursor.getLong(cursor.getColumnIndex(DownloadModel.DOWNLOADED_BYTES)));
					                model.setLastModifiedAt(cursor.getLong(cursor.getColumnIndex(DownloadModel.LAST_MODIFIED_AT)));
					            }
				        } catch (Exception e) {
				            e.printStackTrace();
				        } finally {
				            if (cursor != null) {
					                cursor.close();
					            }
				        }
			        return model;
			    }
		    @Override
		    public void insert(DownloadModel model) {
			        try {
				            ContentValues values = new ContentValues();
				            values.put(DownloadModel.ID, model.getId());
				            values.put(DownloadModel.URL, model.getUrl());
				            values.put(DownloadModel.ETAG, model.getETag());
				            values.put(DownloadModel.DIR_PATH, model.getDirPath());
				            values.put(DownloadModel.FILE_NAME, model.getFileName());
				            values.put(DownloadModel.TOTAL_BYTES, model.getTotalBytes());
				            values.put(DownloadModel.DOWNLOADED_BYTES, model.getDownloadedBytes());
				            values.put(DownloadModel.LAST_MODIFIED_AT, model.getLastModifiedAt());
				            db.insert(TABLE_NAME, null, values);
				        } catch (Exception e) {
				            e.printStackTrace();
				        }
			    }
		    @Override
		    public void update(DownloadModel model) {
			        try {
				            ContentValues values = new ContentValues();
				            values.put(DownloadModel.URL, model.getUrl());
				            values.put(DownloadModel.ETAG, model.getETag());
				            values.put(DownloadModel.DIR_PATH, model.getDirPath());
				            values.put(DownloadModel.FILE_NAME, model.getFileName());
				            values.put(DownloadModel.TOTAL_BYTES, model.getTotalBytes());
				            values.put(DownloadModel.DOWNLOADED_BYTES, model.getDownloadedBytes());
				            values.put(DownloadModel.LAST_MODIFIED_AT, model.getLastModifiedAt());
				            db.update(TABLE_NAME, values, DownloadModel.ID + " = ? ",
				                    new String[] {String.valueOf(model.getId())});
				        } catch (Exception e) {
				            e.printStackTrace();
				        }
			    }
		    @Override
		    public void updateProgress(int id, long downloadedBytes, long lastModifiedAt) {
			        try {
				            ContentValues values = new ContentValues();
				            values.put(DownloadModel.DOWNLOADED_BYTES, downloadedBytes);
				            values.put(DownloadModel.LAST_MODIFIED_AT, lastModifiedAt);
				            db.update(TABLE_NAME, values, DownloadModel.ID + " = ? ",
				                    new String[] {String.valueOf(id)});
				        } catch (Exception e) {
				            e.printStackTrace();
				        }
			    }
		    @Override
		    public void remove(int id) {
			        try {
				            db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " +
				                    DownloadModel.ID + " = " + id);
				        } catch (Exception e) {
				            e.printStackTrace();
				        }
			    }
		    @Override
		    public List<DownloadModel> getUnwantedModels(int days) {
			        List<DownloadModel> models = new ArrayList<>();
			        android.database.Cursor cursor = null;
			        try {
				            final long daysInMillis = days * 24 * 60 * 60 * 1000L;
				            final long beforeTimeInMillis = System.currentTimeMillis() - daysInMillis;
				            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " +
				                    DownloadModel.LAST_MODIFIED_AT + " <= " + beforeTimeInMillis, null);
				            if (cursor != null && cursor.moveToFirst()) {
					                do {
						                    DownloadModel model = new DownloadModel();
						                    model.setId(cursor.getInt(cursor.getColumnIndex(DownloadModel.ID)));
						                    model.setUrl(cursor.getString(cursor.getColumnIndex(DownloadModel.URL)));
						                    model.setETag(cursor.getString(cursor.getColumnIndex(DownloadModel.ETAG)));
						                    model.setDirPath(cursor.getString(cursor.getColumnIndex(DownloadModel.DIR_PATH)));
						                    model.setFileName(cursor.getString(cursor.getColumnIndex(DownloadModel.FILE_NAME)));
						                    model.setTotalBytes(cursor.getLong(cursor.getColumnIndex(DownloadModel.TOTAL_BYTES)));
						                    model.setDownloadedBytes(cursor.getLong(cursor.getColumnIndex(DownloadModel.DOWNLOADED_BYTES)));
						                    model.setLastModifiedAt(cursor.getLong(cursor.getColumnIndex(DownloadModel.LAST_MODIFIED_AT)));
						                    models.add(model);
						                } while (cursor.moveToNext());
					            }
				        } catch (Exception e) {
				            e.printStackTrace();
				        } finally {
				            if (cursor != null) {
					                cursor.close();
					            }
				        }
			        return models;
			    }
		    @Override
		    public void clear() {
			        try {
				            db.delete(TABLE_NAME, null, null);
				        } catch (Exception e) {
				            e.printStackTrace();
				        }
			    }
	}
	
	public static class DatabaseOpenHelper extends android.database.sqlite.SQLiteOpenHelper {
		    private static final String DATABASE_NAME = "prdownloader.db";
		    private static final int DATABASE_VERSION = 1;
		    DatabaseOpenHelper(Context context) {
			        super(context, DATABASE_NAME, null, DATABASE_VERSION);
			    }
		    @Override
		    public void onCreate(android.database.sqlite.SQLiteDatabase db) {
			        db.execSQL("CREATE TABLE IF NOT EXISTS " +
			                AppDbHelper.TABLE_NAME + "( " +
			                DownloadModel.ID + " INTEGER PRIMARY KEY, " +
			                DownloadModel.URL + " VARCHAR, " +
			                DownloadModel.ETAG + " VARCHAR, " +
			                DownloadModel.DIR_PATH + " VARCHAR, " +
			                DownloadModel.FILE_NAME + " VARCHAR, " +
			                DownloadModel.TOTAL_BYTES + " INTEGER, " +
			                DownloadModel.DOWNLOADED_BYTES + " INTEGER, " +
			                DownloadModel.LAST_MODIFIED_AT + " INTEGER " +
			                ")");
			    }
		    @Override
		    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int i, int i1) {
			    }
	}
	
	public static interface DbHelper {
		    DownloadModel find(int id);
		    void insert(DownloadModel model);
		    void update(DownloadModel model);
		    void updateProgress(int id, long downloadedBytes, long lastModifiedAt);
		    void remove(int id);
		    List<DownloadModel> getUnwantedModels(int days);
		    void clear();
	}
	
	
	public static class NoOpsDbHelper implements DbHelper {
		    public NoOpsDbHelper() {
			    }
		    @Override
		    public DownloadModel find(int id) {
			        return null;
			    }
		    @Override
		    public void insert(DownloadModel model) {
			    }
		    @Override
		    public void update(DownloadModel model) {
			    }
		    @Override
		    public void updateProgress(int id, long downloadedBytes, long lastModifiedAt) {
			    }
		    @Override
		    public void remove(int id) {
			    }
		    @Override
		    public List<DownloadModel> getUnwantedModels(int days) {
			        return null;
			    }
		    @Override
		    public void clear() {
			    }
	}
	
	public static class DownloadFutureTask extends java.util.concurrent.FutureTask<DownloadRunnable> implements Comparable<DownloadFutureTask> {
		    private final DownloadRunnable runnable;
		    DownloadFutureTask(DownloadRunnable downloadRunnable) {
			        super(downloadRunnable, null);
			        this.runnable = downloadRunnable;
			    }
		    @Override
		    public int compareTo(DownloadFutureTask other) {
			        Priority p1 = runnable.priority;
			        Priority p2 = other.runnable.priority;
			        return (p1 == p2 ? runnable.sequence - other.runnable.sequence : p2.ordinal() - p1.ordinal());
			    }
	}
	
	public static class DownloadExecutor extends java.util.concurrent.ThreadPoolExecutor {
		    DownloadExecutor(int maxNumThreads, java.util.concurrent.ThreadFactory threadFactory) {
			        super(maxNumThreads, maxNumThreads, 0, java.util.concurrent.TimeUnit.MILLISECONDS,
			                new java.util.concurrent.PriorityBlockingQueue<Runnable>(), threadFactory);
			    }
		    @Override
		    public java.util.concurrent.Future<?> submit(Runnable task) {
			        DownloadFutureTask futureTask = new DownloadFutureTask((DownloadRunnable) task);
			        execute(futureTask);
			        return futureTask;
			    }
	}
	
	{
		
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}