package itest.kz.util;

import android.support.v4.view.ViewPager;
import android.widget.TextView;

import itest.kz.R;

public class PageListener extends ViewPager.SimpleOnPageChangeListener {
    private int currentPage;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void onPageSelected(int position)
    {
        currentPage = position;
//        TextView tv = findViewById(R.id.text_number_pager);
//        tv.setText("fdfvf" + currentPage);
    }
}
