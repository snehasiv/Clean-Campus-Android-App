package com.example.SEPM;





import android.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;




public class TabsPagerAdapter extends FragmentPagerAdapter {
Fragment fragment;
FragmentManager 	fragmentManager;
ViewPager vp;

	public TabsPagerAdapter(FragmentManager fragmentManager) {
		super(fragmentManager);
		this.fragmentManager=fragmentManager;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int index) {
		// TODO Auto-generated method stub
		 switch (index) {
	        case 0:
	            
	          return  new AllComplaintsFragment();
	          
	            
	        case 1:
	            
	            return new MyComplaintsFragment();
	           
	       
	        }
	 
	
	        return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}

}
