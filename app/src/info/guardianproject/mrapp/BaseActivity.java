package info.guardianproject.mrapp;
import info.guardianproject.mrapp.media.OverlayCameraActivity;
import info.guardianproject.mrapp.server.LoginActivity;

import java.io.IOException;
import java.io.InputStream;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.Toast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.view.WindowManager;
import android.widget.ImageView;

import com.actionbarsherlock.view.Window;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingActivity;

public class BaseActivity extends SlidingActivity {

	private SlidingMenu mSlidingMenu;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setProgressBarIndeterminate(false);
        setProgressBarIndeterminateVisibility(false);
        */
        
        // setup drawer
        setBehindContentView(R.layout.fragment_drawer);
        mSlidingMenu = getSlidingMenu();
        mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);
        mSlidingMenu.setShadowDrawable(R.drawable.shadow);
//        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        mSlidingMenu.setBehindWidthRes(R.dimen.slidingmenu_offset);
        
        final Activity activity = this;
        
        ImageButton btnDrawerQuickCaptureVideo = (ImageButton) findViewById(R.id.btnDrawerQuickCaptureVideo);
        ImageButton btnDrawerQuickCapturePhoto = (ImageButton) findViewById(R.id.btnDrawerQuickCapturePhoto);
        ImageButton btnDrawerQuickCaptureAudio = (ImageButton) findViewById(R.id.btnDrawerQuickCaptureAudio);
        
        Button btnDrawerHome = (Button) findViewById(R.id.btnDrawerHome);
        Button btnDrawerProjects = (Button) findViewById(R.id.btnDrawerProjects);
        Button btnDrawerLessons = (Button) findViewById(R.id.btnDrawerLessons);
        Button btnDrawerAccount = (Button) findViewById(R.id.btnDrawerAccount);
        Button btnDrawerSettings = (Button) findViewById(R.id.btnDrawerSettings);
        

       
        btnDrawerQuickCaptureVideo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	 Intent i = new Intent(activity, OverlayCameraActivity.class);
                 activity.startActivity(i);           
                 }
        });
        
        btnDrawerHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	
            	mSlidingMenu.showContent(true);
                
            	 Intent i = new Intent(activity, HomeActivity.class);
                 activity.startActivity(i);
            }
        });
        btnDrawerProjects.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            	mSlidingMenu.showContent(true);
            	  Intent i = new Intent(activity, ProjectsActivity.class);
                  activity.startActivity(i);
            }
        });
        btnDrawerLessons.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            	mSlidingMenu.showContent(true);
            	
                Intent i = new Intent(activity, LessonsActivity.class);
                activity.startActivity(i);
            }
        });
        
        btnDrawerAccount.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            	mSlidingMenu.showContent(true);
                Intent i = new Intent(activity, LoginActivity.class);
                activity.startActivity(i);
            }
        });
        
        btnDrawerSettings.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	mSlidingMenu.showContent(true);

                Intent i = new Intent(activity, SimplePreferences.class);
                activity.startActivity(i);
            }
        });
        
    }
    
    private void detectCoachOverlay ()
    {
        try {
        	
        	if (this.getClass().getName().contains("SceneEditorActivity"))
        	{
        		showCoachOverlay("images/coach/coach_add.png");
        	}
        	else if (this.getClass().getName().contains("OverlayCameraActivity"))
        	{
        		showCoachOverlay("images/coach/coach_camera_prep.png");
        	}
        		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * 
	public void switchContent(final Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, fragment)
		.commit();
		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			public void run() {
				getSlidingMenu().showContent();
			}
		}, 50);
	}	
**/
    
    private void showCoachOverlay (String path) throws IOException
    {
    	ImageView overlayView = new ImageView(this);
    	
    	overlayView.setOnClickListener(new OnClickListener () 
    	{

			@Override
			public void onClick(View v) {
				getWindowManager().removeView(v);
				
			}
    		
    	});
    	
    	AssetManager mngr = getAssets();
        // Create an input stream to read from the asset folder
           InputStream ins = mngr.open(path);

           // Convert the input stream into a bitmap
           Bitmap bmpCoach = BitmapFactory.decodeStream(ins);
           overlayView.setImageBitmap(bmpCoach);
           
    	WindowManager.LayoutParams params = new WindowManager.LayoutParams(
    	        WindowManager.LayoutParams.MATCH_PARENT,
    	        WindowManager.LayoutParams.MATCH_PARENT,
    	        WindowManager.LayoutParams.TYPE_APPLICATION,
    	        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
    	        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
    	        PixelFormat.TRANSLUCENT);

    	getWindowManager().addView(overlayView, params);
    }
}