import com.android.systemui.navigationbar.NavigationBar;
import com.android.systemui.navigationbar.NavigationBarView;

// ++435
    public void setNavigationLocked(boolean locked) {
        if (mNavigationBars == null) {
            Log.i(TAG, "setNavigationLocked.");
            return;
        }

        NavigationBarView mNavigationBarView = getCurrentNavigationBarView();
        if (mNavigationBarView != null) {
            mNavigationBarView.setVisibility(locked ? View.GONE : View.VISIBLE);
            // Use setClickable instead of OnTouchListener
            mNavigationBarView.setClickable(!locked);
            mNavigationBarView.setFocusable(!locked);
        }
    }

    private NavigationBarView getCurrentNavigationBarView() {
        for (int i = 0; i < mNavigationBars.size(); i++) {
            NavigationBar navBar = mNavigationBars.valueAt(i);
            if (navBar != null) {
                return navBar.getView();
            }
        }
        return null;
    }