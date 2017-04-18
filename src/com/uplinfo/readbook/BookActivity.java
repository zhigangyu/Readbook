package com.uplinfo.readbook;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.uplinfo.readbook.view.adapter.ViewPagerAdapter;
import com.uplinfo.readbook.view.fragment.BookFragment;
import com.uplinfo.readbook.view.fragment.DiscussFragment;

public class BookActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book);
		
		getApplicationContext().setTheme(R.style.AppThemeDefault);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

		setSupportActionBar(toolbar);
		toolbar.setOnMenuItemClickListener(onMenuItemClick);

		// BookCoverView v = (BookCoverView) findViewById(R.id.bookcover);
		// v.setImage("1.jpg");

		TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);

		Fragment[] fragments = { new BookFragment(), new DiscussFragment(),
				new DiscussFragment() };
		String[] titles = getResources().getStringArray(R.array.home_tabs);
		ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
		viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),
				fragments, titles));

		tabLayout.setupWithViewPager(viewPager);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
		@Override
		public boolean onMenuItemClick(MenuItem menuItem) {
			String msg = "";
			switch (menuItem.getItemId()) {
			case R.id.action_edit:
				msg += "Click edit";
				break;
			case R.id.action_share:
				msg += "Click share";
				break;
			case R.id.action_settings:
				msg += "Click setting";
				break;
			}

			if (!msg.equals("")) {
				Toast.makeText(BookActivity.this, msg, Toast.LENGTH_SHORT)
						.show();
			}
			return true;
		}
	};

}
