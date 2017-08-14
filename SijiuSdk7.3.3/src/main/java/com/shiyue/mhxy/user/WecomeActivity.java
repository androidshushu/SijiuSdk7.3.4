package com.shiyue.mhxy.user;

import com.shiyue.mhxy.config.AppConfig;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class WecomeActivity extends Activity {
	@Override  
    public void onCreate(Bundle savedInstanceState) {  
	super.onCreate(savedInstanceState);  
    
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);  
    
    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
            WindowManager.LayoutParams.FLAG_FULLSCREEN);  
    setContentView(AppConfig.resourceId(this, "sjwecome", "layout"));  
  //  ImageView logoImage = (ImageView) this.findViewById(R.id.logo);  
    AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);  
    alphaAnimation.setDuration(3000); 
    ImageView logoImage = (ImageView) this.findViewById(AppConfig.resourceId(this, "sjlogo","id" ));  
    logoImage.startAnimation(alphaAnimation);  
    alphaAnimation.setAnimationListener(new AnimationListener() {  

        @Override  
        public void onAnimationStart(Animation animation) {  

        }  

        @Override  
        public void onAnimationRepeat(Animation animation) {  

        }  

        @Override  
        public void onAnimationEnd(Animation animation) {   
            finish();  
        }  
    });  
}  
}


